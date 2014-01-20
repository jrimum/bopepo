/* 
 * Copyright 2014 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 12/01/2014 - 17:33:16
 *
 * ================================================================================
 *
 * Direitos autorais 2014 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 12/01/2014 - 17:33:16 
 * 
 */

package org.jrimum.bopepo.functional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.excludes.Images;
import org.jrimum.bopepo.excludes.PDFs;
import org.jrimum.bopepo.pdf.CodigoDeBarras;
import org.jrimum.bopepo.pdf.PdfDocReader;
import org.jrimum.bopepo.view.BoletoCampo;
import org.jrimum.bopepo.view.BoletoViewer;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.io.Resources;


/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestValoresDosCamposDoBoletoBradescoPDF {
	
	/**
	 * Por definição, o pdf do boleto contém apenas duas imagens: logo do banco e o código de barras.
	 * 
	 * @throws IOException
	 */
	@Test
	public void deve_gerar_boleto_com_apenas_duas_imagens() throws IOException{
		BoletoViewer boletoViewer = BoletoViewer
				.create(BoletoBuilder.defaultValueSacadorAvalista());
		
		Map<String, Collection<BufferedImage>> images = PDFs.getImages(boletoViewer.getPdfAsByteArray());
		
		assertThat(images.size(), equalTo(2));//logo do banco e codigo de barras
		
	}
 
	@Test
	public void deve_gerar_boleto_com_duas_logos_do_banco() throws IOException{
		BoletoViewer boletoViewer = BoletoViewer
				.create(BoletoBuilder.defaultValueSacadorAvalista());

		Map<String, Collection<BufferedImage>> imagensNoBoleto = PDFs.getImages(boletoViewer.getPdfAsByteArray());
		
		Collection<BufferedImage> logosNoBoleto = getImagem(ImagemDoBoleto.LOGO_BANCO, imagensNoBoleto);
		
		BufferedImage logoBancoBradescoEsperado = ImageIO.read(Resources.getResource("img/237.png"));
		
		BufferedImage logo1Boleto = Iterables.get(logosNoBoleto, 0);
		BufferedImage logo2Boleto = Iterables.get(logosNoBoleto, 1);
		
		
		assertThat(logosNoBoleto.size(), equalTo(2));
		assertTrue(Images.areEqual(logo1Boleto, logoBancoBradescoEsperado));
		assertTrue(Images.areEqual(logo1Boleto, logo2Boleto));
	}

	@Test
	public void deve_gerar_boleto_com_codigo_de_barras() throws IOException{
		BoletoViewer boletoViewer = BoletoViewer
				.create(BoletoBuilder.defaultValueSacadorAvalista());
		
		Map<String, Collection<BufferedImage>> imagesMap = PDFs.getImages(boletoViewer.getPdfAsByteArray());
		
		Collection<BufferedImage> codigoDeBarrasImgCollection = getImagem(ImagemDoBoleto.CODIGO_DE_BARRAS, imagesMap);
		
		String codigoDeBarrasNumerico = boletoViewer.getBoleto().getCodigoDeBarras().write();
	
		BufferedImage codigoDeBarrasImgEsperado = Images.toBufferedImage(CodigoDeBarras.valueOf(codigoDeBarrasNumerico).toImage());
		
		assertThat(codigoDeBarrasImgCollection.size(), equalTo(1));
		assertTrue(Images.areEqual(codigoDeBarrasImgEsperado, codigoDeBarrasImgCollection.iterator().next()));
		
		//TODO Ler o código de barras e conferir o número
	}
	
	@Test
	public void deve_gerar_boleto_com_os_campos_texto_formatados_corretamente() throws IOException {
		boolean NAO = false;
		byte[] boletoPdf = BoletoViewer
				.create(BoletoBuilder.defaultValueSacadorAvalista())
				.setPdfRemoverCampos(NAO)
				.getPdfAsByteArray();

		Map<String, String> camposDoBoleto = new PdfDocReader(boletoPdf).getFields();
		Set<Entry<Object, Object>> valoresEsperados = getValoresEsperadosParaBoletoBradescoPDF();
		
		assertThat(camposDoBoleto.size(), equalTo(valoresEsperados.size()));
		assertThat(camposDoBoleto.size(), equalTo(BoletoCampo.values().length));
		assertThatAreEquals(camposDoBoleto, valoresEsperados);
	}

	private void assertThatAreEquals(Map<String, String> camposDoBoleto,
			Set<Entry<Object, Object>> valoresEsperados) {
		
		for(Entry<Object, Object> campoValorEsperado : valoresEsperados){
			
			Object nomeDoCampoEsperado = campoValorEsperado.getKey();
			Object valorDoCampoEsperado = campoValorEsperado.getValue();
			String valorDoCampoNoBoleto = camposDoBoleto.get(nomeDoCampoEsperado);

			assertThat(format("Campo [%s] ",nomeDoCampoEsperado),valorDoCampoNoBoleto, equalTo(valorDoCampoEsperado));
		}
	}

	private Set<Entry<Object, Object>> getValoresEsperadosParaBoletoBradescoPDF() throws IOException {
		Properties properties = new Properties();
		properties.load(Resources.getResource("ValoresEsperadosDosCamposParaBoletoBradescoPDF.properties").openStream());
		return properties.entrySet();
	}
	
	private Collection<BufferedImage> getImagem(ImagemDoBoleto imagem, Map<String, Collection<BufferedImage>> imagesMap){
		for (Collection<BufferedImage> images : imagesMap.values()) {
			if(images.size() == imagem.quantidade()){
				return images;
			}
		}
		return Collections.emptyList();
	}
	
	private enum ImagemDoBoleto{
		LOGO_BANCO(2), 
		CODIGO_DE_BARRAS(1);
		private final int quantidade;
		private ImagemDoBoleto(int q){
			quantidade = q;
		}
		public int quantidade(){
			return quantidade;
		}
	}

}
