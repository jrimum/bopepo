package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.Carteira;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;

public class TestCLItau {

	private ICampoLivre clItau;
	
	private Titulo titulo;

	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_ITAU.newInstance());
		
		contaBancaria.setAgencia(new Agencia(57));
		contaBancaria.setCarteira(new Carteira(110));
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(12345);
		numeroDaConta.setDigitoDaConta("7");//Não importa para o CampoLivre
		contaBancaria.setNumeroDaConta(numeroDaConta);

		cedente.addContaBancaria(contaBancaria);
		
		titulo = Titulo.getInstance(sacado, cedente);
		titulo.setNumeroDoDocumento("1234567");
		titulo.setNossoNumero("12345678");
	}

	@Test
	public void testGetInstanceTitulo() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico
		clItau = FactoryCampoLivre.getInstance(titulo);
		
		assertNotNull(clItau);
	}

	@Test
	public void testToStringPadrao() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico feliz
		clItau = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clItau.write().length() == 25);
		assertEquals("1101234567880057123457000",clItau.write());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		contaBancaria.setBanco(EnumBancos.BANCO_ITAU.newInstance());
		
		contaBancaria.setAgencia(new Agencia(0));
		contaBancaria.setCarteira(new Carteira(0));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(0, "0"));
		
		titulo.setNossoNumero("0");
		titulo.getCedente().addContaBancaria(contaBancaria);
		titulo.setNumeroDoDocumento("0");
		
		clItau = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clItau.write().length() == 25);
		assertEquals("0000000000000000000000000",clItau.write());
		
	}
	
	@Test
	public void testToStringPadraoCarteirasExcecao() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		contaBancaria.setCarteira(new Carteira(126));
		
		titulo.getCedente().getContasBancarias().clear();
		titulo.getCedente().addContaBancaria(contaBancaria);
		
		clItau = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clItau.write().length() == 25);
		assertEquals("1261234567850057123457000",clItau.write());
	}
	
	@Test
	public void testToStringEspecial() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		contaBancaria.setCarteira(new Carteira(198));
		
		titulo.getCedente().getContasBancarias().clear();
		titulo.getCedente().addContaBancaria(contaBancaria);
		
		clItau = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clItau.write().length() == 25);
		assertEquals("1981234567812345671234580",clItau.write());
	}

}
