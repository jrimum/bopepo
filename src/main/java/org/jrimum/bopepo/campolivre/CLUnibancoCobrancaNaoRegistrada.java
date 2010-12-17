package org.jrimum.bopepo.campolivre;

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

class CLUnibancoCobrancaNaoRegistrada extends AbstractCLUnibanco {

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
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco Unibanco
	 *   que tenha o tipo de cobrança não registrada.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLUnibancoCobrancaNaoRegistrada(Titulo titulo) {
		super(FIELDS_LENGTH);

		ContaBancaria conta = titulo.getContaBancaria();
		
		Objects.checkNotNull(conta,"Conta Bancária NULA!");
		Objects.checkNotNull(conta.getNumeroDaConta(),"Número da Conta Bancária NULO!");
		Objects.checkNotNull(conta.getNumeroDaConta().getCodigoDaConta(),"Código da Conta Bancária NULO!");
		Objects.checkNotNull(conta.getNumeroDaConta().getDigitoDaConta(),"Dígito da Conta Bancária NULO!");
		Objects.checkNotNull(titulo.getNossoNumero(),"Nosso Número NULO!");
		
		this.add(new Field<Integer>(CODIGO_TRANSACAO, 1));

		if(conta.getNumeroDaConta().getCodigoDaConta() > 0){
			
			this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 6, Filler.ZERO_LEFT));
			
		}else{
			throw new CampoLivreException(new IllegalArgumentException("Conta bancária com valor inválido, a conta deve ser um número inteiro positivo, e não: "+conta.getNumeroDaConta().getCodigoDaConta()));
		}
		
		if(StringUtils.isNumeric(conta.getNumeroDaConta().getDigitoDaConta())){
			
			Integer digitoDaConta = Integer.valueOf(conta.getNumeroDaConta().getDigitoDaConta());  
			
			if(digitoDaConta >= 0){
				
				this.add(new Field<Integer>(Integer.valueOf(digitoDaConta), 1));
			}else{
				
				throw new CampoLivreException(new IllegalArgumentException("O dígito da conta deve ser um número inteiro não-negativo, e não: ["+conta.getNumeroDaConta().getDigitoDaConta()+"]"));
			}
			
		}else{
			
			throw new CampoLivreException(new IllegalArgumentException("O dígito da conta deve ser numérico, e não: ["+conta.getNumeroDaConta().getDigitoDaConta()+"]"));
		}
		
		this.add(new Field<Integer>(RESERVADO, 2, Filler.ZERO_LEFT));
		
		if(StringUtils.isNumeric(titulo.getNossoNumero())){
			
			if(Long.valueOf(Strings.removeStartWithZeros(titulo.getNossoNumero()))>0){
				
				this.add(new Field<String>(titulo.getNossoNumero(), 14,Filler.ZERO_LEFT));
				
			}else{
				
				throw new CampoLivreException(new IllegalArgumentException("O campo (nosso número) do título deve ser um número inteiro positivo, e não: ["+titulo.getNossoNumero()+"]"));
			}
		}else{
			
			throw new CampoLivreException(new IllegalArgumentException("O campo (nosso número) do título deve ser numérico, e não: ["+titulo.getNossoNumero()+"]"));			
		}
		
		this.add(new Field<String>(calculeDigitoEmModulo11(titulo.getNossoNumero()), 1));			
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
