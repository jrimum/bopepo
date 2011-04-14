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
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoSeguimento;
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
	
	private String instrucaoAoCaixa1;
	private String instrucaoAoCaixa2;
	private String instrucaoAoCaixa3;
	private String instrucaoAoCaixa4;
	private String instrucaoAoCaixa5;
	private String instrucaoAoCaixa6;
	private String instrucaoAoCaixa7;
	private String instrucaoAoCaixa8;

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
	public Guia(Arrecadacao arrecadacao) throws IllegalArgumentException, 
	NotSupportedBancoException, NotSupportedCampoLivreException{	
		super();

		try {
			ObjectUtil.checkNotNull(arrecadacao);
			ObjectUtil.checkNotNull(arrecadacao.getOrgaoRecebedor());
			ObjectUtil.checkNotNull(arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		if (  isNull(arrecadacao.getConvenio()) || isNull(arrecadacao.getConvenio().getBanco())  ) {
			throw new IllegalArgumentException("Como não há um banco especificado através do convênio, a informação do CAMPO LIVRE terá de ser informado manualmente.");			 
		}
		
		
		if(log.isTraceEnabled())
			log.trace("Instanciando guia... ");
		
		if(log.isDebugEnabled())
			log.debug("Arrecadacao instance : " + arrecadacao);
		
		if(log.isDebugEnabled())
			log.debug("CampoLivre instance : " + campoLivre);
		
				
		this.setArrecadacao(arrecadacao);
		this.setCampoLivre(CampoLivreFactory.create(arrecadacao), arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		this.load();
			
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Guia instanciada : " + this);		
		

			
			
			
			
			
		if(log.isTraceEnabled())
			log.trace("Instanciando uma guia...");
		
		if(log.isDebugEnabled())
			log.debug("Arrecadaca instance : " + arrecadacao);
		
		if(isNotNull(arrecadacao)){
			this.setArrecadacao(arrecadacao);
			this.setCampoLivre(CampoLivreFactory.create(arrecadacao), arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
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
			ObjectUtil.checkNotNull(arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
			ObjectUtil.checkNotNull(campoLivre);	
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		if(log.isTraceEnabled())
			log.trace("Instanciando guia... ");
		
		if(log.isDebugEnabled())
			log.debug("Arrecadacao instance : " + arrecadacao);
		
		if(log.isDebugEnabled())
			log.debug("CampoLivre instance : " + campoLivre);
		
				
		this.setArrecadacao(arrecadacao);
		this.setCampoLivre(campoLivre, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		this.load();
			
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
	public void setCampoLivre(CampoLivre campoLivre, TipoSeguimento tipoSeguimento ) {		
		ObjectUtil.checkNotNull(campoLivre);
		ObjectUtil.checkNotNull(tipoSeguimento);		
		
		CampoLivreUtil.validar(campoLivre, tipoSeguimento);
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
	 * @return the instrucaoAoCaixa1
	 */
	public String getInstrucaoAoCaixa1() {
		return instrucaoAoCaixa1;
	}

	/**
	 * @return the instrucaoAoCaixa2
	 */
	public String getInstrucaoAoCaixa2() {
		return instrucaoAoCaixa2;
	}

	/**
	 * @return the instrucaoAoCaixa3
	 */
	public String getInstrucaoAoCaixa3() {
		return instrucaoAoCaixa3;
	}

	/**
	 * @return the instrucaoAoCaixa4
	 */
	public String getInstrucaoAoCaixa4() {
		return instrucaoAoCaixa4;
	}

	/**
	 * @return the instrucaoAoCaixa5
	 */
	public String getInstrucaoAoCaixa5() {
		return instrucaoAoCaixa5;
	}

	/**
	 * @return the instrucaoAoCaixa6
	 */
	public String getInstrucaoAoCaixa6() {
		return instrucaoAoCaixa6;
	}

	/**
	 * @return the instrucaoAoCaixa7
	 */
	public String getInstrucaoAoCaixa7() {
		return instrucaoAoCaixa7;
	}

	/**
	 * @return the instrucaoAoCaixa8
	 */
	public String getInstrucaoAoCaixa8() {
		return instrucaoAoCaixa8;
	}

	/**
	 * @param instrucaoAoCaixa1 the instrucaoAoCaixa1 to set
	 */
	public void setInstrucaoAoCaixa1(String instrucaoAoCaixa1) {
		this.instrucaoAoCaixa1 = instrucaoAoCaixa1;
	}

	/**
	 * @param instrucaoAoCaixa2 the instrucaoAoCaixa2 to set
	 */
	public void setInstrucaoAoCaixa2(String instrucaoAoCaixa2) {
		this.instrucaoAoCaixa2 = instrucaoAoCaixa2;
	}

	/**
	 * @param instrucaoAoCaixa3 the instrucaoAoCaixa3 to set
	 */
	public void setInstrucaoAoCaixa3(String instrucaoAoCaixa3) {
		this.instrucaoAoCaixa3 = instrucaoAoCaixa3;
	}

	/**
	 * @param instrucaoAoCaixa4 the instrucaoAoCaixa4 to set
	 */
	public void setInstrucaoAoCaixa4(String instrucaoAoCaixa4) {
		this.instrucaoAoCaixa4 = instrucaoAoCaixa4;
	}

	/**
	 * @param instrucaoAoCaixa5 the instrucaoAoCaixa5 to set
	 */
	public void setInstrucaoAoCaixa5(String instrucaoAoCaixa5) {
		this.instrucaoAoCaixa5 = instrucaoAoCaixa5;
	}

	/**
	 * @param instrucaoAoCaixa6 the instrucaoAoCaixa6 to set
	 */
	public void setInstrucaoAoCaixa6(String instrucaoAoCaixa6) {
		this.instrucaoAoCaixa6 = instrucaoAoCaixa6;
	}

	/**
	 * @param instrucaoAoCaixa7 the instrucaoAoCaixa7 to set
	 */
	public void setInstrucaoAoCaixa7(String instrucaoAoCaixa7) {
		this.instrucaoAoCaixa7 = instrucaoAoCaixa7;
	}

	/**
	 * @param instrucaoAoCaixa8 the instrucaoAoCaixa8 to set
	 */
	public void setInstrucaoAoCaixa8(String instrucaoAoCaixa8) {
		this.instrucaoAoCaixa8 = instrucaoAoCaixa8;
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
