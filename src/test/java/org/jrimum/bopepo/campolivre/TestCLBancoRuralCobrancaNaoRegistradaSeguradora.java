package org.jrimum.bopepo.campolivre;

import java.math.BigDecimal;

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
 * Teste unitário do campo livre do Banco Rural - Cobrança não Registrada Seguradora.
 * </p>
 * 
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoRuralCobrancaNaoRegistradaSeguradora extends CampoLivreBaseTest {

	private Titulo titulo;

	@Before
	public void setUp() throws Exception {

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");

		Agencia agencia = new Agencia(155, "1");
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setAgencia(agencia);

		NumeroDaConta numeroDaConta = new NumeroDaConta(1625462,"9");
		contaBancaria.setNumeroDaConta(numeroDaConta);

		contaBancaria.setBanco(BancosSuportados.BANCO_RURAL.create());
		contaBancaria.setCarteira(new Carteira(101, TipoDeCobranca.SEM_REGISTRO));

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("0000022569");
		titulo.setDigitoDoNossoNumero("7");
		
		ParametrosBancariosMap map = new ParametrosBancariosMap();
		map.adicione("VALOR_IOS", new BigDecimal(4077));
		map.adicione("CODIGO_REDUZIDO", 1);
		titulo.setParametrosBancarios(map);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		setClasseGeradoraDoCampoLivre(CLBancoRuralCobrancaNaoRegistradaSeguradora.class);
		setCampoLivreValidoAsString("4155001000002256970004077");
	}
}
