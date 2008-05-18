/**
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.entity.Carteira;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;

/**
 * @author misael
 *
 */
public class TestCLHsbcCNR {

	
	private ICampoLivre clHsbcCNR;
	private Titulo titulo;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(3003);
		numeroDaConta.setDigitoDaConta("2");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.HSBC.newInstance());
		contaBancaria.setNumeroDaConta(numeroDaConta);
		contaBancaria.setCarteira(new Carteira(1, EnumTipoCobranca.SEM_REGISTRO));

		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();
		
		titulo = Titulo.getInstance(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("41234567894");
		titulo.setDataDoVencimento(new GregorianCalendar(2000, Calendar.JULY, 4).getTime());			
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.campolivre.CLHsbcCNR#getInstance(br.com.nordestefomento.jrimum.domkee.entity.Titulo)}.
	 */
	@Test
	public final void testGetInstance() {
		clHsbcCNR = FactoryCampoLivre.getInstance(titulo);
		assertNotNull(clHsbcCNR);
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.utilix.LineOfFields#write()}.
	 */
	@Test
	public final void testWrite() {
		//Teste básico
		clHsbcCNR = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clHsbcCNR.write().length() == 25);
		assertEquals("0003003004123456789410012", clHsbcCNR.write());
		
		
		// Alterando alguns dados do título
		titulo.getContaBancaria();
		titulo.setNossoNumero("4412345678944");
		clHsbcCNR = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clHsbcCNR.write().length() == 25);
		assertEquals("0003333441234567894410012", clHsbcCNR.write());		
	}

}
