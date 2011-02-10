/*
 * Copyright 2010 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 25/11/2010 - 15:35:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2010 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 25/11/2010 - 15:35:00
 * 
 */

package org.jrimum.bopepo.campolivre;

import java.math.BigDecimal;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.excludes.CampoLivreBaseTest;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
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
 * <p>
 * Teste unitário do campo livre do banco SICREDI.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLSicredi extends CampoLivreBaseTest {

	private Titulo titulo;

	@Before
	public void setUp() throws Exception {

		ContaBancaria contaBancaria = new ContaBancaria();
		
		contaBancaria.setBanco(BancosSuportados.BANCO_SICREDI.create());
		contaBancaria.setAgencia(new Agencia(165));
		contaBancaria.setCarteira(new Carteira(1, TipoDeCobranca.SEM_REGISTRO));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(623));

		titulo = new Titulo(contaBancaria,  new Sacado("S"), new Cedente("C"));
		titulo.setNossoNumero("07200003");
		titulo.setDigitoDoNossoNumero("1");
		titulo.setParametrosBancarios(new ParametrosBancariosMap("PostoDaAgencia",02));
		titulo.setValor(new BigDecimal("150.35"));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		setClasseGeradoraDoCampoLivre(CLSicredi.class);
		setCampoLivreValidoAsString("3107200003101650200623101");
	}
	

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraNull() {

		titulo.getContaBancaria().setCarteira(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoNegativo() {

		titulo.getContaBancaria().setCarteira(new Carteira(-1,  TipoDeCobranca.SEM_REGISTRO));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraQueNaoSimples() {

		titulo.getContaBancaria().setCarteira(new Carteira(3, TipoDeCobranca.SEM_REGISTRO));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteRegistroDaCarteiraNull() {

		titulo.getContaBancaria().setCarteira(new Carteira(1));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroNull() {

		titulo.setNossoNumero(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComBrancos() {

		titulo.setNossoNumero("           ");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComEspacos() {

		titulo.setNossoNumero("01234 56789");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComTamanhoDiferenteDe8() {

		titulo.setNossoNumero("1234567");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDoNossoNumeroAusente() {

		titulo.setDigitoDoNossoNumero("");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDoNossoNumeroComTamanhoDiferenteDe1() {

		titulo.setDigitoDoNossoNumero("124");

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		seCampoLivreEscritoEstaCorreto();
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
	public void seNaoPermiteAgenciaNula() {

		seNaoPermiteAgenciaNula(titulo);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaAgenciaAcimaDe4Digitos() {

		seNaoPermiteNumeroDaAgenciaComDigitosAcimaDoLimite(titulo, 10000);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteParametroBancarioNulo() {

		seNaoPermiteParametroBancarioNulo(titulo);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteParametroBancarioPostoDaAgenciaAusente() {

		seNaoPermiteParametroBancarioAusente(titulo);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void seNaoPermiteParametroBancarioPostoDaAgenciaSemValor() {

		seNaoPermiteParametroBancarioSemValor(titulo, "PostoDaAgencia");
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
	public void seNaoPermiteNumeroDaContaComCodigoAcimaDe5Digitos() {

		seNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(titulo, 123456);
	}
	
	@Test(expected = NullPointerException.class)
	public void seNaoPermiteValorDoTituloNulo() {

		seNaoPermiteValorDoTituloNulo(titulo);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteValorDoTituloNegativo() {

		seNaoPermiteValorDoTituloNegativo(titulo);
	}
}
