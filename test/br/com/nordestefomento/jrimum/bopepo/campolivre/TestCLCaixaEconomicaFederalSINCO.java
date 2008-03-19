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
 * Created at / Criado em : 22/03/2007 - 08:31:22
 * 
 */

package br.com.nordestefomento.jrimum.bopepo.campolivre;

import junit.framework.TestCase;
import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;

/**
 * 
 * Teste da classe <code>CL_CaixaEconômicaFederal_SINCO</code>.
 * 
 * 
 * @author Gabriel Guimarães
 * @author Gilmar P.S.L
 * @author Misael Barreto
 * @author Rômulo Augusto
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
public class TestCLCaixaEconomicaFederalSINCO extends TestCase {
	
	private ICampoLivre clCaixaSINCO;
	
	private Titulo titulo;

	protected void setUp() throws Exception {
		
		super.setUp();
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();
		
		ContaBancaria contaBancaria = new ContaBancaria();
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(2);
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		contaBancaria.setBanco(EnumBanco.CAIXA_ECONOMICA_FEDERAL);
		
		cedente.addContaBancaria(contaBancaria);
		
		titulo = Titulo.getInstance(sacado, cedente);
	}

	public void testGetInstanceTitulo() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {

		//Básico:
		titulo.setNossoNumero("10000000020061732");
		clCaixaSINCO = FactoryCampoLivre.getInstance(titulo);
	
		assertNotNull(clCaixaSINCO);
		//
		
		clCaixaSINCO = null;

		//Nosso número > 17:
		titulo.setNossoNumero("010000000020061732");
		
		clCaixaSINCO = FactoryCampoLivre.getInstance(titulo);
		assertNull(clCaixaSINCO);
	}

	public void testWrite() throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		titulo.setNossoNumero("10000000020061732");
		
		clCaixaSINCO = FactoryCampoLivre.getInstance(titulo);
		
		assertEquals("1000002910000000020061732", clCaixaSINCO.write());
	}

}
