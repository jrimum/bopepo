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
 * Created at: 16/04/2008 - 23:09:08
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
 * Criado em: 16/04/2008 - 23:09:08
 * 
 */

package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.BancoSuportado;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.Pessoa;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;

public class TestCLItauComCarteirasEspeciais {
	
	private CampoLivre clItauEspecial;
	
	private Titulo titulo;
	
	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancoSuportado.BANCO_ITAU.create());
		
		contaBancaria.setAgencia(new Agencia(57, "1"));
		contaBancaria.setCarteira(new Carteira(198));
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(12345);
		numeroDaConta.setDigitoDaConta("7");//Não importa para o CampoLivre
		contaBancaria.setNumeroDaConta(numeroDaConta);

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("1234567");
		titulo.setNossoNumero("12345678");
	}
	
	@Test
	public void testCreate() {
		
		Assert.assertNotNull(CampoLivreFactory.create(titulo));
	}
	
	@Test
	public void testWriteNaoNulo() {
		
		clItauEspecial = CampoLivreFactory.create(titulo);
		
		Assert.assertNotNull(clItauEspecial.write());
	}
	
	@Test
	public void testWriteValido() {
		
		clItauEspecial = CampoLivreFactory.create(titulo);
		
		Assert.assertEquals(25, clItauEspecial.write().length());
		Assert.assertEquals("1981234567812345671234580", clItauEspecial.write());
	}
	
	@Test
	public void testWriteComValoresInvalidos() {
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		contaBancaria.setBanco(BancoSuportado.BANCO_ITAU.create());
		
		contaBancaria.setAgencia(new Agencia(0, "1"));
		contaBancaria.setCarteira(new Carteira(0));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(0, "0"));
		
		titulo.setNossoNumero("0");
		titulo.setNumeroDoDocumento("0");
		
		clItauEspecial = CampoLivreFactory.create(titulo);
		
		assertTrue(clItauEspecial.write().length() == 25);
		assertEquals("0000000000000000000000000",clItauEspecial.write());
	}
}
