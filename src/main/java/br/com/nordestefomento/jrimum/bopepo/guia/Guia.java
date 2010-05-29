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
 * Created at: 30/03/2008 - 18:04:06
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
 * Criado em: 30/03/2008 - 18:04:06
 * 
 */

package br.com.nordestefomento.jrimum.bopepo.guia;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;
import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNull;

import java.awt.Image;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivreFactory;
import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivreUtil;
import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.NotSupportedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.NotSupportedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.IdentificacaoSeguimento;
import br.com.nordestefomento.jrimum.utilix.DateUtil;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;

/**
 * <p>
 * É a representação do documento Guia que por sua vez, representa um meio
 * para a arrecadação de fundos.
 * </p>
 * 
 * <p>
 * A classe encapsula os atributos integrantes e as funcionalidades inerentes 
 * à construção de tal documento.
 * </p>
 * 
 * @author Misael Barreto 
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public final class Guia {
	
	private static final long serialVersionUID = 4436063640418293021L;
	
	private static Logger log = Logger.getLogger(Guia.class);

	/**
	 * @see Arrecadacao
	 */
	private Arrecadacao arrecadacao;
	
	/**
	 * @see #setDataDeProcessamento(Date)
	 */
	private Date dataDeProcessamento;
	
	/**
	 * @see CodigoDeBarras
	 */
	private CodigoDeBarras codigoDeBarras;
	
	/**
	 * @see LinhaDigitavel
	 */
	private LinhaDigitavel linhaDigitavel;
	
	/**
	 * @see CampoLivre
	 */
	private CampoLivre campoLivre;
	
	/**
	 * @see #setLocalPagamento(String)
	 */
	private String localPagamento;
	
	/**
	 * @see #setInstrucaoAoSacado(String)
	 */
	private String instrucaoAoSacado;
	
	private String instrucao1;
	private String instrucao2;
	private String instrucao3;
	private String instrucao4;
	private String instrucao5;
	private String instrucao6;
	private String instrucao7;
	private String instrucao8;

	/**
	 * @see #setTextosExtras(Map)
	 */
	private Map<String, String> textosExtras; 
	
	/**
	 *<p>
	 * Map com nome do campo e imagem para este campo.
	 *</p>
	 */
	private Map<String, Image> imagensExtras; 
	
	/**
	 * Apenas cria um instâcia de guia com os dados nulos. 
	 */
	public Guia() {
		super();
	}
	
	/**
	 * Cria uma guia pronto para ser gerada.
	 * 
	 * @param arrecadacao
	 * @throws NotSupportedBancoException 
	 * @throws NotSupportedCampoLivreException 
	 */
	public Guia(Arrecadacao arrecadacao)throws IllegalArgumentException, 
	NotSupportedBancoException, NotSupportedCampoLivreException{

		if(log.isTraceEnabled())
			log.trace("Instanciando uma guia...");
		
		if(log.isDebugEnabled())
			log.debug("Arrecadaca instance : " + arrecadacao);
		
		if(isNotNull(arrecadacao)){
			this.setArrecadacao(arrecadacao);
			this.setCampoLivre(CampoLivreFactory.create(arrecadacao), arrecadacao.getOrgaoRecebedor().getIdentificacaoSeguimento());
			this.load();
			
			if(log.isDebugEnabled())
				log.debug("Guia instance : " + this);
			
		}else {
			IllegalArgumentException e = new IllegalArgumentException("Guia nula!");
			log.error("Valor não permitido!", e);
			throw e;
		}
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Guia Instanciado : " + this);

	}

	/**
	 * @param arrecadacao
	 * @param campoLivre
	 */
	public Guia(Arrecadacao arrecadacao, CampoLivre campoLivre) {
		super();

		try {
			ObjectUtil.checkNotNull(arrecadacao);
			ObjectUtil.checkNotNull(arrecadacao.getOrgaoRecebedor());
			ObjectUtil.checkNotNull(arrecadacao.getOrgaoRecebedor().getIdentificacaoSeguimento());
			ObjectUtil.checkNotNull(campoLivre);	
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		if(log.isTraceEnabled())
			log.trace("Instanciando guia... ");
		
		if(log.isDebugEnabled())
			log.debug("Arrecadacao instance : " + arrecadacao);
		
		if(log.isDebugEnabled())
			log.debug("campoLivre instance : " + campoLivre);
		
				
		this.setArrecadacao(arrecadacao);
		this.setCampoLivre(campoLivre, arrecadacao.getOrgaoRecebedor().getIdentificacaoSeguimento());
		this.load();
			
		if(log.isDebugEnabled())
			log.debug("Arrecadaca instance : "+this);
			
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Guia instanciada : " + this);
		
	}

	private void load(){
		codigoDeBarras = new CodigoDeBarras(arrecadacao, campoLivre);
		linhaDigitavel = new LinhaDigitavel(codigoDeBarras);
		dataDeProcessamento = new Date();
		
		log.info("Data de Processamento da Guia: " + DateUtil.FORMAT_DD_MM_YYYY.format(dataDeProcessamento));
	}
	
	/**
	 * @return the campoLivre
	 */
	public CampoLivre getCampoLivre() {
		return campoLivre;
	}

	/**
	 * <p>
	 * Camç
	 * </p>
	 * 
	 * @param campoLivre the campoLivre to set
	 */
	public void setCampoLivre(CampoLivre campoLivre, IdentificacaoSeguimento identificacaoSeguimento ) {		
		ObjectUtil.checkNotNull(campoLivre);
		ObjectUtil.checkNotNull(identificacaoSeguimento);		
		
		CampoLivreUtil.validar(campoLivre, identificacaoSeguimento);
		this.campoLivre = campoLivre;
	}
	
	/**
	 * @return Arrecadacao
	 */
	public Arrecadacao getArrecadacao() {
		return arrecadacao;
	}

	/**
	 * @param arrecadacao
	 */
	public void setArrecadacao(Arrecadacao arrecadacao) {
		this.arrecadacao = arrecadacao;
	}

	/**
	 * @see #getDataDeProcessamento()
	 * 
	 * @return the dataDeProcessamento
	 */
	public Date getDataDeProcessamento() {
		return dataDeProcessamento;
	}

	/**
	 * <p>
	 * Data de emissão do boleto de cobrança.
	 * </p>
	 * 
	 * @param dataDeProcessamento the dataDeProcessamento to set
	 */
	public void setDataDeProcessamento(Date dataDeProcessamento) {
		this.dataDeProcessamento = dataDeProcessamento;
	}

	/**
	 * @return the codigoDeBarras
	 */
	public CodigoDeBarras getCodigoDeBarras() {
		return codigoDeBarras;
	}

	/**
	 * @param codigoDeBarras the codigoDeBarras to set
	 */
	public void setCodigoDeBarras(CodigoDeBarras codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	/**
	 * @return the linhaDigitavel
	 */
	public LinhaDigitavel getLinhaDigitavel() {
		return linhaDigitavel;
	}

	/**
	 * @param linhaDigitavel the linhaDigitavel to set
	 */
	public void setLinhaDigitavel(LinhaDigitavel linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	/**
	 * @see #setLocalPagamento(String)
	 * 
	 * @return String local de pagamento
	 */
	public String getLocalPagamento() {
		return localPagamento;
	}

	/**
	 * <p>
	 * Possíveis locais para pagamento.
	 * </p>
	 * <p>
	 * Exemplo: <em>Pagável preferencialmente na Rede X ou em qualquer Banco até 
	 * o Vencimento.</em>
	 * </p>
	 * 
	 * @param localPagamento1 the localPagamento1 to set
	 */
	public void setLocalPagamento(String localPagamento1) {
		this.localPagamento = localPagamento1;
	}

	/**
	 * @see #setInstrucaoAoSacado(String)
	 * 
	 * @return the instrucaoAoSacado
	 */
	public String getInstrucaoAoSacado() {
		return instrucaoAoSacado;
	}

	/**
	 * <p>
	 *  Instrução adicional ao sacado, para visualizar o conceito de negócio de sacado consultar o 
	 * <a href="http://jrimum.nordestefomento.com.br/wprojeto/wiki/Glossario">glossário</a>.
	 * </p>
	 * 
	 * @param insturcaoAoSacado the insturcaoAoSacado to set
	 */
	public void setInstrucaoAoSacado(String insturcaoAoSacado) {
		this.instrucaoAoSacado = insturcaoAoSacado;
	}

	/**
	 * @return the instrucao1
	 */
	public String getInstrucao1() {
		return instrucao1;
	}

	/**
	 * @param instrucao1 the instrucao1 to set
	 */
	public void setInstrucao1(String instrucao1) {
		this.instrucao1 = instrucao1;
	}

	/**
	 * @return the instrucao2
	 */
	public String getInstrucao2() {
		return instrucao2;
	}

	/**
	 * @param instrucao2 the instrucao2 to set
	 */
	public void setInstrucao2(String instrucao2) {
		this.instrucao2 = instrucao2;
	}

	/**
	 * @return the instrucao3
	 */
	public String getInstrucao3() {
		return instrucao3;
	}

	/**
	 * @param instrucao3 the instrucao3 to set
	 */
	public void setInstrucao3(String instrucao3) {
		this.instrucao3 = instrucao3;
	}

	/**
	 * @return the instrucao4
	 */
	public String getInstrucao4() {
		return instrucao4;
	}

	/**
	 * @param instrucao4 the instrucao4 to set
	 */
	public void setInstrucao4(String instrucao4) {
		this.instrucao4 = instrucao4;
	}

	/**
	 * @return the instrucao5
	 */
	public String getInstrucao5() {
		return instrucao5;
	}

	/**
	 * @param instrucao5 the instrucao5 to set
	 */
	public void setInstrucao5(String instrucao5) {
		this.instrucao5 = instrucao5;
	}

	/**
	 * @return the instrucao6
	 */
	public String getInstrucao6() {
		return instrucao6;
	}

	/**
	 * @param instrucao6 the instrucao6 to set
	 */
	public void setInstrucao6(String instrucao6) {
		this.instrucao6 = instrucao6;
	}

	/**
	 * @return the instrucao7
	 */
	public String getInstrucao7() {
		return instrucao7;
	}

	/**
	 * @param instrucao7 the instrucao7 to set
	 */
	public void setInstrucao7(String instrucao7) {
		this.instrucao7 = instrucao7;
	}

	/**
	 * @return the instrucao8
	 */
	public String getInstrucao8() {
		return instrucao8;
	}

	/**
	 * @param instrucao8 the instrucao8 to set
	 */
	public void setInstrucao8(String instrucao8) {
		this.instrucao8 = instrucao8;
	}

	public Map<String, String> getTextosExtras() {
		
		return this.textosExtras;
	}

	public void setTextosExtras(Map<String,String> textosExtras) {

		this.textosExtras = textosExtras;
	}
	
	public void addTextosExtras(String nome, String valor) {
		
		if(isNull(getTextosExtras())) {
			setTextosExtras(new HashMap<String, String>());
		}
		
		getTextosExtras().put(nome, valor);
	}
	
	public Map<String, Image> getImagensExtras() {
		return this.imagensExtras;
	}

	public void setImagensExtras(Map<String,Image> imagensExtras) {
		this.imagensExtras = imagensExtras;
	}
	
	public void addImagensExtras(String fieldName, Image image) {
		
		if(isNull(getImagensExtras())) {
			setImagensExtras(new HashMap<String, Image>());
		}
		
		getImagensExtras().put(fieldName, image);
	}

	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
