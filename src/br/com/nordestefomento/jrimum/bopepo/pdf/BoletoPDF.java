/*
 * Copyright 2007, JMatryx Group
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright 2007, Grupo JMatryx
 * 
 * Licenciado sob a licença da Apache, versão 2.0 (a “licença”); você não pode
 * usar este arquivo exceto na conformidade com a licença. Você pode obter uma
 * cópia da licença em
 * 
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">
 * http://www.apache.org/licenses/LICENSE-2.0 </a>
 * 
 * A menos que seja requerido pela aplicação da lei ou esteja de acordo com a
 * escrita, o software distribuído sob esta licença é distribuído “COMO É”
 * BASE,SEM AS GARANTIAS OU às CONDIÇÕES DO TIPO, expresso ou implicado. Veja a
 * licença para as permissões sobre a línguagem específica e limitações quando
 * sob licença.
 * 
 * 
 * Created at / Criado em : 02/04/2007 - 17:25:36
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.pdf;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.campolivre.ICampoLivre;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo.E_Aceite;
import br.com.nordestefomento.jrimum.domkee.type.ACadastroDePessoa;
import br.com.nordestefomento.jrimum.domkee.type.CEP;
import br.com.nordestefomento.jrimum.domkee.type.Endereco;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;
import br.com.nordestefomento.jrimum.domkee.type.EnumMoeda;
import br.com.nordestefomento.jrimum.domkee.type.EnumTitulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumUnidadeFederativa;
import br.com.nordestefomento.jrimum.domkee.type.Localidade;
import br.com.nordestefomento.jrimum.domkee.type.Logradouro;
import br.com.nordestefomento.jrimum.utilix.Documento;
import br.com.nordestefomento.jrimum.utilix.Operator4Date;
import br.com.nordestefomento.jrimum.utilix.Operator4Monetary;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.BaseFont;

/**
 * 
 * Descrição:
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
public class BoletoPDF extends Documento {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5595813462700749861L;

	/**
	 * 27% do tamanho normal da imagem do boleto.
	 */
	private static final int PERCENTUAL_DE_MINIMIZACAO = 27;
	
	/**
	 * Largura do logo-tipo do banco.
	 */
	private static final float SIZE_X_LOGO_BANCO = 128;

	/**
	 * Altura do logo-tipo do banco.
	 */
	private static final float SIZE_Y_LOGO_BANCO = 30;
	
	/**
	 * Coordenada do início de uma linha.
	 */
	private static final float INICIAL_X = 14;
	
	/**
	 * Coordenada da altura base na parte superior do recibo do sacado,
	 * primeira linha de informação.
	 */
	private static final float INICIAL_Y = 540;
	
	/**
	 * Tamanho médio de uma linha para outra.
	 */
	private static final float LINHA = 24;
	
	 /**
	  * Separador entre campos de dados de Pessoa.
	  */
	private static final String SEPERADOR = "-";
	
	/**
	 * Fonte normal.
	 */
	private static BaseFont baseFont;
	
	/**
	 * Fonte em negrito.
	 */
    private static BaseFont baseFontBold;
	
    /**
     * Imagem do Boleto usada como Background.
     */
	private Image imgBoleto = null;

	/**
	 * Imagem para Logo-Tipo do banco.
	 */
	private Image imgLogoBanco = null;
	
	/**
	 * Imagem do código de barras.
	 */
	private Image imgBarCode = null;
	
    static {
    	
    	try{
    		
    		baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        	
            baseFontBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
            
    	}catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	
    	
    }

	/**
	 * 
	 */
	private BoletoPDF() {
		super();
	}

	/**
	 * @param tituloDoc
	 * @param autorDoc
	 * @throws DocumentException
	 */
	private BoletoPDF(String tituloDoc, String autorDoc)
			throws DocumentException {
		super(tituloDoc, autorDoc);
	}

	/**
	 * 
	 * @param boleto
	 * @return BoletoPDF
	 * @throws IllegalArgumentException
	 */
	public static BoletoPDF getInstance(Boleto boleto)
			throws IllegalArgumentException{

		BoletoPDF boletoPDF = null;
		
		if(log.isTraceEnabled())
			log.trace("Instanciando Boleto Em PDF");
		
		if(log.isDebugEnabled())
			log.debug("boleto instance : "+boleto);

		if (boleto != null) {

			try {
				
				boletoPDF = new BoletoPDF("Boleto Bancário",
						"JMatryx Corporation.");
				
				initPDF(boletoPDF);
				
				setBoletoBackground(boletoPDF);
				
				setReciboDoSacado(boleto, boletoPDF);
		        
		        //---linha
		        
		        setFichaDeCompensacao(boleto, boletoPDF);
				
		        setCodigoDeBarras(boleto, boletoPDF);
		        
				boletoPDF.documento.close();
				
				if(log.isDebugEnabled() || log.isTraceEnabled())
					log.trace("Boleto Em PDF Instanciado : "+boletoPDF);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}

		} else{
			IllegalArgumentException e = new IllegalArgumentException("Boleto nulo!");
			log.error("Valor Não Permitido!",e);
			throw e;
		}

		return boletoPDF;
	}

	/**
	 * @param boletoPDF
	 */
	private static void initPDF(BoletoPDF boletoPDF) {
		boletoPDF.documento.open();
		
		boletoPDF.contentByte = boletoPDF.writer.getDirectContent();
		
		boletoPDF.documento.newPage();
	}

	/**
	 * @param boletoPDF
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	private static void setBoletoBackground(BoletoPDF boletoPDF) throws MalformedURLException, IOException, DocumentException {
		
		boletoPDF.imgBoleto = Image.getInstance(boletoPDF.getClass()
				.getResource("/resource/img/Boleto_Completo.png"));

		boletoPDF.imgBoleto.scalePercent(PERCENTUAL_DE_MINIMIZACAO);
		
		/*
		 * posicionamento na unidade IText (x,y) 9 da esquerda para direita 40
		 * de baixo para cima. No espaço de 40 e o fim da págia fica o código de
		 * barra.
		 */
		boletoPDF.imgBoleto.setAbsolutePosition(9, 40);
		
		boletoPDF.documento.add(boletoPDF.imgBoleto);
	}	

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param x
	 * @param y
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private static void setReciboDoSacado(Boleto boleto, BoletoPDF boletoPDF) throws BadElementException, MalformedURLException, IOException, DocumentException {
		
		/*
		 * coordenadas no plano do documento. Os valores baseados na
		 * fixação da imagem de background do boleto.
		 */
        Ponto p = boletoPDF.new Ponto();
        
        p.x = INICIAL_X;
        p.y = INICIAL_Y;
		
		setLogoBanco(boleto, boletoPDF,p);
		
		boletoPDF.contentByte.beginText();   
		
		setFontCodigoDoBanco(boletoPDF);

		setCodigoDoBanco(boleto, boletoPDF,p);
		
		setFontLinhaDigitavel(boletoPDF);
		
		setLinhaDigitavel(boleto, boletoPDF,p);  
		
		setFontDados(boletoPDF);
   
		setNomeDoCedente(boleto, boletoPDF, p);
		
		p.x =+ 283;
		
		setCodigoDaAgenciaConta(boleto, boletoPDF, p);
     
		p.x += 115;
		
		setEspecie(boleto, boletoPDF, p);
		
		p.x += 80;
		
		setNossoNumero(boleto, boletoPDF, p);
		
		p.x = INICIAL_X;
		p.y -= LINHA;
		
		setNumeroDoDocumento(boleto, boletoPDF, p);
		
		p.x += 180;
		
		setCadastroDePessoaDoCedente(boleto, boletoPDF, p);
		
		p.x += 120;
		
		setVencimento(boleto, boletoPDF, p);
		
		p.x += 120;
		
		setValor(boleto, boletoPDF, p);
		
		p.x = INICIAL_X;
		p.y -= 2*LINHA;
		
		setNome_E_CadastroDePessoa(boleto.getTitulo().getSacado(), boletoPDF, p);
		
		setInstrucaoAoSacado(boleto, boletoPDF, p);
		
		boletoPDF.contentByte.endText();

	}
	
	/**
	 * @param boleto
	 * @param boletoPDF
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private static void setFichaDeCompensacao(Boleto boleto, BoletoPDF boletoPDF) throws DocumentException, MalformedURLException, IOException {
		
		/*
		 * coordenadas no plano do documento. Os valores baseados na
		 * fixação da imagem de background do boleto.
		 */
		Ponto p = boletoPDF.new Ponto();
        p.x = INICIAL_X;
        p.y = INICIAL_Y - 191;
		
        setLogoBanco(boleto, boletoPDF,p);
		
		boletoPDF.contentByte.beginText(); 
		
		setFontCodigoDoBanco(boletoPDF);
		
		setCodigoDoBanco(boleto, boletoPDF,p);
		
		setFontLinhaDigitavel(boletoPDF);
		
		setLinhaDigitavel(boleto, boletoPDF,p); 
		
		setFontDados(boletoPDF);
		
		setLocalDePagamento(boleto, boletoPDF, p);
		
		p.x += 372;
		
		setVencimento(boleto, boletoPDF, p);
		
		setNomeDoCedente(boleto, boletoPDF, p);
		
		p.x += 434;
		
		setCodigoDaAgenciaConta(boleto, boletoPDF, p);
		
		setDataDoDocumento(boleto, boletoPDF, p);
		
		p.x += 73;
		
		setNumeroDoDocumento(boleto, boletoPDF, p);
		
		setTipoDoDocumento(boleto, boletoPDF, p);
		
		setAceite(boleto, boletoPDF, p);
		
		setDataDeProcessamento(boleto, boletoPDF, p);
		
		p.x += 112;
		
		setNossoNumero(boleto, boletoPDF, p);
		
		setCodigoDaCarteira(boleto, boletoPDF, p);
		
		p.x += 60;
		
		setEspecie(boleto, boletoPDF, p);
		
		p.x += 299;
			
		setValor(boleto, boletoPDF, p);
		
		setInstrucoesAoCaixa(boleto, boletoPDF, p);

		p.x = INICIAL_X;
		p.y -= LINHA + 3;
		
		setDadosDePessoa(boleto.getTitulo().getSacado(), boletoPDF, p);
		
		p.y -= LINHA - 4;
		
		setDadosDePessoa(boleto.getTitulo().getSacadorAvalista(), boletoPDF, p);
		
		boletoPDF.contentByte.endText();
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param x
	 * @param y
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private static void setLogoBanco(Boleto boleto, BoletoPDF boletoPDF, Ponto p) throws BadElementException, MalformedURLException, IOException, DocumentException {
		
		//TODO Código em teste
		ContaBancaria conta = boleto.getTitulo().getCedente().getContasBancarias().iterator().next();
		boletoPDF.imgLogoBanco = Image.getInstance(boletoPDF.getClass()
				.getResource("/resource/img/"+conta.getBanco().getCodigoDeCompensacao()+".png"));
		
		boletoPDF.imgLogoBanco.scaleAbsolute(SIZE_X_LOGO_BANCO, SIZE_Y_LOGO_BANCO);
		
		boletoPDF.imgLogoBanco.setAbsolutePosition(p.x,p.y);
		
		boletoPDF.documento.add(boletoPDF.imgLogoBanco);
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setCodigoDoBanco(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x += 138;
		p.y += 2;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		//TODO Código em teste
		ContaBancaria conta = boleto.getTitulo().getCedente().getContasBancarias().iterator().next();
		
		if(isNotNull(conta.getBanco()))
			boletoPDF.contentByte.showText(conta.getBanco().getCodigoDeCompensacao());
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setLinhaDigitavel(Boleto boleto, BoletoPDF boletoPDF,Ponto p) {
		
		p.x += 52;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	        
		
		boletoPDF.contentByte.showText(boleto.getLinhaDigitavel().write());
	}
	
	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setNomeDoCedente(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x = INICIAL_X;
		p.y -= LINHA;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	  
		
		if(isNotNull(boleto.getTitulo().getCedente().getNome()))
			boletoPDF.contentByte.showText(boleto.getTitulo().getCedente().getNome());
		
	}
	
	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setDadosDePessoa(Pessoa pessoa, BoletoPDF boletoPDF, Ponto p) {
		
		float subLinha = LINHA - 15;
		
		StringBuilder sb = new StringBuilder("");
		
		setNome_E_CadastroDePessoa(pessoa, boletoPDF, p);
		
		//TODO Código em teste
		Endereco endereco = pessoa.getEnderecos().iterator().next();
		
		if(isNotNull(endereco)){
			
			boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );
			
			if(isNotNull(endereco.getBairro()))
				sb.append(endereco.getBairro());
		
			//TODO Código em teste
			if(isNotNull(endereco.getLocalidade().getNome()))
				sb.append(SEPERADOR+endereco.getLocalidade().getNome());
			
			if(isNotNull(endereco.getUf()))
				sb.append(" / "+endereco.getUf().toString());
			
			boletoPDF.contentByte.showText(sb.toString());
			
			sb.delete(0,sb.length());
			
			boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );
			
			if(isNotNull(endereco.getLogradouro()))
				sb.append(endereco.getLogradouro());
			
			if(isNotNull(endereco.getNumero()))
				sb.append(", n°: "+ endereco.getNumero());
			
			if(isNotNull(endereco.getCep()))
				sb.append(SEPERADOR+"Cep: "+ endereco.getCep());
			
			boletoPDF.contentByte.showText(sb.toString());
			
		}else{
			boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );
			boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );
		}
		
	}
	
	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setNome_E_CadastroDePessoa(Pessoa pessoa, BoletoPDF boletoPDF, Ponto p) {
		
		StringBuilder sb = new StringBuilder("");
		
		if(isNotNull(pessoa.getNome())){
			sb.append(pessoa.getNome());
		}
		
		if(isNotNull(pessoa.getCadastroDePessoa())){
			
			sb.append(", ");
			
			if(pessoa.getCadastroDePessoa().isFisica())
				sb.append("Cpf: ");
			else
				if(pessoa.getCadastroDePessoa().isJuridica())
					sb.append("Cnpj: ");
			
			sb.append(pessoa.getCadastroDePessoa().getCodigoFormatado());
		}
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		boletoPDF.contentByte.showText(sb.toString());
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setInstrucoesAoCaixa(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x = INICIAL_X;
		p.y -= LINHA + 3;
		
		float subLinha = LINHA/2;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	        

		if(isNotNull(boleto.getInstrucao1()))
			boletoPDF.contentByte.showText(boleto.getInstrucao1());
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );	        
		
		if(isNotNull(boleto.getInstrucao2()))
			boletoPDF.contentByte.showText(boleto.getInstrucao2());
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );	        

		if(isNotNull(boleto.getInstrucao3()))
			boletoPDF.contentByte.showText(boleto.getInstrucao3());
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );	        

		if(isNotNull(boleto.getInstrucao4()))
			boletoPDF.contentByte.showText(boleto.getInstrucao4());
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );	        

		if(isNotNull(boleto.getInstrucao5()))
			boletoPDF.contentByte.showText(boleto.getInstrucao5());
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );	        

		if(isNotNull(boleto.getInstrucao6()))
			boletoPDF.contentByte.showText(boleto.getInstrucao6());
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );	        

		if(isNotNull(boleto.getInstrucao7()))
			boletoPDF.contentByte.showText(boleto.getInstrucao7());
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y -= subLinha );
		
		if(isNotNull(boleto.getInstrucao8()))
			boletoPDF.contentByte.showText(boleto.getInstrucao8());
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setCodigoDaCarteira(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x = INICIAL_X + 75;
		p.y -= LINHA;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	
		
		//TODO Código em teste
		ContaBancaria conta = boleto.getTitulo().getCedente().getContasBancarias().iterator().next();
		if(isNotNull(conta.getCodigoDaCarteira()))
			boletoPDF.contentByte.showText(conta.getCodigoDaCarteira().toString());
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setDataDeProcessamento(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x += 64;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	   
		
		if(isNotNull(boleto.getDataDeProcessamento()))
			boletoPDF.contentByte.showText(Operator4Date.fmt_dd_MM_yyyy.format(boleto.getDataDeProcessamento()));
		
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setAceite(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x += 90;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	
		
		if(isNotNull(boleto.getTitulo().getAceite()))
			boletoPDF.contentByte.showText(boleto.getTitulo().getAceite().toString());
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setTipoDoDocumento(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x += 95;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	
		
		if(isNotNull(boleto.getTitulo().getTipoDeDocumento().getSigla()))
				boletoPDF.contentByte.showText(boleto.getTitulo().getTipoDeDocumento().getSigla());
		
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setDataDoDocumento(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x = INICIAL_X;
		p.y -= LINHA;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	  
		
		if(isNotNull(boleto.getTitulo().getDataDoDocumento()))
			boletoPDF.contentByte.showText(Operator4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoDocumento()));
		
	}
	

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setInstrucaoAoSacado(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x = INICIAL_X;
		p.y -= LINHA;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		if(isNotNull(boleto.getInsturcaoAoSacado()))
			boletoPDF.contentByte.showText(boleto.getInsturcaoAoSacado());
		
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setValor(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		if(isNotNull(boleto.getTitulo().getValor()))
			boletoPDF.contentByte.showText(Operator4Monetary.fmt_Real.format(boleto.getTitulo().getValor()));
		
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setVencimento(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		if(isNotNull(boleto.getTitulo().getDataDoVencimento()))
				boletoPDF.contentByte.showText(Operator4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoVencimento()));
		
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setCadastroDePessoaDoCedente(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		if(isNotNull(boleto.getTitulo().getCedente().getCadastroDePessoa().getCodigoFormatado()))
			boletoPDF.contentByte.showText(boleto.getTitulo().getCedente().getCadastroDePessoa().getCodigoFormatado());
		
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setNumeroDoDocumento(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		if(isNotNull(boleto.getTitulo().getNumeroDoDocumento()))
			boletoPDF.contentByte.showText(boleto.getTitulo().getNumeroDoDocumento());
		
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setNossoNumero(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		StringBuilder sb = new StringBuilder("");
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		if(isNotNull(boleto.getTitulo().getNossoNumero()))
			sb.append(boleto.getTitulo().getNossoNumero());
		
		if(isNotNull(boleto.getTitulo().getDigitoDoNossoNumero()))
			sb.append(BoletoPDF.SEPERADOR+boleto.getTitulo().getDigitoDoNossoNumero());
		
		boletoPDF.contentByte.showText(sb.toString());
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setEspecie(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);	
		
		if(isNotNull(boleto.getTitulo().getE_Moeda().toString()))
			boletoPDF.contentByte.showText(boleto.getTitulo().getE_Moeda().toString());
	}

	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setCodigoDaAgenciaConta(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		StringBuilder sb = new StringBuilder("");
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		//TODO Código em teste
		ContaBancaria conta = boleto.getTitulo().getCedente().getContasBancarias().iterator().next();
		
		if(isNotNull(conta.getAgencia().getCodigoDaAgencia()))
			sb.append(conta.getAgencia().getCodigoDaAgencia());
		
		if(isNotNull(conta.getAgencia().getDigitoDaAgencia()) && StringUtils.isNotBlank(conta.getAgencia().getDigitoDaAgencia())){
			
			sb.append(BoletoPDF.SEPERADOR);
			sb.append(conta.getAgencia().getDigitoDaAgencia());
		}
		
		if(isNotNull(conta.getNumeroDaConta().getCodigoDaConta())){
			
			sb.append(" / ");
			
			sb.append(conta.getNumeroDaConta().getCodigoDaConta());
			
			if(isNotNull(conta.getNumeroDaConta().getDigitoDaConta())){
				
				sb.append(BoletoPDF.SEPERADOR);
				sb.append(conta.getNumeroDaConta().getDigitoDaConta());
			}
		}
		//Fim Código em teste
		
		boletoPDF.contentByte.showText(sb.toString());
	}

	
	
	/**
	 * @param boleto
	 * @param boletoPDF
	 * @param p
	 */
	private static void setLocalDePagamento(Boleto boleto, BoletoPDF boletoPDF, Ponto p) {
		
		p.x = INICIAL_X + 63;
		p.y -= LINHA - 10;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
		
		if(isNotNull(boleto.getLocalPagamento()))
			boletoPDF.contentByte.showText(boleto.getLocalPagamento());
		
		p.y -= 10;
		
		boletoPDF.contentByte.setTextMatrix(p.x,p.y);
	}


	/**
	 * @param boletoPDF
	 */
	private static void setFontDados(BoletoPDF boletoPDF) {
		boletoPDF.contentByte.setFontAndSize(baseFont,8);
	}

	/**
	 * @param boletoPDF
	 */
	private static void setFontLinhaDigitavel(BoletoPDF boletoPDF) {
		boletoPDF.contentByte.setFontAndSize(baseFontBold,13);
	}

	/**
	 * @param boletoPDF
	 */
	private static void setFontCodigoDoBanco(BoletoPDF boletoPDF) {
		boletoPDF.contentByte.setFontAndSize(baseFontBold,20);
	}
	
	/**
	 * @param boleto
	 * @param boletoPDF
	 * @throws DocumentException
	 */
	private static void setCodigoDeBarras(Boleto boleto, BoletoPDF boletoPDF) throws DocumentException {
		BarcodeInter25 code = new BarcodeInter25();
		
		code.setCode(boleto.getCodigoDeBarra().write());
		
		code.setExtended(true);
		code.setBarHeight(40);
		code.setFont(null);
		code.setN(3);
		
		boletoPDF.imgBarCode = code.createImageWithBarcode(boletoPDF.contentByte, null,null);
		
		/*
		 * posicionamento na unidade IText (x,y) 11 da esquerda para direita 10
		 * de baixo para cima. Posicionado entre a imagem do boleto inferior
		 * (Ficha de Compensação) e o final da página.
		 */
		boletoPDF.imgBarCode.setAbsolutePosition(11, 10);
		
		boletoPDF.documento.add(boletoPDF.imgBarCode);
	}

	/**
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			DocumentException, IOException {

		Titulo titulo;

		final Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3)
				.getTime();
		
		final Date DATA_DO_DOCUMENTO =  new GregorianCalendar(2000, Calendar.APRIL, 14)
		.getTime();

		Boleto boleto;

		Pessoa sacado = new Pessoa();
		
		sacado.setNome("Fulano da Silva Sauro Perdido e Desempregado");
		sacado.setCadastroDePessoa(ACadastroDePessoa.getInstance("222.222.222-22"));
		
		Endereco endereco = new Endereco();
		endereco.setUf(EnumUnidadeFederativa.RN);
		
		//TODO Código em teste
		Localidade localidade = new Localidade();
		localidade.setNome("Natal");
		endereco.setLocalidade(localidade);
		
		CEP cep = new CEP();
		cep.setCep("59064-120");
		endereco.setCep(cep);
		
		endereco.setBairro("Grande Centro");
		
		Logradouro logradouro = new Logradouro();
		logradouro.setNome("Rua Poeta das Princesas");
		endereco.setLogradouro(logradouro);
		
		endereco.setNumero("1");
		
		sacado.addEndereco(endereco);
		
		Pessoa cedente = new Pessoa();
		
		cedente.setNome("Empresa Lucrativa para Todo Sempre Ilimitada");
		cedente.setCadastroDePessoa(ACadastroDePessoa.getInstance("00.000.208/0001-00"));

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBanco.BANCO_BRADESCO);
		
		Agencia agencia = new Agencia();
		agencia.setCodigoDaAgencia(1234);
		agencia.setDigitoDaAgencia("67");
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCodigoDaCarteira(5);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		numeroDaConta.setDigitoDaConta("12");
		contaBancaria.setNumeroDaConta(numeroDaConta);

		cedente.addContaBancaria(contaBancaria);
		
		Pessoa sacadorAvalista = new Pessoa();
		sacadorAvalista.setNome("Banco do Brasil");
		sacadorAvalista.setCadastroDePessoa(ACadastroDePessoa.getInstance("00.000.000/0001-91"));
		
		Endereco endereco2 = new Endereco();
		endereco2.setUf(EnumUnidadeFederativa.DF);
		
		Localidade localidade2 = new Localidade();
		localidade2.setNome("Brasília");
		endereco2.setLocalidade(localidade2);
	
		CEP cep2 = new CEP();
		cep2.setCep("00000-000");
		endereco2.setCep(cep2);

		endereco2.setBairro("Grande Centro");
		
		Logradouro logradouro2 = new Logradouro();
		logradouro2.setNome("Rua Principal Para Sempre");
		endereco2.setLogradouro(logradouro2);

		endereco2.setNumero("001");
		
		sacadorAvalista.addEndereco(endereco2);
		
		//Fim Código em teste

		titulo = Titulo.getInstance(sacado, cedente,sacadorAvalista);
		titulo.setNumeroDoDocumento("123456789");
		titulo.setNossoNumero("12345678901");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setE_Moeda(EnumMoeda.REAL);
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoDocumento(DATA_DO_DOCUMENTO);
		titulo.setDataDoVencimento(VENCIMENTO);
		titulo.setE_Moeda(EnumMoeda.REAL);
		titulo.setTipoDeDocumento(EnumTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(E_Aceite.A);

		boleto = Boleto.getInstance(titulo, new ICampoLivre(){

			@Override
			public void read(String str) {
				System.out.println("Não Fazer Nada.");
				
			}

			@Override
			public String write() {
				return "6666666666666666666666666";
			}
			
		});
		
		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
		
		boleto.setInsturcaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado é injusto e esperamos seu pagamento assim mesmo.");
		
		boleto.setInstrucao1("PARA PAGAMENTO 1 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao2("PARA PAGAMENTO 2 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao3("PARA PAGAMENTO 3 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao4("PARA PAGAMENTO 4 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao5("PARA PAGAMENTO 5 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao6("PARA PAGAMENTO 6 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
		

		BoletoPDF b = BoletoPDF.getInstance(boleto);
		b.getFile("testeBoletoPDF.pdf");
	}
	
	/**
	 * Exibe os valores de instância.
	 * 
	 * @see br.com.nordestefomento.jrimum.ACurbitaObject#toString()
	 */
	@Override
	public String toString() {
		
		ToStringBuilder tsb = new ToStringBuilder(this);
		
		tsb.append(baseFont);
		tsb.append(baseFont);
		tsb.append(baseFontBold);
		tsb.append(imgBoleto);
		tsb.append(imgLogoBanco);
		tsb.append(imgBarCode);
		
		return tsb.toString(); 
	}
	
	/**
	 * 
	 * Um Ponto no plano, classe com propósito de transferência de dados entre os
	 * métodos <code>set</code> do boleto.
	 * 
	 * 
	 * @author Gabriel Guimarães
	 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
	 * @author Misael Barreto
	 * @author Rômulo Augusto
	 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento
	 *         Mercantil</a>
	 * 
	 * @since JMatryx 1.0
	 * 
	 * @version 1.0
	 */
    private class Ponto{
    	/**
    	 * Coordenada x;
    	 */
    	float x = 0;
    	
    	/**
    	 * Coordenada y;
    	 */
    	float y = 0;
    }
	
}
