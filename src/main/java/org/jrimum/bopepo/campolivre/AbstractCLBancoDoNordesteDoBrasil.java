/**
 * 
 */
package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Interface comum para todos os campos livres do Banco do Nordeste do 
 * Brasil que venham a existir.
 * </p>
 * 
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:pporto@gmail.com">Paulo Porto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public abstract class AbstractCLBancoDoNordesteDoBrasil extends AbstractCampoLivre {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5036970456320987443L;

	/**
	 * <p>Cria um campo livre com um determinado número de campos</p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength - Número de campos
	 * 
	 * @since 0.2
	 */
	protected AbstractCLBancoDoNordesteDoBrasil(Integer fieldsLength) {
		
		super(fieldsLength);
	}
	
	/**
	 * @param titulo
	 * @return um CampoLivre
	 * 
	 * @see org.jrimum.bopepo.campolivre.AbstractCLBancoDoNordesteDoBrasil#create(Titulo)
	 */
	protected static CampoLivre create(Titulo titulo){
		return new CLBancoDoNordesteDoBrasil().build(titulo);
	}	
	
}
