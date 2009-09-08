package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;


abstract class AbstractCLSantander extends AbstractCampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6824641098515768442L;

	protected AbstractCLSantander(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
		
	}
	
	static CampoLivre create(Titulo titulo){


		return null;
	}
}
