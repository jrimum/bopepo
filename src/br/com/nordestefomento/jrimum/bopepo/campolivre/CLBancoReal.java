/*
 * Copyright 2007, JMatryx Group
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright 2007, Grupo JMatryx
 * 
 * Licenciado sob a licença da Apache, versão 2.0 (a “licença”); você não pode
 * usar este arquivo exceto na conformidade com a licença. Você pode obter uma
 * cópia da licença em
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * A menos que seja requerido pela aplicação da lei ou esteja de acordo com a
 * escrita, o software distribuído sob esta licença é distribuído “COMO É”
 * BASE,SEM AS GARANTIAS OU às CONDIÇÕES DO TIPO, expresso ou implicado. Veja a
 * licença para as permissões sobre a línguagem específica e limitações quando
 * sob licença.
 * 
 * 
 * Created at / Criado em : 10/05/2007 - 22:23:34
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.Util4String;

/**
 * 
 * O campo livre do Banco Real deve seguir esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th >Conteúdo</th>
 * </thead> </tr>
 * <tr>
 * <td >20-23</td>
 * <td >4</td>
 * <td >9(4) </td>
 * <td >CODIGO DA AGENCIA CEDENTE</td>
 * </tr>
 * <tr>
 * <td >24-30</td>
 * <td >7</td>
 * <td >9(7) </td>
 * <td >CODIGO DA CONTA CORRENTE</td>
 * </tr>
 * <tr>
 * <td >31-31</td>
 * <td >1</td>
 * <td >9(1) </td>
 * <td >DIGITO VERIFICADOR</td>
 * </tr>
 * <tr>
 * <td >32-44</td>
 * <td >13</td>
 * <td >9(13) </td>
 * <td >NUMERO DO TITULO (MAXIMO DE 13 POSICOES NUMERICAS)</td>
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
class CLBancoReal extends ACLBancoReal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5294809022535972391L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;
	
	/**
	 * @param fieldsLength
	 * @param stringLength
	 */
	protected CLBancoReal(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
		
	}

	/**
	 * @param titulo
	 * @return
	 */
	static ICampoLivre getInstance(Titulo titulo) {
		
		ACampoLivre clBancoReal = new CLBancoReal(FIELDS_LENGTH,STRING_LENGTH);
		
		//TODO Código em teste
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		
		//TODO Código em teste
		clBancoReal.add(new Field<Integer>(conta.getAgencia().getCodigoDaAgencia(), 4, Filler.ZERO_LEFT));
		clBancoReal.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 7, Filler.ZERO_LEFT));
		clBancoReal.add(new Field<String>(conta.getNumeroDaConta().getDigitoDaConta(), 1, Filler.ZERO_LEFT));
		
		clBancoReal.add(new Field<String>(Util4String.eliminateSymbols(titulo.getNumeroDoDocumento()), 13, Filler.ZERO_LEFT));
		
		return clBancoReal;
	}
}
