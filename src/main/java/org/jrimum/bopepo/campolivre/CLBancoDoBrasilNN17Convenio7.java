package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * 
 * O campo livre do Banco do Brasil com o nosso número de 17 dígitos e convênio
 * de 7 posições deve seguir esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr>
 * <thead>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th>
 * </thead>
 * </tr>
 * <tr>
 * <td>20-25</td>
 * <td>6</td>
 * <td>9(6)</td>
 * <td>Constante zeros = "000000"</td>
 * <td>Constante zeros = "000000"</td>
 * </tr>
 * <tr>
 * <td>26-42</td>
 * <td>17</td>
 * <td>9(17)</td>
 * <td>Nosso Número (sem dígito) composto pelo número do convênio fornecido pelo
 * Banco (CCCCCCC) e complemento do Nosso-Número, sem DV (NNNNNNNNNN)</td>
 * <td>Nosso Número (sem dígito) CCCCCCCNNNNNNNNNN</td>
 * </tr>
 * <tr>
 * <td>43-44</td>
 * <td>2</td>
 * <td>9(2)</td>
 * <td>Código da carteira</td>
 * <td>Tipo de Carteira/Modalidade de Cobrança</td>
 * </tr>
 * </table>
 * 
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLBancoDoBrasilNN17Convenio7 extends AbstractCLBancoDoBrasil {

	/**
	 * {@code serialVersionUID = 2347714390743602306L}
	 */
	private static final long serialVersionUID = 2347714390743602306L;

	/**
	 * Número de campos = 3.
	 */
	protected static final Integer FIELDS_LENGTH = Integer.valueOf(3);

	/**
	 * Tamanho do campo Constante = 6.
	 */
	protected static final Integer CONSTANT_LENGTH = Integer.valueOf(6);

	/**
	 * Valor do campo Constante = 0.
	 */
	protected static final Integer CONSTANT_VALUE = Integer.valueOf(0);

	/**
	 * Constante em forma de campo {@link #CONSTANT_VALUE} e
	 * {@link #CONSTANT_LENGTH}, valor escrito: "000000".
	 */
	private static final Field<Integer> CONSTANT_FIELD = new Field<Integer>(CONSTANT_VALUE, CONSTANT_LENGTH, Filler.ZERO_LEFT);

	/**
	 * Tamanho do campo Nosso Número = 17.
	 */
	protected static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(17);

	/**
	 * Tamanho do campo Carteira = 2.
	 */
	protected static final Integer CARTEIRA_LENGTH = Integer.valueOf(2);

	/**
	 * <p>
	 * Cria um campo livre instanciando o número de fields ({@code
	 * FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	protected CLBancoDoBrasilNN17Convenio7() {

		super(FIELDS_LENGTH);
	}

	@Override
	protected void checkValues(Titulo titulo) {

		checkNossoNumero(titulo);
		checkTamanhoDoNossoNumero(titulo, NN17);
		checkCarteiraNotNull(titulo);
		checkCodigoDaCarteira(titulo);
		checkCodigoDaCarteiraMenorOuIgualQue(titulo, 99);
	}

	@Override
	protected void addFields(Titulo titulo) {

		this.add(CONSTANT_FIELD);
		this.add(new Field<String>(titulo.getNossoNumero(),NOSSO_NUMERO_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getCarteira().getCodigo(), CARTEIRA_LENGTH, Filler.ZERO_LEFT));
	}

}
