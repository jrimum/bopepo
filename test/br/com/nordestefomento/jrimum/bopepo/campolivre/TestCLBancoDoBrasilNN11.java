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
 * Created at: 30/03/2008 - 18:13:47
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
 * Criado em: 30/03/2008 - 18:13:47
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.BancoSuportado;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.Pessoa;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * 
 * Teste unitário do campo livre do banco do brasil com o nosso número
 * de tamanho igual a 11.
 * 
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoDoBrasilNN11{

	private CampoLivre clBancoDoBrasil;
	
	private Titulo titulo;
	
	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancoSuportado.BANCO_DO_BRASIL.create());
		
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
		clBancoDoBrasil = CampoLivreFactory.create(titulo);
		
		assertNotNull(clBancoDoBrasil);
	}
	
	@Test
	public void testWrite() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico feliz
		clBancoDoBrasil = CampoLivreFactory.create(titulo);
		
		assertTrue(clBancoDoBrasil.write().length() == 25);
		assertEquals("1234567890112340000678905",clBancoDoBrasil.write());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		contaBancaria.setBanco(BancoSuportado.BANCO_DO_BRASIL.create());
		
		Agencia agencia = new Agencia(0,'1');
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCarteira(new Carteira(0));
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(0);
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo.getContaBancaria();
		
		titulo.setNossoNumero("00000000000");
		
		clBancoDoBrasil = CampoLivreFactory.create(titulo);
		
		assertTrue(clBancoDoBrasil.write().length() == 25);
		assertEquals("0000000000000000000000000",clBancoDoBrasil.write());
	}
}
