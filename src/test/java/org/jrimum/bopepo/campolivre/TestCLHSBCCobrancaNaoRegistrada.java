/**
 * 
 */
package org.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.hsbc.TipoIdentificadorCNR;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a> 
 */
public class TestCLHSBCCobrancaNaoRegistrada {

	
	private CampoLivre clHsbcCNR;
	private Titulo titulo;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(8351202);
		numeroDaConta.setDigitoDaConta("2");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.HSBC.create());
		contaBancaria.setNumeroDaConta(numeroDaConta);
		contaBancaria.setCarteira(new Carteira(1, TipoDeCobranca.SEM_REGISTRO));

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("0000239104761");
		titulo.setDataDoVencimento(new GregorianCalendar(2008, Calendar.JULY, 4).getTime());
		titulo.setParametrosBancarios(new ParametrosBancariosMap(TipoIdentificadorCNR.class.getName(), TipoIdentificadorCNR.COM_VENCIMENTO));
	}

	/**
	 * Test method for {@link org.jrimum.bopepo.campolivre.CLHSBCCobrancaNaoRegistrada#create(org.jrimum.domkee.financeiro.banco.febraban.Titulo)}.
	 */
	@Test
	public final void testGetInstance() {
		clHsbcCNR = CampoLivreFactory.create(titulo);
		assertNotNull(clHsbcCNR);
	}

	/**
	 * Test method for {@link org.jrimum.utilix.text.AbstractLineOfFields#write()}.
	 */
	@Test
	public final void testWrite() {
		//Teste básico
		clHsbcCNR = CampoLivreFactory.create(titulo);
		
		assertTrue(clHsbcCNR.write().length() == 25);
		assertEquals("8351202000023910476118682", clHsbcCNR.write());
		
		//TODO
	}

}
