/**
 * 
 */
package br.com.nordestefomento.jrimum.bopepo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra;
import br.com.nordestefomento.jrimum.bopepo.LinhaDigitavel;
import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;
import br.com.nordestefomento.jrimum.domkee.type.EnumMoeda;

/**
 * @author Gilmar
 *
 */
public class TestLinhaDigitavel extends TestCase {

	
	private ICampoLivre clBradesco;

	private Titulo titulo;
	
	private CodigoDeBarra codigoDeBarra;
	
	private LinhaDigitavel linhaDigitavel;
	
	private Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3).getTime();

	@Override
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
		titulo.setE_Moeda(EnumMoeda.REAL);
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoVencimento(VENCIMENTO);
		
		clBradesco = FactoryCampoLivre.getInstance(titulo);
		
		codigoDeBarra = CodigoDeBarra.getInstance(titulo, clBradesco);
		
		linhaDigitavel = LinhaDigitavel.getInstance(codigoDeBarra);

	}
	
	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.LinhaDigitavel#getInstance(br.com.nordestefomento.jrimum.domkee.entity.Titulo, br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra)}.
	 */
	public void testGetInstance() {

		assertNotNull(linhaDigitavel);
		
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.LinhaDigitavel#toString()}.
	 */
	public void testWrite() {
		
		assertEquals("23791.23405 51234.567892 01000.678902 2 10000000010023", linhaDigitavel.write());
		
	}

}
