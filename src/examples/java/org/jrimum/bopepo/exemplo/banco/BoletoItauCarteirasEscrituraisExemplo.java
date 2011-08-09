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
 * Created at: 09/08/2011 - 19:40:00
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
 * Criado em: 09/08/2011 - 19:40:00
 * 
 */
package org.jrimum.bopepo.exemplo.banco;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;

/**
 * 
 * <p>
 * Exemplo do boleto para o Banco Itaú com as carteiras especiais
 * </p>
 * <p>
 * Mostra um exemplo funcional que gere um boleto para a implementação de campo livre
 * do Banco Itaú com carteiras especiais
 * </p>
 * 
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:fernandohsmartin@gmail.com">Fernando Martin</a>
 * 
 * @version 0.2
 */
public class BoletoItauCarteirasEscrituraisExemplo extends AbstractBoletoExemplo {

	@Override
	protected BancosSuportados getBancoSuportado() {
		return BancosSuportados.BANCO_ITAU;
	}

	/**
	 * No javadoc do método {@link org.jrimum.bopepo.campolivre.CLItauPadrao#calculeDigitoDaPosicao31}
	 * há detalhes das carteiras que são escriturais.
	 * */
	@Override
	protected Carteira getCarteira() {
		return new Carteira(112);
	}

	@Override
	protected String getNossoNumero() {
		return "12345678";
	}
	
	@Override
	protected String getNumeroDoDocumento() {
		return "123456789";
	}
}
