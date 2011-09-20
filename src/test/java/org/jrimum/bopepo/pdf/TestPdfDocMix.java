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
 * Created at: 14/04/2011 - 14:49:07
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
 * Criado em: 14/04/2011 - 14:49:07
 * 
 */

package org.jrimum.bopepo.pdf;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * Teste da classe PdfDocMix.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 *
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class TestPdfDocMix {
	
	private PdfDocMix doc = null;
	
	@Test(expected=IllegalArgumentException.class)
	public void seNaoPermiteCriarComTemplateNullBytes(){
		
		byte[] NULL_BYTE_ARRAY = null;
		
		doc = PdfDocMix.createWithTemplate(NULL_BYTE_ARRAY);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void seNaoPermiteCriarComTemplateNullInputStream(){
		
		InputStream NULL_INPUT_STREAM = null;
		
		doc = PdfDocMix.createWithTemplate(NULL_INPUT_STREAM);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void seNaoPermiteCriarComTemplateNullURL(){
		
		URL NULL_URL = null;
		
		doc = PdfDocMix.createWithTemplate(NULL_URL);
	}

	@Test(expected=IllegalArgumentException.class)
	public void seNaoPermiteCriarComTemplateNullFile(){
		
		File NULL_FILE = null;
		
		doc = PdfDocMix.createWithTemplate(NULL_FILE);
	}
	
	@Test
	public void sePermiteCriarComTemplateBytes(){
		
		doc = PdfDocMix.createWithTemplate(EMPTY.getBytes());
		
		assertNotNull(doc);
	}
	
	@Test
	public void sePermiteCriarComTemplateInputStream(){
		
		doc = PdfDocMix.createWithTemplate(new ByteArrayInputStream(EMPTY.getBytes()));
		
		assertNotNull(doc);
	}
	
	@Test
	public void sePermiteCriarComTemplateURL() throws MalformedURLException{
		
		doc = PdfDocMix.createWithTemplate(new URL("file:///"));
		
		assertNotNull(doc);
	}

	@Test
	public void sePermiteCriarComTemplateFile() throws IOException{
		
		doc = PdfDocMix.createWithTemplate(File.createTempFile(this.getClass().getName(), EMPTY));
		
		assertNotNull(doc);
	}
	
	@Test
	public void seTemplateEhAtribuidoCorretamenteNaInstancia(){
		
		final String X = "TEMPLATE";
		
		doc = new PdfDocMix(X.getBytes());
		
		assertEquals(X, new String(doc.getTemplate()));
	}
	
	@Test
	public void sePermiteMudarTemplateCorretamenteBytes(){
		
		doc = new PdfDocMix(EMPTY.getBytes());
		
		assertEquals("DEVE SER EMPTY=''", EMPTY, new String(doc.getTemplate()));
		
		final String Y = "OUTRO_TEMPLATE";
		
		doc.changeTemplate(Y.getBytes());
		
		assertEquals(Y, new String(doc.getTemplate()));
	}
	
	
}
