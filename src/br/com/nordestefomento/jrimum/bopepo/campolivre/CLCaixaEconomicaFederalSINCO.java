/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:09:45
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:09:45
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;

/**
 * 
 * O campo livre para o modelo SINCO segue esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <thead>
 * <tr>
 * <td>Posição</td>
 * <td>Tamanho</td>
 * <td>Conteúdo</td>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>01</td>
 * <td>1</td>
 * <td>Número "1" (valor fixo)</td>
 * </tr>
 * <tr>
 * <td>02-07</td>
 * <td>6</td>
 * <td>Código do cliente CEDENTE (fornecido pela CAIXA)</td>
 * </tr>
 * <tr>
 * <td>08</td>
 * <td>1</td>
 * <td>Número "9" (valor fixo)</td>
 * </tr>
 * <tr>
 * <td>09-25</td>
 * <td>17</td>
 * <td>Posições livres do "nosso número"</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
class CLCaixaEconomicaFederalSINCO extends ACLCaixaEconomicaFederal {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7642075752245778160L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;

	/**
	 * @param fieldsLength
	 * @param stringLength
	 */
	protected CLCaixaEconomicaFederalSINCO(Integer fieldsLength,
			Integer stringLength) {
		super(fieldsLength, stringLength);

	}

	static ICampoLivre getInstance(Titulo titulo) throws IllegalArgumentException {
		
		ACampoLivre clCaixaSINCO = new CLCaixaEconomicaFederalSINCO(FIELDS_LENGTH,STRING_LENGTH);
		
		//TODO Código em teste
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		String nossoNumero = titulo.getNossoNumero();
		
		clCaixaSINCO.add(new Field<Integer>(1, 1));
		
		//TODO Código em teste
		clCaixaSINCO.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 6, Filler.ZERO_LEFT));
		
		clCaixaSINCO.add(new Field<Integer>(9, 1));
		clCaixaSINCO.add(new Field<String>(nossoNumero, 17));
		
		return clCaixaSINCO;
	}
}
