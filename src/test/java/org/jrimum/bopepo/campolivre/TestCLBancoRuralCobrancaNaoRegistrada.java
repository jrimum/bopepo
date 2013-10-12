package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoRural.CODIGO_REDUZIDO;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;

/**
 * <p>
 * Teste unitário do campo livre do banco Rural - Cobrança não Registrada.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoRuralCobrancaNaoRegistrada extends AbstractCampoLivreBaseTest<CLBancoRuralCobrancaNaoRegistrada> {

	@Before
	public void setUp(){

		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_RURAL.create());
		titulo.getContaBancaria().setAgencia(new Agencia(133, "1"));
		titulo.getContaBancaria().setCarteira(new Carteira(5,TipoDeCobranca.SEM_REGISTRO));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(6789));
		titulo.setNossoNumero("123456789012345");
		
		// Código Reduzido do Cliente fornecido pelo Banco
		titulo.setParametrosBancarios( new ParametrosBancariosMap(CODIGO_REDUZIDO, 123));
				
		createCampoLivreToTest();
		
		setCampoLivreEsperadoComoString("9133123123456789012345000");
	}

}
