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

package org.jrimum.bopepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.NotSupportedBancoException;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeMoeda;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste da classe Boleto 
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
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

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_BRADESCO.create());
		
		Agencia agencia = new Agencia(1234, "1");
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCarteira(new Carteira(5));
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		contaBancaria.setNumeroDaConta(numeroDaConta);

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("12345678901");
		titulo.setTipoDeMoeda(TipoDeMoeda.REAL);
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoVencimento(VENCIMENTO);
		
		boleto = new Boleto(titulo);
		
	}

	/**
	 * Test method for {@link org.jrimum.bopepo.Boleto#Boleto(Titulo)}.
	 * @throws NotSupportedBancoException 
	 * @throws NotSupportedCampoLivreException 
	 */
	@Test
	public void testGetInstance() throws NotSupportedBancoException, NotSupportedCampoLivreException {
		
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
	 * Test method for {@link org.jrimum.bopepo.Boleto#getCodigoDeBarras()}.
	 */
	@Test
	public void testGetCodigoDeBarra() {
		
		assertNotNull(boleto.getCodigoDeBarras());
		
	}

	/**
	 * Test method for {@link org.jrimum.bopepo.Boleto#getLinhaDigitavel()}.
	 */
	@Test
	public void testGetLinhaDigitavel() {
		assertNotNull(boleto.getLinhaDigitavel());
	}
	
	/**
	 * Test method for {@link org.jrimum.bopepo.Boleto#getDataDeProcessamento()}.
	 */
	@Test
	public void testGetDataDeProcessamento() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date agora = new Date();
		
		assertEquals(df.format(agora), df.format(boleto.getDataDeProcessamento()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCampoLivreNull() {
		
		boleto = new Boleto(titulo, null);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testSetCampoLivreTamanhoCorreto() {
		
		boleto = new Boleto(titulo, new CampoLivre() {

			public String write() {
				return "1234567890123456789012345";
			}

			public void read(String g) {
			}
		});
		
		Assert.assertNotNull(boleto.getCampoLivre());
		Assert.assertNotNull(boleto.getCampoLivre().write());
		Assert.assertEquals(CampoLivre.STRING_LENGTH.intValue(), boleto.getCampoLivre().write().length());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetCampoLivreTamanhoMaior() {
		
		boleto = new Boleto(titulo, new CampoLivre() {

			private static final long serialVersionUID = 1L;

			public String write() {
				return "1234567890123456789012345000";
			}

			public void read(String g) {
			}
		});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetCampoLivreTamanhoMenor() {
		
		boleto = new Boleto(titulo, new CampoLivre() {

			private static final long serialVersionUID = 1L;

			public String write() {
				return "12345678901234567890";
			}

			public void read(String g) {
			}
		});
	}
}
