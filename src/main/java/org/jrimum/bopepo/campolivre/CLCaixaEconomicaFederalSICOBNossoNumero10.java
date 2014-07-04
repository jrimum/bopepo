package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;
import static org.jrimum.bopepo.parametro.ParametroCaixaEconomicaFederal.CODIGO_OPERACAO;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Exceptions;
import org.jrimum.utilix.Objects;

/**
 * <p>
 * O campo livre da Caixa Econômica Federal para cobrança simples (CS), rápida(CR) e sem registro (SR)
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
 * <td style="text-align:left;padding-left:10">
 * Nosso Número no padrão de uma das
 * três cobranças (simples, rápida ou sem registro)
 * 
 * <p>Exemplos:<br/>
 * Cobrança Simples - Nosso número inicia com 3. Ex: Carteira 11<br/>
 * Cobrança Rápida - Nosso número inicia com 9. Ex: Carteira 12 <br/>
 * Cobrança Sem Registro - Nosso número inicia com 80, 81 ou 82. Ex: Carteira 14.
 * </p>
 * 
 * </td>
 * </tr>
 * <tr>
 * <td>30-33</td>
 * <td>4</td>
 * <td>9(4)</td>
 * <td style="text-align:left;padding-left:10">Código da Agência</td>
 * <td style="text-align:left;padding-left:10">Código da Agência Cedente</td>
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
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>

 * @see <a href="http://www1.caixa.gov.br/download/asp/download.asp?subCategId=198&CategId=65&subCateglayout=Manuais&Categlayout=Cobran%C3%A7a%20Caixa%20%E2%80%93%20SICOB#aba_voce">Manuais SICOB - Caixa</a>
 * @see <a href="http://downloads.caixa.gov.br/_arquivos/cobrcaixasicob/manuaissicob/ESPCODBARR_SICOB.pdf">Especificação código barras com nosso número de 11 posições</a>
 * @see <a href="http://downloads.caixa.gov.br/_arquivos/cobrcaixasicob/manuaissicob/ESPCODBARBLOQCOBRANREGIST_16POSICOES.pdf">Leiaute de Arquivo Eletrônico Padrão CNAB 240 - Cobrança Bancária CAIXA - SICOB</a>
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
		
		// TODO: Testar checkPadraoNossoNumeroPorCodigoDaCarteira;
		/*
		Integer codigoDaCarteira = titulo.getContaBancaria().getCarteira().getCodigo(); 
		if (Objects.isNotNull(codigoDaCarteira)) {
			checkPadraoNossoNumeroPorCodigoDaCarteira(titulo.getNossoNumero(), codigoDaCarteira);
		}
		*/
		
		this.add(new FixedField<String>(titulo.getNossoNumero(), 10));
	
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), 4, Fillers.ZERO_LEFT));
		
		if(titulo.getParametrosBancarios().contemComNome(CODIGO_OPERACAO)){
			
			Integer cnpv = titulo.getParametrosBancarios().getValor(CODIGO_OPERACAO);
		
			Objects.checkNotNull(titulo.getParametrosBancarios(), "Parâmetro bancário código operação inválido [CodigoOperacao==null]!");
				
			this.add(new FixedField<Integer>(cnpv, 3, Fillers.ZERO_LEFT));
			
			this.add(new FixedField<Integer>(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 8, Fillers.ZERO_LEFT));
			
		}else{
			
			throw new CampoLivreException("Parâmetro bancário código operação (\"CodigoOperacao\") não encontrado!");
		}
		
	}
	
	/**
	 * <p>
	 * Verifica se o nosso número do título começa com 3 (identificador da
	 * Carteira Simples), 9 (identificador da Carteira Rápida) ou 80, 81 ou 82
	 * (que são identificadores da Carteira Sem Registro); Caso contrário gera
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * <p>
	 * Padrões aceitos de 10 dígitos:
	 * <ul>
	 * <li>3NNNNNNNNN (a ser validado com os colegas da Neogrid)</li>
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
		if(!nn.startsWith("3") && !nn.startsWith("9") && !nn.startsWith("80") && !nn.startsWith("81") && !nn.startsWith("82")){
			Exceptions.throwIllegalArgumentException(format("Para a cobrança SICOB o nosso número [%s] deve começar com 3 que é o identificador da \"carteira siples\" [3NNNNNNNNN] ou 9 que é o identificador da \"carteira rápida\" [9NNNNNNNNN] ou 80, 81 e 82 para \"carteira sem registro\" [82NNNNNNNN]!", nn));
		}
	}
	
	/**
	 * <p>
	 * Verifica se o nosso número informado tem o padrão esperado de acordo com
	 * o código da carteira informada.
	 * </p>
	 * <p>
	 * Obs: Como não há uma garantia documental de que todo nosso número que
	 * começa com "3" pertencerá <u>somente</u> a carteira 11, então o mais
	 * coerente é não amarrar o início do nosso número a determinada carteira,
	 * mas sim o contrário, ou seja, amarrar que toda carteira 11 tem de ter o
	 * nosso número iniciando com 3". O mesmo raciocíno serve para as outras
	 * carteiras.
	 * </p>
	 * 
	 * @param nossoNumero
	 *            - Nosso Número
	 * @param codigoDaCarteira
	 *            - Código da carteira
	 */
	private void checkPadraoNossoNumeroPorCodigoDaCarteira(String nossoNumero, Integer codigoDaCarteira){
		switch (codigoDaCarteira) {
		case 11:
			if(!nossoNumero.startsWith("3")){
				Exceptions.throwIllegalArgumentException(format("Para a cobrança SICOB, carteira 11 (cobrança simples), o nosso número [%s] deve começar com 3!", nossoNumero));
			}
			break;

		case 12:
			if(!nossoNumero.startsWith("9")){
				Exceptions.throwIllegalArgumentException(format("Para a cobrança SICOB, carteira 12 (cobrança rápida), o nosso número [%s] deve começar com 9!", nossoNumero));
			}
			break;
					
		case 14:
			if(!nossoNumero.startsWith("80") && !nossoNumero.startsWith("81") && !nossoNumero.startsWith("82")){
				Exceptions.throwIllegalArgumentException(format("Para a cobrança SICOB, carteira 14 (cobrança sem registro), o nosso número [%s] deve começar com 80, 81 ou 82!", nossoNumero));
			}
			break;
		}
	}
	
	
	@Override
	protected void addFields(Titulo titulo) {
		// TODO IMPLEMENTAR
		Exceptions.throwUnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}

	@Override
	protected void checkValues(Titulo titulo) {
		// TODO IMPLEMENTAR
		Exceptions.throwUnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}
}
