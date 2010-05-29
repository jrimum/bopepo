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

package br.com.nordestefomento.jrimum.bopepo.view.guia;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.JRimumException;
import br.com.nordestefomento.jrimum.bopepo.guia.Guia;

import com.lowagie.text.DocumentException;

/**
 * 
 * <p>
 * Agrupa as formas de visualização de um boleto.
 * </p>
 * 
 * <p>
 * EXEMPLO de formas de visualização:
 * <ul>
 * <li>PDF</li>
 * <li>Stream</li>
 * <li>Array de Bytes</li>
 * </ul>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael
 * @author Romulo
 * 
 * @since 0.3
 * 
 * @version 0.3
 */

public class GuiaViewer {

	//TODO Teste no teste unitário.
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(GuiaViewer.class);

	/**
	 * <p> Engine responsável pela visualização em formato <em>PDF</em>.
	 */
	private ViewerPDF viewerPDF;

	/**
	 * @param guia
	 * @throws DocumentException
	 * @throws IOException
	 */
	public GuiaViewer(Guia guia) throws JRimumException {
		initViewerPDF(null, null, guia);			
	}
	
	/**
	 * @param guia
	 * @param templatePathName
	 * @throws JRimumException
	 */
	public GuiaViewer(Guia guia, String templatePathName) throws JRimumException {
		initViewerPDF(templatePathName, null, guia);
	}
	
	/**
	 * @param guia
	 * @param template
	 * @throws JRimumException
	 */
	public GuiaViewer(Guia guia, File template) throws JRimumException {
		initViewerPDF(null, template, guia);
	}

	/**
	 *<p> Para uso interno do componente </p> 
	 */
	protected GuiaViewer() {
		this.viewerPDF = new ViewerPDF();
	}

	/**
	 * <p>
	 * Agrupo vários guias em um único pdf.
	 * </p>
	 * 
	 * @param pathName
	 *            Caminho no qual será gerado o pdf
	 * @param guias
	 *            Guias a serem agrupadas
	 * @return Arquivo pdf
	 * @throws JRimumException
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(String pathName, List<Guia> guias) throws JRimumException {

		File group = null;
		
		if (validatePathName(pathName) && validateGuiasList(guias)) {
				group = groupInOnePDF(pathName, guias, new GuiaViewer());
		}

		return group;
	}

	public static File groupInOnePDF(String destPathName, List<Guia> guias, String templatePathName) throws JRimumException {

		File group = null;

		if (validatePathName(destPathName) &&validateGuiasList(guias) && validatePathName(templatePathName)) {
			group = groupInOnePDF(destPathName, guias, new GuiaViewer().setTemplate(templatePathName));
		}
					
		return group;
	}
	
	public static File groupInOnePDF(String destPathName, List<Guia> guias, File templateFile) throws JRimumException {

		File group = null;

		if (validatePathName(destPathName) && validateGuiasList(guias) && validateFile(templateFile, "template")) {
					group = groupInOnePDF(destPathName, guias, new GuiaViewer().setTemplate(templateFile));
		}
					
		return group;
	}

	/**
	 * <p>
	 * Gera vários arquivos pdf, cada qual com a sua guia.
	 * </p>
	 * 
	 * @param path Caminho no qual será gerados os arquivos
	 * @param extensao TODO
	 * @param guias Guias a partir dos quais serão gerados os arquivos
	 * @return Vários arquivos pdf
	 * @throws JRimumException
	 * 
	 * @since 0.2
	 */

	public static List<File> onePerPDF(String path, String extensao, List<Guia> guias) throws JRimumException {
		
		List<File> files = new ArrayList<File>();
		
		if (isNotNull(path, "path") && isNotNull(guias, "guias")) {
			
			if(StringUtils.isNotBlank(path)) {
				
				if(!guias.isEmpty()) {
					
					files.addAll(ViewerPDF.onePerPDF(path, extensao, guias));
					
				} else {
					throw new IllegalArgumentException("A Lista de guias está vazia!");
				}
				
			} else {
				throw new IllegalArgumentException("Path(Diretório) destinado a geração dos arquivos não contém informação!");
			}
		}
		
		return files;
	}

	
	public File getTemplate() {
		return viewerPDF.getTemplate();
	}

	
	/**
	 * <p>
	 * Define o template que será utilizado para construir o guia.
	 * </p>
	 * 
	 * @param template
	 * 
	 * @since 0.2
	 */
	public GuiaViewer setTemplate(File template) {

		if(isNotNull(template)) {
			this.viewerPDF.setTemplate(template);
			
		} else {
			throw new NullPointerException("Arquivo de template inválido: valor [null]");
		}
		
		return this;
	}

	
	/**
	 * <p>
	 * @see GuiaViewer#setTemplate(File)
	 * </p>
	 * 
	 * @param pathName
	 * 
	 * @since 0.2
	 */
		
	public GuiaViewer setTemplate(String pathName) {
		
		if(isNotBlank(pathName)) {
			viewerPDF.setTemplate(pathName);
			
		} else {
			throw new IllegalArgumentException("Caminho do template inválido: valor [" + pathName + "]");
		}
		
		return this;
	}
	
	/**
	 * <p>
	 * Caso algum template tenha sido utilizado, este método define que após sua
	 * execução o guia será consturído com o template padrão.
	 * </p>
	 * 
	 * @since 0.2
	 */
	public GuiaViewer removeTemplate() {

		final String PADRAO = null;

		if (isNotNull(viewerPDF)) {
			viewerPDF.setTemplate(PADRAO);
		}

		return this;
	}

	/**
	 * <p>
	 * Retorna o guia em um arquivo pdf.
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
			log.debug("Documento instance: " + viewerPDF);
		}

		return viewerPDF.getFile(pathName);
	}

	/**
	 * <p>
	 * Retorna o guia em uma stream.
	 * </p>
	 * 
	 * @return ByteArrayOutputStream
	 * 
	 * @since 0.1
	 */
	public ByteArrayOutputStream getPdfAsStream() {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + viewerPDF);
		}

		return viewerPDF.getStream();

	}

	/**
	 * <p>
	 * Retorna o guia em um array de bytes.
	 * </p>
	 * 
	 * @return byte[]
	 * 
	 * @since 0.1
	 */
	public byte[] getPdfAsByteArray() {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + viewerPDF);
		}

		return viewerPDF.getBytes();
	}

	/**
	 * @return the guia
	 * 
	 * @since 0.2
	 */
	public Guia getGuia() {
		return viewerPDF.getGuia();
	}

	/**
	 * @param guia
	 *            the guia to set
	 * 
	 * @since 0.2
	 */
	public GuiaViewer setGuia(Guia guia) {
		
		if(isNotNull(guia, "guia")) {
			updateViewerPDF(guia);
		}
		
		return this;
	}

	private static boolean validatePathName(String pathName){
		
		boolean ok = false;
		
		if (isNotNull(pathName, "pathName")) {
			
			if(StringUtils.isNotBlank(pathName)) {
				ok = true;
			} else {
				throw new IllegalArgumentException("Path(Diretório) destinado a geração do(s) arquivo(s) não contém informação!");
			}
		}
		
		return ok;
	}
	
	private static boolean validateFile(File file, String name){
		
		boolean ok = false;
		
		if (isNotNull(file)) {
				ok = true;
		} else {
			throw new NullPointerException("File(Arquivo) destinado a geração do(s) documento(s) [" + name + "] nulo!");
		}
		
		return ok;
	}
	
	private static boolean validateGuiasList(List<Guia> guias){
		
		boolean ok = false;
		
		if (isNotNull(guias, "guias")) {
			
			if(!guias.isEmpty()) {
				ok = true;
				
			} else {
				throw new IllegalArgumentException("A Lista de guias está vazia!");
			}
		}
		
		return ok;
	}
	
	private static File groupInOnePDF(String pathName, List<Guia> guias, GuiaViewer guiaViewer){
		
		return ViewerPDF.groupInOnePDF(pathName, guias, guiaViewer);		
	}

	private void initViewerPDF(String templatePathName, File template, Guia guia) {
		
		if (isNotNull(guia, "guia")) {
			this.viewerPDF = new ViewerPDF(guia);
		}
		
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
	 * Atualiza o objeto GuiaViewer mantendo as "invariantes".
	 * </p>
	 * 
	 * @param guia
	 * 
	 * @since 
	 */
		
	private void updateViewerPDF(Guia guia) {

		if(isNotNull(this.viewerPDF)) {
			this.viewerPDF = new ViewerPDF(guia, this.viewerPDF.getTemplate());
			
		} else {
			this.viewerPDF = new ViewerPDF(guia);
		}
	}
}
