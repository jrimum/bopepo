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

package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 *<p>
 * Interface comum para todos os campos livres do Banco Safra que venham a
 * existir.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLBancoSafra extends AbstractCampoLivre {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -555393808447532987L;

	/**
	 * Tamanho deste campo. Em outras palavras, é a quantidade de partes que
	 * compõem este campo livre.
	 */
	protected static final Integer FIELDS_LENGTH = 4;
	
	protected static final int SISTEMA = 7;

	/**
	 * <p>Cria um campo livre com um determinado número de campos</p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength - Número de campos
	 */
	protected AbstractCLBancoSafra(Integer fieldsLength) {
		
		super(fieldsLength);
	}

	/**
	 * {@inheritDoc}
	 */
	protected static CampoLivre create(Titulo titulo) throws NotSupportedCampoLivreException {

		checkCarteiraNotNull(titulo);
		checkRegistroDaCarteiraNotNull(titulo);
		
		switch(titulo.getContaBancaria().getCarteira().getTipoCobranca()){
		case COM_REGISTRO:
			return new CLBancoSafraCobrancaRegistrada(titulo);
		case SEM_REGISTRO:
			return new CLBancoSafraCobrancaNaoRegistrada(titulo);
		default:
			return null;
		}
	}
}
