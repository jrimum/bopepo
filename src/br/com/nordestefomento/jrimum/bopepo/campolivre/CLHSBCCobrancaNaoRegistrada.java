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
 * Created at: 30/03/2008 - 18:09:27
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
 * Criado em: 30/03/2008 - 18:09:27
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.BancoUtil;

/**
 * 
 * 	O campo livre do HSBC, para cobrança não registrada(CNR), deve seguir esta forma:
 * 
 * 	<table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * 	collapse" bordercolor="#111111" width="60%" id="campolivre">
 * 		<tr>
 * 			<thead>
 *				<th>Posição </th>
 * 				<th>Tamanho</th>
 * 				<th>Picture</th>
 * 				<th>Conteúdo (terminologia padrão)</th>
 * 				<th>Conteúdo (terminologia do banco)</th>
 * 			</thead>
 * 		</tr>
 * 
 * 		<tr>
 * 			<td>20-26</td>
 * 			<td>7</td>
 * 			<td>9(7) </td>
 * 			<td>Conta do cedente (sem dígito)</td>
 * 			<td>Código do cedente</td>
 * 		</tr>
 * 
 * 		<tr>
 * 			<td>27-39</td>
 * 			<td>13</td>
 * 			<td>9(13) </td>
 * 			<td>Nosso número (sem dígito)</td>
 * 			<td>
 * 				Número bancário - Código do documento, sem os dígitos
 * 				verificadores e tipo identificador.
 * 			</td>
 * 		</tr>
 * 
 * 		<tr>
 * 			<td>40-43</td>
 * 			<td>4</td>
 * 			<td>9(4) </td>
 * 			<td>Fator de vencimento</td>
 * 			<td>Data do vencimento no formato juliano</td>
 * 		</tr>
 * 
 * 		<tr>
 * 			<td>44-44</td>
 * 			<td>1</td>
 * 			<td>9(1) </td>
 * 			<td>2 FIXO</td>
 * 			<td>Código do Aplicativo CNR = 2</td>
 * 		</tr>
 * </table>
 * 
 * 
 * @see br.com.nordestefomento.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLHSBCCobrancaNaoRegistrada extends AbstractCLHSBC {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1253549781074159862L;

	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;
	
	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco HSBC
	 *   que tenha o tipo de cobrança não registrada.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLHSBCCobrancaNaoRegistrada(Titulo titulo) {
		super(FIELDS_LENGTH, STRING_LENGTH);
		
		ContaBancaria conta = titulo.getContaBancaria();
		String nossoNumero = titulo.getNossoNumero();
		
		//Conta do cedente (sem dígito)
		this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 7, Filler.ZERO_LEFT));
		
		//Nosso número (sem dígito)
		this.add(new Field<String>(nossoNumero, 13, Filler.ZERO_LEFT));
		
		// Data de vencimento (formato juliano)
		int dataVencimentoFormatoJuliano = BancoUtil.calculceFatorDeVencimento(titulo.getDataDoVencimento());
		this.add(new Field<Integer>(dataVencimentoFormatoJuliano, 4, Filler.ZERO_LEFT));
		
		//2 FIXO (Código do Aplicativo CNR - Cob. Não Registrada)
		this.add(new Field<Integer>(2, 1));
		
	}
	
}
