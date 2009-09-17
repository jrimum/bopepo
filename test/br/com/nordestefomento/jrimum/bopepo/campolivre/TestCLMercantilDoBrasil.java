/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 25/09/2008 - 20:55:13
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 25/09/2008 - 20:55:13
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.BancoSuportado;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.Pessoa;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;

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
 * @author Rômulo
 * 
 * @since 
 * 
 * @version 
 */
public class TestCLMercantilDoBrasil {

	private CampoLivre clMercantil;
	
	private Titulo titulo;
	
	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();
		
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancoSuportado.MERCANTIL_DO_BRASIL.create());
		
		contaBancaria.setAgencia(new Agencia(1234, "1"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(123456789));
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("1234567890");
		titulo.setDigitoDoNossoNumero("5");
	}
	
	@Test
	public void testCreate() {
		
		try {
			
			clMercantil = CampoLivreFactory.create(titulo);
			Assert.assertNotNull(clMercantil);
			
		} catch(NotSuporttedCampoLivreException e) {
			e.printStackTrace();
			Assert.fail("O campo livre do banco Mercantil do Brasil deve estar sendo suportado.");
		}
		
	}
	
	@Test
	public void testWriteNotNull() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertNotNull(campoLivre);
	}

	private String getCampoLivreComoString() {
		clMercantil = CampoLivreFactory.create(titulo);
		String campoLivre = clMercantil.write();
		return campoLivre;
	}
	
	@Test
	public void testWriteCampo1Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("1234", campoLivre.substring(0, 4));
	}
	
	@Test
	public void testWriteCampo2Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("12345678905", campoLivre.substring(4, 15));
	}
	
	@Test
	public void testWriteCampo3Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("123456789", campoLivre.substring(15, 24));
	}
	
	@Test
	public void testWriteCampo4ComDesconto() {
		
		titulo.setDesconto(BigDecimal.TEN);
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("0", campoLivre.substring(24, 25));
	}
	
	@Test
	public void testWriteCampo4SemDescontoNull() {

		titulo.setDesconto(null);
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("2", campoLivre.substring(24, 25));
	}
	
	@Test
	public void testWriteCampo4SemDescontoZero() {

		titulo.setDesconto(BigDecimal.ZERO);
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("2", campoLivre.substring(24, 25));
	}
	
	@Test
	public void testWriteCopmletoComDesconto() {
		
		titulo.setDesconto(BigDecimal.TEN);
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("1234123456789051234567890", campoLivre);
	}
	
	@Test
	public void testWriteCopmletoSemDesconto() {
		
		titulo.setDesconto(null);
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("1234123456789051234567892", campoLivre);
	}
}
