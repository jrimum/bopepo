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

import static org.jrimum.utilix.Objects.isNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.bopepo.pdf.PDFUtil;
import org.jrimum.bopepo.pdf.RectanglePDF;
import org.jrimum.bopepo.view.AbstractViewer;

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
class PdfViewer extends AbstractViewer {
	private static Logger log = Logger.getLogger(PdfViewer.class);
	
	private static URL TEMPLATE_PADRAO_COM_SACADOR_AVALISTA = PdfViewer.class.getResource("/pdf/BoletoTemplateComSacadorAvalista.pdf");
	private static URL TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA = PdfViewer.class.getResource("/pdf/BoletoTemplateSemSacadorAvalista.pdf");

	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	
	private ByteArrayOutputStream outputStream;

	/**
	 *<p>
	 * Para uso interno do componente
	 * </p>
	 * 
	 * @since 0.2
	 */
	PdfViewer() {
	}
	
	/**
	 *<p>
	 * Para uso interno do componente
	 * </p>
	 * 
	 * @since 0.2
	 */
	PdfViewer(Boleto boleto) {
		
		this.boleto = boleto;
	}
	
	/**
	 *<p>
	 * Para uso interno do componente
	 * </p>
	 * 
	 * @since 0.2
	 */
	PdfViewer(Boleto boleto, File template) {
		
		this.boleto = boleto;
		
		setTemplate(template);
	}

	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param fileDest
	 *            - Arquivo o qual armazenará os boletos
	 * @param boletoViewer
	 *            - Visualizador contendo o template para geração
	 * 
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	protected static File groupInOnePDF(List<Boleto> boletos, File fileDest, BoletoViewer boletoViewer) {

		File arq = null;

		List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());

		for (Boleto bop : boletos) {
			boletosEmBytes.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
		}

		try {
			
			arq = Files.bytesToFile(fileDest, PDFUtil.mergeFiles(boletosEmBytes));
			
		} catch (Exception e) {
			
			log.error("Erro durante geração do PDF." + e.getLocalizedMessage(), e);
			
			throw new IllegalStateException("Erro durante geração do PDF! Causado por " + e.getLocalizedMessage(), e);
		}

		return arq;
	}


	/**
	 * <p>
	 * Gera o arquivo PDF para cada boleto contido na lista. O nome do arquivo
	 * segue a forma:<br />
	 * <br />
	 * <tt>diretorio + (/ ou \\) prefixo + (indice do arquivo na lista + 1) + sufixo + ".pdf"</tt>
	 * </p>
	 * 
	 * <p>
	 * Exemplo, uma lista com 3 boletos: {@code onePerPDF(boletos, file,
	 * "BoletoPrefixo", "exSufixo");} <br />
	 * <br />
	 * Arquivos gerados:
	 * <ul>
	 * <li><strong>BoletoPrefixo1exSufixo.pdf</strong></li>
	 * <li><strong>BoletoPrefixo2exSufixo.pdf</strong></li>
	 * <li><strong>BoletoPrefixo3exSufixo.pdf</strong></li>
	 * </ul>
	 * </p>
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param fileDest
	 *            - Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            - Prefixo do nome do arquivo
	 * @param sufixo
	 *            - Sufixo do nome do arquivo
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	protected static List<File> onePerPDF(List<Boleto> boletos, File destDir,String prefixo, String sufixo) {

		final List<File> arquivos = new ArrayList<File>(boletos.size());
		final BoletoViewer bv = new BoletoViewer();
		int cont = 1;
		
		for (Boleto bop : boletos) {
			arquivos.add(bv.setBoleto(bop).getPdfAsFile(destDir.getAbsolutePath() + File.separator + prefixo + cont++ + sufixo + ".pdf"));
		}

		return arquivos;
	}

	protected File getFile(String destPath) {
		
	
		return getFile(new File(destPath));
	}
	
	/**
	 * 
	 * @param destPath
	 * @return
	 * @throws IllegalArgumentException
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
	 *
	 * 
	 * @return
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
	 *
	 * 
	 * @return
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
	private void processarPdf() throws Exception {
		
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
	
	@Override
	protected void setText(String field, String content) throws IOException, DocumentException {
		form.setField(field, content);
	}
	
	@Override
	protected void setImage(String field, java.awt.Image image) throws IOException, DocumentException {
		float posCampoImgLogo[] = form.getFieldPositions(field);

		if (isNotNull(posCampoImgLogo)) {
			PDFUtil.changeFieldToImage(stamper, posCampoImgLogo, Image.getInstance(image, null));
		}
	}
	
	@Override
	protected void setBarcode(String field, String content) throws DocumentException {
		// Montando o código de barras.
		BarcodeInter25 barCode = new BarcodeInter25();
		barCode.setCode(content);

		barCode.setExtended(true);
		barCode.setBarHeight(40);
		barCode.setFont(null);
		barCode.setN(3);

		// FICHA DE COMPENSAÇÃO
		PdfContentByte cb = null;
		
		// Verifcando se existe o field(campo) da imagem no template do boleto.
		float posCampoImgLogo[] = form.getFieldPositions(field);

		if (isNotNull(posCampoImgLogo)) {

			RectanglePDF rect = new RectanglePDF(posCampoImgLogo);

			cb = stamper.getOverContent(rect.getPage());
			Image imgBarCode = barCode.createImageWithBarcode(cb, null, null);

			PDFUtil.changeFieldToImage(stamper, rect, imgBarCode);
		}
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
