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
 * Created at: 14/01/2014 - 14:01:19
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
 * Criado em: 14/01/2014 - 14:01:19 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.jrimum.utilix.Objects.isNotNull;

import org.jrimum.domkee.financeiro.banco.febraban.EntidadeDeCobranca;

/**
 * Formata os dados como CPRF e Nome de uma pessoa que são impressos no boleto.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoInfoCampoPessoa {

	/**
	 * Retorna o texto formatado no estilo {@code "%nome, %tipo_de_cprf: %cprf"}:
	 * 
	 * <ul>
	 * <li>"Fulando Da Silva, CPF: 111.111.111-11"</li>
	 * <li>"Empresa Boa, CNPJ: 00.123.456/0001-78"</li>
	 * </ul>
	 * 
	 * @param pessoa
	 * @return string formatada para ser exibida no boleto
	 */
	public static String getTextoNomeCprfDaPessoa(EntidadeDeCobranca pessoa) {
		StringBuilder sb = new StringBuilder(EMPTY);
		boolean temNome = false;

		if (isNotNull(pessoa)) {

			if (isNotNull(pessoa.getNome())) {
				temNome = true;
				sb.append(pessoa.getNome());
			}

			if (isNotNull(pessoa.getCPRF())) {
				
				if(temNome){
					sb.append(", ");
				}

				if (pessoa.getCPRF().isFisica()) {
					sb.append("CPF: ");

				} else if (pessoa.getCPRF().isJuridica()) {
					sb.append("CNPJ: ");
				}

				sb.append(pessoa.getCPRF().getCodigoFormatado());
			}
		}

		return sb.toString();
	}

}
