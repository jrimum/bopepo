/* 
 * Copyright 2014 JRimum Project
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
 * Created at: 19/01/2014 - 21:35:45
 *
 * ================================================================================
 *
 * Direitos autorais 2014 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 19/01/2014 - 21:35:45 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.jrimum.bopepo.BancosSuportados.isSuportado;
import static org.jrimum.utilix.Objects.isNotNull;

import java.awt.Image;

import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 */
public class BoletoInfoCampoLogoBanco {
	
	public static Image getImagemBanco(ResourceBundle resourceBundle, ContaBancaria conta){
			
		if (isNotNull(conta.getBanco().getImgLogo())) {
			return conta.getBanco().getImgLogo();
		} 
		
		if (isSuportado(conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {
				
			conta.getBanco().setImgLogo(
					resourceBundle
							.getLogotipoDoBanco(conta.getBanco()
									.getCodigoDeCompensacaoBACEN()
									.getCodigoFormatado()));
		} 
		
		return conta.getBanco().getImgLogo();		
	}

}
