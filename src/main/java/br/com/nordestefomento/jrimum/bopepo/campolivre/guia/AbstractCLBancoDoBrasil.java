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


package br.com.nordestefomento.jrimum.bopepo.campolivre.guia;

import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.IdentificacaoSeguimento;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.IdentificacaoValorReferencia;

/**
 * 
 * @author Misael Barreto
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
abstract class AbstractCLBancoDoBrasil extends AbstractCampoLivre {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7324315662526104153L;


	protected AbstractCLBancoDoBrasil(Integer fieldsLength, IdentificacaoSeguimento identificacaoSeguimento) {
		super(fieldsLength, identificacaoSeguimento);
	}

	
	static CampoLivre create(Arrecadacao arrecadacao) throws NotSupportedCampoLivreException{			
		
		CampoLivre campoLivre = null;
		IdentificacaoValorReferencia identValRef = null;
		IdentificacaoSeguimento identSeg = null;
		
		identValRef = arrecadacao.getIdentificacaoValorReferencia();
		identSeg = arrecadacao.getOrgaoRecebedor().getIdentificacaoSeguimento();
		if (identSeg == IdentificacaoSeguimento.USO_EXCLUSIVO_BANCO) {
		
			if ( (identValRef == IdentificacaoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10)
				 || (identValRef == IdentificacaoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_11) ) {

				campoLivre = new CLBancoDoBrasilSegmento9(arrecadacao);	
			}
			else {
				throw new NotSupportedCampoLivreException(
					"Campo livre diponível somente para títulos com identificação " +
					"de valor referência " + identValRef.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10.getCodigo() + 
					" ou " + identValRef.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_11.getCodigo() +
					"."
				);
			}
		}
		else {
			throw new NotSupportedCampoLivreException(
				"Campo livre diponível somente para guias nas quais o órgão " +
				"ou empresa recebedora fizer parte do segmento " + identSeg.getNome() +
				" (código " + identSeg.getCodigo() + ")."
			);
		}

		return campoLivre;
	}
	
}
