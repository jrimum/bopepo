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
 * Created at: 30/03/2008 - 18:04:23
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
 * Criado em: 30/03/2008 - 18:04:23
 * 
 */


package org.jrimum.bopepo;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.BlockOfFields;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.DecimalFormat;
import org.jrimum.vallia.digitoverificador.BoletoCodigoDeBarrasDV;


/**
 * <p>
 * É um número único para cada Boleto composto dos seguintes campos:
 * </p>
 * <div>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * 	collapse" bordercolor="#111111" width="100%"> 
 * <thead bgcolor="#DEDEDE">
 * <tr>
 * <th>Posição </th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo</th>
 * </tr>
 * </thead>
 * <tbody style="text-align:center">
 * <tr>
 * <td>01-03</td>
 * <td>3</td>
 * <td style="text-align:right;padding-right:10px">9(3)</td>
 * <td style="text-align:left;padding-left:10px">Identificação do banco </td>
 * </tr>
 * <tr>
 * <td>04-04 </td>
 * <td>1 </td>
 * <td style="text-align:right;padding-right:10px">9 </td>
 * <td style="text-align:left;padding-left:10px">Código moeda (9-Real) </td>
 * </tr>
 * <tr>
 * <td>05-05 </td>
 * <td>1 </td>
 * <td style="text-align:right;padding-right:10px">9 </td>
 * <td style="text-align:left;padding-left:10px">Dígito verificador do composição de barras (DV) </td>
 * </tr>
 * <tr>
 * <td>06-09 </td>
 * <td>4 </td>
 * <td style="text-align:right;padding-right:10px">9(4)</td>
 * <td style="text-align:left;padding-left:10px">Posições 06 a 09 – fator de vencimento</td>
 * </tr>
 * <tr>
 * <td>10-19</td>
 * <td>10</td>
 * <td style="text-align:right;padding-right:10px">9(08)v99</td>
 * <td style="text-align:left;padding-left:10px">Posições 10 a 19 – valor nominal do título&nbsp;</td>
 * </tr>
 * <tr>
 * <td>20-44 </td>
 * <td>25 </td>
 * <td style="text-align:right;padding-right:10px">9(25) </td>
 * <td style="text-align:left;padding-left:10px">FixedField livre – utilizado de acordo com a especificação interna do banco
 * emissor</td>
 * </tr>
 * </tbody>
 * </table>
 * </div>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a> 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CodigoDeBarras extends BlockOfFields{

	/**
	 * 
	 */
	private static final long serialVersionUID = 748913164143978133L;
	
	private static Logger log = Logger.getLogger(CodigoDeBarras.class);
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;
	
	/**
	 * 
	 */
	private static final Integer STRING_LENGTH = 44;
	
	/**
	 * Código do Banco.
	 */
	private FixedField<String> codigoDoBanco;
	
	/**
	 * Código da moeda usada no boleto.
	 */
	private FixedField<Integer> codigoDaMoeda;
	
	/**
	 * Mecanismo de autenticação usado no composição de barras.
	 * 
	 * @see org.jrimum.vallia.digitoverificador.BoletoCodigoDeBarrasDV
	 */
	private FixedField<Integer> digitoVerificadorGeral;
	
	/**
	 * Representa a quantidade de dias decorridos da data base à data de
	 * vencimento do título.
	 * 
	 * @see FatorDeVencimento#toFator(Date)
	 */
	private FixedField<Integer> fatorDeVencimento;
	
	/**
	 * Valor do título.
	 */
	private FixedField<BigDecimal> valorNominalDoTitulo;
	
	/**
	 * @see org.jrimum.bopepo.campolivre.CampoLivre
	 */
	private FixedField<String> campoLivre;
	
	/**
	 * <p>
	 * Cria um Código de Barras a partir do título e campo livre passados.
	 * </p>
	 * 
	 * @param titulo
	 * @param campoLivre
	 * 
	 * @see CampoLivre
	 */
	CodigoDeBarras(Titulo titulo, CampoLivre campoLivre) {
		super();
		setLength(STRING_LENGTH);
		setSize(FIELDS_LENGTH);
		
		if(log.isTraceEnabled())
			log.trace("Instanciando o CodigoDeBarras");
			
		if(log.isDebugEnabled()){
			log.debug("titulo instance : "+titulo);
			log.debug("campoLivre instance : "+campoLivre);
		}

		codigoDoBanco = new FixedField<String>("0", 3, Fillers.ZERO_LEFT);
		codigoDaMoeda = new FixedField<Integer>(0, 1, Fillers.ZERO_LEFT);
		digitoVerificadorGeral = new FixedField<Integer>(0, 1, Fillers.ZERO_LEFT);
		fatorDeVencimento = new FixedField<Integer>(0, 4, Fillers.ZERO_LEFT);
		valorNominalDoTitulo = new FixedField<BigDecimal>(new BigDecimal(0), 10,DecimalFormat.NUMBER_DD_BR.copy(),Fillers.ZERO_LEFT);
		this.campoLivre = new FixedField<String>(StringUtils.EMPTY, 25);
		
		add(codigoDoBanco);
		add(codigoDaMoeda);
		add(digitoVerificadorGeral);
		add(fatorDeVencimento);
		add(valorNominalDoTitulo);
		add(this.campoLivre);
	
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		this.codigoDoBanco.setValue(contaBancaria.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado());
		this.codigoDaMoeda.setValue(titulo.getTipoDeMoeda().getCodigo());
		this.calculateAndSetFatorDeVencimento(titulo.getDataDoVencimento());
		this.valorNominalDoTitulo.setValue(titulo.getValor());
		this.campoLivre.setValue(campoLivre.write());
		this.calculateAndSetDigitoVerificadorGeral();
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.debug("codigoDeBarra instanciado : "+this);
	}
	
	private void calculateAndSetDigitoVerificadorGeral() {
		
		if (log.isTraceEnabled())
			log.trace("Calculando Digito Verificador Geral");

		// Instanciando o objeto irá calcular o dígito verificador do boleto.
		BoletoCodigoDeBarrasDV calculadorDV = new BoletoCodigoDeBarrasDV();

		// Preparando o conjunto de informações que será a base para o cálculo
		// do dígito verificador, conforme normas da FEBRABAN.
		StringBuilder toCalculateDV = new StringBuilder(codigoDoBanco.write())
		.append(codigoDaMoeda.write())
		.append(fatorDeVencimento.write())
		.append(valorNominalDoTitulo.write())
		.append(campoLivre.write());

		// Realizando o cálculo dígito verificador e em seguida armazenando 
		// a informação no campo "digitoVerificadorGeral".
		digitoVerificadorGeral.setValue(
				calculadorDV.calcule(toCalculateDV.toString())
				);

		if (log.isDebugEnabled())
			log.debug("Digito Verificador Geral calculado : "
					+ digitoVerificadorGeral.getValue());
	}

	/**
	 * <p>
	 * Representa a quantidade de dias decorridos da data base à data de
	 * vencimento do título.
	 * </p>
	 * <p>
	 * É o resultado da subtração entre a data do vencimento do título e a DATA
	 * BASE, fixada em 07.10.1997 (03.07.2000 retrocedidos 1000 dias do in�cio
	 * do processo).
	 * </p>
	 * <p>
	 * Os bloquetos de cobrança emitidos a partir de primeiro de setembro de
	 * 2000 devem conter essas características, para que quando forem capturados
	 * pela rede bancária, os sistemas façam a operação inversa, ou seja,
	 * adicionar à data base o fator de vencimento capturado, obtendo, dessa
	 * forma, a data do vencimento do bloqueto.
	 * </p>
	 * 
	 * @param vencimento
	 */
	private void calculateAndSetFatorDeVencimento(Date vencimento) {

		fatorDeVencimento.setValue(
				FatorDeVencimento.toFator(vencimento));
	}

	/**
	 * @return the codigoDoBanco
	 */
	FixedField<String> getCodigoDoBanco() {
		return codigoDoBanco;
	}

	/**
	 * @param codigoDoBanco the codigoDoBanco to set
	 */
	void setCodigoDoBanco(FixedField<String> codigoDoBanco) {
		this.codigoDoBanco = codigoDoBanco;
	}

	/**
	 * @return the codigoDaMoeda
	 */
	FixedField<Integer> getCodigoDaMoeda() {
		return codigoDaMoeda;
	}

	/**
	 * @param codigoDaMoeda the codigoDaMoeda to set
	 */
	void setCodigoDaMoeda(FixedField<Integer> codigoDaMoeda) {
		this.codigoDaMoeda = codigoDaMoeda;
	}

	/**
	 * @return the digitoVerificadorGeral
	 */
	FixedField<Integer> getDigitoVerificadorGeral() {
		return digitoVerificadorGeral;
	}

	/**
	 * @param digitoVerificadorGeral the digitoVerificadorGeral to set
	 */
	void setDigitoVerificadorGeral(FixedField<Integer> digitoVerificadorGeral) {
		this.digitoVerificadorGeral = digitoVerificadorGeral;
	}

	/**
	 * @return the fatorDeVencimento
	 */
	FixedField<Integer> getFatorDeVencimento() {
		return fatorDeVencimento;
	}

	/**
	 * @param fatorDeVencimento the fatorDeVencimento to set
	 */
	void setFatorDeVencimento(FixedField<Integer> fatorDeVencimento) {
		this.fatorDeVencimento = fatorDeVencimento;
	}

	/**
	 * @return the valorNominalDoTitulo
	 */
	FixedField<BigDecimal> getValorNominalDoTitulo() {
		return valorNominalDoTitulo;
	}

	/**
	 * @param valorNominalDoTitulo the valorNominalDoTitulo to set
	 */
	void setValorNominalDoTitulo(FixedField<BigDecimal> valorNominalDoTitulo) {
		this.valorNominalDoTitulo = valorNominalDoTitulo;
	}

	/**
	 * @return the campoLivre
	 */
	FixedField<String> getCampoLivre() {
		return campoLivre;
	}

	/**
	 * @param campoLivre the campoLivre to set
	 */
	void setCampoLivre(FixedField<String> campoLivre) {
		this.campoLivre = campoLivre;
	}

	@Override
	public String toString() {
		return Objects.toString(this);
	}
}
