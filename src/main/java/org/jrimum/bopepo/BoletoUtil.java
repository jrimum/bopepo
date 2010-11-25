/* 
 * Copyright 2010 JRimum Project
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
 * Created at: 27/01/2010 - 00:53:43
 *
 * ================================================================================
 *
 * Direitos autorais 2010 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 27/01/2010 - 00:53:43
 * 
 */


package org.jrimum.bopepo;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.trim;
import static org.jrimum.utilix.Objects.checkNotNull;
import static org.jrimum.utilix.text.Strings.WHITE_SPACE;

import org.jrimum.vallia.digitoverificador.BoletoLinhaDigitavelDV;

/**
 * <p>
 * Utilitário para recuperar informações de strings de códigos de barras e
 * linhas digitáveis de boletos.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public final class BoletoUtil {

	/*
	 * Regras REGEX.
	 */
	private static final String CODIGO_DE_BARRAS_REGEX = "\\d{44}";
	private static final String LINHA_DIGITAVEL_NUMERICA_REGEX = "\\d{47}";
	private static final String LINHA_DIGITAVEL_FORMATADA_REGEX = "\\d{5}\\.\\d{5} \\d{5}\\.\\d{6} \\d{5}\\.\\d{6} \\d{1} \\d{14}";
	/*
	 * Mensagens.
	 */
	private static final String MSG_CODIGO_DE_BARRAS = "Código de barras inválido!";
	private static final String MSG_LINHA_INVALIDA = "Linha digitável inválida!";
	private static final String MSG_STR_VAZIA = "String vazia [ \"%s\" ] tamanho [ %d ].";
	private static final String MSG_NAO_FORMATADA = "String formatada [ \"%s\" ] de tamanho [ %d ] está fora do padrão [ \"ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd\" ] tamanho = 54.";
	private static final String MSG_STR_NUMERICA = "String numérica [ \"%s\" ] de tamanho [ %d ] está fora do padrão [ \"ddddddddddddddddddddddddddddddddddddddddddddddd\" ] tamanho = 47.";

	/**
	 * Utility class pattern: classe não instanciável
	 * 
	 * @throws AssertionError
	 *             caso haja alguma tentativa de utilização deste construtor.
	 */
	private BoletoUtil(){

		throw new AssertionError("NOT SUPORTED OPERATION!");
	}
	
	/**
	 * <p>
	 * Retorna o campo livre de uma dada linha digitável.
	 * </p>
	 * 
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String getCampoLivreDaLinhaDigitavelFormatada(
			String linhaDigitavel) throws 
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		final String linhaNumerica = linhaDigitavelFormatadaEmNumerica(linhaDigitavel);

		return new StringBuilder().append(linhaNumerica.substring(4, 9))
				.append(linhaNumerica.substring(10, 20)).append(
						linhaNumerica.substring(21, 31)).toString();
	}

	/**
	 * <p>
	 * Retorna o valor do título de uma dada linha digitável.
	 * </p>
	 * 
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String getValorDoTituloDaLinhaDigitavelFormatada(
			String linhaDigitavel) throws 
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavelFormatadaEmNumerica(linhaDigitavel).substring(37,
				47);
	}

	/**
	 * <p>
	 * Retorna o fator de vencimento de uma dada linha digitável.
	 * </p>
	 * 
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String getFatorDeVencimentoDaLinhaDigitavelFormatada(
			String linhaDigitavel) throws 
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavelFormatadaEmNumerica(linhaDigitavel).substring(33,
				37);
	}

	/**
	 * <p>
	 * Retorna o dígito verificador geral de uma dada linha digitável.
	 * </p>
	 * 
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String getDigitoVerificadorGeralDaLinhaDigitavelFormatada(
			String linhaDigitavel) throws 
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavelFormatadaEmNumerica(linhaDigitavel).substring(32,
				33);
	}

	/**
	 * <p>
	 * Retorna o código da moeda de uma dada linha digitável.
	 * </p>
	 * 
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String getCodigoDaMoedaDaLinhaDigitavelFormatada(
			String linhaDigitavel) throws 
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavelFormatadaEmNumerica(linhaDigitavel)
				.substring(3, 4);
	}

	/**
	 * <p>
	 * Retorna o código do banco de uma dada linha digitável.
	 * </p>
	 * 
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String getCodigoDoBancoDaLinhaDigitavelFormatada(
			String linhaDigitavel) throws 
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavelFormatadaEmNumerica(linhaDigitavel)
				.substring(0, 3);
	}

	/**
	 * <p>
	 * Retorna o campo livre de um dado código de barras.
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *            quando a string é nula ou  quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String getCampoLivreDoCodigoDeBarras(
			String codigoDeBarras) throws 
			IllegalArgumentException, CodigoDeBarrasException {

		checkFormatoCodigoDeBarras(codigoDeBarras);

		return trim(codigoDeBarras).substring(19, 44);
	}

	/**
	 * <p>
	 * Retorna o valor do título de um dado código de barras.
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String getValorDoTituloDoCodigoDeBarras(
			String codigoDeBarras) throws 
			IllegalArgumentException, CodigoDeBarrasException {

		checkFormatoCodigoDeBarras(codigoDeBarras);

		return trim(codigoDeBarras).substring(9, 19);
	}

	/**
	 * <p>
	 * Retorna o fator de vencimento de um dado código de barras.
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String getFatorDeVencimentoDoCodigoDeBarras(
			String codigoDeBarras) throws 
			IllegalArgumentException, CodigoDeBarrasException {

		checkFormatoCodigoDeBarras(codigoDeBarras);

		return trim(codigoDeBarras).substring(5, 9);
	}

	/**
	 * <p>
	 * Retorna o dígito verificador de um dado código de barras.
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String getDigitoVerificadorGeralDoCodigoDeBarras(
			String codigoDeBarras) throws 
			IllegalArgumentException, CodigoDeBarrasException {

		checkFormatoCodigoDeBarras(codigoDeBarras);

		return trim(codigoDeBarras).substring(4, 5);
	}

	/**
	 * <p>
	 * Retorna o código da moeda de um dado código de barras.
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String getCodigoDaMoedaDoCodigoDeBarras(
			String codigoDeBarras) throws 
			IllegalArgumentException, CodigoDeBarrasException {

		checkFormatoCodigoDeBarras(codigoDeBarras);

		return trim(codigoDeBarras).substring(3, 4);
	}

	/**
	 * <p>
	 * Retorna o código do banco de um dado código de barras.
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return código do banco em string
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String getCodigoDoBancoDoCodigoDeBarras(
			String codigoDeBarras) throws 
			IllegalArgumentException, CodigoDeBarrasException {

		checkFormatoCodigoDeBarras(codigoDeBarras);

		return trim(codigoDeBarras).substring(0, 3);
	}

	/**
	 * <p>
	 * Transforma um código de barras em uma linha digitável no formato
	 * FEBRABAN.
	 * </p>
	 * <p>
	 * "<strong>dddddddddddddddddddddddddddddddddddddddddddd</strong>" <br />
	 * <b>&rArr;</b> <br />
	 * "<strong>ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd</strong>"
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return linha digitável contendo somente números
	 * @throws IllegalArgumentException
	 *              quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String codigoDeBarrasEmLinhaDigitavelFormatada(
			String codigoDeBarras) throws
			IllegalArgumentException, CodigoDeBarrasException {

		return linhaDigitavelNumericaEmFormatada(codigoDeBarrasEmLinhaDigitavelNumerica(codigoDeBarras));
	}

	/**
	 * <p>
	 * Transforma um código de barras em uma linha digitável numérica.
	 * </p>
	 * <p>
	 * "<strong>dddddddddddddddddddddddddddddddddddddddddddd</strong>" <br />
	 * <b>&rArr;</b> <br />
	 * "<strong>ddddddddddddddddddddddddddddddddddddddddddddddd</strong>"
	 * </p>
	 * 
	 * @see #checkFormatoCodigoDeBarras(String)
	 * 
	 * @param codigoDeBarras
	 *            string contendo somente números
	 * @return linha digitável contendo somente números
	 *            
	 * @throws IllegalArgumentException
	 *            quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static String codigoDeBarrasEmLinhaDigitavelNumerica(
			String codigoDeBarras) throws
			IllegalArgumentException, CodigoDeBarrasException {

		checkFormatoCodigoDeBarras(codigoDeBarras);

		final BoletoLinhaDigitavelDV calculadorDV = new BoletoLinhaDigitavelDV();

		final StringBuilder linhaDigitavel = new StringBuilder();

		final String c = trim(codigoDeBarras);

		// campo1
		// banco
		linhaDigitavel.append(c.substring(0, 3));
		// moeda
		linhaDigitavel.append(c.substring(3, 4));
		linhaDigitavel.append(c.substring(19, 24));
		linhaDigitavel.append(calculadorDV.calcule(linhaDigitavel.toString()));

		// campo2
		linhaDigitavel.append(c.substring(24, 34));
		linhaDigitavel.append(calculadorDV.calcule(c.substring(24, 34)));

		// campo3
		linhaDigitavel.append(c.substring(34, 44));
		linhaDigitavel.append(calculadorDV.calcule(c.substring(34, 44)));

		// campo4=DV_Geral
		linhaDigitavel.append(c.substring(4, 5));

		// campo5
		linhaDigitavel.append(c.substring(5, 19));

		return linhaDigitavel.toString();
	}

	/**
	 * <p>
	 * Transforma uma linha digitável no formato FEBRABAN em um código de
	 * barras.
	 * </p>
	 * <p>
	 * "<strong>ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd</strong>"
	 * <br />
	 * <b>&rArr;</b> <br />
	 * "<strong>dddddddddddddddddddddddddddddddddddddddddddd</strong>"
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return linha digitável contendo somente números
	 * @throws IllegalArgumentException
	 *              quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String linhaDigitavelFormatadaEmCodigoDeBarras(
			String linhaDigitavel) throws
			IllegalArgumentException, LinhaDigitavelException {

		return linhaDigitavelNumericaEmCodigoDeBarras(linhaDigitavelFormatadaEmNumerica(linhaDigitavel));
	}

	/**
	 * <p>
	 * Transforma uma linha digitável não formatada em um código de barras.
	 * </p>
	 * <p>
	 * "<strong>ddddddddddddddddddddddddddddddddddddddddddddddd</strong>" <br />
	 * <b>&rArr;</b> <br />
	 * "<strong>dddddddddddddddddddddddddddddddddddddddddddd</strong>"
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return linha digitável contendo somente números
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String linhaDigitavelNumericaEmCodigoDeBarras(
			String linhaDigitavel) throws
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelNumerica(linhaDigitavel);

		final StringBuilder codigoDeBarras = new StringBuilder();

		final String l = trim(linhaDigitavel);

		// banco
		codigoDeBarras.append(l.substring(0, 3));
		// moeda
		codigoDeBarras.append(l.substring(3, 4));

		codigoDeBarras.append(l.substring(32, 33));
		codigoDeBarras.append(l.substring(33, 47));
		codigoDeBarras.append(l.substring(4, 9));
		codigoDeBarras.append(l.substring(10, 20));
		codigoDeBarras.append(l.substring(21, 31));

		return codigoDeBarras.toString();
	}

	/**
	 * <p>
	 * Remove formatação e espaços de uma linha digitável no formato FEBRABAN:
	 * </p>
	 * <p>
	 * "<strong>ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd</strong>"
	 * <br />
	 * <b>&rArr;</b> <br />
	 * "<strong>ddddddddddddddddddddddddddddddddddddddddddddddd</strong>"
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return linha digitável contendo somente números
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String linhaDigitavelFormatadaEmNumerica(
			String linhaDigitavel) throws IllegalArgumentException,
			LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavel.replaceAll(WHITE_SPACE, EMPTY).replaceAll("\\.",
				EMPTY);
	}

	/**
	 * <p>
	 * Transforma linha digitável númerica em FEBRABAN:
	 * </p>
	 * <p>
	 * "<strong>ddddddddddddddddddddddddddddddddddddddddddddddd</strong>" <br />
	 * <b>&rArr;</b> <br />
	 * "<strong>ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd</strong>"
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * @see #checkFormatoLinhaDigitavelNumerica(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return linha digitável contendo somente números
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String linhaDigitavelNumericaEmFormatada(
			String linhaDigitavel) throws
			IllegalArgumentException, LinhaDigitavelException {

		checkFormatoLinhaDigitavelNumerica(linhaDigitavel);

		final StringBuilder linhaFormatada = new StringBuilder();

		final String l = trim(linhaDigitavel);

		linhaFormatada.append(l.substring(0, 5));
		linhaFormatada.append(".");
		linhaFormatada.append(l.substring(5, 10));
		linhaFormatada.append(WHITE_SPACE);
		linhaFormatada.append(l.substring(10, 15));
		linhaFormatada.append(".");
		linhaFormatada.append(l.substring(15, 21));
		linhaFormatada.append(WHITE_SPACE);
		linhaFormatada.append(l.substring(21, 26));
		linhaFormatada.append(".");
		linhaFormatada.append(l.substring(26, 32));
		linhaFormatada.append(WHITE_SPACE);
		linhaFormatada.append(l.substring(32, 33));
		linhaFormatada.append(WHITE_SPACE);
		linhaFormatada.append(l.substring(33));

		return linhaFormatada.toString();
	}

	/**
	 * <p>
	 * Informa se um dado código de barras é válido ou não.
	 * </p>
	 * 
	 * @param codigoDeBarras
	 * @return true = valida
	 */
	public static boolean isCodigoDeBarrasValido(String codigoDeBarras) {

		if (isNotBlank(codigoDeBarras)) {

			return codigoDeBarras.trim().matches(CODIGO_DE_BARRAS_REGEX);
		} else {

			return false;
		}
	}

	/**
	 * <p>
	 * Informa se uma dada linha digitável formatada é válida ou não.
	 * </p>
	 * 
	 * @param linhaDigitavel
	 * @return true = valida
	 */
	public static boolean isLinhaDigitavelFormatadaValida(
			String linhaDigitavel) {

		if (isNotBlank(linhaDigitavel)) {

			return linhaDigitavel.trim().matches(
					LINHA_DIGITAVEL_FORMATADA_REGEX);
		} else {

			return false;
		}
	}

	/**
	 * <p>
	 * Informa se uma dada linha digitável é válida ou não.
	 * </p>
	 * 
	 * @param linhaDigitavel
	 * @return true = valida
	 */
	public static boolean isLinhaDigitavelNumericaValida(
			String linhaDigitavel) {

		if (isNotBlank(linhaDigitavel)) {

			return linhaDigitavel.trim()
					.matches(LINHA_DIGITAVEL_NUMERICA_REGEX);
		} else {

			return false;
		}
	}

	/**
	 * <p>
	 * Verifica se a linha digitável <strong>não é nula</strong>, <strong>não é
	 * vazia</strong> e <strong>é numérica</strong>, obedecendo o seguinte
	 * FEBRABAN:
	 * </p>
	 * <p>
	 * "<strong>ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd</strong>"
	 * </p>
	 * <p>
	 * Onde o número de dígitos é igual a 54 e o número de espaços é igual a 4.
	 * </p>
	 * <p>
	 * <ul>
	 * <li>Caso a string seja nula, lança uma <code>IllegalArgumentException</code>;
	 * </li>
	 * <li>Caso seja vazia, lança uma <code>IllegalArgumentException</code>;</li>
	 * <li>Caso não esteja no formato especificado, lança uma
	 * <code>LinhaDigitavelException</code>.</li>
	 * </ul>
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @throws IllegalArgumentException
	 *              quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static void checkFormatoLinhaDigitavelFormatada(
			String linhaDigitavel) throws
			IllegalArgumentException, LinhaDigitavelException {

		checkExistsLinhaDigitavel(linhaDigitavel);

		if (!linhaDigitavel.contains(".")) {
			throw new LinhaDigitavelException(MSG_LINHA_INVALIDA
					+ " "
					+ String.format(MSG_NAO_FORMATADA, linhaDigitavel,
							linhaDigitavel.length())
					+ " A linha digitável formatada deve conter pontos!");
		}

		if (!linhaDigitavel.trim().contains(" ")) {
			throw new LinhaDigitavelException(MSG_LINHA_INVALIDA
					+ " "
					+ String.format(MSG_NAO_FORMATADA, linhaDigitavel,
							linhaDigitavel.length())
					+ " A linha digitável formatada deve conter espaços!");
		}

		if (!isLinhaDigitavelFormatadaValida(linhaDigitavel)) {
			throw new LinhaDigitavelException(MSG_LINHA_INVALIDA
					+ " "
					+ String.format(MSG_NAO_FORMATADA, linhaDigitavel,
							linhaDigitavel.length()));
		}
	}

	/**
	 * <p>
	 * Verifica se a linha digitável <strong>não é nula</strong>, <strong>não é
	 * vazia</strong> e <strong>é numérica</strong>, obedecendo o seguinte
	 * formato:
	 *</p>
	 * <p>
	 * "<strong>ddddddddddddddddddddddddddddddddddddddddddddddd</strong>"
	 * </p>
	 * <p>
	 * Onde o número de dígitos é igual a 47.
	 * </p>
	 * <p>
	 * <ul>
	 * <li>Caso a string seja nula, lança uma <code>IllegalArgumentException</code>;
	 * </li>
	 * <li>Caso seja vazia, lança uma <code>IllegalArgumentException</code>;</li>
	 * <li>Caso não esteja no formato especificado, lança uma
	 * <code>LinhaDigitavelException</code>.</li>
	 * </ul>
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * 
	 * @param linhaDigitavel
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static void checkFormatoLinhaDigitavelNumerica(
			String linhaDigitavel) throws
			IllegalArgumentException, LinhaDigitavelException {

		checkExistsLinhaDigitavel(linhaDigitavel);

		if (!isLinhaDigitavelNumericaValida(linhaDigitavel)) {
			throw new LinhaDigitavelException(MSG_LINHA_INVALIDA
					+ " "
					+ String.format(MSG_STR_NUMERICA, linhaDigitavel,
							linhaDigitavel.length())
					+ " A linha deve conter apenas 47 dígitos númericos [0-9]!");
		}

	}

	/**
	 * <p>
	 * Verifica se o código de barras <strong>não é nulo</strong>, <strong>não é
	 * vazio</strong> e <strong>é numérico</strong>, obedecendo o seguinte
	 * formato:
	 *</p>
	 * <p>
	 * "<strong>dddddddddddddddddddddddddddddddddddddddddddd</strong>"
	 * </p>
	 * <p>
	 * Onde o número de dígitos é igual a 44.
	 * </p>
	 * <p>
	 * <ul>
	 * <li>Caso a string seja nula, lança uma <code>IllegalArgumentException</code>;
	 * </li>
	 * <li>Caso seja vazia, lança uma <code>IllegalArgumentException</code>;</li>
	 * <li>Caso não esteja no formato especificado, lança uma
	 * <code>LinhaDigitavelException</code>.</li>
	 * </ul>
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * 
	 * @param codigoDeBarras
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 * @throws CodigoDeBarrasException
	 *             quando a string não está no formato válido
	 */
	public static void checkFormatoCodigoDeBarras(String codigoDeBarras)
			throws IllegalArgumentException,
			CodigoDeBarrasException {

		checkExistsCodigoDeBarras(codigoDeBarras);

		if (!isCodigoDeBarrasValido(codigoDeBarras)) {
			throw new CodigoDeBarrasException(
					MSG_CODIGO_DE_BARRAS
							+ " "
							+ String.format(MSG_STR_NUMERICA, codigoDeBarras,
									codigoDeBarras.length())
							+ " O código de barras deve conter apenas 44 dígitos númericos [0-9]!");
		}

	}

	/**
	 * <p>
	 * Verifica se a linha digitável <strong>não é nula</strong> e <strong>não é
	 * vazia</strong>. Caso nula lança uma <code>IllegalArgumentException</code>,
	 * caso vazia lança uma <code>IllegalArgumentException</code>.
	 * </p>
	 * <p>
	 * Considera-se vazia se <code>linhaDigitavel.trim().length()</code> == 0.
	 * </p>
	 * 
	 * @param linhaDigitavel
	 * 
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 */
	public static void checkExistsLinhaDigitavel(String linhaDigitavel)
			throws IllegalArgumentException {

		checkNotNull(linhaDigitavel, MSG_LINHA_INVALIDA);

		if (isBlank(linhaDigitavel)) {
			throw new IllegalArgumentException(MSG_LINHA_INVALIDA
					+ " "
					+ String.format(MSG_STR_VAZIA, linhaDigitavel,
							linhaDigitavel.length()));
		}
	}

	/**
	 * <p>
	 * Verifica se código de barras <strong>não é nulo</strong> e <strong>não é
	 * vazio</strong>. Caso nula lança uma <code>IllegalArgumentException</code>,
	 * caso vazia lança uma <code>IllegalArgumentException</code>.
	 * </p>
	 * <p>
	 * Considera-se vazia se <code>linhaDigitavel.trim().length()</code> == 0.
	 * </p>
	 * 
	 * @param codigoDeBarras
	 * @throws IllegalArgumentException
	 *             quando a string é nula ou quando a string é vazia
	 */
	public static void checkExistsCodigoDeBarras(String codigoDeBarras)
			throws IllegalArgumentException {

		checkNotNull(codigoDeBarras, MSG_LINHA_INVALIDA);

		if (isBlank(codigoDeBarras)) {
			throw new IllegalArgumentException(MSG_CODIGO_DE_BARRAS
					+ " "
					+ String.format(MSG_STR_VAZIA, codigoDeBarras,
							codigoDeBarras.length()));
		}
	}
}
