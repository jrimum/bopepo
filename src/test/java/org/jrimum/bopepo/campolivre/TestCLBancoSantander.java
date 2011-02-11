package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.excludes.CampoLivreBaseTest;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
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
 * Teste unitário do campo livre do banco Santander.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *  
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoSantander extends CampoLivreBaseTest {

	private Titulo titulo;

	@Before
	public void setUp() throws Exception {
		
		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");
		
		ContaBancaria contaBancaria = new ContaBancaria();
		
		NumeroDaConta numeroDaConta = new NumeroDaConta(162546,"9");
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		contaBancaria.setBanco(BancosSuportados.BANCO_SANTANDER.create());
		contaBancaria.setCarteira(new Carteira(101, TipoDeCobranca.COM_REGISTRO, "101- Cobrança Simples Rápida COM Registro"));
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("00000000002");
		titulo.setDigitoDoNossoNumero("7");
		
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		setClasseGeradoraDoCampoLivre(CLBancoSantander.class);
		setCampoLivreValidoAsString("9162546900000000000270101");
		
	}
	
	@Test
	public void testIofSeguradora(){
		
		titulo.setParametrosBancarios(new ParametrosBancariosMap("IOF_SEGURADORA", 8));
		
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		setCampoLivreValidoAsString("9162546900000000000278101");
		
		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void testCarteiras(){
		
		//Carteira nao autorizada
		titulo.getContaBancaria().setCarteira(new Carteira(123));
		
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		seCampoLivreEscritoEstaCorreto();
	}
}
