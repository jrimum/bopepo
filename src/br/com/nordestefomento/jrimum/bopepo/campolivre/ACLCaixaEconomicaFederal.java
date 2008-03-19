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
 * Created at / Criado em : 21/03/2007 - 11:46:38
 * 
 */

package br.com.nordestefomento.jrimum.bopepo.campolivre;

/**
 * 
 * Representa a família de classes do campo livre para o banco Caixa Econômica Federal.
 * 
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.domkee.entity.Titulo;


abstract class ACLCaixaEconomicaFederal extends ACampoLivre {
	
	private static final int NOSSO_NUMERO_SINCO = 17;

	protected ACLCaixaEconomicaFederal(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static ICampoLivre getInstance(Titulo titulo) throws NotSuporttedCampoLivreException{
		ICampoLivre campoLivre = null;
		String nossoNumero = titulo.getNossoNumero();
		
		if(StringUtils.isNotBlank(nossoNumero)) {
			switch(nossoNumero.length()) {
				case NOSSO_NUMERO_SINCO:
					campoLivre = CLCaixaEconomicaFederalSINCO.getInstance(titulo);
					break;
			}
		}
		
		if (campoLivre == null) {
			throw new NotSuporttedCampoLivreException (
					"Campo livre disponível somente para títulos com " +
					" comprimento de " + NOSSO_NUMERO_SINCO + " " + 
					"(SINCO) caracteres"
			);
		}
		else {
			return campoLivre;
		}
	}
}
