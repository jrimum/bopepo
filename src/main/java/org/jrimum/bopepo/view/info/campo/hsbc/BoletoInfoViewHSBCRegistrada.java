package org.jrimum.bopepo.view.info.campo.hsbc;

import static org.apache.commons.lang.StringUtils.leftPad;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.ResourceBundle;

/**
 * View para a cobrança registrada do HSBC.
 * 
 * @author Rômulo Augusto
 */
public class BoletoInfoViewHSBCRegistrada extends AbstractBoletoInfoViewHSBC {

	public BoletoInfoViewHSBCRegistrada(ResourceBundle resourceBundle, Boleto boleto) {
		super(resourceBundle, boleto);
	}
	
	@Override
	public String getTextoFcAgenciaCodigoCedente() {
		String agencia = leftPad(getBoleto().getTitulo().getContaBancaria().getAgencia().getCodigo().toString(), 4, "0");
		String conta = leftPad(getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getCodigoDaConta().toString(), 7, "0");
		return agencia + conta;
	}
	
	@Override
	public String getTextoRsAgenciaCodigoCedente() {
		return getTextoFcAgenciaCodigoCedente();
	}
	
	@Override
	public String getTextoFcEspecieDocumento() {
		return "PD";
	}
	
	@Override
	public String getTextoFcAceite() {
		return "NÃO";
	}
	
	@Override
	public String getTextoFcCarteira() {
		return "CSB";
	}
}
