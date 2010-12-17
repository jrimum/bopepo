package org.jrimum.bopepo.exemplo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.ClassLoaders;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Exemplo de boleto no estilo carnê com três boletos em uma página.
 * 
 * @author Rômulo Augusto
 */
public class BoletoCarne3PorPagina {

	public static void main(String[] args) throws DocumentException, IOException {

		Boleto boletoBBNossoNumero10 = crieBoletoBBNossoNumero10();
		Boleto boletoBradesco = crieBoletoBradesco();
		
		List<Boleto> boletos = new ArrayList<Boleto>();
		boletos.add(boletoBBNossoNumero10);
		boletos.add(boletoBradesco);
		boletos.add(boletoBBNossoNumero10);
		boletos.add(boletoBradesco);
		boletos.add(boletoBBNossoNumero10);
		boletos.add(boletoBBNossoNumero10);
		boletos.add(boletoBradesco);
		boletos.add(boletoBBNossoNumero10);
		
		//Informando o template personalizado:
		File templatePersonalizado = new File(ClassLoaders.getResource("/templates/BoletoCarne3PorPagina.pdf").getFile());
		
		File boletosPorPagina = groupInPages(boletos, "Carne3PorPagina.pdf", templatePersonalizado);

		Exemplos.mostreBoletoNaTela(boletosPorPagina);
	}
	
	private static File groupInPages(List<Boleto> boletos, String filePath, File templatePersonalizado) {
		
		File arq = null;
		BoletoViewer boletoViewer = new BoletoViewer(boletos.get(0));
		boletoViewer.setTemplate(templatePersonalizado);

		List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());

		for (Boleto bop : boletos) {
			boletosEmBytes.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
		}

		try {
			
			arq = Files.bytesToFile(filePath, mergeFilesInPages(boletosEmBytes));
			
		} catch (Exception e) {
			throw new IllegalStateException("Erro durante geração do PDF! Causado por " + e.getLocalizedMessage(), e);
		}

		return arq;
	}

	private static Boleto crieBoletoBBNossoNumero10() {
		
		Titulo titulo = Exemplos.crieTitulo();
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_DO_BRASIL.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(1234567));
		contaBancaria.setCarteira(new Carteira(12));
		
		titulo.setNossoNumero("1234567890");
		
		Boleto boletoBBNossoNumero10 = Exemplos.crieBoleto(titulo);
		
		return boletoBBNossoNumero10;
	}
	
	private static Boleto crieBoletoBradesco() {
		
		Titulo titulo = Exemplos.crieTitulo();
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_BRADESCO.create());
		contaBancaria.setAgencia(new Agencia(1234));
		contaBancaria.setCarteira(new Carteira(12));
		titulo.setNossoNumero("01234567891");
		contaBancaria.setNumeroDaConta(new NumeroDaConta(1234567));
		
		Boleto boletoBradesco = Exemplos.crieBoleto(titulo);
		
		return boletoBradesco;
	}
	
	public static byte[] mergeFilesInPages(List<byte[]> pdfFilesAsByteArray) throws DocumentException, IOException {

		Document document = new Document();
		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
		
        PdfWriter writer = PdfWriter.getInstance(document, byteOS);
        
        document.open();
        
        PdfContentByte cb = writer.getDirectContent();
        float positionAnterior = 0;
        
        for (byte[] in : pdfFilesAsByteArray) {
        	
            PdfReader reader = new PdfReader(in);
            
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            	
            	float documentHeight = cb.getPdfDocument().getPageSize().getHeight();

            	//import the page from source pdf
            	PdfImportedPage page = writer.getImportedPage(reader, i);
            	float pagePosition = positionAnterior;
            	
            	if ( (documentHeight - positionAnterior) <=  page.getHeight()) {
            		document.newPage();
            		pagePosition = 0;
            		positionAnterior = 0;
            	}
            	
                //add the page to the destination pdf
                cb.addTemplate(page, 0, pagePosition);
                
                positionAnterior += page.getHeight();
            }
        }
        
        byteOS.flush();
        document.close();
        
        byte[] arquivoEmBytes = byteOS.toByteArray();
        byteOS.close();
        
        return arquivoEmBytes;
	}
}
