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
 * Created at: 21/04/2008 - 23:35:28
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
 * Criado em: 21/04/2008 - 23:35:28
 * 
 */
	
package br.com.nordestefomento.jrimum.bopepo.guia;

import br.com.nordestefomento.jrimum.JRimumException;


/**
 * 
 * <p>
 * Qualquer exceção gerada durante a geração de uma guia gera uma <code>GuiaExceptiton</code>. 
 * Centraliza e localiza os problemas relativos a geração de uma guia de recolhimento.
 * </p>
 * 
 * 
 * @author misael
 * 
 * @see br.com.nordestefomento.jrimum.JRimumException
 * @see br.com.nordestefomento.jrimum.bopepo.GuiaException
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class GuiaException extends JRimumException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8754542319535890521L;

	/**
	 * 
	 */
	public GuiaException() {
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GuiaException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param message
	 */
	public GuiaException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public GuiaException(Throwable cause) {
		super(cause);
		
	}

}
