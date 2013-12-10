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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * Teste unitário da classe PdfDocMix.
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
		
		assertEquals("DEVE SER EMPTY", EMPTY, new String(doc.getTemplate()));
		
		final String Y = "OUTRO_TEMPLATE";
		
		doc.withTemplate(Y.getBytes());
		
		assertEquals(Y, new String(doc.getTemplate()));
	}
		
	@Test
	public void seColocaTextoCorretamenteNaVariavel(){
		
		doc = createDoc();
		
		doc.put("k1", "v1");
		
		Map<String,String> definido = new HashMap<String, String>(1);
		definido.put("k1", "v1");
		
		assertEquals(definido, doc.getTextFields());
	}

	@Test
	public void seColocaTextosCorretamenteNaVariavel(){
		
		doc = createDoc();
		
		Map<String,String> old = new HashMap<String, String>(3);
		old.put("k1", "v1"); 
		old.put("k2", "v2"); 
		
		doc.putAllTexts(old);

		assertEquals(old, doc.getTextFields());
		
		Map<String,String> newOne = new HashMap<String, String>(old);
		newOne.put("k3", "v3");
		
		doc.putAllTexts(newOne);

		assertEquals(old, doc.getTextFields());
	}
	
	@Test
	public void seColocaImagemCorretamenteNaVariavel(){
		
		doc = createDoc();
		
		final Image img = null;
		
		doc.put("k1", img);
		
		Map<String,Image> definido = new HashMap<String, Image>(1);
		definido.put("k1", img);
		
		assertEquals(definido, doc.getImageFields());
	}
	
	@Test
	public void seColocaImagensCorretamenteNaVariavel(){
		
		final Image img1 = null;
		final Image img2 = null;
		final Image img3 = null;
		
		doc = createDoc();	
		
		Map<String,Image> old = new HashMap<String, Image>(3);
		old.put("k1", img1); 
		old.put("k2", img2); 
		
		doc.putAllImages(old);

		assertEquals(old,doc.getImageFields());
		
		Map<String,Image> newOne = new HashMap<String, Image>(old);
		newOne.put("k3", img3);
		
		doc.putAllImages(newOne);
		
		assertEquals(newOne, doc.getImageFields());
	}
	
	@Test
	public void seGeraDocumentoCorretamenteEmBytes() throws IOException{
		
		doc = createDoc();
		
		PdfDocReader readerArqBase = new PdfDocReader(Resources.crieInputStreamParaArquivoComCampos());
		
		PdfDocReader readerArqNovo = new PdfDocReader(doc.toBytes());

		assertTrue("DEVEM SER IGUAIS",
				Resources.DOCUMENT_TITLE.equals(readerArqBase.getInfo().title())
				&& 
				Resources.DOCUMENT_TITLE.equals(readerArqNovo.getInfo().title()));
		
		readerArqBase.close();
		readerArqNovo.close();
	}
	
	@Test
	public void seRemoveCamposDeTextoCorretamente(){
		
		doc = createDoc();
		
		doc.put("k1", "v1");
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertTrue(reader.getFields().isEmpty());
		
		reader.close();
	}
	
	@Test
	public void seColocaTextoCorretamenteNoArquivo(){

		doc = createDoc();
		
		doc.put("nomeDoTestador", "Gilmar P.S.L.");
		doc.put("funcaoDoTestador", "Tester");
		doc.put("nomeDoTeste", "sePutTextCorretamenteNoArquivo");
		
		doc.removeFields(false);
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertEquals(doc.getTextFields(), reader.getFields());
		
		reader.close();
	}
	
	@Test
	public void seColocaTituloCorretamenteNoArquivo(){
		
		final String TITULO = "Titulo Corretamente No Arquivo?";
		
		doc = createDoc();
		
		doc.withTitle(TITULO);
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertEquals(TITULO, reader.getInfo().title());
		
		reader.close();
	}
	
	@Test
	public void seColocaAssuntoCorretamenteNoArquivo(){
		
		final String ASSUNTO = "Assunto Correto No Arquivo?";
		
		doc = createDoc();
		
		doc.withSubject(ASSUNTO);
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertEquals(ASSUNTO, reader.getInfo().subject());
		
		reader.close();
	}
	
	@Test
	public void seColocaPalavrasChaveCorretamenteNoArquivo(){
		
		final String PALAVRA_CHAVE = "palavras, chave, corretas, no arquivo?";
		
		doc = createDoc();
		
		doc.withKeywords(PALAVRA_CHAVE);
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertEquals(PALAVRA_CHAVE, reader.getInfo().keywords());
		
		reader.close();
	}
	
	@Test
	public void seMudaDocinfo(){
		
		final String NOVO_TITULO = "Título mudado agora!";
		
		PdfDocInfo docInfo = PdfDocInfo.create();
		docInfo.title(NOVO_TITULO);
		
		doc = createDoc();
		
		doc.withDocInfo(docInfo);
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertEquals(NOVO_TITULO, reader.getInfo().title());
		
		reader.close();
	}

	@Test
	public void seColocaAutorCorretamenteNoArquivo(){
		
		final String AUTOR = "Este (você) é o autor do arquivo?";
		
		doc = createDoc();
		
		doc.withAuthor(AUTOR);
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertEquals(AUTOR, reader.getInfo().author());
		
		reader.close();
	}

	@Test
	public void seColocaCriadorCorretamenteNoArquivo(){
		
		final String CRIADOR = "Minha Aplicação";
		
		doc = createDoc();
		
		doc.withCreator(CRIADOR);
		
		PdfDocReader reader = new PdfDocReader(doc.toBytes());
		
		assertEquals(CRIADOR+" by (jrimum.org/bopepo)", reader.getInfo().creator());
		
		reader.close();
	}
	
	@Test
	public void seGeraDocumentoEmArquivoViaParamentroFile() throws IOException{

		final String file = "ArquivoComCampos";
		final String filePath = "./src/test/resources/"+file+".pdf";
		
		doc = createDoc();
		
		PdfDocReader readerArqBase = new PdfDocReader(Resources.crieInputStreamParaArquivoComCampos());
		
		final File arqBase = new File(filePath); 
		final File arqTest = File.createTempFile(file, ".pdf");
		
		FileUtils.copyFile(arqBase, arqTest);
		
		doc.toFile(arqTest);
		
		PdfDocReader readerArqNovo = new PdfDocReader(arqBase);

		assertTrue("DEVEM SER IGUAIS",
				Resources.DOCUMENT_TITLE.equals(readerArqBase.getInfo().title())
				&& 
				Resources.DOCUMENT_TITLE.equals(readerArqNovo.getInfo().title()));
		
		readerArqBase.close();
		readerArqNovo.close();
		arqTest.delete();
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
	 * @return doc pronto para uso
	 * 
	 * @see org.jrimum.bopepo.pdf.Resources#crieInputStreamParaArquivoComCampos()
	 */
	private PdfDocMix createDoc(){
		return new PdfDocMix(Resources.crieInputStreamParaArquivoComCampos());
	}
}
