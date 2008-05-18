/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 21/04/2008 - 21:54:06
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 21/04/2008 - 21:54:06
 * 
 */
	
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;

/**
 * 
 * <p>
 * Layout para a cobrança NÃO registrada:
 * <br />
 * <br />
 * <table border="1" cellpadding="3" cellspacing="0">
 * <tr>
 * <td align="center" bgcolor="#C0C0C0"><strong><font face="Arial">Posição</font></strong></td>
 * <td bgcolor="#C0C0C0"><strong><font face="Arial">Conteúdo</font></strong></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">1 a 3</font></td>
 * <td><font face="Arial">Número do banco = 422</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">4</font></td>
 * <td><font face="Arial">Código da Moeda - 9 para Real</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">5</font></td>
 * <td><font face="Arial">Digito verificador do Código de Barras</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">6 a 9</font></td>
 * <td><font face="Arial">Fator de Vencimento<br>
 * <small>(diferença em dias entre o vencimento e 07/10/1997)</small></font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">10 a 19</font></td>
 * <td><font face="Arial">Valor (8 inteiros e 2 decimais)</font></td>
 * </tr>
 * <tr>
 * <td align="center" bgcolor="#C0C0C0"><font face="Arial">20 a 44</font></td>
 * <td bgcolor="#C0C0C0"><font face="Arial">Campo Livre definido por cada banco</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">20 a 20</font></td>
 * <td><font face="Arial">Sistema = 7 (Valor Fixo)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">21 a 26</font></td>
 * <td><font face="Arial">Identificação do cliente = FIXO ATRIBUÍDO PELO 
 * BANCO (número + dígito verificador)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">27 a 43</font>
 * </td>
 * <td><font face="Arial">Livre do cliente (Nosso número)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">44 a 44</font></td>
 * <td><font face="Arial">Tipo de cobraça = 4 (valor fixo)</font></td>
 * </tr>
 * </table>
 * </div>
 * </p>
 * 
 * @author Romulo
 * 
 * @since 
 * 
 * @version 
 */
public class CLBancoSafraCobrancaNaoRegistrada extends ACLBancoSafra {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6573340701469029151L;
	
	private static final int TIPO_COBRANCA = 4;

	/**
	 * @param fieldsLength
	 * @param stringLength
	 */
	public CLBancoSafraCobrancaNaoRegistrada(Integer fieldsLength,
			Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static ICampoLivre getInstance(Titulo titulo) {

		ContaBancaria conta = titulo.getContaBancaria();
		
		ACampoLivre aCLBancoSafra = new CLBancoSafraCobrancaNaoRegistrada(
				FIELDS_LENGTH, STRING_LENGTH);
		
		aCLBancoSafra.add(new Field<Integer>(SISTEMA, 1));
		
		//Referente a identificação do cliente.
		aCLBancoSafra.add(new Field<String>(
				Filler.ZERO_LEFT.fill(conta.getNumeroDaConta().getCodigoDaConta(), 5) +
				conta.getNumeroDaConta().getDigitoDaConta(), 6));
		
		aCLBancoSafra.add(new Field<String>(titulo.getNossoNumero(), 17, Filler.ZERO_LEFT));
		aCLBancoSafra.add(new Field<Integer>(TIPO_COBRANCA, 1));
		
		return aCLBancoSafra;
	}
}
