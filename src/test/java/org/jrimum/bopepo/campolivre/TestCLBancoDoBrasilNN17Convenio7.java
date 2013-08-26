package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.junit.Before;


/**
 * 
 * Teste unitário do campo livre do banco do brasil com o nosso número
 * de tamanho igual a 17 e convênio de 7 posições.
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoDoBrasilNN17Convenio7 extends AbstractCampoLivreBaseTest<CLBancoDoBrasilNN17Convenio7> {

	@Before
	public void setUp(){

		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_DO_BRASIL.create());
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(1234567));
		titulo.getContaBancaria().setCarteira(new Carteira(23));
		titulo.setNossoNumero("12345678901234567");
		
		createCampoLivreToTest();
		
		setCampoLivreEsperadoComoString("0000001234567890123456723");
	}
}
