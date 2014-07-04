/* 
 * Copyright 2014 JRimum Project
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
 * Created at: 14/01/2014 - 19:27:46
 *
 * ================================================================================
 *
 * Direitos autorais 2014 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 14/01/2014 - 19:27:46 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * Formata o Nosso Número do Título que é impresso no boleto.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoInfoCampoNossoNumero {

	/**
	 * Retorna o nosso número fomatado no estilo "123456-7".
	 * 
	 * 
	 * @param titulo
	 * @return string formatada para ser exibida no boleto
	 */
	public static String getTextoNossoNumero(Titulo titulo) {

		StringBuilder texto = new StringBuilder(EMPTY);

		if (isNotBlank(titulo.getNossoNumero())) {
			texto.append(titulo.getNossoNumero());

			if (isNotBlank(titulo.getDigitoDoNossoNumero())) {
				texto.append("-" + titulo.getDigitoDoNossoNumero());
			}
		}

		return texto.toString();
	}
}
