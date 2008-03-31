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


package br.com.nordestefomento.jrimum.bopepo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.ACurbitaObject;
import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.bopepo.pdf.BoletoPDF;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Util4Date;

import com.lowagie.text.DocumentException;


/**
 * 
 * É a representação do documento Boleto que por sua vez, representa títulos em cobrança.
 * 
 * A classe encapsula os atributos integrantes e as funcionalidades inerentes 
 * à construção de tal documento.
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
public final class Boleto extends ACurbitaObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4436063640418293021L;
	 
	protected static Logger log = Logger.getLogger(Boleto.class); 

	private Titulo titulo;
	
	/**
	 * Data de emissão do boleto de cobrança.
	 */
	private Date dataDeProcessamento;
	
	private CodigoDeBarra codigoDeBarra;
	private LinhaDigitavel linhaDigitavel;
	private ICampoLivre campoLivre;
	
	private String localPagamento;
	private String insturcaoAoSacado;
	
	private String instrucao1;
	private String instrucao2;
	private String instrucao3;
	private String instrucao4;
	private String instrucao5;
	private String instrucao6;
	private String instrucao7;
	private String instrucao8;
	
	private static File templatePadrao1 = new File(Boleto.class.getResource("/resource/pdf/BoletoTemplate1.pdf").getPath());
	private static File templatePadrao2 = new File(Boleto.class.getResource("/resource/pdf/BoletoTemplate2.pdf").getPath());
	
	private File template;
	private Map<String, String> listaCamposExtra; 
	
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
	 * @throws NotSuporttedBancoException 
	 * @throws NotSuporttedCampoLivreException 
	 */
	public Boleto(Titulo titulo)throws IllegalArgumentException, NotSuporttedBancoException, NotSuporttedCampoLivreException{
		super();
		getInstance(titulo);
	}

	/**
	 * @param titulo
	 * @param campoLivre
	 */
	public Boleto(Titulo titulo, ICampoLivre campoLivre) {
		super();
		
		getInstance(titulo, campoLivre);
	}

	public static Boleto getInstance(Titulo titulo)throws IllegalArgumentException, NotSuporttedBancoException, NotSuporttedCampoLivreException{
		
		Boleto boleto = null;
		
		if(log.isTraceEnabled())
			log.trace("Instanciando boleto");
		
		
		if(log.isDebugEnabled())
			log.debug("titulo instance : "+titulo);
		
		if(titulo != null){
			
			boleto = new Boleto();			
			boleto.setTitulo(titulo);
			boleto.setCampoLivre(FactoryCampoLivre.getInstance(titulo));
			boleto.load();
			
			if(log.isDebugEnabled())
				log.debug("boleto instance : "+boleto);
			
		}else {
			IllegalArgumentException e = new IllegalArgumentException("Título nulo!");
			log.error("Valor Não Permitido!",e);
			throw e;
		}
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Boleto Instanciado : "+boleto);
		
		return boleto;
	}
	
	public static Boleto getInstance(Titulo titulo, ICampoLivre campoLivre)throws IllegalArgumentException{
		
		Boleto boleto = null;
		
		if(log.isTraceEnabled())
			log.trace("Instanciando boleto");
		
		
		if(log.isDebugEnabled())
			log.debug("titulo instance : "+titulo);
		

		if(log.isDebugEnabled())
			log.debug("campoLivre instance : "+campoLivre);
		
		if(titulo != null){
			
			boleto = new Boleto();			
			boleto.setTitulo(titulo);
			boleto.setCampoLivre(campoLivre);
			boleto.load();
			
			if(log.isDebugEnabled())
				log.debug("boleto instance : "+boleto);
			
		}else {
			IllegalArgumentException e = new IllegalArgumentException("Título nulo!");
			log.error("Valor Não Permitido!",e);
			throw e;
		}
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Boleto Instanciado : "+boleto);
		
		return boleto;
	}
	
	public File getAsPDF(String pathName)throws IllegalArgumentException, IOException, DocumentException{

		BoletoPDF boletoPDF = BoletoPDF.getInstance(this);
		
		if(log.isDebugEnabled())
			log.debug("documento instance : " + boletoPDF);
		
		return boletoPDF.getFile(pathName);
	}
	
	public ByteArrayOutputStream getAsStream() throws IOException, DocumentException{
		
		BoletoPDF boletoPDF = BoletoPDF.getInstance(this);
		
		if(log.isDebugEnabled())
			log.debug("documento instance : "+boletoPDF);
		
		return boletoPDF.getStream();
		
	}
	
	public byte[] getAsByteArray() throws IOException, DocumentException{
		
		BoletoPDF boletoPDF = BoletoPDF.getInstance(this);
		
		if(log.isDebugEnabled())
			log.debug("documento instance : "+boletoPDF);
		
		return boletoPDF.getBytes();
	}
	
	private void load(){
		
		codigoDeBarra = CodigoDeBarra.getInstance(titulo, campoLivre);
		linhaDigitavel = LinhaDigitavel.getInstance(codigoDeBarra);
		dataDeProcessamento = new Date();
		
		log.info("Data de Processamento do Boleto : "+Util4Date.fmt_dd_MM_yyyy.format(dataDeProcessamento));
	}
	
	/**
	 * @return the campoLivre
	 */
	public ICampoLivre getCampoLivre() {
		return campoLivre;
	}

	/**
	 * @param campoLivre the campoLivre to set
	 */
	public void setCampoLivre(ICampoLivre campoLivre) {
		
		if(!isNull(campoLivre, "campoLivre")){
			
			int length = campoLivre.write().length();
			
			if(length == ICampoLivre.STRING_LENGTH)
				this.campoLivre = campoLivre;
			else
				if(length > ICampoLivre.STRING_LENGTH)
					throw new IllegalArgumentException("String length [ " + length + " ] greater than specified [ "+ICampoLivre.STRING_LENGTH+" ]!");
				else
					throw new IllegalArgumentException("String length [ " + length + " ] less than specified [ "+ICampoLivre.STRING_LENGTH+" ]!");
		}
	}

	/**
	 * @return the log
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public static void setLogger(Logger logger) {
		Boleto.log = logger;
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
	 * @return the dataDeProcessamento
	 */
	public Date getDataDeProcessamento() {
		return dataDeProcessamento;
	}

	/**
	 * @param dataDeProcessamento the dataDeProcessamento to set
	 */
	public void setDataDeProcessamento(Date dataDeProcessamento) {
		this.dataDeProcessamento = dataDeProcessamento;
	}

	/**
	 * @return the codigoDeBarra
	 */
	public CodigoDeBarra getCodigoDeBarra() {
		return codigoDeBarra;
	}

	/**
	 * @param codigoDeBarra the codigoDeBarra to set
	 */
	public void setCodigoDeBarra(CodigoDeBarra codigoDeBarra) {
		this.codigoDeBarra = codigoDeBarra;
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
	 * @return the localPagamento1
	 */
	public String getLocalPagamento() {
		return localPagamento;
	}

	/**
	 * @param localPagamento1 the localPagamento1 to set
	 */
	public void setLocalPagamento(String localPagamento1) {
		this.localPagamento = localPagamento1;
	}

	/**
	 * @return the insturcaoAoSacado
	 */
	public String getInsturcaoAoSacado() {
		return insturcaoAoSacado;
	}

	/**
	 * @param insturcaoAoSacado the insturcaoAoSacado to set
	 */
	public void setInsturcaoAoSacado(String insturcaoAoSacado) {
		this.insturcaoAoSacado = insturcaoAoSacado;
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
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public File getTemplate() {
		setTemplate(this.template);
		return template;
	}

	public void setTemplate(File template) {
		if (template == null) {
			if (titulo.getSacadorAvalista() != null)
				this.template = templatePadrao1;
			else
				this.template = templatePadrao2;
		}
		else {
			this.template = template;
		}		
	}
	
	public void setTemplate(String pathname) {
		setTemplate(new File(pathname));
	}

	public Map<String, String> getListaCamposExtra() {
		setListaCamposExtra(this.listaCamposExtra);
		return this.listaCamposExtra;
	}

	public void setListaCamposExtra(Map<String,String> camposTemplate) {
		if (camposTemplate == null)
			this.listaCamposExtra = new HashMap<String, String>();
		else
			this.listaCamposExtra = camposTemplate;
	}
	
	public void addCampoExtra(String nome, String valor) {
		
		getListaCamposExtra().put(nome, valor);
	}

}
