package org.jrimum.bopepo.campolivre;

import org.apache.commons.lang.StringUtils;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

public class CLBancoDeBrasilia extends AbstractCLBancoDeBrasilia{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6099168685425835517L;

	/**
	 * Número de campos = 8.
	 */
	private static final Integer FIELDS_LENGTH = Integer.valueOf(8);

	/**
	 * Tamanho do campo Agência = 3. 
	 */
	private static final Integer AGENCIA_LENGTH = Integer.valueOf(3);

	/**
	 * Tamanho do campo Conta = 7. 
	 */
	private static final Integer CONTA_LENGTH = Integer.valueOf(7);
	
	
	/**
	 * Tamanho do campo Nosso Número = 6. 
	 */
	private static final Integer NOSSO_NUMERO_LENGTH = Integer.valueOf(6);

	/**
	 * Tamanho do campo Dígito do Nosso Número = 1. 
	 */
	private static final Integer DIGITO_NOSSO_NUMERO_LENGTH = Integer.valueOf(1);
	
	/**
	 * Tamanho do campo Carteira = 1. 
	 */
	private static final Integer CARTEIRA_LENGTH = Integer.valueOf(1);
	
	
	/**
	 * Tamanho do campo "Campo Zerado" = 3. 
	 */
	private static final Integer CAMPO_ZERADO_LENGTH = Integer.valueOf(3);
	
	/**
	 * Valor do campo "Campo Zerado" =  0. 
	 */
	protected static final Integer CAMPO_ZERADO_VALUE = Integer.valueOf(0);
	
	/**
	 * Tamanho do campo Banco = 3. 
	 */
	protected static final Integer BANCO_LENGTH = Integer.valueOf(3);
	
	private String chaveAsbace;
	private Integer digito1;
	private Integer digito2;
	
	/**
	 * <p>
	 *   Cria um campo livre instanciando o número de fields ({@code FIELDS_LENGTH}) deste campo.
	 * </p>
	 * 
	 * @since 0.2
	 */
	protected CLBancoDeBrasilia() {
		super(FIELDS_LENGTH);
	}

	@Override
	protected void checkValues(Titulo titulo) {
		checkAgenciaNotNull(titulo);
		checkCodigoDaAgencia(titulo);
		checkCodigoDaAgenciaMenorOuIgualQue(titulo, 999);
		checkNumeroDaContaNotNull(titulo);
		checkCodigoDoNumeroDaConta(titulo);
		checkCodigoDoNumeroDaContaMenorOuIgualQue(titulo, 9999999);
		checkNossoNumero(titulo);
		checkCarteiraNotNull(titulo);
		checkCodigoDaCarteira(titulo);
		checkCodigoDaCarteiraMenorOuIgualQue(titulo, 3);
		
	}

	@Override
	protected void addFields(Titulo titulo) {
		
		String agencia = StringUtils.leftPad(String.valueOf(titulo.getContaBancaria().getAgencia().getCodigo()),AGENCIA_LENGTH,'0');
		String conta = StringUtils.leftPad(String.valueOf(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta()),CONTA_LENGTH,'0');
		String carteira = StringUtils.leftPad(String.valueOf(titulo.getContaBancaria().getCarteira().getCodigo()),CARTEIRA_LENGTH,'0');
		String nossoNumero = StringUtils.leftPad(titulo.getNossoNumero(),NOSSO_NUMERO_LENGTH,'0');
		String codigoBanco = StringUtils.leftPad(String.valueOf(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigo()),BANCO_LENGTH,'0');
		chaveAsbace = "000"+ agencia + conta + carteira + nossoNumero + codigoBanco;
		
		digito1 = getDigito1();
		digito2 = getDigito2();
		
		this.add(new Field<Integer>(CAMPO_ZERADO_VALUE, CAMPO_ZERADO_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), AGENCIA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), CONTA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getCarteira().getCodigo(), CARTEIRA_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<String>(titulo.getNossoNumero(), NOSSO_NUMERO_LENGTH, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigo(), BANCO_LENGTH, Filler.ZERO_LEFT)); 
		this.add(new Field<Integer>(digito1, DIGITO_NOSSO_NUMERO_LENGTH));
		this.add(new Field<Integer>(digito2, DIGITO_NOSSO_NUMERO_LENGTH));	
		
		titulo.setNossoNumero(carteira+nossoNumero+codigoBanco+String.valueOf(digito1)+String.valueOf(digito2));
	}
	
	private Integer getDigito1(){
		int somaP = 0;
		int peso = 2;
		for (int aux = 1; aux <= chaveAsbace.length(); aux ++){
			int numero = Integer.valueOf(String.valueOf(chaveAsbace.charAt(aux - 1)));
			int p = numero * peso;
			if (p > 9){
				somaP += (p - 9);
			}else{
				somaP+=p;
			}
			peso = peso == 2 ? 1 : 2;
		}
		return somaP % 10 == 0 ? 0 : 10 - (somaP % 10);
	}
	
	private Integer getDigito2(){
		String chave = chaveAsbace + String.valueOf(digito1) ;
		int peso = 7;
		int somaP = 0;
		for (int aux = 0 ; aux < chave.length(); aux ++){
			int numero = Integer.valueOf(String.valueOf(chave.charAt(aux)));
			somaP += numero * peso;
			peso--;
			if (peso < 2)
				peso = 7;
		}
		int resto = somaP % 11;
		if (resto == 0){
			return 0;
		}else if (resto > 1){
			return 11 - resto;
		}else{
			digito1 = digito1+1 == 10 ? 0 : digito1+1;
			return getDigito2();
		}
	}
	
}
