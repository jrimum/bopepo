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
 * Created at: 02/08/2008 - 13:39:46
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
 * Criado em: 02/08/2008 - 13:39:46
 * 
 */
package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * 
 * <p>
 * Representação do campo livre usado para boletos com carteiras (<em>cobrança</em>)
 * com registro.
 * </p>
 * 
 * <p>
 * Layout:<br />
 * <div align="center">
 * <p align="center">
 * <font face="Arial">Cobrança Normal (com registro) - CAMPO LIVRE - Sistema BDL/Carteira de Letras</font>
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" >
 * <tr>
 * <td align="center" bgcolor="#C0C0C0"><strong><font face="Arial">Posição</font></strong></td>
 * <td bgcolor="#C0C0C0"><strong><font face="Arial">Campo Livre No Código De
 * Barras (20 a 44)</font></strong></td>
 * <tr>
 * <td align="center"><font face="Arial">20 a 20</font></td>
 * <td><font face="Arial">Produto = "1" Cobrança Normal, Fichário emitido pelo BANRISUL</font></td>
 * 
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">21 a 21</font></td>
 * <td><font face="Arial">Constante = "1"</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">22 a 24</font></td>
 * 
 * <td><font face="Arial">Agência Cedente sem Número de Controle</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">25 a 31</font></td>
 * 
 * <td><font face="Arial">Código do Cedente sem Número de Controle</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">32 a 39</font></td>
 * <td><font face="Arial">Nosso Número sem Número de Controle</font></td>
 * </tr>
 * 
 * <tr>
 * <td align="center"><font face="Arial">40 a 42</font></td>
 * <td><font face="Arial">Constante = "041"</font></td>
 * </tr>
 * 
 * <tr>
 * <td align="center"><font face="Arial">43 a 44</font></td>
 * <td><font face="Arial">Duplo Dígito referente às posições 20 a 42 (módulos 10 e 11)</font></td>
 * </tr>
 * 
 * </table> </div>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author Samuel Valerio
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLBanrisulCobrancaRegistrada extends AbstractCLBanrisul {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1577477244182494602L;
	
	private static final Integer FIELDS_LENGTH = 7;

	CLBanrisulCobrancaRegistrada(Titulo titulo) {
		super(FIELDS_LENGTH, STRING_LENGTH);

		this.add(new Field<Integer>(1, 1));
		this.add(new Field<String>("1", 1));
		this.add(new Field<Integer>(titulo.getContaBancaria().getAgencia()
				.getCodigo(), 3, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria()
				.getNumeroDaConta().getCodigoDaConta(), 7, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(Integer.valueOf(titulo.getNossoNumero()),
				8, Filler.ZERO_LEFT));
		this.add(new Field<String>("041", 3));
		this
				.add(new Field<String>(
						calculaDuploDigito(concateneOsCamposExistentesAteOMomento()),
						2));
	}

}
