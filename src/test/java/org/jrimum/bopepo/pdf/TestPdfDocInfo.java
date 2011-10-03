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
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

/**
 * Teste da unitário da classe {@linkplain PdfDocInfo}.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestPdfDocInfo {
	
	private final String TITLE = "Modelo Geral";
	private final String SUBJECT = "Template para uso geral";
	private final String KEYWORDS = "Modelo, Artefato, Exemplo";
	private final String AUTHOR = "Gilmar P.S.L.";
	private final String CREATOR = "Writer";
	private final String CREATION_DATE = "D:20110414170209-03'00'";
	private final String MOD_DATE = "D:20110414191242-03'00'";
	private final String PRODUCER = "BrOffice 3.3; modified using iText 5.0.6 (c) 1T3XT BVBA";
	
	private Map<String,String> info;
	
	@Before
	public void setUp(){
		
		info = new HashMap<String, String>(8);
		info.put("Title", TITLE);
		info.put("Subject", SUBJECT);
		info.put("Keywords", KEYWORDS);
		info.put("Author", AUTHOR);
		info.put("Creator", CREATOR);
		info.put("Producer", PRODUCER);
		info.put("CreationDate",CREATION_DATE);
		info.put("ModDate", MOD_DATE);
	}
	
	@Test
	public void titleInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create().title(TITLE);
		
		assertEquals(TITLE, docInfo.title());
	}

	@Test
	public void subjectInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create().subject(SUBJECT);
		
		assertEquals(SUBJECT, docInfo.subject());
	}
	
	@Test
	public void keywordsInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create().keywords(KEYWORDS);
		
		assertEquals(KEYWORDS, docInfo.keywords());
	}

	@Test
	public void authorInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create().author(AUTHOR);
		
		assertEquals(AUTHOR, docInfo.author());
	}
	
	@Test
	public void creatorInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create().creator(CREATOR);
		
		assertEquals(CREATOR, docInfo.creator());
	}

	@Test
	public void producerInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		assertEquals(PRODUCER, docInfo.producer());
	}
	
	@Test
	public void creationRawInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		assertEquals(CREATION_DATE, docInfo.creationRaw());
	}

	@Test
	public void creation(){

		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		assertEquals(CREATION_DATE, PdfDateConverter.convert(docInfo.creation()));
	}
	
	@Test
	public void creationWithCalendar(){
		
		final Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.APRIL, 14, 17, 2, 9);
		c.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
		
		PdfDocInfo docInfo = PdfDocInfo.create();
		
		docInfo.creation(c);
		
		assertEquals(CREATION_DATE, PdfDateConverter.convert(docInfo.creation()));
	}

	@Test
	public void modificationRawInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		assertEquals(MOD_DATE, docInfo.modificationRaw());
	}
	
	@Test
	public void modification(){
		
		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		assertEquals(MOD_DATE, PdfDateConverter.convert(docInfo.modification()));
	}
	
	@Test
	public void modificationWithCalendar(){
		
		final Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.APRIL, 14, 19, 12, 42);
		c.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
		
		PdfDocInfo docInfo = PdfDocInfo.create();
		
		docInfo.modification(c);
		
		assertEquals(MOD_DATE, PdfDateConverter.convert(docInfo.modification()));
	}

	@Test
	public void equalsInCaseEquals(){
		
		PdfDocInfo docInfo1 = PdfDocInfo.create(info);
		PdfDocInfo docInfo2 = PdfDocInfo.create(new HashMap<String, String>(info));
		
		assertEquals(docInfo1, docInfo2);
	}

	@Test
	public void equalsInCaseDiff(){
		
		PdfDocInfo docInfo1 = PdfDocInfo.create(info);
		
		Map<String,String> diffInfo = new HashMap<String, String>(info);
		diffInfo.put("Author", "OUTRO");
		
		PdfDocInfo docInfo2 = PdfDocInfo.create(diffInfo);
		
		assertTrue("Deve ser diferente!",!docInfo1.equals(docInfo2));
	}
	
	@Test
	public void hashCodeInCaseEquals(){

		PdfDocInfo docInfo1 = PdfDocInfo.create(info);
		PdfDocInfo docInfo2 = PdfDocInfo.create(new HashMap<String, String>(info));
		
		assertEquals(docInfo1.hashCode(), docInfo2.hashCode());
	}
	
	@Test
	public void hashCodeInCaseDiff(){
		
		PdfDocInfo docInfo1 = PdfDocInfo.create(info);
		
		Map<String,String> diffInfo = new HashMap<String, String>(info);
		diffInfo.put("Author", "OUTRO");
		
		PdfDocInfo docInfo2 = PdfDocInfo.create(diffInfo);
		
		assertTrue("Deve ser diferente!",docInfo1.hashCode() != docInfo2.hashCode());
	}
	
	@Test
	public void docInfoToMap(){
		
		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		assertEquals(info, docInfo.toMap());
	}

}
