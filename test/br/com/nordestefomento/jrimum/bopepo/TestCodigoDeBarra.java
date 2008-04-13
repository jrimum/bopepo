/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:12:06
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:12:06
 * 
 */


package br.com.nordestefomento.jrimum.bopepo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra;
import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumMoeda;


/**
 * 
 * Teste Unitário para: <br />
 * {@link br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra#getFatorDeVencimento()}
 * 
 * 
 * @author Gabriel Guimarães
 * @author Gilmar P.S.L
 * @author Misael Barreto
 * @author Rômulo Augusto
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
public class TestCodigoDeBarra extends TestCase {

	private ICampoLivre clBradesco;

	private Titulo titulo;
	
	private CodigoDeBarra codigoDeBarra;
	
	private Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3).getTime();

	protected void setUp() throws Exception {

		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_BRADESCO.newInstance());
		
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

	}

	/**
	 * Test method for
	 * {@link br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra#getInstance(br.com.nordestefomento.jrimum.domkee.entity.Titulo, ICampoLivre)}.
	 */
	public void testGetInstance() {
		
		assertNotNull(codigoDeBarra);
		
	}
	
	/**
	 * Test method for
	 * {@link br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra#getDigitoVerificadorGeral()}.
	 */
	public void testGetDigitoVerificadorGeral() {
		assertTrue(2 == codigoDeBarra.getDigitoVerificadorGeral().getField());
	}

	/**
	 * Test method for
	 * {@link br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra#toString()}.
	 */
	public void testWrite() {
		
		assertEquals("23792100000000100231234051234567890100067890", codigoDeBarra.write());
		
	}

	/**
	 * Test method for
	 * {@link br.com.nordestefomento.jrimum.bopepo.CodigoDeBarra#getFatorDeVencimento()}.
	 */
	public void testGetFatorDeVencimento() {
		
		assertTrue(1000 == codigoDeBarra.getFatorDeVencimento().getField());
		
	}

}
