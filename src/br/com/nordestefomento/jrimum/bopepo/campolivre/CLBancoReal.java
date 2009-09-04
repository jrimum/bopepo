/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:09:11
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:09:11
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.bank.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.StringUtil;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.EnumModulo;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.Modulo;

/**
 * <p><strong>*** COBRANÇA SEM REGISTRO ***</strong></p>
 * O campo livre do Banco Real deve seguir esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th>
 * </thead> </tr>
 * <tr>
 * <td>20-23</td>
 * <td>4</td>
 * <td>9(4)</td>
 * <td>Código da agência (sem dígito)</td>
 * <td>Agência - Código da agência do cedente</td>
 * </tr>
 * <tr>
 * <td>24-30</td>
 * <td>7</td>
 * <td>9(7) </td>
 * <td>Código da conta (sem dígito)</td>
 * <td>Conta - Número da conta do cedente</td>
 * </tr>
 * <tr>
 * <td>31-31</td>
 * <td>1</td>
 * <td>9(1) </td>
 * <td>Dígito verificador</td>
 * <td>Digitão - Dígito de cobrança</td>
 * </tr>
 * <tr>
 * <td>32-44</td>
 * <td>13</td>
 * <td>9(13) </td>
 * <td>Número do título(máximo de 13 posições numéricas)</td>
 * <td>Número do banco - Número do título no banco</td>
 * </tr>
 * </table>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
	
class CLBancoReal extends ACLBancoAbnAmroReal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5294809022535972391L;
	
	private static final Modulo modulo10 = new Modulo(EnumModulo.MODULO10);
	
	/**
	 * Tamanho deste campo.
	 */
	private static final Integer FIELDS_LENGTH = 4;
	
	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco Real.
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLBancoReal(Titulo titulo) {
		super(FIELDS_LENGTH, STRING_LENGTH);
		
		ContaBancaria conta = titulo.getContaBancaria();
		
		this.add(new Field<Integer>(conta.getAgencia().getCodigo(), 4, Filler.ZERO_LEFT));
		this.add(new Field<Integer>(conta.getNumeroDaConta().getCodigoDaConta(), 7, Filler.ZERO_LEFT));
		this.add(new Field<String>(calculeDigitoDaPosicao31(titulo.getNumeroDoDocumento(), conta.getAgencia().getCodigo(), conta.getNumeroDaConta().getCodigoDaConta()), 1, Filler.ZERO_LEFT));
		
		this.add(new Field<String>(StringUtil.eliminateSymbols(titulo.getNumeroDoDocumento()), 13, Filler.ZERO_LEFT));
		
	}
	
	/**
	 * <p>
	 * Calcula o Dígito da posição <tt>31</tt> deste campo livre (<code>CLBancoReal</code>).
	 * </p>
	 * 
	 * <p>
	 * No cálculo do dígito da posição 31 são considerados, para a obtenção do
	 * dígito, os dados <em><tt>{[NOSSO NÚMERO],[AGÊNCIA],[CONTA]}</tt></em> calculado pelos
	 * critérios do Módulo 10.
	 * </p>
	 * <h5>Exemplo:</h5>
	 * 
	 * <div align="center"> <table border="1" cellpadding="3" cellspacing="0">
	 * <tr>
	 * <td>Nosso Número</td>
	 * <td>1234567890123</td>
	 * </tr>
	 * <tr>
	 * <td>Agência</td>
	 * <td>4444</td>
	 * </tr>
	 * <tr>
	 * <td>Conta Corrente</td>
	 * <td>7777777</td>
	 * </tr>
	 * </table></div>
	 * 
	 * @param nossoNumero
	 * @param agencia
	 * @param contaCorrente
	 * @return Dígito verficador calculado
	 * 
	 * @see br.com.nordestefomento.jrimum.vallia.digitoverificador.Modulo
	 * 
	 * @since 0.2
	 */	
	private String calculeDigitoDaPosicao31(String nossoNumero, Integer agencia, Integer contaCorrente){
			
			StringBuilder formula = new StringBuilder();
			String dV = null;
			
			formula.append(Filler.ZERO_LEFT.fill(nossoNumero,13));
			formula.append(Filler.ZERO_LEFT.fill(agencia, 4));
			formula.append(Filler.ZERO_LEFT.fill(contaCorrente, 7));
			
			int restoDivisao = modulo10.calcule(formula.toString());
			
			int restoSubtracao = (10 - restoDivisao);
			
			if(restoSubtracao == 10)
				dV = "0";
			else
				dV = ""+restoSubtracao;
			
			return dV;
			
		}
	
}
