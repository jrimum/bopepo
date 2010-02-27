package br.com.nordestefomento.jrimum.bopepo;

import java.util.IllegalFormatException;
import java.util.IllegalFormatFlagsException;

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
	private static final String MSG_STR_VAZIA = "String vazia [\"%s\"] tamanho:%d.";
	private static final String MSG_NAO_FORMATADA = "String formatada [ %s ] fora do padrão [ \"ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd\" ].";
	private static final String MSG_STR_NUMERICA = "String numérica [ %s ] fora do padrão [ \"ddddddddddddddddddddddddddddddddddddddddddddddd\" ]";

	public static void main(String[] args) {

		String s = "39991.23452 67000.000009 00222.268922 3 43710000000965";

		/*
		 * Todos os dados a partir da linha digitával
		 */
		String n = linhaDigitavelFormatadaEmNumerica(s);

		/*
		 * Transformando em código de barras.
		 */
		String c = linhaDigitavelEmCodigoDeBarras(n);

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

	public static String codigoDeBarrasEmLinhaDigitavel(String c) {
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

	public static String linhaDigitavelEmCodigoDeBarras(String n) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>
	 * Remove formatação e espaços de uma linha digitável no formato:
	 * </p>
	 * <p>
	 * "<strong>ddddd.ddddd ddddd.dddddd ddddd.dddddd d dddddddddddddd</strong>"
	 * </p>
	 * 
	 * @param linhaDigitavel
	 * @return linha digitável contendo somente números
	 * @throws NullPointerException
	 *             quando linhaDigitavel é nula
	 * @throws IllegalArgumentException
	 *             quando linhaDigitavel é vazio o tem tamanho diferente
	 * @throws IllegalFormatException
	 */
	public static String linhaDigitavelFormatadaEmNumerica(String linhaDigitavel)
			throws NullPointerException, IllegalArgumentException,
			IllegalFormatException {

		checkFormatoLinhaDigitavelFormatada(linhaDigitavel);

		return linhaDigitavel.replaceAll(" ", StringUtils.EMPTY).replaceAll("\\.", StringUtils.EMPTY);
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

	private static void checkFormatoLinhaDigitavelFormatada(
			String linhaDigitavel) {

		checkExistsLinhaDigitavel(linhaDigitavel);

		if (!linhaDigitavel.contains(".")) {
			throw new IllegalFormatFlagsException(MSG_LINHA_INVALIDA + " "
					+ MSG_NAO_FORMATADA
					+ " A linha digitável formatada deve conter pontos!");
		}

		if (!linhaDigitavel.trim().contains(" ")) {
			throw new IllegalFormatFlagsException(MSG_LINHA_INVALIDA + " "
					+ String.format(MSG_NAO_FORMATADA, linhaDigitavel)
					+ " A linha digitável formatada deve conter espaços!");
		}

		if (!isLinhaDigitavelFormatadaValida(linhaDigitavel)) {
			throw new IllegalFormatFlagsException(MSG_LINHA_INVALIDA + " "
					+ String.format(MSG_NAO_FORMATADA, linhaDigitavel));
		}
	}

	private static void checkFormatoLinhaDigitavelNumerica(String linhaDigitavel) {

		checkExistsLinhaDigitavel(linhaDigitavel);

		if (!isLinhaDigitavelNumericaValida(linhaDigitavel)) {
			throw new IllegalFormatFlagsException(MSG_LINHA_INVALIDA + " "
					+ String.format(MSG_STR_NUMERICA, linhaDigitavel)
					+ " A linha deve conter apenas dígitos de 0 a 9.");
		}
	}

	private static void checkExistsLinhaDigitavel(String linhaDigitavel) {

		ObjectUtil.checkNotNull(linhaDigitavel, MSG_LINHA_INVALIDA);

		if (StringUtils.isBlank(linhaDigitavel)) {
			throw new IllegalArgumentException(MSG_LINHA_INVALIDA + " "
					+ MSG_STR_VAZIA);
		}
	}
}
