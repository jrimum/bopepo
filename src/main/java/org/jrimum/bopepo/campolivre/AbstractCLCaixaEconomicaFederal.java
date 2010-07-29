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

package org.jrimum.bopepo.campolivre;

import static org.jrimum.utilix.ObjectUtil.isNull;

import org.apache.commons.lang.StringUtils;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * Fábrica para os campos livres da Caixa Econômica Federal. 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a> 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author Rogério Kleinkauf - Colaborador do campo livre SIGCB
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLCaixaEconomicaFederal extends AbstractCampoLivre {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4104858478390595830L;
	
	private static final int NOSSO_NUMERO_SINCO = 17;

	private static final int NOSSO_NUMERO_SICOB = 11;
	
	private static final int NOSSO_NUMERO_SIGCB = 15;

	protected AbstractCLCaixaEconomicaFederal(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static CampoLivre create(Titulo titulo) throws NotSupportedCampoLivreException{
		
		CampoLivre campoLivre = null;
		
		String nossoNumero = titulo.getNossoNumero();
		
		if(StringUtils.isNotBlank(nossoNumero)) {
			
			switch(nossoNumero.length()) {
				
				case NOSSO_NUMERO_SICOB:
					campoLivre = new CLCaixaEconomicaFederalSICOB(titulo);
					break;

				case NOSSO_NUMERO_SIGCB:
					campoLivre = new CLCaixaEconomicaFederalSIGCB(titulo);
					break;
					
				case NOSSO_NUMERO_SINCO:
					campoLivre = new CLCaixaEconomicaFederalSINCO(titulo);
					break;
			}
		}
		
		if (isNull(campoLivre)) {
			throw new NotSupportedCampoLivreException(
					String.format("Campo Livre não suportado para o Nosso Número [%s] de tamanho [%s]. " +
					"Apenas títulos com Nosso Número de tamanho [%s] são suportados (SICOB, SIGCB e SINCO respectivamente).", 
					nossoNumero, (StringUtils.isBlank(nossoNumero) ? "0" : nossoNumero.length()), 
					NOSSO_NUMERO_SICOB + "," + NOSSO_NUMERO_SIGCB + "," + NOSSO_NUMERO_SINCO));
		} else {
			return campoLivre;
		}
	}
}
