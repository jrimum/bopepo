package br.com.nordestefomento.jrimum.bopepo;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.utilix.ObjectUtil;

/**
 * <p>
 * Utilitário para recuperar informações de strings de códigos de barras e
 * linhas digitáveis de boletos.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoUtil {

	/*
	 * Regras REGEX.
	 */
	private static final String LINHA_DIGITAVEL_NUMERICA_REGEX = "\\d{47}";
	private static final String LINHA_DIGITAVEL_FORMATADA_REGEX = "\\d{5}\\.\\d{5} \\d{5}\\.\\d{6} \\d{5}\\.\\d{6} \\d{1} \\d{14}";
	/*
	 * Mensagens.
	 */
	private static final String MSG_LINHA_INVALIDA = "Linha digitável inválida!";
	private static final String MSG_STR_VAZIA = "String vazia [ \"%s\" ] tamanho [ %d ].";
	private static final String MSG_NAO_FORMATADA = "String formatada [ \"%s\" ] de tamanho [ %d ] está fora do padrão [ \"ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd\" ] tamanho = 54.";
	private static final String MSG_STR_NUMERICA = "String numérica [ \"%s\" ] de tamanho [ %d ] está fora do padrão [ \"ddddddddddddddddddddddddddddddddddddddddddddddd\" ] tamanho = 47.";

	public static void main(String[] args) {

		String s = "39991.23452 67000.000009 00222.268922 3 43710000000965";

		/*
		 * Todos os dados a partir da linha digitával
		 */
		String n = linhaDigitavelFormatadaEmNumerica(s);

		/*
		 * Transformando em código de barras.
		 */
		String c = linhaDigitavelNumericaEmCodigoDeBarras(n);

		/*
		 * Recuperando dados a partir do código de barras
		 */
		String banco = getCodigoDoBancoDoCodigoDeBarras(c);

		String codigoMoeda = getCodigoDaMoedaDoCodigoDeBarras(c);

		String fatorDeVencimento = getFatorDeVencimentoDoCodigoDeBarras(c);

		String valor = getValorDoTituloDoCodigoDeBarras(c);

		String campoLivre = getCampoLivreDoCodigoDeBarras(c);

		/*
		 * Transformando código de barras em linha digitával formatada.
		 */
		String l = codigoDeBarrasEmLinhaDigitavel(c);

		/*
		 * Recuperando dados a partir da linha digitável
		 */
		banco = getCodigoDoBancoDaLinhaDigitavel(l);

		codigoMoeda = getCodigoDaMoedaDaLinhaDigitavel(l);

		fatorDeVencimento = getFatorDeVencimentoDaLinhaDigitavel(l);

		valor = getValorDoTituloDaLinhaDigitavel(l);

		campoLivre = getCampoLivreDaLinhaDigitavel(l);

	}

	public static String getCampoLivreDaLinhaDigitavel(String l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getValorDoTituloDaLinhaDigitavel(String l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getFatorDeVencimentoDaLinhaDigitavel(String l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getCodigoDaMoedaDaLinhaDigitavel(String l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getCodigoDoBancoDaLinhaDigitavel(String l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getCampoLivreDoCodigoDeBarras(String c) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getValorDoTituloDoCodigoDeBarras(String c) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getFatorDeVencimentoDoCodigoDeBarras(String c) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getCodigoDaMoedaDoCodigoDeBarras(String c) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getCodigoDoBancoDoCodigoDeBarras(String c) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String codigoDeBarrasEmLinhaDigitavel(String c) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String linhaDigitavelFormatadaEmCodigoDeBarras(
			String linhaDigitavel) {

		return linhaDigitavelNumericaEmCodigoDeBarras(linhaDigitavelFormatadaEmNumerica(linhaDigitavel));
	}

	public static String linhaDigitavelNumericaEmCodigoDeBarras(
			String linhaDigitavel) {

		checkFormatoLinhaDigitavelNumerica(linhaDigitavel);

		// TODO IMPL

		return null;
	}

	/**
	 * <p>
	 * Remove formatação e espaços de uma linha digitável no formato FEBRABAN:
	 * </p>
	 * <p>
	 * "<strong>ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd</strong>"
	 * </p>
	 * 
	 * @see #checkExistsLinhaDigitavel(String)
	 * @see #checkFormatoLinhaDigitavelFormatada(String)
	 * 
	 * @param linhaDigitavel
	 *            string no formato FEBRABAN
	 * @return linha digitável contendo somente números
	 * @throws NullPointerException
	 *             quando a string é nula
	 * @throws IllegalArgumentException
	 *             quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 */
	public static String linhaDigitavelFormatadaEmNumerica(String linhaDigitavel)
			throws NullPointerException, IllegalArgumentException,
			LinhaDigitavelException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavel.replaceAll(" ", StringUtils.EMPTY).replaceAll(
				"\\.", StringUtils.EMPTY);
	}

	public static boolean isLinhaDigitavelFormatadaValida(String linhaDigitavel) {

		if (StringUtils.isNotBlank(linhaDigitavel)) {

			return linhaDigitavel.trim().matches(
					LINHA_DIGITAVEL_FORMATADA_REGEX);
		} else {

			return false;
		}
	}

	public static boolean isLinhaDigitavelNumericaValida(String linhaDigitavel) {

		if (StringUtils.isNotBlank(linhaDigitavel)) {

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
	 * <li>Caso a string seja nula, lança uma <code>NullPointerException</code>;
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
	 * @return linha digitável contendo somente números
	 * @throws NullPointerException
	 *             quando a string é nula
	 * @throws IllegalArgumentException
	 *             quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 * 
	 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
	 */
	public static void checkFormatoLinhaDigitavelFormatada(String linhaDigitavel)
			throws NullPointerException, IllegalArgumentException,
			LinhaDigitavelException {

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
	 * <li>Caso a string seja nula, lança uma <code>NullPointerException</code>;
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
	 * @throws NullPointerException
	 *             quando a string é nula
	 * @throws IllegalArgumentException
	 *             quando a string é vazia
	 * @throws LinhaDigitavelException
	 *             quando a string não está no formato válido
	 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
	 */
	public static void checkFormatoLinhaDigitavelNumerica(String linhaDigitavel)
			throws NullPointerException, IllegalArgumentException,
			LinhaDigitavelException {

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
	 * Verifica se a linha digitável <strong>não é nula</strong> e <strong>não é
	 * vazia</strong>. Caso nula lança uma <code>NullPointerException</code>,
	 * caso vazia lança uma <code>IllegalArgumentException</code>.
	 * </p>
	 * <p>
	 * Considera-se vazia se <code>linhaDigitavel.trim().length()</code> == 0.
	 * </p>
	 * 
	 * @param linhaDigitavel
	 * @throws NullPointerException
	 *             quando a string é nula
	 * @throws IllegalArgumentException
	 *             quando a string é vazia
	 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
	 */
	public static void checkExistsLinhaDigitavel(String linhaDigitavel)
			throws NullPointerException, IllegalArgumentException {

		ObjectUtil.checkNotNull(linhaDigitavel, MSG_LINHA_INVALIDA);

		if (StringUtils.isBlank(linhaDigitavel)) {
			throw new IllegalArgumentException(MSG_LINHA_INVALIDA
					+ " "
					+ String.format(MSG_STR_VAZIA, linhaDigitavel,
							linhaDigitavel.length()));
		}
	}
}
