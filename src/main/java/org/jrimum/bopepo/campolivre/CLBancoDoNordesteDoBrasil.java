/* 
 * Copyright 2010 JRimum Project
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
 * Created at: 16/12/2010 - 13:24:00
 *
 * ================================================================================
 *
 * Direitos autorais 2010 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Created at: 16/12/2010 - 13:24:00
 * 
 */


package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * O campo livre do Banco do Nordeste deve seguir esta forma:
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
 * <td>20-23</td>
 * <td>4</td>
 * <td>9(4)</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente (sem o digito verificador, completar com zeros à esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>24-30</td>
 * <td>7</td>
 * <td>9(7)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (sem o dígito verificador, completar com zeros à esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>31-31</td>
 * <td>1</td>
 * <td>9(1)</td>
 * <td style="text-align:left;padding-left:10">Dígito da Conta do Cedente</td>
 * <td style="text-align:left;padding-left:10">Dígito da Conta do Cedente</td>
 * </tr>
 * <tr>
 * <td>32-38</td>
 * <td>7</td>
 * <td>9(7)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (sem o dígito verificador, completar com zeros à esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Nosso Número (No. Bancário) (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>39-39</td>
 * <td>1</td>
 * <td>9(1)</td>
 * <td style="text-align:left;padding-left:10">Dígito do Nosso Número</td>
 * <td style="text-align:left;padding-left:10">Dígito do Nosso Número (No. Bancário)</td>
 * </tr>
 * <tr>
 * <td>40-41</td>
 * <td>2</td>
 * <td>9(2)</td>
 * <td style="text-align:left;padding-left:10">Carteira (utilizar o código da carteira)</td>
 * <td style="text-align:left;padding-left:10">Carteira (Tipo de Operação)</td>
 * </tr>
 * <tr>
 * <td>42-44</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td style="text-align:left;padding-left:10">Preenchar campo com ZEROS.</td>
 * <td style="text-align:left;padding-left:10">Campo zerado</td>
 * </tr>
 * </table>
 * <br/>
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:pporto@gmail.com">Paulo Porto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLBancoDoNordesteDoBrasil extends AbstractCLBancoDoNordesteDoBrasil {
	
	/**
	 * {@code serialVersionUID = 5203223333877548162L}
	 */
	private static final long serialVersionUID = 5203223333877548162L;

	/**
	 * Número de campos = 7.
	 */
	private static final Integer FIELDS_LENGTH = Integer.valueOf(7);

	/**
	 * Tamanho do campo Agência = 4. 
	 */
	private static final Integer AGENCIA_LENGTH = Integer.valueOf(4);

	/**
	 * Tamanho do campo Conta = 7. 
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(7);

	/**
	 * Tamanho do campo Dígito da Conta = 1. 
	 */
	private static final Integer DIGITO_CONTA_LENGTH = Integer.valueOf(1);
	

	/**
	 * Tamanho do campo Nosso Número = 7. 
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(7);

	/**
	 * Tamanho do campo Dígito do Nosso Número = 1. 
	 */
	private static final Integer DIGITO_NOSSO_NUMERO_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo Carteira = 2. 
	 */
	private static final Integer CARTEIRA_LENGTH = Integer.valueOf(2);
	
	
	/**
	 * Tamanho do campo "Campo Zerado" = 3. 
	 */
	private static final Integer CAMPO_ZERADO_LENGTH = Integer.valueOf(3);
	
	/**
	 * Valor do campo "Campo Zerado" =  0. 
	 */
	protected static final Integer CAMPO_ZERADO_VALUE = Integer.valueOf(0);
	
	/**
	 * <p>
	 *   Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	protected CLBancoDoNordesteDoBrasil() {
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
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 9999999);
		checkDigitoDoCodigoDoNumeroDaConta(titulo);
		checkNossoNumero(titulo);
		checkDigitoDoNossoNumero(titulo);
		checkCarteiraNotNull(titulo);
		checkCodigoDaCarteira(titulo);
		checkCodigoDaCarteiraMenorOuIgualQue(titulo, 99);
	}
	
	/**
	 *  {@inheritDoc}
	 *  
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#addFields(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 */
	@Override
	protected void addFields(Titulo titulo) {
		
		this.add(new Field<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), AGENCIA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), DIGITO_CONTA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getNossoNumero(), NOSSO_NUMERO_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getDigitoDoNossoNumero(), DIGITO_NOSSO_NUMERO_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getCarteira().getCodigo(), CARTEIRA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(CAMPO_ZERADO_VALUE, CAMPO_ZERADO_LENGTH,  Filler.ZERO_LEFT));		
	}
}
