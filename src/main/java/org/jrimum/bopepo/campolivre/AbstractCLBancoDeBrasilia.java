package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

public abstract class AbstractCLBancoDeBrasilia extends AbstractCampoLivre {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3200353843356508888L;

	protected AbstractCLBancoDeBrasilia(Integer fieldsLength) {
		super(fieldsLength);
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected static CampoLivre create(Titulo titulo){
		return new CLBancoDeBrasilia().build(titulo);
	}	
}