/*
 * Copyright 2007, JMatryx Group
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright 2007, Grupo JMatryx
 * 
 * Licenciado sob a licença da Apache, versão 2.0 (a “licença”); você não pode
 * usar este arquivo exceto na conformidade com a licença. Você pode obter uma
 * cópia da licença em
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * A menos que seja requerido pela aplicação da lei ou esteja de acordo com a
 * escrita, o software distribuído sob esta licença é distribuído “COMO É”
 * BASE,SEM AS GARANTIAS OU às CONDIÇÕES DO TIPO, expresso ou implicado. Veja a
 * licença para as permissões sobre a línguagem específica e limitações quando
 * sob licença.
 * 
 * 
 * Created at / Criado em : 06/04/2007 - 19:07:39
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.Titulo;

/**
 * 
 * Descrição:
 * 
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */

abstract class ACLBancoDoBrasil extends ACampoLivre {
	
	
	
	
	protected ACLBancoDoBrasil(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	
	static ICampoLivre getInstance(Titulo titulo) throws NotSuporttedCampoLivreException{
				
		ICampoLivre campoLivre = null;
		
		if (titulo.getNossoNumero().length() == 10) {
			campoLivre = CLBancoDoBrasilNN10.getInstance(titulo);
		}
		else if (titulo.getNossoNumero().length() == 11) {
			campoLivre = CLBancoDoBrasilNN11.getInstance(titulo);
		}
		else if (titulo.getNossoNumero().length() == 17) {
			campoLivre = CLBancoDoBrasilNN17.getInstance(titulo);	
		}
		else {
			throw new NotSuporttedCampoLivreException(
				"Campo livre diponível somente para títulos com nosso número " +
				"composto por 10 posições(convênio com 7), 11 posições ou " +
				"17 posições(convênio com 6)."
			);
		}


		return campoLivre;
	}
	
}
