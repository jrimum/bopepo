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
 * Created at: 30/09/2011 - 16:07:23
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
 * Criado em: 30/09/2011 - 16:07:23
 * 
 */

package org.jrimum.bopepo.pdf;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Teste unitário da classe PdfDateConverter.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class TestPdfDateConverter {

	@Test(expected=IllegalArgumentException.class)
	public void seNaoPermiteConversaoDeValoresNulos(){
		
		PdfDateConverter.convert(null);
	}

	@Test
	public void comTimeZoneZero(){
		
		final String expectedDate = "D:20110930144532Z00'00'";
		
		Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.SEPTEMBER, 30, 14, 45, 32);
		c.setTimeZone(TimeZone.getTimeZone("GMT"));

		assertEquals(expectedDate,PdfDateConverter.convert(c));
	}
	
	@Test
	public void comTimeZoneMaiorQueZero(){
		
		final String expectedDate = "D:20110930144532+05'23'";
		
		Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.SEPTEMBER, 30, 14, 45, 32);
		c.setTimeZone(TimeZone.getTimeZone("GMT+05:23"));

		assertEquals(expectedDate,PdfDateConverter.convert(c));
	}

	@Test
	public void comTimeZoneMenorQueZero(){
		
		final String expectedDate = "D:20110930144532-05'23'";
		
		Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.SEPTEMBER, 30, 14, 45, 32);
		c.setTimeZone(TimeZone.getTimeZone("GMT-05:23"));
		
		assertEquals(expectedDate,PdfDateConverter.convert(c));
	}
}
