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
 * Created at: 30/03/2008 - 18:07:47
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
 * Criado em: 30/03/2008 - 18:07:47
 * 
 */


package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Interface comum para todos os campos livres do Banco do Brasil que venham a existir.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLBancoDoBrasil extends AbstractCampoLivre {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7324315662526104153L;

	/**
	 * <p>Cria um campo livre com um determinado número de campos</p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength - Número de campos
	 */
	protected AbstractCLBancoDoBrasil(Integer fieldsLength) {
		
		super(fieldsLength);
	}

	protected static CampoLivre create(Titulo titulo) throws NotSupportedCampoLivreException{

		checkNossoNumero(titulo);
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		
		switch(titulo.getNossoNumero().length()){
		case NN10:
			return new CLBancoDoBrasilNN10(titulo);
		case NN11:
			return new CLBancoDoBrasilNN11(titulo);
		case NN17:
			int nrDigConvenio;
			if (titulo.getContaBancaria().getNumeroDigConvenio() != null) {
				nrDigConvenio = titulo.getContaBancaria().getNumeroDigConvenio().intValue();
			} else {
				nrDigConvenio = titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() < 1000000 ? 6 : 7;
			}
			if(nrDigConvenio == 6){
				return new CLBancoDoBrasilNN17Convenio6(titulo);
			}else{
				return new CLBancoDoBrasilNN17Convenio7().build(titulo);
			}
		default:
			throw new NotSupportedCampoLivreException(
					"Campo livre diponível somente para títulos com nosso número " +
					"composto por 10 posições(convênio com 7), 11 posições ou " +
					"17 posições(convênio com 6)."
				);
		}
	}
}
