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
 * Created at: 30/03/2008 - 18:14:15
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
 * Criado em: 30/03/2008 - 18:14:15
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;

public class TestCLBradesco{

	private ICampoLivre clBradesco;
	
	private Titulo titulo;

	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_BRADESCO.create());
		
		Agencia agencia = new Agencia(1234,'1');
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCarteira(new Carteira(5));
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("12345678901");
		
	}

	@Test
	public void testGetInstanceTitulo() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico
		clBradesco = CampoLivreFactory.create(titulo);
		
		assertNotNull(clBradesco);
	}
	
	@Test
	public void testWrite() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico feliz
		clBradesco = CampoLivreFactory.create(titulo);
		
		assertTrue(clBradesco.write().length() == 25);
		assertEquals("1234051234567890100067890",clBradesco.write());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_BRADESCO.create());
		
		Agencia agencia = new Agencia(0,'1');
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCarteira(new Carteira(0));
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(0);
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo.setNossoNumero("0");
		
		clBradesco = CampoLivreFactory.create(titulo);
		
		assertTrue(clBradesco.write().length() == 25);
		assertEquals("0000000000000000000000000",clBradesco.write());
	}

}
