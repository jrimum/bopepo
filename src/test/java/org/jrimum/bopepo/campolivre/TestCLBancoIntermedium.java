package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;

/**
 * <p>
 * Teste unitário do campo livre do Banco Intermedium.
 * </p>
 * 
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 */
public class TestCLBancoIntermedium extends AbstractCampoLivreBaseTest<CLBancoIntermedium> {

	private Titulo titulo;

	@Before
	public void setUp(){

		ContaBancaria contaBancaria = new ContaBancaria();

		contaBancaria.setBanco(BancosSuportados.BANCO_INTEMEDIUM.create());
		contaBancaria.setAgencia( new Agencia(54, "0"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(149666,"6"));
		contaBancaria.setCarteira(new Carteira(5));

		titulo = new Titulo(contaBancaria, new Sacado("S"), new Cedente("C"));
		titulo.setNossoNumero("5611002");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		setCampoLivreValidoAsString("0054700000561100214966660");
	}
	
}
