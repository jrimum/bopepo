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


package br.com.nordestefomento.jrimum.bopepo.guia;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivre;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoSeguimento;
import br.com.nordestefomento.jrimum.utilix.AbstractLineOfFields;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.GuiaCodigoDeBarrasDV;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.Modulo;

/**
 * 
 * É um número único para cada Guia composto dos seguintes campos:
 * <br/><br/>
 * 
 * <table border="1" cellpadding="1" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="85%" id="composição"> 
 * <thead>
 * <tr>
 * 		<th>Posição</th>
 * 		<th>Tamanho</th>
 * 		<th>Conteúdo</th>
 * 		<th>Descricao</th>
 * </tr>
 * </thead>
 * <tr>
 * 		<td>01-01</td>
 * 		<td>1</td>
 * 		<td>Identificação do Produto</td>
 * 		<td>Constante "8" para identificar arrecadação</td>
 * </tr>
 * <tr>
 * 		<td>02-02 </td>
 * 		<td>1 </td>
 * 		<td>Identificação do Segmento</td>
 * 		<td>
 * 			Identificará o segmento e a forma de identificação da Empresa/Órgão: <br/> 
 * 			1. Prefeituras; <br/> 
 * 			2. Saneamento;  <br/> 
 * 			3. Energia Elétrica e Gás; <br/>  
 * 			4. Telecomunicações;  <br/> 
 * 			5. Órgãos Governamentais; <br/>  
 * 			6. Carnes e Assemelhados ou demais  <br/>  
 * 			Empresas / Órgãos que serão identificadas através do CNPJ. <br/>  
 * 			7. Multas de trânsito  <br/> 
 * 			9. Uso exclusivo do banco  <br/> 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>03-03 </td>
 * 		<td>1</td>
 * 		<td>Identificação do valor real ou referência</td>
 * 		<td>
 * 
 * 			Este campo será: <br/>  <br/>
 * 			<strong>
 * 			“6”- Valor   a   ser  cobrado   efetivamente   em   reais
 * 			</strong>  <br/>
 * 			 com  dígito verificador calculado pelo módulo 10  na quarta  
 * 			posição do Código de  Barras e  valor  com    11 posições (versão 
 * 			2 e posteriores) sem qualquer alteração; <br/> <br/>
 * 			 
 * 			<strong>
 * 			“7”-  Quantidade   de  moeda 
 * 			</strong> <br/>  
 * 			Zeros – somente na impossibilidade de utilizar o valor;  <br/>
 * 			Valor a   ser  reajustado   por  um índice com  dígito verificador 
 * 			calculado pelo módulo 10   na  quarta 
 * 			posição do Código de Barras e valor com 11 posições  (versão 2 e 
 * 			posteriores).  <br/> <br/>
 * 			 
 * 			<strong>
 * 			“8” – Valor a ser cobrado efetivamente em reais
 * 			</strong><br/>
 * 			com  dígito verificador  calculado pelo módulo 11  na quarta  
 * 			posição do Código de  Barras e  valor  com 11 posições (versão 2 
 * 			e posteriores) sem qualquer alteração.  <br/><br/>
 * 			 
 * 			<strong>
 * 			“9” – Quantidade de moeda 
 *			</strong> <br/>
 * 			Zeros – somente na impossibilidade de utilizar o valor; 
 * 			Valor a   ser  reajustado   por  um índice 
 * 			 
 * 			com   dígito  .verificador calculado pelo módulo 11   na  quarta 
 * 			posição do Código de Barras e valor com 11 posições  (versão 2 e 
 * 			posteriores).  <br/>
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>04-04</td>
 * 		<td>1</td>
 * 		<td>Dígito verificador geral (módulo 10 ou 11)</td>
 * 		<td>   Dígito de auto conferência dos dados contidos  no Código de Barras.</td>
 * </tr>
 * <tr>
 * 		<td>05–15</td>
 * 		<td>11</td>
 * 		<td>Valor</td>
 * 		<td>
 * 			Se o campo “03 – Código de Moeda” indicar valor efetivo, este campo 
 * 			deverá conter o valor a ser cobrado. <br/><br/>

 * 			Se o campo “03 - Código de Moeda” indicado valor de referência, neste 
 * 			campo poderá conter uma quantidade de moeda, zeros, ou um valor a ser 
 * 			reajustado por um índice, etc... 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>16–19 <br/><br/> ou <br/><br/> 16-23</td>
 * 		<td>4 <br/><br/> ou <br/><br/> 8</td>
 * 		<td>
 * 				Identificação da Empresa/Órgão <br/><br/>
 * 				<CENTER>
 * 				Codigo Banco Febraban (4)  <br/> ou <br/>CNPJ Orgão Recebedor(8)
 * 				</CENTER>
 * 		</td>
 * 		<td>
 * 			O campo identificação da Empresa/Órgão terá uma codificação especial 
 * 			para cada segmento. <br/><br/>
 * 		 
 * 			Será um código de quatro posições atribuído e controlado pela Febraban (posições 16 a 19), 
 * 			ou as primeiras oito posições do cadastro geral de contribuintes do 
 * 			Ministério da Fazenda - CNPJ (posições 16 a 23). <br/><br/>
 * 		 
 * 			É através desta informação que o banco identificará a quem repassar as 
 * 			informações e o crédito. <br/><br/>  
 * 		 
 * 			Se for utilizado o CNPJ para identificar a Empresa/Órgão (posições 16 a 23), haverá uma 
 * 			redução no seu campo livre que passará a conter 21 posições (posições 24 a 44). <br/><br/> 
 * 		 
 * 			No caso de utilização do Segmento 9, este campo deverá conter o código 
 * 			de compensação do mesmo, com quatro dígitos (posições 16 a 29). Neste
 * 			caso o campo livre permanece com o tamanho padrão (posições 20 a 44)<br/><br/> 
 * 		 
 * 			Cada banco definirá a forma de identificação da empresa a partir da 1ª 
 * 			posição do campo livre (20 ou 24, de acordo com o que já foi explanado). 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>20–44 <br/><br/> ou <br/><br/> 24-44</td>
 * 		<td>25 <br/><br/> ou <br/><br/> 21</td>
 * 		<td> Campo livre de utilização da Empresa/Órgão</td>
 * 		<td>
 * 			Este campo é de uso exclusivo da Empresa/Órgão e será devolvido 
 * 			inalterado. <br/><br/>
 * 			 
 * 			Se existir data de vencimento no campo livre, ela deverá vir em primeiro 
 * 			lugar e em formato AAAAMMDD. 
 * 		</td>
 * </tr>
 * </table>
 * 
 * 
 * @author Misael Barreto 
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public final class CodigoDeBarras extends AbstractLineOfFields{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6280859254008661464L;

	private static Logger log = Logger.getLogger(CodigoDeBarras.class);
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 7;
	
	/**
	 * 
	 */
	private static final Integer STRING_LENGTH = 44;

	
	/**
	 *  Identificação do Produto. 
	 */
	private Field<Integer> produto;
	
	/**
	 *  Identificação do Segmento.
	 */
	private Field<Integer> segmento;
	
	/**
	 * Identificação do valor real ou referência.
	 */
	private Field<Integer> valorReferencia;
	
	/**
	 * Dígito verificador geral (módulo 10 ou 11) 
	 * Mecanismo de autenticação usado no composição de barras.
	 * 
	 * @see br.com.nordestefomento.jrimum.vallia.digitoverificador.GuiaCodigoDeBarrasDV
	 */
	private Field<Integer> digitoVerificadorGeral;
	
	/**
	 *  Valor
	 */
	private Field<BigDecimal> valor;
	

	/**
	 *  Identificação da Empresa/Órgão
	 */
	private Field<String> orgao;
	
	
	/**
	 * @see br.br.com.nordestefomento.jrimum.bopepo.campolivre.boleto.CampoLivre
	 */
	private Field<String> campoLivre;
	
	
	private Modulo moduloParaCalculoDV;
	
	/**
	 * <p>
	 * Cria um Código de Barras a partir do título e campo livre passados.
	 * </p>
	 * 
	 * @param arrecadacao
	 * @param campoLivre
	 * 
	 * @see CampoLivre
	 */
	CodigoDeBarras(Arrecadacao arrecadacao, CampoLivre campoLivre) {
		super(FIELDS_LENGTH ,STRING_LENGTH);
		
		if(log.isTraceEnabled())
			log.trace("Instanciando o CodigoDeBarras");
			
		if(log.isDebugEnabled()){
			log.debug("titulo instance : "+arrecadacao);
			log.debug("campoLivre instance : "+campoLivre);
		}

		// Configurando os campos.
		produto = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		segmento = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		valorReferencia = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		digitoVerificadorGeral = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		valor = new Field<BigDecimal>(new BigDecimal(0), 11, Filler.ZERO_LEFT);
		
		if (arrecadacao.getOrgaoRecebedor().getTipoSeguimento() == TipoSeguimento.USO_EXCLUSIVO_BANCO) {
			this.orgao = new Field<String>("0", 4, Filler.ZERO_LEFT);
			this.campoLivre = new Field<String>(StringUtils.EMPTY, 25);
		}
		else {
			this.orgao = new Field<String>("0", 8, Filler.ZERO_LEFT);
			this.campoLivre = new Field<String>(StringUtils.EMPTY, 21);
		}
			 
		
		// Adicionando os campos no código de barras, devidamente configurados.
		add(this.produto);
		add(this.segmento);
		add(this.valorReferencia);
		add(this.digitoVerificadorGeral);
		add(this.valor);
		add(this.orgao);
		add(this.campoLivre);
	
		
		// Informando o valor de cada campo.
		this.produto.setValue(arrecadacao.getTipoProduto().getCodigo());
		this.segmento.setValue(arrecadacao.getOrgaoRecebedor().getTipoSeguimento().getCodigo());
		this.valorReferencia.setValue(arrecadacao.getTipoValorReferencia().getCodigo());
		this.valor.setValue(arrecadacao.getValor().movePointRight(2));
		
		if (arrecadacao.getOrgaoRecebedor().getTipoSeguimento() == TipoSeguimento.USO_EXCLUSIVO_BANCO) {
			this.orgao.setValue(arrecadacao.getConvenio().getBanco().getCodigoDeCompensacaoBACEN().getCodigo().toString());
		}
		else {
			String cnpjFormatadoSemPontucao = arrecadacao.getOrgaoRecebedor().getCNPJ().getCodigoFormatadoSemPontuacao();
			this.orgao.setValue(cnpjFormatadoSemPontucao.substring(0, 8));
		}
		
		this.campoLivre.setValue(campoLivre.write());
		
		this.moduloParaCalculoDV = arrecadacao.getTipoValorReferencia().getModulo();
		this.calculateAndSetDigitoVerificadorGeral(this.moduloParaCalculoDV);
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.debug("codigoDeBarra instanciado : " + this);
	}

	
	
	private void calculateAndSetDigitoVerificadorGeral(Modulo modulo) {
		
		if (log.isTraceEnabled())
			log.trace("Calculando Digito Verificador Geral");

		// Instanciando o objeto irá calcular o dígito verificador da guia.
		GuiaCodigoDeBarrasDV calculadorDV = new GuiaCodigoDeBarrasDV(modulo);

		// Preparando o conjunto de informações que será a base para o cálculo
		// do dígito verificador, conforme normas da FEBRABAN.
		StringBuilder toCalculateDV = new StringBuilder(produto.write())
		.append(segmento.write())
		.append(valorReferencia.write())
		.append(valor.write())
		.append(orgao.write())
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
	 * @return the produto
	 */
	public Field<Integer> getProduto() {
		return produto;
	}

	/**
	 * @return the segmento
	 */
	public Field<Integer> getSegmento() {
		return segmento;
	}



	/**
	 * @return the valorReferencia
	 */
	public Field<Integer> getValorReferencia() {
		return valorReferencia;
	}



	/**
	 * @return the digitoVerificadorGeral
	 */
	public Field<Integer> getDigitoVerificadorGeral() {
		return digitoVerificadorGeral;
	}



	/**
	 * @return the valor
	 */
	public Field<BigDecimal> getValor() {
		return valor;
	}



	/**
	 * @return the orgao
	 */
	public Field<String> getOrgao() {
		return orgao;
	}



	/**
	 * @return the campoLivre
	 */
	public Field<String> getCampoLivre() {
		return campoLivre;
	}



	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}


	/**
	 * @return the moduloParaCalculoDV
	 */
	public Modulo getModuloParaCalculoDV() {
		return moduloParaCalculoDV;
	}
	
}
