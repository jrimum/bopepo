package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * O campo livre do Banco Santander deve seguir esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr>
 * <thead>
 * <th >Posição</th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th>Conteúdo</th>
 * </thead>
 * </tr>
 * <tr>
 * <td >20-20</td>
 * <td >1</td>
 * <td >9(01)</td>
 * <td >Fixo "9"</td>
 * </tr>
 * <tr>
 * <td >21-27</td>
 * <td >7</td>
 * <td >9(7)</td>
 * <td >Conta do cedente padrão Santander</td>
 * </tr>
 * <tr>
 * <td >28-40</td>
 * <td >13</td>
 * <td >9(13)</td>
 * <td >
 * <p>
 * Nosso Número com zeros a esquerda. <strong>OBS:</strong> Caso o arquivo de
 * registro para os títulos seja de 400 bytes (CNAB)
 * </p>
 * <ul>
 * <li>Banco 353 (Banco Santander) - Utilizar somente 08 posições do Nosso Numero (07 posições +
 * DV), zerando os 05 primeiros dígitos</li>
 * <li>Banco 008 (Meridional do Brasil S/A) - Utilizar somente 09 posições do Nosso Numero (08 posições +
 * DV), zerando os 04 primeiros dígitos</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td >41-41</td>
 * <td >1</td>
 * <td >9(1)</td>
 * <td >
 * <p>
 * IOF – Seguradoras (Se 7% informar 7. Limitado a 9%)
 * </p>
 * <p>
 * <strong>Demais clientes usar 0 (zero)</strong>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td >42-44</td>
 * <td >3</td>
 * <td >9(3)</td>
 * <td >
 *    <ul>
 *       <li>101-Cobrança Simples Rápida COM Registro</li>
 *       <li>102- Cobrança simples – SEM Registro</li>
 *       <li>201- Penhor Rápida com Registro</li>
 *    </ul>
 * </td>
 * </tr>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
class CLBancoSantander extends AbstractCLSantander implements CampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = -412221524249334574L;

	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;

	/**
	 * Constante informada no manual.
	 */
	private static final Integer CONSTANTE = Integer.valueOf(9);

	/**
	 * Chave de pesquisa em parâmetros bancários para saber se o boelto deve
	 * usar IOF – Seguradoras: (Se 7% informar 7. Limitado a 9%); Demais
	 * clientes usar 0 (zero).
	 */
	private static final String IOF_SEGURADORA = "IOF_SEGURADORA";

	/**
	 * 101- Cobrança Simples Rápida COM Registro
	 */
	private static final int CARTEIRA_RAPIDA_COM_REGISTRO = 101;

	/**
	 * 201- Penhor Rápida com Registro
	 */
	private static final int CARTEIRA_RAPIDA_SEM_REGISTRO = 201;

	/**
	 * 102- Cobrança simples – SEM Registro
	 */
	private static final int CARTEIRA_SIMPLES_SEM_REGISTRO = 102;

	CLBancoSantander(Titulo titulo) {
		super(FIELDS_LENGTH);

		ContaBancaria conta = titulo.getContaBancaria();
		StringBuilder nossoNumero = new StringBuilder(titulo.getNossoNumero());
		nossoNumero.append(titulo.getDigitoDoNossoNumero());

		this.add(new Field<Integer>(CONSTANTE, 1));
		this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 6, Filler.ZERO_LEFT));
		this.add(new Field<String>(conta.getNumeroDaConta().getDigitoDaConta(), 1));
		
		this.add(new Field<String>(nossoNumero.toString(), 13, Filler.ZERO_LEFT));

		// IOF – Seguradoras

		if (Objects.isNotNull(titulo.getParametrosBancarios())
				&& Objects.isNotNull(titulo.getParametrosBancarios().getValor(
						IOF_SEGURADORA))) {

			this.add(new Field<Integer>((Integer) titulo
					.getParametrosBancarios().getValor(IOF_SEGURADORA), 1));

		} else {

			this.add(new Field<Integer>(0, 1));
		}

		// Tipo de Modalidade Carteira

		switch (conta.getCarteira().getCodigo()) {

		case CARTEIRA_RAPIDA_COM_REGISTRO:
		case CARTEIRA_RAPIDA_SEM_REGISTRO:
		case CARTEIRA_SIMPLES_SEM_REGISTRO:

			this.add(new Field<Integer>(conta.getCarteira().getCodigo(), 3,
					Filler.ZERO_LEFT));

			break;

		default:
			
			throw new IllegalArgumentException(String.format(
					"CARTEIRA [%s] NÃO SUPORTADA!", conta.getCarteira()
							.getCodigo()));
		}
	}
	
	@Override
	protected void addFields(Titulo titulo) {
		// TODO IMPLEMENTAR
		throw new UnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}

	@Override
	protected void checkValues(Titulo titulo) {
		// TODO IMPLEMENTAR
		throw new UnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}
}
