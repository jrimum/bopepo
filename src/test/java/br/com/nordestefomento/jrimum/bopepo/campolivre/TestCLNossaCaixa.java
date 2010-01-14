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
 * Created at: 03/10/2008 - 11:39:10
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
 * Criado em: 03/10/2008 - 11:39:10
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.BancoSuportado;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.Pessoa;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Modalidade;
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
 * @version 0.2
 */
public class TestCLNossaCaixa {

	private CampoLivre clNossaCaixa;
	
	private Titulo titulo;
	
	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();
		
		ContaBancaria contaBancaria = new ContaBancaria(BancoSuportado.NOSSA_CAIXA.create());
		contaBancaria.setAgencia(new Agencia(1, "1"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(2818));
		contaBancaria.setModalidade(new Modalidade(13));
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("997654321");
		titulo.setDigitoDoNossoNumero("1");
	}
	
	@Test
	public void testCreate() {
		
		try {
			
			clNossaCaixa = CampoLivreFactory.create(titulo);
			Assert.assertNotNull(clNossaCaixa);
			
		} catch(NotSupportedCampoLivreException e) {
			e.printStackTrace();
			Assert.fail("O campo livre do banco Nossa Caixa deve estar sendo suportado.");
		}
	}
	
	private String getCampoLivreComoString() {
		clNossaCaixa = CampoLivreFactory.create(titulo);
		String campoLivre = clNossaCaixa.write();
		return campoLivre;
	}
	
	@Test
	public void testWriteNotNull() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertNotNull(campoLivre);
	}
	
	@Test
	public void testWriteCampo1Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("9", campoLivre.substring(0, 1));
	}
	
	@Test
	public void testWriteCampo2Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("90000001", campoLivre.substring(1, 9));
	}
	
	@Test
	public void testWriteCampo3Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("0001", campoLivre.substring(9, 13));
	}
	
	@Test
	public void testWriteCampo4Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("3", campoLivre.substring(13, 14));
	}
	
	@Test
	public void testWriteCampo5Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("002818", campoLivre.substring(14, 20));
	}
	
	@Test
	public void testWriteCampo6Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("151", campoLivre.substring(20, 23));
	}
	
	@Test
	public void testWriteCampo7Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("3", campoLivre.substring(23, 24));
	}
	
	@Test
	public void testWriteCampo8Correto() {
		
		String campoLivre = getCampoLivreComoString();
		
		Assert.assertEquals("0", campoLivre.substring(24, 25));
	}
}
