package org.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.excludes.CampoLivreBaseTest;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do banco unibanco para cobrança não registrada
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 *
 */
public class TestCLUnibancoCobrancaNaoRegistrada extends CampoLivreBaseTest {

	/**
	 * String Campo Livre.
	 */
	private static String TEST_CASE = "5123456100112233445566777";

	private Titulo titulo;

	/**
	 * <p>
	 * Situação: precisamos emitir um título para um sacado com essas
	 * características:
	 * </p>
	 * <p>
	 * Banco: UNIBANCO – identificação 409<br />
	 * Moeda: Real – R$ - identificação 9<br />
	 * Vencimento: 31 de dezembro de 2001<br />
	 * Valor: R$1000,00<br />
	 * Código para transação CVT: 5 (cobrança sem registro – 7744-5)<br />
	 * Número de referência do cliente(NossoNúmero) : 112233445566777<br />
	 * Número de referência do cliente (Código Conta): 1234561<br />
	 * </p>
	 * <p>
	 * NÚMERO DE ORIGEM DO CÓDIGO DE BARRAS:
	 * </p>
	 * <p>
	 * 
	 * <pre>
	 * 409  9  2  1546  0000100000  5  1234561  00  112233445566777
	 * </pre>
	 * 
	 * </p>
	 * <p>
	 * LINHA DIGITÁVEL:
	 * </p>
	 * <p>
	 * 
	 * <pre>
	 * 40995.12347  56100.112236  34455.667773  2  15460000100000
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @throws Exception
	 * 
	 * @since 0.2
	 */

	@Before
	public void setUp() throws Exception {

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.UNIBANCO.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "1"));
		contaBancaria.setCarteira(new Carteira(123, TipoDeCobranca.SEM_REGISTRO));
		contaBancaria.setAgencia(new Agencia(01234, "1"));

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("1234567");
		titulo.setNossoNumero("11223344556677");
		titulo.setDigitoDoNossoNumero("7");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		setClasseGeradoraDoCampoLivre(CLUnibancoCobrancaNaoRegistrada.class);
		setCampoLivreValidoAsString("5123456100112233445566777");
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComContaNula() {

		titulo.getContaBancaria().setNumeroDaConta(null);
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComContaNegativa() {

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(-23));
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComDigitoDaContaNula() {

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(23, null));
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComDigitoDaContaNegativa() {

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(2, "-3"));
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComDigitoDaContaNaoNumerico() {

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(-23, "X"));
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComNossoNumeroNula() {

		titulo.setNossoNumero(null);
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComNossoNumeroNegativo() {

		titulo.setNossoNumero("-012345679012345");
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComNossoNumeroNaoNumerico() {

		titulo.setNossoNumero("123456790123y45");
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
	}

	@Test
	public final void testITextStream() {

		assertEquals(TEST_CASE, getCampoLivreToTest().write());

		getCampoLivreToTest().read(TEST_CASE);

		assertEquals(TEST_CASE, getCampoLivreToTest().write());
	}
}
