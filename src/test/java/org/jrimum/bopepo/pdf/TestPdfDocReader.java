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
 * Created at: 19/09/2011 - 15:43:26
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
 * Criado em: 19/09/2011 - 15:43:26
 * 
 */

package org.jrimum.bopepo.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

/**
 * Teste unitário da classe TestPdfDocReader.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class TestPdfDocReader {

	private PdfDocReader reader;
	
	@Test
	public void seDocInfoEstahCorreto() throws ParseException{
		
		reader = crieReaderParaArquivoSemCampos();
		
		PdfDocInfo info = reader.getInfo();
		
		assertEquals(Resources.DOCUMENT_TITLE, info.title());
		assertEquals("Caso de teste", info.subject());
		assertEquals("Teste, JRimum", info.keywords());
		assertEquals("Gilmar P.S.L.", info.author());
		assertEquals("Writer", info.creator());
		assertEquals("BrOffice 3.3", info.producer());
		assertEquals("D:20110923112741-03'00'", info.creationRaw());
	}
	
	@Test
	public void seGetFieldRetornaNullQuandoNaoTemCamposDisponiveis(){
		
		reader = crieReaderParaArquivoSemCampos();

		assertNull(reader.getField("teste"));
	}

	@Test
	public void seGetFieldsNamesRetornaVazioQuandoNaoTemCamposDisponiveis(){
		
		reader = crieReaderParaArquivoSemCampos();
		
		assertTrue(reader.getFieldsNames().isEmpty());
	}

	@Test
	public void seGetFieldsRetornaVazioQuandoNaoTemCamposDisponiveis(){
		
		reader = crieReaderParaArquivoSemCampos();
		
		assertTrue(reader.getFields().isEmpty());
	}
	
	@Test
	public void seGetFieldRetornaValorCorretoQuandoCamposEstaoDisponiveis(){
		
		reader = crieReaderParaArquivoComCampos();

		assertEquals("Developer",reader.getField("funcaoDoTestador"));
	}

	@Test
	public void seGetFieldsNamesRetornaValoresCorretosQuandoCamposEstaoDisponiveis(){
		
		reader = crieReaderParaArquivoComCampos();
		
		Set<String> camposDefinidos = new HashSet<String>(3);
	
		camposDefinidos.add("nomeDoTestador");
		camposDefinidos.add("funcaoDoTestador");
		camposDefinidos.add("nomeDoTeste");
		
		assertEquals(camposDefinidos,reader.getFieldsNames());
	}
	
	@Test
	public void seGetFieldsRetornaValoresCorretosQuandoCamposEstaoDisponiveis(){
		
		reader = crieReaderParaArquivoComCampos();
		
		Map<String,String> definicao = new HashMap<String, String>(3); 
		definicao.put("nomeDoTestador","JRiboy Brasileiro da Ordem do Progresso");
		definicao.put("funcaoDoTestador","Developer");
		definicao.put("nomeDoTeste","A definir...");
		
		assertEquals(definicao, reader.getFields());
	}
	
	@After
	public void tearDown(){
		reader.close();
	}
	
	/**
	 * Arquivo no classpath sem fields.
	 * 
	 * @return reader pronto para uso
	 * 
	 * @see org.jrimum.bopepo.pdf.Resources#crieInputStreamParaArquivoSemCampos()
	 */
	private PdfDocReader crieReaderParaArquivoSemCampos(){
		return new PdfDocReader(Resources.crieInputStreamParaArquivoSemCampos());
	}

	/**
	 * Arquivo no classpath com 3 campos:
	 * 
	 * <ul>
	 * <li>nomeDoTestador:"JRiboy Brasileiro da Ordem do Progresso"</li>
	 * <li>funcaoDoTestador:"Developer"</li>
	 * <li>nomeDoTeste:"A definir..."</li>
	 * </u>
	 * 
	 * @return reader pronto para uso
	 * 
	 * org.jrimum.bopepo.pdf.Resources#crieInputStreamParaArquivoComCampos()
	 */
	private PdfDocReader crieReaderParaArquivoComCampos(){
		return new PdfDocReader(Resources.crieInputStreamParaArquivoComCampos());
	}
}
