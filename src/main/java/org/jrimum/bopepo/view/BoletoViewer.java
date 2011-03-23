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

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.jrimum.utilix.Objects.isNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.utilix.Collections;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Strings;

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
 * <li>Outros</li>
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
	 *            Boleto preenchido
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code boleto} seja nulo
	 */
	public BoletoViewer(Boleto boleto) {

		checkBoleto(boleto);

		this.pdfViewer = new PdfViewer(boleto);
	}

	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            Boleto preenchido
	 * @param templatePath
	 *            Template PDF o qual o boleto será gerado
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code boleto} ou {@code template} seja nulo
	 */
	public BoletoViewer(Boleto boleto, String templatePath) {

		checkBoleto(boleto);
		checkTemplatePath(templatePath);

		this.pdfViewer = new PdfViewer(boleto);

		setTemplate(templatePath);
	}

	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            Boleto preenchido
	 * @param templateFile
	 *            Template PDF o qual o boleto será gerado
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code boleto} ou {@code template} seja nulo
	 */
	public BoletoViewer(Boleto boleto, File templateFile) {

		checkBoleto(boleto);
		checkTemplateFile(templateFile);

		this.pdfViewer = new PdfViewer(boleto);

		setTemplate(templateFile);
	}

	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            Boleto preenchido
	 * @param templateUrl
	 *            Template PDF o qual o boleto será gerado
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code boleto} ou {@code template} seja nulo
	 */
	public BoletoViewer(Boleto boleto, URL templateUrl) {

		checkBoleto(boleto);
		checkTemplateFile(templateUrl);

		this.pdfViewer = new PdfViewer(boleto);

		setTemplate(templateUrl);
	}

	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            Boleto preenchido
	 * @param templateInput
	 *            Template PDF o qual o boleto será gerado
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code boleto} ou {@code template} seja nulo
	 */
	public BoletoViewer(Boleto boleto, InputStream templateInput) {

		checkBoleto(boleto);
		checkTemplateFile(templateInput);

		this.pdfViewer = new PdfViewer(boleto);

		setTemplate(templateInput);
	}

	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            Boleto preenchido
	 * @param template
	 *            Template PDF o qual o boleto será gerado
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code boleto} ou {@code template} seja nulo
	 */
	public BoletoViewer(Boleto boleto, byte[] template) {

		checkBoleto(boleto);
		checkTemplateFile(template);

		this.pdfViewer = new PdfViewer(boleto);

		setTemplate(template);
	}

	/**
	 * Para uso interno do componente
	 */
	protected BoletoViewer() {

		this.pdfViewer = new PdfViewer();		
	}
	
	/**
	 * <p>
	 * Instancia o visualizador com o template padrão. Caso o boleto seja nulo,
	 * nenhuma instância do viewer é criada.
	 * </p>
	 * 
	 * @param boleto
	 *            Boleto preenchido
	 * 
	 * @throws IllegalArgumentException
	 *             Caso o {@code boleto} seja nulo
	 */
	public static BoletoViewer create(Boleto boleto) {

		checkBoleto(boleto);

		return new BoletoViewer(boleto);
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF. Ex: Se a lista contém
	 * 10 boletos, ao final será gerado um único arquivo PDF contendo os 10
	 * boletos, sendo 1 boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * 
	 * @return Arquivo PDF em array de bytes gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static byte[] groupInOnePDF(List<Boleto> boletos) {

		checkBoletosList(boletos);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer());
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * Ex: Se a lista contém 10 boletos, ao final será gerado
	 * um único arquivo PDF contendo os 10 boletos, sendo 1
	 * boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destPath
	 *            Caminho para o arquivo que armazenará os boletos
	 * 
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, String destPath) {

		checkBoletosList(boletos);
		checkDestPath(destPath);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer(),
				new File(destPath));
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * Ex: Se a lista contém 10 boletos, ao final será gerado
	 * um único arquivo PDF contendo os 10 boletos, sendo 1
	 * boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destPath
	 *            Arquivo que armazenará os boletos
	 * 
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, File destFile) {

		checkBoletosList(boletos);
		checkDestFile(destFile);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer(), destFile);
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * Ex: Se a lista contém 10 boletos, ao final será gerado
	 * um único arquivo PDF contendo os 10 boletos, sendo 1
	 * boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param templatePath
	 *            Caminho para o arquivo com o template para geração
	 * @return Arquivo PDF em array de bytes gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static byte[] groupInOnePdfWithTemplate(List<Boleto> boletos, String templatePath) {

		checkBoletosList(boletos);
		checkTemplatePath(templatePath);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templatePath));
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF. Ex: Se a lista contém
	 * 10 boletos, ao final será gerado um único arquivo PDF contendo os 10
	 * boletos, sendo 1 boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param templateUrl
	 *            Arquivo com o template para geração
	 * @return Arquivo PDF em array de bytes gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static byte[] groupInOnePdfWithTemplate(List<Boleto> boletos, URL templateUrl) {

		checkBoletosList(boletos);
		checkTemplateFile(templateUrl);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templateUrl));
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF. Ex: Se a lista contém
	 * 10 boletos, ao final será gerado um único arquivo PDF contendo os 10
	 * boletos, sendo 1 boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param templateFile
	 *            Arquivo com o template para geração
	 * @return Arquivo PDF em array de bytes gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static byte[] groupInOnePdfWithTemplate(List<Boleto> boletos, File templateFile) {

		checkBoletosList(boletos);
		checkTemplateFile(templateFile);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templateFile));
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF. Ex: Se a lista contém
	 * 10 boletos, ao final será gerado um único arquivo PDF contendo os 10
	 * boletos, sendo 1 boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param templateInput
	 *            Arquivo com o template para geração
	 * @return Arquivo PDF em array de bytes gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static byte[] groupInOnePdfWithTemplate(List<Boleto> boletos, InputStream templateInput) {
		
		checkBoletosList(boletos);
		checkTemplateFile(templateInput);
		
		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templateInput));
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF. Ex: Se a lista contém
	 * 10 boletos, ao final será gerado um único arquivo PDF contendo os 10
	 * boletos, sendo 1 boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param template
	 *            Arquivo com o template para geração
	 * @return Arquivo PDF em array de bytes gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static byte[] groupInOnePdfWithTemplate(List<Boleto> boletos, byte[] template) {
		
		checkBoletosList(boletos);
		checkTemplateFile(template);
		
		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer().setTemplate(template));
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * Ex: Se a lista contém 10 boletos, ao final será gerado
	 * um único arquivo PDF contendo os 10 boletos, sendo 1
	 * boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destPath
	 *            Caminho para o arquivo que armazenará os boletos
	 * @param templatePath
	 *            Caminho para o arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePdfWithTemplate(List<Boleto> boletos, String destPath,
			String templatePath) {

		checkBoletosList(boletos);
		checkDestPath(destPath);
		checkTemplatePath(templatePath);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templatePath),
				new File(destPath));
	}
	
	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * Ex: Se a lista contém 10 boletos, ao final será gerado
	 * um único arquivo PDF contendo os 10 boletos, sendo 1
	 * boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destPath
	 *            Caminho para o arquivo que armazenará os boletos
	 * @param templateFile
	 *            Arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePdfWithTemplate(List<Boleto> boletos, String destPath,
			File templateFile) {

		checkBoletosList(boletos);
		checkDestPath(destPath);
		checkTemplateFile(templateFile);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer().setTemplate(templateFile),
				new File(destPath));
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * Ex: Se a lista contém 10 boletos, ao final será gerado
	 * um único arquivo PDF contendo os 10 boletos, sendo 1
	 * boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destFile
	 *            Arquivo que armazenará os boletos
	 * @param templatePath
	 *            Caminho para o arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePdfWithTemplate(List<Boleto> boletos, File destFile,
			String templatePath) {

		checkBoletosList(boletos);
		checkDestFile(destFile);
		checkTemplatePath(templatePath);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer()
				.setTemplate(templatePath), destFile);
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * Ex: Se a lista contém 10 boletos, ao final será gerado
	 * um único arquivo PDF contendo os 10 boletos, sendo 1
	 * boleto em cada página.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destFile
	 *            Arquivo que armazenará os boletos
	 * @param templateFile
	 *            Arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePdfWithTemplate(List<Boleto> boletos, File destFile,
			File templateFile) {

		checkBoletosList(boletos);
		checkDestFile(destFile);
		checkTemplateFile(templateFile);

		return PdfViewerMultiProcessor.groupInOnePDF(boletos, new BoletoViewer()
				.setTemplate(templateFile), destFile);
	}
	
	/**
	 * <p>
	 * Agrupa os boletos das listas com seus respectivos templates em um único
	 * arquivo PDF. Caso exista sequência na coleção, a mesma é mantida.
	 * </p>
	 * 
	 * 
	 * @param templatesAndBoletos
	 *            Coleção de templates e boletos a serem agrupados
	 * 
	 * @return Arquivo PDF em array de bytes gerado com os boletos fornecidos
	 * 
	 * @since 0.2
	 */
	public static byte[] groupInOnePdfWithTemplates(Collection<Entry<byte[], List<Boleto>>> templatesAndBoletos) {

		checkTemplateBoletosEntries(templatesAndBoletos);
		
		return PdfViewerMultiProcessor.groupInOnePDF(templatesAndBoletos);
	}
	
	/**
	 * <p>
	 * Agrupa os boletos das listas com seus respectivos templates em um único
	 * arquivo PDF. Caso exista sequência na coleção, a mesma é mantida.
	 * </p>
	 * 
	 * 
	 * @param templatesAndBoletos
	 *            Coleção de templates e boletos a serem agrupados
	 * 
	 * @param destFile
	 *            Arquivo que armazenará os boletos
	 * 
	 * @return Arquivo PDF em array de bytes gerado com os boletos fornecidos
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePdfWithTemplates(Collection<Entry<byte[], List<Boleto>>> templatesAndBoletos, File destFile) {

		checkTemplateBoletosEntries(templatesAndBoletos);
		checkDestFile(destFile);
		
		try {
			
			return Files.bytesToFile(destFile, PdfViewerMultiProcessor.groupInOnePDF(templatesAndBoletos));
			
		} catch (Exception e) {

			throw new IllegalStateException("Erro inesperado!", e);
		}
	}
	
	public static List<byte[]> onePerPDF(List<Boleto> boletos) {

		checkBoletosList(boletos);

		return PdfViewerMultiProcessor.onePerPDF(boletos);
	}
	
	/**
	 * <p>
	 * Gera o arquivo PDF para cada boleto contido na lista. O nome do arquivo
	 * segue a forma:<br />
	 * <br />
	 * <tt>diretorio + (/ ou \\) + (indice do arquivo na lista + 1) + ".pdf"</tt>
	 * </p>
	 * 
	 * <p>
	 * Exemplo, uma lista com 3 boletos: {@code onePerPDF(boletos, file);} <br />
	 * <br />
	 * Arquivos gerados:
	 * <ul>
	 * <li><strong>1.pdf</strong></li>
	 * <li><strong>2.pdf</strong></li>
	 * <li><strong>3.pdf</strong></li>
	 * </ul>
	 * </p>
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destPath
	 *            Diretório o qual os boletos serão criados
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, String destPath) {

		checkBoletosList(boletos);
		checkDestPath(destPath);

		return onePerPDF(boletos, new File(destPath), EMPTY, EMPTY);
	}

	/**
	 * <p>
	 * Gera o arquivo PDF para cada boleto contido na lista. O nome do arquivo
	 * segue a forma:<br />
	 * <br />
	 * <tt>diretorio + (/ ou \\) + (indice do arquivo na lista + 1) + ".pdf"</tt>
	 * </p>
	 * 
	 * <p>
	 * Exemplo, uma lista com 3 boletos: {@code onePerPDF(boletos, file);} <br />
	 * <br />
	 * Arquivos gerados:
	 * <ul>
	 * <li><strong>1.pdf</strong></li>
	 * <li><strong>2.pdf</strong></li>
	 * <li><strong>3.pdf</strong></li>
	 * </ul>
	 * </p>
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destDir
	 *            Diretório o qual os boletos serão criados
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, File destDir) {

		checkBoletosList(boletos);
		checkDestDir(destDir);

		return onePerPDF(boletos, destDir, EMPTY, EMPTY);
	}

	/**
	 * <p>
	 * Gera o arquivo PDF para cada boleto contido na lista. O nome do arquivo
	 * segue a forma:<br />
	 * <br />
	 * <tt>diretorio + (/ ou \\) prefixo + (indice do arquivo na lista + 1) + ".pdf"</tt>
	 * </p>
	 * 
	 * <p>
	 * Exemplo, uma lista com 3 boletos: {@code onePerPDF(boletos, file,
	 * "BoletoPrefixo");} <br />
	 * <br />
	 * Arquivos gerados:
	 * <ul>
	 * <li><strong>BoletoPrefixo1.pdf</strong></li>
	 * <li><strong>BoletoPrefixo2.pdf</strong></li>
	 * <li><strong>BoletoPrefixo3.pdf</strong></li>
	 * </ul>
	 * </p>
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destPath
	 *            Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            Prefixo do nome do arquivo
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, String destPath, String prefixo) {

		checkBoletosList(boletos);
		checkDestPath(destPath);

		return onePerPDF(boletos, new File(destPath), prefixo, EMPTY);
	}

	/**
	 * <p>
	 * Gera o arquivo PDF para cada boleto contido na lista. O nome do arquivo
	 * segue a forma:<br />
	 * <br />
	 * <tt>diretorio + (/ ou \\) prefixo + (indice do arquivo na lista + 1) + ".pdf"</tt>
	 * </p>
	 * 
	 * <p>
	 * Exemplo, uma lista com 3 boletos: {@code onePerPDF(boletos, file,
	 * "BoletoPrefixo");} <br />
	 * <br />
	 * Arquivos gerados:
	 * <ul>
	 * <li><strong>BoletoPrefixo1.pdf</strong></li>
	 * <li><strong>BoletoPrefixo2.pdf</strong></li>
	 * <li><strong>BoletoPrefixo3.pdf</strong></li>
	 * </ul>
	 * </p>
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param destDir
	 *            Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            Prefixo do nome do arquivo
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, File destDir, String prefixo) {

		checkBoletosList(boletos);
		checkDestDir(destDir);

		return onePerPDF(boletos, destDir, prefixo, EMPTY);
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
	 *            Lista com os boletos a serem agrupados
	 * @param destPath
	 *            Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            Prefixo do nome do arquivo
	 * @param sufixo
	 *            Sufixo do nome do arquivo
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, String destPath, String prefixo, String sufixo) {

		checkBoletosList(boletos);
		checkDestPath(destPath);

		return onePerPDF(boletos, new File(destPath), prefixo, sufixo);
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
	 *            Lista com os boletos a serem agrupados
	 * @param fileDest
	 *            Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            Prefixo do nome do arquivo
	 * @param sufixo
	 *            Sufixo do nome do arquivo
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, File destDir, String prefixo, String sufixo) {

		checkBoletosList(boletos);
		checkDestDir(destDir);

		List<File> files = new ArrayList<File>(boletos.size());

		files.addAll(PdfViewerMultiProcessor.onePerPDF(boletos, destDir, prefixo, sufixo));

		return files;
	}
	
	public static List<byte[]> onePerPDFWithTemplate(List<Boleto> boletos, String templatePath) {

		checkBoletosList(boletos);
		checkTemplatePath(templatePath);

		return PdfViewerMultiProcessor.onePerPDF(boletos, new BoletoViewer().setTemplate(templatePath));
	}
	
	public static List<byte[]> onePerPDFWithTemplate(List<Boleto> boletos, URL templateUrl) {

		checkBoletosList(boletos);
		checkTemplateFile(templateUrl);

		return PdfViewerMultiProcessor.onePerPDF(boletos, new BoletoViewer().setTemplate(templateUrl));
	}
	
	public static List<byte[]> onePerPDFWithTemplate(List<Boleto> boletos, File templateFile) {

		checkBoletosList(boletos);
		checkTemplateFile(templateFile);

		return PdfViewerMultiProcessor.onePerPDF(boletos, new BoletoViewer().setTemplate(templateFile));
	}
	
	public static List<byte[]> onePerPDFWithTemplate(List<Boleto> boletos, InputStream templateInput) {

		checkBoletosList(boletos);
		checkTemplateFile(templateInput);

		return PdfViewerMultiProcessor.onePerPDF(boletos, new BoletoViewer().setTemplate(templateInput));
	}
	
	public static List<byte[]> onePerPDFWithTemplate(List<Boleto> boletos, byte[] template) {
		
		checkBoletosList(boletos);
		checkTemplateFile(template);
		
		return PdfViewerMultiProcessor.onePerPDF(boletos, new BoletoViewer().setTemplate(template));
	}
	
	/**
	 * <p>
	 * Agrupa os boletos das listas com seus respectivos templates em um único
	 * arquivo PDF. Caso exista sequência na coleção, a mesma é mantida.
	 * </p>
	 * 
	 * 
	 * @param templatesAndBoletos
	 *            Coleção de templates e boletos a serem agrupados
	 * 
	 * @return Arquivo PDF em array de bytes gerado com os boletos fornecidos
	 * 
	 * @since 0.2
	 */
	public static List<byte[]> onePerPdfWithTemplates(Collection<Entry<byte[], List<Boleto>>> templatesAndBoletos) {

		checkTemplateBoletosEntries(templatesAndBoletos);

		return PdfViewerMultiProcessor.onePerPDF(templatesAndBoletos);
	}
	
	/**
	 * Indica se o PDF do boleto gerado deve ser comprimido completamente ou
	 * não, o padrão é <b>true</b>.
	 * 
	 * <p>
	 * "Full compression" foi introduzido no modelo de documento de PDF 1.5, o
	 * que quer dizer que os boletos gerados por padrão pelo Bopepo estão nesse
	 * modelo a menos que se utilize este método informando o contrário: {@code
	 * #setPdfFullCompression(false)}.
	 * </p>
	 * <p>
	 * Um PDF que seja "fully compressed", só pode ser lido por um visualizador
	 * PDF como Acrobat Reader 6 ou superior. Entretanto, se o documento
	 * não for comprimido [ {@code #setPdfFullCompression(false)} ], o boleto pdf
	 * gerado no modelo de documento PDF 1.4 poderá ser lido por um visualizador
	 * tal qual Acrobat Reader 5 ou superior.
	 * </p>
	 * 
	 * @param option  Escolha de compressão
	 * 
	 * @return Esta instância após a operação
	 */
	public BoletoViewer setPdfFullCompression(boolean option){
		
		pdfViewer.setFullCompression(option);
		
		return this;
	}
	
	/**
	 * Define o template que será utilizado para construir o boleto.
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
	public BoletoViewer setTemplate(byte[] template) {

		checkTemplateFile(template);

		this.pdfViewer.setTemplate(template);

		return this;
	}

	/**
	 * Define o template que será utilizado para construir o boleto.
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
	public BoletoViewer setTemplate(URL templateUrl) {

		checkTemplateFile(templateUrl);

		this.pdfViewer.setTemplate(templateUrl);

		return this;
	}

	/**
	 * Define o template que será utilizado para construir o boleto.
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
	public BoletoViewer setTemplate(InputStream templateInput) {

		checkTemplateFile(templateInput);

		this.pdfViewer.setTemplate(templateInput);

		return this;
	}

	/**
	 * Define o template que será utilizado para construir o boleto.
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
	public BoletoViewer setTemplate(String templatePath) {

		checkTemplatePath(templatePath);

		this.pdfViewer.setTemplate(templatePath);

		return this;
	}

	/**
	 * Define o template que será utilizado para construir o boleto.
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
	public BoletoViewer setTemplate(File templateFile) {

		checkTemplateFile(templateFile);

		this.pdfViewer.setTemplate(templateFile);

		return this;
	}
	
	/**
	 * <p>
	 * Atribui um boleto para uso no visualizador. {@code Null} não é permitido.
	 * </p>
	 * 
	 * @param boleto
	 *            Boleto a ser visualizado
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 */
	public BoletoViewer setBoleto(Boleto boleto) {

		checkBoleto(boleto);

		updateViewerPDF(boleto);

		return this;
	}
	
	/**
	 * <p>
	 * Caso algum template tenha sido utilizado, este método define que após sua
	 * execução o boleto será consturído com o template padrão.
	 * </p>
	 * 
	 * @since 0.2
	 * 
	 * @return Esta instância após a operação
	 */
	public BoletoViewer removeTemplate() {

		final String DEFAULT = null;

		if (isNotNull(pdfViewer)) {
			pdfViewer.setTemplate(DEFAULT);
		}

		return this;
	}
	
	/**
	 * <p>
	 * Retorna o aquivo template utilizado pelo visualizador, que pode ser o
	 * template padrão ou outro.
	 * </p>
	 * 
	 * @return Arquivo template
	 * 
	 * @since 0.2
	 */
	public byte[] getTemplate() {

		return pdfViewer.getTemplate();
	}
	
	/**
	 * <p>
	 * Retorna o boleto usado pelo visualizador
	 * </p>
	 * 
	 * @return o boleto
	 * 
	 * @since 0.2
	 */
	public Boleto getBoleto() {

		return pdfViewer.getBoleto();
	}

	/**
	 * <p>
	 * Retorna o boleto em um arquivo pdf.
	 * </p>
	 * 
	 * @param destPath
	 *            Caminho onde será criado o arquivo pdf
	 * @return Boleo em File
	 * 
	 * @since 0.2
	 */
	public File getPdfAsFile(String destPath) {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + pdfViewer);
		}

		return pdfViewer.getFile(destPath);
	}

	/**
	 * <p>
	 * Retorna o boleto em um arquivo pdf.
	 * </p>
	 * 
	 * @param destFile
	 *            Caminho onde será criado o arquivo pdf
	 * @return Boleto em File
	 * 
	 * @since 0.2
	 */
	public File getPdfAsFile(File destFile) {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + pdfViewer);
		}

		return pdfViewer.getFile(destFile);
	}

	/**
	 * <p>
	 * Retorna o boleto em uma stream de bytes.
	 * </p>
	 * 
	 * @return Boleto em ByteArrayOutputStream
	 * 
	 * @since 0.2
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
	 * @return Boleto em byte[]
	 * 
	 * @since 0.2
	 */
	public byte[] getPdfAsByteArray() {

		if (log.isDebugEnabled()) {
			log.debug("documento instance : " + pdfViewer);
		}

		return pdfViewer.getBytes();
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

		if (isNotNull(this.pdfViewer)) {

			this.pdfViewer = new PdfViewer(boleto, this.pdfViewer.getTemplate());

		} else {

			this.pdfViewer = new PdfViewer(boleto);
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

	private static void checkDestDir(File file) {

		Objects.checkNotNull(file, "Diretório destinado a geração do(s) boleto(s) nulo!");
		Objects.checkArgument(file.isDirectory(), "Isto não é um diretório válido!");
	}

	private static void checkDestFile(File file) {

		Objects.checkNotNull(file, "Arquivo destinado a geração do(s) boleto(s) nulo!");
	}

	private static void checkBoleto(Boleto boleto) {
		
		Objects.checkNotNull(boleto, "Boleto nulo!");
		
	}
	
	private static void checkBoletosList(List<Boleto> boletos) {

		Objects.checkNotNull(boletos, "Lista de boletos nula!");
		Collections.checkNotEmpty(boletos, "A Lista de boletos está vazia!");
	}

	private static void checkTemplateBoletosEntries(Collection<Entry<byte[],List<Boleto>>> templatesAndBoletos) {
		
		Collections.checkNotEmpty(templatesAndBoletos, "A Coleção de pares: (template,boletos) está vazia!");
	}
}
