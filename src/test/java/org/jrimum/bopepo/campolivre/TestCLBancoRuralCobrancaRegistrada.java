package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;

/**
 * <p>
 * Teste unitário do campo livre do banco Rural - Cobrança Registrada.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoRuralCobrancaRegistrada extends AbstractCampoLivreBaseTest<CLBancoRuralCobrancaRegistrada> {
	
	@Before
	public void setUp(){
		
		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_RURAL.create());
		titulo.getContaBancaria().setAgencia(new Agencia(133, "1"));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(6789,"1"));
		titulo.getContaBancaria().setCarteira(new Carteira(5,TipoDeCobranca.COM_REGISTRO));
		titulo.setNossoNumero("1234567");
		titulo.setDigitoDoNossoNumero("1");

		createCampoLivreToTest();
		
		setCampoLivreEsperadoComoString("0133000006789112345671000");
	}
}
