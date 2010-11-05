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
	 *            - Boleto preenchido
	 */
	public BoletoViewer(Boleto boleto) {

		Objects.checkNotNull(boleto);

		this.pdfViewer = new PdfViewer(boleto);
	}

	/**
	 * <p>
	 * Instancia o visualizador com um template determinado.
	 * </p>
	 * 
	 * @param boleto
	 *            - Boleto preenchido
	 * @param templatePath
	 *            - Template PDF o qual o boleto será gerado
	 */
	public BoletoViewer(Boleto boleto, String templatePath) {

		Objects.checkNotNull(boleto);

		setTemplate(templatePath);

		this.pdfViewer = new PdfViewer(boleto);
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
	public BoletoViewer(Boleto boleto, File templateFile) {

		Objects.checkNotNull(boleto);

		this.pdfViewer = new PdfViewer(boleto);
		
		setTemplate(templateFile);
	}

	/**
	 * Para uso interno do componente
	 */
	BoletoViewer() {

		this.pdfViewer = new PdfViewer();
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param destPath
	 *            - Caminho para o arquivo que armazenará os boletos
	 * 
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, String destPath) {

		checkBoletosList(boletos);
		checkDestPath(destPath);

		return PdfViewer.groupInOnePDF(boletos, new File(destPath),
				new BoletoViewer());
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param destPath
	 *            - Arquivo que armazenará os boletos
	 * 
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, File destFile) {

		checkBoletosList(boletos);
		checkDestFile(destFile);

		return PdfViewer.groupInOnePDF(boletos, destFile, new BoletoViewer());
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param destPath
	 *            - Caminho para o arquivo que armazenará os boletos
	 * @param templatePath
	 *            - Caminho para o arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, String destPath,
			String templatePath) {

		checkBoletosList(boletos);
		checkDestPath(destPath);
		checkTemplatePath(templatePath);

		return PdfViewer.groupInOnePDF(boletos, new File(destPath),
				new BoletoViewer().setTemplate(templatePath));
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param destPath
	 *            - Caminho para o arquivo que armazenará os boletos
	 * @param templateFile
	 *            - Arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, String destPath,
			File templateFile) {

		checkBoletosList(boletos);
		checkDestPath(destPath);
		checkTemplateFile(templateFile);

		return PdfViewer.groupInOnePDF(boletos, new File(destPath),
				new BoletoViewer().setTemplate(templateFile));
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param destFile
	 *            - Arquivo que armazenará os boletos
	 * @param templatePath
	 *            - Caminho para o arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, File destFile,
			String templatePath) {

		checkBoletosList(boletos);
		checkDestFile(destFile);
		checkTemplatePath(templatePath);

		return PdfViewer.groupInOnePDF(boletos, destFile, new BoletoViewer()
				.setTemplate(templatePath));
	}

	/**
	 * <p>
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * </p>
	 * 
	 * 
	 * @param boletos
	 *            - Lista com os boletos a serem agrupados
	 * @param destFile
	 *            - Arquivo que armazenará os boletos
	 * @param templateFile
	 *            - Arquivo com o template para geração
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	public static File groupInOnePDF(List<Boleto> boletos, File destFile,
			File templateFile) {

		checkBoletosList(boletos);
		checkDestFile(destFile);
		checkTemplateFile(templateFile);

		return PdfViewer.groupInOnePDF(boletos, destFile, new BoletoViewer()
				.setTemplate(templateFile));
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
	 *            - Lista com os boletos a serem agrupados
	 * @param destPath
	 *            - Diretório o qual os boletos serão criados
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
	 *            - Lista com os boletos a serem agrupados
	 * @param destDir
	 *            - Diretório o qual os boletos serão criados
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
	 *            - Lista com os boletos a serem agrupados
	 * @param destPath
	 *            - Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            - Prefixo do nome do arquivo
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, String destPath,
			String prefixo) {

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
	 *            - Lista com os boletos a serem agrupados
	 * @param destDir
	 *            - Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            - Prefixo do nome do arquivo
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	public static List<File> onePerPDF(List<Boleto> boletos, File destDir,
			String prefixo) {

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
	 *            - Lista com os boletos a serem agrupados
	 * @param destPath
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
	public static List<File> onePerPDF(List<Boleto> boletos, String destPath,
			String prefixo, String sufixo) {

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
	public static List<File> onePerPDF(List<Boleto> boletos, File destDir,
			String prefixo, String sufixo) {

		checkBoletosList(boletos);
		checkDestDir(destDir);

		List<File> files = new ArrayList<File>(boletos.size());

		files.addAll(PdfViewer.onePerPDF(boletos, destDir, prefixo, sufixo));

		return files;
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

		checkTemplateFile(template);

		this.pdfViewer.setTemplate(template);

		return this;
	}

	/**
	 * @see BoletoViewer#setTemplate(File)
	 * 
	 * @param pathName
	 * 
	 * @since 0.2
	 */
	public BoletoViewer setTemplate(String pathName) {

		checkTemplatePath(pathName);

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

		final String DEFAULT = null;

		if (isNotNull(pdfViewer)) {
			pdfViewer.setTemplate(DEFAULT);
		}

		return this;
	}

	/**
	 * <p>
	 * Retorna o boleto em um arquivo pdf.
	 * </p>
	 * 
	 * @param destPath
	 *            - Caminho onde será criado o arquivo pdf
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
	 *            - Caminho onde será criado o arquivo pdf
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
	 * Atribui um boleto para uso no visualizador. {@code Null} não é permitido.
	 * </p>
	 * 
	 * @param boleto
	 *            - Boleto a ser visualizado
	 * 
	 * @since 0.2
	 */
	public BoletoViewer setBoleto(Boleto boleto) {

		Objects.checkNotNull(boleto);

		updateViewerPDF(boleto);

		return this;
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

		checkString(path,
				"Caminho destinado a geração do(s) arquivo(s) não contém informação!");
	}

	private static void checkTemplatePath(String path) {

		checkString(path, "Caminho do template não contém informação!");
	}

	private static void checkString(String str, String msg) {

		Objects.checkNotNull(str);

		if (StringUtils.isBlank(str)) {

			throw new IllegalArgumentException(msg);
		}
	}

	private static void checkDestDir(File file) {

		Objects.checkNotNull(file,
				"Diretório destinado a geração do(s) boleto(s) nulo!");

		if (!file.isDirectory()) {

			throw new IllegalArgumentException(
					"Isto não é um diretório válido!");
		}
	}

	private static void checkDestFile(File file) {

		Objects.checkNotNull(file,
				"Arquivo destinado a geração do(s) boleto(s) nulo!");
	}

	private static void checkTemplateFile(File file) {

		Objects.checkNotNull(file, "Arquivo de template nulo!");
	}

	private static void checkBoletosList(List<Boleto> boletos) {

		Objects.checkNotNull(boletos, "Lista de boletos nula!");
		Collections.checkNotEmpty(boletos, "A Lista de boletos está vazia!");
	}
}
