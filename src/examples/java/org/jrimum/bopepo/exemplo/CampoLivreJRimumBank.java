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
 * Created at: 01/11/2010 - 10:30:00
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
 * Criado em: 01/11/2010 - 10:30:00
 * 
 */
package org.jrimum.bopepo.exemplo;

import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * Campo Livre fictício para usar junto com o <code>JRimumBank</code>.
 * Como todo campo livre, deve possuir o tamanho de 25 dígitos.
 * </p>
 * <p>
 * O campo livre é, geralmente, formado através de dados do título. Assim, essa implementação
 * recebe um título através de seu construtor.
 * </p>
 * Segue abaixo o layout desse campo livre fictício:
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%">
 * 	<thead>
 * 	<tr>
 * 		<th>Posição</th>
 * 		<th>Tamanho</th>
 * 		<th>Picture</th>
 * 		<th>Conteúdo</th>
 * 	</tr>
 * 	</thead>
 * 	<tbody>
 * 	<tr>
 * 		<td>20-25</td>
 * 		<td>6</td>
 * 		<td>9 (6)</td>
 * 		<td>Código da conta (sem o dígito)</td>
 * 	</tr>
 * 	<tr>
 * 		<td>26-29</td>
 * 		<td>4</td>
 * 		<td>9 (4)</td>
 * 		<td>Código da agência (sem o dígito)</td>
 * 	</tr>
 * 	<tr>
 * 		<td>30-35</td>
 * 		<td>6</td>
 * 		<td>9 (6)</td>
 * 		<td>ZEROS</td>
 * 	</tr>
 * 	<tr>
 * 		<td>36-44</td>
 * 		<td>9</td>
 * 		<td>9 (9)</td>
 * 		<td>Nosso número (sem o dígito)</td>
 * 	</tr>
 * 	</tbody>
 * </table>
 * 
 * @author Rômulo Augusto
 */
public class CampoLivreJRimumBank implements CampoLivre {
	
	private static final long serialVersionUID = 338556606717660573L;

	private Titulo titulo;
	
	public CampoLivreJRimumBank(Titulo titulo) {
		this.titulo = titulo;
	}


	public void read(String g) {
		//Sem leitura
	}

	public String write() {
		
		ContaBancaria conta = titulo.getContaBancaria();
		
		Field<Integer> fieldConta = new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 6, Filler.ZERO_LEFT);
		Field<Integer> fieldAgencia = new Field<Integer>(conta.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT);
		Field<Integer> fieldZeros = new Field<Integer>(0, 6, Filler.ZERO_LEFT);
		Field<String> fieldNossoNumero = new Field<String>(titulo.getNossoNumero(), 9);
		
		return fieldConta.write() + fieldAgencia.write() + fieldZeros.write() + fieldNossoNumero.write();
	}
}
