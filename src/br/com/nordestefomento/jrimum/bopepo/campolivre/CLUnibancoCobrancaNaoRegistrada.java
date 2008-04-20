package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;

/**
 * 
 * <p>
 * Representação do campo livre usado para boletos com carteiras (<em>cobrança</em>)
 * sem registro.
 * </p>
 * 
 * <p>
 * Layout:<br />
 * <div align="center">
 * <p align="center">
 * <font face="Arial">Cobrança Especial (sem registro)</font>
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" >
 * <tr>
 * <td align="center" bgcolor="#C0C0C0"><strong><font face="Arial">Posição</font></strong></td>
 * <td bgcolor="#C0C0C0"><strong><font face="Arial">Campo Livre No Código De
 * Barras (20 a 44)</font></strong></td>
 * <tr>
 * <td align="center"><font face="Arial">20</font></td>
 * 
 * <td><font face="Arial">Código da transação = 5</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">21 a 26</font></td>
 * <td><font face="Arial">Número do Cliente (Espécie de conta)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">27</font></td>
 * <td><font face="Arial">Dígito Verificador do Número do Cliente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">28 a 29</font></td>
 * <td><font face="Arial">zeros</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">30 a 43</font></td>
 * <td><font face="Arial">Referência do Cliente (Nosso Número Gerado Pelo
 * Cliente)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">44</font></td>
 * <td><font face="Arial">Dígito Verificador da Referência do Cliente</font></td>
 * </tr>
 * </table> </div>
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class CLUnibancoCobrancaNaoRegistrada extends ACLUnibanco {

	/**
	 * 
	 */
	private static final long serialVersionUID = 487906631678160993L;

	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;

	private static final Integer CODIGO_TRANSACAO = 5;

	private static final Integer RESERVADO = 0;

	/**
	 * @param fieldsLength
	 * @param stringLength
	 */
	protected CLUnibancoCobrancaNaoRegistrada(Integer fieldsLength,
			Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	/**
	 * @param titulo
	 * @return
	 */
	static ICampoLivre getInstance(Titulo titulo) {

		ACampoLivre aCLUnibanco = new CLUnibancoCobrancaNaoRegistrada(
				FIELDS_LENGTH, STRING_LENGTH);

		// TODO Código em teste
		ContaBancaria conta = titulo.getCedente().getContasBancarias()
				.iterator().next();

		aCLUnibanco.add(new Field<Integer>(CODIGO_TRANSACAO, 1));

		aCLUnibanco.add(new Field<Integer>(conta.getNumeroDaConta()
				.getCodigoDaConta(), 6, Filler.ZERO_LEFT));

		aCLUnibanco.add(new Field<Integer>(Integer.valueOf(conta
				.getNumeroDaConta().getDigitoDaConta()), 1));

		aCLUnibanco.add(new Field<Integer>(RESERVADO, 2, Filler.ZERO_LEFT));

		aCLUnibanco.add(new Field<String>(titulo.getNossoNumero(), 14,
				Filler.ZERO_LEFT));

		aCLUnibanco.add(new Field<String>(calculeDigitoEmModulo11(titulo
				.getNossoNumero()), 1));

		return aCLUnibanco;
	}
	
	//TODO TESTES

}
