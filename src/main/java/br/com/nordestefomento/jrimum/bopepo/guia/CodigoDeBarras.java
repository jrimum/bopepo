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
 * Created at: 30/03/2008 - 18:04:23
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
 * Criado em: 30/03/2008 - 18:04:23
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.guia;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.bopepo.campolivre.CampoLivre;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;
import br.com.nordestefomento.jrimum.utilix.AbstractLineOfFields;
import br.com.nordestefomento.jrimum.utilix.BancoUtil;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.BoletoCodigoDeBarrasDV;

/**
 * 
 * É um número único para cada Guia composto dos seguintes campos:
 * <br/><br/>
 * 
 * <table border="1" cellpadding="1" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="85%" id="composição"> 
 * <thead>
 * <tr>
 * 		<th>Posição</th>
 * 		<th>Tamanho</th>
 * 		<th>Conteúdo</th>
 * 		<th>Descricao</th>
 * </tr>
 * </thead>
 * <tr>
 * 		<td>01-01</td>
 * 		<td>1</td>
 * 		<td>Identificação do Produto</td>
 * 		<td>Constante "8" para identificar arrecadação</td>
 * </tr>
 * <tr>
 * 		<td>02-02 </td>
 * 		<td>1 </td>
 * 		<td>Identificação do Segmento</td>
 * 		<td>
 * 			Identificará o segmento e a forma de identificação da Empresa/Órgão: <br/> 
 * 			1. Prefeituras; <br/> 
 * 			2. Saneamento;  <br/> 
 * 			3. Energia Elétrica e Gás; <br/>  
 * 			4. Telecomunicações;  <br/> 
 * 			5. Órgãos Governamentais; <br/>  
 * 			6. Carnes e Assemelhados ou demais  <br/>  
 * 			Empresas / Órgãos que serão identificadas através do CNPJ. <br/>  
 * 			7. Multas de trânsito  <br/> 
 * 			9. Uso exclusivo do banco  <br/> 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>03-03 </td>
 * 		<td>1</td>
 * 		<td>Identificação do valor real ou referência</td>
 * 		<td>
 * 
 * 			Este campo será: <br/>  <br/>
 * 			<strong>
 * 			“6”- Valor   a   ser  cobrado   efetivamente   em   reais
 * 			</strong>  <br/>
 * 			 com  dígito verificador calculado pelo módulo 10  na quarta  
 * 			posição do Código de  Barras e  valor  com    11 posições (versão 
 * 			2 e posteriores) sem qualquer alteração; <br/> <br/>
 * 			 
 * 			<strong>
 * 			“7”-  Quantidade   de  moeda 
 * 			</strong> <br/>  
 * 			Zeros – somente na impossibilidade de utilizar o valor;  <br/>
 * 			Valor a   ser  reajustado   por  um índice com  dígito verificador 
 * 			calculado pelo módulo 10   na  quarta 
 * 			posição do Código de Barras e valor com 11 posições  (versão 2 e 
 * 			posteriores).  <br/> <br/>
 * 			 
 * 			<strong>
 * 			“8” – Valor a ser cobrado efetivamente em reais
 * 			</strong><br/>
 * 			com  dígito verificador  calculado pelo módulo 11  na quarta  
 * 			posição do Código de  Barras e  valor  com 11 posições (versão 2 
 * 			e posteriores) sem qualquer alteração.  <br/><br/>
 * 			 
 * 			<strong>
 * 			“9” – Quantidade de moeda 
 *			</strong> <br/>
 * 			Zeros – somente na impossibilidade de utilizar o valor; 
 * 			Valor a   ser  reajustado   por  um índice 
 * 			 
 * 			com   dígito  .verificador calculado pelo módulo 11   na  quarta 
 * 			posição do Código de Barras e valor com 11 posições  (versão 2 e 
 * 			posteriores).  <br/>
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>04-04</td>
 * 		<td>1</td>
 * 		<td>Dígito verificador geral (módulo 10 ou 11)</td>
 * 		<td>   Dígito de auto conferência dos dados contidos  no Código de Barras.</td>
 * </tr>
 * <tr>
 * 		<td>05–15</td>
 * 		<td>11</td>
 * 		<td>Valor</td>
 * 		<td>
 * 			Se o campo “03 – Código de Moeda” indicar valor efetivo, este campo 
 * 			deverá conter o valor a ser cobrado. <br/><br/>

 * 			Se o campo “03 - Código de Moeda” indicado valor de referência, neste 
 * 			campo poderá conter uma quantidade de moeda, zeros, ou um valor a ser 
 * 			reajustado por um índice, etc... 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>16–19 <br/><br/> ou <br/><br/> 16-23</td>
 * 		<td>4 <br/><br/> ou <br/><br/> 8</td>
 * 		<td>
 * 				Identificação da Empresa/Órgão <br/><br/>
 * 				<CENTER>
 * 				Codigo Banco Febraban (4)  <br/> ou <br/>CNPJ Orgão Recebedor(8)
 * 				</CENTER>
 * 		</td>
 * 		<td>
 * 			O campo identificação da Empresa/Órgão terá uma codificação especial 
 * 			para cada segmento. <br/><br/>
 * 		 
 * 			Será um código de quatro posições atribuído e controlado pela Febraban (posições 16 a 19), 
 * 			ou as primeiras oito posições do cadastro geral de contribuintes do 
 * 			Ministério da Fazenda - CNPJ (posições 16 a 23). <br/><br/>
 * 		 
 * 			É através desta informação que o banco identificará a quem repassar as 
 * 			informações e o crédito. <br/><br/>  
 * 		 
 * 			Se for utilizado o CNPJ para identificar a Empresa/Órgão (posições 16 a 23), haverá uma 
 * 			redução no seu campo livre que passará a conter 21 posições (posições 24 a 44). <br/><br/> 
 * 		 
 * 			No caso de utilização do Segmento 9, este campo deverá conter o código 
 * 			de compensação do mesmo, com quatro dígitos (posições 16 a 29). Neste
 * 			caso o campo livre permanece com o tamanho padrão (posições 20 a 44)<br/><br/> 
 * 		 
 * 			Cada banco definirá a forma de identificação da empresa a partir da 1ª 
 * 			posição do campo livre (20 ou 24, de acordo com o que já foi explanado). 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>20–44 <br/><br/> ou <br/><br/> 24-44</td>
 * 		<td>25 <br/><br/> ou <br/><br/> 21</td>
 * 		<td> Campo livre de utilização da Empresa/Órgão</td>
 * 		<td>
 * 			Este campo é de uso exclusivo da Empresa/Órgão e será devolvido 
 * 			inalterado. <br/><br/>
 * 			 
 * 			Se existir data de vencimento no campo livre, ela deverá vir em primeiro 
 * 			lugar e em formato AAAAMMDD. 
 * 		</td>
 * </tr>
 * </table>
 * 
 * 
 * @author Misael Barreto 
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public final class CodigoDeBarras extends AbstractLineOfFields{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2585072841078547723L;

	
	public CodigoDeBarras(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
		// TODO Auto-generated constructor stub
	}
}
