/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 17/02/2011 - 12:40:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 17/02/2011 - 12:40:00
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Teste unitário do campo livre do Banco Bancoob para cobrança não registrada.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCLBancoobCobrancaNaoRegistrada extends AbstractCampoLivreBaseTest<CLBancoobCobrancaNaoRegistrada>{
	
	private final int NOSSO_NUMERO_LENGTH = 7;

	@Before
	public void setUp(){

		titulo.getContaBancaria().setBanco(BancosSuportados.BANCOOB.create());
		titulo.getContaBancaria().setAgencia(new Agencia(4340));
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(1, "0"));
		titulo.getContaBancaria().setCarteira(new Carteira(1));
		titulo.setNossoNumero("0200000");
		titulo.setDigitoDoNossoNumero("1");

		createCampoLivreToTest();

		setCampoLivreEsperadoComoString("1434001000001002000001001");
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNossoNumeroComTamanhoDiferenteDe7() {
		testeSeNaoPermiteNossoNumeroComTamanhoDiferenteDoEspecificado(NOSSO_NUMERO_LENGTH + 1);
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaNulo() {
		testeSeNaoPermiteDigitoDaContaNulo();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaNaoNumero() {
		testeSeNaoPermiteDigitoDaContaNaoNumerico();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaNegativo() {
		testeSeNaoPermiteDigitoDaContaNegativo();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteDigitoDaContaVazio() {
		testeSeNaoPermiteDigitoDaContaVazio();
	}
	
	@Test(expected = CampoLivreException.class)
	public void seNaoPermiteNumeroDaContaMaiorQueSeisDigitos() {
		testeSeNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(1000000);
	}
}
