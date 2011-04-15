package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * O campo livre da Caixa Econômica Federal para cobrança rápida e sem registro
 * - SICOB, deve seguir esta forma:
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="campolivre">
 * <thead bgcolor="#DEDEDE">
 * <tr>
 * <th>Posição</th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th>
 * </tr>
 * </thead> <tbody style="text-align:center">
 * <tr>
 * <td>20-29</td>
 * <td>10</td>
 * <td>9(10)</td>
 * <td style="text-align:left;padding-left:10px">Nosso Número</td>
 * <td style="text-align:left;padding-left:10">Nosso Número no padrão de uma das
 * duas cobranças (rápida ou sem registro)</td>
 * </tr>
 * <tr>
 * <td>30-33</td>
 * <td>4</td>
 * <td>9(4)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência</td>
 * <td style="text-align:left;padding-left:10">CNPJ da Agência Cedente</td>
 * </tr>
 * <tr>
 * <td>34-36</td>
 * <td>3</td>
 * <td>9(3)</td>
 * <td style="text-align:left;padding-left:10">Código da Operação</td>
 * <td style="text-align:left;padding-left:10">Operação Código</td>
 * </tr>
 * <tr>
 * <td>37-44</td>
 * <td>8</td>
 * <td>9(8)</td>
 * <td style="text-align:left;padding-left:10">Código do número da conta</td>
 * <td style="text-align:left;padding-left:10">Código fornecido pela Agência</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLCaixaEconomicaFederalSICOBNossoNumero10 extends AbstractCLCaixaEconomicaFederal {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 5585190685525441426L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;

	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco Caixa Econômica
	 *   Federal que tenha o serviço SINCO.
	 * </p>
	 * 
	 * @param titulo - Título com as informações para geração do campo livre
	 */
	CLCaixaEconomicaFederalSICOBNossoNumero10(Titulo titulo) {
		
		super(FIELDS_LENGTH);
		
		Objects.checkNotNull(titulo.getParametrosBancarios(), "Parâmetros bancários necessários [titulo.getParametrosBancarios()==null]!");
		checkPadraoNossoNumero(titulo.getNossoNumero());
		
		this.add(new Field<String>(titulo.getNossoNumero(), 10));
	
		this.add(new Field<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		
		if(titulo.getParametrosBancarios().contemComNome("CodigoOperacao")){
			
			Integer cnpv = titulo.getParametrosBancarios().getValor("CodigoOperacao");
		
			Objects.checkNotNull(titulo.getParametrosBancarios(), "Parâmetro bancário código operação inválido [CodigoOperacao==null]!");
				
			this.add(new Field<Integer>(cnpv, 3, Filler.ZERO_LEFT));
			
			this.add(new Field<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 8, Filler.ZERO_LEFT));
			
		}else{
			
			throw new CampoLivreException("Parâmetro bancário código operação (\"CodigoOperacao\") não encontrado!");
		}
		
	}
	
	/**
	 * <p>
	 * Verifica se o nosso número do título começa com 9 (identificador da
	 * Carteira Rápida) ou, 80, 81 ou 82 (que são identificadores da Carteira
	 * Sem Registro); Caso contrário gera uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * <p>
	 * Padrões aceitos de 10 dígitos:
	 * <ul>
	 * <li>9NNNNNNNNN</li>
	 * <li>80NNNNNNNN</li>
	 * <li>81NNNNNNNN</li>
	 * <li>82NNNNNNNN</li>
	 * </ul>
	 * </p>
	 * 
	 * @param nn
	 *            - Nosso Número
	 */
	private void checkPadraoNossoNumero(String nn){
		
		if(!nn.startsWith("9") && !nn.startsWith("80") && !nn.startsWith("81") && !nn.startsWith("82")){
			
			throw new IllegalArgumentException(format("Para a cobrança SICOB o nosso número [%s] deve começar com 9 que é o identificador da \"carteira rápida\" [9NNNNNNNNN] ou 80, 81 e 82 para \"carteira sem registro\" [82NNNNNNNN]!", nn));
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
