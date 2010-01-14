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
 * Created at: Dec 13, 2008 - 11:00:04 AM
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
 * Criado em: Dec 13, 2008 - 11:00:04 AM
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * 
 * <p>
 * Fábrica de campos livre do Banco Banestes.
 * </p>
 * 
 * <p>
 * Objetivos:
 * <ul>
 *   <li>Instaciar campos livre</li>
 * </ul>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author Samuel Valerio
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLBanestes extends AbstractCampoLivre {

	protected AbstractCLBanestes(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}
	
	static CampoLivre create(Titulo titulo) throws NotSupportedCampoLivreException {
		final CampoLivre campoLivre;
		
		campoLivre = new CLBanestes(titulo);
		
		return campoLivre;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2713363808443832056L;

}
