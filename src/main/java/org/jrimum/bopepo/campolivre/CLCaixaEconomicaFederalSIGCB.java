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
import static org.jrimum.vallia.digitoverificador.Modulo.MOD11;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Exceptions;
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
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:rogerio@visaosoft.com.br">Rogério Kleinkauf</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
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
	 * Modalidade de cobrança.
	 */
	private static final int COBRANCA_REGISTRADA = 1;
	
	/**
	 * Modalidade de cobrança.
	 */
	private static final int COBRANCA_NAO_REGISTRADA = 2;
	
	/**
	 * Constante que indica emissão de boleto pelo cedente. 
	 */
	private static final int EMISSAO_CEDENTE = 4;

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
		
		super(FIELDS_LENGTH);

		ContaBancaria conta = titulo.getContaBancaria();
		String nossoNumero = titulo.getNossoNumero();

		Integer dVCodigoDoCedente = calculeDigitoVerificador(conta.getNumeroDaConta().getCodigoDaConta().toString());

		this.add(new FixedField<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 6, Fillers.ZERO_LEFT));
		this.add(new FixedField<Integer>(dVCodigoDoCedente, 1));
		this.add(new FixedField<String>(nossoNumero.substring(0, 3), 3));
		
		if(conta.getCarteira().isComRegistro()){
			
			this.add(new FixedField<Integer>(COBRANCA_REGISTRADA, 1));
			
		}else{
			
			this.add(new FixedField<Integer>(COBRANCA_NAO_REGISTRADA, 1));
		}

		this.add(new FixedField<String>(nossoNumero.substring(3, 6), 3));
		this.add(new FixedField<Integer>(EMISSAO_CEDENTE, 1));
		this.add(new FixedField<String>(nossoNumero.substring(6, 15), 9));

		this.add(new FixedField<Integer>(calculeDigitoVerificador(gereCampoLivre()), 1));
	}

	/**
	 * Gera o número que serve para calcular o digito verificador do campoLivre, que é todo o campo livre menos o dígito verificador.

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
	 * @return String com campos, exceto o dígito verificador.
	 * 
	 * @since 0.2
	 */
	private String gereCampoLivre() {

		return writeFields();
	}

	/**
	 * Este dígito é calculado através do Módulo 11 com os pesos 2 e 9.
	 * 
	 * @param numeroParaCalculo
	 * @return digito
	 * 
	 * @since 0.2
	 */
	private int calculeDigitoVerificador(String numeroParaCalculo) {
		
		int soma = Modulo.calculeSomaSequencialMod11(numeroParaCalculo.toString(), 2, 9);

		int dvCampoLivre;
		
		if (soma < MOD11) {
			
			dvCampoLivre = MOD11 - soma;
			
		} else {
		
			int restoDiv11 = soma % MOD11;
			
			int subResto = MOD11 - restoDiv11;
			
			if (subResto > 9) {
			
				dvCampoLivre = 0;
			
			} else {
				
				dvCampoLivre = subResto;
			}
		}
		
		return dvCampoLivre;
	}
	
	@Override
	protected void addFields(Titulo titulo) {
		// TODO IMPLEMENTAR
		Exceptions.throwUnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}

	@Override
	protected void checkValues(Titulo titulo) {
		// TODO IMPLEMENTAR
		Exceptions.throwUnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}
}