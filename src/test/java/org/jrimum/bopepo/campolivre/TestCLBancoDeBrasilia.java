/*
 * Copyright 2013 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 24/08/2013 - 19:54:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2013 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 24/08/2013 - 19:54:00
 * 
 */

package org.jrimum.bopepo.campolivre;

import static org.jrimum.bopepo.parametro.ParametroBancoDeBrasilia.CHAVE_ASBACE_DIGITO1;
import static org.jrimum.bopepo.parametro.ParametroBancoDeBrasilia.CHAVE_ASBACE_DIGITO2;
import static org.junit.Assert.assertEquals;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do BRB - Banco de Brasília.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoDeBrasilia extends AbstractCampoLivreBaseTest<CLBancoDeBrasilia> {

	private final int NOSSO_NUMERO_LENGTH = 6;

	@Before
	public void setUp() {

		titulo.getContaBancaria().setBanco(
				BancosSuportados.BANCO_DE_BRASILIA.create());
		
		titulo.getContaBancaria().setAgencia(new Agencia(58));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(6002006));
		titulo.getContaBancaria().setCarteira(new Carteira(1,TipoDeCobranca.SEM_REGISTRO));
		titulo.setNossoNumero("000001");

		createCampoLivreToTest();
		
		setCampoLivreEsperadoComoString("0000586002006100000107045");
		assertEquals(4, titulo.getParametrosBancarios().getValor(CHAVE_ASBACE_DIGITO1));
		assertEquals(5, titulo.getParametrosBancarios().getValor(CHAVE_ASBACE_DIGITO2));
	}
	
	@Test
	public void seGeraCorretoParaCarteiraComRegistro(){
		
		titulo.getContaBancaria().setCarteira(new Carteira(2,TipoDeCobranca.COM_REGISTRO));
		
		createCampoLivreToTest();
		
		setCampoLivreEsperadoComoString("0000586002006200000107031");
		
		seCampoLivreEscritoEstaCorreto();
		assertEquals(3, titulo.getParametrosBancarios().getValor(CHAVE_ASBACE_DIGITO1));
		assertEquals(1, titulo.getParametrosBancarios().getValor(CHAVE_ASBACE_DIGITO2));
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteAgenciaNula() {

		testeSeNaoPermiteAgenciaNula();
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
	public void seNaoPermiteNumeroDaAgenciaAcimaDe3Digitos() {

		testeSeNaoPermiteNumeroDaAgenciaComDigitosAcimaDoLimite(1000);
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
	public void seNaoPermiteNumeroDaContaComCodigoAcimaDe7Digitos() {

		testeSeNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(12345678);
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
	public void seNaoPermiteNossoNumeroComTamanhoDiferenteDe6() {

		testeSeNaoPermiteNossoNumeroComTamanhoDiferenteDoEspecificado(NOSSO_NUMERO_LENGTH - 1);
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraNull() {

		testeSeNaoPermiteCarteiraNula();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoNegativo() {

		testeSeNaoPermiteCarteiraComCodigoNegativo();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteCarteiraComCodigoAcimaDe2() {

		testeSeNaoPermiteCarteiraComCodigoAcimaDoLimite(3);
	}

}