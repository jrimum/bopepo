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
 * Created at: 19/01/2014 - 22:42:27
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
 * Criado em: 19/01/2014 - 22:42:27 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.jrimum.utilix.Objects.isNotNull;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.campo.caixa.BoletoInfoViewCaixaFactory;
import org.jrimum.bopepo.view.info.campo.hsbc.AbstractBoletoInfoViewHSBC;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 */
public class BoletoInfoCampoViewFactory {

	public static BoletoInfoCampoView create(ResourceBundle resourceBundle,
			Boleto boleto) {

		final BancosSuportados banco = BancosSuportados.suportados.get(boleto
				.getTitulo().getContaBancaria().getBanco()
				.getCodigoDeCompensacaoBACEN().getCodigoFormatado());

		if (isNotNull(banco)) {
			 switch (banco) {
			 case BANCO_BRADESCO: return new BoletoInfoViewBradesco(resourceBundle, boleto);
			// case BANCO_DO_BRASIL: return new ..;
			// case BANCO_DO_NORDESTE_DO_BRASIL: return new ..;
			// case BANCO_ABN_AMRO_REAL: return new ..;
			 case CAIXA_ECONOMICA_FEDERAL: return BoletoInfoViewCaixaFactory.create(resourceBundle, boleto);
			 case HSBC: return AbstractBoletoInfoViewHSBC.create(resourceBundle, boleto);
			// case UNIBANCO: return new ..;
			// case BANCO_ITAU: return new ..;
			// case BANCO_SAFRA: return new ..;
			// case BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL: return new ..;
			// case MERCANTIL_DO_BRASIL: return new ..;
			// case BANCO_DO_ESTADO_DO_ESPIRITO_SANTO: return new ..;
			// case BANCO_RURAL: return new ..;
			// case BANCO_SANTANDER: return new ..;
			// case BANCO_INTEMEDIUM: return nCew ..;
			 case BANCO_SICREDI: return new BoletoInfoViewSicredi(resourceBundle, boleto);
			// case BANCOOB: return new ..;
			// case CITIBANK: return new ..;
			// case BANCO_DE_BRASILIA: return new ..;
			  case CECRED: return new BoletoInfoViewCecred(resourceBundle, boleto);
			 }
		}

		return new BoletoInfoViewDefault(resourceBundle, boleto);
	}

}
