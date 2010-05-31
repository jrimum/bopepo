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

package br.com.nordestefomento.jrimum.bopepo.guia;

import java.io.Serializable;
import java.util.HashMap;

import br.com.nordestefomento.jrimum.domkee.comum.pessoa.id.cprf.CNPJ;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Banco;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.CodigoDeCompensacaoBACEN;

/**
 * 
 * <p>
 * Enumeração dos bancos segundo o <a href="http://www.bcb.gov.br>Banco Central
 * do Brasil</a> que são suportados por este componente na tarefa de geração de
 * boletos.
 * </p>
 * 
 * <p>
 * Aqui se encontram todos os bancos sob a <a
 * href="http://www.bcb.gov.br/?CHEQUESCOMPE">supervisão da BACEN</a> em
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
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author Samuel Valério
 * 
 * @see br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivre
 * @see br.com.nordestefomento.jrimum.domkee.financeiro.banco.Banco
 * 
 * @since 0.3
 * 
 * @version 0.3
 */

public enum BancoSuportado implements Serializable{

	// TODO FAZER um link para a lista de componentes suportados no javadoc
	// dessa classe.

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
	 * @since 0.3
	 */
	BANCO_DO_BRASIL("001", "00000000000191", "BANCO DO BRASIL S.A.",
			"Banco do Brasil - Banco Múltiplo");

	/**
	 * Singleton <code>Map</code> para pesquisa por bancos suportados no
	 * componente.
	 * 
	 * @since 0.3
	 */
	public static final HashMap<String, BancoSuportado> suportados = new HashMap<String, BancoSuportado>(
			BancoSuportado.values().length);

	static {
		suportados.put(BANCO_DO_BRASIL.codigoDeCompensacaoBACEN, BANCO_DO_BRASIL);	}

	/**
	 * Códigos de instituições bancárias na compensação - COMPE <a
	 * href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.3
	 */
	private String codigoDeCompensacaoBACEN;

	/**
	 * CNPJ registrado na <a href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.3
	 */
	private String cNPJ;

	/**
	 * Nome da instituição registrado na <a
	 * href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.3
	 */
	private String instituicao;

	/**
	 * Segmento bancário da instituição registrado na <a
	 * href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>.
	 * 
	 * @since 0.3
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
	 * @since 0.3
	 * 
	 */
	private BancoSuportado(String codigoDeCompensacaoBACEN, String cnpj,
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
	 * @since 0.3
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
	 * @see br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Banco#Banco(CodigoDeCompensacaoBACEN, String, CNPJ, String)
	 * @see <a href="http://www.bcb.gov.br/?CHEQUESCOMPE">Bancos supervisionados
	 *      pela BACEN</a>
	 * 
	 * @since 0.3
	 */
	public Banco create() {
		return new Banco(new CodigoDeCompensacaoBACEN(this.codigoDeCompensacaoBACEN), this.instituicao, new CNPJ(
				this.cNPJ), this.segmento);
	}

	/**
	 * @return the codigoDeCompensacaoBACEN
	 * 
	 * @since 0.3
	 */
	public String getCodigoDeCompensacao() {
		return codigoDeCompensacaoBACEN;
	}

	/**
	 * @return the cNPJ
	 * 
	 * @since 0.3
	 */
	public String getCNPJ() {
		return cNPJ;
	}

	/**
	 * @return the instituicao
	 * 
	 * @since 0.3
	 */
	public String getInstituicao() {
		return instituicao;
	}

	/**
	 * @return the segmento
	 * 
	 * @since 0.3
	 */
	public String getSegmento() {
		return segmento;
	}
}