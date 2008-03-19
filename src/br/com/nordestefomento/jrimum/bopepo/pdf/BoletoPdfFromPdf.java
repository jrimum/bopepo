package br.com.nordestefomento.jrimum.bopepo.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.nordestefomento.jrimum.ACurbitaObject;
import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.Banco;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo.E_Aceite;
import br.com.nordestefomento.jrimum.domkee.type.CEP;
import br.com.nordestefomento.jrimum.domkee.type.CNPJ;
import br.com.nordestefomento.jrimum.domkee.type.Endereco;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;
import br.com.nordestefomento.jrimum.domkee.type.EnumTitulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumUnidadeFederativa;
import br.com.nordestefomento.jrimum.domkee.type.Localidade;
import br.com.nordestefomento.jrimum.domkee.type.Logradouro;
import br.com.nordestefomento.jrimum.utilix.Operator4Date;
import br.com.nordestefomento.jrimum.utilix.Operator4Monetary;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class BoletoPdfFromPdf extends ACurbitaObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String SEPERADOR = "-";
	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	private ByteArrayOutputStream outputStream;
	private Boleto boleto;
	
	private  BoletoPdfFromPdf (Boleto boleto) {
		super();
		this.boleto = boleto;
	}
	
	
	public static BoletoPdfFromPdf getInstance(Boleto boleto) throws IOException, DocumentException {
		
		BoletoPdfFromPdf boletoPdf = new BoletoPdfFromPdf(boleto);

		boletoPdf.inicializar();
		boletoPdf.preencher();
		boletoPdf.finalizar();
		
		return boletoPdf;
	}
	
	
	private void inicializar() throws IOException, DocumentException {
		//reader = new PdfReader(this.getClass().getResource("/resource/pdf/BoletoTemplate.pdf"));
		reader = new PdfReader(boleto.getTemplate().getAbsolutePath());
		outputStream = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, outputStream);
		form = stamper.getAcroFields();
	}
	
	private void finalizar() throws DocumentException, IOException {
		stamper.close();		
	}
	
	private void preencher() throws MalformedURLException, IOException, DocumentException {
		setLogoBanco();
		setCodigoBanco();
		setLinhaDigitavel();
		setCedente();
		setAgenciaCondigoCedente();
		setEspecie();
		setQuantidade();
		setNossoNumero();
		setNumeroDocumento();
		setCpfCnpjCedente();
		setDataVencimeto();
		setValorDocumento();
		setDescontoAbatimento();
		setOutraDeducao();
		setMoraMulta();
		setOutroAcrescimo();
		setInstrucaoAoSacado();
		setInstrucaoAoCaixa();
		setSacado();
		setLocalPagamento();
		setDataDocumento();
		setEspecieDoc();
		setAceite();
		setDataProcessamento();
		setSacadorAvalista();
		setCodigoBarra();
		setCamposExtra();
	}




	private void setCamposExtra() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		Map <String, String> listaCamposExtra = this.boleto.getListaCamposExtra();
		
		if (listaCamposExtra != null) {
			Set<String> listaCampo = listaCamposExtra.keySet();
			for(String campo : listaCampo) {
				form.setField(campo, listaCamposExtra.get(campo));
			}		
		}
	}


	private void setCodigoBarra() throws DocumentException {
		
		// Montando o código de barras.
		BarcodeInter25 barCode = new BarcodeInter25();
		barCode.setCode(boleto.getCodigoDeBarra().write());
		
		barCode.setExtended(true);
		barCode.setBarHeight(40);
		barCode.setFont(null);
		barCode.setN(3);
		
		// Variáveis necessárias para saber a posicao e as medidas do campo texto que será
		// usado como base para a adição da imagem com o código de barras.
		float[] posicaoField = null;
		Rectangle medidaField  = null;
		PdfContentByte cb = null;
		
		// FICHA DE COMPENSAÇÃO
		posicaoField = form.getFieldPositions("txtFcCodigoBarra");
		medidaField = new Rectangle(posicaoField[1], posicaoField[2], posicaoField[3], posicaoField[4]);		
		cb = stamper.getOverContent((int)posicaoField[0]);
		Image imgBarCode = barCode.createImageWithBarcode(cb, null,null);
		
		//imgBarCode.scaleToFit(medidaField.width(), medidaField.height());
		imgBarCode.scaleAbsolute(medidaField.width(), medidaField.height());
		
		//imgBarCode.setAbsolutePosition(posicaoField[1], posicaoField[2]);
		
		imgBarCode.setAbsolutePosition(
										posicaoField[1] + (medidaField.width() - imgBarCode.scaledWidth()) / 2,
										posicaoField[2] + (medidaField.height() - imgBarCode.scaledHeight()) / 2
									  );
		
		cb.addImage(imgBarCode);		
	}


	private void setDataProcessamento() throws IOException, DocumentException {
		// TODO Auto-generated method stub		
		form.setField("txtFcDataProcessamento", Operator4Date.fmt_dd_MM_yyyy.format(boleto.getDataDeProcessamento()));
	}


	private void setAceite() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcAceite", boleto.getTitulo().getAceite().toString());
	}


	private void setEspecieDoc() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcEspecieDocumento", boleto.getTitulo().getTipoDeDocumento().getSigla());		
	}


	private void setDataDocumento() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcDataDocumento", Operator4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoDocumento()));			
		
	}


	private void setLocalPagamento() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcLocalPagamento", (boleto.getLocalPagamento()));
	}


	private void setSacado() throws IOException, DocumentException {

		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("");
		Pessoa sacado = boleto.getTitulo().getSacado();
		

		if(isNotNull(sacado.getNome())){
			sb.append(sacado.getNome());
		}
		if(isNotNull(sacado.getCadastroDePessoa())){
			sb.append(", ");			
			if(sacado.getCadastroDePessoa().isFisica())
				sb.append("Cpf: ");
			else if(sacado.getCadastroDePessoa().isJuridica())
				sb.append("Cnpj: ");
			
			sb.append(sacado.getCadastroDePessoa().getCodigoFormatado());
		}
		form.setField("txtRsSacado", sb.toString());
		form.setField("txtFcSacadoL1", sb.toString());
		
		
		
		//TODO Código em teste
		sb.delete(0, sb.length());
		Endereco endereco = sacado.getEnderecos().iterator().next();

		if(isNotNull(endereco)){
			if(isNotNull(endereco.getBairro()))
				sb.append(endereco.getBairro());	
			if(isNotNull(endereco.getLocalidade().getNome()))
				sb.append(SEPERADOR+endereco.getLocalidade().getNome());
			if(isNotNull(endereco.getUf()))
				sb.append(" / "+endereco.getUf().toString());
			
			form.setField("txtFcSacadoL2", sb.toString());
			
			
			sb.delete(0,sb.length());
			if(isNotNull(endereco.getLogradouro()))
				sb.append(endereco.getLogradouro().getNome());
			
			if(isNotNull(endereco.getNumero()))
				sb.append(", n°: "+ endereco.getNumero());
			
			if(isNotNull(endereco.getCep()))
				sb.append(SEPERADOR + "Cep: " + endereco.getCep().getCep());
			
			form.setField("txtFcSacadoL3", sb.toString());
		}
	}

	private void setSacadorAvalista() throws IOException, DocumentException {
		Pessoa sacadorAvalista = boleto.getTitulo().getSacadorAvalista();
	
		if (sacadorAvalista != null) {
			StringBuilder sb = new StringBuilder("");	

			if(isNotNull(sacadorAvalista.getNome())){
				sb.append(sacadorAvalista.getNome());
			}
			if(isNotNull(sacadorAvalista.getCadastroDePessoa())){
				sb.append(", ");			
				if(sacadorAvalista.getCadastroDePessoa().isFisica())
					sb.append("Cpf: ");
				else if(sacadorAvalista.getCadastroDePessoa().isJuridica())
					sb.append("Cnpj: ");
				
				sb.append(sacadorAvalista.getCadastroDePessoa().getCodigoFormatado());
			}
			form.setField("txtFcSacadorAvalistaL1", sb.toString());
			
			
			
			//TODO Código em teste
			sb.delete(0, sb.length());
			Endereco endereco = sacadorAvalista.getEnderecos().iterator().next();

			if(isNotNull(endereco)){
				if(isNotNull(endereco.getBairro()))
					sb.append(endereco.getBairro());	
				if(isNotNull(endereco.getLocalidade().getNome()))
					sb.append(SEPERADOR+endereco.getLocalidade().getNome());
				if(isNotNull(endereco.getUf()))
					sb.append(" / "+endereco.getUf().toString());
				
				form.setField("txtFcSacadorAvalistaL2", sb.toString());
				
				
				sb.delete(0,sb.length());
				if(isNotNull(endereco.getLogradouro()))
					sb.append(endereco.getLogradouro().getNome());
				
				if(isNotNull(endereco.getNumero()))
					sb.append(", n°: "+ endereco.getNumero());
				
				if(isNotNull(endereco.getCep()))
					sb.append(SEPERADOR + "Cep: " + endereco.getCep().getCep());
				
				form.setField("txtFcSacadorAvalistaL3", sb.toString());
			}
		}
	}

	
	private void setInstrucaoAoCaixa() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcInstrucaoAoCaixa1", boleto.getInstrucao1());		
		form.setField("txtFcInstrucaoAoCaixa2", boleto.getInstrucao2());		
		form.setField("txtFcInstrucaoAoCaixa3", boleto.getInstrucao3());		
		form.setField("txtFcInstrucaoAoCaixa4", boleto.getInstrucao4());		
		form.setField("txtFcInstrucaoAoCaixa5", boleto.getInstrucao5());		
		form.setField("txtFcInstrucaoAoCaixa6", boleto.getInstrucao6());		
		form.setField("txtFcInstrucaoAoCaixa7", boleto.getInstrucao7());		
		form.setField("txtFcInstrucaoAoCaixa8", boleto.getInstrucao8());		
	}


	private void setMoraMulta() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsMoraMulta", "");
		form.setField("txtFcMoraMulta", "");
	}


	private void setInstrucaoAoSacado() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsInstrucaoAoSacado", boleto.getInsturcaoAoSacado());			
	}


	private void setOutroAcrescimo() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsOutroAcrescimo", "");
		form.setField("txtFcOutroAcrescimo", "");				
	}


	private void setOutraDeducao() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsOutraDeducao", "");
		form.setField("txtFcOutraDeducao", "");
	
	}


	private void setDescontoAbatimento() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsDescontoAbatimento", "");
		form.setField("txtFcDescontoAbatimento", "");
				
	}


	private void setValorDocumento() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsValorDocumento", Operator4Monetary.fmt_Real.format(boleto.getTitulo().getValor()));		
		form.setField("txtFcValorDocumento", Operator4Monetary.fmt_Real.format(boleto.getTitulo().getValor()));		
	}


	private void setDataVencimeto() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsDataVencimento", Operator4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoVencimento()));		
		form.setField("txtFcDataVencimento", Operator4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoVencimento()));		
	}


	private void setCpfCnpjCedente() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsCpfCnpj", boleto.getTitulo().getCedente().getCadastroDePessoa().getCodigoFormatado());				
	}


	private void setNumeroDocumento() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());	
		form.setField("txtFcNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());		
	}
	
	
	private void setCedente() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsCedente", boleto.getTitulo().getCedente().getNome());
		form.setField("txtFcCedente", boleto.getTitulo().getCedente().getNome());		
	}


	private void setQuantidade() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsQuantidade", "");
		form.setField("txtFcQuantidade", "");				
	}


	private void setEspecie() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsEspecie", boleto.getTitulo().getE_Moeda().toString());
		form.setField("txtFcEspecie", boleto.getTitulo().getE_Moeda().toString());				
	}


	private void setLinhaDigitavel() throws DocumentException, IOException {
		// TODO Auto-generated method stub
		form.setField("txtRsLinhaDigitavel", boleto.getLinhaDigitavel().write());
		form.setField("txtFcLinhaDigitavel", boleto.getLinhaDigitavel().write());		
	}

	
	private void setLogoBanco() throws MalformedURLException, IOException, DocumentException {
		// Através da conta bancária será descoberto a imagem que representa o banco, com base
		// no código do banco.
		ContaBancaria conta = boleto.getTitulo().getCedente().getContasBancarias().iterator().next();	
		
		// Verificando se há uma imagem e logo informada no objeto banco.
		Image imgLogoBanco = conta.getBanco().getImgLogo(); 
		
		// Caso não exista, com base no código do banco será feita
		// uma busca pela imagem no resource.img.
		if ( imgLogoBanco == null) {			
			URL url = this.getClass().getResource("/resource/img/" + conta.getBanco().getCodigoDeCompensacao()+".png"); 
			if (url != null)
				imgLogoBanco = Image.getInstance(url);
			
			
			if (imgLogoBanco != null) {
				conta.getBanco().setImgLogo(imgLogoBanco);
				
				// Se o banco em questão não é suportado nativamente pelo componente,
				// então um alerta será exibido.
				if (!(conta.getBanco() instanceof EnumBanco)) {
					if(log.isDebugEnabled())
						log.debug("Banco sem imagem da logo informada. Com base no código do banco, uma imagem foi encontrada no resource e esta sendo utilizada.");
				}
				
				// Variáveis necessárias para saber a posicao e as medidas do campo texto que será
				// usado como base para a adição da imagem com a logo do banco.
				float[] posicaoField = null;
				Rectangle medidaField  = null;
				PdfContentByte cb = null;
				
				// RECIBO DO SACADO
				posicaoField = form.getFieldPositions("txtRsLogoBanco");
				medidaField = new Rectangle(posicaoField[1], posicaoField[2], posicaoField[3], posicaoField[4]);		
				
				// Ajustando o tamanho da imagem de acordo com o tamanho do campo.
				//img.scaleToFit(imagemMedida.width(), imagemMedida.height());
				imgLogoBanco.scaleAbsolute(medidaField.width(), medidaField.height());
				
				// A rotina abaixo tem por objetivo deixar a imagem posicionada no centro
				// do field, tanto na perspectiva horizontal como na vertical. 
				// Caso não se queira mais posicionar a imagem no centro do field, basta
				// efetuar a chamada a seguir:
				// "img.setAbsolutePosition	(posicaoField[1], posicaoField[2]);"
				imgLogoBanco.setAbsolutePosition(
													posicaoField[1] + (medidaField.width() - imgLogoBanco.scaledWidth()) / 2,
													posicaoField[2] + (medidaField.height() - imgLogoBanco.scaledHeight()) / 2
												);
				
				//cb = stamper.getUnderContent((int)posicaoField[0]);
				cb = stamper.getOverContent((int)posicaoField[0]);
				cb.addImage(imgLogoBanco);
				
				
				// FICHA DE COMPENSAÇÃO
				posicaoField = form.getFieldPositions("txtFcLogoBanco");
				medidaField = new Rectangle(posicaoField[1], posicaoField[2], posicaoField[3], posicaoField[4]);		
				imgLogoBanco.scaleAbsolute(medidaField.width(), medidaField.height());
				imgLogoBanco.setAbsolutePosition(
													posicaoField[1] + (medidaField.width() - imgLogoBanco.scaledWidth()) / 2,
													posicaoField[2] + (medidaField.height() - imgLogoBanco.scaledHeight()) / 2
												);
				cb = stamper.getOverContent((int)posicaoField[0]);
				cb.addImage(imgLogoBanco);				
				
				
			} 
			else {
				// Caso nenhuma imagem seja encontrada, um alerta é exibido.
				if (log.isDebugEnabled())
					log.debug("Banco sem imagem definida. No caso será utilizada o nome da instiuição ao invés da logo.");
				
				form.setField("txtRsLogoBanco", conta.getBanco().getInstituicao());
				form.setField("txtFcLogoBanco", conta.getBanco().getInstituicao());			
			}	
		}
	}
	
	
	private void setCodigoBanco() throws IOException, DocumentException {
		ContaBancaria conta = boleto.getTitulo().getCedente().getContasBancarias().iterator().next();
		form.setField("txtRsCodBanco", conta.getBanco().getCodigoDeCompensacao());
		form.setField("txtFcCodBanco", conta.getBanco().getCodigoDeCompensacao());
	}
	
	private void setAgenciaCondigoCedente() throws IOException, DocumentException {
		StringBuilder sb = new StringBuilder("");
		ContaBancaria conta = boleto.getTitulo().getCedente().getContasBancarias().iterator().next();
		
		if(isNotNull(conta.getAgencia().getCodigoDaAgencia()))
			sb.append(conta.getAgencia().getCodigoDaAgencia());
		
		if(isNotNull(conta.getAgencia().getDigitoDaAgencia()) && StringUtils.isNotBlank(conta.getAgencia().getDigitoDaAgencia())){
			
			sb.append(BoletoPdfFromPdf.SEPERADOR);
			sb.append(conta.getAgencia().getDigitoDaAgencia());
		}
		
		if(isNotNull(conta.getNumeroDaConta().getCodigoDaConta())){
			
			sb.append(" / ");
			
			sb.append(conta.getNumeroDaConta().getCodigoDaConta());
			
			if(isNotNull(conta.getNumeroDaConta().getDigitoDaConta())){
				
				sb.append(BoletoPdfFromPdf.SEPERADOR);
				sb.append(conta.getNumeroDaConta().getDigitoDaConta());
			}
		}

		form.setField("txtRsAgenciaCodigoCedente", sb.toString());
		form.setField("txtFcAgenciaCodigoCedente", sb.toString());			
	}
	
	
	private void setNossoNumero() throws IOException, DocumentException {
		StringBuilder sb = new StringBuilder("");
		
		if(isNotNull(boleto.getTitulo().getNossoNumero()))
			sb.append(boleto.getTitulo().getNossoNumero());
		if(isNotNull(boleto.getTitulo().getDigitoDoNossoNumero()))
			sb.append(BoletoPdfFromPdf.SEPERADOR + boleto.getTitulo().getDigitoDoNossoNumero());
		
		form.setField("txtRsNossoNumero", sb.toString());
		form.setField("txtFcNossoNumero", sb.toString());				
	}
	
	
	
	public File getFile(String pathName)throws IllegalArgumentException, IOException{
		File file = null;
		
		if(StringUtils.isNotBlank(pathName)){
			file = new File(pathName);
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(outputStream.toByteArray());
			
			fout.flush();
			fout.close();
			
		}else{
			IllegalArgumentException e = new IllegalArgumentException("Path File [ "+pathName+" ] não permitido !");
			log.error("Valor Não Permitido!",e);
			throw e;
		}
		
		return file;
	}
	
	public ByteArrayOutputStream getStream() throws IOException{
		
		ByteArrayOutputStream outPuted = null;
		
		outPuted = new ByteArrayOutputStream();
		
		outPuted.write(outputStream.toByteArray());
		
		return outPuted;
	}
	
	public byte[] getBytes(){
		return outputStream.toByteArray();
	}
	
	/**
	 * Exibe os valores de instância.
	 * 
	 * @see br.com.nordestefomento.jrimum.ACurbitaObject#toString()
	 */
	@Override
	public String toString() {
		
		ToStringBuilder tsb = new ToStringBuilder(this);
		
		tsb.append(boleto);
		
		return tsb.toString(); 
	}
	
	
	public static void main (String[] args) throws DocumentException, IllegalArgumentException, IOException, NotSuporttedBancoException, NotSuporttedCampoLivreException {
		Titulo titulo;

		final Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3)
				.getTime();
		
		final Date DATA_DO_DOCUMENTO =  new GregorianCalendar(2000, Calendar.APRIL, 14)
		.getTime();

		Boleto boleto;

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
		
		ContaBancaria contaBancaria = new ContaBancaria(EnumBanco.BANCO_DO_BRASIL);
		//ContaBancaria contaBancaria = new ContaBancaria(  new Banco("035", "Misael Bank", new CNPJ("00.000.208/0001-00"), "Seg" )  );
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

		boleto = Boleto.getInstance(titulo);
		
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
		
		//boleto.setTemplate("TemplateMisael.pdf");
		//boleto.getListaCamposExtra().put("txtNew", "Puta que pariu...");
		
		boleto.getAsPDF("testeBoletoPdfFromPdf.pdf");
		//BoletoPdfFromPdf b = BoletoPdfFromPdf.getInstance(boleto);
		//b.getFile("testeBoletoPdfFromPdf.pdf");
	}
	
}
