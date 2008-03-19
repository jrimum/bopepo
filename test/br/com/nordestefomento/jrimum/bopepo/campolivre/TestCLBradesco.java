package br.com.nordestefomento.jrimum.bopepo.campolivre;

import junit.framework.TestCase;
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

public class TestCLBradesco extends TestCase {

	private ICampoLivre clBradesco;
	
	private Titulo titulo;

	protected void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBanco.BANCO_BRADESCO);
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(1234);
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCodigoDaCarteira(5);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		contaBancaria.setNumeroDaConta(numeroDaConta);

		cedente.addContaBancaria(contaBancaria);
		
		titulo = Titulo.getInstance(sacado, cedente);
		titulo.setNossoNumero("12345678901");
		
	}

	public void testGetInstanceTitulo() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico
		clBradesco = FactoryCampoLivre.getInstance(titulo);
		
		assertNotNull(clBradesco);
	}
	
	public void testWrite() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico feliz
		clBradesco = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBradesco.write().length() == 25);
		assertEquals("1234051234567890100067890",clBradesco.write());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		contaBancaria.setBanco(EnumBanco.BANCO_BRADESCO);
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(0);
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCodigoDaCarteira(0);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(0);
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo.getCedente().addContaBancaria(contaBancaria);
		
		titulo.setNossoNumero("0");
		
		clBradesco = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBradesco.write().length() == 25);
		assertEquals("0000000000000000000000000",clBradesco.write());
	}

}
