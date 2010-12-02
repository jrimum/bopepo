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
 * Created at: 03/10/2008 - 16:13:14
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
 * Criado em: 03/10/2008 - 16:13:14
 * 
 */
package org.jrimum.bopepo.campolivre;

import java.util.Arrays;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;
import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

/**
 * 
 * <p>
 * Campo Livre para o Banco Nossa Caixa. Segue o seguinte formato:
 * <br/>
 * <br/>
 * <strong>OBS.: O Nosso Número tem 9 posições, porém, na formação deste campo livre ele
 * é dividido em 2: Identificação do sistema (1 posição) e Nosso Número (8 posições)</strong>
 * <br/>
 * <br/>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Formato</th>
 * <th >Conteúdo</th>
 * </thead> </tr>
 * <tr>
 * <td >20-20</td>
 * <td >1</td>
 * <td >Z</td>
 * <td >Identificação do sistema(9)</td>
 * </tr>
 * <tr>
 * <td>21-28</td>
 * <td>8</td>
 * <td>Z</td>
 * <td>Nosso Número (posição 2 a 9)</td>
 * </tr>
 * <tr>
 * <td>29-32</td>
 * <td>4</td>
 * <td>Z</td>
 * <td>Agência do Cedente</td>
 * </tr>
 * <tr>
 * <td>33-33</td>
 * <td>1</td>
 * <td>Z</td>
 * <td>Modalidade da conta corrente (conforme tabela de conversão)</td>
 * </tr>
 * <tr>
 * <td>34-39</td>
 * <td>6</td>
 * <td>Z</td>
 * <td>Conta Corrente (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>40-42</td>
 * <td>3</td>
 * <td>Z</td>
 * <td>Banco Cedente (151)</td>
 * </tr>
 * <tr>
 * <td>43-43</td>
 * <td>1</td>
 * <td>Z</td>
 * <td>Dígito 1 da ASBACE</td>
 * </tr>
 * <tr>
 * <td>44-44</td>
 * <td>1</td>
 * <td>Z</td>
 * <td>Dígito 2 da ASBACE</td>
 * </tr>
 * </table>
 * </p>
 * 
 * <p>
 * Tabela de conversão da modalidade da conta:
 * <table>
 * <tr>
 * <td>Modalidade</td>
 * <td>01</td>
 * <td>04</td>
 * <td>09</td>
 * <td>13</td>
 * <td>16</td>
 * <td>17</td>
 * <td>18</td>
 * </tr>
 * <tr>
 * <td>Adotar</td>
 * <td>1</td>
 * <td>4</td>
 * <td>9</td>
 * <td>3</td>
 * <td>6</td>
 * <td>7</td>
 * <td>8</td>
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
class CLNossaCaixa extends AbstractCLNossaCaixa {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3478291082522788040L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 8;
	
	/**
	 * Necessário por poder ser modificado pelo cálculo do dígito 2
	 */
	private int digito1ASBACE;
	
	protected CLNossaCaixa(Titulo titulo) {

		super(FIELDS_LENGTH);
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		Integer modalidadeContaConvertida = convertaModalidadeDaConta(contaBancaria.getModalidade().getCodigoAsInteger());
		
		this.add(new Field<Integer>(getIdentificacaoDoSistemaPeloNossoNumero(titulo.getNossoNumero()), 1));
		
		this.add(new Field<String>(getNossoNumeroCom8Posicoes(titulo.getNossoNumero()), 8));
		
		this.add(new Field<Integer>(contaBancaria.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(modalidadeContaConvertida, 1));
		
		this.add(new Field<Integer>(contaBancaria.getNumeroDaConta().getCodigoDaConta(), 6, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(contaBancaria.getBanco().getCodigoDeCompensacaoBACEN().getCodigo(), 3));
		
		digito1ASBACE = calculeDigito1ASBACE(titulo, modalidadeContaConvertida);
		int digito2ASBACE = calculeDigito2ASBACE(titulo, modalidadeContaConvertida, digito1ASBACE);
		
		this.add(new Field<Integer>(digito1ASBACE, 1));
		
		this.add(new Field<Integer>(digito2ASBACE, 1));
	}
	
	/**
	 * 
	 * <p>
	 * Este dígito é calculado através do Módulo 11 com os pesos 2 e 7.
	 * </p>
	 * 
	 * @param titulo
	 * @param modalidadeDaConta
	 * @return
	 * 
	 * @since
	 */
	private int calculeDigito2ASBACE(Titulo titulo, Integer modalidadeDaConta, int digito1) {

		digito1ASBACE = digito1;
		String numeroParaCalculo = gereNumeroParaCalculoDosDigitosASBACE(titulo, modalidadeDaConta) + digito1ASBACE;
		
		Modulo modulo11 = new Modulo(TipoDeModulo.MODULO11);
		modulo11.setLimiteMinimo(2);
		modulo11.setLimiteMaximo(7);
		
		int resto = modulo11.calcule(numeroParaCalculo);
		int digito = resto;
		
		if(resto > 1) {
			digito = modulo11.getMod().valor() - resto;
			
		} else if(resto == 1) {
			
			digito1ASBACE++;
			if(digito1ASBACE == 10) {
				digito1ASBACE = 0;
			}
			
			calculeDigito2ASBACE(titulo, modalidadeDaConta, digito1ASBACE);
		}
		
		return digito;
	}

	/**
	 * 
	 * Este dígito é calculado através do Módulo 10
	 * 
	 * @param titulo
	 * @param modalidadeDaConta
	 * @return
	 * 
	 * @since
	 */
	private int calculeDigito1ASBACE(Titulo titulo, Integer modalidadeDaConta) {
		
		Modulo modulo10 = new Modulo(TipoDeModulo.MODULO10);
		int resto = modulo10.calcule(gereNumeroParaCalculoDosDigitosASBACE(titulo, modalidadeDaConta));
		
		int digito = resto;
		if(resto > 0) {
			digito = modulo10.getMod().valor() - resto;
		}
		
		return digito;
	}
	
	/**
	 * 
	 * <p>
	 * Gera o número que server para calcular os dígitos 1 e 2 da ASBACE
	 * </p>
	 * <p>
	 * Os campos utilizados são:
	 * <ul>
	 * <li>Identificação do sistema: 01 posições</li>
	 * <li>Nosso Número: 08 posições (2 a 9)</li>
	 * <li>Código da Agência Cedente (sem dígito): 04 posições</li>
	 * <li>Número da Conta Corrente: 06 posições</li>
	 * <li>Código do Banco Cedente: 03 posições</li>
	 * </ul>
	 * </p>
	 * 
	 * @param titulo
	 * @param modalidadeDaConta
	 * @return
	 * 
	 * @since
	 */
	private String gereNumeroParaCalculoDosDigitosASBACE(Titulo titulo, Integer modalidadeDaConta) {
		
		StringBuilder numeroParaCalculo = new StringBuilder();
		numeroParaCalculo.append(getIdentificacaoDoSistemaPeloNossoNumero(titulo.getNossoNumero()));
		numeroParaCalculo.append(getNossoNumeroCom8Posicoes(titulo.getNossoNumero()));
		numeroParaCalculo.append(Filler.ZERO_LEFT.fill(titulo.getContaBancaria().getAgencia().getCodigo(), 4));
		numeroParaCalculo.append(modalidadeDaConta);
		numeroParaCalculo.append(Filler.ZERO_LEFT.fill(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 6));
		numeroParaCalculo.append(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigo());
		
		return numeroParaCalculo.toString();
	}

	/**
	 * 
	 * <p>
	 * A identificação do sistema é o primeiro dígito do Nosso Número, que por obrigatoriedade 
	 * deve ser '9'.
	 * </p>
	 * 
	 * @param nossoNumero
	 * @return primeira posição do campo Nosso Número (9)
	 * 
	 * @since
	 */
	private Integer getIdentificacaoDoSistemaPeloNossoNumero(String nossoNumero) {
		
		return new Integer(nossoNumero.substring(0,1));
	}
	
	/**
	 * 
	 * <p>
	 * Retorna o Nosso Número sem a primeira posição, que pelo layout definido pelo banco
	 * contém os dígitos 99 nas duas primeiras posições. 
	 * </p>
	 * 
	 * @param nossoNumero completo
	 * @return Nosso Número sem a primeira posição.
	 * 
	 * @since
	 */
	private String getNossoNumeroCom8Posicoes(String nossoNumero) {
		
		if (  (nossoNumero.length() != 9) || (!nossoNumero.substring(0, 2).equals("99"))  ) 
			throw new CampoLivreException("O nosso número deve conter exatamente 9 posições, sendo as 2 posições iniciais obrigatoriamente igual a 99.");
		
		return nossoNumero.substring(1);
	}

	/**
	 * 
	 * <p>
	 * Realiza a conversão da modalidade da conta de acordo com a tabela de conversões.
	 * </p>
	 * 
	 * @param modalidadeDaConta
	 * @throws CampoLivreException caso a modalidade fornecida não seja um valor válido.
	 * @return
	 * 
	 * @since
	 */
	private Integer convertaModalidadeDaConta(Integer modalidadeDaConta) {
	
		final int[] modalidadesValidas = {1, 4, 9, 13, 16, 17, 18};
		
		if (modalidadeDaConta == null || Arrays.binarySearch(modalidadesValidas, modalidadeDaConta.intValue()) < 0) {
			throw new CampoLivreException("Campo livre diponível somente para títulos de contas correntes com modalidades 01, 04, 09, 13, 16, 17 e 18.");
		}
		
		int modalidadeConvertida = modalidadeDaConta.intValue();
		
		if (modalidadeDaConta.intValue() > 9) {
			modalidadeConvertida = modalidadeDaConta.intValue() - 10;
		}
	
		return modalidadeConvertida;
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
