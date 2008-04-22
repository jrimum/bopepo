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
 * Created at: 21/04/2008 - 20:27:04
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
 * Criado em: 21/04/2008 - 20:27:04
 * 
 */

package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.EnumCobranca;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;

public abstract class ACLBancoSafra extends ACampoLivre {
	
	/**
	 * Tamanho deste campo. Em outras palavras, é a quantidade de partes que
	 * compõem este campo livre.
	 */
	protected static final Integer FIELDS_LENGTH = 4;
	
	protected static final int SISTEMA = 7;

	protected ACLBancoSafra(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static ICampoLivre getInstance(Titulo titulo)
			throws NotSuporttedCampoLivreException {

		ICampoLivre campoLivre = null;

		// TODO Código em teste
		ContaBancaria conta = titulo.getCedente().getContasBancarias()
				.iterator().next();

		if (exists(conta.getCarteira().getCobranca())) {
			
			if(exists(conta.getNumeroDaConta().getDigitoDaConta())) {

				if (conta.getCarteira().getCobranca() == EnumCobranca.COM_REGISTRO) {
	
					campoLivre = CLBancoSafraCobrancaRegistrada.getInstance(titulo);
	
				} else {// Sem_Registro
	
					campoLivre = CLBancoSafraCobrancaNaoRegistrada.getInstance(titulo);
				}
			}
			else {
				throw new CampoLivreException(new IllegalArgumentException(
						"Defina o dígito verificador do número da conta bancária."));
			}

		} else {
			throw new NotSuporttedCampoLivreException(
					"Campo livre indeterminado, defina o tipo de cobranca para a carteira usada.");
		}

		return campoLivre;
	}
}
