package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Interface comum para todos os campos livres do Banco Santander que venham a
 * existir.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLSantander extends AbstractCampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1882819688182515282L;

	/**
	 * <p>Cria um campo livre com um determinado número de campos</p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength - Número de campos
	 */
	protected AbstractCLSantander(Integer fieldsLength) {
		
		super(fieldsLength);
	}

	/**
	 * {@inheritDoc}
	 */
	protected static CampoLivre create(Titulo titulo) {
		
		return new CLBancoSantander(titulo);
	}
}
