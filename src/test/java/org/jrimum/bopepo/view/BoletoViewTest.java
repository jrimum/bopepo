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
 * Created at: 16/10/2013 - 00:36:20
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
 * Criado em: 16/10/2013 - 00:36:20 
 * 
 */


package org.jrimum.bopepo.view;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
	

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class BoletoViewTest {

	@Mock
	private PdfViewer pdfViewer;
	
	@Test
	public void deve_chamar_metodo_de_definicao_da_compressao_em_pdfview(){
		BoletoViewer boletoViewer = new BoletoViewer(pdfViewer);
		boolean option = true;
		
		boletoViewer.setPdfFullCompression(option);
		
		verify(pdfViewer).setFullCompression(option);
	}

	@Test
	public void deve_chamar_metodo_de_definicao_do_titulo_em_pdfview(){
		BoletoViewer boletoViewer = new BoletoViewer(pdfViewer);
		String titulo = "Boleto de Pagamento";
		
		boletoViewer.setPdfTitulo(titulo);
		
		verify(pdfViewer).setTitle(titulo);
	}

	@Test
	public void deve_chamar_metodo_de_definicao_do_autor_em_pdfview(){
		BoletoViewer boletoViewer = new BoletoViewer(pdfViewer);
		String autor = "Eu";
		
//		boletoViewer.setPdfAutor(autor);
		
//		verify(pdfViewer).setAuthor(autor);
	}
	
}