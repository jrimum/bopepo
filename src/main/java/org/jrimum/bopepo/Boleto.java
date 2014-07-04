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

package org.jrimum.bopepo;

import static org.jrimum.utilix.Objects.isNotNull;
import static org.jrimum.utilix.Objects.isNull;
import static org.jrimum.utilix.text.DateFormat.DDMMYYYY_B;

import java.awt.Image;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.CampoLivreFactory;
import org.jrimum.bopepo.campolivre.NotSupportedBancoException;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.bopepo.view.BoletoCampo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Exceptions;
import org.jrimum.utilix.Objects;

/**
 * <p>
 * É a representação do documento Boleto que por sua vez, representa títulos 
 * em cobrança.
 * </p>
 * 
 * <p>
 * A classe encapsula os atributos integrantes e as funcionalidades inerentes 
 * à construção de tal documento.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a> 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento Mercantil</a>
 * @author <a href="mailto:samuelvalerio@gmail.com">Samuel Valério</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class Boleto {
	
	private static Logger log = Logger.getLogger(Boleto.class);

	/**
	 * @see Titulo
	 */
	private Titulo titulo;
	
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
	 * Apenas cria um instâcia do boleto com os dados nulos. 
	 */
	public Boleto() {
		super();
	}
	
	/**
	 * Cria um boleto pronto para ser gerado.
	 * 
	 * @param titulo
	 * @throws NotSupportedBancoException 
	 * @throws NotSupportedCampoLivreException 
	 */
	public Boleto(Titulo titulo)throws IllegalArgumentException, NotSupportedBancoException, NotSupportedCampoLivreException{

		if(log.isTraceEnabled())
			log.trace("Instanciando boleto");
		
		if(log.isDebugEnabled())
			log.debug("titulo instance : "+titulo);
		
		if(isNotNull(titulo)){
			
			this.setTitulo(titulo);
			this.setCampoLivre(CampoLivreFactory.create(titulo));
			this.load();
			
			if(log.isDebugEnabled()){
				log.debug("boleto instance : " + this);
			}
			
		}else {
			
			if(log.isDebugEnabled()){
				log.debug("Título Nulo - Valor Não Permitido!");
			}
			
			Exceptions.throwIllegalArgumentException("Título nulo!");
		}
		
		if(log.isDebugEnabled() || log.isTraceEnabled()){
			log.trace("Boleto Instanciado : "+this);
		}

	}

	/**
	 * @param titulo
	 * @param campoLivre
	 */
	public Boleto(Titulo titulo, CampoLivre campoLivre) {
		super();

		if(log.isTraceEnabled())
			log.trace("Instanciando boleto");
		
		if(log.isDebugEnabled())
			log.debug("titulo instance : "+titulo);
		
		if(log.isDebugEnabled())
			log.debug("campoLivre instance : "+campoLivre);
		
		if(isNotNull(titulo)){
			
			this.setTitulo(titulo);
			this.setCampoLivre(campoLivre);
			this.load();
			
			if(log.isDebugEnabled())
				log.debug("boleto instance : "+this);
			
		}else {
			IllegalArgumentException e = new IllegalArgumentException("Título nulo!");
			log.error("Valor Não Permitido!",e);
			throw e;
		}
		
		if(log.isDebugEnabled() || log.isTraceEnabled()){
			
			log.trace("Boleto Instanciado : "+this);
		}
		
	}

	private void load(){
		
		codigoDeBarras = new CodigoDeBarras(titulo, campoLivre);
		linhaDigitavel = new LinhaDigitavel(codigoDeBarras);
		dataDeProcessamento = new Date();
		
		if(log.isInfoEnabled()){
			
			log.info("Data de Processamento do Boleto : "+DDMMYYYY_B.format(dataDeProcessamento));
		}
	}
	
	/**
	 * @return O campoLivre da isntância.
	 */
	public CampoLivre getCampoLivre() {
		
		return campoLivre;
	}

	/**
	 * @param campoLivre the campoLivre to set
	 */
	private void setCampoLivre(CampoLivre campoLivre) {
		
		Objects.checkNotNull(campoLivre);
		
		int length = campoLivre.write().length();
		
		if (length == CampoLivre.STRING_LENGTH) {
			this.campoLivre = campoLivre;
			
		} else {
			
			if (length > CampoLivre.STRING_LENGTH) {
				Exceptions.throwIllegalArgumentException("O tamanho da String [" + length + "] é maior que o especificado [" + CampoLivre.STRING_LENGTH + "]!");
				
			} else {
				Exceptions.throwIllegalArgumentException("O tamanho da String [" + length + "] é menor que o especificado [" + CampoLivre.STRING_LENGTH + "]!");
			}
		}
	}

	/**
	 * @return the titulo
	 */
	public Titulo getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
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
	 * <a href="http://www.jrimum.org/bopepo">glossário</a>.
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
	
	/**
	 * Sobrescreve um campo padrão do boleto.
	 * 
	 * @param campo
	 *            Nome do campo no template
	 * @param conteudo
	 *            Texto a ser adicionado ao campo no template
	 */
	public void sobrescrevaCampo(BoletoCampo campo, String conteudo){
		addTextosExtras(campo.name(), conteudo);
	}
	
	
	/**
	 * @return Todas os campos de texto usados como extra ou sobrescrita no
	 *         template.
	 */
	public Map<String, String> getTextosExtras() {
		
		return this.textosExtras;
	}

	
	/**
	 * Substitui todos os campos extra da instância caso exista.
	 * 
	 * @param textosExtras
	 *            Campos atribuídos
	 */
	public void setTextosExtras(Map<String,String> textosExtras) {

		this.textosExtras = textosExtras;
	}
	
	/**
	 * Adiciona um campo de texto no boleto caso o campo informado tenha o mesmo
	 * nome no template da instância.
	 * 
	 * @param campo
	 *            Nome do campo no template
	 * @param conteudo
	 *            Texto a ser adicionado ao campo no template
	 */
	public void addTextosExtras(String campo, String conteudo) {
		
		if(isNull(getTextosExtras())) {
			setTextosExtras(new HashMap<String, String>());
		}
		
		getTextosExtras().put(campo, conteudo);
	}
	
	/**
	 * @return Todas os campos de imagem usados como extra ou sobrescrita no
	 *         template.
	 */
	public Map<String, Image> getImagensExtras() {
		return this.imagensExtras;
	}

	/**
	 * Substitui todos os campos extra da instância caso exista.
	 * 
	 * @param imagensExtras
	 *            Campos atribuídos
	 */
	public void setImagensExtras(Map<String,Image> imagensExtras) {
		this.imagensExtras = imagensExtras;
	}
	
	/**
	 * Adiciona um campo de imagem no boleto caso o campo informado tenha o
	 * mesmo nome no template da instância.
	 * 
	 * @param campo
	 *            Nome do campo no template
	 * @param conteudo
	 *            Imagem a ser adicionada ao campo no template
	 */
	public void addImagensExtras(String campo, Image conteudo) {
		
		if(isNull(getImagensExtras())) {
			setImagensExtras(new HashMap<String, Image>());
		}
		
		getImagensExtras().put(campo, conteudo);
	}

	@Override
	public String toString() {
		return Objects.toString(this);
	}
}
