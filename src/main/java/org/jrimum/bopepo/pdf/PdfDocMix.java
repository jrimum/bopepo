/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 14/04/2011 - 14:49:07
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 14/04/2011 - 14:49:07
 * 
 */

package org.jrimum.bopepo.pdf;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Collections.hasElement;
import static org.jrimum.utilix.Objects.isNotNull;
import static org.jrimum.utilix.Objects.isNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jrimum.utilix.Collections;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Strings;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Classe geradora de documentos PDF utilizando templates com fields.
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 *
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class PdfDocMix {

	private static final Logger LOG = Logger.getLogger(PdfDocMix.class);

	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	
	private ByteArrayOutputStream outputStream;

	private byte[] template;
	
	/**
	 * Map dos campos de texto do documento com nome e valor.
	 */
	private Map<String, String> txtMap; 
	
	/**
	 * Map dos campos de imagem do documento com nome e valor.
	 */
	private Map<String, java.awt.Image> imgMap; 

	/**
	 * Modo full compression do PDF, default = true. 
	 * 
	 * @since 0.2
	 */
	private boolean fullCompression = true;

	/**
	 * Classe não instanciável
	 * 
	 * @throws IllegalStateException
	 *             Caso haja alguma tentativa de utilização deste construtor.
	 *             
	 * @since 0.2
	 */
	@SuppressWarnings("unused")
	private PdfDocMix() {

		Objects.checkState(false, "Instanciação não permitida!");
	}
	
	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(byte[] template) {

		checkTemplateFile(template);
		setTemplate(template);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateUrl
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(URL templateUrl) {

		checkTemplateFile(templateUrl);
		setTemplate(templateUrl);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateInput
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(InputStream templateInput) {

		checkTemplateFile(templateInput);
		setTemplate(templateInput);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templatePath
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(String templatePath) {

		checkTemplatePath(templatePath);
		setTemplate(templatePath);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateFile
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(File templateFile) {

		checkTemplateFile(templateFile);
		setTemplate(templateFile);
	}
	
	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public static PdfDocMix createWithTemplate(byte[] template) {

		checkTemplateFile(template);

		return new PdfDocMix(template);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateUrl
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public static PdfDocMix createWithTemplate(URL templateUrl) {

		checkTemplateFile(templateUrl);

		return new PdfDocMix(templateUrl);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateInput
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public static PdfDocMix createWithTemplate(InputStream templateInput) {

		checkTemplateFile(templateInput);

		return new PdfDocMix(templateInput);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templatePath
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public static PdfDocMix createWithTemplate(String templatePath) {

		checkTemplatePath(templatePath);

		return new PdfDocMix(templatePath);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateFile
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public static PdfDocMix createWithTemplate(File templateFile) {

		checkTemplateFile(templateFile);

		return new PdfDocMix(templateFile);
	}
	
	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix changeTemplate(byte[] template) {

		checkTemplateFile(template);

		return setTemplate(template);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateUrl
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix changeTemplate(URL templateUrl) {

		checkTemplateFile(templateUrl);

		return setTemplate(templateUrl);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateInput
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix changeTemplate(InputStream templateInput) {

		checkTemplateFile(templateInput);

		return setTemplate(templateInput);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templatePath
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix changeTemplate(String templatePath) {

		checkTemplatePath(templatePath);

		return setTemplate(templatePath);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o documento.
	 * 
	 * @param templateFile
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix changeTemplate(File templateFile) {

		checkTemplateFile(templateFile);

		return setTemplate(templateFile);
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
	public PdfDocMix withFullCompression(boolean option){
		this.fullCompression = option;
		return this;
	}
	
	public Map<String, String> getTextFields() {
		return this.txtMap;
	}

	public PdfDocMix putAllText(Map<String, String> txtMap) {
		
		Collections.checkNotEmpty(txtMap,"Fields ausentes!");
		
		this.txtMap = txtMap;
		return this;
	}
	
	public PdfDocMix put(String name, String value) {
		
		Strings.checkNotBlank(name, "Nome do campo ausente!");
		
		if(isNull(txtMap)) {
			this.txtMap = new HashMap<String, String>();
		}
		
		this.txtMap.put(name, value);
		
		return this;
	}
	
	public Map<String, java.awt.Image> getImageFields() {
		return this.imgMap;
	}

	public PdfDocMix putAllImage(Map<String,java.awt.Image> imgMap) {
		
		Collections.checkNotEmpty(imgMap,"Fields ausentes!");
		
		this.imgMap = imgMap;
		
		return this;
	}
	
	public PdfDocMix put(String name, java.awt.Image value) {
		
		Strings.checkNotBlank(name, "Nome do campo ausente!");
		
		if(isNull(imgMap)) {
			this.imgMap = new HashMap<String, java.awt.Image>();
		}
		
		this.imgMap.put(name, value);
		
		return this;
	}
	
	/**
	 * Retorna o documento em forma de arquivo PDF.
	 * 
	 * @param destPath
	 *            Caminho completo do arquivo o qual o documento será gerado
	 * @return Documento em forma de arquivo PDF
	 * 
	 * @since 0.2
	 * 
	 */
	public File toFile(String destPath) {
		
		checkDestPath(destPath);
		
		return toFile(new File(destPath));
	}
	
	/**
	 * Retorna o documento em forma de arquivo PDF.
	 * 
	 * @param destPath
	 *            Arquivo o qual o boleto será gerado
	 * @return Documento em forma de arquivo PDF
	 * @throws IllegalStateException
	 *             Caso ocorral algum problema imprevisto
	 *             
	 * @since 0.2
	 * 
	 */
	public File toFile(File destFile) {
		
		checkDestFile(destFile);
		
		try {

			process();
			
			return Files.bytesToFile(destFile, outputStream.toByteArray());
			
		} catch (Exception e) {
			
			LOG.error("Erro durante a criação do arquivo! " + e.getLocalizedMessage(), e);
			
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
	public ByteArrayOutputStream toStream() {
		
		try {

			process();
			
			return Files.bytesToStream(outputStream.toByteArray());
			
		} catch (Exception e) {
			
			LOG.error("Erro durante a criação do stream! " + e.getLocalizedMessage(), e);
			
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
	public byte[] toBytes() {
		
		try {

			process();
			
			return outputStream.toByteArray();
			
		} catch (Exception e) {
			
			LOG.error("Erro durante a criação do array de bytes! " + e.getLocalizedMessage(), e);
			
			throw new IllegalStateException("Erro durante a criação do array de bytes! " +"Causado por " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Retorna o uma cópia do template atual do viewer em array de bytes.
	 * 
	 * @return Template em bytes
	 * 
	 * @since 0.2
	 * 
	 */
	public byte[] getTemplate() {
		
		return template.clone();
	}
	
	/**
	 * Define o template que será utilizado para construir o documento.
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(byte[] template) {
		this.template = template;
		return this;
	}
	
	/**
	 * Define o template que será utilizado para construir o documento.
	 * 
	 * @param templateUrl
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(URL templateUrl) {
		try {
			setTemplate(templateUrl.openStream());
			return this;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Define o template que será utilizado para construir o documento.
	 * 
	 * @param templateInput
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(InputStream templateInput) {
		try {
			setTemplate(Files.toByteArray(templateInput));
			return this;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Define o template que será utilizado para construir o documento.
	 * 
	 * @param templatePath
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(String templatePath) {
		setTemplate(new File(templatePath));
		return this;
	}

	/**
	 * Define o template que será utilizado para construir o documento.
	 * 
	 * @param templateFile
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(File templateFile) {
		try {
			setTemplate(Files.fileToBytes(templateFile));
			return this;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * Indica se o viewer foi habilitado a comprimir o pdf do documento gerado.
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
	 * Executa os seguintes métodos na sequência:
	 * <ol>
	 * <li>{@linkplain #init()}</li>
	 * <li>{@linkplain #fillFields()}</li>
	 * <li>{@linkplain #end()}</li>
	 * </ol>
	 * 
	 * @since 0.2
	 */
	private void process(){
		
		init();
		fillFields();
		end();
	}

	/**
	 * Inicializa os principais objetos para a escrita dos dados do documento no
	 * template PDF: {@code stamper}, {@code reader} e {@code outputStream}.
	 * 
	 * @since 0.2
	 */
	private void init(){
		
		try{
			
			reader = new PdfReader(getTemplate());
	
			outputStream = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, outputStream);
			form = stamper.getAcroFields();
			
		}catch (Exception e) {
			
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * Preenche todos os campos do formulário PDF com os dados do documento contido
	 * na instância.
	 * 
	 * @since 0.2
	 */
	private void fillFields(){
		
		setTextFields();
		setImageFields();
	}

	private void setTextFields(){

		if (hasElement(txtMap)) {
			for (Entry<String, String> e : txtMap.entrySet()) {
				try {
					form.setField(e.getKey(), e.getValue());
				}catch (Exception ex) {
					throw new IllegalStateException(ex);
				}
			}
		}		
	}

	/**
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do documento atribuídos no map e templante.
	 * 
	 * @since 0.2
	 */
	private void setImageFields(){
		
		if (hasElement(imgMap)) {
			for (Entry<String, java.awt.Image> e : imgMap.entrySet()) {
				try {
					setImage(e.getKey(), Image.getInstance(e.getValue(),null));
				}catch (Exception ex) {
					throw new IllegalStateException(ex);
				}
			}
		}
	}

	/**
	 * Coloca uma imagem no pdf de acordo com o nome do field no templante.
	 * 
	 * @param fieldName
	 * @param image
	 * 
	 * @since 0.2
	 */
	private void setImage(String fieldName, Image image){
	
		float posImgField[];
		
		if (isNotBlank(fieldName)) {
			
			posImgField = form.getFieldPositions(fieldName);
			
			if (isNotNull(posImgField)) {
				try {
					PDFUtil.changeFieldToImage(stamper, posImgField, image);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

	/**
	 * Finaliza a escrita de dados no template através do fechamento do {@code
	 * stamper}, {@code reader} e {@code outputStream}.
	 * 
	 * @since 0.2
	 */
	private void end(){

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
		try{
			// Send immediately
			outputStream.flush();
			// close All in this order
			outputStream.close();
			reader.close();
			stamper.close();
			
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	private static void checkDestPath(String path) {

		checkString(path, "Caminho destinado a geração do(s) arquivo(s) não contém informação!");
	}

	private static void checkTemplatePath(String path) {

		checkString(path, "Caminho do template não contém informação!");
	}
	
	private static void checkTemplateFile(Object template) {

		Objects.checkNotNull(template, "Arquivo de template nulo!");
	}

	private static void checkString(String str, String msg) {

		Objects.checkNotNull(str);
		Strings.checkNotBlank(str, msg);
	}

	private static void checkDestFile(File file) {

		Objects.checkNotNull(file, "Arquivo destinado a geração do(s) documentos(s) nulo!");
	}
	
}
