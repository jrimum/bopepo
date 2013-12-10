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

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Collections.hasElement;
import static org.jrimum.utilix.Objects.checkNotNull;
import static org.jrimum.utilix.Objects.isNotNull;
import static org.jrimum.utilix.Objects.isNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;
import org.jrimum.utilix.Collections;
import org.jrimum.utilix.Exceptions;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Strings;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfBoolean;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Classe geradora de documentos PDF utilizando templates com fields.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
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
	
	private Map<java.awt.Image, Image> imagesInUseMap = new WeakHashMap<java.awt.Image, Image>();

	/**
	 * Template em byte array.
	 */
	private byte[] template;
	
	/**
	 * Informações sobre o documento.
	 */
	private PdfDocInfo docInfo = PdfDocInfo.create();

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
	 * Remove todos os campos do PDF, default = true.
	 * 
	 * @since 0.2
	 */
	private boolean removeFields = true;

	/**
	 * Indicação de que o título do documento deve ser mostrado barra superior.
	 * 
	 * @since 0.2
	 */
	private Boolean displayDocTitle;

	/**
	 * Cria uma instância sem o template que será utilizado para construir o
	 * documento.
	 * 
	 * @since 0.2
	 */
	private PdfDocMix() {}

	/**
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(byte[] template) {

		checkTemplateFile(template);
		setTemplate(template);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
	 * 
	 * @param templateUrl
	 * 
	 * @since 0.2
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(URL templateUrl) {

		checkTemplateFile(templateUrl);
		setTemplate(templateUrl);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
	 * 
	 * @param templateInput
	 * 
	 * @since 0.2
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(InputStream templateInput) {

		checkTemplateFile(templateInput);
		setTemplate(templateInput);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
	 * 
	 * @param templatePath
	 * 
	 * @since 0.2
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(String templatePath) {

		checkTemplatePath(templatePath);
		setTemplate(templatePath);
	}

	/**
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
	 * 
	 * @param templateFile
	 * 
	 * @since 0.2
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code template} seja nulo
	 */
	public PdfDocMix(File templateFile) {

		checkTemplateFile(templateFile);
		setTemplate(templateFile);
	}

	/**
	 * Cria uma instância sem o template que será utilizado para construir o
	 * documento.
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 */
	public static PdfDocMix create() {

		return new PdfDocMix();
	}
	
	/**
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
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
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
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
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
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
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
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
	 * Cria uma instância com o template que será utilizado para construir o
	 * documento.
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
	 * Define o template que será utilizado para construir o
	 * documento.
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
	public PdfDocMix withTemplate(byte[] template) {

		checkTemplateFile(template);

		return setTemplate(template);
	}

	/**
	 * Define o template que será utilizado para construir o
	 * documento.
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
	public PdfDocMix withTemplate(URL templateUrl) {

		checkTemplateFile(templateUrl);

		return setTemplate(templateUrl);
	}

	/**
	 * Define o template que será utilizado para construir o
	 * documento.
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
	public PdfDocMix withTemplate(InputStream templateInput) {

		checkTemplateFile(templateInput);

		return setTemplate(templateInput);
	}

	/**
	 * Define o template que será utilizado para construir o
	 * documento.
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
	public PdfDocMix withTemplate(String templatePath) {

		checkTemplatePath(templatePath);

		return setTemplate(templatePath);
	}

	/**
	 * Define o template que será utilizado para construir o
	 * documento.
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
	public PdfDocMix withTemplate(File templateFile) {

		checkTemplateFile(templateFile);

		return setTemplate(templateFile);
	}

	/**
	 * Retorna um {@code Map} com os campos e seus respectivos textos adicionados nessa
	 * instância.
	 * 
	 * @return Map de campo,texto
	 * 
	 * @since 0.2
	 */
	public Map<String, String> getTextFields() {
		return this.txtMap;
	}

	/**
	 * Coloca todos chave-valor na instância, caso uma chave existe o valor será
	 * substituído. Caso a instância não contenha valores ainda, atribui o
	 * {@code Map} informado para uso no preenchimento de campos de Texto na
	 * instância.
	 * 
	 * @param txtMap
	 *            Map com os campos(key) e textos(value)
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 */
	public PdfDocMix putAllTexts(Map<String, String> txtMap) {

		Collections.checkNotEmpty(txtMap, "Campos ausentes!");

		if (isNull(this.txtMap)) {			
			this.txtMap = txtMap;
		}else{
			this.txtMap.putAll(txtMap);
		}
		
		return this;
	}

	/**
	 * Coloca um par {@code key,value} para uso no preenchimento de campos de
	 * Texto na instância.
	 * 
	 * @param name
	 *            Nome do campo
	 * @param value
	 *            Valor em texto do campo
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 */
	public PdfDocMix put(String name, String value) {

		Strings.checkNotBlank(name, "Nome do campo ausente!");

		if (isNull(txtMap)) {
			this.txtMap = new WeakHashMap<String, String>();
		}

		this.txtMap.put(name, value);

		return this;
	}

	/**
	 * Retorna um {@code Map} com os campos e suas respectivas imagens
	 * adicionadas nessa instância.
	 * 
	 * @return Map de campo,imagem
	 * 
	 * @since 0.2
	 */
	public Map<String, java.awt.Image> getImageFields() {
		return this.imgMap;
	}

	/**
	 * Coloca todos chave-valor na instância, caso uma chave existe o valor
	 * será substituído. Caso a instância não contenha valores ainda, atribui o
	 * {@code Map} informado para uso no preenchimento de campos de Imagem na
	 * instância.
	 * 
	 * @param imgMap
	 *            Map com os campos(key) e imagens(value)
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 */
	public PdfDocMix putAllImages(Map<String, java.awt.Image> imgMap) {

		Collections.checkNotEmpty(imgMap, "Campos ausentes!");

		if (isNull(this.imgMap)) {			
			this.imgMap = imgMap;
		}else{
			this.imgMap.putAll(imgMap);
		}

		return this;
	}

	/**
	 * Coloca um par {@code key,value} para uso no preenchimento de campos de
	 * Imagem na instância.
	 * 
	 * @param name
	 *            Nome do campo
	 * @param value
	 *            Valor em {@link java.awt.Image} do campo
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 */
	public PdfDocMix put(String name, java.awt.Image value) {

		Strings.checkNotBlank(name, "Nome do campo ausente!");

		if (isNull(imgMap)) {
			this.imgMap = new WeakHashMap<String, java.awt.Image>();
		}

		this.imgMap.put(name, value);

		return this;
	}
	
	/**
	 * Habilita/Desabilita o modo full compression do PDF veja
	 * {@link com.lowagie.text.pdf.PdfStamper#setFullCompression()}.
	 * 
	 * <p>
	 * Itext doc: <i>Sets the document's compression to the new 1.5 mode with
	 * object streams and xref streams.</i>
	 * </p>
	 * 
	 * @param option
	 *            Escolha de compressão
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 * 
	 */
	public PdfDocMix withFullCompression(boolean option) {
		this.fullCompression = option;
		return this;
	}
	
	/**
	 * Habilita/Desabilita a remoção dos campos do PDF.
	 * 
	 * <p>
	 * Por padrão os campos são removidos, ou seja, default = true.
	 * </p>
	 * 
	 * @param option
	 *            Escolha por remoção
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 * 
	 */
	public PdfDocMix removeFields(boolean option) {
		this.removeFields = option;
		return this;
	}
	
	/**
	 * Define o Título do documento.
	 * 
	 * @param title
	 * 
	 * @return Esta instância após a operação
	 */
	public PdfDocMix withTitle(String title){
		docInfo.title(title);
		return this;
	}
	
	/**
	 * Define o Autor do documento.
	 * 
	 * @param author
	 * 
	 * @return Esta instância após a operação
	 */
	public PdfDocMix withAuthor(String author){
		docInfo.author(author);
		return this;
	}
	
	/**
	 * Define o Assunto do documento.
	 * 
	 * @param subject
	 * 
	 * @return Esta instância após a operação
	 */
	public PdfDocMix withSubject(String subject){
		docInfo.subject(subject);
		return this;
	}
	
	/**
	 * Define as Palavras-chave do documento.
	 * 
	 * @param keywords
	 * 
	 * @return Esta instância após a operação
	 */
	public PdfDocMix withKeywords(String keywords){
		docInfo.keywords(keywords);
		return this;
	}
	
	/**
	 * Define o Software/Ferramenta de criação do documento.
	 * 
	 * @param creator
	 * 
	 * @return Esta instância após a operação
	 */
	public PdfDocMix withCreator(String creator){
		docInfo.creator(creator);
		return this;
	}

	/**
	 * Define a data de criação do documento.
	 * 
	 * @param date Data de criação
	 * 
	 * @return Esta instância após a operação
	 */
	public PdfDocMix withCreation(Calendar date){
		docInfo.creation(date);
		return this;
	}
	
	/**
	 * Redefine as meta-informações do documento, ex: título, autor, data de
	 * criação, etc.
	 * 
	 * <p>
	 * Todas as informações anteriormente atribuídas por:
	 * {@linkplain #withTitle(String)}, {@linkplain #withSubject(String)}, etc. serão
	 * substituídas pelo conteúdo do {@code docInfo} nessa operação.
	 * </p>
	 * 
	 * @param docInfo
	 *            Informações sobre o documento
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @see org.jrimum.bopepo.pdf.PdfDocInfo
	 */
	public PdfDocMix withDocInfo(PdfDocInfo docInfo){
		
		checkNotNull(docInfo, "Valor null para docInfo não permitido!");
		
		this.docInfo = docInfo;
		
		return this;
	}
	
	/**
	 * Define se o título do documento será exibido na barra superior do PDF.
	 * 
	 * <p>
	 * Caso não seja informada uma opção, prevalece a definição do template PDF.
	 * </p>
	 * 
	 * @param option
	 * 
	 * @return Esta instância após a operação
	 */
	public PdfDocMix withDisplayDocTilteOption(boolean option){
		
		this.displayDocTitle = option;
		
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
	 * @param destURL
	 *            URL do arquivo o qual o documento será gerado
	 * @return Documento em forma de arquivo PDF
	 * 
	 * @since 0.2
	 * 
	 */
	public File toFile(URL destURL){

		checkDestURL(destURL);
				
		try {
		
			return toFile(new File(destURL.toURI()));
			
		} catch (Exception e) {
			
			LOG.error(
					"Erro durante a criação do arquivo! "
							+ e.getLocalizedMessage(), e);

			return Exceptions.throwIllegalStateException(
					"Erro ao tentar criar arquivo! " + "Causado por "
							+ e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Retorna o documento em forma de arquivo PDF.
	 * 
	 * @param destFile
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

			LOG.error(
					"Erro durante a criação do arquivo! "
							+ e.getLocalizedMessage(), e);

			return Exceptions.throwIllegalStateException(
					"Erro ao tentar criar arquivo! " + "Causado por "
							+ e.getLocalizedMessage(), e);
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

			LOG.error(
					"Erro durante a criação do stream! "
							+ e.getLocalizedMessage(), e);

			return Exceptions.throwIllegalStateException(
					"Erro durante a criação do stream! " + "Causado por "
							+ e.getLocalizedMessage(), e);
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

			LOG.error(
					"Erro durante a criação do array de bytes! "
							+ e.getLocalizedMessage(), e);

			return Exceptions.throwIllegalStateException(
					"Erro durante a criação do array de bytes! "
							+ "Causado por " + e.getLocalizedMessage(), e);
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
	 * @return Esta instância após a operação
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
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(URL templateUrl) {
		try {
			setTemplate(templateUrl.openStream());
			return this;
		} catch (Exception e) {
			return Exceptions.throwIllegalStateException(e);
		}
	}

	/**
	 * Define o template que será utilizado para construir o documento.
	 * 
	 * @param templateInput
	 * 
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(InputStream templateInput) {
		try {
			setTemplate(Files.toByteArray(templateInput));
			return this;
		} catch (Exception e) {
			return Exceptions.throwIllegalStateException(e);
		}
	}

	/**
	 * Define o template que será utilizado para construir o documento.
	 * 
	 * @param templatePath
	 * 
	 * @return Esta instância após a operação
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
	 * @return Esta instância após a operação
	 * 
	 * @since 0.2
	 * 
	 */
	private PdfDocMix setTemplate(File templateFile) {
		try {
			setTemplate(Files.fileToBytes(templateFile));
			return this;
		} catch (Exception e) {
			return Exceptions.throwIllegalStateException(e);
		}
	}

	/**
	 * Indica se o viewer foi habilitado a comprimir o pdf do documento gerado.
	 * 
	 * @see #withFullCompression(boolean)
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
	 * Indica se o viewer foi habilitado para remover todos os campos do pdf
	 * gerado.
	 * 
	 * @see #removeFields
	 * 
	 * @return indicativo de compressão
	 * 
	 * @since 0.2
	 */
	private boolean isRemoveFields() {
		return removeFields;
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
	private void process() {

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
	private void init() {

		try {

			reader = new PdfReader(getTemplate());

			outputStream = new ByteArrayOutputStream();
			
			stamper = new PdfStamper(reader, outputStream);
			
			final String JRIMUM = "jrimum.org/bopepo"; 

			String creator = docInfo.creator();
			
			if(isBlank(creator)){
				withCreator(JRIMUM);
			}else{
				withCreator(creator+" by ("+JRIMUM+")");
			}

			if(isNull(docInfo.creation())){
				docInfo.creation(Calendar.getInstance());
			}			
			
			stamper.setMoreInfo((HashMap<?,?>)docInfo.toMap());
			
			if(isNotNull(displayDocTitle)){
				stamper.addViewerPreference(PdfName.DISPLAYDOCTITLE, displayDocTitle ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
			}
			
			form = stamper.getAcroFields();
			
		} catch (Exception e) {

			Exceptions.throwIllegalStateException(e);
		}
	}

	/**
	 * Preenche todos os campos do formulário PDF com os dados do documento
	 * contido na instância.
	 * 
	 * @since 0.2
	 */
	private void fillFields() {

		setTextFields();
		setImageFields();
	}

	/**
	 * Adiciona, caso existam, os textos definidos em
	 * {@linkplain #put(String, String)} ou {@linkplain #putAllTexts(Map)}.
	 * 
	 * @since 0.2
	 */
	private void setTextFields() {

		if (hasElement(txtMap)) {
			for (Entry<String, String> e : txtMap.entrySet()) {
				try {
					form.setField(e.getKey(), e.getValue());
				} catch (Exception ex) {
					Exceptions.throwIllegalStateException(ex);
				}
			}
		}
	}

	/**
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do
	 * documento atribuídos no map e templante.
	 * 
	 * @since 0.2
	 */
	private void setImageFields() {

		if (hasElement(imgMap)) {
			for (Entry<String, java.awt.Image> e : imgMap.entrySet()) {
					setImage(e.getKey(),e.getValue());
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
	private void setImage(String fieldName, java.awt.Image image) {

		float posImgField[];

		if (isNotBlank(fieldName)) {

			posImgField = form.getFieldPositions(fieldName);

			if (isNotNull(posImgField)) {
				try {
					PDFs.changeFieldToImage(stamper, posImgField, getPdfImage(image));
				} catch (Exception e) {
					Exceptions.throwIllegalStateException(e);
				}
			}else{
				LOG.warn("Posicionamento do campo de imagem nao encontrado! CAMPO: "+fieldName);
			}
		}
	}
	
	public Image getPdfImage(java.awt.Image image){
		
		Image pdfImage = imagesInUseMap.get(image);
		
		if(isNull(pdfImage)){
			try {
				pdfImage = Image.getInstance(image, null);
				imagesInUseMap.put(image, pdfImage);
			} catch (Exception ex) {
				Exceptions.throwIllegalStateException(ex);
			}
		}
		return pdfImage;
	}

	/**
	 * Finaliza a escrita de dados no template através do fechamento do
	 * {@code stamper}, {@code reader} e {@code outputStream}.
	 * 
	 * @since 0.2
	 */
	private void end() {

		if (isFullCompression()) {
			stamper.setFullCompression();
		}

		if (isRemoveFields()) {
			stamper.setFreeTextFlattening(true);
			stamper.setFormFlattening(true);
			reader.removeFields();
		} else {
			stamper.setFreeTextFlattening(false);
			stamper.setFormFlattening(false);
		}

		reader.consolidateNamedDestinations();

		reader.eliminateSharedStreams();

		try {
			// Send immediately
			outputStream.flush();
			// close All in this order
			outputStream.close();
			reader.close();
			stamper.close();

		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
	}

	private static void checkDestPath(String path) {

		checkString(path,
				"Caminho destinado a geração do(s) arquivo(s) não contém informação!");
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

	private static void checkDestURL(URL url) {

		Objects.checkNotNull(url,
				"URL destinada a geração do(s) documentos(s) nula!");
	}
	
	private static void checkDestFile(File file) {
		
		Objects.checkNotNull(file,
		"Arquivo destinado a geração do(s) documentos(s) nulo!");
	}

}
