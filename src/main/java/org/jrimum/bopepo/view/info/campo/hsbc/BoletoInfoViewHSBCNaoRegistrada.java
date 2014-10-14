package org.jrimum.bopepo.view.info.campo.hsbc;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.leftPad;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.ResourceBundle;

/**
 * View para a cobrança não registrada do HSBC.
 * 
 * @author Rômulo Augusto
 */
public class BoletoInfoViewHSBCNaoRegistrada extends AbstractBoletoInfoViewHSBC{

	public BoletoInfoViewHSBCNaoRegistrada(ResourceBundle resourceBundle, Boleto boleto) {
		super(resourceBundle, boleto);
	}
	
	@Override
	public String getTextoFcAgenciaCodigoCedente() {
		return leftPad(getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getCodigoDaConta().toString(), 7, "0");
	}
	
	@Override
	public String getTextoRsAgenciaCodigoCedente() {
		return getTextoFcAgenciaCodigoCedente();
	}
	
	@Override
	public String getTextoFcEspecieDocumento() {
		return EMPTY;
	}
	
	@Override
	public String getTextoFcAceite() {
		return EMPTY;
	}

	@Override
	public String getTextoFcCarteira() {
		return "CNR";
	}
}
