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

import java.util.Arrays;

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
 * <h2>Layout do Banco Itaú para o campo livre PADRÃO</h2>
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
 * <td >31 a 31</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >DAC [Agência /Conta/Carteira/Nosso Número]</td>
 * </tr>
 * <tr>
 * <td >32 a 35</td>
 * <td >4</td>
 * <td >9(04)</td>
 * <td >N.º da Agência cedente</td>
 * </tr>
 * <tr>
 * <td >36 a 40</td>
 * <td >5</td>
 * <td >9(05)</td>
 * <td >N.º da Conta Corrente</td>
 * </tr>
 * <tr>
 * <td >41 a 41</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >DAC [Agência/Conta Corrente]</td>
 * </tr>
 * <tr>
 * <td >42 a 44</td>
 * <td >3</td>
 * <td >9(03)</td>
 * <td >Zeros</td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLItauPadrao extends AbstractCLItau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1544486299245786533L;
	
	/**
	 * Tamanho deste campo. Em outras palavras, é a quantidade de partes que
	 * compõem este campo livre.
	 */
	private static final Integer FIELDS_LENGTH = 7;

	/**
	 * <p>
	 *   Dado um título, cria o campo livre padrão do Banco Itaú.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	public CLItauPadrao(Titulo titulo) {
		super(FIELDS_LENGTH);
		
		ContaBancaria conta = titulo.getContaBancaria();
		
		this.add(new Field<Integer>(conta.getCarteira().getCodigo(), 3, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getNossoNumero(), 8, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(calculeDigitoDaPosicao31(
									conta.getAgencia().getCodigo(), 
									conta.getNumeroDaConta().getCodigoDaConta(), 
									conta.getCarteira().getCodigo(), 
									titulo.getNossoNumero()), 1));
		
		this.add(new Field<Integer>(conta.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 5, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(calculeDigitoDaPosicao41(
									conta.getAgencia().getCodigo(), 
									conta.getNumeroDaConta().getCodigoDaConta()), 1));
		
		this.add(new Field<String>("000", 3));
	}
	
	/**
	 * <p>
	 * Calcula o dígito verificador do campo 31 a partir do código da agência, 
	 * do código da conta, do código da carteira e do nosso número.
	 * </p>
	 * <p>
	 * À exceção, estão as carteiras 126, 131, 146, 150 e 168 cuja obtenção 
	 * está baseada apenas nos dados "CARTEIRA/NOSSO NÚMERO" da operação.
	 * </p>
	 * <p>
	 * Exemplo do cálculo:
	 * <br />
	 * <pre>
	 * AG / CONTA = 0057 / 12345-7 CART / NNº = 110 / 12345678-?
	 * 
	 * Sequência para Cálculo 	0 0 5 7 1 2 3 4 5 1 1 0 1 2 3 4 5 6 7 8
	 * Módulo 10 		1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2 1 2
	 * 			| | | | | | | | | | | | | | | | | | | |___________8 x 2 = 16 (1+6)
	 * 			| | | | | | | | | | | | | | | | | | | ____________7 x 1 = 7
	 *			| | | | | | | | | | | | | | | | | | ______________6 x 2 = 12 (1+2)
	 * 			| | | | | | | | | | | | | | | | | ________________5 x 1 = 5
	 * 			| | | | | | | | | | | | | | | | | ________________4 x 2 = 8
	 *			| | | | | | | | | | | | | | | ____________________3 x 1 = 3
	 * 			| | | | | | | | | | | | | | ______________________2 x 2 = 4
	 * 			| | | | | | | | | | | | | ________________________1 x 1 = 1
	 * 			| | | | | | | | | | | | __________________________0 x 2 = 0
	 * 			| | | | | | | | | | | ____________________________1 x 1 = 1
	 * 			| | | | | | | | | | ______________________________1 x 2 = 2
	 * 			| | | | | | | | | ________________________________5 x 1 = 5
	 * 			| | | | | | | | __________________________________4 x 2 = 8
	 * 			| | | | | | | ____________________________________3 x 1 = 3
	 * 			| | | | | | ______________________________________2 x 2 = 4
	 * 			| | | | | ________________________________________1 x 1 = 1
	 * 			| | | | __________________________________________7 x 2 = 14 (1+4)
	 * 			| | | ____________________________________________5 x 1 = 5
	 * 			| | ______________________________________________0 x 2 = 0
	 * 			| ________________________________________________0 x 1 = 0
	 * 
	 * Total 								            72
	 * 
	 * Dividir o resultado da soma por 10 => 72 / 10 = 7, resto = 2
	 *  
	 * DAC = 10 - 2 = 8
	 * Portanto DAC = 8
	 * </pre>
	 * </p>
	 * 
	 * @param codigoDaAgencia
	 * @param codigoDaConta
	 * @param codigoDaCarteira
	 * @param nossoNumero
	 * @return Integer dígito
	 * 
	 * @since 0.2
	 */
	private Integer calculeDigitoDaPosicao31(Integer codigoDaAgencia,
			Integer codigoDaConta, Integer codigoDaCarteira, String nossoNumero) {

		Integer[] carteirasExcecao = {126, 131, 146, 150, 168};
		StringBuilder campo = new StringBuilder();
		
		campo.append(Filler.ZERO_LEFT.fill(codigoDaCarteira.intValue(), 3));
		campo.append(Filler.ZERO_LEFT.fill(nossoNumero, 8));
		
		/*
		 * Se a carteira não estiver nas exceções, acrescenta a agência e a conta.
		 */
		if(Arrays.binarySearch(carteirasExcecao, codigoDaCarteira) < 0) {
			
			campo.insert(0, Filler.ZERO_LEFT.fill(codigoDaConta.intValue(), 5));
			campo.insert(0, Filler.ZERO_LEFT.fill(codigoDaAgencia.intValue(), 4));
		}
		
		return calculeDigitoVerificador(campo.toString());
	}
	
	/**
	 * <p>
	 * Calcula o dígito verificador do campo 41 a partir do código da agência e 
	 * do código da conta.
	 * </p>
	 * <p>
	 * O cálculo é feito da seguinte forma: <br />
	 * <ol>
	 * <li>
	 * Multiplica-se cada algarismo do campo pela sequência de multiplicadores 
	 * 2, 1, 2, 1, 2, 1..., posicionados da direita para a esquerda;
	 * </li>
	 * <li>
	 * Some individualmente, os algarismos dos resultados dos produtos, obtendo-se o total (N);
	 * </li>
	 * <li>
	 * Divida o total encontrado (N) por 10, e determine o resto da divisão como MOD 10 (N);
	 * </li>
	 * <li>
	 * Encontre o DAC através da seguinte expressão: DAC = 10 – Mod 10 (N)
	 * <br />
	 * OBS.: Se o resultado da etapa d for 10, considere o DAC = 0.
	 * </li>
	 * </ol>
	 * 
	 * </p>
	 * 
	 * @param codigoDaAgencia
	 * @param codigoDaConta
	 * @return Integer digito
	 * 
	 * @since 0.2
	 */
	private Integer calculeDigitoDaPosicao41(Integer codigoDaAgencia,
			Integer codigoDaConta) {

		StringBuilder campo = new StringBuilder();
		campo.append(Filler.ZERO_LEFT.fill(codigoDaAgencia.intValue(), 4));
		campo.append(Filler.ZERO_LEFT.fill(codigoDaConta.intValue(), 5));
		
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

