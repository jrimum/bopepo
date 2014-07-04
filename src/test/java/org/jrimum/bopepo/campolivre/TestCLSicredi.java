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

import static org.jrimum.bopepo.parametro.ParametroBancoSicredi.POSTO_DA_AGENCIA;

import java.math.BigDecimal;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
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
public class TestCLSicredi extends AbstractCampoLivreBaseTest<CLSicredi> {

	private final int NOSSO_NUMERO_LENGTH = 8;
	
	@Before
	public void setUp(){

		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_SICREDI.create());
		titulo.getContaBancaria().setAgencia(new Agencia(165));
		titulo.getContaBancaria().setCarteira(new Carteira(1, TipoDeCobranca.SEM_REGISTRO));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(623));
		titulo.setNossoNumero("07200003");
		titulo.setDigitoDoNossoNumero("1");
		titulo.setParametrosBancarios(new ParametrosBancariosMap(POSTO_DA_AGENCIA, 2));
		titulo.setValor(new BigDecimal("150.35"));
		
		createCampoLivreToTest();

		setCampoLivreEsperadoComoString("3107200003101650200623101");
	}
	
	@Test
	public void seEscritaOndeTituloNaoTemValorEstahCorreta(){
		
		titulo.setValor(new BigDecimal("0.00"));
		
		createCampoLivreToTest();

		setCampoLivreEsperadoComoString("3107200003101650200623004");
		
		seCampoLivreEscritoEstaCorreto();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoNegativo() {

		testeSeNaoPermiteCarteiraComCodigoNegativo();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraQueNaoSimples() {

		titulo.getContaBancaria().setCarteira(new Carteira(3, TipoDeCobranca.SEM_REGISTRO));

		createCampoLivreToTest();

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteRegistroDaCarteiraNull() {

		titulo.getContaBancaria().setCarteira(new Carteira(1));

		createCampoLivreToTest();

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroNulo() {

		testeSeNaoPermiteNossoNumeroNulo();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComBrancos() {

		testeSeNaoPermiteNossoNumeroComBrancos(NOSSO_NUMERO_LENGTH);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComEspacos() {

		testeSeNaoPermiteNossoNumeroComEspacos(NOSSO_NUMERO_LENGTH);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComTamanhoDiferenteDe11() {

		testeSeNaoPermiteNossoNumeroComTamanhoDiferenteDoEspecificado(NOSSO_NUMERO_LENGTH - 1);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDoNossoNumeroAusente() {

		titulo.setDigitoDoNossoNumero("");

		createCampoLivreToTest();

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDoNossoNumeroComTamanhoDiferenteDe1() {

		titulo.setDigitoDoNossoNumero("124");

		createCampoLivreToTest();

		seCampoLivreEscritoEstaCorreto();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void seNaoPermiteAgenciaComCodigoNegativo() {

		testeSeNaoPermiteAgenciaComCodigoNegativo();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteAgenciaComCodigoZero() {

		testeSeNaoPermiteAgenciaComCodigoZero();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteAgenciaNula() {

		testeSeNaoPermiteAgenciaNula();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaAgenciaAcimaDe4Digitos() {

		testeSeNaoPermiteNumeroDaAgenciaComDigitosAcimaDoLimite(10000);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteParametroBancarioNulo() {

		testeSeNaoPermiteParametroBancarioNulo();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteParametroBancarioPostoDaAgenciaAusente() {

		testeSeNaoPermiteParametroBancarioAusente();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void seNaoPermiteParametroBancarioPostoDaAgenciaSemValor() {

		testeSeNaoPermiteParametroBancarioSemValor(POSTO_DA_AGENCIA);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaNulo() {

		testeSeNaoPermiteNumeroDaContaNulo();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaComCodigoZero() {

		testeSeNaoPermiteNumeroDaContaComCodigoZero();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaComCodigoNegativo() {

		testeSeNaoPermiteNumeroDaContaComCodigoNegativo();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaComCodigoAcimaDe5Digitos() {

		testeSeNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(123456);
	}
	
	@Test(expected = NullPointerException.class)
	public void seNaoPermiteValorDoTituloNulo() {

		testeSeNaoPermiteValorDoTituloNulo();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteValorDoTituloNegativo() {

		testeSeNaoPermiteValorDoTituloNegativo();
	}
}
