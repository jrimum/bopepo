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
package org.jrimum.bopepo.campolivre;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import org.jrimum.bopepo.BancoSuportado;
import org.jrimum.bopepo.campolivre.CLMercantilDoBrasil;
import org.jrimum.bopepo.campolivre.CampoLivreFactory;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Teste unitário do campo livre do banco mercantil do brasil
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 * 
 */
public class TestCLMercantilDoBrasil extends CampoLivreTest {

	private Titulo titulo;
	
	@Before
	public void setUp() throws Exception {
		
		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");
		
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancoSuportado.MERCANTIL_DO_BRASIL.create());
		
		contaBancaria.setAgencia(new Agencia(1234, "1"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(123456789));
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("1234567890");
		titulo.setDigitoDoNossoNumero("5");
		
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		setClasseGeradoraDoCampoLivre(CLMercantilDoBrasil.class);
		setCampoLivreValidoAsString("1234123456789051234567892"); //Sem desconto
	}
	
	@Test
	public void testWriteComDesconto() {
		
		titulo.setDesconto(BigDecimal.TEN);
		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		Assert.assertEquals("1234123456789051234567890", getCampoLivreToTest().write());
	}
}
