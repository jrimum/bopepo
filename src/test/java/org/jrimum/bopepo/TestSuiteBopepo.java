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
 * Created at: 30/03/2008 - 18:12:28
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
 * Criado em: 30/03/2008 - 18:12:28
 * 
 */


package org.jrimum.bopepo;

import org.jrimum.bopepo.campolivre.TestCLBancoDoBrasilNN10;
import org.jrimum.bopepo.campolivre.TestCLBancoDoBrasilNN11;
import org.jrimum.bopepo.campolivre.TestCLBancoDoBrasilNN17Convenio6;
import org.jrimum.bopepo.campolivre.TestCLBancoReal;
import org.jrimum.bopepo.campolivre.TestCLBancoSafraCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.TestCLBancoSafraCobrancaRegistrada;
import org.jrimum.bopepo.campolivre.TestCLBanestes;
import org.jrimum.bopepo.campolivre.TestCLBanrisulCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.TestCLBanrisulCobrancaRegistrada;
import org.jrimum.bopepo.campolivre.TestCLBradesco;
import org.jrimum.bopepo.campolivre.TestCLCaixaEconomicaFederalSICOBNossoNumero14;
import org.jrimum.bopepo.campolivre.TestCLCaixaEconomicaFederalSIGCB;
import org.jrimum.bopepo.campolivre.TestCLCaixaEconomicaFederalSINCO;
import org.jrimum.bopepo.campolivre.TestCLHSBCCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.TestCLItauComCarteirasEspeciais;
import org.jrimum.bopepo.campolivre.TestCLItauPadrao;
import org.jrimum.bopepo.campolivre.TestCLMercantilDoBrasil;
import org.jrimum.bopepo.campolivre.TestCLNossaCaixa;
import org.jrimum.bopepo.campolivre.TestCLSicredi;
import org.jrimum.bopepo.campolivre.TestCLUnibancoCobrancaNaoRegistrada;
import org.jrimum.bopepo.campolivre.TestCLUnibancoCobrancaRegistrada;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith(Suite.class)
@Suite.SuiteClasses
( 
		{ 
			TestBoleto.class,
			TestBoletoUtil.class,
			TestFatorDeVencimento.class,
			TestBancosSuportados.class,
			TestCLBradesco.class,
			TestCLSicredi.class,
			TestCodigoDeBarras.class,
			TestLinhaDigitavel.class,
			TestCLBancoDoBrasilNN10.class,
			TestCLBancoDoBrasilNN11.class,
			TestCLBancoDoBrasilNN17Convenio6.class,
			TestCLBancoReal.class,
			TestCLBancoSafraCobrancaNaoRegistrada.class,
			TestCLBancoSafraCobrancaRegistrada.class,
			TestCLBanestes.class,
			TestCLBanrisulCobrancaNaoRegistrada.class,
			TestCLBanrisulCobrancaRegistrada.class,
			TestCLCaixaEconomicaFederalSIGCB.class,
			TestCLCaixaEconomicaFederalSINCO.class,
			TestCLCaixaEconomicaFederalSICOBNossoNumero14.class,
			TestCLHSBCCobrancaNaoRegistrada.class,
			TestCLItauComCarteirasEspeciais.class,
			TestCLItauPadrao.class,
			TestCLMercantilDoBrasil.class,
			TestCLNossaCaixa.class,
			TestCLUnibancoCobrancaNaoRegistrada.class,
			TestCLUnibancoCobrancaRegistrada.class
		}
)
public class TestSuiteBopepo {

	/*
	 * The class remains completely empty, being used only as a holder for the
	 * above annotations
	 */
}
