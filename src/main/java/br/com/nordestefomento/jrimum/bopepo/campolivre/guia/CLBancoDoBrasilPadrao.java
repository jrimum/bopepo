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
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:erisvaldojunior@gmail.com">Erisvaldo Júnior</a>
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
class CLBancoDoBrasilPadrao extends AbstractCLBancoDoBrasil { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4697152038277741060L;
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 2;

	/**
	 * <p>
	 *   Dada uma arrecadacão, cria um campo livre para o padrão do Banco do Brasil
	 *   para todos os tipos de segmento, exceto os seguimentos <b>"6. Carnes e Assemelhados ou demais"</b>
	 *   e <b>"9. Uso exclusivo do banco"</b>. 
	 *   O tamanho total do campo livre é 25.  
	 * </p>
	 * @param arrecadacao título com as informações para geração do campo livre
	 */
	CLBancoDoBrasilPadrao(Arrecadacao arrecadacao) {
		super(FIELDS_LENGTH, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		
		// Data de vencimento no formato YYYYMMDD.
		// Tamanho: 8	
		String dataFormatadaYYYYMMDD = DateUtil.FORMAT_YYYYMMDD.format(arrecadacao.getDataDoVencimento());
		this.add(new Field<String>(dataFormatadaYYYYMMDD, 8));	

	
		// Número da guia (nosso número)
		// Tamanho: 17
		this.add(new Field<String>(arrecadacao.getNossoNumero(), 17, Filler.ZERO_LEFT));
	}

}
