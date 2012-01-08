/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 17/02/2011 - 12:40:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a LicenÃ§a Apache, VersÃ£o 2.0 ("LICENÃ‡A"); vocÃª nÃ£o pode usar
 * esse arquivo exceto em conformidade com a esta LICENÃ‡A. VocÃª pode obter uma
 * cÃ³pia desta LICENÃ‡A em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigÃªncia legal ou acordo por escrito, a distribuiÃ§Ã£o de software sob
 * esta LICENÃ‡A se darÃ¡ â€œCOMO ESTÃ?â€?, SEM GARANTIAS OU CONDIÃ‡Ã•ES DE QUALQUER
 * TIPO, sejam expressas ou tÃ¡citas. Veja a LICENÃ‡A para a redaÃ§Ã£o especÃ­fica a
 * reger permissÃµes e limitaÃ§Ãµes sob esta LICENÃ‡A.
 * 
 * Criado em: 17/02/2011 - 12:40:00
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * Interface comum para todos os campos livres do CITIBANK
 * 
 * @author <a href="mailto:fabianojustino@gmail.com">Fabiano Carrijo Justino</a>
 * @since 0.2
 * @version 0.2
 */
abstract class AbstractCLBancoCitibank extends AbstractCampoLivre {

	/**
	 * {@code serialVersionUID = 6080749970883991308L}
	 */
	private static final long serialVersionUID = 6080749970883991308L;

	/**
	 * <p>
	 * Cria um campo livre com um determinado nÃºmero de campos
	 * </p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength
	 *            - NÃºmero de campos
	 */
	protected AbstractCLBancoCitibank(Integer fieldsLength) {

		super(fieldsLength);
	}

	/**
	 * @param titulo
	 * @return um CampoLivre
	 * 
	 */
	 protected static CampoLivre create(Titulo titulo)
     throws NotSupportedCampoLivreException	{
	     checkNossoNumero(titulo);
	     checkNumeroDaContaNotNull(titulo);
	     checkCodigoDoNumeroDaConta(titulo);
	     checkNossoNumero(titulo);
	     checkNumeroDaContaNotNull(titulo);
	     checkCodigoDoNumeroDaConta(titulo);
	     switch(titulo.getNossoNumero().length())
	     {
     case 11: 
         return new CLBancoCitiBankNN11(titulo);
     }
     throw new NotSupportedCampoLivreException("Campo livre diponivel somente para titulos com nosso nmero composto por 10 posicoes(convenio com 7), 11 posicoes ou 17 posicoes(convenio com 6).");
 }


}
