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
 * Created at: 21/04/2008 - 20:31:39
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
 * Criado em: 21/04/2008 - 20:31:39
 * 
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;

/**
 * <p>
 * O campo livre do Bradesco deve seguir esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="campolivre">
 * <thead bgcolor="#DEDEDE">
 * <tr>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th>
 * </tr>
 * </thead> <tbody style="text-align:center">
 * <tr>
 * <td >20-20</td>
 * <td >1</td>
 * <td >9(1)</td>
 * <td style="text-align:left;padding-left:10">Sistema = constante 7</td>
 * <td style="text-align:left;padding-left:10">Valor fixo 7</td>
 * </tr>
 * <tr>
 * <td >21-34</td>
 * <td >14</td>
 * <td >9(14)</td>
 * <td style="text-align:left;padding-left:10">Código do cliente = Agência +
 * Conta</td>
 * <td style="text-align:left;padding-left:10">Código do cliente = (Ag[4] +
 * dv[1]) + (Cc[8] + dv[1])</td>
 * </tr>
 * <tr>
 * <td >35-43</td>
 * <td >9</td>
 * <td >&nbsp;9(9)</td>
 * <td style="text-align:left;padding-left:10">Número do Nosso Número(Sem o
 * digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número do título exatamente
 * com 9 dígitos (sem dígito verificador)</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Tipo cobrança = constante 2 -
 * Eletrônica Emitido pelo Cliente</td>
 * <td style="text-align:left;padding-left:10">Valor fixo 2</td>
 * </tr>
 * </table>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLBancoSafraCobrancaRegistrada extends AbstractCLBancoSafra {

	/**
	 * {@code serialVersionUID = -4532989921797507161L}
	 */
	private static final long serialVersionUID = -4532989921797507161L;
	
	/**
	 * Tamanho do número de campos = 7.
	 */
	protected static final Integer FIELDS_LENGTH = Integer.valueOf(7);
	
	/**
	 * Tamanho do campo Agência = 4. 
	 */
	private static final Integer AGENCIA_LENGTH = Integer.valueOf(4);
	
	/**
	 * Tamanho do campo Dígito da Agência = 1. 
	 */
	private static final Integer AGENCIA_DIGITO_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo Conta = 8. 
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(8);
	
	/**
	 * Tamanho do campo Dígito da Conta = 1. 
	 */
	private static final Integer CONTA_DIGITO_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo Nosso Número = 9. 
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(9);

	/**
	 * Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}
	 * ) deste campo.
	 * 
	 * @since 0.2
	 */
	protected CLBancoSafraCobrancaRegistrada() {

		super(FIELDS_LENGTH);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#checkValues(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 */
	@Override
	protected void checkValues(Titulo titulo){
		
		checkAgenciaNotNull(titulo);
		checkCodigoDaAgencia(titulo);
		checkCodigoDaAgenciaMenorOuIgualQue(titulo, 9999);
		checkDigitoDoCodigoDaAgencia(titulo);
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 999999);
		checkDigitoDoCodigoDoNumeroDaConta(titulo);
		checkNossoNumero(titulo);
		checkTamanhoDoNossoNumero(titulo, NN9);
	}

	/**
	 *  {@inheritDoc}
	 *  
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#addFields(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 */
	@Override
	protected void addFields(Titulo titulo) {

		this.add(SISTEMA_CONSTANT_FIELD);
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), AGENCIA_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<String>(titulo.getContaBancaria().getAgencia().getDigitoVerificador(), AGENCIA_DIGITO_LENGTH));
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<String>(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), CONTA_DIGITO_LENGTH));
		this.add(new FixedField<String>(titulo.getNossoNumero(), NOSSO_NUMERO_LENGTH));
		this.add(new FixedField<Integer>(TipoDeCobranca.DIRETA_BOLETO_EMITIDO_PELO_CLIENTE.codigo(), TIPO_COBRANCA_FIELD_LENGTH));
	}

}
