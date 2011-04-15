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
 * Created at: 30/03/2008 - 18:14:15
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
 * Criado em: 30/03/2008 - 18:14:15
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
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do banco bradesco.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBradesco extends CampoLivreBaseTest {

	private static final int NOSSO_NUMERO_LENGTH = 11;
	
	private Titulo titulo;

	@Before
	public void setUp() throws Exception {

		ContaBancaria contaBancaria = new ContaBancaria();

		contaBancaria.setBanco(BancosSuportados.BANCO_BRADESCO.create());
		contaBancaria.setAgencia(new Agencia(1234, "1"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(6789));
		contaBancaria.setCarteira(new Carteira(5));

		titulo = new Titulo(contaBancaria, new Sacado("S"), new Cedente("C"));
		titulo.setNossoNumero("12345678901");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		setClasseGeradoraDoCampoLivre(CLBradesco.class);
		setCampoLivreValidoAsString("1234051234567890100067890");
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteAgenciaNula() {

		seNaoPermiteAgenciaNula(titulo);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void seNaoPermiteAgenciaComCodigoNegativo() {

		seNaoPermiteAgenciaComCodigoNegativo(titulo);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteAgenciaComCodigoZero() {

		seNaoPermiteAgenciaComCodigoZero(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaAgenciaAcimaDe4Digitos() {

		seNaoPermiteNumeroDaAgenciaComDigitosAcimaDoLimite(titulo, 10000);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraNull() {

		seNaoPermiteCarteiraNula(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoNegativo() {

		seNaoPermiteCarteiraComCodigoNegativo(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoAcimaDe2Digitos() {

		seNaoPermiteCarteiraComCodigoAcimaDoLimite(titulo, 111);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroNulo() {

		seNaoPermiteNossoNumeroNulo(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComBrancos() {

		seNaoPermiteNossoNumeroComBrancos(titulo, NOSSO_NUMERO_LENGTH);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComEspacos() {

		seNaoPermiteNossoNumeroComEspacos(titulo, NOSSO_NUMERO_LENGTH);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComTamanhoDiferenteDe11() {

		seNaoPermiteNossoNumeroComTamanhoDiferenteDoEspecificado(titulo, NOSSO_NUMERO_LENGTH - 1);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaNulo() {

		seNaoPermiteNumeroDaContaNulo(titulo);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaComCodigoZero() {

		seNaoPermiteNumeroDaContaComCodigoZero(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaComCodigoNegativo() {

		seNaoPermiteNumeroDaContaComCodigoNegativo(titulo);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaComCodigoAcimaDe7Digitos() {

		seNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(titulo, 12345678);
	}
}
