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
 * Created at: 30/03/2008 - 18:08:12
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
 * Criado em: 30/03/2008 - 18:08:12
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.Titulo;

/**
 * 
 * Descrição:
 * 
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLHSBC extends AbstractCampoLivre {

	/**
	 *
	 */
	private static final long serialVersionUID = 3179450500491723317L;

	protected AbstractCLHSBC(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
		
	}

	static CampoLivre create(Titulo titulo){
		CampoLivre campoLivre = null;
		ContaBancaria conta = titulo.getContaBancaria();
		
		if (conta.getCarteira().getTipoCobranca() == EnumTipoCobranca.SEM_REGISTRO) {
			campoLivre = new CLHsbcCNR(titulo); 
		}
		else {
			throw new CampoLivreException("Atualmente para o banco" +
					" " + EnumBancos.HSBC.getInstituicao() + 
					" só é possível a montagem do campo livre para carteiras" +
					" não registradas.");
		}
		
		return campoLivre;
	}
}
