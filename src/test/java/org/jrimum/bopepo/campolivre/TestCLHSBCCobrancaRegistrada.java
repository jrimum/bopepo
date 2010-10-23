package org.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
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
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 */
public class TestCLHSBCCobrancaRegistrada {

	private CampoLivre clHsbcCR;
	private Titulo titulo;
	
	@Before
	public void setUp() throws Exception {
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(674456);
		numeroDaConta.setDigitoDaConta("8");

		Agencia agencia = new Agencia(12, "1");
		
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.HSBC.create());
		contaBancaria.setNumeroDaConta(numeroDaConta);
		contaBancaria.setCarteira(new Carteira(1, TipoDeCobranca.COM_REGISTRO));
		contaBancaria.setAgencia(agencia);

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("0039900032");
		titulo.setDigitoDoNossoNumero("2");
		titulo.setDataDoVencimento(new GregorianCalendar(2010, Calendar.JULY, 4).getTime());
	}
	
	@Test
	public final void testGetInstance() {
		clHsbcCR = CampoLivreFactory.create(titulo);
		assertNotNull(clHsbcCR);
	}
	
	@Test
	public final void testWrite() {

		clHsbcCR = CampoLivreFactory.create(titulo);

		assertTrue(clHsbcCR.write().length() == 25);
		assertEquals("0039900032200120674456001", clHsbcCR.write());
	}	

}
