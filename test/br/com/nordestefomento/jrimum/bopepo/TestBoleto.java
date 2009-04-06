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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.EnumMoeda;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.utilix.Util4Date;


/**
 * 
 * <p>
 * DEFINIÇÃO DA CLASSE
 * </p>
 * 
 * <p>
 * OBJETIVO/PROPÓSITO
 * </p>
 * 
 * <p>
 * EXEMPLO: 
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael
 * @author Rômulo
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
	
public class TestBoleto{


	private Titulo titulo;
	
	private Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3).getTime();
	
	private Boleto boleto;


	@Before
	public void setUp() throws Exception {

		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_BRADESCO.create());
		
		Agencia agencia = new Agencia(1234, '1');
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCarteira(new Carteira(5));
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		contaBancaria.setNumeroDaConta(numeroDaConta);

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("12345678901");
		titulo.setEnumMoeda(EnumMoeda.REAL);
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoVencimento(VENCIMENTO);
		
		boleto = new Boleto(titulo);
		
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#Boleto(Titulo)}.
	 * @throws NotSuporttedBancoException 
	 * @throws NotSuporttedCampoLivreException 
	 */
	@Test
	public void testGetInstance() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		assertNotNull(boleto);
		assertNotNull(boleto.getTitulo());
		
		try{
			new Boleto(null);
			assertTrue(false);
			fail("Teste Falho!");
			
		} catch(IllegalArgumentException illegalArgumentException){
			assertTrue(true);
		}
		
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#getCodigoDeBarras()}.
	 */
	@Test
	public void testGetCodigoDeBarra() {
		
		assertNotNull(boleto.getCodigoDeBarras());
		
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#getLinhaDigitavel()}.
	 */
	@Test
	public void testGetLinhaDigitavel() {

		assertNotNull(boleto.getLinhaDigitavel());
		
	}
	
	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.Boleto#getDataDeProcessamento()}.
	 */
	@Test
	public void testGetDataDeProcessamento() {
		
		Date agora = new Date();
		
		assertEquals(Util4Date.fmt_dd_MM_yyyy.format(agora), Util4Date.fmt_dd_MM_yyyy.format(boleto.getDataDeProcessamento()));
		
	}

}