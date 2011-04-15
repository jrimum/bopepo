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
 * Created at: 30/03/2008 - 18:09:27
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
 * Criado em: 30/03/2008 - 18:09:27
 * 
 */


package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

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
 * <td >20-23</td>
 * <td >4</td>
 * <td >9(4)</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente (Sem o digito verificador, completar com zeros a esquerda quando necessário)</td>
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
 * <td style="text-align:left;padding-left:10">Número do Nosso Número(Sem o digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >37-43</td>
 * <td >7</td>
 * <td >&nbsp;9(7)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (Sem o digito verificador, completar com zeros a esquerda quando necessário)</td>
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
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLBradesco extends AbstractCLBradesco {
	
	/**
	 * {@code serialVersionUID = -1253549781074159862L}
	 */
	private static final long serialVersionUID = -1253549781074159862L;

	/**
	 * Número de campos = 5.
	 */
	private static final Integer FIELDS_LENGTH = Integer.valueOf(5);

	/**
	 * Tamanho do campo Agência = 4. 
	 */
	private static final Integer AGENCIA_LENGTH = Integer.valueOf(4);
	
	/**
	 * Tamanho do campo Carteira = 2. 
	 */
	private static final Integer CARTEIRA_LENGTH = Integer.valueOf(2);
	
	/**
	 * Tamanho do campo Nosso Número = 11. 
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(11);
	
	/**
	 * Tamanho do campo Conta = 7. 
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(7);
	
	/**
	 * Tamanho do campo Constante = 1. 
	 */
	private static final Integer CONSTANT_LENGTH = Integer.valueOf(1);
	
	/**
	 * Valor do campo Constante =  0. 
	 */
	private static final Integer CONSTANT_VALUE = Integer.valueOf(0);

	/**
	 * Constante em forma de campo {@link #CONSTANT_VALUE} e {@link #CONSTANT_LENGTH}.
	 */
	private static final Field<Integer> CONSTANT_FIELD = new Field<Integer>(CONSTANT_VALUE, CONSTANT_LENGTH);
	
	/**
	 * Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}) deste campo.
	 * 
	 * @since 0.2
	 */
	protected CLBradesco() {
		
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
		checkCarteiraNotNull(titulo);
		checkCodigoDaCarteira(titulo);
		checkCodigoDaCarteiraMenorOuIgualQue(titulo, 99);
		checkNossoNumero(titulo);
		checkTamanhoDoNossoNumero(titulo, NN11);
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 9999999);
	}
	
	/**
	 *  {@inheritDoc}
	 *  
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#addFields(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 */
	@Override
	protected void addFields(Titulo titulo) {
		
		this.add(new Field<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), AGENCIA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getCarteira().getCodigo(), CARTEIRA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getNossoNumero(), NOSSO_NUMERO_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH, Filler.ZERO_LEFT));
		this.add(CONSTANT_FIELD );
	}
}
