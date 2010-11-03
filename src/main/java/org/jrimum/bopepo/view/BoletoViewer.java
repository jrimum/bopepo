/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 08/05/2008 - 00:10:01
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 08/05/2008 - 00:10:01
 * 
 */

package org.jrimum.bopepo.view;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.Boleto;
import org.jrimum.utilix.Collections;
import org.jrimum.utilix.Objects;

/**
 * <p>
 * Agrupa as formas de <strong>"visão"</strong> de um boleto.
 * </p>
 * 
 * <p>
 * Exemplo de formas de visualização:
 * <ul>
 * <li>PDF</li>
 * <li>Stream</li>
 * <li>Array de Bytes</li>
 * <li>Array de Bytes</li>
 * </ul>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:samuelvalerio@gmail.com">Samuel Valério</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class BoletoViewer {

	/**
	 * 
	 */
	private static Logger log = Logger.getLogger(BoletoViewer.class);

	/**
	 * <p>
	 * Engine responsável pela visualização em formato <em>PDF</em>.
	 * </p>
	 */
	private PdfViewer pdfViewer;

	
	/**
	 * <p>
	 * Instancia o visualizador com o template padrão.
	 * </p>
	 * 
	 * @param boleto
	 *            - Boleto preenchido
	 */
	public BoletoViewer(Boleto boleto) {
		initViewerPDF(null, null, boleto);			
	}

	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            - Boleto preenchido
	 * @param templatePathName
	 *            - Template PDF o qual o boleto será gerado
	 */
	public BoletoViewer(Boleto boleto, String templatePathName) {
		initViewerPDF(templatePathName, null, boleto);
	}
	
	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            - Boleto preenchido
	 * @param templatePathName
	 *            - Template PDF o qual o boleto será gerado
	 */
	public BoletoViewer(Boleto boleto, File template) {
		initViewerPDF(null, template, boleto);
	}

	/**
	 * Para uso interno do componente
	 */
	private BoletoViewer() {
		this.pdfViewer = new PdfViewer();
	}

	/**
	 * <p>
	 * Agrupo vários boletos em um único PDF.
	 * </p>
	 * 
	 * @param pathNameDest
	 *            Caminho no qual será gerado o PDF
	 * @param boletos
	 *            Boletos a serem agrupados
	 *            
	 * @return Arquivo PDF
	 * 
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(String pathNameDest, List<Boleto> boletos) {

		checkPathDest(pathNameDest);
		checkBoletosList(boletos);
		
		return PdfViewer.groupInOnePDF(boletos, new BoletoViewer(), new File(pathNameDest));
	}
	
	public static File groupInOnePDF(File file, List<Boleto> boletos) {

		checkFileDest(file);
		checkBoletosList(boletos);
		
		return PdfViewer.groupInOnePDF(boletos, new BoletoViewer(), file);
	}

	public static File groupInOnePDF(String pathNameDest, List<Boleto> boletos, String templatePathName) {

		checkPathDest(pathNameDest);
		checkBoletosList(boletos);
		checkPathTemplate(templatePathName);
		
		return PdfViewer.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templatePathName), new File(pathNameDest));
	}
	
	public static File groupInOnePDF(String pathNameDest, List<Boleto> boletos, File templateFile) {

		checkPathDest(pathNameDest);
		checkBoletosList(boletos);
		checkFileTemplate(templateFile);
		
		return PdfViewer.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templateFile), new File(pathNameDest));
	}
	
	public static File groupInOnePDF(File file, List<Boleto> boletos, String templatePathName) {

		checkFileDest(file);
		checkBoletosList(boletos);
		checkPathTemplate(templatePathName);
		
		return PdfViewer.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templatePathName), file);
	}
	
	public static File groupInOnePDF(File file, List<Boleto> boletos, File template) {

		checkFileDest(file);
		checkBoletosList(boletos);
		checkFileTemplate(template);
					
		return PdfViewer.groupInOnePDF(boletos, new BoletoViewer().setTemplate(template), file);
	}

	/**
	 * <p>
	 * Gera vários arquivos pdf, cada qual com o seu boleto.
	 * </p>
	 * 
	 * @param path Caminho no qual será gerados os arquivos
	 * @param extensao TODO
	 * @param boletos Boletos a partir dos quais serão gerados os arquivos
	 * @return Vários arquivos pdf
	 * 
	 * 
	 * @since 0.2
	 */

	public static List<File> onePerPDF(String path, String extensao, List<Boleto> boletos) {
		
		List<File> files = new ArrayList<File>();
		
		Objects.checkNotNull(path,"Path inválido!");
		Objects.checkNotNull(extensao, "Extensão inválida!");
		Objects.checkNotNull(boletos,"Lista de boletos inválida!");
		
		if(StringUtils.isNotBlank(path)) {
			
			if(!boletos.isEmpty()) {
				
				files.addAll(PdfViewer.onePerPDF(path, extensao, boletos));
				
			} else {
				throw new IllegalArgumentException("A Lista de boletos está vazia!");
			}
			
		} else {
			throw new IllegalArgumentException("Path(Diretório) destinado a geração dos arquivos não contém informação!");
		}
		
		return files;
	}

	public File getTemplate() {
		
		return pdfViewer.getTemplate();
	}

	/**
	 * <p>
	 * Define o template que será utilizado para construir o boleto.
	 * </p>
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 */
	public BoletoViewer setTemplate(File template) {

		checkFileTemplate(template);
			
		this.pdfViewer.setTemplate(template);
			
		return this;
	}

	
	/**
	 * <p>
	 * @see BoletoViewer#setTemplate(File)
	 * </p>
	 * 
	 * @param pathName
	 * 
	 * @since 0.2
	 */
		
	public BoletoViewer setTemplate(String pathName) {
		
		
		checkPathTemplate(pathName);
		
		this.pdfViewer.setTemplate(pathName);
			
		return this;
	}
	
	/**
	 * <p>
	 * Caso algum template tenha sido utilizado, este método define que após sua
	 * execução o boleto será consturído com o template padrão.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public BoletoViewer removeTemplate() {

		final String PADRAO = null;

		if (isNotNull(pdfViewer)) {
			pdfViewer.setTemplate(PADRAO);
		}

		return this;
	}

	/**
	 * <p>
	 * Retorna o boleto em um arquivo pdf.
	 * </p>
	 * 
	 * @param pathName Caminho onde será criado o arquivo pdf
	 * @return File
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */
	public File getPdfAsFile(String pathName) {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + pdfViewer);
		}

		return pdfViewer.getFile(pathName);
	}

	/**
	 * <p>
	 * Retorna o boleto em uma stream.
	 * </p>
	 * 
	 * @return ByteArrayOutputStream
	 * 
	 * @since 0.1
	 */
	public ByteArrayOutputStream getPdfAsStream() {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + pdfViewer);
		}

		return pdfViewer.getStream();

	}

	/**
	 * <p>
	 * Retorna o boleto em um array de bytes.
	 * </p>
	 * 
	 * @return byte[]
	 * 
	 * @since 0.1
	 */
	public byte[] getPdfAsByteArray() {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + pdfViewer);
		}

		return pdfViewer.getBytes();
	}

	/**
	 * @return the boleto
	 * 
	 * @since 0.2
	 */
	public Boleto getBoleto() {
		return pdfViewer.getBoleto();
	}

	/**
	 * @param boleto
	 *            the boleto to set
	 * 
	 * @since 0.2
	 */
	public BoletoViewer setBoleto(Boleto boleto) {
		
		Objects.checkNotNull(boleto);
		
		updateViewerPDF(boleto);
		
		return this;
	}

	private void initViewerPDF(String templatePathName, File template, Boleto boleto) {
		
		Objects.checkNotNull(boleto);
			
		this.pdfViewer = new PdfViewer(boleto);
		
		/*
		 * O arquivo tem prioridade 
		 */
		if (isNotBlank(templatePathName) && isNotNull(template)) {
			
			setTemplate(template);
			
		} else {
			
			if (isNotBlank(templatePathName)) {
				
				setTemplate(templatePathName);
			}
			
			if (isNotNull(template)) {
				
				setTemplate(template);
			}
		}
	}
	
	/**
	 * <p>
	 * Atualiza o objeto BoletoViewer mantendo as "invariantes".
	 * </p>
	 * 
	 * @param boleto
	 * 
	 * @since 0.2
	 */
	private void updateViewerPDF(Boleto boleto) {

		if(isNotNull(this.pdfViewer)) {
			
			this.pdfViewer = new PdfViewer(boleto, this.pdfViewer.getTemplate());
			
		} else {
			
			this.pdfViewer = new PdfViewer(boleto);
		}
	}
	

	private static void checkPathDest(String path){
		
		checkString(path, "Caminho destinado a geração do(s) arquivo(s) não contém informação!");
	}
	
	private static void checkPathTemplate(String path){
		
		checkString(path, "Caminho do template não contém informação!");
	}
	
	private static void checkString(String str, String msg){
		
		Objects.checkNotNull(str);
		
		if(StringUtils.isBlank(str)) {
			
			throw new IllegalArgumentException(msg);
		}
	}
	
	private static void checkFileDest(File file){
		
		Objects.checkNotNull(file, "Arquivo destinado a geração do(s) boleto(s) nulo!");
	}
	
	private static void checkFileTemplate(File file){
		
		Objects.checkNotNull(file, "Arquivo de template nulo!");
	}
	
	private static void checkBoletosList(List<Boleto> boletos){
		
		Objects.checkNotNull(boletos,"Lista de boletos nula!");
		Collections.checkNotEmpty(boletos, "A Lista de boletos está vazia!");
	}
}
