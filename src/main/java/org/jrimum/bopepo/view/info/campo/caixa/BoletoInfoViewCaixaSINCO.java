package org.jrimum.bopepo.view.info.campo.caixa;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView;

/**
 * View para o convênio SINCO da CAIXA.
 * 
 * @author Rômulo Augusto
 */
public class BoletoInfoViewCaixaSINCO extends AbstractBoletoInfoCampoView {

	private static final String CARTEIRA_SINCO = "01";
	private static final int CONSTANTE_NOSSO_NUMERO = 9;
	
	BoletoInfoViewCaixaSINCO(ResourceBundle resourceBundle, Boleto boleto) {
		super(resourceBundle, boleto);
	}
	
	@Override
	public String getTextoFcNossoNumero() {
		return CONSTANTE_NOSSO_NUMERO + super.getTextoFcNossoNumero();
	}
	
	@Override
	public String getTextoFcCarteira() {
		return CARTEIRA_SINCO;
	}
}
