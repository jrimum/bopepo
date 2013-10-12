package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoRural.CODIGO_REDUZIDO;
import static org.jrimum.bopepo.parametro.ParametroBancoRural.VALOR_IOS;

import java.math.BigDecimal;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;

/**
 * <p>
 * Teste unitário do campo livre do Banco Rural - Cobrança não Registrada Seguradora.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoRuralCobrancaNaoRegistradaSeguradora extends AbstractCampoLivreBaseTest<CLBancoRuralCobrancaNaoRegistradaSeguradora> {

	@Before
	public void setUp(){


		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_RURAL.create());
		titulo.getContaBancaria().setAgencia(new Agencia(155, "1"));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(1625462,"9"));
		titulo.getContaBancaria().setCarteira(new Carteira(101, TipoDeCobranca.SEM_REGISTRO));
		titulo.setNossoNumero("0000022569");
		titulo.setDigitoDoNossoNumero("7");
		
		ParametrosBancariosMap map = new ParametrosBancariosMap();
		map.adicione(VALOR_IOS, new BigDecimal("40.77"));
		map.adicione(CODIGO_REDUZIDO, 1);
		
		titulo.setParametrosBancarios(map);

		createCampoLivreToTest();

		setCampoLivreEsperadoComoString("4155001000002256970004077");
	}
}
