package org.jrimum.bopepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBoletoUtil {

	private static final String CODIGO_DO_BANCO_EXPECTED = "399";
	
	private static final String CODIGO_DA_MOEDA_EXPECTED = "9";

	private static final String CODIGO_DV_GERAL_EXPECTED = "3";

	private static final String FATOR_DE_VENCIMENTO_EXPECTED = "4371";
	
	private static final String VALOR_NOMINAL_EXPECTED = "0000000965";
	
	private static final String CAMPO_LIVRE_EXPECTED = "1234567000000000022226892";

	private static final String CODIGO_DE_BARRAS_EXPECTED = new StringBuilder()
			.append(CODIGO_DO_BANCO_EXPECTED)
			.append(CODIGO_DA_MOEDA_EXPECTED)
			.append(CODIGO_DV_GERAL_EXPECTED)
			.append(FATOR_DE_VENCIMENTO_EXPECTED)
			.append(VALOR_NOMINAL_EXPECTED)
			.append(CAMPO_LIVRE_EXPECTED)
			.toString();

	private static String LINHA_DIGITAVEL_FORMATADA_EXPECTED = "39991.23452 67000.000009 00222.268922 3 43710000000965";
	private static String LINHA_DIGITAVEL_NUMERICA_EXPECTED = "39991234526700000000900222268922343710000000965";

	private static final ArrayList<String> INPUTS_CODIGO_DE_BARRAS = new ArrayList<String>();
	
	private static final ArrayList<String> INPUTS_LINHAS_DIGITAVEIS_FORMATADAS = new ArrayList<String>();
	private static final ArrayList<String> INPUTS_LINHAS_DIGITAVEIS_NUMERICAS = new ArrayList<String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		/*
		 * ENTRADAS FORMATADAS
		 */
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991.23452 67000.000009 00222.268922 3 43710000000 965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991.23452 67000.000009 00222.268922 3 4371000000096");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991.23452 67000.000009 00222.268922 $ 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991.23452 67000.000009 00222.26892 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991.23452 67000.00000 00222.268922 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991.2345 67000.000009 00222.268922 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add(0 + LINHA_DIGITAVEL_FORMATADA_EXPECTED);
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add(LINHA_DIGITAVEL_FORMATADA_EXPECTED + 0);
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991.2345267000.00000900222.268922343710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991234526700000000900222268922343710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991$23452 67000$000009 00222$268922 3 43710000000965");
		INPUTS_LINHAS_DIGITAVEIS_FORMATADAS.add("39991#23452 67000#000009 00222#268922 3 43710000000965");
		/*
		 * ENTRADAS NÃO FORMATADAS
		 */
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add("000000000000000000000000000000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add("0000000000000000000000000000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add("000000000000000000000000x0000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add("000000000000000000000000 0000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add(0 + LINHA_DIGITAVEL_NUMERICA_EXPECTED);
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add(0 + LINHA_DIGITAVEL_NUMERICA_EXPECTED + 0);
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add(LINHA_DIGITAVEL_NUMERICA_EXPECTED + 0);
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add(" 000000000000000000000000x0000000000000000000000");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add(" 000000000000000000000000x0000000000000000000000 ");
		INPUTS_LINHAS_DIGITAVEIS_NUMERICAS.add("000000000000000000000000x0000000000000000000000 ");
		/*
		 * ENTRADAS DE CÓDIGOS DE BARRAS
		 */
		INPUTS_CODIGO_DE_BARRAS.add("000000000000000000000000000000000000000000000000");
		INPUTS_CODIGO_DE_BARRAS.add("0000000000000000000000000000000000000000000000");
		INPUTS_CODIGO_DE_BARRAS.add("000000000000000000000009x9000000000000000000000");
		INPUTS_CODIGO_DE_BARRAS.add("000000000000000000000009 9000000000000000000000");
		INPUTS_CODIGO_DE_BARRAS.add(1 + CODIGO_DE_BARRAS_EXPECTED);
		INPUTS_CODIGO_DE_BARRAS.add(1 + CODIGO_DE_BARRAS_EXPECTED + 2);
		INPUTS_CODIGO_DE_BARRAS.add(CODIGO_DE_BARRAS_EXPECTED + 9);
		INPUTS_CODIGO_DE_BARRAS.add(" 100000000000000000000009x9000000000000000000000");
		INPUTS_CODIGO_DE_BARRAS.add(" 100000000000000000000009x9000000000000000000002 ");
		INPUTS_CODIGO_DE_BARRAS.add("000000000000000000000009x9000000000000000000001 ");
	}

	@Test
	public void testGetCampoLivreDaLinhaDigitavelFormatada() {
		
		assertEquals(CAMPO_LIVRE_EXPECTED, BoletoUtil.getCampoLivreDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertTrue(!CAMPO_LIVRE_EXPECTED.equals(BoletoUtil.getCampoLivreDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("0", "1"))));
	}

	@Test
	public void testGetValorDoTituloDaLinhaDigitavelFormatada() {
		
		assertEquals(VALOR_NOMINAL_EXPECTED, BoletoUtil.getValorDoTituloDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertTrue(!VALOR_NOMINAL_EXPECTED.equals(BoletoUtil.getValorDoTituloDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("0", "1"))));
	}

	@Test
	public void testGetFatorDeVencimentoDaLinhaDigitavelFormatada() {
		
		assertEquals(FATOR_DE_VENCIMENTO_EXPECTED, BoletoUtil.getFatorDeVencimentoDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertTrue(!FATOR_DE_VENCIMENTO_EXPECTED.equals(BoletoUtil.getFatorDeVencimentoDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("7", "9"))));
	}
	
	@Test
	public void testGetDigitoVerificadorGeralDaLinhaDigitavelFormatada() {
		
		assertEquals(CODIGO_DV_GERAL_EXPECTED, BoletoUtil.getDigitoVerificadorGeralDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertTrue(!CODIGO_DV_GERAL_EXPECTED.equals(BoletoUtil.getDigitoVerificadorGeralDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("3", "6"))));
	}

	@Test
	public void testGetCodigoDaMoedaDaLinhaDigitavelFormatada() {
		
		assertEquals(CODIGO_DA_MOEDA_EXPECTED, BoletoUtil.getCodigoDaMoedaDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertTrue(!CODIGO_DA_MOEDA_EXPECTED.equals(BoletoUtil.getCodigoDaMoedaDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("9", "5"))));
	}

	@Test
	public void testGetCodigoDoBancoDaLinhaDigitavelFormatada() {
		
		assertEquals(CODIGO_DO_BANCO_EXPECTED, BoletoUtil.getCodigoDoBancoDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertTrue(!CODIGO_DE_BARRAS_EXPECTED.equals(BoletoUtil.getCodigoDoBancoDaLinhaDigitavelFormatada(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("9", "4"))));
	}

	@Test
	public void testGetCampoLivreDoCodigoDeBarras() {
		
		assertEquals(CAMPO_LIVRE_EXPECTED, BoletoUtil.getCampoLivreDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED));
		assertTrue(!CAMPO_LIVRE_EXPECTED.equals(BoletoUtil.getCampoLivreDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED.replace("0", "1"))));
	}

	@Test
	public void testGetValorDoTituloDoCodigoDeBarras() {
		
		assertEquals(VALOR_NOMINAL_EXPECTED, BoletoUtil.getValorDoTituloDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED));
		assertTrue(!VALOR_NOMINAL_EXPECTED.equals(BoletoUtil.getValorDoTituloDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED.replace("0", "1"))));
	}

	@Test
	public void testGetFatorDeVencimentoDoCodigoDeBarras() {
		
		assertEquals(FATOR_DE_VENCIMENTO_EXPECTED, BoletoUtil.getFatorDeVencimentoDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED));
		assertTrue(!FATOR_DE_VENCIMENTO_EXPECTED.equals(BoletoUtil.getFatorDeVencimentoDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED.replace("7", "9"))));
	}

	@Test
	public void testGetDigitoVerificadorGeralDoCodigoDeBarras() {
		
		assertEquals(CODIGO_DV_GERAL_EXPECTED, BoletoUtil.getDigitoVerificadorGeralDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED));
		assertTrue(!CODIGO_DV_GERAL_EXPECTED.equals(BoletoUtil.getDigitoVerificadorGeralDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED.replace("3", "6"))));
	}
	
	@Test
	public void testGetCodigoDaMoedaDoCodigoDeBarras() {
		
		assertEquals(CODIGO_DA_MOEDA_EXPECTED, BoletoUtil.getCodigoDaMoedaDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED));
		assertTrue(!CODIGO_DA_MOEDA_EXPECTED.equals(BoletoUtil.getCodigoDaMoedaDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED.replace("9", "5"))));
	}

	@Test
	public void testGetCodigoDoBancoDoCodigoDeBarras() {

		assertEquals(CODIGO_DO_BANCO_EXPECTED, BoletoUtil.getCodigoDoBancoDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED));
		assertTrue(!CODIGO_DE_BARRAS_EXPECTED.equals(BoletoUtil.getCodigoDoBancoDoCodigoDeBarras(CODIGO_DE_BARRAS_EXPECTED.replace("9", "4"))));
	}

	@Test
	public void testCodigoDeBarrasEmLinhaDigitavelFormatada() {

		assertEquals(CODIGO_DE_BARRAS_EXPECTED, BoletoUtil.linhaDigitavelFormatadaEmCodigoDeBarras(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertEquals(CODIGO_DE_BARRAS_EXPECTED, BoletoUtil.linhaDigitavelFormatadaEmCodigoDeBarras(" "+LINHA_DIGITAVEL_FORMATADA_EXPECTED+" "));
		assertTrue(!CODIGO_DE_BARRAS_EXPECTED.equals(BoletoUtil.linhaDigitavelFormatadaEmCodigoDeBarras(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("8", "3"))));	
	}

	@Test
	public void testCodigoDeBarrasLinhaDigitavelNumerica() {

		assertEquals(LINHA_DIGITAVEL_NUMERICA_EXPECTED, BoletoUtil.codigoDeBarrasEmLinhaDigitavelNumerica(CODIGO_DE_BARRAS_EXPECTED));
		assertEquals(LINHA_DIGITAVEL_NUMERICA_EXPECTED, BoletoUtil.codigoDeBarrasEmLinhaDigitavelNumerica(" "+CODIGO_DE_BARRAS_EXPECTED+" "));
		assertTrue(!LINHA_DIGITAVEL_NUMERICA_EXPECTED.equals(BoletoUtil.codigoDeBarrasEmLinhaDigitavelNumerica(CODIGO_DE_BARRAS_EXPECTED.replace("8", "3"))));
	}
	
	@Test
	public void testLinhaDigitavelFormatadaEmCodigoDeBarras() {

		assertEquals(CODIGO_DE_BARRAS_EXPECTED, BoletoUtil.linhaDigitavelFormatadaEmCodigoDeBarras(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertEquals(CODIGO_DE_BARRAS_EXPECTED, BoletoUtil.linhaDigitavelFormatadaEmCodigoDeBarras(" "+LINHA_DIGITAVEL_FORMATADA_EXPECTED+" "));
		assertTrue(!CODIGO_DE_BARRAS_EXPECTED.equals(BoletoUtil.linhaDigitavelFormatadaEmCodigoDeBarras(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("8", "3"))));	
	}

	@Test
	public void testLinhaDigitavelNumericaEmCodigoDeBarras() {

		assertEquals(CODIGO_DE_BARRAS_EXPECTED, BoletoUtil.linhaDigitavelNumericaEmCodigoDeBarras(LINHA_DIGITAVEL_NUMERICA_EXPECTED));
		assertEquals(CODIGO_DE_BARRAS_EXPECTED, BoletoUtil.linhaDigitavelNumericaEmCodigoDeBarras(" "+LINHA_DIGITAVEL_NUMERICA_EXPECTED+" "));
		assertTrue(!CODIGO_DE_BARRAS_EXPECTED.equals(BoletoUtil.linhaDigitavelNumericaEmCodigoDeBarras(LINHA_DIGITAVEL_NUMERICA_EXPECTED.replace("8", "3"))));
	}
	
	@Test
	public void testCheckFormatoCodigoDeBarras() {
		for (String inputTest : INPUTS_CODIGO_DE_BARRAS) {
			try {
				BoletoUtil.checkFormatoCodigoDeBarras(inputTest);
				fail("Controle de formatação falho.");
			} catch (CodigoDeBarrasException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckExistsCodigoDeBarrasComArgumentoStringNull() {

		BoletoUtil.checkExistsCodigoDeBarras(null);
	}
	
	@Test
	public void testCheckExistsCodigoDeBarrasComArgumentoStringVazio() {

		StringBuilder emptyInput = new StringBuilder("");

		for (int i = 0; i <= 60; i++) {
			try {
				BoletoUtil.checkExistsCodigoDeBarras(emptyInput.toString());
				fail("Controle de espaços falho.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
			emptyInput.append(" ");
		}
	}


	@Test
	public void testIsCodigoDeBarrasValido() {
		
		assertTrue(!BoletoUtil.isCodigoDeBarrasValido(null));
		assertTrue(BoletoUtil.isCodigoDeBarrasValido(CODIGO_DE_BARRAS_EXPECTED));
		assertTrue(!BoletoUtil.isCodigoDeBarrasValido(CODIGO_DE_BARRAS_EXPECTED+0));
		assertTrue(!BoletoUtil.isCodigoDeBarrasValido(1+CODIGO_DE_BARRAS_EXPECTED));
	}
	
	@Test
	public void testIsLinhaDigitavelFormatadaValida() {
		
		assertTrue(!BoletoUtil.isLinhaDigitavelFormatadaValida(null));
		assertTrue(BoletoUtil.isLinhaDigitavelFormatadaValida(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertTrue(!BoletoUtil.isLinhaDigitavelFormatadaValida(LINHA_DIGITAVEL_FORMATADA_EXPECTED+0));
		assertTrue(!BoletoUtil.isLinhaDigitavelFormatadaValida(LINHA_DIGITAVEL_NUMERICA_EXPECTED));
	}

	@Test
	public void testIsLinhaDigitavelNumericaValida() {
		
		assertTrue(!BoletoUtil.isLinhaDigitavelNumericaValida(null));
		assertTrue(BoletoUtil.isLinhaDigitavelNumericaValida(LINHA_DIGITAVEL_NUMERICA_EXPECTED));
		assertTrue(!BoletoUtil.isLinhaDigitavelNumericaValida(0+LINHA_DIGITAVEL_NUMERICA_EXPECTED));
		assertTrue(!BoletoUtil.isLinhaDigitavelNumericaValida(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
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

	@Test(expected = IllegalArgumentException.class)
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

	@Test(expected = IllegalArgumentException.class)
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

		assertEquals(LINHA_DIGITAVEL_NUMERICA_EXPECTED,BoletoUtil.linhaDigitavelFormatadaEmNumerica(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
		assertEquals(LINHA_DIGITAVEL_NUMERICA_EXPECTED,BoletoUtil.linhaDigitavelFormatadaEmNumerica(" "+LINHA_DIGITAVEL_FORMATADA_EXPECTED+" "));
		assertTrue(!LINHA_DIGITAVEL_NUMERICA_EXPECTED.equals(BoletoUtil.linhaDigitavelFormatadaEmNumerica(LINHA_DIGITAVEL_FORMATADA_EXPECTED.replace("8", "3"))));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLinhaDigitavelNumericaEmFormatadaComArgumentoNull() {

		BoletoUtil.linhaDigitavelNumericaEmFormatada(null);
	}

	@Test
	public void testLinhaDigitavelNumericaEmFormatadaComArgumentoStringVazio() {

		StringBuilder emptyInput = new StringBuilder("");

		for (int i = 0; i <= 60; i++) {
			try {
				BoletoUtil.linhaDigitavelNumericaEmFormatada(emptyInput
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
	public void testLinhaDigitavelNumericaEmFormatadaComArgumentoStringDeFormatoNaoPrevisto() {

		for (String inputTest : INPUTS_LINHAS_DIGITAVEIS_NUMERICAS) {
			try {
				BoletoUtil.linhaDigitavelNumericaEmFormatada(inputTest);
				fail("Controle de formatação falho.");
			} catch (LinhaDigitavelException e) {
				System.out.println(e.getMessage());
				assertTrue(true);
			}
		}
	}

	@Test
	public void testLinhaDigitavelNumericaEmFormatada() {

		assertEquals(LINHA_DIGITAVEL_FORMATADA_EXPECTED,BoletoUtil.linhaDigitavelNumericaEmFormatada(LINHA_DIGITAVEL_NUMERICA_EXPECTED));
		assertEquals(LINHA_DIGITAVEL_FORMATADA_EXPECTED,BoletoUtil.linhaDigitavelNumericaEmFormatada(" "+LINHA_DIGITAVEL_NUMERICA_EXPECTED+" "));
		assertTrue(!LINHA_DIGITAVEL_FORMATADA_EXPECTED.equals(BoletoUtil.linhaDigitavelNumericaEmFormatada(LINHA_DIGITAVEL_NUMERICA_EXPECTED.replace("8", "3"))));
	}
}
