package org.jrimum.bopepo.pdf;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Teste da unitário da classe {@linkplain PdfDocInfo}.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestPdfDocInfo {
	
	/**
	 * Zero left format.
	 */
	private final String ZL = "%02d";
	
	private final String TITLE = "Modelo Geral";
	private final String SUBJECT = "Template para uso geral";
	private final String KEYWORDS = "Modelo, Artefato, Exemplo";
	private final String AUTHOR = "Gilmar P.S.L.";
	private final String CREATOR = "Writer";
	private final String CREATION_DATE = "D:20110314170209-03'00'";
	private final String MOD_DATE = "D:20110314191242-03'00'";
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
		
		// "D:20110314170209-03'00'"

		PdfDocInfo docInfo = PdfDocInfo.create(info);

		Calendar cal = docInfo.creation();
		
		StringBuilder dateTime = new StringBuilder("D:")
		.append(cal.get(Calendar.YEAR))
		.append(format(ZL, cal.get(Calendar.MONTH)+1))
		.append(format(ZL, cal.get(Calendar.DAY_OF_MONTH)))
		.append(format(ZL, cal.get(Calendar.HOUR_OF_DAY)))
		.append(format(ZL, cal.get(Calendar.MINUTE)))
		.append(format(ZL, cal.get(Calendar.SECOND)));
		
		String zone = cal.getTimeZone().getDisplayName().replace("GMT", EMPTY).replace(":", "'")+"'";
		
		dateTime.append(zone);
		
		assertEquals(CREATION_DATE, dateTime.toString());
	}

	@Test
	public void modificationRawInfo(){
		
		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		assertEquals(MOD_DATE, docInfo.modificationRaw());
	}
	
	@Test
	public void modification(){
		
		// "D:20110314191242-03'00'"
		
		PdfDocInfo docInfo = PdfDocInfo.create(info);
		
		Calendar cal = docInfo.modification();
		
		StringBuilder dateTime = new StringBuilder("D:")
		.append(cal.get(Calendar.YEAR))
		.append(format(ZL, cal.get(Calendar.MONTH)+1))
		.append(format(ZL, cal.get(Calendar.DAY_OF_MONTH)))
		.append(format(ZL, cal.get(Calendar.HOUR_OF_DAY)))
		.append(format(ZL, cal.get(Calendar.MINUTE)))
		.append(format(ZL, cal.get(Calendar.SECOND)));
		
		String zone = cal.getTimeZone().getDisplayName().replace("GMT", EMPTY).replace(":", "'")+"'";
		
		dateTime.append(zone);
		
		assertEquals(MOD_DATE, dateTime.toString());
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
