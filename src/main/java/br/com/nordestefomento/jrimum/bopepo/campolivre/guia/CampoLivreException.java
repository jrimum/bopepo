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
 * Created at: 21/04/2008 - 21:14:40
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
 * Criado em: 21/04/2008 - 21:14:40
 * 
 */
	
package br.com.nordestefomento.jrimum.bopepo.campolivre.guia;

import br.com.nordestefomento.jrimum.JRimumException;

/**
 * 
 * <p>
 * Representa qualquer exceção gerada durante a geração de um campo livre gera uma 
 * <code>CampoLivreException</code>. Centraliza e localiza os problemas relativos
 *  a geração de um campo livre.
 * </p>
 * 
 * 
 * @author Misael Barreto
 * 
 * @see br.com.nordestefomento.jrimum.JRimumException
 * @see br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivre
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public class CampoLivreException extends JRimumException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1207913109397507114L;

	/**
	 * 
	 */
	public CampoLivreException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CampoLivreException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CampoLivreException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CampoLivreException(Throwable cause) {
		super(cause);
	}

}
