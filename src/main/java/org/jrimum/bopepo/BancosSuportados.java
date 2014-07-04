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
 * Created at: 30/03/2008 - 19:08:39
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
 * Criado em: 30/03/2008 - 19:08:39
 * 
 */

package org.jrimum.bopepo;

import java.util.HashMap;
import java.util.Map;

import org.jrimum.domkee.comum.pessoa.id.cprf.CNPJ;
import org.jrimum.domkee.financeiro.banco.febraban.Banco;
import org.jrimum.domkee.financeiro.banco.febraban.CodigoDeCompensacaoBACEN;

/**
 * <p>
 * Enumeração dos bancos segundo o <a href="http://www.bcb.gov.br>Banco Central
 * do Brasil</a> que são suportados por este componente na tarefa de geração de
 * boletos.
 * </p>
 * 
 * <p>
 * Aqui se encontram todos os bancos sob a <a
 * href="http://www.bcb.gov.br/?RELINST">supervisão da BACEN</a> em
 * funcionamento no país e que possuem pelo menos uma implementação de
 * <code>ICampoLivre</code>.
 * </p>
 * 
 * <p>
 * A partir de um <code>EnumBanco</code> específico, como o
 * <code>BANCO_DO_BRASIL</code>, você pode solicitar um nova instância de um
 * banco representado por <code>IBanco</code> ou utilizar as costantes
 * enumeradas e não enumeradas como melhor for o caso.
 * </p>
 * 
 * <h5>EXEMPLOS:</h5>
 * 
 * <p>
 * Para uma nova instância do Banco do Brasil faça: <br />
 * <br />
 * <code>
 *   IBanco bancoDoBrasil = EnumBancos.BANCO_DO_BRASIL.newInstance();
 *   </code>
 * </p>
 * 
 * <p>
 * Para utilizar somento o código de compensação: <br />
 * <br />
 * <code>
 *   EnumBancos.BANCO_DO_BRASIL.getCodigoDeCompensacao();
 *   </code>
 * </p>
 * 
 * <p>
 * Para saber se um banco é suportado pelo componete, veja a lista antes
 * (LinkParaLista) ou faça: <br />
 * <br />
 * <code>
 *   EnumBancos.isSuportado(banco.getCodigoDeCompensacao)
 *   </code>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:samuelvalerio@gmail.com">Samuel Valério</a>
 * @author <a href="mailto:lukas.antunes@virtualsistemas.com.br">Lukas
 *         Antunes</a> - Colaborador com o banco Intermedium (077)
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a> -
 *         Colaborador com o banco Rural (453)
 * @author <a href="mailto:pporto@gmail.com">Paulo Porto</a> - 
 * 		   Colaborador com o Banco do Nordeste do Brasil (004).
 * @author <a href="mailto:fabianojustino@gmail.com">Fabiano Carrijo</a> - 
 * 		   Colaborador com o Banco Citibank (756).
 * @author <a href="mailto:contato@douglasramiro.com.br">Douglas Ramiro</a> - 
 * 		   Colaborador com o Banco de Brasília (070).
 * 
 * @see org.jrimum.bopepo.campolivre.CampoLivre
 * @see org.jrimum.domkee.financeiro.banco.Banco
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public enum BancosSuportados{

	/*
	 * <=====================================================================>
	 * Observe que toda a enumeração segue a ORDEM dos códigos de compensação.
	 * Caso queira modificar alguma coisa, leve sempre em consideração essa
	 * ORDEM.
	 * <=====================================================================>
	 */

	/**
	 * Tipo enumerado que representa o <strong>Banco do Brasil</strong>, código
	 * de compensação <strong><tt>001</tt></strong> <a
	 * href="http://www.bb.com.br">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_DO_BRASIL("001", "00000000000191", "BANCO DO BRASIL S.A.", "Banco do Brasil - Banco Múltiplo"),
		
	/**
	 * Tipo enumerado que representa o <strong>Banco do Nordeste do Brasil</strong>, código
	 * de compensação <strong><tt>004</tt></strong> (<a href="http://www.bnb.gov.br">site</a>).
	 * 
	 * @since 0.2-Helio
	 */	
	BANCO_DO_NORDESTE_DO_BRASIL("004","07237373000120", "BANCO DO NORDESTE DO BRASIL S.A.","Banco Múltiplo"),		
	
	/**
	 * Tipo enumerado que representa o Banestes, <strong>Banco do Estado do Espírito Santo</strong>,
	 * código de compensação <strong><tt>021</tt></strong>
	 *  <a href="http://www.banestes.com.br">
	 * site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_DO_ESTADO_DO_ESPIRITO_SANTO("021", "28127603000178", "BANCO DO ESTADO DO ESPIRITO SANTO S.A.", "Banco Múltiplo"),
			
	/**
	 * Tipo enumerado que representa o Santander <strong>Banco Santander
	 * (Brasil) S. A.</strong>, código de compensação <strong><tt>033</tt>
	 * </strong> <a href="http://www.santander.com.br"> site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_SANTANDER("033", "90400888000142", "BANCO SANTANDER (BRASIL) S. A.", "Banco Mútiplo"),

	/**
	 * Tipo enumerado que representa o Banrisul, Banco <strong>do Estado do Rio Grande do Sul</strong>,
	 * código de compensação <strong><tt>041</tt></strong> <a href="http://www.banrisul.com.br/">
	 * site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL("041", "92702067000196", "BANCO DO ESTADO DO RIO GRANDE DO SUL S.A.", "Banco Múltiplo"),
			
	/**
	 * Tipo enumerado que representa o Banco <strong>BRB - Banco de Brasília</strong>,
	 * código de compensação <strong><tt>070</tt></strong> <a
	 * href="http://www.brb.com.br">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_DE_BRASILIA("070","00000208000100","BRB- Banco de Brasília S.A.","Banco Múltiplo"),
	
	/**
	 * Tipo enumerado que representa o BANISA, Banco <strong>Intermedium</strong>,
	 * código de compensação <strong><tt>077</tt></strong> <a href="http://www.bancointermedium.com.br/">
	 * site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_INTEMEDIUM("077", "00416968000101", "BANCO INTERMEDIUM S.A.", "Banco Múltiplo"),

	/**
	 * Tipo enumerado que representa o CECRED, <strong>Cooperativa Central de Crédito Urbano</strong>,
	 * código de compensação <strong><tt>085</tt></strong> <a href="http://www.cecred.coop.br/">
	 * site</a>.
	 * 
	 * @since 0.2
	 */
	CECRED("085", "05.463.212/0001-29", "COOPERATIVA CENTRAL DE CRÉDITO URBANO - CECRED", "Cooperativa de Crédito"),
	
	/**
	 * Tipo enumerado que representa o Banco <strong>Caixa Econômica Federal</strong>,
	 * código de compensação <strong><tt>104</tt></strong> <a
	 * href="http://www.caixa.gov.br">site</a>.
	 * 
	 * @since 0.2
	 */
	CAIXA_ECONOMICA_FEDERAL("104", "00360305000104", "CAIXA ECONOMICA FEDERAL", "Caixa Econômica Federal"),

	/**
	 * Tipo enumerado que representa o Banco <strong>Bradesco</strong>, código
	 * de compensação <strong><tt>237</tt></strong> <a
	 * href="http://www.bradesco.com.br">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_BRADESCO("237", "60746948000112", "BANCO BRADESCO S.A.", "Banco Múltiplo"),

	/**
	 * Tipo enumerado que representa o <strong>Banco Itaú</strong>, código de
	 * compensação <strong><tt>341</tt></strong> <a
	 * href="http://www.	.com.br">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_ITAU("341", "60701190000104", "BANCO ITAÚ S.A.", "Banco Múltiplo"),

	/**
	 * Tipo enumerado que representa o <strong>Banco ABN AMRO Real</strong>
	 * (<a href="http://www.bancoreal.com.br">http://www.bancoreal.com.br</a>),
	 * código de compensação <strong><tt>356</tt></strong>. <br/>
	 * <p>
	 * Obs: Os bancos <strong>Sudameris</strong> e <strong>Bandepe</strong>
	 * foram incorporados ao Banco Real, portanto para gerar boletos bancários
	 * dos bancos citados utilize este tipo enumerado.
	 * </p>
	 * @since 0.2
	 */
	BANCO_ABN_AMRO_REAL("356", "33066408000115", "BANCO ABN AMRO REAL S.A.", "Banco Múltiplo"),

	/**
	 * Tipo enumerado que representa o <strong>Banco Mercantil do Brasil</strong>
	 * (<a href="http://www.mercantildobrasil.com.br">http://www.mercantildobrasil.com.br</a>),
	 * código de compensação <strong><tt>389</tt></strong>.
	 * @since 0.2
	 */
	MERCANTIL_DO_BRASIL("389", "17184037000110", "BANCO MERCANTIL DO BRASIL S.A.", "Banco Múltiplo"),
			
	/**
	 * Tipo enumerado que representa o <strong>HSBC</strong>, código de
	 * compensação <strong><tt>399</tt></strong> <a
	 * href="http://www.hsbc.com.br">site</a>.
	 * 
	 * @since 0.2
	 */
	HSBC("399", "01701201000189", "HSBC BANK BRASIL S.A.", "Banco Múltiplo"),

	/**
	 * Tipo enumerado que representa o <strong>Unibanco</strong>, código de
	 * compensação <strong><tt>409</tt></strong> <a
	 * href="http://www.unibanco.com.br">site</a>.
	 * 
	 * @since 0.2
	 */
	UNIBANCO("409", "33700394000140", "UNIBANCO-UNIAO DE BANCOS BRASILEIROS S.A.", "Banco Múltiplo"),

	/**
	 * Tipo enumerado que representa o <strong>Unibanco</strong>, código de
	 * compensação <strong><tt>422</tt></strong> <a
	 * href="http://www.safra.com.br/">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_SAFRA("422", "58160789000128", "BANCO SAFRA S.A.", "Banco Múltiplo"),
	
	/**
	 * Tipo enumerado que representa o <strong>Citibank</strong>, código de
	 * compensação <strong><tt>756</tt></strong> <a
	 * href="http://www.citibank.com.br/">site</a>.
	 * 
	 * @since 0.2
	 */
	CITIBANK("745", "33479023000180", "BANCO CITIBANK S.A.", "Banco Múltiplo"),
	
	/**
	 * Tipo enumerado que representa o <strong>Banco Sicredi</strong>, código de
	 * compensação <strong><tt>748</tt></strong> <a
	 * href="http://www.sicredi.com.br/">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_SICREDI("748", "01181521000155", "BANCO COOPERATIVO SICREDI S.A.", "Banco Mútiplo Cooperativo"),
	
	/**
	 * Tipo enumerado que representa o <strong>Banco Rural</strong>, código de
	 * compensação <strong><tt>453</tt></strong> <a
	 * href="http://www.rural.com.br/">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCO_RURAL("453", "58160789000128", "BANCO RURAL S.A.", "Banco Múltiplo"),

	/**
	 * Tipo enumerado que representa o <strong>Bancoob</strong>, código de
	 * compensação <strong><tt>756</tt></strong> <a
	 * href="http://www.bancoob.com.br/">site</a>.
	 * 
	 * @since 0.2
	 */
	BANCOOB("756", "02038232000164", "BANCO COOPERATIVO DO BRASIL S.A. - BANCOOB", "Banco Comercial Cooperativo");
	
	/**
	 * Singleton <code>Map</code> para pesquisa por bancos suportados no
	 * componente.
	 * 
	 * @since 0.2
	 */
	public static final Map<String, BancosSuportados> suportados = new HashMap<String, BancosSuportados>(
			BancosSuportados.values().length);

	static {

		suportados.put(BANCO_DO_BRASIL.codigoDeCompensacaoBACEN, BANCO_DO_BRASIL);
		suportados.put(BANCO_DO_NORDESTE_DO_BRASIL.codigoDeCompensacaoBACEN, BANCO_DO_NORDESTE_DO_BRASIL);
		suportados.put(CAIXA_ECONOMICA_FEDERAL.codigoDeCompensacaoBACEN, CAIXA_ECONOMICA_FEDERAL);
		suportados.put(BANCO_BRADESCO.codigoDeCompensacaoBACEN, BANCO_BRADESCO);
		suportados.put(BANCO_ABN_AMRO_REAL.codigoDeCompensacaoBACEN, BANCO_ABN_AMRO_REAL);
		suportados.put(UNIBANCO.codigoDeCompensacaoBACEN, UNIBANCO);
		suportados.put(HSBC.codigoDeCompensacaoBACEN, HSBC);
		suportados.put(BANCO_ITAU.codigoDeCompensacaoBACEN, BANCO_ITAU);
		suportados.put(BANCO_SAFRA.codigoDeCompensacaoBACEN, BANCO_SAFRA);
		suportados.put(BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL.codigoDeCompensacaoBACEN, BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL);
		suportados.put(MERCANTIL_DO_BRASIL.codigoDeCompensacaoBACEN, MERCANTIL_DO_BRASIL);
		suportados.put(BANCO_DO_ESTADO_DO_ESPIRITO_SANTO.codigoDeCompensacaoBACEN, BANCO_DO_ESTADO_DO_ESPIRITO_SANTO);
		suportados.put(BANCO_RURAL.codigoDeCompensacaoBACEN, BANCO_RURAL);
		suportados.put(BANCO_SANTANDER.codigoDeCompensacaoBACEN, BANCO_SANTANDER);
		suportados.put(BANCO_INTEMEDIUM.codigoDeCompensacaoBACEN, BANCO_INTEMEDIUM);
		suportados.put(BANCO_SICREDI.codigoDeCompensacaoBACEN, BANCO_SICREDI);
		suportados.put(BANCOOB.codigoDeCompensacaoBACEN, BANCOOB);
		suportados.put(CITIBANK.codigoDeCompensacaoBACEN, CITIBANK);
		suportados.put(BANCO_DE_BRASILIA.codigoDeCompensacaoBACEN, BANCO_DE_BRASILIA);
		suportados.put(CECRED.codigoDeCompensacaoBACEN, CECRED);
	}

	/**
	 * Códigos de instituições bancárias na compensação - COMPE <a
	 * href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.2
	 */
	private String codigoDeCompensacaoBACEN;

	/**
	 * CNPJ registrado na <a href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.2
	 */
	private String cNPJ;

	/**
	 * Nome da instituição registrado na <a
	 * href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.2
	 */
	private String instituicao;

	/**
	 * Segmento bancário da instituição registrado na <a
	 * href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.2
	 */
	private String segmento;

	/**
	 * <p>
	 * Construtor naturalmente <code>private</code> responsável por criar uma
	 * única instância para cada banco.
	 * </p>
	 * 
	 * @param codigoDeCompensacaoBACEN
	 * @param cNPJ
	 * @param instituicao
	 * @param segmento
	 * 
	 * @see java.lang.Enum
	 * @see <a
	 *      href="http://java.sun.com/j2se/1.5.0/docs/guide/language/enums.html">Enum
	 *      Guide</a>
	 * 
	 * @since 0.2
	 * 
	 */
	private BancosSuportados(String codigoDeCompensacaoBACEN, String cnpj,
			String instituicao, String segmento) {
		
		this.codigoDeCompensacaoBACEN = codigoDeCompensacaoBACEN;
		this.cNPJ = cnpj;
		this.instituicao = instituicao;
		this.segmento = segmento;
	}
	
	
	/**
	 * <p>
	 * Verifica se exite suporte (implementação) de "Campos Livres" para o banco
	 * representado pelo <code>codigoDeCompensacao</code>.
	 * </p>
	 * 
	 * @param codigoDeCompensacao
	 * @return verdadeiro se existe implementação para o banco em questão.
	 * 
	 * @since 0.2
	 */
	public static boolean isSuportado(String codigoDeCompensacao) {
		return suportados.containsKey(codigoDeCompensacao);
	}

	/**
	 * <p>
	 * Cria uma instância para o banco representado pelo tipo enumerado.
	 * </p>
	 * <p>
	 * Cada instância retornada por este método contém:
	 * <ul>
	 * <li><tt>Código de componsação</tt></li>
	 * <li><tt>Nome da instituição</tt></li>
	 * <li><tt>CNPJ da instituição</tt></li>
	 * <li><tt>Segmento da instituição bancária</tt></li>
	 * </ul>
	 * </p>
	 * 
	 * @return Uma instância do respectivo banco.
	 * 
	 * @see org.jrimum.domkee.financeiro.banco.febraban.Banco#Banco(CodigoDeCompensacaoBACEN, String, CNPJ, String)
	 * @see <a href="http://www.bcb.gov.br/?CHEQUESCOMPE">Bancos supervisionados
	 *      pela BACEN</a>
	 * 
	 * @since 0.2
	 */
	public Banco create() {
		return new Banco(new CodigoDeCompensacaoBACEN(this.codigoDeCompensacaoBACEN), this.instituicao, new CNPJ(
				this.cNPJ), this.segmento);
	}

	/**
	 * @return the codigoDeCompensacaoBACEN
	 * 
	 * @since 0.2
	 */
	public String getCodigoDeCompensacao() {
		return codigoDeCompensacaoBACEN;
	}

	/**
	 * @return the cNPJ
	 * 
	 * @since 0.2
	 */
	public String getCNPJ() {
		return cNPJ;
	}

	/**
	 * @return the instituicao
	 * 
	 * @since 0.2
	 */
	public String getInstituicao() {
		return instituicao;
	}

	/**
	 * @return the segmento
	 * 
	 * @since 0.2
	 */
	public String getSegmento() {
		return segmento;
	}

}
