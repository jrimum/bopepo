/*
 * Copyright 2013 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 24/08/2013 - 19:54:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2013 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 24/08/2013 - 19:54:00
 * 
 */

package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;
import static org.jrimum.bopepo.parametro.ParametroBancoDeBrasilia.CHAVE_ASBACE_DIGITO1;
import static org.jrimum.bopepo.parametro.ParametroBancoDeBrasilia.CHAVE_ASBACE_DIGITO2;

import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Objects;
import org.jrimum.vallia.digitoverificador.Modulo;

/**
 * <p>
 * O campo livre do BRB - Banco de Brasília deve seguir esta forma:
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
 * <td >20-22</td>
 * <td >3</td>
 * <td >9(3)</td>
 * <td style="text-align:left;padding-left:10">fixo "000" ZEROS</td>
 * <td style="text-align:left;padding-left:10">ZEROS</td>
 * </tr>
 * <tr>
 * <td >23-25</td>
 * <td >3</td>
 * <td >9(3)</td>
 * <td style="text-align:left;padding-left:10">Agência Cedente (Sem o dígito verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência (sem dígito)</td>
 * </tr>
  * <tr>
 * <td >26-32</td>
 * <td >7</td>
 * <td >&nbsp;9(7)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (Sem o digito verificador, completar com zeros a esquerda quando necessário)</td>
 * <td style="text-align:left;padding-left:10">Conta do Cedente (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >33-33</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Carteira | Modalidade/ Categoria</td>
 * <td style="text-align:left;padding-left:10">Categoria - referente ao tipo de cobrança 1-sem registro impressão local, 2-com registro impressão local</td>
 * </tr>
 * <tr>
 * <td >34-39</td>
 * <td >6</td>
 * <td >&nbsp;9(6)</td>
 * <td style="text-align:left;padding-left:10">Número do Nosso Número(Sem o digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Número sequencial</td>
 * </tr>
 * <tr>
 * <td >40-42</td>
 * <td >3</td>
 * <td >&nbsp;9(3)</td>
 * <td style="text-align:left;padding-left:10">Código BACEN(Sem o digito verificador)</td>
 * <td style="text-align:left;padding-left:10">Código do Banco- 070 - BRB</td>
 * </tr>
 * <tr>
 * <td >43-43</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador 1 da CHAVE ASBACE</td>
 * <td style="text-align:left;padding-left:10">DV1</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >9</td>
 * <td style="text-align:left;padding-left:10">Dígito verificador 2 da CHAVE ASBACE</td>
 * <td style="text-align:left;padding-left:10">DV2</td>
 * </tr>
 * </table>
 * 
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:contato@douglasramiro.com.br">Douglas Ramiro</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
/**
 * @author gleitao
 *
 */
public class CLBancoDeBrasilia extends AbstractCLBancoDeBrasilia{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6099168685425835517L;
	
	/**
	 * Número de campos = 8.
	 */
	private static final Integer FIELDS_LENGTH = Integer.valueOf(8);

	/**
	 * Tamanho do primeiro campo "Campo Zerado [000]" = 3. 
	 */
	private static final Integer CAMPO_ZERADO_LENGTH = Integer.valueOf(3);
	
	/**
	 * Valor do campo "Campo Zerado" =  "000". 
	 */
	private static final String CAMPO_ZERADO_VALUE = "000";

	/**
	 * Tamanho do campo Agência = 3. 
	 */
	private static final Integer AGENCIA_LENGTH = Integer.valueOf(3);

	/**
	 * Tamanho do campo Conta = 7. 
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(7);
	
	/**
	 * Tamanho do campo Nosso Número = 6. 
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(6);

	/**
	 * Tamanho do campo Carteira = 1. 
	 */
	private static final Integer CARTEIRA_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo do dígito da chave ASBACE = 1. 
	 */
	private static final Integer DIGITO_CHAVE_ASBACE_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo Banco = 3. 
	 */
	private static final Integer BANCO_LENGTH = Integer.valueOf(3);
	
	/**
	 * Tipo de cobrança: 1-Sem registro impressão local (mesmo que carteira, modalidade ou categoria). 
	 */ 
	private static final Integer CARTEIRA_SEM_REGISTRO = Integer.valueOf(1);;
	
	/**
	 * Tipo de cobrança: 2-Com registro impressão local (mesmo que carteira, modalidade ou categoria). 
	 */
	private static final Integer CARTEIRA_COM_REGISTRO = Integer.valueOf(2);

	/**
	 * Dígito verificador calculado em função da CHAVE ASBACE e necessário para o cálculo do {@link #digitoVerificador2DaChaveASBACE}.
	 */
	private Integer digitoVerificador1DaChaveASBACE;

	/**
	 * Dígito verificador calculado em função da CHAVE ASBACE + {@link #digitoVerificador1DaChaveASBACE}.
	 */
	private Integer digitoVerificador2DaChaveASBACE;
	
	/**
	 * <p>
	 *   Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	protected CLBancoDeBrasilia() {
		super(FIELDS_LENGTH);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#checkValues(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 * 
	 * @since 0.2
	 */
	@Override
	protected void checkValues(Titulo titulo) {
		
		checkAgenciaNotNull(titulo);
		checkCodigoDaAgencia(titulo);
		checkCodigoDaAgenciaMenorOuIgualQue(titulo, 999);
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 9999999);
		checkNossoNumero(titulo);
		checkTamanhoDoNossoNumero(titulo, 6);
		checkCarteiraNotNull(titulo);
		checkCodigoDaCarteira(titulo);
		checkCarteiraComOuSemRegistro(titulo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre#addFields(org.jrimum.domkee.financeiro.banco.febraban.Titulo)
	 * 
	 * @since 0.2
	 */
	@Override
	protected void addFields(Titulo titulo) {
		
		this.add(new FixedField<String>(CAMPO_ZERADO_VALUE, CAMPO_ZERADO_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), AGENCIA_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getCarteira().getCodigo(), CARTEIRA_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<String>(titulo.getNossoNumero(), NOSSO_NUMERO_LENGTH, Fillers.ZERO_LEFT));
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigo(), BANCO_LENGTH, Fillers.ZERO_LEFT));
		calculeDigitosNecessariosDaChaveASBACE();
		this.add(new FixedField<Integer>(this.digitoVerificador1DaChaveASBACE, DIGITO_CHAVE_ASBACE_LENGTH));
		this.add(new FixedField<Integer>(this.digitoVerificador2DaChaveASBACE, DIGITO_CHAVE_ASBACE_LENGTH));	
		
		disponibilizeDigitosDaChaveAsbaceNeste(titulo);
	}
	
	/**
	 * Calcula os dos dígitos verificadores da CHAVE ASBACE.
	 * 
	 * @since 0.2
	 */
	private void calculeDigitosNecessariosDaChaveASBACE() {
		calculeChaveAsbaceDigito1();
		calculeChaveAsbaceDigito2();
	}

	/**
	 * Calcula o primeiro dígito da CHAVE ASBECE independente do segundo, mas o
	 * segundo DV depende deste e quando o segundo é calculado este pode,
	 * talvez, ser alterado.
	 * 
	 * @since 0.2
	 */
	private void calculeChaveAsbaceDigito1(){
		
		final String chaveAasbace = writeFields();
		
		int dig = Modulo.calculeMod10(chaveAasbace, 1, 2);
		
		if(dig == 0){
			this.digitoVerificador1DaChaveASBACE = dig;
		}else{
			this.digitoVerificador1DaChaveASBACE = 10 - dig;
		}
	}

	/**
	 * Calcula o segundo dígito da CHAVE ASBECE, recursivamente dependendo do valor do primeiro DV.
	 * 
	 * @since 0.2
	 */
	private void calculeChaveAsbaceDigito2(){
		final String chaveAasbaceComDv1 = writeFields()+this.digitoVerificador1DaChaveASBACE;
		
		int digito2 = Modulo.calculeMod11(chaveAasbaceComDv1, 2, 7);
		
		if(digito2 == 0){
			this.digitoVerificador2DaChaveASBACE = digito2;
		}else{
			
			if(digito2 != 1){
				this.digitoVerificador2DaChaveASBACE = 11 - digito2;
			}else{
				
				int digito1Recalculado = this.digitoVerificador1DaChaveASBACE + 1;
				
				this.digitoVerificador1DaChaveASBACE = (digito1Recalculado == 10) ? 0 : digito1Recalculado;
				
				calculeChaveAsbaceDigito2();
			}
		}
	}
	
	/**
	 * Disponibiliza no objeto titulo os dígitos da CHAVE ASBACE = mesmo que o
	 * campo livre menos os dois ultimos digitos.
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private void disponibilizeDigitosDaChaveAsbaceNeste(Titulo titulo) {
		
		ParametrosBancariosMap parametrosBancarios = titulo.getParametrosBancarios();
		
		if(parametrosBancarios == null){
			parametrosBancarios = new ParametrosBancariosMap();
		}
		
		parametrosBancarios.adicione(CHAVE_ASBACE_DIGITO1, this.digitoVerificador1DaChaveASBACE);
		parametrosBancarios.adicione(CHAVE_ASBACE_DIGITO2, this.digitoVerificador2DaChaveASBACE);
		
		titulo.setParametrosBancarios(parametrosBancarios);
	}
	
	/**
	 * <p>
	 * Verifica se o código da carteira da conta bancária do título não é nulo e
	 * se é um número > 0, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private void checkCarteiraComOuSemRegistro(Titulo titulo) {
		
		Integer codigoCarteira = titulo.getContaBancaria().getCarteira().getCodigo();
		
		final boolean carteiraValida = codigoCarteira.equals(CARTEIRA_SEM_REGISTRO) || codigoCarteira.equals(CARTEIRA_COM_REGISTRO);
		
		Objects.checkArgument(carteiraValida, format("Código da carteira deve ser \"1-Sem registro impressão local\" ou  \"2-Com registro impressão local\" e não [%s].", titulo.getContaBancaria().getCarteira().getCodigo()));
	}
	
}
