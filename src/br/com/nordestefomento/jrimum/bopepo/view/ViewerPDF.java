/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:05:16
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:05:16
 * 
 */

package br.com.nordestefomento.jrimum.bopepo.view;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;
import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.JRimumException;
import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.type.Endereco;
import br.com.nordestefomento.jrimum.utilix.DateUtil;
import br.com.nordestefomento.jrimum.utilix.FileUtil;
import br.com.nordestefomento.jrimum.utilix.MonetaryUtil;
import br.com.nordestefomento.jrimum.utilix.PDFUtil;
import br.com.nordestefomento.jrimum.utilix.RectanglePDF;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * 
 * <p>
 * DEFINIÇÃO DA CLASSE
 * </p>
 * 
 * <p>
 * OBJETIVO/PROPÓSITO
 * </p>
 * 
 * <p>
 * EXEMPLO:
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class ViewerPDF {

	// TODO Teste no teste unitário.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(ViewerPDF.class);

	private static URL TEMPLATE_PADRAO_COM_SACADOR_AVALISTA = ViewerPDF.class.getResource("/resource/pdf/BoletoTemplateComSacadorAvalista.pdf");
	private static URL TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA = ViewerPDF.class.getResource("/resource/pdf/BoletoTemplateSemSacadorAvalista.pdf");

	private static final String HIFEN_SEPERADOR = "-";
	
	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	
	private ByteArrayOutputStream outputStream;

	private Boleto boleto;

	private File template;

	/**
	 *<p> Para uso interno do componente </p> 
	 */
	ViewerPDF() {
	}
	
	ViewerPDF(Boleto boleto) {
		
		this.boleto = boleto;
	}
	
	ViewerPDF(Boleto boleto, File template) {
		
		this.boleto = boleto;
		
		setTemplate(template);
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param pathName arquivo de destino
	 * @param boletos a serem agrupados
	 * @param boletoViewer visualizador
	 * @return File contendo boletos gerados
	 * 
	 * @throws JRimumException Quando ocorrer um problema na geração do PDF que está fora do controle
	 * da biblioteca.
	 * 
	 * @since 0.2
	 */
	protected static File groupInOnePDF(String pathName, List<Boleto> boletos, BoletoViewer boletoViewer) {

		File arq = null;

		List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());

		for (Boleto bop : boletos) {
			boletosEmBytes.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
		}

		try {
			
			arq = FileUtil.bytes2File(pathName, PDFUtil.mergeFiles(boletosEmBytes));
			
		} catch (FileNotFoundException e) {
			
			log.error("Erro durante geração do PDF." + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante geração do PDF. Causado por " + e.getLocalizedMessage(), e);
			
		} catch (IOException e) {

			log.error("Erro durante geração do PDF." + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante geração do PDF. Causado por " + e.getLocalizedMessage(), e);
		}

		return arq;
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param path
	 * @param extensao TODO
	 * @param boletos
	 * @return List<File> com os boletos gerados.
	 * 
	 * @since 0.2
	 */
	protected static List<File> onePerPDF(String path, String extensao, List<Boleto> boletos) {

		List<File> arquivos = new ArrayList<File>(boletos.size());
		int cont = 1;

		for (Boleto bop : boletos) {
			arquivos.add(new BoletoViewer(bop).getPdfAsFile(path + "Boleto" + cont++ + extensao));
		}

		return arquivos;
	}

	/**
	 * 
	 * @param pathName
	 * @return
	 * @throws IllegalArgumentException
	 */
	protected File getFile(String pathName) {
		
		File file = null;

		try {

			processarPdf();
			
			file = FileUtil.bytes2File(pathName, outputStream.toByteArray());
			
		} catch (FileNotFoundException e) {
			
			log.error("Erro ao tentar acessar arquivo inexistente. " + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro ao tentar acessar arquivo inexistente: [" + pathName + "]. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (IOException e) {
			
			log.error("Erro durante a criação do arquivo. " + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do arquivo: [" + pathName + "]. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (DocumentException e) {
			
			log.error("Erro durante a criação do arquivo. " + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do arquivo: [" + pathName + "]. " +
					"Causado por " + e.getLocalizedMessage(), e);
		}
		
		return file;
	}

	/**
	 * @throws JRimumException
	 * 
	 * @return
	 */
	protected ByteArrayOutputStream getStream() {
		
		ByteArrayOutputStream baos = null;
		
		try {

			processarPdf();
			
			baos = FileUtil.bytes2Stream(outputStream.toByteArray());
			
		} catch (IOException e) {
			
			log.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (DocumentException e) {
			
			log.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
		}
		
		return baos;
	}

	/**
	 * @throws JRimumException
	 * 
	 * @return
	 */
	protected byte[] getBytes() {
		
		byte[] bytes = null;
		
		try {

			processarPdf();
			
			bytes = outputStream.toByteArray();
			
		} catch (IOException e) {
			
			log.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
			
		} catch (DocumentException e) {
			
			log.error("Erro durante a criação do stream. " + e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. " +
					"Causado por " + e.getLocalizedMessage(), e);
		}
		
		return bytes;
	}

	protected File getTemplate() {
		return template;
	}

	protected void setTemplate(File template) {
		this.template = template;
	}

	protected void setTemplate(String pathname) {
		setTemplate(new File(pathname));
	}

	/**
	 * @return the boleto
	 * 
	 * @since 0.2
	 */
	protected Boleto getBoleto() {
		return this.boleto;
	}
	
	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since
	 */
	private void processarPdf() throws IOException, DocumentException {
		
		inicializar();
		preencher();
		finalizar();
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @return URL template
	 * 
	 * @since
	 */
	private URL getTemplateFromResource() {

		URL templateFromResource = null;

		if (boleto.getTitulo().hasSacadorAvalista()) {
			templateFromResource = TEMPLATE_PADRAO_COM_SACADOR_AVALISTA;
			
		} else {
			templateFromResource = TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA;
		}

		return templateFromResource;
	}

	/**
	 * <p>
	 * Verifica se o template que será utilizado virá do resource ou é externo,
	 * ou seja, se o usuário definiu ou não um template.
	 * </p>
	 * 
	 * @return true caso o template que pode ser definido pelo usuário for null;
	 *         false caso o usuário tenha definido um template.
	 * 
	 * @since
	 */
	private boolean isTemplateFromResource() {
		return isNull(getTemplate());
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since
	 */

	private void inicializar() throws IOException, DocumentException {

		if (isTemplateFromResource()) {
			reader = new PdfReader(getTemplateFromResource());
			
		} else {
			reader = new PdfReader(getTemplate().getAbsolutePath());
		}

		outputStream = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, outputStream);
		form = stamper.getAcroFields();
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 * 
	 * @since
	 */
	private void finalizar() throws DocumentException, IOException {

		reader.consolidateNamedDestinations();/*
												 * Replaces all the local named
												 * links with the actual
												 * destinations.
												 */

		stamper.setFormFlattening(true);/*
										 * Determines if the fields are
										 * flattened on close.
										 */
		stamper.setRotateContents(true);/*
										 * Flags the content to be automatically
										 * adjusted to compensate the original
										 * page rotation.
										 */

		reader.removeFields();/* Removes all the fields from the document. */

		stamper.setFullCompression();/*
										 * Sets the document's compression to
										 * the new 1.5 mode with object streams
										 * and xref streams.
										 */

		reader.eliminateSharedStreams();/*
										 * Eliminates shared streams if they
										 * exist.
										 */

		// Send immediately
		outputStream.flush();

		// close All in this order
		outputStream.close();
		reader.close();
		stamper.close();
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since
	 */
	private void preencher() throws MalformedURLException, IOException, DocumentException {
		
		setLogoBanco();
		setCodigoBanco();
		setLinhaDigitavel();
		setCedente();
		setAgenciaCondigoCedente();
		setEspecie();
		setQuantidade();
		setNossoNumero();
		setNumeroDocumento();
		setAbstractCPRFCedente();
		setDataVencimeto();
		setValorDocumento();
		setDescontoAbatimento();
		setOutraDeducao();
		setMoraMulta();
		setOutroAcrescimo();
		setInstrucaoAoSacado();
		setInstrucaoAoCaixa();
		setSacado();
		setLocalPagamento();
		setDataDocumento();
		setEspecieDoc();
		setAceite();
		setDataProcessamento();
		setSacadorAvalista();
		setCodigoBarra();
		setCarteira();
		setCamposExtra();
		setImagensNosCampos();
	}

	private void setCamposExtra() throws IOException, DocumentException {

		if (isNotNull(boleto.getTextosExtras())) {
			
			for (String campo : boleto.getTextosExtras().keySet()) {
				form.setField(campo, boleto.getTextosExtras().get(campo));
			}
		}
	}

	private void setCodigoBarra() throws DocumentException {

		// Montando o código de barras.
		BarcodeInter25 barCode = new BarcodeInter25();
		barCode.setCode(boleto.getCodigoDeBarras().write());

		barCode.setExtended(true);
		barCode.setBarHeight(40);
		barCode.setFont(null);
		barCode.setN(3);

	
		// FICHA DE COMPENSAÇÃO
		PdfContentByte cb = null;

		// Verifcando se existe o field(campo) da imagem no template do boleto.
		float posCampoImgLogo[] = form.getFieldPositions("txtFcCodigoBarra");
		
		if (isNotNull(posCampoImgLogo)) {
			
			RectanglePDF field = new RectanglePDF(posCampoImgLogo);
			
			cb = stamper.getOverContent(field.getPage());
			Image imgBarCode = barCode.createImageWithBarcode(cb, null, null);
			
			PDFUtil.changeField2Image(stamper, field, imgBarCode);
		}
	}

	private void setDataProcessamento() throws IOException, DocumentException {
		form.setField("txtFcDataProcessamento", DateUtil.FORMAT_DD_MM_YYYY.format(boleto.getDataDeProcessamento()));
	}

	private void setAceite() throws IOException, DocumentException {

		if (isNotNull(boleto.getTitulo().getAceite())) {
			form.setField("txtFcAceite", boleto.getTitulo().getAceite().name());
		}
	}

	private void setEspecieDoc() throws IOException, DocumentException {
		form.setField("txtFcEspecieDocumento", boleto.getTitulo().getTipoDeDocumento().getSigla());
	}

	private void setDataDocumento() throws IOException, DocumentException {
		form.setField("txtFcDataDocumento", DateUtil.FORMAT_DD_MM_YYYY.format(boleto.getTitulo().getDataDoDocumento()));
	}

	private void setLocalPagamento() throws IOException, DocumentException {
		form.setField("txtFcLocalPagamento", (boleto.getLocalPagamento()));
	}

	private void setSacado() throws IOException, DocumentException {

		StringBuilder sb = new StringBuilder();
		Pessoa sacado = boleto.getTitulo().getSacado();

		if (isNotNull(sacado.getNome())) {
			sb.append(sacado.getNome());
		}
		
		if (isNotNull(sacado.getCPRF())) {
			sb.append(", ");
		
			if (sacado.getCPRF().isFisica()) {
				sb.append("CPF: ");
				
			} else if (sacado.getCPRF().isJuridica()) {
				sb.append("CNPJ: ");
			}

			sb.append(sacado.getCPRF().getCodigoFormatado());
		}
		
		form.setField("txtRsSacado", sb.toString());
		form.setField("txtFcSacadoL1", sb.toString());

		// TODO Código em teste
		sb.delete(0, sb.length());
		Endereco endereco = sacado.getEnderecos().iterator().next();
		
		setEndereco(endereco, "txtFcSacadoL2", "txtFcSacadoL3", sb);
	}

	private void setSacadorAvalista() throws IOException, DocumentException {
		
		if (boleto.getTitulo().hasSacadorAvalista()) {
			
			Pessoa sacadorAvalista = boleto.getTitulo().getSacadorAvalista(); 
			
			StringBuilder sb = new StringBuilder();

			if (isNotNull(sacadorAvalista.getNome())) {
				sb.append(sacadorAvalista.getNome());
			}
			
			if (isNotNull(sacadorAvalista.getCPRF())) {
			
				sb.append(", ");
				
				if (sacadorAvalista.getCPRF().isFisica()) {
					sb.append("CPF: ");
					
				} else if (sacadorAvalista.getCPRF().isJuridica()) {
					sb.append("CNPJ: ");
				}

				sb.append(sacadorAvalista.getCPRF().getCodigoFormatado());
			}
			
			form.setField("txtFcSacadorAvalistaL1", sb.toString());

			// TODO Código em teste
			sb.delete(0, sb.length());
			Endereco endereco = sacadorAvalista.getEnderecos().iterator().next();

			setEndereco(endereco, "txtFcSacadorAvalistaL2", "txtFcSacadorAvalistaL3", sb);
		}
	}
	
	private void setEndereco(Endereco endereco, String campoEndereco1, String campoEndereco2, StringBuilder sb) 
		throws IOException, DocumentException {
		
		if (isNotNull(endereco)) {
			
			if (isNotNull(endereco.getBairro())) {
				sb.append(endereco.getBairro());
			}
			
			if (isNotNull(endereco.getLocalidade())) {
				sb.append(HIFEN_SEPERADOR);
				sb.append(endereco.getLocalidade());
			}
			
			if (isNotNull(endereco.getUF())) {
				sb.append(" / ");
				sb.append(endereco.getUF().getNome());
			}

			form.setField(campoEndereco1, sb.toString());

			sb.delete(0, sb.length());
			
			if (isNotNull(endereco.getLogradouro())) {
				sb.append(endereco.getLogradouro());
			}

			if (isNotNull(endereco.getNumero())) {
				sb.append(", n°: ");
				sb.append(endereco.getNumero());
			}

			if (isNotNull(endereco.getCEP())) {
				sb.append(" ");
				sb.append(HIFEN_SEPERADOR);
				sb.append(" CEP: ");
				sb.append(endereco.getCEP().getCep());
			}

			form.setField(campoEndereco2, sb.toString());
		}
	}

	private void setInstrucaoAoCaixa() throws IOException, DocumentException {

		form.setField("txtFcInstrucaoAoCaixa1", boleto.getInstrucao1());
		form.setField("txtFcInstrucaoAoCaixa2", boleto.getInstrucao2());
		form.setField("txtFcInstrucaoAoCaixa3", boleto.getInstrucao3());
		form.setField("txtFcInstrucaoAoCaixa4", boleto.getInstrucao4());
		form.setField("txtFcInstrucaoAoCaixa5", boleto.getInstrucao5());
		form.setField("txtFcInstrucaoAoCaixa6", boleto.getInstrucao6());
		form.setField("txtFcInstrucaoAoCaixa7", boleto.getInstrucao7());
		form.setField("txtFcInstrucaoAoCaixa8", boleto.getInstrucao8());
	}

	private void setMoraMulta() throws IOException, DocumentException {

		form.setField("txtRsMoraMulta", StringUtils.EMPTY);
		form.setField("txtFcMoraMulta", StringUtils.EMPTY);
	}

	private void setInstrucaoAoSacado() throws IOException, DocumentException {

		form.setField("txtRsInstrucaoAoSacado", boleto.getInstrucaoAoSacado());
	}

	private void setOutroAcrescimo() throws IOException, DocumentException {

		form.setField("txtRsOutroAcrescimo", StringUtils.EMPTY);
		form.setField("txtFcOutroAcrescimo", StringUtils.EMPTY);
	}

	private void setOutraDeducao() throws IOException, DocumentException {

		form.setField("txtRsOutraDeducao", StringUtils.EMPTY);
		form.setField("txtFcOutraDeducao", StringUtils.EMPTY);

	}

	private void setDescontoAbatimento() throws IOException, DocumentException {

		if(isNotNull(boleto.getTitulo().getDesconto())){
			
			form.setField("txtRsDescontoAbatimento", MonetaryUtil.FORMAT_REAL.format(boleto.getTitulo().getDesconto()));
			form.setField("txtFcDescontoAbatimento", MonetaryUtil.FORMAT_REAL.format(boleto.getTitulo().getDesconto()));
		}
		
	}

	private void setValorDocumento() throws IOException, DocumentException {

		form.setField("txtRsValorDocumento", MonetaryUtil.FORMAT_REAL.format(boleto.getTitulo().getValor()));
		form.setField("txtFcValorDocumento", MonetaryUtil.FORMAT_REAL.format(boleto.getTitulo().getValor()));
	}

	private void setDataVencimeto() throws IOException, DocumentException {

		// Obtendo uma string com a data de vencimento formatada 
		// no padrão "dd/mm/yyyy".
		// Ex: 03/07/2008.
		String dataFormatada = DateUtil.FORMAT_DD_MM_YYYY.format(boleto.getTitulo().getDataDoVencimento());
		
		// Realizando a impressão da data de vencimeto no boleto.
		form.setField("txtRsDataVencimento", dataFormatada);
		form.setField("txtFcDataVencimento", dataFormatada);
		
	}

	private void setAbstractCPRFCedente() throws IOException, DocumentException {

		form.setField("txtRsCpfCnpj", boleto.getTitulo().getCedente().getCPRF().getCodigoFormatado());
	}

	private void setNumeroDocumento() throws IOException, DocumentException {

		form.setField("txtRsNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());
		form.setField("txtFcNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());
	}

	
	private void setCedente() throws IOException, DocumentException {
		
		form.setField("txtRsCedente", boleto.getTitulo().getCedente().getNome());
		form.setField("txtFcCedente", boleto.getTitulo().getCedente().getNome());
	}
	
	
	private void setCarteira() throws IOException, DocumentException {

		Carteira carteira = boleto.getTitulo().getContaBancaria().getCarteira();
		
		if (isNotNull(carteira)) {
		  form.setField("txtFcCarteira", carteira.getCodigo().toString());
		}
	}	

	private void setQuantidade() throws IOException, DocumentException {

		form.setField("txtRsQuantidade", StringUtils.EMPTY);
		form.setField("txtFcQuantidade", StringUtils.EMPTY);
	}

	private void setEspecie() throws IOException, DocumentException {

		form.setField("txtRsEspecie", boleto.getTitulo().getEnumMoeda().name());
		form.setField("txtFcEspecie", boleto.getTitulo().getEnumMoeda().name());
	}

	private void setLinhaDigitavel() throws DocumentException, IOException {
		
		form.setField("txtRsLinhaDigitavel", boleto.getLinhaDigitavel().write());
		form.setField("txtFcLinhaDigitavel", boleto.getLinhaDigitavel().write());
	}

	
	private void setLogoBanco() throws MalformedURLException, IOException, DocumentException {
		
		// Através da conta bancária será descoberto a imagem que representa o
		// banco, com base
		// no código do banco.
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		Image imgLogoBanco = null;

		if (isNotNull(conta.getBanco().getImgLogo())) {

			imgLogoBanco = Image.getInstance(conta.getBanco().getImgLogo(), null);

			setImageLogo(imgLogoBanco);

		} else {

			if (EnumBancos.isSuportado(conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				URL url = this.getClass().getResource("/resource/img/"
											+ conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()
											+ ".png");

				if (isNotNull(url)) {
					imgLogoBanco = Image.getInstance(url);
				}

				if (isNotNull(imgLogoBanco)) {

					// Esta imagem gerada aqui é do tipo java.awt.Image
					conta.getBanco().setImgLogo(ImageIO.read(url));
				}

				// Se o banco em questão é suportado nativamente pelo
				// componente,
				// então um alerta será exibido.
				if (log.isDebugEnabled()) {
					log.debug("Banco sem imagem da logo informada. "
							+ "Com base no código do banco, uma imagem foi "
							+ "encontrada no resource e está sendo utilizada.");
				}

				setImageLogo(imgLogoBanco);

			} else {

				// Sem imagem, um alerta é exibido.
				log.warn("Banco sem imagem definida. O nome da instituição será usado como logo.");

				form.setField("txtRsLogoBanco", conta.getBanco().getNome());
				form.setField("txtFcLogoBanco", conta.getBanco().getNome());

			}
		}
	}

	/**
	 * <p>
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do boleto atribuídos no map e templante.
	 * </p>
	 * 
	 * @throws DocumentException
	 * @throws IOException 
	 * 
	 * @since 0.2
	 */
	private void setImagensNosCampos() throws DocumentException, IOException {

		if (isNotNull(boleto.getImagensExtras())) {
			
			for (String campo : boleto.getImagensExtras().keySet()) {
				setImagemNoCampo(campo, Image.getInstance(boleto.getImagensExtras().get(campo),null));
			}
		}
	}

	
	/**
	 * <p>
	 * Coloca uma imagem no pdf de acordo com o nome do field no templante.
	 * </p>
	 * 
	 * @param nomeDoCampo
	 * @param imagem
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	private void setImagemNoCampo(String nomeDoCampo, Image imagem) throws DocumentException {
	
		float posCampoImgLogo[];
		
		if (StringUtils.isNotBlank(nomeDoCampo)) {
			
			posCampoImgLogo = form.getFieldPositions(nomeDoCampo);
			
			if (isNotNull(posCampoImgLogo)) {
				PDFUtil.changeField2Image(stamper, posCampoImgLogo, imagem);
			}
		}
	}
	
	
	/**
	 * <p>
	 * Coloca a logo do passada na ficha de compensação do boleto e no recibo do
	 * sacado.
	 * </p>
	 * 
	 * @param imgLogoBanco
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	private void setImageLogo(Image imgLogoBanco) throws DocumentException {

		// RECIBO DO SACADO
		setImagemNoCampo("txtRsLogoBanco",imgLogoBanco);

		// FICHA DE COMPENSAÇÃO
		setImagemNoCampo("txtFcLogoBanco",imgLogoBanco);	
	}

	
	private void setCodigoBanco() throws IOException, DocumentException {

		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		
		String codigoCompensacao = conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado();
		String digitoCompensacao = conta.getBanco().getCodigoDeCompensacaoBACEN().getDigito().toString();
		
		form.setField("txtRsCodBanco", codigoCompensacao + HIFEN_SEPERADOR + digitoCompensacao);
		form.setField("txtFcCodBanco", codigoCompensacao + HIFEN_SEPERADOR + digitoCompensacao);
	}

	private void setAgenciaCondigoCedente() throws IOException, DocumentException {

		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();

		if (isNotNull(conta.getAgencia().getCodigo()))
			sb.append(conta.getAgencia().getCodigo());

		if (isNotNull(conta.getAgencia().getDigitoVerificador())
				&& StringUtils.isNotBlank(conta.getAgencia().getDigitoVerificador().toString())) {

			sb.append(HIFEN_SEPERADOR);
			sb.append(conta.getAgencia().getDigitoVerificador());
		}

		if (isNotNull(conta.getNumeroDaConta().getCodigoDaConta())) {

			sb.append(" / ");

			sb.append(conta.getNumeroDaConta().getCodigoDaConta());

			if (isNotNull(conta.getNumeroDaConta().getDigitoDaConta())) {

				sb.append(HIFEN_SEPERADOR);
				sb.append(conta.getNumeroDaConta().getDigitoDaConta());
			}
		}

		form.setField("txtRsAgenciaCodigoCedente", sb.toString());
		form.setField("txtFcAgenciaCodigoCedente", sb.toString());
	}

	private void setNossoNumero() throws IOException, DocumentException {

		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);

		if (isNotNull(boleto.getTitulo().getNossoNumero())) {
			sb.append(boleto.getTitulo().getNossoNumero());
		}

		if (isNotNull(boleto.getTitulo().getDigitoDoNossoNumero())) {
			sb.append(HIFEN_SEPERADOR + boleto.getTitulo().getDigitoDoNossoNumero());
		}

		form.setField("txtRsNossoNumero", sb.toString());
		form.setField("txtFcNossoNumero", sb.toString());
	}

	/**
	 * Exibe os valores de instância.
	 * 
	 * @see br.com.nordestefomento.jrimum.utilix.ObjectUtil#toString()
	 */
	@Override
	public String toString() {

		ToStringBuilder tsb = new ToStringBuilder(this);

		tsb.append(boleto);

		return tsb.toString();
	}
}
