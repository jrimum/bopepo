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
 * Created at: 21/04/2008 - 21:54:06
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
 * Criado em: 21/04/2008 - 21:54:06
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
 * <td >21-26</td>
 * <td >6</td>
 * <td >9(6)</td>
 * <td style="text-align:left;padding-left:10">Fixo atribuído pelo banco (*)
 * Identificação numérica com cinco números + um dígito verificador</td>
 * <td style="text-align:left;padding-left:10">Número da conta + DV</td>
 * </tr>
 * <tr>
 * <td >27-43</td>
 * <td >17</td>
 * <td >&nbsp;9(17)</td>
 * <td style="text-align:left;padding-left:10">Livre do cliente - Variável
 * conforme necessidade do cliente</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Tipo cobrança = constante 4 -
 * Express Emitido pelo Cliente</td>
 * <td style="text-align:left;padding-left:10">Valor fixo 4</td>
 * </tr>
 * </table>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLBancoSafraCobrancaNaoRegistrada extends AbstractCLBancoSafra {

	/**
	 * {@code serialVersionUID = -6573340701469029151L}
	 */
	private static final long serialVersionUID = -6573340701469029151L;
	
	/**
	 * Tamanho do número de campos = 5.
	 */
	protected static final Integer FIELDS_LENGTH = Integer.valueOf(5);
	
	/**
	 * Tamanho do campo Conta = 5. 
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(5);
	
	/**
	 * Tamanho do campo Dígito da Conta = 1. 
	 */
	private static final Integer CONTA_DIGITO_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo Nosso Número = 17. 
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(17);

	/**
	 * Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}
	 * ) deste campo.
	 * 
	 * @since 0.2
	 */
	protected CLBancoSafraCobrancaNaoRegistrada() {

		super(FIELDS_LENGTH);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#checkValues(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 */
	@Override
	protected void checkValues(Titulo titulo) {
		
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 999999);
		checkDigitoDoCodigoDoNumeroDaConta(titulo);
		checkNossoNumero(titulo);
		checkTamanhoDoNossoNumero(titulo, NN17);
	}

	/**
	 *  {@inheritDoc}
	 *  
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#addFields(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 */
	@Override
	protected void addFields(Titulo titulo) {

		this.add(SISTEMA_CONSTANT_FIELD);
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<String>(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), CONTA_DIGITO_LENGTH));
		this.add(new FixedField<String>(titulo.getNossoNumero(), NOSSO_NUMERO_LENGTH));		
		this.add(new FixedField<Integer>(TipoDeCobranca.EXPRESS_BOLETO_EMITIDO_PELO_CLIENTE.codigo(), TIPO_COBRANCA_FIELD_LENGTH));
	}

}
