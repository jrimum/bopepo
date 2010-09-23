package org.jrimum.bopepo.campolivre;

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

/**
 * <p>
 * Teste unitário do campo livre do banco Rural - Cobrança Registrada.
 * </p>
 * 
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoRuralCobrancaRegistrada extends CampoLivreBaseTest {
	
	private Titulo titulo;
	
	@Before
	public void setUp() throws Exception {
		
		Sacado sacado = new Sacado("Nome do Sacado");
		Cedente cedente = new Cedente("Nome do Cedente");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_RURAL.create());

		Agencia agencia = new Agencia(133, "1");
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		numeroDaConta.setDigitoDaConta("1");
		
		Carteira carteira = new Carteira();
		carteira.setCodigo(5);
		carteira.setTipoCobranca(TipoDeCobranca.COM_REGISTRO);
		
		contaBancaria.setNumeroDaConta(numeroDaConta);
		contaBancaria.setAgencia(agencia);
		contaBancaria.setCarteira(carteira);

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("1234567");
		titulo.setDigitoDoNossoNumero("1");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		setClasseGeradoraDoCampoLivre(CLBancoRuralCobrancaRegistrada.class);
		setCampoLivreValidoAsString("0133000006789112345671000");
	}
}
