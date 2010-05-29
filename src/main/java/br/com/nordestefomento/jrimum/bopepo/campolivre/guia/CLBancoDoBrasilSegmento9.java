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
 * Created at: 30/03/2008 - 18:08:37
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
 * Criado em: 30/03/2008 - 18:08:37
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre.guia;

import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.utilix.DateUtil;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;


/**
 * 
 * @author Misael Barreto
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
class CLBancoDoBrasilSegmento9 extends AbstractCLBancoDoBrasil { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5437349135380008187L;
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;

	/**
	 * <p>
	 *   Dado uma arrecadacão, cria um campo livre para o padrão do Banco do Brasil
	 *   para o tipo de segmento 9 e tipo de valor referencia 6 ou 8.  
	 * </p>
	 * @param arrecadacao título com as informações para geração do campo livre
	 */
	CLBancoDoBrasilSegmento9(Arrecadacao arrecadacao) {
		super(FIELDS_LENGTH, arrecadacao.getOrgaoRecebedor().getIdentificacaoSeguimento());
		

		String digitos11Com12DoCNPJ = arrecadacao.getOrgaoRecebedor().getCNPJ().getCodigoFormatadoSemPontuacao().substring(10,12);
		this.add(new Field<String>(digitos11Com12DoCNPJ, 2, Filler.ZERO_LEFT));
		
		this.add(new Field<Integer>(arrecadacao.getConvenio().getNumero(), 6, Filler.ZERO_LEFT));
		
		String dataFormatadaYYYYMMDD = DateUtil.FORMAT_YYYYMMDD.format(arrecadacao.getDataDoVencimento());
		this.add(new Field<String>(dataFormatadaYYYYMMDD, 8));	

		this.add(new Field<String>(arrecadacao.getNossoNumero(), 9, Filler.ZERO_LEFT));
		
	}

}
