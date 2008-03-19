/*
 * Copyright 2007, JMatryx Group
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright 2007, Grupo JMatryx
 * 
 * Licenciado sob a licença da Apache, versão 2.0 (a “licença”); você não pode
 * usar este arquivo exceto na conformidade com a licença. Você pode obter uma
 * cópia da licença em
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * A menos que seja requerido pela aplicação da lei ou esteja de acordo com a
 * escrita, o software distribuído sob esta licença é distribuído “COMO É”
 * BASE,SEM AS GARANTIAS OU às CONDIÇÕES DO TIPO, expresso ou implicado. Veja a
 * licença para as permissões sobre a línguagem específica e limitações quando
 * sob licença.
 * 
 * 
 * Created at / Criado em : 19/04/2007 - 11:02:18
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import junit.framework.TestCase;
import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;

/**
 * 
 * Teste unitário do campo livre do banco do brasil com o nosso número
 * de tamanho igual a 11.
 * 
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
public class TestCLBancoDoBrasilNN11 extends TestCase {

	private ICampoLivre clBancoDoBrasil;
	
	private Titulo titulo;
	
	protected void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBanco.BANCO_DO_BRASIL);
		
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
		
	}

	public void testGetInstanceTitulo() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico
		clBancoDoBrasil = FactoryCampoLivre.getInstance(titulo);
		
		assertNotNull(clBancoDoBrasil);
	}
	
	public void testWrite() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		//básico feliz
		clBancoDoBrasil = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBancoDoBrasil.write().length() == 25);
		assertEquals("1234567890112340000678905",clBancoDoBrasil.write());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		contaBancaria.setBanco(EnumBanco.BANCO_DO_BRASIL);
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(0);
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCodigoDaCarteira(0);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(0);
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo.getCedente().addContaBancaria(contaBancaria);
		
		titulo.setNossoNumero("0");
		
		clBancoDoBrasil = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBancoDoBrasil.write().length() == 25);
		assertEquals("0000000000000000000000000",clBancoDoBrasil.write());
	}
}
