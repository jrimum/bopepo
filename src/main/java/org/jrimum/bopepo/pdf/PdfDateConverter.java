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

import static java.lang.String.format;
import static org.jrimum.texgit.type.component.Fillers.ZERO_LEFT;
import static org.jrimum.utilix.Objects.isNotNull;

import java.util.Calendar;
import java.util.TimeZone;

import org.jrimum.utilix.Objects;

/**
 * Converte datas para o formato usando no padrão PDF.
 * 
 * <p>
 * PDF defines standard date format, which closely follows that of the
 * international standard ASN.1 (Abstract Syntax Notation One), defined in
 * ISO/IEC 8824. A date is a string of the form (<b>D:YYYYMMDDHHmmSSOHH'mm'</b>)
 * where: <br />
 * <ul>
 * <li><b>YYYY</b> is the year</li>
 * <li><b>MM</b> is the month</li>
 * <li><b>DD</b> is the day (01-31)</li>
 * <li><b>HH</b> is the hour (00-23)</li>
 * <li><b>mm</b> is the minute (00-59)</li>
 * <li><b>SS</b> is the second (00-59)</li>
 * <li><b>O</b> is the relationship of local time to Universal Time (UT),
 * denoted by one of the characters +, -, or Z (see below)</li>
 * <li><b>HH</b> followed by ' is the absolute value of the offset from UT in
 * hours (00-23)</li>
 * <li><b>mm</b> followed by ' is the absolute value of the offset from UT in
 * minutes (00-59)</li>
 * </ul>
 * The apostrophe character (') after HH and mm is part of the syntax. All
 * fields after the year are optional. (The prefix D:, although also optional,
 * is strongly recommended.)
 * </p>
 * <p>
 * The default values for MM and DD are both 01; all other numerical fields
 * default to zero values. A plus sign (+) as the value of the O field signifies
 * that local time is later than UT, a minus sign (-) that local time is earlier
 * than UT, and the letter Z that local time is equal to UT. If no UT
 * information is specified, the relationship of the specified time to UT is
 * considered to be unknown. Whether or not the time zone is known, the rest of
 * the date should be specified in local time.
 * </p>
 * <p>
 * For example, April 14, 2010, at 9:50 PM, U.S. Pacific Standard Time, is
 * represented by the string (D:201004142150-08'00')
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class PdfDateConverter {

	/**
	 * Converte a data para o formato de data do PDF: <b>
	 * {@code D:YYYYMMDDHHmmSSOHH'mm'}</b>, caso não exista um time zone, o fuso
	 * horário do meridiano de Greenwich então será usado, resultando em
	 * D:YYYYMMDDHHmm{@code "Z00'00'"}.
	 * 
	 * @param date
	 * 
	 * @return data no formato PDF: {@code D:YYYYMMDDHHmmSSOHH'mm'}
	 */
	public static String convert(final Calendar date) {

		Objects.checkNotNull(date,
				"Null não pode ser convertido em uma data no formato PDF!");

		final int NOT_INDEX = 1;

		StringBuilder dateTime = new StringBuilder("D:")
				.append(date.get(Calendar.YEAR))
				.append(ZERO_LEFT.fill(date.get(Calendar.MONTH) + NOT_INDEX, 2))
				.append(ZERO_LEFT.fill(date.get(Calendar.DAY_OF_MONTH), 2))
				.append(ZERO_LEFT.fill(date.get(Calendar.HOUR_OF_DAY), 2))
				.append(ZERO_LEFT.fill(date.get(Calendar.MINUTE), 2))
				.append(ZERO_LEFT.fill(date.get(Calendar.SECOND), 2))
				.append(convertTimeZone(date));

		return dateTime.toString();
	}

	/**
	 * Converte o time zone para o formato {@code "OHH'mm'"}, caso não exista um
	 * time zone, o fuso horário do meridiano de Greenwich então será usado,
	 * resultando no valor {@code "Z00'00'"}.
	 * 
	 * @param date
	 * 
	 * @return time zone no formato {@code "OHH'mm'"}
	 */
	private static String convertTimeZone(final Calendar date) {

		final int MINUTES_PER_HOUR = 60;
		final int MILLISECONDS_PER_SECOND = 1000;
		final int SECONDS_PER_MINUTE = 60;

		final TimeZone tz = date.getTimeZone();

		String timeZone = "Z00'00'";

		if (isNotNull(tz)) {

			final long offset = tz.getOffset(date.getTimeInMillis());

			final long timeInMinutes = Math.abs(offset)/MILLISECONDS_PER_SECOND/SECONDS_PER_MINUTE;

			final String signal = offset == 0 ? "Z" : (offset > 0 ? "+" : "-");

			long hours = 0;
			long minutes = 0;

			if (timeInMinutes > MINUTES_PER_HOUR) {

				hours = timeInMinutes / MINUTES_PER_HOUR;
				minutes = timeInMinutes % MINUTES_PER_HOUR;

			} else {
				minutes = timeInMinutes;
			}

			timeZone = format("%1$s%2$02d'%3$02d'", signal, hours, minutes);
		}

		return timeZone;
	}
	
}
