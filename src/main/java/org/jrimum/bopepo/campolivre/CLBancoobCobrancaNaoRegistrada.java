/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 17/02/2011 - 12:40:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 17/02/2011 - 12:40:00
 */

package org.jrimum.bopepo.campolivre;

import static org.jrimum.utilix.Objects.isNotNull;

import org.jrimum.bopepo.BoletoUtil;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * O campo livre do bradesco deve seguir esta forma:
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
 * <td >20-23</td>
 * <td >4</td>
 * <td >9(4)</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente (Sem o digito
 * verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >24-25</td>
 * <td >2</td>
 * <td >9(2)</td>
 * <td style="text-align:left;padding-left:10">Código da Carteira</td>
 * <td style="text-align:left;padding-left:10">Código da Carteira</td>
 * </tr>
 * <tr>
 * <td >26-36</td>
 * <td >11</td>
 * <td >&nbsp;9(11)</td>
 * <td style="text-align:left;padding-left:10">Número do Nosso Número(Sem o
 * digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >37-43</td>
 * <td >7</td>
 * <td >&nbsp;9(7)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (Sem o digito
 * verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Constante "0"</td>
 * <td style="text-align:left;padding-left:10">Zero Fixo</td>
 * </tr>
 * </table>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CLBancoobCobrancaNaoRegistrada extends AbstractCLBancoob{

	/**
	 * {@code serialVersionUID = 2864939240695151533L}
	 */
	private static final long serialVersionUID = 2864939240695151533L;
	
	/**
	 * Número de campos = 6.
	 */
	protected static final Integer FIELDS_LENGTH = 6;
	
	/**
	 * Tamanho do campo Carteira = 1. 
	 */
	protected static final Integer CARTEIRA_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo Agência = 4. 
	 */
	protected static final Integer AGENCIA_LENGTH = Integer.valueOf(4);
	
	/**
	 * Tamanho do campo Constante = 2.
	 */
	protected static final Integer CONSTANT_LENGTH = Integer.valueOf(2);
	
	/**
	 * Valor do campo Constante = 1.
	 */
	protected static final Integer CONSTANT_VALUE = Integer.valueOf(1);

	/**
	 * Constante em forma de campo {@link #CONSTANT_VALUE} e
	 * {@link #CONSTANT_LENGTH}, valor escrito: "01".
	 */
	private static final Field<Integer> CONSTANT_FIELD = new Field<Integer>(CONSTANT_VALUE, CONSTANT_LENGTH, Filler.ZERO_LEFT);
	
	/**
	 * Tamanho do campo Nosso Número = 8.
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(8);
	
	/**
	 * Tamanho do campo Conta = 7. 
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(7);
	
	/**
	 * Tamanho do campo Conta = 3. 
	 */
	private static final Integer NUMERO_DA_PARCELA_LENGTH = 3;
	
	/**
	 * <p>Nome do parâmetro bancário contendo o número da parcela do título.</p>
	 */
	private static final String NUMERO_DA_PARCELA = "NumeroDaParcela";
	
	/**
	 * Parcela única - constante para o campo parcela, valor escrito: "001".
	 */
	private static final Field<Integer> PARCELA_UNICA = new Field<Integer>(CONSTANT_VALUE, NUMERO_DA_PARCELA_LENGTH, Filler.ZERO_LEFT);

	/**
	 * <p>
	 *   Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	protected CLBancoobCobrancaNaoRegistrada() {
		
		super(FIELDS_LENGTH);
	}

	@Override
	protected void checkValues(Titulo titulo) {
		
		checkCarteiraNotNull(titulo);
		checkCodigoDaCarteira(titulo);
		checkCodigoDaCarteiraMenorOuIgualQue(titulo, 9);
		checkAgenciaNotNull(titulo);
		checkCodigoDaAgencia(titulo);
		checkCodigoDaAgenciaMenorOuIgualQue(titulo, 9999);
		checkNossoNumero(titulo);
		checkTamanhoDoNossoNumero(titulo, NN8);
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 9999999);
	}

	@Override
	protected void addFields(Titulo titulo) {
		
		this.add(new Field<Integer>(titulo.getContaBancaria().getCarteira().getCodigo(), CARTEIRA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), AGENCIA_LENGTH, Filler.ZERO_LEFT));
		this.add(CONSTANT_FIELD);
		this.add(new Field<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getNossoNumero(),NOSSO_NUMERO_LENGTH, Filler.ZERO_LEFT));

		if (isNotNull(titulo.getParametrosBancarios()) 
				&& titulo.getParametrosBancarios().contemComNome(NUMERO_DA_PARCELA)){

			checkParametrosBancarios(titulo, NUMERO_DA_PARCELA);
			
			this.add(new Field<Object>(titulo.getParametrosBancarios().getValor(NUMERO_DA_PARCELA), NUMERO_DA_PARCELA_LENGTH, Filler.ZERO_LEFT));

		} else {

			this.add(PARCELA_UNICA);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(BoletoUtil.getCampoLivreDaLinhaDigitavelFormatada("75691.99990 01777.777705 81234.560019 2 48860000295295"));
		System.out.println(BoletoUtil.getCampoLivreDaLinhaDigitavelFormatada("75691.43402 01000.000107 20000.010015 1 19260000050000"));
	}
	
}
