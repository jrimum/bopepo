/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:17:40
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:17:40
 * 
 */

package org.jrimum.bopepo;

import static java.lang.String.format;
import static org.jrimum.utilix.Objects.isNull;
import static org.jrimum.utilix.text.DateFormat.DDMMYYYY_B;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.jrimum.utilix.Dates;

/**
 * <p>
 * Serviços utilitários do universo bancário, como por exemplo calcular o fator
 * de vencimento de boletos.</code>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class FatorDeVencimento{

	/**
	 *
	 */
	private static final long serialVersionUID = -9041865935492749542L;

	/**
	 * <p>
	 * Data base para o cálculo do fator de vencimento fixada em 07/10/1997 pela
	 * FEBRABAN.
	 * </p>
	 */
	private static final Calendar BASE_DO_FATOR_DE_VENCIMENTO = new GregorianCalendar(1997, Calendar.OCTOBER, 7);
	
	/**
	 * <p>
	 * Data base para o cálculo do fator de vencimento fixada em 07/10/1997 pela
	 * FEBRABAN.
	 * </p>
	 */
	private static final Date DATA_BASE_DO_FATOR_DE_VENCIMENTO = BASE_DO_FATOR_DE_VENCIMENTO.getTime();

	/**
	 *<p>
	 * Data máxima alcançada pelo fator de vencimento com base fixada em
	 * 07/10/1997.
	 * </p>
	 */
	private static final Date DATA_LIMITE_DO_FATOR_DE_VENCIMENTO = new GregorianCalendar(2025, Calendar.FEBRUARY, 21).getTime();

	/**
	 * <p>
	 * Calcula o fator de vencimento a partir da subtração entre a DATA DE
	 * VENCIMENTO de um título e a DATA BASE fixada em 07/10/1997.
	 * </p>
	 * 
	 * <p>
	 * O fator de vencimento nada mais é que um referencial numérico de 4
	 * dígitos que representa a quantidade de dias decorridos desde a data base
	 * (07/10/1997) até a data de vencimento do título. Ou seja, a diferença em
	 * dias entre duas datas.
	 * </p>
	 * 
	 * <p>
	 * Exemplos:
	 * </p>
	 * <ul type="circule"> <li>07/10/1997 (Fator = 0);</li> <li>03/07/2000
	 * (Fator = 1000);</li> <li>05/07/2000 (Fator = 1002);</li> <li>01/05/2002
	 * (Fator = 1667);</li> <li>21/02/2025 (Fator = 9999).</li> </ul>
	 * 
	 * <p>
	 * Funcionamento:
	 * </p>
	 * 
	 * <ul type="square"> <li>Caso a data de vencimento seja anterior a data
	 * base (Teoricamente fator negativo), uma exceção do tipo
	 * IllegalArgumentException será lançada.</li> <li>A data limite para o
	 * cálculo do fator de vencimento é 21/02/2025 (Fator de vencimento = 9999).
	 * Caso a data de vencimento seja posterior a data limite, uma exceção do
	 * tipo IllegalArgumentException será lançada.</li> </ul>
	 * 
	 * <p>
	 * <strong>ATENÇÃO</strong>, esse cálculo se refere a títulos em cobrança,
	 * ou melhor: BOLETOS. Desta forma, lembramos que a DATA BASE é uma norma da
	 * FEBRABAN. Essa norma diz que todos os boletos emitidos a partir de 1º de
	 * setembro de 2000 (primeiro dia útil = 03/07/2000 - SEGUNDA) devem seguir
	 * esta regra de cálculo para compor a informação de vencimento no código de
	 * barras. Portanto, boletos no padrão FEBRABAN quando capturados por
	 * sistemas da rede bancária permitem que se possa realizar a operação
	 * inversa, ou seja, adicionar à data base o fator de vencimento capturado.
	 * Obtendo então a data de vencimento deste boleto.
	 * </p>
	 * @param data
	 *            data de vencimento de um título
	 * @return fator de vencimento calculado
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */
	public static int toFator(Date data) throws IllegalArgumentException {

		if (isNull(data)) {
			
			throw new IllegalArgumentException("Impossível realizar o cálculo do fator de vencimento de uma data nula!");
			
		} else {
			
			Date dataTruncada = DateUtils.truncate(data, Calendar.DATE);
			
			checkIntervalo(dataTruncada);
				
			return (int) Dates.calculeDiferencaEmDias(DATA_BASE_DO_FATOR_DE_VENCIMENTO, dataTruncada);
		}
	}
	
	/**
	 * <p>
	 * Transforma um fator de vencimento em um objeto data da forma inversa
	 * descrita em {@link #toFator(Date)}.
	 * </p>
	 * 
	 * @param fator
	 *            - Número entre o intervalo (incluíndo) 0 e 9999
	 * @return Data do vencimento
	 * @throws IllegalArgumentException
	 *             Caso o {@code fator} < 0 ou {@code fator} > 9999
	 */
	public static Date toDate(int fator) throws IllegalArgumentException {
		
		checkIntervalo(fator);
		
		Calendar date = (Calendar) BASE_DO_FATOR_DE_VENCIMENTO.clone();
		
		date.add(Calendar.DAY_OF_YEAR, fator);
		
		return  DateUtils.truncate(date.getTime(), Calendar.DATE);
	}

	/**
	 * <p>
	 * Lança exceção caso a {@code dataVencimentoTruncada} esteja fora do
	 * intervalo entre a {@link #DATA_BASE_DO_FATOR_DE_VENCIMENTO} e a
	 * {@link #DATA_LIMITE_DO_FATOR_DE_VENCIMENTO}.
	 * </p>
	 * 
	 * @param dataVencimentoTruncada
	 *            data de vencimento truncada com {@code
	 *            DateUtils.truncate(date, Calendar.DATE)}
	 * @throws IllegalArgumentException
	 *             Caso a data esteja {@code dataVencimentoTruncada} esteja fora
	 *             do intervalo entre a
	 *             {@link #DATA_BASE_DO_FATOR_DE_VENCIMENTO} e a
	 *             {@link #DATA_LIMITE_DO_FATOR_DE_VENCIMENTO}
	 */
	private static void checkIntervalo(Date dataVencimentoTruncada) throws IllegalArgumentException {
		
		if(dataVencimentoTruncada.before(DATA_BASE_DO_FATOR_DE_VENCIMENTO)
				|| dataVencimentoTruncada.after(DATA_LIMITE_DO_FATOR_DE_VENCIMENTO)) {
			
			throw new IllegalArgumentException(
					format("Para o cálculo do fator de vencimento se faz necessário informar uma data entre %s e %s.",
					DDMMYYYY_B.format(DATA_BASE_DO_FATOR_DE_VENCIMENTO), DDMMYYYY_B.format(DATA_LIMITE_DO_FATOR_DE_VENCIMENTO)));
					
		}
	}
	
	/**
	 * <p>Lança exceção caso o {@code fator} estja fora do intervalo.</p> 
	 * 
	 * @param fatorDeVencimento - Número entre o intervalo (incluíndo) 0 e 9999
	 * @throws IllegalArgumentException Caso o {@code fator} < 0 ou {@code fator} > 9999
	 */
	private static void checkIntervalo(int fatorDeVencimento) throws IllegalArgumentException {

		if (fatorDeVencimento < 0 || fatorDeVencimento > 9999) {

			throw new IllegalArgumentException(
					"Impossível transformar em data um fator menor que zero! O fator de vencimento deve ser um número entre 0 e 9999.");
		}
	}
}
