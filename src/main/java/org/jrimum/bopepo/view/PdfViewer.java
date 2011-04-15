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

package org.jrimum.bopepo.view;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;
import static org.jrimum.utilix.Objects.isNull;
import static org.jrimum.utilix.text.DateFormat.DDMMYYYY_B;
import static org.jrimum.utilix.text.DecimalFormat.MONEY_DD_BR;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.bopepo.pdf.PDFUtil;
import org.jrimum.bopepo.pdf.RectanglePDF;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.utilix.ClassLoaders;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * <p>
 * Classe utilizada para preencher o PDF do boleto com os dados do título e boleto.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class PdfViewer {

	private static Logger log = Logger.getLogger(PdfViewer.class);

	private static URL TEMPLATE_PADRAO_COM_SACADOR_AVALISTA = ClassLoaders.getResource("/pdf/BoletoTemplateComSacadorAvalista.pdf",PdfViewer.class);
	private static URL TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA = ClassLoaders.getResource("/pdf/BoletoTemplateSemSacadorAvalista.pdf",PdfViewer.class);

	private static final String HIFEN_SEPERADOR = "-";
	
	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	
	private ByteArrayOutputStream outputStream;

	private Boleto boleto;

	private byte[] template;

	/**
	 * Modo full compression do PDF, default = true. 
	 * 
	 * @since 0.2
	 */
	private boolean fullCompression = true;

	/**
	 * Para uso interno do componente
	 * 
	 * @since 0.2
	 * 
	 */
	protected PdfViewer() {
	}
	
	/**
	 * Para uso interno do componente
	 * 
	 * @param boleto
	 * 
	 * @since 0.2
	 * 
	 */
	protected PdfViewer(Boleto boleto) {

		this.boleto = boleto;
	}
	
	/**
	 * Para uso interno do componente
	 * 
	 * @param boleto Boleto para visualização
	 * @param template Template a ser utilizado na visualização
	 * 
	 * @since 0.2
	 * 
	 */
	protected PdfViewer(Boleto boleto, byte[] template) {
		
		this.boleto = boleto;
		
		setTemplate(template);
	}
	
	/**
	 * Retorna o boleto em forma de arquivo PDF.
	 * 
	 * @param destPath
	 *            Caminho completo do arquivo o qual o boleto será gerado
	 * @return Boleto em forma de arquivo PDF
	 * 
	 * @since 0.2
	 * 
	 */
	protected File getFile(String destPath) {
		
	
		return getFile(new File(destPath));
	}
	
	/**
	 * Retorna o boleto em forma de arquivo PDF.
	 * 
	 * @param destPath
	 *            Arquivo o qual o boleto será gerado
	 * @return Boleto em forma de arquivo PDF
	 * @throws IllegalStateException
	 *             Caso ocorral algum problema imprevisto
	 *             
	 * @since 0.2
	 * 
	 */
	protected File getFile(File destFile) {
		
		try {

			processarPdf();
			
			return Files.bytesToFile(destFile, outputStream.toByteArray());
			
		} catch (Exception e) {
			
			log.error("Erro durante a criação do arquivo! " + e.getLocalizedMessage(), e);
			
			throw new IllegalStateException("Erro ao tentar criar arquivo! " +"Causado por " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Retorna o arquivo PDF em um stream de array de bytes.
	 * 
	 * @return O PDF em stream
	 * 
	 * @since 0.2
	 * 
	 */
	protected ByteArrayOutputStream getStream() {
		
		try {

			processarPdf();
			
			return Files.bytesToStream(outputStream.toByteArray());
			
		} catch (Exception e) {
			
			log.error("Erro durante a criação do stream! " + e.getLocalizedMessage(), e);
			
			throw new IllegalStateException("Erro durante a criação do stream! " +"Causado por " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Retorna o arquivo PDF em array de bytes.
	 * 
	 * @return O PDF em array de bytes
	 * 
	 * @since 0.2
	 * 
	 */
	protected byte[] getBytes() {
		
		try {

			processarPdf();
			
			return outputStream.toByteArray();
			
		} catch (Exception e) {
			
			log.error("Erro durante a criação do array de bytes! " + e.getLocalizedMessage(), e);
			
			throw new IllegalStateException("Erro durante a criação do array de bytes! " +"Causado por " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Retorna o template atual do viewer em array de bytes.
	 * 
	 * @return Template em bytes
	 * 
	 * @since 0.2
	 * 
	 */
	protected byte[] getTemplate() {
		return template;
	}
	
	/**
	 * Define o template que será utilizado para construir o boleto.
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 * 
	 */
	protected void setTemplate(byte[] template) {
		this.template = template;
	}
	
	/**
	 * Define o template que será utilizado para construir o boleto.
	 * 
	 * @param templateUrl
	 * 
	 * @since 0.2
	 * 
	 */
	protected void setTemplate(URL templateUrl) {
		try {
			setTemplate(templateUrl.openStream());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Define o template que será utilizado para construir o boleto.
	 * 
	 * @param templateInput
	 * 
	 * @since 0.2
	 * 
	 */
	protected void setTemplate(InputStream templateInput) {
		try {
			setTemplate(Files.toByteArray(templateInput));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Define o template que será utilizado para construir o boleto.
	 * 
	 * @param templatePath
	 * 
	 * @since 0.2
	 * 
	 */
	protected void setTemplate(String templatePath) {
		setTemplate(new File(templatePath));
	}

	/**
	 * Define o template que será utilizado para construir o boleto.
	 * 
	 * @param templateFile
	 * 
	 * @since 0.2
	 * 
	 */
	protected void setTemplate(File templateFile) {
		try {
			setTemplate(Files.fileToBytes(templateFile));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * Habilita o modo full compression do PDF veja
	 * {@link com.lowagie.text.pdf.PdfStamper#setFullCompression()}.
	 * 
	 * <p>
	 * Itext doc: <i>Sets the document's compression to the new 1.5 mode with
	 * object streams and xref streams.</i>
	 * </p>
	 * 
	 * @param option
	 *            Escolha de compressão.
	 *            
	 * @since 0.2
	 *        
	 */
	protected void setFullCompression(boolean option){
		this.fullCompression = option;
	}
	
	/**
	 * Indica se o viewer foi habilitado a comprimir o pdf do boleto gerado.
	 * 
	 * @see #setFullCompression();
	 * 
	 * @return indicativo de compressão
	 * 
	 * @since 0.2
	 * 
	 */
	private boolean isFullCompression() {
		return this.fullCompression;
	}

	/**
	 * @return the boleto
	 * 
	 * @since 0.2
	 * 
	 */
	protected Boleto getBoleto() {
		return this.boleto;
	}
	
	/**
	 * Executa os seguintes métodos na sequência:
	 * <ol>
	 * <li>{@linkplain #inicializar()}</li>
	 * <li>{@linkplain #preencher()}</li>
	 * <li>{@linkplain #finalizar()}</li>
	 * </ol>
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 * 
	 */
	private void processarPdf() throws IOException, DocumentException {
		
		inicializar();
		preencher();
		finalizar();
	}

	/**
	 * Retorna o template padrão a ser usado, dependendo se o boleto é com ou
	 * sem sacador avalsita.
	 * 
	 * @return URL do template padrão
	 * 
	 * @since 0.2
	 * 
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
	 * Verifica se o template que será utilizado virá do resource ou é externo,
	 * ou seja, se o usuário definiu ou não um template.
	 * 
	 * @return true caso o template que pode ser definido pelo usuário for null;
	 *         false caso o usuário tenha definido um template.
	 * 
	 * @since 0.2
	 * 
	 */
	private boolean isTemplateFromResource() {
		
		return isNull(getTemplate());
	}

	/**
	 * Inicializa os principais objetos para a escrita dos dados do boleto no
	 * template PDF: {@code stamper}, {@code reader} e {@code outputStream}.
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 * 
	 */
	private void inicializar() throws IOException, DocumentException {

		if (isTemplateFromResource()) {
			
			reader = new PdfReader(getTemplateFromResource());
			
		} else {
			
			reader = new PdfReader(getTemplate());
		}

		outputStream = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, outputStream);
		form = stamper.getAcroFields();
	}

	/**
	 * Finaliza a escrita de dados no template através do fechamento do {@code
	 * stamper}, {@code reader} e {@code outputStream}.
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 * 
	 * @since 0.2
	 * 
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

		if(isFullCompression()){
			
			stamper.setFullCompression();/*
											 * Sets the document's compression to
											 * the new 1.5 mode with object streams
											 * and xref streams.
											 */
		}

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
	 * Preenche todos os campos do formulário PDF com os dados do boleto contido
	 * na instância.
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 * 
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
		setValorCobrado();
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
		float posCampoImg[] = form.getFieldPositions("txtFcCodigoBarra");
		
		if (isNotNull(posCampoImg)) {
			
			RectanglePDF field = new RectanglePDF(posCampoImg);
			
			cb = stamper.getOverContent(field.getPage());
			Image imgBarCode = barCode.createImageWithBarcode(cb, null, null);
			
			PDFUtil.changeFieldToImage(stamper, field, imgBarCode);
		}
	}

	private void setDataProcessamento() throws IOException, DocumentException {
		
		form.setField("txtFcDataProcessamento", DDMMYYYY_B.format(boleto.getDataDeProcessamento()));
	}

	private void setAceite() throws IOException, DocumentException {

		if (isNotNull(boleto.getTitulo().getAceite())) {
			form.setField("txtFcAceite", boleto.getTitulo().getAceite().name());
		}
	}

	private void setEspecieDoc() throws IOException, DocumentException {
		if (isNotNull(boleto.getTitulo().getTipoDeDocumento()) && isNotNull(boleto.getTitulo().getTipoDeDocumento().getSigla())) {
			form.setField("txtFcEspecieDocumento", boleto.getTitulo().getTipoDeDocumento().getSigla());
		}
	}

	private void setDataDocumento() throws IOException, DocumentException {
		form.setField("txtFcDataDocumento", DDMMYYYY_B.format(boleto.getTitulo().getDataDoDocumento()));
	}

	private void setLocalPagamento() throws IOException, DocumentException {
		form.setField("txtFcLocalPagamento", (boleto.getLocalPagamento()));
	}

	private void setSacado() throws IOException, DocumentException {

		StringBuilder sb = new StringBuilder();
		Sacado sacado = boleto.getTitulo().getSacado();

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

		//clear
		sb.delete(0, sb.length());
		
		Endereco endereco = sacado.getNextEndereco();
		
		setEndereco(endereco, "txtFcSacadoL2", "txtFcSacadoL3", sb);
	}

	private void setSacadorAvalista() throws IOException, DocumentException {
		
		if (boleto.getTitulo().hasSacadorAvalista()) {
			
			SacadorAvalista sacadorAvalista = boleto.getTitulo().getSacadorAvalista(); 
			
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

			//clear
			sb.delete(0, sb.length());
			
			Endereco endereco = sacadorAvalista.getNextEndereco();

			setEndereco(endereco, "txtFcSacadorAvalistaL2", "txtFcSacadorAvalistaL3", sb);
		}
	}
	
	private void setEndereco(Endereco endereco, String campoEndereco1, String campoEndereco2, StringBuilder sb) 
		throws IOException, DocumentException {
		
		if (isNotNull(endereco)) {
			
			if (isNotBlank(endereco.getBairro())) {
				sb.append(endereco.getBairro());
			}
			
			if (isNotBlank(endereco.getLocalidade())) {
				sb.append(HIFEN_SEPERADOR);
				sb.append(endereco.getLocalidade());
			}
			
			if (isNotNull(endereco.getUF())) {
				sb.append(" / ");
				sb.append(endereco.getUF().getNome());
			}

			form.setField(campoEndereco1, sb.toString());

			sb.delete(0, sb.length());
			
			if (isNotBlank(endereco.getLogradouro())) {
				sb.append(endereco.getLogradouro());
			}

			if (isNotBlank(endereco.getNumero())) {
				sb.append(", n°: ");
				sb.append(endereco.getNumero());
			}

			if (isNotNull(endereco.getCEP()) && isNotBlank(endereco.getCEP().getCep())) {
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

	private void setInstrucaoAoSacado() throws IOException, DocumentException {

		form.setField("txtRsInstrucaoAoSacado", boleto.getInstrucaoAoSacado());
	}

	private void setMoraMulta() throws IOException, DocumentException {

		if(isNotNull(boleto.getTitulo().getMora())){
		
			form.setField("txtRsMoraMulta", MONEY_DD_BR.format(boleto.getTitulo().getMora()));
			form.setField("txtFcMoraMulta", MONEY_DD_BR.format(boleto.getTitulo().getMora()));
		}
	}
	
	private void setOutroAcrescimo() throws IOException, DocumentException {

		if(isNotNull(boleto.getTitulo().getAcrecimo())){
		
			form.setField("txtRsOutroAcrescimo", MONEY_DD_BR.format(boleto.getTitulo().getAcrecimo()));
			form.setField("txtFcOutroAcrescimo", MONEY_DD_BR.format(boleto.getTitulo().getAcrecimo()));
		}
	}

	private void setOutraDeducao() throws IOException, DocumentException {
		
		if(isNotNull(boleto.getTitulo().getDeducao())){
			
			form.setField("txtRsOutraDeducao", MONEY_DD_BR.format(boleto.getTitulo().getDeducao()));
			form.setField("txtFcOutraDeducao", MONEY_DD_BR.format(boleto.getTitulo().getDeducao()));
		}
	}

	private void setDescontoAbatimento() throws IOException, DocumentException {

		if(isNotNull(boleto.getTitulo().getDesconto())){
			
			form.setField("txtRsDescontoAbatimento", MONEY_DD_BR.format(boleto.getTitulo().getDesconto()));
			form.setField("txtFcDescontoAbatimento", MONEY_DD_BR.format(boleto.getTitulo().getDesconto()));
		}
	}
	private void setValorDocumento() throws IOException, DocumentException {

		if(isNotNull(boleto.getTitulo().getValor())){
			
			form.setField("txtRsValorDocumento", MONEY_DD_BR.format(boleto.getTitulo().getValor()));
			form.setField("txtFcValorDocumento", MONEY_DD_BR.format(boleto.getTitulo().getValor()));
		}
	}

	private void setValorCobrado() throws IOException, DocumentException {

		if(isNotNull(boleto.getTitulo().getValorCobrado())){
			
			form.setField("txtRsValorCobrado", MONEY_DD_BR.format(boleto.getTitulo().getValorCobrado()));
			form.setField("txtFcValorCobrado", MONEY_DD_BR.format(boleto.getTitulo().getValorCobrado()));
		}
	}

	/**
	 * Data no formata "dd/mm/yyyy"
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void setDataVencimeto() throws IOException, DocumentException {

		if(isNotNull(boleto.getTitulo().getDataDoVencimento())){
			
			form.setField("txtRsDataVencimento",  DDMMYYYY_B.format(boleto.getTitulo().getDataDoVencimento()));
			form.setField("txtFcDataVencimento",  DDMMYYYY_B.format(boleto.getTitulo().getDataDoVencimento()));
		}
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
		
		if (isNotNull(carteira) && isNotNull(carteira.getCodigo())) {
			
			form.setField("txtFcCarteira", carteira.getCodigo().toString());
		}
	}	

	private void setQuantidade() throws IOException, DocumentException {

		form.setField("txtRsQuantidade", StringUtils.EMPTY);
		form.setField("txtFcQuantidade", StringUtils.EMPTY);
	}

	private void setEspecie() throws IOException, DocumentException {

		form.setField("txtRsEspecie", boleto.getTitulo().getTipoDeMoeda().name());
		form.setField("txtFcEspecie", boleto.getTitulo().getTipoDeMoeda().name());
	}

	private void setLinhaDigitavel() throws DocumentException, IOException {
		
		form.setField("txtRsLinhaDigitavel", boleto.getLinhaDigitavel().write());
		form.setField("txtFcLinhaDigitavel", boleto.getLinhaDigitavel().write());
	}

	
	private void setLogoBanco() throws MalformedURLException, IOException, DocumentException {
		
		// Através da conta bancária será descoberto a imagem que representa o
		// banco, com base no código do banco.
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		Image imgLogoBanco = null;

		if (isNotNull(conta.getBanco().getImgLogo())) {
			imgLogoBanco = Image.getInstance(conta.getBanco().getImgLogo(), null);

		} else {

			if (BancosSuportados.isSuportado(conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				URL url = this.getClass().getResource("/img/"
											+ conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()
											+ ".png");

				if (isNotNull(url)) {
					imgLogoBanco = Image.getInstance(url);
				}

				if (isNotNull(imgLogoBanco)) {
					// Esta imagem gerada aqui é do tipo java.awt.Image
					conta.getBanco().setImgLogo(ImageIO.read(url));

					// Se o banco em questão é suportado nativamente pelo
					// componente, então um alerta será exibido.
					if (log.isDebugEnabled()) {
						log.debug("Banco sem imagem da logo informada. "
								+ "Com base no código de compensação do banco, uma imagem foi "
								+ "encontrada no resource e está sendo utilizada.");
					}
				}


			} 
			
		}
		
		
		if (isNotNull(imgLogoBanco)) {
			setImageLogo(imgLogoBanco);
		} 
		else {
			// Sem imagem, um alerta é exibido.
			log.warn("Banco sem imagem definida. O nome da instituição será usado como logo.");
			setTextLogo(conta.getBanco().getNome());
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
				PDFUtil.changeFieldToImage(stamper, posCampoImgLogo, imagem);
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
	
	/**
	 * <p>
	 * Coloca a nome do banco na ficha de compensação do boleto e no recibo do
	 * sacado.
	 * </p>
	 * 
	 * @param nomeBanco
	 * @throws DocumentException
	 * @throws IOException 
	 * 
	 * @since 0.2
	 */
	private void setTextLogo(String nomeBanco) throws IOException, DocumentException {

		// RECIBO DO SACADO
		form.setField("txtRsLogoBanco",nomeBanco);

		// FICHA DE COMPENSAÇÃO
		form.setField("txtFcLogoBanco",nomeBanco);	
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

		if (isNotNull(conta.getAgencia())) {
			if (isNotNull(conta.getAgencia().getCodigo()))
				sb.append(conta.getAgencia().getCodigo());
	
			if (isNotNull(conta.getAgencia().getDigitoVerificador())
					&& StringUtils.isNotBlank(conta.getAgencia().getDigitoVerificador().toString())) {
	
				sb.append(HIFEN_SEPERADOR);
				sb.append(conta.getAgencia().getDigitoVerificador());
			}
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
	 * @see org.jrimum.utilix.Objects#toString()
	 */
	@Override
	public String toString() {

		ToStringBuilder tsb = new ToStringBuilder(this);

		tsb.append(boleto);

		return tsb.toString();
	}
}
