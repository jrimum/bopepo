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
 * Created at: 19/07/2008 - 10:58:09
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
 * Criado em: 19/07/2008 - 10:58:09
 * 
 */
package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.excludes.CampoLivreBaseTest;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * <p>
 * Valida a implementação do campo livre com cobrança registrada
 * para o Banco Banrisul.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:samuelvalerio@gmail.com">Samuel Valério</a> Valerio
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBanrisulCobrancaRegistrada extends CampoLivreBaseTest {

	private Titulo titulo;

	@Before
	public void setUp() {

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");
		
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL.create());
		contaBancaria.setCarteira(new Carteira(1, TipoDeCobranca.COM_REGISTRO));
		contaBancaria.setAgencia(new Agencia(1102));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(9000150));

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("22832563");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));
		
		setClasseGeradoraDoCampoLivre(CLBanrisulCobrancaRegistrada.class);
		setCampoLivreValidoAsString("1111029000150228325634071");
	}

	@Test(expected=CampoLivreException.class)
	public void criacaoSemTipoDeCobranca() {
		
		titulo.getContaBancaria().setCarteira(new Carteira(1, null));
		CampoLivreFactory.create(titulo);
	}
	
	@Test(expected=CampoLivreException.class)
	public void criacaoSemAgencia() {
		
		titulo.getContaBancaria().setAgencia(null);
		CampoLivreFactory.create(titulo);
	}

	@Test(expected=CampoLivreException.class)
	public void criacaoSemNumeroDaConta() {
		
		titulo.getContaBancaria().setNumeroDaConta(null);
		CampoLivreFactory.create(titulo);
	}
	
	@Test(expected=CampoLivreException.class)
	public void criacaoSemNossoNumero() {
		
		titulo.setNossoNumero(null);
		CampoLivreFactory.create(titulo);
	}

	@Test(expected=CampoLivreException.class)
	public void criacaoAgenciaComCodigoMaiorQue4Digitos() {
		
		titulo.getContaBancaria().setAgencia(new Agencia(10000, "1"));
		CampoLivreFactory.create(titulo);
	}
	
	@Test(expected=CampoLivreException.class)
	public void criacaoNumeroDaContaMaiorQue7Digitos() {
		
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(12345678));
		CampoLivreFactory.create(titulo);
	}
	
	@Test(expected=CampoLivreException.class)
	public void criacaoNossoNumeroMaiorQue8Digitos(){
		
		titulo.setNossoNumero("123456789");
		CampoLivreFactory.create(titulo);
	}

}
