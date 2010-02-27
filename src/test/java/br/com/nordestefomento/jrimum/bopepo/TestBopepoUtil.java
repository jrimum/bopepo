package br.com.nordestefomento.jrimum.bopepo;

import static org.junit.Assert.*;

import java.util.IllegalFormatException;

import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBopepoUtil {

	private static String LINHA_DIGITAVEL_FORMATADA_EXPECTED = "39991.23452 67000.000009 00222.268922 3 43710000000965";
	private static String LINHA_DIGITAVEL_NUMERICA_EXPECTED = "39991234526700000000900222268922343710000000965";

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
	public void testLinhaDigitavelEmCodigoDeBarras() {

		fail("Not yet implemented");
	}

	@Test(expected = NullPointerException.class)
	public void testLinhaDigitavelEmNumerosComArgumentoNull() {

		BoletoUtil.linhaDigitavelFormatadaEmNumerica(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLinhaDigitavelEmNumerosComArgumentoStringVazio() {
			
			BoletoUtil.linhaDigitavelFormatadaEmNumerica("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLinhaDigitavelEmNumerosComArgumentoStringVazio2() {
			
			BoletoUtil.linhaDigitavelFormatadaEmNumerica(" ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLinhaDigitavelEmNumerosComArgumentoStringVazio3() {
			
			BoletoUtil.linhaDigitavelFormatadaEmNumerica("  ");
	}

	@Test(expected = IllegalFormatException.class)
	public void testLinhaDigitavelEmNumerosComArgumentoStringDeFormatoNaoPrevisto() {
		
		BoletoUtil.linhaDigitavelFormatadaEmNumerica("00032.3232.32");
			
	}

	@Test(expected = IllegalFormatException.class)
	public void testLinhaDigitavelEmNumerosComArgumentoStringNumericaComTamanhoDiferenteDe47() {

		BoletoUtil.linhaDigitavelFormatadaEmNumerica(LINHA_DIGITAVEL_FORMATADA_EXPECTED
				+ "0");
	}

	@Test
	public void testLinhaDigitavelEmNumeros() {

		assertEquals(LINHA_DIGITAVEL_NUMERICA_EXPECTED, BoletoUtil
				.linhaDigitavelFormatadaEmNumerica(LINHA_DIGITAVEL_FORMATADA_EXPECTED));
	}
}
