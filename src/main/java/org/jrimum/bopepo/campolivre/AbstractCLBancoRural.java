package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 *
 */
abstract class AbstractCLBancoRural extends AbstractCampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = -602454445158254612L;

	protected AbstractCLBancoRural(Integer fieldsLength) {
		super(fieldsLength);
	}

	static CampoLivre create(Titulo titulo) {
		return new CLBancoRuralCobrancaRegistrada(titulo);
	}
}
