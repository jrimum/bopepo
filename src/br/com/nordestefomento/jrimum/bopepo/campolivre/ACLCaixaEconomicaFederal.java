/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:08:25
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:08:25
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
		
		if (isNull(campoLivre)) {
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
