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

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;
import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

/**
 * <p>
 * O campo livre do banco SICREDI deve seguir esta forma:
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
 * <td style="text-align:left;padding-left:10">tipo de cobrança: "1" ou "3"</td>
 * <td style="text-align:left;padding-left:10">Código numérico correspondente ao tipo de cobrança: "1"- Com Registro ou "3"- Sem Registro</td>
 * </tr>
 * <tr>
 * <td >21-21</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Constante correspondente ao tipo de carteira: "1"- carteira simples. Embora no código fonte do manual mencione outras duas possibilidades "2"-Caucionada ou "3"-Descontada.</td>
 * <td style="text-align:left;padding-left:10">Código númerico correspondente ao tipo de carteira: "1" - carteira simples</td>
 * </tr>
 * <tr>
 * <td >22-30</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (8) + dígito verificador (1)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (9)</td>
 * </tr>
 * <tr>
 * <td >31-34</td>
 * <td >4</td>
 * <td style="text-align:left;padding-left:10">Cooperativa de crédito / agência cedente</td>
 * <td style="text-align:left;padding-left:10">Cooperativa de crédito / agência cedente</td>
 * </tr>
 * <tr>
 * <td >35-36</td>
 * <td >2</td>
 * <td style="text-align:left;padding-left:10">Unidade de atendimento / posto da agênica cedente</td>
 * <td style="text-align:left;padding-left:10">Unidade de atendimento / posto da agênica cedente</td>
 * </tr>
 * <tr>
 * <td >37-41</td>
 * <td >5</td>
 * <td style="text-align:left;padding-left:10">Código do Cedente</td>
 * <td style="text-align:left;padding-left:10">Código do Cedente</td>
 * </tr>
 * <tr>
 * <td >42-42</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Filler: 1 (um) quando o campo "valor do documento" diferente de 0 (zero) e cobrança sem registro, caso contrário zero.</td>
 * <td style="text-align:left;padding-left:10">Filler: 1 (um) quando o campo "valor do documento" diferente de 0 (zero), caso contrário zero.</td>
 * </tr>
 * <tr>
 * <td >43-43</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Constante "0"- Zero</td>
 * <td style="text-align:left;padding-left:10">Zero Fixo</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador do campo livre calculado por módulo 11 com aproveitamento total (resto igual a (0) zero ou (1) um o Dígito será (0) zero)</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador do campo livre calculado por módulo 11 com aproveitamento total (resto igual a (0) zero ou (1) um o Dígito será (0) zero)</td>
 * </tr>
 * </table>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLSicredi extends AbstractCLSicredi {

	/**
	 * {@code serialVersionUID = 7697120719706717353L}
	 */
	private static final long serialVersionUID = 7697120719706717353L;
	
	/**
	 * Número de campos = 9.
	 */
	protected static final Integer FIELDS_LENGTH = 9;

	/**
	 * <p>
	 * Código númerico correspondente ao tipo de cobrança: "1" - Com Registro.
	 * </p>
	 */
	private static final String COBRANCA_COM_REGISTRO = "1";
	
	/**
	 * <p>
	 * Código númerico correspondente ao tipo de cobrança: "3" - Sem Registro.
	 * </p>
	 */
	private static final String COBRANCA_SEM_REGISTRO = "3";

	/**
	 * <p>
	 * Valor constante do campo "Tipo da Carteira": "1" - carteira simples.
	 * </p>
	 */
	private static final Integer CARTEIRA_SIMPLES_VALUE = Integer.valueOf(1);
	
	/**
	 * <p>Nome do parâmetro bancário contendo o valor do posto da agência SICREDI.</p>
	 */
	private static final String POSTO_DA_AGENCIA = "PostoDaAgencia";
	
	/**
	 * <p>
	 * Segunda posição do campo livre.
	 * </p>
	 */
	private static final Field<Integer> FIELD_CARTEIRA = new Field<Integer>(CARTEIRA_SIMPLES_VALUE, 1);
	
	/**
	 * <p>
	 * Instância de módulo 11 para cálculo do DV do campo livre.   
	 * </p>
	 */
	private static final Modulo modulo11 = new Modulo(TipoDeModulo.MODULO11);

	/**
	 * <p>
	 *   Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	protected CLSicredi() {
		
		super(FIELDS_LENGTH);
	}
	
	@Override
	protected void checkValues(Titulo titulo){
		
		checkCarteiraNotNull(titulo);
		checkCodigoDaCarteira(titulo);
		checkCarteiraSimples(titulo);
		checkRegistroDaCarteiraNotNull(titulo);
		checkNossoNumero(titulo);
		checkTamanhoDoNossoNumero(titulo, NN8);
		checkDigitoDoNossoNumero(titulo);
		checkTamanhoDigitoDoNossoNumero(titulo, 1);
		checkCodigoDaAgencia(titulo);
		checkCodigoDaAgenciaMenorOuIgualQue(titulo, 10000);
		checkParametrosBancarios(titulo, POSTO_DA_AGENCIA);
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 100000);
		checkValor(titulo);
	}
	
	@Override
	protected void addFields(Titulo titulo) {
		
		if(titulo.getContaBancaria().getCarteira().isComRegistro()){
			
			this.add(new Field<String>(COBRANCA_COM_REGISTRO, 1));
			
		}else{
			
			this.add(new Field<String>(COBRANCA_SEM_REGISTRO, 1));
		}
		
		this.add(FIELD_CARTEIRA);
		this.add(new Field<String>(titulo.getNossoNumero()+titulo.getDigitoDoNossoNumero(), 9, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		this.add(new Field<Object>(titulo.getParametrosBancarios().getValor(POSTO_DA_AGENCIA), 2, Filler.ZERO_LEFT));
		this.add(new Field<Object>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 5, Filler.ZERO_LEFT));
		
		if (titulo.getContaBancaria().getCarteira().isSemRegistro() && titulo.getValor().compareTo(ZERO) == 1) {
			
			this.add(new Field<String>("1", 1));
			
		} else {
			
			this.add(new Field<String>("0", 1));
		}

		this.add(new Field<String>("0", 1));
		this.add(new Field<Integer>(calculeDigitoVerificador(), 1));
	}
	
	/**
	 * <p>
	 * Verifica se o código da carteira da conta bancária do título é igual 1
	 * (carteira simples), caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 */
	private void checkCarteiraSimples(Titulo titulo) {
		
		if(!titulo.getContaBancaria().getCarteira().getCodigo().equals(CARTEIRA_SIMPLES_VALUE)){
		
			throw new IllegalArgumentException(format("Apenas a carteira de código [1] \"carteira simples\" é permitida e não o código [%s]!", titulo.getContaBancaria().getCarteira().getCodigo()));
		}
	}

	/**
	 * <p>
	 * Calcula o dígito verificador deste campo livre (posição 25 do campo livre
	 * 44 do código de barras) com módulo 11 a partir das 24 posições deste
	 * campo livre.
	 * </p>
	 * 
	 * @return dígito verificador
	 * 
	 *  @since 0.2
	 */
	private Integer calculeDigitoVerificador() {

		final int resto = modulo11.calcule(writeFields());

		if (resto != 0 && resto != 1) {

			return Integer.valueOf(modulo11.valor() - resto);
			
		} else{
			
			return Integer.valueOf(resto);
		}
	}

}
