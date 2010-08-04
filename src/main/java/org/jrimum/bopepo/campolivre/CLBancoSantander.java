package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

public class CLBancoSantander extends AbstractCLSantander implements CampoLivre{

	/**
	 * 
	 */
	private static final long serialVersionUID = -412221524249334574L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 5;
	
	private static final Integer CONSTANTE = Integer.valueOf(9);  
	
	CLBancoSantander(Titulo titulo) {
		super(FIELDS_LENGTH, STRING_LENGTH);
		
		ContaBancaria conta = titulo.getContaBancaria();
		String nossoNumero = titulo.getNossoNumero();
		
		this.add(new Field<Integer>(CONSTANTE, 1));
		this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 8, Filler.ZERO_LEFT));
		this.add(new Field<String>(nossoNumero, 13, Filler.ZERO_LEFT));
		///IOS OU ZERO
		if (Objects.isNotNull(titulo.getParametrosBancarios())
				&& Objects.isNotNull(titulo.getParametrosBancarios().getValor("IOS"))) {
			this.add(new Field<Integer>((Integer) titulo.getParametrosBancarios().getValor("IOS"), 1));
		}else{
			this.add(new Field<Integer>(0, 1));
		}
		//Tipo de Modalidade Carteira
		this.add(new Field<Integer>(conta.getCarteira().getCodigo(), 3, Filler.ZERO_LEFT));
	}
}
