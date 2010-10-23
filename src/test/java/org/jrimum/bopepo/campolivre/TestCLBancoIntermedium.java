package org.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do Banco Intermedium.
 * </p>
 * 
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *  
 */
public class TestCLBancoIntermedium {
	
	Titulo titulo;
	CampoLivre clBcIntermedium;
	
	@Before
	public void setUp() {
		
		Agencia agencia = new Agencia(54, "0");
		NumeroDaConta numeroConta = new NumeroDaConta();
		numeroConta.setCodigoDaConta(149666);
		numeroConta.setDigitoDaConta("6");
		
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco( BancosSuportados.BANCO_INTEMEDIUM.create());
		contaBancaria.setAgencia(agencia);
		contaBancaria.setNumeroDaConta(numeroConta);
		
		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("5611002");
		titulo.setNumeroDoDocumento("0");
	}
	
	@Test
	public final void testGetInstance() {
		clBcIntermedium = CampoLivreFactory.create(titulo);
		assertNotNull(clBcIntermedium);
	}
	
	@Test
	public final void testWrite() {

		clBcIntermedium = CampoLivreFactory.create(titulo);
		
		assertTrue(clBcIntermedium.write().length() == 25);
		assertEquals("0054700000561100214966660", clBcIntermedium.write());
	}	
}
