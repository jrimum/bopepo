package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.excludes.CampoLivreBaseTest;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
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
 * Teste unitário do campo livre do banco Rural - Cobrança não Registrada.
 * </p>
 * 
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoRuralCobrancaNaoRegistrada extends CampoLivreBaseTest {

	private Titulo titulo;

	@Before
	public void setUp() throws Exception {

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_RURAL.create());

		Agencia agencia = new Agencia(133, "1");

			
		contaBancaria.setCarteira(new Carteira(5,TipoDeCobranca.SEM_REGISTRO));

		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		
		contaBancaria.setNumeroDaConta(numeroDaConta);
		contaBancaria.setAgencia(agencia);

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("123456789012345");
		
		// Código Reduzido do Cliente fornecido pelo Banco
		titulo.setParametrosBancarios( new ParametrosBancariosMap(AbstractCLBancoRural.CODIGO_REDUZIDO, 123));
		
		
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		setClasseGeradoraDoCampoLivre(CLBancoRuralCobrancaNaoRegistrada.class);
		setCampoLivreValidoAsString("9133123123456789012345000");
	}

}
