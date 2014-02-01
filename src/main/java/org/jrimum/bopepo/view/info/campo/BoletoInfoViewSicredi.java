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
 * Created at: 31/01/2014 - 00:11:24
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
 * Criado em: 31/01/2014 - 00:11:24 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.parametro.ParametroBancoSicredi;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 */
public class BoletoInfoViewSicredi extends AbstractBoletoInfoCampoView{

	BoletoInfoViewSicredi(ResourceBundle resourceBundle, Boleto boleto) {
		super(resourceBundle, boleto);
	}

	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoRsCodigoBanco()
	 */
	@Override
	public String getTextoRsCodigoBanco() {
		return getTextoCodigoDoBanco();
	}

	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoFcCodigoBanco()
	 */
	@Override
	public String getTextoFcCodigoBanco() {
		return getTextoCodigoDoBanco();
	}
	
	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoFcLocalPagamento()
	 */
	@Override
	public String getTextoFcLocalPagamento() {
		return "Preferencialmente nas cooperativas de crédito do SICREDI";
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

	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoRsNossoNumero()
	 */
	@Override
	public String getTextoRsNossoNumero() {
		return getTextoNossoNumero();
	}

	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoFcNossoNumero()
	 */
	@Override
	public String getTextoFcNossoNumero() {
		return getTextoNossoNumero(); 
	}
	
	/**
	 * @see org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView#getTextoFcAceite()
	 */
	@Override
	public String getTextoFcAceite() {
		Aceite aceite = super.getBoleto().getTitulo().getAceite();
		if(aceite == Aceite.A){
			return "SIM";
		}
		return "NÃO";
	}

	private String getAgenciaCodigoCedente(){
		Integer agencia = super.getBoleto().getTitulo().getContaBancaria().getAgencia().getCodigo();
		Integer posto = super.getBoleto().getTitulo().getParametrosBancarios().getValor(ParametroBancoSicredi.POSTO_DA_AGENCIA);
		Integer codigoCedente = super.getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getCodigoDaConta();
		
		return String.format("%04d.%02d.%05d", agencia, posto, codigoCedente);
	}
	
	private String getTextoNossoNumero() {
		String nn = super.getTextoFcNossoNumero();
		return nn.substring(0,2)+"/"+nn.substring(2);
	}
	
	private String getTextoCodigoDoBanco(){
		return super.getTextoFcCodigoBanco().replace("-0", "-X");
	}

}
