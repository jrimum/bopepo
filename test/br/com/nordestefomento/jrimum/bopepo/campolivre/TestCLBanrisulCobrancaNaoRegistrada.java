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
 * Created at: 02/08/2008 - 16:37:47
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
 * Criado em: 02/08/2008 - 16:37:47
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;

/**
 * 
 * <p>
 * Valida a implementação do campo livre com cobrança não registrada para o Banco
 * Banrisul.
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
public class TestCLBanrisulCobrancaNaoRegistrada {

	private ICampoLivre clBanrisulCobrancaNaoRegistrada;
	private Titulo titulo;

	@Before
	public void inicializa() {

		ContaBancaria contaBancaria = new ContaBancaria(
				EnumBancos.BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL.create());
		contaBancaria
		.setCarteira(new Carteira(1, EnumTipoCobranca.SEM_REGISTRO));
		contaBancaria.setAgencia(new Agencia(100,'1'));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(1));

		titulo = new Titulo(contaBancaria, new Pessoa("Nordeste Fomento"),
				new Pessoa("João Pereira"));
		titulo.setNossoNumero("22832563");
		clBanrisulCobrancaNaoRegistrada = CampoLivreFactory.create(titulo);
	}

	@Test
	public void seOCampoLivreNaoEstaNulo() {
		assertNotNull("Se o campo livre sem registro criado não é nulo.",
				clBanrisulCobrancaNaoRegistrada);
	}

	@Test
	public void seOWriteRetornaUmaStringNaoNula() {
		assertNotNull("Todo campo livre retorna uma string não nula.",
				clBanrisulCobrancaNaoRegistrada.write());
	}

	@Test
	public void seOTamanhoRetornadoPeloWriteEh25() {
		assertEquals("Todo campo livre sem registro deve ter tamanho 25"
				+ clBanrisulCobrancaNaoRegistrada, 25,
				clBanrisulCobrancaNaoRegistrada.write().length());
	}

	@Test
	public void seOWriteRetornaOValorEsperado() {
		assertEquals(
				"Testando um campo livre válido da carteira sem registro.",
				"2110000000012283256304168", clBanrisulCobrancaNaoRegistrada
						.write());
	}

	@Test(expected = CampoLivreException.class)
	public void criacaoSemTipoDeCobranca() {
		titulo.getContaBancaria().setCarteira(new Carteira(1, null));
		CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void criacaoSemAgencia() {
		titulo.getContaBancaria().setAgencia(null);
		CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void criacaoSemNumeroDaConta() {
		titulo.getContaBancaria().setNumeroDaConta(null);
		CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void criacaoSemNossoNumero() {
		titulo.setNossoNumero(null);
		CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void criacaoAgenciaComCodigoMaiorQue3Digitos() {
		titulo.getContaBancaria().setAgencia(new Agencia(1000,'1'));
		CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void criacaoNumeroDaContaMaiorQue7Digitos() {
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(12345678));
		CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void criacaoNossoNumeroMaiorQue8Digitos() {
		titulo.setNossoNumero("123456789");
		CampoLivreFactory.create(titulo);
	}

}
