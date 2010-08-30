package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * Campo Livre para boletos de Cobrança rápida e sem Registro SICOB - Nosso
 * Número 11 posições.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 */
class CLCaixaEconomicaFederalSICOB extends AbstractCLCaixaEconomicaFederal {
	
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
	 * 
	 * @param titulo - Título com as informações para geração do campo livre
	 */
	CLCaixaEconomicaFederalSICOB(Titulo titulo) {
		
		super(FIELDS_LENGTH);
		
		Objects.checkNotNull(titulo.getParametrosBancarios(), "Parâmetros bancários necessários [titulo.getParametrosBancarios()==null]!");
		
		ContaBancaria conta = titulo.getContaBancaria();
		
		if(conta.getCarteira().isSemRegistro()){
		
			this.add(new Field<Integer>(82, 2));
			this.add(new Field<String>(titulo.getNossoNumero(), 8));
			
		}else{
			
			this.add(new Field<Integer>(9, 1));
			this.add(new Field<String>(titulo.getNossoNumero(), 9));
			
		}
	
		this.add(new Field<Integer>(conta.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		
		if(titulo.getParametrosBancarios().contemComNome("CodigoOperacao")){
			
			Integer cnpv = titulo.getParametrosBancarios().getValor("CodigoOperacao");
		
			Objects.checkNotNull(titulo.getParametrosBancarios(), "Parâmetro bancário código operação inválido [CodigoOperacao==null]!");
				
			this.add(new Field<Integer>(cnpv, 3, Filler.ZERO_LEFT));
			
			this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 8, Filler.ZERO_LEFT));
			
		}else{
			
			throw new CampoLivreException("Parâmetro bancário código operação (\"CodigoOperacao\") não encontrado!");
		}
		
	}
}
