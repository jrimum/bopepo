package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoSantander.IOF_SEGURADORA;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do banco Santander.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoSantander extends AbstractCampoLivreBaseTest<CLBancoSantander> {

	@Before
	public void setUp(){
		
		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_SANTANDER.create());
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(162546,"9"));
		titulo.getContaBancaria().setCarteira(new Carteira(101, TipoDeCobranca.COM_REGISTRO, "101- Cobrança Simples Rápida COM Registro"));
		titulo.setNossoNumero("00000000002");
		titulo.setDigitoDoNossoNumero("7");
		
		createCampoLivreToTest();
		
		setCampoLivreEsperadoComoString("9162546900000000000270101");
		
	}
	
	@Test
	public void testIofSeguradora(){
		
		titulo.setParametrosBancarios(new ParametrosBancariosMap(IOF_SEGURADORA, 8));
		
		createCampoLivreToTest();
		
		setCampoLivreEsperadoComoString("9162546900000000000278101");
		
		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void testCarteiras(){
		
		//Carteira nao autorizada
		titulo.getContaBancaria().setCarteira(new Carteira(123));
		
		createCampoLivreToTest();
		
		seCampoLivreEscritoEstaCorreto();
	}
}
