
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
 * Created at: 25/04/2008 - 01:53:23
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
 * Criado em: 25/04/2008 - 01:53:23
 * 
 */
	
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;


/**
 * 
 * <p>
 * DEFINIÇÃO DA CLASSE
 * </p>
 * 
 * <p>
 * OBJETIVO/PROPÓSITO
 * </p>
 * 
 * <p>
 * EXEMPLO: 
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class TestCLUnibancoCobrancaRegistrada {

	/**
	 * String Campo Livre.
	 */
	private static String TEST_CASE = "0401123100019112233445540";

	private CampoLivre campoLivre;

	private Titulo titulo;
	
	/**
	 * <p>
	 * Cobrança com registro<br />
	 * <br />
	 * Situação: precisamos emitir um título para um sacado com essas
	 * características:
	 * </p>
	 * <p>
	 * Banco: UNIBANCO – identificação 409<br />
	 * Agência: 0001-9<br />
	 * Moeda: Real – R$ - identificação 9<br />
	 * Vencimento: 31 de dezembro de 2001<br />
	 * Valor: R$1000,00<br />
	 * Código para transação CVT: 04 (cobrança com registro – 5539-5)<br />
	 * “Nosso Número”: 11223344554<br />
	 * </p>
	 * 
	 * <p>
	 * NÚMERO DE ORIGEM DO CÓDIGO DE BARRAS:<br />
	 * <br />
	 * 
	 * <pre>
	 * 409 9  1  1546  0000100000  04  011231  0001  9  11223344554  0
	 * </pre>
	 * 
	 * <br />
	 * LINHA DIGITÁVEL: <br />
	 * 
	 * <pre>
	 * 40990.40117  20100.019110  22334.455403  1  15460000100000
	 * </pre>
	 * 
	 * </p>
	 * 
	 * 
	 * @throws Exception
	 * 
	 * @since
	 */
	@Before
	public void setUp() throws Exception {
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(2001,Calendar.DECEMBER,31);
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.UNIBANCO.create());
		contaBancaria.setAgencia(new Agencia(1, '9'));
		contaBancaria.setCarteira(new Carteira(123,EnumTipoCobranca.COM_REGISTRO));

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("1234567");
		titulo.setNossoNumero("11223344554");

		titulo.setDataDoVencimento(cal.getTime());

		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test
	public final void testGetInstance() {

		assertNotNull(campoLivre);
		assertTrue(campoLivre instanceof CLUnibancoCobrancaRegistrada);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComAgenciaNula() {

		titulo.getContaBancaria().setAgencia(null);
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComAgenciaNegativa() {

		titulo.getContaBancaria().setAgencia(new Agencia(-23,'1'));
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComDigitoDaAgenciaNulo() {

		titulo.getContaBancaria().setAgencia((new Agencia(23, null)));
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComDigitoDaAgenciaNegativo() {

		titulo.getContaBancaria().setAgencia(new Agencia(2, '3'));
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComDigitoDaAgenciaNaoNumerico() {

		titulo.getContaBancaria().setAgencia(new Agencia(-23, 'X'));
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComNossoNumeroNulo() {

		titulo.setNossoNumero(null);
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComNossoNumeroNegativo() {

		titulo.setNossoNumero("-012345679012345");
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public final void testGetInstanceComNossoNumeroNaoNumerico() {

		titulo.setNossoNumero("123456790123y45");
		campoLivre = CampoLivreFactory.create(titulo);
	}

	@Test
	public final void testITextStream() {

		assertEquals(TEST_CASE, campoLivre.write());

		campoLivre.read(TEST_CASE);

		assertEquals(TEST_CASE, campoLivre.write());
	}

}
