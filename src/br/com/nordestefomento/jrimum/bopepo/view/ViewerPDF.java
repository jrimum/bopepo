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
 * Created at: 30/03/2008 - 18:05:16
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
 * Criado em: 30/03/2008 - 18:05:16
 * 
 */

package br.com.nordestefomento.jrimum.bopepo.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.nordestefomento.jrimum.ACurbitaObject;
import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.type.Endereco;
import br.com.nordestefomento.jrimum.utilix.RectanglePDF;
import br.com.nordestefomento.jrimum.utilix.Util4Date;
import br.com.nordestefomento.jrimum.utilix.Util4File;
import br.com.nordestefomento.jrimum.utilix.Util4Monetary;
import br.com.nordestefomento.jrimum.utilix.Util4PDF;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

class ViewerPDF extends ACurbitaObject {

	//TODO Testes no teste unitário: TestViewPDF
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static URL TEMPLATE_PADRAO_COM_SACADOR_AVALISTA = Object.class.getResource("/resource/pdf/BoletoTemplateComSacadorAvalista.pdf");
	private static URL TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA = Object.class.getResource("/resource/pdf/BoletoTemplateSemSacadorAvalista.pdf");
	
	private static final String SEPERADOR = "-";
	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	private ByteArrayOutputStream outputStream;
	
	private Boleto boleto;
	
	private File template;
	
	private  ViewerPDF (Boleto boleto) {
		super();
		this.boleto = boleto;
	}
	
	protected File getTemplate() {
		return template;
	}

	protected void setTemplate(File template) {
		this.template = template;
	}
	
	protected void setTemplate(String pathname) {
		setTemplate(new File(pathname));
	}
	
	private URL getTemplateFromResource() {
		
		URL templateFromResource = null;
		
		if (boleto.getTitulo().hasSacadorAvalista()) {
			templateFromResource = TEMPLATE_PADRAO_COM_SACADOR_AVALISTA;
		}
		else {
			templateFromResource = TEMPLATE_PADRAO_SEM_SACADOR_AVALISTA;
		}
		
		return templateFromResource;
	}
	
	/**
	 * <p>
	 * Verifica se o template que será utilizado virá do resource ou é externo, 
	 * ou seja, se o usuário definiu ou não um template.
	 * </p>
	 * 
	 * @return true caso o template que pode ser definido pelo usuário for null; 
	 * false caso o usuário tenha definido um template.
	 * 
	 * @since 
	 */
	private boolean isTemplateFromResource() {
		
		return getTemplate() == null;
	}
	
	protected static ViewerPDF getInstance(Boleto boleto) {
		ViewerPDF boletoPdf = new ViewerPDF(boleto);
		return boletoPdf;
	}
	
	private void processarPdf() throws IOException, DocumentException {
		inicializar();
		preencher();
		finalizar();
	}
	
	/*
	public static File groupInOnePDF(String pathName, List<Boleto> boletos)
			throws IOException, DocumentException {

		File arq = null;

		if (!isNull(pathName, "pathName") && !isNull(boletos, "boletos")) {

			List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());
			
			for (Boleto bop : boletos){
				boletosEmBytes.add(bop.getAsByteArray());
			}

			arq = Util4File.bytes2File(pathName + ".pdf", Util4PDF
					.mergeFiles(boletosEmBytes));
		}

		return arq;

	}
	*/
	private void inicializar() throws IOException, DocumentException {

		if(isTemplateFromResource()) {
			reader = new PdfReader(getTemplateFromResource());
		}
		else {
			reader = new PdfReader(getTemplate().getAbsolutePath());
		}

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
		barCode.setCode(boleto.getCodigoDeBarras().write());
		
		barCode.setExtended(true);
		barCode.setBarHeight(40);
		barCode.setFont(null);
		barCode.setN(3);
		
		PdfContentByte cb = null;
		
		// FICHA DE COMPENSAÇÃO
		RectanglePDF field = new RectanglePDF(form.getFieldPositions("txtFcCodigoBarra"));		
		cb = stamper.getOverContent(field.getPage());
		Image imgBarCode = barCode.createImageWithBarcode(cb, null,null);
		
		Util4PDF.changeField2Image(stamper, field, imgBarCode);
	}


	private void setDataProcessamento() throws IOException, DocumentException {
		// TODO Auto-generated method stub		
		form.setField("txtFcDataProcessamento", Util4Date.fmt_dd_MM_yyyy.format(boleto.getDataDeProcessamento()));
	}


	private void setAceite() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		if (  isNotNull(boleto.getTitulo().getAceite())  )
			form.setField("txtFcAceite", boleto.getTitulo().getAceite().toString());
	}


	private void setEspecieDoc() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcEspecieDocumento", boleto.getTitulo().getTipoDeDocumento().getSigla());		
	}


	private void setDataDocumento() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcDataDocumento", Util4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoDocumento()));			
		
	}


	private void setLocalPagamento() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtFcLocalPagamento", (boleto.getLocalPagamento()));
	}


	private void setSacado() throws IOException, DocumentException {

		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		Pessoa sacado = boleto.getTitulo().getSacado();
		

		if(isNotNull(sacado.getNome())){
			sb.append(sacado.getNome());
		}
		if(isNotNull(sacado.getACpfCnpj())){
			sb.append(", ");			
			if(sacado.getACpfCnpj().isFisica())
				sb.append("Cpf: ");
			else if(sacado.getACpfCnpj().isJuridica())
				sb.append("Cnpj: ");
			
			sb.append(sacado.getACpfCnpj().getCodigoFormatado());
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
			if(isNotNull(sacadorAvalista.getACpfCnpj())){
				sb.append(", ");			
				if(sacadorAvalista.getACpfCnpj().isFisica())
					sb.append("Cpf: ");
				else if(sacadorAvalista.getACpfCnpj().isJuridica())
					sb.append("Cnpj: ");
				
				sb.append(sacadorAvalista.getACpfCnpj().getCodigoFormatado());
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
		form.setField("txtRsInstrucaoAoSacado", boleto.getInstrucaoAoSacado());			
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
		form.setField("txtRsValorDocumento", Util4Monetary.fmt_Real.format(boleto.getTitulo().getValor()));		
		form.setField("txtFcValorDocumento", Util4Monetary.fmt_Real.format(boleto.getTitulo().getValor()));		
	}


	private void setDataVencimeto() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsDataVencimento", Util4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoVencimento()));		
		form.setField("txtFcDataVencimento", Util4Date.fmt_dd_MM_yyyy.format(boleto.getTitulo().getDataDoVencimento()));		
	}


	private void setCpfCnpjCedente() throws IOException, DocumentException {
		// TODO Auto-generated method stub
		form.setField("txtRsCpfCnpj", boleto.getTitulo().getCedente().getACpfCnpj().getCodigoFormatado());				
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
		form.setField("txtRsEspecie", boleto.getTitulo().getEnumMoeda().toString());
		form.setField("txtFcEspecie", boleto.getTitulo().getEnumMoeda().toString());				
	}


	private void setLinhaDigitavel() throws DocumentException, IOException {
		// TODO Auto-generated method stub
		form.setField("txtRsLinhaDigitavel", boleto.getLinhaDigitavel().write());
		form.setField("txtFcLinhaDigitavel", boleto.getLinhaDigitavel().write());		
	}

	
	private void setLogoBanco() throws MalformedURLException, IOException, DocumentException {
		// Através da conta bancária será descoberto a imagem que representa o banco, com base
		// no código do banco.
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();	
		Image imgLogoBanco = null;
		
		if (isNotNull(conta.getBanco().getImgLogo())) {
			
			imgLogoBanco = Image.getInstance(conta.getBanco().getImgLogo(), null);
			
			setImageLogo(imgLogoBanco);
		
		}else{

			if(EnumBancos.isSuportado(conta.getBanco().getCodigoDeCompensacao())){
				
				URL url = this.getClass().getResource("/resource/img/" + conta.getBanco().getCodigoDeCompensacao() + ".png");
				
				if (url != null)
					imgLogoBanco = Image.getInstance(url);
				
				if (imgLogoBanco != null){
					
					// Esta imagem gerada aqui é do tipo java.awt.Image
					conta.getBanco().setImgLogo(ImageIO.read(url));
				}
				
				// Se o banco em questão é suportado nativamente pelo componente,
				// então um alerta será exibido.
					if(log.isDebugEnabled())
						log.debug("Banco sem imagem da logo informada. " +
								"Com base no código do banco, uma imagem foi " +
								"encontrada no resource e esta sendo utilizada.");

					setImageLogo(imgLogoBanco);			
				
			}else{
			
				// Sem imagem, um alerta é exibido.
				log.warn("Banco sem imagem definida. O nome da instiuição será usado como logo.");
				
				form.setField("txtRsLogoBanco", conta.getBanco().getNome());
				form.setField("txtFcLogoBanco", conta.getBanco().getNome());			

			}				
		}
	}


	
	/**
	 * <p>
	 * Coloca a logo do passada na ficha de compensação do boleto e no recibo do sacado.
	 * </p>
	 * 
	 * @param imgLogoBanco
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	private void setImageLogo(Image imgLogoBanco) throws DocumentException {
		
		// RECIBO DO SACADO
		Util4PDF.changeField2Image(stamper, form.getFieldPositions("txtRsLogoBanco"), imgLogoBanco);		
		
		// FICHA DE COMPENSAÇÃO
		Util4PDF.changeField2Image(stamper, form.getFieldPositions("txtFcLogoBanco"), imgLogoBanco);
	}
	
	
	private void setCodigoBanco() throws IOException, DocumentException {
		
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		form.setField("txtRsCodBanco", conta.getBanco().getCodigoDeCompensacao());
		form.setField("txtFcCodBanco", conta.getBanco().getCodigoDeCompensacao());
	}
	
	private void setAgenciaCondigoCedente() throws IOException, DocumentException {
		
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		
		if(isNotNull(conta.getAgencia().getCodigoDaAgencia()))
			sb.append(conta.getAgencia().getCodigoDaAgencia());
		
		if(isNotNull(conta.getAgencia().getDigitoDaAgencia()) && StringUtils.isNotBlank(conta.getAgencia().getDigitoDaAgencia())){
			
			sb.append(ViewerPDF.SEPERADOR);
			sb.append(conta.getAgencia().getDigitoDaAgencia());
		}
		
		if(isNotNull(conta.getNumeroDaConta().getCodigoDaConta())){
			
			sb.append(" / ");
			
			sb.append(conta.getNumeroDaConta().getCodigoDaConta());
			
			if(isNotNull(conta.getNumeroDaConta().getDigitoDaConta())){
				
				sb.append(ViewerPDF.SEPERADOR);
				sb.append(conta.getNumeroDaConta().getDigitoDaConta());
			}
		}

		form.setField("txtRsAgenciaCodigoCedente", sb.toString());
		form.setField("txtFcAgenciaCodigoCedente", sb.toString());			
	}
	
	
	private void setNossoNumero() throws IOException, DocumentException {
		
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		
		if(isNotNull(boleto.getTitulo().getNossoNumero()))
			sb.append(boleto.getTitulo().getNossoNumero());
		
		if(isNotNull(boleto.getTitulo().getDigitoDoNossoNumero()))
			sb.append(ViewerPDF.SEPERADOR + boleto.getTitulo().getDigitoDoNossoNumero());
		
		form.setField("txtRsNossoNumero", sb.toString());
		form.setField("txtFcNossoNumero", sb.toString());				
	}
	
	
	
	protected File getFile(String pathName)throws IllegalArgumentException, IOException, DocumentException{
		processarPdf();
		return Util4File.bytes2File(pathName, outputStream.toByteArray());
	}
	
	protected ByteArrayOutputStream getStream() throws IOException, DocumentException{
		processarPdf();		
		return Util4File.bytes2Stream(outputStream.toByteArray());
	}
	
	protected byte[] getBytes() throws IOException, DocumentException{
		processarPdf();
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
}
