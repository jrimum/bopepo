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


package br.com.nordestefomento.jrimum.bopepo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.LineOfFields;
import br.com.nordestefomento.jrimum.utilix.Util4Date;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.DV4BoletoCodigoDeBarra;


/**
 * 
 * É um número único para cada Boleto composto dos seguintes campos:
 * 
 * <br />
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="85%" id="composição"> <thead>
 * <tr>
 * <th>Posição </th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo</th>
 * </tr>
 * </thead>
 * <tr>
 * <td>01-03</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td>Identificação do banco </td>
 * </tr>
 * <tr>
 * <td>04-04 </td>
 * <td>1 </td>
 * <td>9 </td>
 * <td>Código moeda (9-Real) </td>
 * </tr>
 * <tr>
 * <td>05-05 </td>
 * <td>1 </td>
 * <td>9 </td>
 * <td>Dígito verificador do composição de barras (DV) </td>
 * </tr>
 * <tr>
 * <td>06-09 </td>
 * <td>4 </td>
 * <td>9(4)</td>
 * <td>Posições 06 a 09 – fator de vencimento</td>
 * </tr>
 * <tr>
 * <td>10-19</td>
 * <td>10</td>
 * <td>9(08)v99</td>
 * <td>Posições 10 a 19 – valor nominal do título&nbsp;</td>
 * </tr>
 * <tr>
 * <td>20-44 </td>
 * <td>25 </td>
 * <td>9(25) </td>
 * <td>Field livre – utilizado de acordo com a especificação interna do banco
 * emissor</td>
 * </tr>
 * </table>
 * 
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
public final class CodigoDeBarra extends LineOfFields{

	/**
	 * 
	 */
	private static final long serialVersionUID = 748913164143978133L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;
	
	/**
	 * 
	 */
	private static final Integer STRING_LENGTH = 44;

	/**
	 * Data Base de 07.10.1997, data usada pela FEBRABAN para realizar o cálculo
	 * do fator de vencimento.
	 * 
	 * @see #calculateFatorDeVencimento(Date)
	 */
	private static final Date DATA_BASE_DO_FATOR_DE_VENCIMENTO = new GregorianCalendar(
			1997, Calendar.OCTOBER, 7).getTime();

	
	/**
	 * Código do Banco.
	 */
	private Field<String> codigoDoBanco;
	
	/**
	 * Código da moeda usada no boleto.
	 */
	private Field<Integer> codigoDaMoeda;
	
	/**
	 * Mecanismo de autenticação usado no composição de barras.
	 * 
	 * @see br.com.nordestefomento.jrimum.vallia.digitoverificador.DV4BoletoCodigoDeBarra
	 */
	private Field<Integer> digitoVerificadorGeral;
	
	/**
	 * Representa a quantidade de dias decorridos da data base à data de
	 * vencimento do título.
	 * 
	 * @see #calculateFatorDeVencimento(Date)
	 */
	private Field<Integer> fatorDeVencimento;
	
	/**
	 * Valor do título.
	 */
	private Field<BigDecimal> valorNominalDoTitulo;
	
	/**
	 * @see br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre
	 */
	private Field<String> campoLivre;

	/**
	 * Acesso private para forçar o usuário desta classe a
	 * buscar o método <code>getInstance</code> com forma criação.
	 * 
	 * @param fieldsLength
	 * @param stringLength
	 */
	private CodigoDeBarra(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);	

		codigoDoBanco = new Field<String>("0", 3, Filler.ZERO_LEFT );
		codigoDaMoeda = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		digitoVerificadorGeral = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		fatorDeVencimento = new Field<Integer>(0, 4, Filler.ZERO_LEFT);
		valorNominalDoTitulo = new Field<BigDecimal>(new BigDecimal(0), 10, Filler.ZERO_LEFT);
		campoLivre = new Field<String>(StringUtils.EMPTY, 25);
		
		add(codigoDoBanco);
		add(codigoDaMoeda);
		add(digitoVerificadorGeral);
		add(fatorDeVencimento);
		add(valorNominalDoTitulo);
		add(campoLivre);
	
	}

	/**
	 * Retorna uma instância da classe.
	 * 
	 * @param titulo
	 * @see br.com.nordestefomento.jrimum.domkee.entity.Titulo
	 * @param campoLivre
	 * @see br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre
	 * @return códigoDeBarra
	 */
	static CodigoDeBarra getInstance(Titulo titulo, ICampoLivre campoLivre) {
		
		if(log.isTraceEnabled())
			log.trace("Instanciando o CodigoDeBarra");
			
		if(log.isDebugEnabled()){
			log.debug("titulo instance : "+titulo);
			log.debug("campoLivre instance : "+campoLivre);
		}
		
		CodigoDeBarra codigoDeBarra = new CodigoDeBarra(FIELDS_LENGTH ,STRING_LENGTH);
		
		//TODO Código em teste.
		//codigoDeBarra.codigoDoBanco.setField(titulo.getCedente().getContaBancaria().getE_Banco().getCodigo());
		ContaBancaria contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
		codigoDeBarra.codigoDoBanco.setField(contaBancaria.getBanco().getCodigoDeCompensacao());
		
		codigoDeBarra.codigoDaMoeda.setField(titulo.getE_Moeda().getCodigo());
		
		//Was here DigitoVerificador 
		//But wait
		codigoDeBarra.calculateAndSetFatorDeVencimento(titulo.getDataDoVencimento());
	
		codigoDeBarra.valorNominalDoTitulo.setField(titulo.getValor().movePointRight(2));
		codigoDeBarra.campoLivre.setField(campoLivre.write());
		
		//Now you can
		codigoDeBarra.calculateAndSetDigitoVerificadorGeral();
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.debug("codigoDeBarra instanciado : "+codigoDeBarra);

		return codigoDeBarra;
	}
	
	private void calculateAndSetDigitoVerificadorGeral() {

		if (log.isTraceEnabled())
			log.trace("Calculando Digito Verificador Geral");

		DV4BoletoCodigoDeBarra calculadorDV = new DV4BoletoCodigoDeBarra();

		StringBuilder toCalculateDV = new StringBuilder(codigoDoBanco.write())
		.append(codigoDaMoeda.write())
		.append(fatorDeVencimento.write())
		.append(valorNominalDoTitulo.write())
		.append(campoLivre.write());

		digitoVerificadorGeral.setField(calculadorDV.calcular(toCalculateDV.toString()));

		if (log.isDebugEnabled())
			log.debug("Digito Verificador Geral calculado : "
					+ digitoVerificadorGeral.getField());
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

		fatorDeVencimento.setField(new Long(Util4Date.calcularDiferencaEmDias(
				DATA_BASE_DO_FATOR_DE_VENCIMENTO, vencimento)).intValue());
	}

	/**
	 * @return the codigoDoBanco
	 */
	Field<String> getCodigoDoBanco() {
		return codigoDoBanco;
	}

	/**
	 * @param codigoDoBanco the codigoDoBanco to set
	 */
	void setCodigoDoBanco(Field<String> codigoDoBanco) {
		this.codigoDoBanco = codigoDoBanco;
	}

	/**
	 * @return the codigoDaMoeda
	 */
	Field<Integer> getCodigoDaMoeda() {
		return codigoDaMoeda;
	}

	/**
	 * @param codigoDaMoeda the codigoDaMoeda to set
	 */
	void setCodigoDaMoeda(Field<Integer> codigoDaMoeda) {
		this.codigoDaMoeda = codigoDaMoeda;
	}

	/**
	 * @return the digitoVerificadorGeral
	 */
	Field<Integer> getDigitoVerificadorGeral() {
		return digitoVerificadorGeral;
	}

	/**
	 * @param digitoVerificadorGeral the digitoVerificadorGeral to set
	 */
	void setDigitoVerificadorGeral(Field<Integer> digitoVerificadorGeral) {
		this.digitoVerificadorGeral = digitoVerificadorGeral;
	}

	/**
	 * @return the fatorDeVencimento
	 */
	Field<Integer> getFatorDeVencimento() {
		return fatorDeVencimento;
	}

	/**
	 * @param fatorDeVencimento the fatorDeVencimento to set
	 */
	void setFatorDeVencimento(Field<Integer> fatorDeVencimento) {
		this.fatorDeVencimento = fatorDeVencimento;
	}

	/**
	 * @return the valorNominalDoTitulo
	 */
	Field<BigDecimal> getValorNominalDoTitulo() {
		return valorNominalDoTitulo;
	}

	/**
	 * @param valorNominalDoTitulo the valorNominalDoTitulo to set
	 */
	void setValorNominalDoTitulo(Field<BigDecimal> valorNominalDoTitulo) {
		this.valorNominalDoTitulo = valorNominalDoTitulo;
	}

	/**
	 * @return the campoLivre
	 */
	Field<String> getCampoLivre() {
		return campoLivre;
	}

	/**
	 * @param campoLivre the campoLivre to set
	 */
	void setCampoLivre(Field<String> campoLivre) {
		this.campoLivre = campoLivre;
	}
	
}
