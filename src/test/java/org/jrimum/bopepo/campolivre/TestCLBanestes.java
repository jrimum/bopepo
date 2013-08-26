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
 * Created at: Dec 13, 2008 - 1:07:16 PM
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
 * Criado em: Dec 13, 2008 - 1:07:16 PM
 * 
 */
package org.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * <p>
 * Valida a implementação do campo livre para o Banco Banestes.
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
public class TestCLBanestes extends AbstractCampoLivreBaseTest<CLBanestes> {
	
	@Before
	public void setUp() {
		
		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_DO_ESTADO_DO_ESPIRITO_SANTO.create());
		titulo.getContaBancaria().setCarteira(new Carteira(4, TipoDeCobranca.COM_REGISTRO));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(7730070));
		titulo.setNossoNumero("10297");
		
		createCampoLivreToTest();

		setCampoLivreEsperadoComoString("0001029700007730070402182");
	}
	
	/**
	 * Configura, no título, a carteira utilizada no teste.
	 * 
	 * @param carteira
	 */
	private void setCarteiraDoTitulo(Carteira carteira) {
		titulo.getContaBancaria().setCarteira(carteira);
	}
	
	@Test
	public void seOWriteRetornaOValorEsperadoParaUmaCarteiraSemRegistro() {
		
		final Carteira carteira = new Carteira();
		carteira.setTipoCobranca(TipoDeCobranca.SEM_REGISTRO);
		setCarteiraDoTitulo(carteira);
		
		createCampoLivreToTest();
		
		assertEquals("Testando um campo livre válido da carteira sem registro.", "0001029700007730070202108", writeCampoLivre());
	}
	
	@Test
	public void seOWriteRetornaOValorEsperadoParaUmaCarteiraCaucionada() {
		
		setCarteiraDoTitulo(new Carteira(3, TipoDeCobranca.COM_REGISTRO));

		createCampoLivreToTest();
		
		assertEquals("Testando um campo livre válido da carteira caucionada.", "0001029700007730070302196", writeCampoLivre());
	}
	
	@Test(expected=CampoLivreException.class)
	public void criacaoSemTipoDeCobranca() {
		
		setCarteiraDoTitulo(new Carteira());
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
	public void criacaoNossoNumeroMaiorQue8Digitos() {
		
		titulo.setNossoNumero("123456789");
		CampoLivreFactory.create(titulo);
	}
}
