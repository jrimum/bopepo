package br.com.nordestefomento.jrimum.bopepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBopepoUtil {

	private static String LINHA_DIGITAVEL_FORMATADA_EXPECTED = "39991.23452 67000.000009 00222.268922 3 43710000000965";
	private static String LINHA_DIGITAVEL_NUMERICA_EXPECTED = "39991234526700000000900222268922343710000000965";

	private static final ArrayList<String> INPUTS_LINHAS_DIGITAVEIS_FORMATADAS = new ArrayList<String>();
	private static final ArrayList<String> INPUTS_LINHAS_DIGITAVEIS_NUMERICAS = new ArrayList<String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991.23452 67000.000009 00222.268922 3 43710000000 965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991.23452 67000.000009 00222.268922 3 4371000000096");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991.23452 67000.000009 00222.268922 $ 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991.23452 67000.000009 00222.26892 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991.23452 67000.00000 00222.268922 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991.2345 67000.000009 00222.268922 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add(0 + LINHA_DIGITAVEL_FORMATADA_EXPECTED);
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add(LINHA_DIGITAVEL_FORMATADA_EXPECTED + 0);
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991.2345267000.00000900222.268922343710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991234526700000000900222268922343710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991$23452 67000$000009 00222$268922 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS
				.add("39991#23452 67000#000009 00222#268922 3 43710000000965");

		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add("000000000000000000000000000000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add("0000000000000000000000000000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add("000000000000000000000000x0000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add("000000000000000000000000 0000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add(0 + LINHA_DIGITAVEL_NUMERICA_EXPECTED);
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add(0 + LINHA_DIGITAVEL_NUMERICA_EXPECTED + 0);
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add(LINHA_DIGITAVEL_NUMERICA_EXPECTED + 0);
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add(" 000000000000000000000000x0000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add(" 000000000000000000000000x0000000000000000000000 ");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS
				.add("000000000000000000000000x0000000000000000000000 ");
	}

	@Test
	public void testGetCampoLivreDaLinhaDigitavel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValorDoTituloDaLinhaDigitavel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFatorDeVencimentoDaLinhaDigitavel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCodigoDaMoedaDaLinhaDigitavel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCodigoDoBancoDaLinhaDigitavel() {
		fail("Not yet implemented");
	}

	@Test
	public void testCodigoDeBarrasEmLinhaDigitavel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCampoLivreDoCodigoDeBarras() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValorDoTituloDoCodigoDeBarras() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFatorDeVencimentoDoCodigoDeBarras() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCodigoDaMoedaDoCodigoDeBarras() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCodigoDoBancoDoCodigoDeBarras() {
		fail("Not yet implemented");
	}

	@Test
	public void testLinhaDigitavelFormatadaEmCodigoDeBarras() {

		fail("Not yet implemented");
	}

	@Test
	public void testLinhaDigitavelNumericaEmCodigoDeBarras() {

		fail("Not yet implemented");
	}

	@Test
	public void testIsLinhaDigitavelFormatadaValida() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsLinhaDigitavelNumericaValida() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckFormatoLinhaDigitavelFormatada() {

		for (String inputTest : INPUTS_LINHAS_DIGITAVEIS_FORMATADAS) {
			try {
				BoletoUtil.checkFormatoLinhaDigitavelFormatada(inputTest);
				fail("Controle de formatação falho.");
			} catch (LinhaDigitavelException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
		}
	}

	@Test
	public void testCheckFormatoLinhaDigitavelNumerica() {
		for (String inputTest : INPUTS_LINHAS_DIGITAVEIS_NUMERICAS) {
			try {
				BoletoUtil.checkFormatoLinhaDigitavelNumerica(inputTest);
				fail("Controle de formatação falho.");
			} catch (LinhaDigitavelException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
		}
	}

	@Test(expected = NullPointerException.class)
	public void testCheckExistsLinhaDigitavelComArgumentoStringNull() {

		BoletoUtil.checkExistsLinhaDigitavel(null);
	}

	@Test
	public void testCheckExistsLinhaDigitavelComArgumentoStringVazio() {

		StringBuilder emptyInput = new StringBuilder("");

		for (int i = 0; i <= 60; i++) {
			try {
				BoletoUtil.checkExistsLinhaDigitavel(emptyInput.toString());
				fail("Controle de espaços falho.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
			emptyInput.append(" ");
		}
	}

	@Test(expected = NullPointerException.class)
	public void testLinhaDigitavelFormatadaEmNumericaComArgumentoNull() {

		BoletoUtil.linhaDigitavelFormatadaEmNumerica(null);
	}

	@Test
	public void testLinhaDigitavelFormatadaEmNumericaComArgumentoStringVazio() {

		StringBuilder emptyInput = new StringBuilder("");

		for (int i = 0; i <= 60; i++) {
			try {
				BoletoUtil.linhaDigitavelFormatadaEmNumerica(emptyInput
						.toString());
				fail("Controle de espaços falho.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
			emptyInput.append(" ");
		}
	}

	@Test
	public void testLinhaDigitavelFormatadaEmNumericaComArgumentoStringDeFormatoNaoPrevisto() {

		for (String inputTest : INPUTS_LINHAS_DIGITAVEIS_FORMATADAS) {
			try {
				BoletoUtil.linhaDigitavelFormatadaEmNumerica(inputTest);
				fail("Controle de formatação falho.");
			} catch (LinhaDigitavelException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
		}
	}

	@Test
	public void testLinhaDigitavelFormatadaEmNumerica() {

		assertEquals(
				LINHA_DIGITAVEL_NUMERICA_EXPECTED,
				BoletoUtil
						.linhaDigitavelFormatadaEmNumerica(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
	}
}
