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
 * Created at: 30/03/2008 - 18:08:50
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
 * Criado em: 30/03/2008 - 18:08:50
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;

/**
 * 
 * O campo livre do Banco do Brasil com o nosso número de 11 dígitos deve seguir
 * esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th >Conteúdo</th>
 * </thead> </tr>
 * <tr>
 * <td >20-30</td>
 * <td >11</td>
 * <td >9(11) </td>
 * <td >Nosso número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >31-34</td>
 * <td >4</td>
 * <td >9(4) </td>
 * <td >Código da Agência (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >35-42</td>
 * <td >8</td>
 * <td >9(8) </td>
 * <td >Conta do cedente (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >43-44</td>
 * <td >2</td>
 * <td >9(2) </td>
 * <td >Carteira</td>
 * </tr>
 * </table>
 * 
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
class CLBancoDoBrasilNN11 extends ACLBancoDoBrasil {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4859699102593834115L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;

	/**
	 * @param fieldsLength
	 * @param stringLength
	 */
	protected CLBancoDoBrasilNN11(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	
	}

	/**
	 * @param titulo
	 * @return
	 */
	static ICampoLivre getInstance(Titulo titulo) {
		
		ACampoLivre clBancoDoBrasilN11 = new CLBancoDoBrasilNN11(FIELDS_LENGTH,STRING_LENGTH);
		
		//TODO Código em teste
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		String nossoNumero = titulo.getNossoNumero();
		
		clBancoDoBrasilN11.add(new Field<String>(nossoNumero, 11, Filler.ZERO_LEFT));
		
		//TODO Código em teste
		clBancoDoBrasilN11.add(new Field<Integer>(conta.getAgencia().getCodigoDaAgencia(), 4, Filler.ZERO_LEFT));
		clBancoDoBrasilN11.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 8, Filler.ZERO_LEFT));
		
		clBancoDoBrasilN11.add(new Field<Integer>(conta.getCarteira().getCodigo(), 2, Filler.ZERO_LEFT));

		return clBancoDoBrasilN11;
	}

}
