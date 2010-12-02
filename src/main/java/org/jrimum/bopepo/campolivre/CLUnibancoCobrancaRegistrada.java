package org.jrimum.bopepo.campolivre;

import static org.jrimum.utilix.text.DateFormat.YYMMDD;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;
import org.jrimum.utilix.text.Strings;

/**
 * 
 * <p>
 * Representação do campo livre usado para boletos com carteiras (<em>cobrança</em>)
 * com registro.
 * </p>
 * 
 * <p>
 * Layout:<br />
 * <div align="center">
 * <p align="center">
 * <font face="Arial">Cobrança Direta (com registro)</font>
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" >
 * <tr>
 * <td align="center" bgcolor="#C0C0C0"><strong><font face="Arial">Posição</font></strong></td>
 * <td bgcolor="#C0C0C0"><strong><font face="Arial">Campo Livre No Código De
 * Barras (20 a 44)</font></strong></td>
 * <tr>
 * <td align="center"><font face="Arial">20 a 21</font></td>
 * <td><font face="Arial">Código da transação = 04</font></td>
 * 
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">22 a 27</font></td>
 * <td><font face="Arial">Data do Vencimento do Título (AAMMDD)</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">28 a 31</font></td>
 * 
 * <td><font face="Arial">Agência do Cedente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">32</font></td>
 * 
 * <td><font face="Arial">Dígito Verificador da Agência do Cedente</font></td>
 * </tr>
 * <tr>
 * <td align="center"><font face="Arial">33 a 43</font></td>
 * <td><font face="Arial">Nosso Número</font></td>
 * </tr>
 * <tr>
 * 
 * <td align="center"><font face="Arial">44</font></td>
 * <td><font face="Arial">Super Digito do Nosso Número (*)</font></td>
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

class CLUnibancoCobrancaRegistrada extends AbstractCLUnibanco {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2740172688796212239L;

	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;

	private static final String CODIGO_TRANSACAO = "04";
	
	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco Unibanco
	 *   que tenha o tipo de cobrança registrada.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLUnibancoCobrancaRegistrada(Titulo titulo) {
		super(FIELDS_LENGTH);
		
		ContaBancaria conta = titulo.getContaBancaria();
		
		Objects.checkNotNull(conta,"Conta Bancária NULA!");
		Objects.checkNotNull(titulo.getDataDoVencimento(), "Data de vencimento do título NULA!");
		Objects.checkNotNull(conta.getAgencia().getCodigo(), "Número da Agência Bancária NULO!");
		Objects.checkNotNull(conta.getAgencia().getDigitoVerificador(),"Dígito da Agência Bancária NULO!");
		Objects.checkNotNull(titulo.getNossoNumero(),"Nosso Número NULO!");
		
		this.add(new Field<String>(CODIGO_TRANSACAO, 2));
		this.add(new Field<Date>(titulo.getDataDoVencimento(), 6, YYMMDD.copy()));
			
		if(conta.getAgencia().getCodigo() > 0){
			
			this.add(new Field<Integer>(conta.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
			
		}else{
			
			throw new CampoLivreException(new IllegalArgumentException("Agência bancária com valor inválido, a agência deve ser um número inteiro positivo, e não: "+conta.getNumeroDaConta().getCodigoDaConta()));
		}
		
		
		if (StringUtils.isNumeric(conta.getAgencia().getDigitoVerificador())) {
			
			Integer digitoDaAgencia = Integer.valueOf(conta.getAgencia().getDigitoVerificador());  
			
			if(digitoDaAgencia>=0){
				
				this.add(new Field<Integer>(Integer.valueOf(digitoDaAgencia), 1));
			}else{
				
				throw new CampoLivreException(new IllegalArgumentException("O dígito da agência deve ser um número interio não-negativo, e não: ["+conta.getAgencia().getDigitoVerificador()+"]"));
			}
		}else{
			
			throw new CampoLivreException(new IllegalArgumentException("O dígito da agência deve ser numérico, e não: ["+conta.getAgencia().getDigitoVerificador()+"]"));
		}
		
		if(StringUtils.isNumeric(titulo.getNossoNumero())){
			
			if(Long.valueOf(Strings.removeStartWithZeros(titulo.getNossoNumero()))>0){
				
				this.add(new Field<String>(titulo.getNossoNumero(), 11,Filler.ZERO_LEFT));
			}else{
				
				throw new CampoLivreException(new IllegalArgumentException("O campo (nosso número) do título deve ser um número natural positivo, e não: ["+titulo.getNossoNumero()+"]"));
			}
		}else{
			
			throw new CampoLivreException(new IllegalArgumentException("O campo (nosso número) do título deve ser numérico, e não: ["+titulo.getNossoNumero()+"]"));
		}
		
		this.add(new Field<String>(calculeSuperDigito(titulo.getNossoNumero()), 1));
	}

	/**
	 * <p>
	 * Calcula o Super Dígito do Nosso Número.
	 * </p>
	 * 
	 * <p>
	 * Super dígito do “Nosso Número” [calculado com o MÓDULO 11 (de 2 a 9)]
	 * obtido utilizando-se os algarismos do “Nosso Número” acrescido do número
	 * 1 à esquerda = [1/NNNNNNNNNNN] e multiplicando-se a sequência obetem-se a
	 * soma dos produtos. Em seguida multiplicando-se novamente a soma por 10 e
	 * depois realizando-se o módulo 11.
	 * </p>
	 * 
	 * 
	 * @param nossoNumero
	 * 
	 * @return Dígito verficador calculado
	 * 
	 * @see #calculeDigitoEmModulo11(String)
	 * @see org.jrimum.vallia.digitoverificador.Modulo
	 * 
	 * @since 0.2
	 */
	private String calculeSuperDigito(String nossoNumero) {

		return calculeDigitoEmModulo11("1" + nossoNumero);
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
