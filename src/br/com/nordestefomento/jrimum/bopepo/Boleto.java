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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.ACurbitaObject;
import br.com.nordestefomento.jrimum.JRimumException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.FactoryCampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.bopepo.pdf.BoletoPDF;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo.E_Aceite;
import br.com.nordestefomento.jrimum.domkee.type.CEP;
import br.com.nordestefomento.jrimum.domkee.type.Endereco;
import br.com.nordestefomento.jrimum.domkee.type.EnumTitulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumUnidadeFederativa;
import br.com.nordestefomento.jrimum.domkee.type.Localidade;
import br.com.nordestefomento.jrimum.domkee.type.Logradouro;
import br.com.nordestefomento.jrimum.utilix.Util4Date;

import static br.com.nordestefomento.jrimum.bopepo.EnumBancos.BANCO_DO_BRASIL;

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
	
	public static void main(String[] args) throws FileNotFoundException, IOException, DocumentException {
		
		List<Boleto> boletos = new ArrayList<Boleto>(5);
		
		Titulo titulo;

		final Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3)
				.getTime();
		
		final Date DATA_DO_DOCUMENTO =  new GregorianCalendar(2000, Calendar.APRIL, 14)
		.getTime();

		Pessoa sacado = new Pessoa("Fulano da Silva Sauro Perdido e Desempregado", "222.222.222-22");
		
		Endereco endereco = new Endereco();
		endereco.setUf(EnumUnidadeFederativa.RN);
		endereco.setLocalidade(new Localidade("Natal"));
		endereco.setCep(new CEP("59064-120"));
		endereco.setBairro("Grande Centro");
		endereco.setLogradouro(new Logradouro("Rua Poeta das Princesas"));
		endereco.setNumero("1");
		
		sacado.addEndereco(endereco);
		
		Pessoa cedente = new Pessoa("Empresa Lucrativa para Todo Sempre Ilimitada", "00.000.208/0001-00");
	
		ContaBancaria contaBancaria = new ContaBancaria(BANCO_DO_BRASIL.newInstance());
		
		contaBancaria.setAgencia(new Agencia(1234, "67"));
		contaBancaria.setCodigoDaCarteira(5);
		contaBancaria.setNumeroDaConta(new NumeroDaConta(6789, "12"));

		cedente.addContaBancaria(contaBancaria);
		
		Pessoa sacadorAvalista = new Pessoa("Banco do Brasil", "00.000.000/0001-91");
		
		Endereco endereco2 = new Endereco();
		endereco2.setUf(EnumUnidadeFederativa.DF);
		endereco2.setLocalidade(new Localidade("Brasília"));
		endereco2.setCep(new CEP("00000-000"));
		endereco2.setBairro("Grande Centro");
		endereco2.setLogradouro(new Logradouro("Rua Principal Para Sempre"));
		endereco2.setNumero("001");

		sacadorAvalista.addEndereco(endereco2);
		
		//Fim Código em teste

		titulo = Titulo.getInstance(sacado, cedente, sacadorAvalista);
		titulo.setNumeroDoDocumento("123456789");
		titulo.setNossoNumero("1234567890");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoDocumento(DATA_DO_DOCUMENTO);
		titulo.setDataDoVencimento(VENCIMENTO);
		titulo.setTipoDeDocumento(EnumTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(E_Aceite.A);
	
		Boleto b1,b2,b3,b4,b5;
		b1 = Boleto.getInstance(titulo);
		
		b1.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
		b1.setInsturcaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado é injusto e esperamos seu pagamento assim mesmo.");
		b1.setInstrucao1("PARA PAGAMENTO 1 ");
	
		b2 = Boleto.getInstance(titulo);
		
		b2.setLocalPagamento(b1.getLocalPagamento());
		b2.setInsturcaoAoSacado(b1.getInsturcaoAoSacado());
		b2.setInstrucao1("PARA PAGAMENTO 2 ");
		
		b3 = Boleto.getInstance(titulo);
		
		b3.setLocalPagamento(b1.getLocalPagamento());
		b3.setInsturcaoAoSacado(b1.getInsturcaoAoSacado());
		b3.setInstrucao1("PARA PAGAMENTO 3 ");
		
		b4 = Boleto.getInstance(titulo);
		
		b4.setLocalPagamento(b1.getLocalPagamento());
		b4.setInsturcaoAoSacado(b1.getInsturcaoAoSacado());
		b4.setInstrucao1("PARA PAGAMENTO 4 ");
		
		b5 = Boleto.getInstance(titulo);
		
		b5.setLocalPagamento(b1.getLocalPagamento());
		b5.setInsturcaoAoSacado(b1.getInsturcaoAoSacado());
		b5.setInstrucao1("PARA PAGAMENTO 5 ");
		
		boletos.add(b1);
		boletos.add(b2);
		boletos.add(b3);
		boletos.add(b4);
		boletos.add(b5);
		
		int cont = 0;
		
		for (Boleto bop : boletos){
			
			cont++;
			//Util4File.bytes2File("t"+cont+".pdf",bop.getAsByteArray()); }
			bop.getAsPDF("t"+cont+".pdf");
		}
		//Boleto.groupInOnePDF("TesteVariosEmUm.pdf", boletos);		
	}

	public static File groupInOnePDF(String pathName, List<Boleto> boletos)throws JRimumException{
		
		File arq = null;
		
		try{
			
			arq = BoletoPDF.groupInOnePDF(pathName, boletos);
			
		}catch(Exception e){
			throw new JRimumException("Arquivo nao gerado!",e);
		}
		
		return arq;
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
