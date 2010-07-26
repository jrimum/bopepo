package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Field;
import org.jrimum.utilix.Filler;
import org.jrimum.utilix.ObjectUtil;

/**
 * <p>
 * Campo Livre para boletos de Cobrança rápida e sem Registro SICOB - Nosso
 * Número 11 posições.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 */
public class CLCaixaEconomicaFederalSICOB extends AbstractCLCaixaEconomicaFederal {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5585190685525441426L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 5;

	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco Caixa Econômica
	 *   Federal que tenha o serviço SINCO.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLCaixaEconomicaFederalSICOB(Titulo titulo) {
		
		super(FIELDS_LENGTH, STRING_LENGTH);
		
		ObjectUtil.checkNotNull(titulo.getParametrosBancarios(), "Parâmetros bancários necessários [titulo.getParametrosBancarios()==null]!");
		
		ContaBancaria conta = titulo.getContaBancaria();
		
		if(conta.getCarteira().isSemRegistro()){
		
			this.add(new Field<Integer>(82, 2));
			this.add(new Field<String>(titulo.getNossoNumero(), 8));
			
		}else{
			
			this.add(new Field<Integer>(9, 1));
			this.add(new Field<String>(titulo.getNossoNumero(), 9));
			
		}
	
		this.add(new Field<Integer>(conta.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		
		if(titulo.getParametrosBancarios().contemComNome("CNPV")){
			
			Integer cnpv = titulo.getParametrosBancarios().getValor("CNPV");
		
			ObjectUtil.checkNotNull(titulo.getParametrosBancarios(), "Parâmetro bancário CNPV inválido [CNPV==null]!");
				
			this.add(new Field<Integer>(cnpv, 3, Filler.ZERO_LEFT));
			
			this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 8, Filler.ZERO_LEFT));
			
		}else{
			
			throw new CampoLivreException("Parâmetro bancário CNPV (Operação Código Cedente) não encontrado!");
		}
		
	}
}
