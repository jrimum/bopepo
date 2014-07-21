package org.jrimum.bopepo.view.info.campo.caixa;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.campo.BoletoInfoCampoView;

/**
 * Factory para determinar qual a implementação da CAIXA será usada de acordo com o convênio (SIGCB, SINCO, SICOB).
 * @author Rômulo Augusto
 */
public class BoletoInfoViewCaixaFactory {

	private static final int NN10 = 10;
	private static final int NN14 = 14;
	private static final int NN15 = 15;
	private static final int NN17 = 17;

	public static BoletoInfoCampoView create(ResourceBundle resourceBundle, Boleto boleto) {
		String nossoNumero = boleto.getTitulo().getNossoNumero();
		switch (nossoNumero.length()) {
			
			case NN10: return new BoletoInfoViewCaixaSICOB10(resourceBundle, boleto);
			case NN14: return new BoletoInfoViewCaixaSICOB14(resourceBundle, boleto);
			case NN15: return new BoletoInfoViewCaixaSIGCB(resourceBundle, boleto);
			case NN17: return new BoletoInfoViewCaixaSINCO(resourceBundle, boleto);
		}
		
		return null;
	}
}
