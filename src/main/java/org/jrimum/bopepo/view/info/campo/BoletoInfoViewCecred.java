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
 * Created at: 04/02/2014 - 21:28:09
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
 * Criado em: 04/02/2014 - 21:28:09 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.apache.commons.lang.StringUtils.isBlank;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.ResourceBundle;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoInfoViewCecred extends AbstractBoletoInfoCampoView {

	BoletoInfoViewCecred(ResourceBundle resourceBundle, Boleto boleto) {
		super(resourceBundle, boleto);
	}
	
	@Override
	public String getTextoFcLocalPagamento() {
		String textoFcLocalPagamento = super.getTextoFcLocalPagamento();
		return isBlank(textoFcLocalPagamento) ? "PAGAVEL PREFERENCIALMENTE NAS COOPERATIVAS DO SISTEMA CECRED." : textoFcLocalPagamento;
	}
	
	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoRsAgenciaCodigoCedente()
	 */
	@Override
	public String getTextoRsAgenciaCodigoCedente() {
		return getAgenciaCodigoCedente();
	}

	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoFcAgenciaCodigoCedente()
	 */
	@Override
	public String getTextoFcAgenciaCodigoCedente() {
		return getAgenciaCodigoCedente();
	}

	private String getAgenciaCodigoCedente(){
		Integer agencia = super.getBoleto().getTitulo().getContaBancaria().getAgencia().getCodigo();
		String agDv = super.getBoleto().getTitulo().getContaBancaria().getAgencia().getDigitoVerificador();
		Integer numeroDaConta = super.getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getCodigoDaConta();
		String contaDv = super.getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getDigitoDaConta();
		
		return String.format("%04d-%s / %07d-%s", agencia, agDv, numeroDaConta, contaDv);
	}

}
