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
 * Created at: 21/04/2008 - 22:36:47
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
 * Criado em: 21/04/2008 - 22:36:47
 * 
 */
	
package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do banco safra para cobrança não registrada
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 *
 */
public class TestCLBancoSafraCobrancaNaoRegistrada extends AbstractCLBancoSafraBaseTest<CLBancoSafraCobrancaNaoRegistrada> {
	
	private final int NOSSO_NUMERO_LENGTH = 17;
	
	@Before
	public void setUp(){
		
		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_SAFRA.create());
		titulo.getContaBancaria().setAgencia(new Agencia(1730, "0"));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(12110, "0"));
		titulo.getContaBancaria().setCarteira(new Carteira(6, TipoDeCobranca.SEM_REGISTRO));
		titulo.setNossoNumero("12345678901234567");

		createCampoLivreToTest();

		setCampoLivreEsperadoComoString("7121100123456789012345674");
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
	public void seNaoPermiteNumeroDaContaComCodigoAcimaDe8Digitos() {

		testeSeNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(123456789);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaNulo() {
		
		testeSeNaoPermiteDigitoDaContaNulo();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaVazio() {
		
		testeSeNaoPermiteDigitoDaContaVazio();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaNegativo() {
		
		testeSeNaoPermiteDigitoDaContaNegativo();
	}

	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaNaoNumerico() {
		
		testeSeNaoPermiteDigitoDaContaNaoNumerico();
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
	public void seNaoPermiteNossoNumeroComTamanhoDiferenteDe9() {

		testeSeNaoPermiteNossoNumeroComTamanhoDiferenteDoEspecificado(NOSSO_NUMERO_LENGTH - 1);
	}
	
}
