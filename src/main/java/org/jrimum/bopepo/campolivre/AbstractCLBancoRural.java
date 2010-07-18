package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *
 */
public class AbstractCLBancoRural extends AbstractCampoLivre {

	private static final long serialVersionUID = 1L;
	
	protected AbstractCLBancoRural(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static CampoLivre create(Titulo titulo) {
		return new CLBancoRuralCobrancaRegistrada(titulo);
	}
}
