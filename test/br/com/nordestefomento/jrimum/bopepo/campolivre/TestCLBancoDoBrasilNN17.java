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
 * Created at: 30/03/2008 - 18:13:58
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
 * Criado em: 30/03/2008 - 18:13:58
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import junit.framework.TestCase;
import br.com.nordestefomento.jrimum.bopepo.EnumBanco;
import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;

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
public class TestCLBancoDoBrasilNN17 extends TestCase {

	private ICampoLivre clBancoDoBrasil;
	
	private Titulo titulo;
	
	protected void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBanco.BANCO_DO_BRASIL);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(123456);
		contaBancaria.setNumeroDaConta(numeroDaConta);

		cedente.addContaBancaria(contaBancaria);
		
		titulo = Titulo.getInstance(sacado, cedente);
		titulo.setNossoNumero("12345678901234567");
		
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
		assertEquals("1234561234567890123456721",clBancoDoBrasil.write());
		
		//Infeliz básico
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		contaBancaria.setBanco(EnumBanco.BANCO_DO_BRASIL);

		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(0);
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		titulo.getCedente().addContaBancaria(contaBancaria);
		
		titulo.setNossoNumero("00000000000000001");
		
		clBancoDoBrasil = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBancoDoBrasil.write().length() == 25);
		assertEquals("0000000000000000000000121",clBancoDoBrasil.write());
	}
}