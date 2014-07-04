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
 * Created at: 17/01/2014 - 14:38:51
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
 * Criado em: 17/01/2014 - 14:38:51 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;

/**
 * Formata a agência e o número da conta.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoInfoCampoAgenciaCodigoCedente {

	/**
	 * Retorna a agência e o número da conta no seguinte formato: {@code "1234-1 / 123456-0"}.
	 * 
	 * 
	 * @param conta
	 * @return string formatada para ser exibida no boleto
	 */
	public static String getTextoAgenciaCodigoCedente(ContaBancaria conta){

		StringBuilder texto = new StringBuilder(EMPTY);
		boolean temAgencia = false;
		

		if (isNotNull(conta.getAgencia())) {
			
			temAgencia = true;
			
			if (isNotNull(conta.getAgencia().getCodigo())){
				texto.append(conta.getAgencia().getCodigo());
			}
	
			if (isNotBlank(conta.getAgencia().getDigitoVerificador().toString())) {
	
				texto.append("-");
				texto.append(conta.getAgencia().getDigitoVerificador());
			}
		}
		
		if(isNotNull(conta.getNumeroDaConta())){
		
			if(temAgencia){
				texto.append(" / ");
			}

			if (isNotNull(conta.getNumeroDaConta().getCodigoDaConta())) {
	
				texto.append(conta.getNumeroDaConta().getCodigoDaConta());
	
				if (isNotBlank(conta.getNumeroDaConta().getDigitoDaConta())) {
	
					texto.append("-");
					texto.append(conta.getNumeroDaConta().getDigitoDaConta());
				}
			}
		}

		return texto.toString();
	}

}
