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
 * Created at: 30/03/2008 - 18:11:45
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
 * Criado em: 30/03/2008 - 18:11:45
 * 
 */


package br.com.nordestefomento.jrimum.bopepo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;
import br.com.nordestefomento.jrimum.domkee.type.EnumMoeda;
import br.com.nordestefomento.jrimum.utilix.Util4Date;


/**
 * @author Gilmar
 *
 */
public class TestBoleto extends TestCase {


	private Titulo titulo;
	
	private Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3).getTime();
	
	private Boleto boleto;

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
		
		boleto = Boleto.getInstance(titulo);
		
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#getInstance(br.com.nordestefomento.jrimum.domkee.entity.Titulo)}.
	 * @throws NotSuporttedBancoException 
	 * @throws NotSuporttedCampoLivreException 
	 */
	public void testGetInstance() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		assertNotNull(boleto);
		assertNotNull(boleto.getTitulo());
		
		try{
			Boleto.getInstance(null);
			assertTrue(false);
			fail("Teste Falho!");
			
		} catch(IllegalArgumentException illegalArgumentException){
			assertTrue(true);
		}
		
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#getCodigoDeBarra()}.
	 */
	public void testGetCodigoDeBarra() {
		
		assertNotNull(boleto.getCodigoDeBarra());
		
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#getLinhaDigitavel()}.
	 */
	public void testGetLinhaDigitavel() {

		assertNotNull(boleto.getLinhaDigitavel());
		
	}
	
	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#getDataDeProcessamento()}.
	 */
	public void testGetDataDeProcessamento() {
		
		Date agora = new Date();
		
		assertEquals(Util4Date.fmt_dd_MM_yyyy.format(agora), Util4Date.fmt_dd_MM_yyyy.format(boleto.getDataDeProcessamento()));
		
	}

}