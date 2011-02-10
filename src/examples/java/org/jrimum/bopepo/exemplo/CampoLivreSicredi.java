/* 
 * Copyright 2008 JRimum Project
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
 * Created at: 28/06/2008 - 18:48:48
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 28/06/2008 - 18:48:48
 * 
 */

package org.jrimum.bopepo.exemplo;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;
import static org.jrimum.utilix.Objects.isNotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.CampoLivreException;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.AbstractLineOfFields;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;
import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

/**
 * <p>
 * O campo livre definido pela <a href="http://www.sicredi.com.br/">Sicredi</a>.
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Conteúdo</th>
 * </thead> </tr>
 * <tr>
 * <td >20-20</td>
 * <td >1</td>
 * <td >Código númerico correspondente ao tipo de cobrança: "3" - SICREDI</td>
 * </tr>
 * <tr>
 * <td >21-21</td>
 * <td >1</td>
 * <td >Código númerico correspondente ao tipo de carteira: "1" - carteira
 * simples</td>
 * </tr>
 * <tr>
 * <td >22-30</td>
 * <td >9</td>
 * <td >Nosso Número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >31-34</td>
 * <td >4</td>
 * <td >Cooperativa de crédito / agência cedente</td>
 * </tr>
 * <tr>
 * <td >35-36</td>
 * <td >2</td>
 * <td >Unidade de atendimento / posto da agênica cedente</td>
 * </tr>
 * <tr>
 * <td >37-41</td>
 * <td >5</td>
 * <td >Código do Cedente</td>
 * </tr>
 * <tr>
 * <td >42-42</td>
 * <td >1</td>
 * <td >Filler: 1 (um) quando o campo "valor do documento" diferente de 0
 * (zero), caso contrário zero.</td>
 * </tr>
 * <tr>
 * <td >43-43</td>
 * <td >1</td>
 * <td >Zero Fixo</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >Dígito verificador do campo livre calculado por módulo 11 com
 * aproveitamento total (resto igual a (0) zero ou (1) um o Dígito será (0)
 * zero)</td>
 * </tr>
 * </table>
 * 
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 */

@SuppressWarnings("serial")
public class CampoLivreSicredi extends AbstractLineOfFields implements CampoLivre {

	static Logger log = Logger.getLogger(CampoLivreSicredi.class);

	private static final Integer FIELDS_LENGTH = 9;

	private static final Integer STRING_LENGTH = 25;

	private static final Modulo modulo11 = new Modulo(TipoDeModulo.MODULO11);

	/**
	 * <p>
	 * Código númerico correspondente ao tipo de cobrança: "3" - SICREDI.
	 * </p>
	 */
	private static final String COBRANCA = "3";

	/**
	 * <p>
	 * Código númerico correspondente ao tipo de carteira: "1" - carteira
	 * simples.
	 * </p>
	 */
	private static final String CARTEIRA = "1";

	/**
	 * <p>
	 * Primeira posição do campo livre.
	 * </p>
	 */
	private static final Field<String> FIELD_COBRANCA = new Field<String>(
			COBRANCA, 1);

	/**
	 * <p>
	 * Segunda posição do campo livre.
	 * </p>
	 */
	private static final Field<String> FIELD_CARTEIRA = new Field<String>(
			CARTEIRA, 1);

	public CampoLivreSicredi(Titulo titulo) throws CampoLivreException {

		super(FIELDS_LENGTH, STRING_LENGTH);

		try {

			this.add(FIELD_COBRANCA);
			this.add(FIELD_CARTEIRA);

			this.add(new Field<String>(loadNossoNumero(titulo), 9,
					Filler.ZERO_LEFT));

			InnerCooperativaDeCredito cooperativa = loadCooperativaDeCredito(titulo
					.getContaBancaria().getAgencia());

			this
					.add(new Field<String>(cooperativa.codigo, 4,
							Filler.ZERO_LEFT));
			this.add(new Field<String>(cooperativa.posto, 2, Filler.ZERO_LEFT));

			this.add(new Field<String>(componhaCodigoDoCedente(titulo
					.getContaBancaria().getNumeroDaConta()), 5,
					Filler.ZERO_LEFT));

			if (titulo.getValor() != null
					&& titulo.getValor().doubleValue() > 0)
				this.add(new Field<String>("1", 1));
			else
				this.add(new Field<String>("0", 1));

			this.add(new Field<String>("0", 1));
			this.add(new Field<String>(calculeDigitoVerificador(), 1));

		} catch (Exception e) {
			throw new CampoLivreException(
					"Ocorreu um problema ao tentar gerar o campo livre Sicredi.",
					e);
		}
	}

	private CampoLivreSicredi(Integer fieldsLength, Integer stringLength) {
		super(FIELDS_LENGTH, STRING_LENGTH);

	}

	private String loadNossoNumero(Titulo titulo) {

		String nossoNumeroComposto = null;

		String nossoNumero = titulo.getNossoNumero();
		String dvNossoNumero = titulo.getDigitoDoNossoNumero();

//		if (isNotNull(nossoNumero, "Nosso Número")) {
		if (isNotNull(nossoNumero)) {
			if (isNotBlank(nossoNumero) && isNumeric(nossoNumero)) {

				if (nossoNumero.length() == 8)
					nossoNumeroComposto = nossoNumero;
				else
					new IllegalArgumentException(
							"Nosso número deve ter exatamente 8 dígitos: "
									+ nossoNumero);
			} else
				new IllegalArgumentException(
						"Nosso número deve conter somente números e não: "
								+ nossoNumero);
		}

//		if (isNotNull(dvNossoNumero, "Dígito Verificador do Nosso Número")) {
		if (isNotNull(dvNossoNumero)) {
			if (isNotBlank(dvNossoNumero) && isNumeric(dvNossoNumero)) {

				Integer dvNN = Integer.valueOf(dvNossoNumero);

				if (dvNN >= 0 && dvNN <= 9)
					nossoNumeroComposto += dvNN.toString();
				else
					new IllegalArgumentException(
							"O dígito Verificador do Nosso Número deve ser um número natural não-negativo de 0 a 9, e não: ["
									+ dvNN + "]");

			} else
				new IllegalArgumentException(
						"Nosso número deve conter somente números e não: "
								+ nossoNumero);
		}

		if (nossoNumeroComposto.length() != 9)
			throw new IllegalStateException("Nosso número ["
					+ nossoNumeroComposto
					+ "] com tamanho diferente da especificação (9)");

		return nossoNumeroComposto.toString();
	}

	InnerCooperativaDeCredito loadCooperativaDeCredito(Agencia agencia) {

		InnerCooperativaDeCredito cooperativa = null;

//		if (isNotNull(agencia.getCodigo(), "Número da Agência Sicredi")) {
		if (isNotNull(agencia.getCodigo())) {
			if (agencia.getCodigo() > 0) {
				if (String.valueOf(agencia.getCodigo()).length() <= 4) {

					cooperativa = new InnerCooperativaDeCredito();

					cooperativa.codigo = "" + agencia.getCodigo();

				} else
					new IllegalArgumentException(
							"Número da Agência Sicredi deve conter no máximo 4 dígitos (SEM O DIGITO VERIFICADOR) e não: "
									+ agencia.getCodigo());
			} else
				new IllegalArgumentException(
						"Número da Agência Sicredi com valor inválido: "
								+ agencia.getCodigo());
		}

//		if (isNotNull(agencia.getDigitoVerificador(), "Dígito da Agência Sicredi")) {
		if (isNotNull(agencia.getDigitoVerificador())) {
			if (StringUtils.isNumeric(agencia.getDigitoVerificador())) {

				if (String.valueOf(agencia.getDigitoVerificador()).length() <= 2) {

					Integer digitoDaAgencia = Integer.valueOf(agencia
							.getDigitoVerificador());

					if (digitoDaAgencia >= 0)
						cooperativa.posto = digitoDaAgencia.toString();
					else
						new IllegalArgumentException(
								"O dígito da Agência Sicredi deve ser um número natural não-negativo, e não: ["
										+ agencia.getDigitoVerificador() + "]");

				} else
					new IllegalArgumentException(
							"Dígito da Agência Sicredi deve conter no máximo 2 dígitos e não: "
									+ agencia.getCodigo());
			} else
				new IllegalArgumentException(
						"O dígito da Agência Sicredi deve ser numérico, e não: ["
								+ agencia.getDigitoVerificador() + "]");
		}

		return cooperativa;
	}

	String componhaCodigoDoCedente(NumeroDaConta conta) {// 5digitos sem dv

		final String msg = "<<<ATENÇÃO>>> O dígito da Conta/Código do Cedente Sicredi deve ser fornecido somente quando o número da (Conta/Código do Cedente) for composto de 1 a 4 dígitos, e não: ["
				+ conta.getDigitoDaConta() + "]";

		StringBuilder codigoDoCedente = new StringBuilder();

//		if (isNotNull(conta.getCodigoDaConta(), "Número da Conta/Código do Cedente Sicredi")) {
		if (isNotNull(conta.getCodigoDaConta())) {

			if (conta.getCodigoDaConta() > 0) {
				if (conta.getCodigoDaConta().toString().length() <= 5) {

					codigoDoCedente.append(conta.getCodigoDaConta().toString());

					if (conta.getCodigoDaConta().toString().length() < 5) {// ComDigito
						if (isNotBlank(conta.getDigitoDaConta())) {
							if (isNumeric(conta.getDigitoDaConta())) {

								Integer digitoDaConta = Integer.valueOf(conta
										.getDigitoDaConta());

								if (digitoDaConta >= 0)
									codigoDoCedente.append(digitoDaConta);
								else
									new IllegalArgumentException(
											"O dígito da Conta/Código do Cedente Sicredi deve ser um número natural não-negativo, e não: ["
													+ conta.getDigitoDaConta()
													+ "]");

							} else
								throw new CampoLivreException(
										new IllegalArgumentException(
												"O dígito da Conta/Código do Cedente Sicredi deve ser numérico, e não: ["
														+ conta
																.getDigitoDaConta()
														+ "]"));
						} else {
							System.out.println(msg);
							log.warn(msg);
						}
					}

				} else
					new IllegalArgumentException(
							"Número da Conta/Código do Cedente Sicredi deve conter no máximo 6 dígitos (SEM O DIGITO VERIFICADOR) e não: "
									+ conta.getCodigoDaConta());
			} else
				new IllegalArgumentException(
						"Número da Conta/Código do Cedente Sicredi com valor inválido: "
								+ conta.getCodigoDaConta());
		}

		return codigoDoCedente.toString();
	}

	private String calculeDigitoVerificador() {

		Integer dv = 0;

		this.setStringLength(STRING_LENGTH - 1);
		this.setFieldsLength(FIELDS_LENGTH - 1);

		String campoLivreSemDv = this.write();

		this.setFieldsLength(FIELDS_LENGTH);
		this.setStringLength(STRING_LENGTH);

		int resto = modulo11.calcule(campoLivreSemDv);

		if (resto != 0 && resto != 1) {

			dv = modulo11.valor() - resto;
		} else
			dv = resto;

		return "" + dv;
	}

	class InnerCooperativaDeCredito {

		String codigo;

		String posto;

	}
}
