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
 * Created at: 16/04/2008 - 23:09:08
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
 * Criado em: 16/04/2008 - 23:09:08
 * 
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * 
 * 
 * <p>
 * Campo livre padrão do Banco Itaú
 * </p>
 * 
 * <p>
 * Constrói o campo livre no caso especial, ou seja, quando a carteira for:
 * 106, 107, 122, 142, 143, 195, 196 ou 198.
 * </p>
 * 
 * <p>
 * <h2>Layout do Banco Itaú para o campo livre ESPECIAL</h2>
 * <pre>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" id="campolivre">
 * <thead>
 * <tr>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td >20 a 22</td>
 * <td >3</td>
 * <td >9(03)</td>
 * <td >Carteira</td>
 * </tr>
 * <tr>
 * <td >23 a 30</td>
 * <td >8</td>
 * <td >9(08)</td>
 * <td >Nosso número</td>
 * </tr>
 * <tr>
 * <td >31 a 37</td>
 * <td >7</td>
 * <td >9(07)</td>
 * <td >Seu Número (Número do Documento)</td>
 * </tr>
 * <tr>
 * <td >38 a 42</td>
 * <td >5</td>
 * <td >9(05)</td>
 * <td >Código do Cliente (fornecido pelo Banco)</td>
 * </tr>
 * <tr>
 * <td >43 a 43</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >DAC dos campos acima (posições 20 a 42 veja anexo 3)</td>
 * </tr>
 * <tr>
 * <tr>
 * <td >44 a 44</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >Zero</td>
 * </tr>
 * </tbody>
 * </table>
 * <pre>
 * </p>
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLItauComCarteirasEspeciais extends AbstractCLItau {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1532454262023154419L;
	
	/**
	 * Tamanho do campo livre para carteiras especiais.
	 */
	private static final Integer FIELDS_LENGTH = 6;
	
	/**
	 * <p>
	 *   Dado um título, cria o campo livre do Banco Itaú para carteiras especiais.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	public CLItauComCarteirasEspeciais(Titulo titulo) {
		super(FIELDS_LENGTH);
		
		ContaBancaria conta = titulo.getContaBancaria();
		
		this.add(new Field<Integer>(conta.getCarteira().getCodigo(), 3, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getNossoNumero(), 8, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getNumeroDoDocumento(), 7, Filler.ZERO_LEFT));
		
		//Aqui é o código do cedente, simbolizado pelo código da conta bancária.
		this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 5, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(calculeDigitoDoCampoLivreEspecial(
											conta.getCarteira().getCodigo(), 
											titulo.getNossoNumero(),
											titulo.getNumeroDoDocumento(),
											conta.getNumeroDaConta().getCodigoDaConta()), 1));
		this.add(new Field<Integer>(0, 1));
	}
	
	/**
	 * <p>
	 * Calcula o dígito verificador para o campo livre especial a partir do 
	 * código da carteira, do nosso número, do número do documento e do código da conta.
	 * </p>
	 * 
	 * @param codigoDaCarteira
	 * @param nossoNumero
	 * @param numeroDoDocumento
	 * @param codigoDaConta
	 * @return Integer digito
	 * 
	 * @see #calculeDigitoDaPosicao41(Integer, Integer)
	 * 
	 * @since 0.2
	 */
	private Integer calculeDigitoDoCampoLivreEspecial(Integer codigoDaCarteira,
			String nossoNumero, String numeroDoDocumento, Integer codigoDaConta) {

		StringBuilder campo = new StringBuilder();
		
		campo.append(Filler.ZERO_LEFT.fill(codigoDaCarteira.intValue(), 3));
		campo.append(Filler.ZERO_LEFT.fill(nossoNumero, 8));
		campo.append(Filler.ZERO_LEFT.fill(numeroDoDocumento, 7));
		campo.append(Filler.ZERO_LEFT.fill(codigoDaConta, 5));
		
		return calculeDigitoVerificador(campo.toString());
	}

	@Override
	protected void addFields(Titulo titulo) {
		// TODO IMPLEMENTAR
		throw new UnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}

	@Override
	protected void checkValues(Titulo titulo) {
		// TODO IMPLEMENTAR
		throw new UnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}
}
