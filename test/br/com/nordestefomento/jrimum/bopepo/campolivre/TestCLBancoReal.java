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
 * Created at: 30/03/2008 - 18:14:06
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
 * Criado em: 30/03/2008 - 18:14:06
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.bopepo.campolivre.Factory4CampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;

public class TestCLBancoReal {
	
	private ICampoLivre clReal;
	
	private Titulo titulo;

	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_ABN_AMRO_REAL.newInstance());
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(1018);
		contaBancaria.setAgencia(agencia);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(16324);
		numeroDaConta.setDigitoDaConta("0");//Não importa para o CampoLivre
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("1234567890123");
		titulo.setNossoNumero("5020");
	}

	@Test
	public void testGetInstanceTitulo() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico
		clReal = Factory4CampoLivre.create(titulo);
		
		assertNotNull(clReal);
	}

	@Test
	public void testToString() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico feliz
		clReal = Factory4CampoLivre.create(titulo);
		
		assertTrue(clReal.write().length() == 25);
		assertEquals("1018001632491234567890123",clReal.write());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_ABN_AMRO_REAL.newInstance());
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(0);
		contaBancaria.setAgencia(agencia);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(0);
		numeroDaConta.setDigitoDaConta("0");
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo.getContaBancaria();
		titulo.setNumeroDoDocumento("0");
		
		clReal = Factory4CampoLivre.create(titulo);
		
		assertTrue(clReal.write().length() == 25);
		assertEquals("0000000000000000000000000",clReal.write());
		
	}

}
