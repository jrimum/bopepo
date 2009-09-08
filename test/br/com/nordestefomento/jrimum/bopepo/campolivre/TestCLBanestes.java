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
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;

/**
 * 
 * <p>
 * Valida a implementação do campo livre para o Banco Banestes.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author Samuel Valerio
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBanestes {
	
	private CampoLivre clBanestes;
	private Titulo titulo;
	
	@Before
	public void inicializa() {

		ContaBancaria contaBancaria = new ContaBancaria(
				EnumBancos.BANCO_DO_ESTADO_DO_ESPIRITO_SANTO.create());
		contaBancaria.setCarteira(new Carteira(4, EnumTipoCobranca.COM_REGISTRO));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(7730070));

		titulo = new Titulo(contaBancaria,
				new Pessoa("Nordeste Fomento"), new Pessoa("João Pereira"));
		titulo.setNossoNumero("10297");
		clBanestes = CampoLivreFactory.create(titulo);
		
	}
	
	@Test
	public void seOCampoLivreNaoEstaNulo() {
		assertNotNull("Se o campo livre com registro criado não é nulo.",
				clBanestes);
	}
	
	@Test
	public void seOWriteRetornaUmaStringNaoNula() {
		assertNotNull("Todo campo livre retorna uma string não nula.",
				clBanestes.write());
	}
	
	@Test
	public void seOTamanhoRetornadoPeloWriteEh25() {
		assertEquals("Todo campo livre com registro deve ter tamanho 25."
				+ clBanestes, 25, clBanestes
				.write().length());
	}
	
	@Test
	public void seOWriteRetornaOValorEsperadoParaUmaCarteiraComRegistro() {
		assertEquals(
				"Testando um campo livre válido da carteira com registro.",
				"0001029700007730070402182", clBanestes.write());
	}
	
	@Test
	public void seOWriteRetornaOValorEsperadoParaUmaCarteiraSemRegistro() {
		final Carteira carteiraSemRegistro = new Carteira();
		carteiraSemRegistro.setTipoCobranca(EnumTipoCobranca.SEM_REGISTRO);
		titulo.getContaBancaria().setCarteira(carteiraSemRegistro);
		clBanestes = CampoLivreFactory.create(titulo);
		assertEquals(
				"Testando um campo livre válido da carteira sem registro.",
				"0001029700007730070202108", clBanestes.write());
	}
	
	@Test
	public void seOWriteRetornaOValorEsperadoParaUmaCarteiraCaucionada() {
		final Carteira carteiraCaucionada = new Carteira(3, EnumTipoCobranca.COM_REGISTRO);
		titulo.getContaBancaria().setCarteira(carteiraCaucionada);
		clBanestes = CampoLivreFactory.create(titulo);
		assertEquals(
				"Testando um campo livre válido da carteira caucionada.",
				"0001029700007730070302196", clBanestes.write());
	}
	
	@Test(expected=CampoLivreException.class)
	public void criacaoSemTipoDeCobranca() {
		titulo.getContaBancaria().setCarteira(new Carteira());
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
	public void criacaoNossoNumeroMaiorQue8Digitos(){
		titulo.setNossoNumero("123456789");
		CampoLivreFactory.create(titulo);
	}
	
}
