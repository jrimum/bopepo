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
 * Created at: 28/07/2010 - 21:05:00
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
 * Criado em: 28/07/2010 - 21:05:00
 *
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Field;
import org.jrimum.utilix.Filler;
import org.jrimum.vallia.digitoverificador.Modulo;

/**
 * <p>
 * O campo livre para o modelo SIGCB segue esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <thead>
 * <tr>
 * <td>Posição</td>
 * <td>Tamanho</td>
 * <td>Conteúdo</td>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>01-05</td>
 * <td>6</td>
 * <td>Código do Cedente</td>
 * </tr>
 * <tr>
 * <td>06</td>
 * <td>1</td>
 * <td>Dígito Verificador do Código do Cedente</td>
 * </tr>
 * <tr>
 * <td>07-09</td>
 * <td>3</td>
 * <td>Nosso Número – Seqüência 1</td>
 * </tr>
 * <tr>
 * <td>10</td>
 * <td>1</td>
 * <td>Constante 1</td>
 * </tr>
 * <tr>
 * <td>11-13</td>
 * <td>3</td>
 * <td>Nosso Número – Seqüência 2</td>
 * </tr>
 * <tr>
 * <td>14</td>
 * <td>1</td>
 * <td>Constante 2</td>
 * </tr>
 * <tr>
 * <td>15-23</td>
 * <td>9</td>
 * <td>Nosso Número – Seqüência 3</td>
 * </tr>
 * <tr>
 * <td>24</td>
 * <td>1</td>
 * <td>Dígito Verificador do Campo Livre</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author Rogério Kleinkauf - Colaborador do campo livre SIGCB
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLCaixaEconomicaFederalSIGCB extends AbstractCLCaixaEconomicaFederal {

	/**
	 *
	 */
	private static final long serialVersionUID = -7642075752245778160L;

	/**
	 *
	 */
	private static final Integer FIELDS_LENGTH = 8;

	/**
	 * <p>
	 * Dado um título, cria um campo livre para o padrão do Banco Caixa
	 * Econômica Federal que tenha o serviço SIGCB.
	 * </p>
	 * 
	 * @param titulo
	 *            - Título com as informações para geração do campo livre.
	 */
	CLCaixaEconomicaFederalSIGCB(Titulo titulo) {
		
		super(FIELDS_LENGTH, STRING_LENGTH);

		ContaBancaria conta = titulo.getContaBancaria();

		String nossoNumero = titulo.getNossoNumero();

		Integer dVCodigoDoCedente = calculeDigitoVerificadorDoCampoLivre(conta.getNumeroDaConta().getCodigoDaConta().toString());

		this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 6, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(dVCodigoDoCedente, 1));

		this.add(new Field<String>(nossoNumero.substring(0, 3), 3));
		this.add(new Field<Integer>(2, 1));

		this.add(new Field<String>(nossoNumero.substring(3, 6), 3));
		this.add(new Field<Integer>(4, 1));

		this.add(new Field<String>(nossoNumero.substring(6, 15), 9));

		String numeroParaCalculo = gereNumeroParaCalculoDoDigitoVerificadorDoCampoLivre(titulo, dVCodigoDoCedente);

		this.add(new Field<Integer>(calculeDigitoVerificadorDoCampoLivre(numeroParaCalculo), 1));
	}

	/**
	 * <p>
	 * Gera o número que serve para calcular o digito verificador do campoLivre
	 * </p>
	 * <p>
	 * Os campos utilizados são:
	 * <ul>
	 * <li>Código do Cedente: 06 posições</li>
	 * <li>Dígito Verificador do Código do Cedente: 01 posição</li>
	 * <li>Nosso Número – Seqüência 1: 03 posições</li>
	 * <li>Constante 1: 01 posição</li>
	 * <li>Nosso Número – Seqüência 2: 03 posições</li>
	 * <li>Constante 2: 01 posição</li>
	 * <li>Nosso Número – Seqüência 3: 09 posições</li>
	 * </ul>
	 * </p>
	 * 
	 * @param titulo
	 *            - Título com os dados para geração do campo livre.
	 * @param dVCodigoDoCedente
	 *            - Dígito verificador do código do cedente.
	 * @return String com os dígitos que compõem o campo livro, exceto o dígito verificador.
	 * 
	 * @since 0.2
	 */
	private String gereNumeroParaCalculoDoDigitoVerificadorDoCampoLivre(Titulo titulo, Integer dVCodigoDoCedente) {

		StringBuilder numeroParaCalculo = new StringBuilder();
		
		numeroParaCalculo.append(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta());
		numeroParaCalculo.append(dVCodigoDoCedente);
		numeroParaCalculo.append(titulo.getNossoNumero().substring(0, 3));
		numeroParaCalculo.append(2);
		numeroParaCalculo.append(titulo.getNossoNumero().substring(3, 6));
		numeroParaCalculo.append(4);
		numeroParaCalculo.append(titulo.getNossoNumero().substring(6, 15));

		return numeroParaCalculo.toString();
	}

	/**
	 * <p>
	 * Este dígito é calculado através do Módulo 11 com os pesos 2 e 9.
	 * </p>
	 * 
	 * @param numeroParaCalculo
	 * @return
	 * 
	 * @since
	 */
	private int calculeDigitoVerificadorDoCampoLivre(String numeroParaCalculo) {
		
		int soma = Modulo.calculeSomaSequencialMod11(numeroParaCalculo.toString(), 2, 9);

		int dvCampoLivre;
		if (soma < 11) {
			dvCampoLivre = 11 - soma;

		} else {
		
			int restoDiv11 = soma % 11;
			int subResto = 11 - restoDiv11;
			
			if (subResto > 9) {
				dvCampoLivre = 0;
			} else {
				dvCampoLivre = subResto;
			}
		}
		
		return dvCampoLivre;
	}
}