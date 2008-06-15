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

package br.com.nordestefomento.jrimum.bopepo.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.ACurbitaObject;
import br.com.nordestefomento.jrimum.JRimumException;
import br.com.nordestefomento.jrimum.bopepo.Boleto;

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
 * @author Samuel
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class BoletoViewer extends ACurbitaObject {

	//TODO Teste no teste unitário.
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Boleto utilizado para a visualização.
	 */
	private Boleto boleto;

	/**
	 * <p> Engine responsável pela visualização em formato <em>PDF</em>.
	 */
	private ViewerPDF viewerPDF;

	/**
	 * @param boleto
	 * @throws DocumentException
	 * @throws IOException
	 */
	public BoletoViewer(Boleto boleto) throws JRimumException {
		super();

		this.boleto = boleto;

		try {

			this.viewerPDF = new ViewerPDF(this.boleto);

		} catch (Exception e) {
			throw new JRimumException(e);
		}
	}

	/**
	 * <p>
	 * Agrupo vários boletos em um único pdf.
	 * </p>
	 * 
	 * @param pathName Caminho no qual será gerado o pdf
	 * @param boletos Boletos a serem agrupados
	 * @return Arquivo pdf
	 * @throws JRimumException
	 * 
	 * @since 0.2
	 */

	public static File groupInOnePDF(String pathName, List<Boleto> boletos)
			throws JRimumException {

		if (isNotNull(pathName, "pathName") && isNotNull(boletos, "boletos")) {

			if(StringUtils.isNotBlank(pathName)){
				if(!boletos.isEmpty()){
					
						try {
							
							ViewerPDF.groupInOnePDF(pathName, boletos);
							
						} catch (IOException e) {
							
							JRimumException jrie =  new JRimumException("Erro durante a geração do arquivo",e); 
							
							log.error("Erro no agrupamento de boletos", e);
							
							throw jrie;
							
						} catch (DocumentException e) {
							
							JRimumException jrie =  new JRimumException("Erro durante a geração do arquivo",e); 
							
							log.error("Erro no agrupamento de boletos", e);
							
							throw jrie;
						}
					
				}else
					throw new JRimumException(new IllegalArgumentException("A Lista de boletos está vazia!"));
			}else
				throw new JRimumException(new IllegalArgumentException("Path/Name para o arquivo não contém informação!"));
		}

		return null;
	}

	/**
	 * <p>
	 * Gera vários arquivos pdf, cada qual com o seu boleto.
	 * </p>
	 * 
	 * @param path Caminho no qual será gerados os arquivos
	 * @param boletos Boletos a partir dos quais serão gerados os arquivos
	 * @return Vários arquivos pdf
	 * @throws JRimumException
	 * 
	 * @since 0.2
	 */

	public static List<File> onePerPDF(String path, List<Boleto> boletos)
			throws JRimumException {

		if (isNotNull(path, "path") && isNotNull(boletos, "boletos")) {

			if(StringUtils.isNotBlank(path)){
				if(!boletos.isEmpty()){
					
						try {
							
							ViewerPDF.onePerPDF(path, boletos);
							
						} catch (IOException e) {
							
							JRimumException jrie =  new JRimumException("Erro durante a geração dos arquivos",e); 
							
							log.error("Erro durante a geração de PDFs para boletos", e);
							
							throw jrie;
							
						} catch (DocumentException e) {
							
							JRimumException jrie =  new JRimumException("Erro durante a geração dos arquivos",e); 
							
							log.error("Erro durante a geração de PDFs para boletos", e);
							
							throw jrie;
						}
					
				}else
					throw new JRimumException(new IllegalArgumentException("A Lista de boletos está vazia!"));
			}else
				throw new JRimumException(new IllegalArgumentException("Path(Diretório) destinado a geração dos arquivos não contém informação!"));
		}
		return null;
	}

	
	public File getTemplate() {

		return viewerPDF.getTemplate();
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
		
	public void setTemplate(File template) {

		viewerPDF.setTemplate(template);
	}

	
	/**
	 * <p>
	 * @see BoletoViewer#setTemplate(File)
	 * </p>
	 * 
	 * @param pathname
	 * 
	 * @since 0.2
	 */
		
	public void setTemplate(String pathname) {

		viewerPDF.setTemplate(pathname);
	}

	/**
	 * <p>
	 * Retorna o boleto em um arquivo pdf.
	 * </p>
	 * 
	 * @param pathName Caminho onde será criado o arquivo pdf
	 * @return File
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */

	public File getAsPDF(String pathName) throws IllegalArgumentException,
			IOException, DocumentException {

		if (log.isDebugEnabled())
			log.debug("documento instance : " + viewerPDF);

		return viewerPDF.getFile(pathName + ".pdf");
	}

	/**
	 * <p>
	 * Retorna o boleto em uma stream.
	 * </p>
	 * 
	 * @return ByteArrayOutputStream
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since 0.1
	 */

	public ByteArrayOutputStream getAsStream() throws IOException,
			DocumentException {

		if (log.isDebugEnabled())
			log.debug("documento instance : " + viewerPDF);

		return viewerPDF.getStream();

	}

	/**
	 * <p>
	 * Retorna o boleto em um array de bytes.
	 * </p>
	 * 
	 * @return byte[]
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * 
	 * @since 0.1
	 */

	public byte[] getAsByteArray() throws IOException, DocumentException {

		if (log.isDebugEnabled())
			log.debug("documento instance : " + viewerPDF);

		return viewerPDF.getBytes();
	}

	/**
	 * @return the boleto
	 * 
	 * @since 0.2
	 */
	public Boleto getBoleto() {
		return boleto;
	}

	/**
	 * @param boleto
	 *            the boleto to set
	 * 
	 * @since 0.2
	 */
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

}
