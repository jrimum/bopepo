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
 * Created at: 25/09/2008 - 21:23:20
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
 * Criado em: 25/09/2008 - 21:23:20
 * 
 */
package org.jrimum.bopepo.campolivre;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * 
 * <p>
 * Campo Livre para o Banco Mercantil do Brasil. Segue o seguinte formato:
 * <br/>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th >Conteúdo</th>
 * </thead> </tr>
 * <tr>
 * <td >20-23</td>
 * <td >4</td>
 * <td >9</td>
 * <td >Agência do Cedente(sem dígito)</td>
 * </tr>
 * <tr>
 * <td >24-34</td>
 * <td >11</td>
 * <td >9</td>
 * <td >Nosso Número (com dígito)</td>
 * </tr>
 * <tr>
 * <td>35-43</td>
 * <td>9</td>
 * <td>9</td>
 * <td>Código do Cedente (Contrato de Cobrança)</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >9</td>
 * <td >Indicador de desconto:<br/>=2 sem desconto<br/>=0 com desconto</td>
 * </tr>
 * </table>
 * </p>
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLMercantilDoBrasil extends AbstractCLMercantilDoBrasil {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2335934898236961987L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;

	/**
	 * <p>
	 * Dado um título, cria um campo livre para o padrão do Banco Mercantil do Brasil.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLMercantilDoBrasil(Titulo titulo) {
		super(FIELDS_LENGTH);
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		
//		int digitoDoNossoNumero = calculeDigitoVerificadorDoNossoNumero(contaBancaria.getAgencia().getCodigoDaAgencia(), nossoNumero);
		
		//TODO Verificar de onde virá esta informação: apenas verificar se o título tem ou não valor de desconto.
		int desconto;
		if(titulo.getDesconto() == null || titulo.getDesconto().equals(BigDecimal.ZERO.setScale(2, RoundingMode.DOWN))) {
			desconto = 2;
			
		} else {
			desconto = 0;
		}
		
		this.add(new Field<Integer>(contaBancaria.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		
		this.add(new Field<String>(titulo.getNossoNumero() + titulo.getDigitoDoNossoNumero(), 11, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(contaBancaria.getNumeroDaConta().getCodigoDaConta(), 9, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(desconto, 1));
	}
	
	/**
	 * 
	 * <p>
	 * Calcula o dígito verificador do Nosso Número.
	 * </p>
	 * <p>
	 * É calculado através do módulo 11 a partir do código da agência e do Nosso Número.
	 * </p>
	 * 
	 * @param agencia Código da agência
	 * @param nossoNumero Nosso Número
	 * @return
	 * 
	 * @since 0.2
	 */
//	private int calculeDigitoVerificadorDoNossoNumero(Integer agencia, String nossoNumero) {
//		
//		Modulo modulo = new Modulo(EnumModulo.MODULO11);
//		int resto = modulo.calcule(Filler.ZERO_LEFT.fill(agencia, 4) + nossoNumero);
//		
//		int digito = modulo.valor() - resto;
//		
//		return digito;
//	}
	
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
