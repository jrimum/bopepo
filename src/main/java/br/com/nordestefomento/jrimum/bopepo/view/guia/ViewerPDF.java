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

package br.com.nordestefomento.jrimum.bopepo.view.guia;

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
import br.com.nordestefomento.jrimum.bopepo.guia.BancoSuportado;
import br.com.nordestefomento.jrimum.bopepo.guia.Guia;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Convenio;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.OrgaoRecebedor;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoValorReferencia;
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
 * @author misael
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
class ViewerPDF {

	// TODO Teste no teste unitário.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ViewerPDF.class);

	private static URL TEMPLATE_PADRAO = ViewerPDF.class.getResource("/pdf/GuiaTemplate.pdf");

	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;

	private ByteArrayOutputStream outputStream;

	private Guia guia;

	private File template;

	/**
	 *<p>
	 * Para uso interno do componente
	 * </p>
	 */
	ViewerPDF() {
	}

	ViewerPDF(Guia guia) {
		this.guia = guia;
	}

	ViewerPDF(Guia guia, File template) {
		this.guia = guia;
		setTemplate(template);
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param pathName
	 *            arquivo de destino
	 * @param guias
	 *            a serem agrupados
	 * @param guiaViewer
	 *            visualizador
	 *            
	 * @return File contendo guias geradas.
	 * 
	 * @throws JRimumException
	 *             Quando ocorrer um problema na geração do PDF que está fora do
	 *             controle da biblioteca.
	 * 
	 * @since 0.3
	 */
	protected static File groupInOnePDF(String pathName, List<Guia> guias,
			GuiaViewer guiaViewer) {

		File arq = null;

		List<byte[]> guiasEmBytes = new ArrayList<byte[]>(guias.size());

		for (Guia guia : guias) {
			guiasEmBytes.add(guiaViewer.setGuia(guia).getPdfAsByteArray());
		}

		try {

			arq = FileUtil.bytes2File(pathName, PDFUtil
					.mergeFiles(guiasEmBytes));

		} catch (FileNotFoundException e) {

			log.error("Erro durante geração do PDF." + e.getLocalizedMessage(),
					e);
			throw new JRimumException(
					"Erro durante geração do PDF. Causado por "
							+ e.getLocalizedMessage(), e);

		} catch (IOException e) {

			log.error("Erro durante geração do PDF." + e.getLocalizedMessage(),
					e);
			throw new JRimumException(
					"Erro durante geração do PDF. Causado por "
							+ e.getLocalizedMessage(), e);
		}

		return arq;
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param path
	 * @param extensao
	 *            TODO
	 * @param guias
	 * @return List<File> com os boletos gerados.
	 * 
	 * @since 0.2
	 */
	protected static List<File> onePerPDF(String path, String extensao,
			List<Guia> guias) {

		List<File> arquivos = new ArrayList<File>(guias.size());
		int cont = 1;

		for (Guia guia : guias) {
			arquivos.add(new GuiaViewer(guia).getPdfAsFile(path + "Guia"
					+ cont++ + extensao));
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

			log.error("Erro ao tentar acessar arquivo inexistente. "
					+ e.getLocalizedMessage(), e);
			throw new JRimumException(
					"Erro ao tentar acessar arquivo inexistente: [" + pathName
							+ "]. " + "Causado por " + e.getLocalizedMessage(),
					e);

		} catch (IOException e) {

			log.error("Erro durante a criação do arquivo. "
					+ e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do arquivo: ["
					+ pathName + "]. " + "Causado por "
					+ e.getLocalizedMessage(), e);

		} catch (DocumentException e) {

			log.error("Erro durante a criação do arquivo. "
					+ e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do arquivo: ["
					+ pathName + "]. " + "Causado por "
					+ e.getLocalizedMessage(), e);
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

			log.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);

		} catch (DocumentException e) {

			log.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);
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

			log.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);

		} catch (DocumentException e) {

			log.error("Erro durante a criação do stream. "
					+ e.getLocalizedMessage(), e);
			throw new JRimumException("Erro durante a criação do stream. "
					+ "Causado por " + e.getLocalizedMessage(), e);
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
	protected Guia getGuia() {
		return this.guia;
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
		URL templateFromResource = TEMPLATE_PADRAO;
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
									 * Sets the document's compression to the
									 * new 1.5 mode with object streams and xref
									 * streams.
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
	private void preencher() throws MalformedURLException, IOException,
			DocumentException {

		setLogoBanco();
		setLogoOrgaoRecebedor();
		setContribuinteNome();
		setContribuinteCPF();
		setDescricao();
		setTitulo();
		setNossoNumero();
		setValorDocumento();
		setDataDocumento();
		setDataVencimeto();
		setInstrucaoAoCaixa();
		setLinhaDigitavel();
		setCodigoBarra();

		setCamposExtra();
		setImagensNosCampos();
	}

	private void setCamposExtra() throws IOException, DocumentException {

		if (isNotNull(guia.getTextosExtras())) {

			for (String campo : guia.getTextosExtras().keySet()) {
				form.setField(campo, guia.getTextosExtras().get(campo));
			}
		}
	}

	private void setCodigoBarra() throws DocumentException {
		// Montando o código de barras.
		BarcodeInter25 barCode = new BarcodeInter25();
		barCode.setCode(guia.getCodigoDeBarras().write());

		barCode.setExtended(true);
		barCode.setBarHeight(40);
		barCode.setFont(null);
		barCode.setN(3);

		PdfContentByte cb = null;

		// Verifcando se existe o field(campo) da imagem no template do boleto.
		float posCampoImgLogo[] = form.getFieldPositions("txtCodigoBarra");

		if (isNotNull(posCampoImgLogo)) {

			RectanglePDF field = new RectanglePDF(posCampoImgLogo);

			cb = stamper.getOverContent(field.getPage());
			Image imgBarCode = barCode.createImageWithBarcode(cb, null, null);

			PDFUtil.changeField2Image(stamper, field, imgBarCode);
		}
	}

	private void setDataDocumento() throws IOException, DocumentException {
		form.setField("txtDataDocumento", DateUtil.FORMAT_DD_MM_YYYY
				.format(guia.getArrecadacao().getDataDoDocumento()));
	}

	private void setInstrucaoAoCaixa() throws IOException, DocumentException {
		form.setField("txtInstrucaoAoCaixa1", guia.getInstrucao1());
		form.setField("txtInstrucaoAoCaixa2", guia.getInstrucao2());
		form.setField("txtInstrucaoAoCaixa3", guia.getInstrucao3());
	}

	private void setValorDocumento() throws IOException, DocumentException {
		String valorStr = null;
		
		if (  (guia.getArrecadacao().getTipoValorReferencia() == TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10)
				|| (guia.getArrecadacao().getTipoValorReferencia() == TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_11)  ) {
			valorStr = MonetaryUtil.FORMAT_REAL_COM_PREFIXO.format(guia.getArrecadacao().getValorDocumento());
		}
		else {
			valorStr = MonetaryUtil.FORMAT_REAL.format(guia.getArrecadacao().getValorDocumento());
		}
				
		form.setField("txtValorDocumento1", valorStr);
		form.setField("txtValorDocumento2",valorStr);
		form.setField("txtValorDocumento3",valorStr);
	}

	private void setDataVencimeto() throws IOException, DocumentException {
		// Obtendo uma string com a data de vencimento formatada
		// no padrão "dd/mm/yyyy".
		// Ex: 03/07/2008.
		String dataFormatada = DateUtil.FORMAT_DD_MM_YYYY.format(guia
				.getArrecadacao().getDataDoVencimento());
		
		form.setField("txtDataVencimento", dataFormatada);
		form.setField("txtDataVencimento1", dataFormatada);
		form.setField("txtDataVencimento2", dataFormatada);
		form.setField("txtDataVencimento3", dataFormatada);

	}

	private void setContribuinteNome() throws IOException, DocumentException {
		form.setField("txtContribuinteNome", guia.getArrecadacao()
				.getContribuinte().getNome());
	}

	private void setContribuinteCPF() throws IOException, DocumentException {
		form.setField("txtContribuinteCPF", guia.getArrecadacao()
				.getContribuinte().getCPF().getCodigoFormatado());
	}

	private void setDescricao() throws IOException, DocumentException {
		form.setField("txtDescricao", guia.getArrecadacao().getDescricao());
	}

	private void setTitulo() throws IOException, DocumentException {
		form.setField("txtTitulo", guia.getArrecadacao().getTitulo());
	}

	private void setLinhaDigitavel() throws DocumentException, IOException {
		form.setField("txtLinhaDigitavel", guia.getLinhaDigitavel().write());
	}

	private void setLogoBanco() throws MalformedURLException, IOException,
			DocumentException {

		// Através da conta bancária será descoberto a imagem que representa o
		// banco, com base
		// no código do banco.
		Convenio convenio = guia.getArrecadacao().getConvenio();
		Image imgLogoBanco = null;

		if (isNotNull(convenio.getBanco().getImgLogo())) {
			imgLogoBanco = Image.getInstance(convenio.getBanco().getImgLogo(),
					null);
			setImageLogo(imgLogoBanco);

		} else {
			if (BancoSuportado.isSuportado(convenio.getBanco()
					.getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				URL url = this.getClass().getResource(
						"/img/"
								+ convenio.getBanco()
										.getCodigoDeCompensacaoBACEN()
										.getCodigoFormatado() + ".png");

				if (isNotNull(url)) {
					imgLogoBanco = Image.getInstance(url);
				}

				if (isNotNull(imgLogoBanco)) {

					// Esta imagem gerada aqui é do tipo java.awt.Image
					convenio.getBanco().setImgLogo(ImageIO.read(url));
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

				form.setField("txtLogoBanco", convenio.getBanco().getNome());
			}
		}
	}

	
	private void setLogoOrgaoRecebedor() throws MalformedURLException, IOException,
			DocumentException {

		OrgaoRecebedor orgaoRecebedor = guia.getArrecadacao().getOrgaoRecebedor();
		Image imgLogoBanco = Image.getInstance(orgaoRecebedor.getImgLogo(),	null);

		if (isNotNull(imgLogoBanco)) {
			setImagemNoCampo("txtLogoOrgaoRecebedor1", imgLogoBanco);
			setImagemNoCampo("txtLogoOrgaoRecebedor2", imgLogoBanco);
		}
		
	}
	
	
	/**
	 * <p>
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do
	 * boleto atribuídos no map e templante.
	 * </p>
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 * 
	 * @since 0.2
	 */
	private void setImagensNosCampos() throws DocumentException, IOException {
		if (isNotNull(guia.getImagensExtras())) {

			for (String campo : guia.getImagensExtras().keySet()) {
				setImagemNoCampo(campo, Image.getInstance(guia
						.getImagensExtras().get(campo), null));
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
	private void setImagemNoCampo(String nomeDoCampo, Image imagem)
			throws DocumentException {

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
		setImagemNoCampo("txtLogoBanco", imgLogoBanco);
	}

	private void setNossoNumero() throws IOException, DocumentException {
		form.setField("txtNossoNumero", guia.getArrecadacao().getNossoNumero());
	}

	/**
	 * Exibe os valores de instância.
	 * 
	 * @see br.com.nordestefomento.jrimum.utilix.ObjectUtil#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append(guia);
		return tsb.toString();
	}
}
