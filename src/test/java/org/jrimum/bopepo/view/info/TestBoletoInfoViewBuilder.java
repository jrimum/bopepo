/*
 * Copyright 2013 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 08/09/2013 - 01:13:22
 * 
 * ================================================================================
 * 
 * Direitos autorais 2013 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 08/09/2013 - 01:13:22
 * 
 */

package org.jrimum.bopepo.view.info;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.jrimum.utilix.Objects.whenNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.excludes.Images;
import org.jrimum.bopepo.pdf.CodigoDeBarras;
import org.jrimum.bopepo.view.BoletoCampo;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.BoletoInfoViewBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBoletoInfoViewBuilder {
	
	private Boleto boleto;
	private ResourceBundle resourceBundle; 
	private BoletoInfoViewBuilder boletoInfoViewBuilder;
	private java.util.ResourceBundle boletoDadosEsperados;
	
	@Before
	public void setup(){
		
		this.resourceBundle = new ResourceBundle();
		this.boleto = BoletoBuilder.defaultValueSacadorAvalista();
		this.boletoInfoViewBuilder = new BoletoInfoViewBuilder(resourceBundle, boleto).build();
		this.boletoDadosEsperados = java.util.ResourceBundle.getBundle("ValoresEsperadosDosCamposParaBoletoBradescoPDF");
	}
	
	@Test
	public void deve_ter_todos_os_campos_de_texto_padrao_preenchidos_com_textos_formatados_para_exibir_no_boleto(){
		
		Map<String, String> camposTextoNoBoleto = new TreeMap<String, String> (boletoInfoViewBuilder.texts());
		
		for(BoletoCampo campo: BoletoCampo.values()){
			String textoEsperado = boletoDadosEsperados.getString(campo.name());
			String textoAtual = whenNull(camposTextoNoBoleto.get(campo.name()), EMPTY);
			assertEquals("CAMPO: "+campo,textoEsperado,textoAtual);
		}
	}

	@Test
	public void deve_ter_todos_os_campos_de_imagem_padrao_preenchidos_com_as_imagens_corretas_para_exibir_no_boleto(){

		Map<String, Image> camposImagem = boletoInfoViewBuilder.images();
		
		Image logoBancoBradescoEsperada = resourceBundle.getLogotipoDoBanco("237");
		assertEquals(logoBancoBradescoEsperada, camposImagem.get(BoletoCampo.txtRsLogoBanco.name()));
		assertEquals(logoBancoBradescoEsperada, camposImagem.get(BoletoCampo.txtFcLogoBanco.name()));

		BufferedImage codigoDeBarrasEsperado = Images.toBufferedImage(CodigoDeBarras.valueOf(boleto.getCodigoDeBarras().write()).toImage());
		BufferedImage codigoDeBarradasCriado = Images.toBufferedImage(camposImagem.get(BoletoCampo.txtFcCodigoBarra.name()));
		assertTrue(Images.areEqual(codigoDeBarrasEsperado,codigoDeBarradasCriado));
	}

}
