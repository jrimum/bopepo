package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Field;
import org.jrimum.utilix.Filler;

public class CLBancoRuralCobrancaRegistrada extends AbstractCLBancoRural{
	
	private static final long serialVersionUID = -5166628254198207874L;
	
	private static final Integer FIELDS_LENGTH = 7;
	
	private static final int TIPO_COBRANCA = 0;

	CLBancoRuralCobrancaRegistrada(Titulo titulo) {
		super(FIELDS_LENGTH, STRING_LENGTH);
		
		ContaBancaria conta = titulo.getContaBancaria();
			
		this.add( new Field<Integer>( TIPO_COBRANCA , 1 ) );
		this.add( new Field<Integer>( conta.getAgencia().getCodigo(), 3 , Filler.ZERO_LEFT ) );
		this.add( new Field<Integer>( conta.getNumeroDaConta().getCodigoDaConta(), 9, Filler.ZERO_LEFT ) );
		this.add( new Field<String>( conta.getNumeroDaConta().getDigitoDaConta(), 1 ) );
		this.add( new Field<String>( titulo.getNossoNumero(), 7 , Filler.ZERO_LEFT ) );
		this.add( new Field<String>( titulo.getDigitoDoNossoNumero(), 1 ) );
		this.add( new Field<Integer>( 0, 3, Filler.ZERO_LEFT) );

	}

}
