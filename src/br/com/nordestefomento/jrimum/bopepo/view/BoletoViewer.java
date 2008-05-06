package br.com.nordestefomento.jrimum.bopepo.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import br.com.nordestefomento.jrimum.ACurbitaObject;
import br.com.nordestefomento.jrimum.JRimumException;
import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.Carteira;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo.EnumAceite;
import br.com.nordestefomento.jrimum.domkee.ientity.IBanco;
import br.com.nordestefomento.jrimum.domkee.type.CEP;
import br.com.nordestefomento.jrimum.domkee.type.Endereco;
import br.com.nordestefomento.jrimum.domkee.type.EnumTitulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumUnidadeFederativa;
import br.com.nordestefomento.jrimum.domkee.type.Localidade;
import br.com.nordestefomento.jrimum.domkee.type.Logradouro;

import com.lowagie.text.DocumentException;

public class BoletoViewer extends ACurbitaObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boleto boleto;
	
	ViewerPDF viewerPDF;
	
	/**
	 * @param boleto
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	private BoletoViewer(Boleto boleto) throws JRimumException {
		super();
		
		this.boleto = boleto;
		
		try{
		
			this.viewerPDF = ViewerPDF.getInstance(this.boleto);
			
		}catch (Exception e) {
			throw new JRimumException(e);
		}
		
	}
	
	public File getTemplate() {
		
		return viewerPDF.getTemplate();
	}

	public void setTemplate(File template) {
		
		viewerPDF.setTemplate(template);
	}
	
	public void setTemplate(String pathname) {
		
		viewerPDF.setTemplate(pathname);
	}

	public File getAsPDF(String pathName)throws IllegalArgumentException, IOException, DocumentException{
		
		if(log.isDebugEnabled())
			log.debug("documento instance : " + viewerPDF);
		
		return viewerPDF.getFile(pathName);
	}
	
	public ByteArrayOutputStream getAsStream() throws IOException, DocumentException{
		
		if(log.isDebugEnabled())
			log.debug("documento instance : "+viewerPDF);
		
		return viewerPDF.getStream();
		
	}
	
	public byte[] getAsByteArray() throws IOException, DocumentException{
		
		if(log.isDebugEnabled())
			log.debug("documento instance : "+viewerPDF);
		
		return viewerPDF.getBytes();
	}

	/**
	 * @return the boleto
	 */
	public Boleto getBoleto() {
		return boleto;
	}

	/**
	 * @param boleto the boleto to set
	 */
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}
	
	public static void main (String[] args) throws DocumentException, IllegalArgumentException, IOException, NotSuporttedBancoException, NotSuporttedCampoLivreException {
		
		Pessoa sacado = new Pessoa("Fulano da Silva Sauro Super Feliz com a Compra", "222.222.222-22");
		
		Endereco endereco = new Endereco();
		endereco.setUf(EnumUnidadeFederativa.RN);
		endereco.setLocalidade(new Localidade("Natal"));
		endereco.setCep(new CEP("59064-120"));
		endereco.setBairro("Grande Centro");
		endereco.setLogradouro(new Logradouro("Rua Poeta das Princesas"));
		endereco.setNumero("1");
		
		sacado.addEndereco(endereco);
		
		Pessoa cedente = new Pessoa("Empresa Lucrativa S.A para Todo Sempre Ilimitada", "00.000.208/0001-00");
		
		IBanco banco = EnumBancos.BANCO_DO_BRASIL.newInstance();
		ContaBancaria contaBancaria = new ContaBancaria(banco);
		
		contaBancaria.setAgencia(new Agencia(1234, "67"));
		contaBancaria.setCarteira(new Carteira(5));
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

		Titulo titulo = Titulo.getInstance(sacado, cedente, sacadorAvalista);
		titulo.setNumeroDoDocumento("123456789");
		titulo.setNossoNumero("1234567890");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(new Date());
		titulo.setTipoDeDocumento(EnumTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(EnumAceite.A);

		Boleto boleto = Boleto.getInstance(titulo);
		
		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
		
		boleto.setInsturcaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado não é o esperado, aproveite o DESCONTÃO!");
		
		boleto.setInstrucao1("PARA PAGAMENTO 1 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao2("PARA PAGAMENTO 2 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao3("PARA PAGAMENTO 3 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao4("PARA PAGAMENTO 4 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao5("PARA PAGAMENTO 5 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao6("PARA PAGAMENTO 6 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR DE: R$ YY,YY");
		boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
		
		BoletoViewer bViewer = new BoletoViewer(boleto);
		bViewer.getAsPDF("testeBoletoPdfFromPdf.pdf");
		
		 java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		 
		try {
			desktop.open(new File("testeBoletoPdfFromPdf.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
