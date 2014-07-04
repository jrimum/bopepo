/* 
 * Copyright 2013 JRimum Project
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
 * Created at: 10/12/2013 - 19:18:43
 *
 * ================================================================================
 *
 * Direitos autorais 2013 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 10/12/2013 - 19:18:43 
 * 
 */


package org.jrimum.bopepo.functional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.pdf.PdfDocInfo;
import org.jrimum.bopepo.pdf.PdfDocReader;
import org.jrimum.bopepo.view.BoletoViewer;
import org.junit.Test;

public class TestBoletoPdfFeatures {

	@Test
	public void deve_ter_todos_os_meta_dados_do_boleto_em_pdf_definidos_pelo_usuario() {
		byte[] boletoPdf = BoletoViewer.create(BoletoBuilder.defaultValue())
		.setPdfTitulo("Titulo")
		.setPdfAssunto("Assunto")
		.setPdfPalavrasChave("Palavras Chave")
		.setPdfAutor("Autor")
		.getPdfAsByteArray();

		PdfDocInfo pdfMetaInfo = new PdfDocReader(boletoPdf).getInfo();

		assertThat(pdfMetaInfo.title(), equalTo("Titulo"));
		assertThat(pdfMetaInfo.subject(), equalTo("Assunto"));
		assertThat(pdfMetaInfo.keywords(), equalTo("Palavras Chave"));
		assertThat(pdfMetaInfo.author(), equalTo("Autor"));
	}

	@Test
	public void deve_comprimir_pdf_por_padrao() {
		final boolean NAO = false;
		byte[] boletoPdfComprimido = BoletoViewer.create(BoletoBuilder.defaultValue()).getPdfAsByteArray();
		byte[] boletoPdfNaoComprimido = BoletoViewer.create(BoletoBuilder.defaultValue()).setPdfFullCompression(NAO).getPdfAsByteArray();

		assertTrue(boletoPdfComprimido.length < boletoPdfNaoComprimido.length);
	}

	@Test
	public void deve_remover_campos_por_padrao() {
		final boolean NAO = false;
		byte[] boletoPdfSemCampos = BoletoViewer.create(BoletoBuilder.defaultValue()).getPdfAsByteArray();
		byte[] boletoPdfComCampos = BoletoViewer.create(BoletoBuilder.defaultValue()).setPdfRemoverCampos(NAO).getPdfAsByteArray();
		
		PdfDocReader pdfDocSemCampos = new PdfDocReader(boletoPdfSemCampos);
		PdfDocReader pdfDocComCampos = new PdfDocReader(boletoPdfComCampos);
		
		assertThat(pdfDocSemCampos.getFields().size(), equalTo(0));
		assertThat(pdfDocComCampos.getFields().size(), not(equalTo(0)));
	}

}
