/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 19/03/2011 - ‎21:56:10
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 19/03/2011 - ‎21:56:10
 * 
 */

package org.jrimum.bopepo.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.bopepo.pdf.PDFUtil;

/**
 * <p>
 * Classe utilizada para preencher o PDF de boletos em lote.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class PdfViewerMultiProcessor {
	
	/**
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param boletoViewer
	 *            Visualizador contendo o template para geração
	 * 
	 * @return Arquivo PDF em array de bytes gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	protected static byte[] groupInOnePDF(List<Boleto> boletos, BoletoViewer boletoViewer) {
		
		List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());
		
		for (Boleto bop : boletos) {
			boletosEmBytes.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
		}
		
		try {
			
			return PDFUtil.mergeFiles(boletosEmBytes);
			
		} catch (Exception e) {
			
			throw new IllegalStateException("Erro durante geração do PDF! Causado por " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Agrupa os boletos da lista em um único arquivo PDF.
	 * 
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param boletoViewer
	 *            Visualizador contendo o template para geração
	 * @param fileDest
	 *            Arquivo o qual armazenará os boletos
	 * @return Arquivo PDF gerado com os boletos da lista
	 * 
	 * @since 0.2
	 */
	protected static File groupInOnePDF(List<Boleto> boletos, BoletoViewer boletoViewer, File fileDest) {

		try {
			
			return Files.bytesToFile(fileDest, groupInOnePDF(boletos, boletoViewer));
			
		} catch (Exception e) {
			
			throw new IllegalStateException("Erro durante geração do PDF! Causado por " + e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * Agrupa os boletos das listas com seus respectivos templates em um único
	 * arquivo PDF. Caso exista sequência na coleção, a mesma é mantida.
	 * 
	 * 
	 * @param templatesAndBoletos
	 *            Coleção de templates e boletos a serem agrupados
	 * 
	 * @return Arquivo PDF em array de bytes gerado com os boletos fornecidos
	 * 
	 * @since 0.2
	 */
	protected static byte[] groupInOnePDF(Collection<Entry<byte[],List<Boleto>>> templatesAndBoletos) {
		
		List<byte[]> toMerge = new ArrayList<byte[]>(templatesAndBoletos.size());
		
		BoletoViewer viewer = new BoletoViewer();
		
		for(Entry<byte[],List<Boleto>> entry : templatesAndBoletos){
			
			toMerge.add(groupInOnePDF(entry.getValue(), viewer.setTemplate(entry.getKey())));
		}

		return PDFUtil.mergeFiles(toMerge);
	}

	/**
	 * Gera um arquivo PDF para cada boleto contido na lista. O nome do arquivo
	 * segue a forma:<br />
	 * <br />
	 * <tt>diretorio + (/ ou \\) prefixo + (indice do arquivo na lista + 1) + sufixo + ".pdf"</tt>
	 * 
	 * <p>
	 * Exemplo, uma lista com 3 boletos: {@code onePerPDF(boletos, file,
	 * "BoletoPrefixo", "exSufixo");} <br />
	 * <br />
	 * Arquivos gerados:
	 * <ul>
	 * <li><strong>BoletoPrefixo1exSufixo.pdf</strong></li>
	 * <li><strong>BoletoPrefixo2exSufixo.pdf</strong></li>
	 * <li><strong>BoletoPrefixo3exSufixo.pdf</strong></li>
	 * </ul>
	 * </p>
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param fileDest
	 *            Diretório o qual os boletos serão criados
	 * @param prefixo
	 *            Prefixo do nome do arquivo
	 * @param sufixo
	 *            Sufixo do nome do arquivo
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	protected static List<File> onePerPDF(List<Boleto> boletos, File destDir, String prefixo, String sufixo) {

		final List<File> arquivos = new ArrayList<File>(boletos.size());
		final BoletoViewer bv = new BoletoViewer();
		int cont = 1;
		
		for (Boleto bop : boletos) {
			arquivos.add(bv.setBoleto(bop).getPdfAsFile(destDir.getAbsolutePath() + File.separator + prefixo + cont++ + sufixo + ".pdf"));
		}

		return arquivos;
	}
	
	/**
	 * Gera um arquivo PDF para cada boleto contido na lista utilizando o
	 * template padrão do Bopepo.
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	protected static List<byte[]> onePerPDF(List<Boleto> boletos) {

		return onePerPDF(boletos, new BoletoViewer());
	}
	
	/**
	 * Gera um arquivo PDF para cada boleto contido na lista utilizando o Viewer
	 * informado.
	 * 
	 * @param boletos
	 *            Lista com os boletos a serem agrupados
	 * @param boletoViewer
	 *            Viewer contendo as informações necessárias, incluindo template
	 * 
	 * @return Lista contendo os arquivos PDF gerados a partir da lista de
	 *         boletos
	 * 
	 * @since 0.2
	 */
	protected static List<byte[]> onePerPDF(List<Boleto> boletos, BoletoViewer boletoViewer) {

		final List<byte[]> arquivos = new ArrayList<byte[]>(boletos.size());
		
		for (Boleto bop : boletos) {
			arquivos.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
		}

		return arquivos;
	}
	
	/**
	 * Gera um arquivo PDF para cada template e boleto relacionado em cada
	 * Entry. Caso exista sequência na coleção, a mesma é mantida.
	 * 
	 * 
	 * @param templatesAndBoletos
	 *            - Coleção de templates e boletos para geração de arquivo PDF
	 * 
	 * @return Lista contendo os arquivos PDF gerados com cada template e seus
	 *         boletos
	 * 
	 * @since 0.2
	 */
	protected static List<byte[]> onePerPDF(Collection<Entry<byte[],List<Boleto>>> templatesAndBoletos) {
		
		List<byte[]> boletos = new ArrayList<byte[]>(templatesAndBoletos.size());
		
		BoletoViewer viewer = new BoletoViewer();
		
		for(Entry<byte[],List<Boleto>> entry : templatesAndBoletos){
			
			boletos.addAll(onePerPDF(entry.getValue(), viewer.setTemplate(entry.getKey())));
		}

		return boletos;
	}
	
}
