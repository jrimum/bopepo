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
 * Created at: 17/01/2014 - 19:32:45
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
 * Criado em: 17/01/2014 - 19:32:45 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.financeiro.banco.febraban.EntidadeDeCobranca;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoInfoCampoEndereco {
	
	public static String getTextoEnderecoLinha1(EntidadeDeCobranca pessoa){
		if (isNotNull(pessoa)) {
			return getTextoEnderecoLinha1(pessoa.getNextEndereco());
		}
		return EMPTY;
	}
	
	public static String getTextoEnderecoLinha1(Endereco endereco){
		
		final StringBuilder texto = new StringBuilder(EMPTY);
		
		if (isNotNull(endereco)) {
			
			boolean temBairro = false;
			boolean temLocalidade = false;
			
			if (isNotBlank(endereco.getBairro())) {
				temBairro = true;
				texto.append(endereco.getBairro());
			}
			
			if (isNotBlank(endereco.getLocalidade())) {
				temLocalidade = true;
				if(temBairro){
					texto.append(" - ");
				}
				texto.append(endereco.getLocalidade());
			}
			
			if (isNotNull(endereco.getUF())) {
				if(temBairro || temLocalidade){
					texto.append(" / ");
				}
				texto.append(endereco.getUF().getSigla());
			}
		}
		
		return texto.toString();
	}
	
	public static String getTextoEnderecoLinha2(EntidadeDeCobranca pessoa){
		if (isNotNull(pessoa)) {
			return getTextoEnderecoLinha2(pessoa.getNextEndereco());
		}
		return EMPTY;
	}

	public static String getTextoEnderecoLinha2(Endereco endereco){

		final StringBuilder texto = new StringBuilder(EMPTY);
		
		if (isNotNull(endereco)) {
			
			if (isNotBlank(endereco.getLogradouro())) {
				texto.append(endereco.getLogradouro());
			}

			if (isNotBlank(endereco.getNumero())) {
				texto.append(", n°: ")
				.append(endereco.getNumero());
			}
			
			if (isNotBlank(endereco.getComplemento())) {
				texto.append(" / ")
				.append(endereco.getComplemento());
			}

			if (isNotNull(endereco.getCEP()) && isNotBlank(endereco.getCEP().getCep())) {
				texto.append(" - ")
				.append("CEP: ")
				.append(endereco.getCEP().getCep());
			}

		}
		
		return texto.toString();
	}

}
