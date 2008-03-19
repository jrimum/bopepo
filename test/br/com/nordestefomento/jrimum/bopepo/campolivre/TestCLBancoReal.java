package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;

public class TestCLBancoReal {
	
	private ICampoLivre clReal;
	
	private Titulo titulo;

	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBanco.BANCO_DO_BRASIL);
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(1234);
		contaBancaria.setAgencia(agencia);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		numeroDaConta.setDigitoDaConta("9");
		contaBancaria.setNumeroDaConta(numeroDaConta);

		cedente.addContaBancaria(contaBancaria);
		
		titulo = Titulo.getInstance(sacado, cedente);
		titulo.setNumeroDoDocumento("1234567890123");
	}

	@Test
	public void testGetInstanceTitulo() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico
		clReal = FactoryCampoLivre.getInstance(titulo);
		
		assertNotNull(clReal);
	}

	@Test
	public void testToString() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico feliz
		clReal = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clReal.toString().length() == 25);
		assertEquals("1234000678991234567890123",clReal.toString());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		contaBancaria.setBanco(EnumBanco.BANCO_ABN_AMRO_REAL);
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(0);
		contaBancaria.setAgencia(agencia);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(0);
		numeroDaConta.setDigitoDaConta("0");
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo.getCedente().addContaBancaria(contaBancaria);
		titulo.setNumeroDoDocumento("0");
		
		clReal = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clReal.toString().length() == 25);
		assertEquals("0000000000000000000000000",clReal.toString());
	}

}
