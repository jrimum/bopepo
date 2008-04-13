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

package br.com.nordestefomento.jrimum.bopepo;

import java.util.HashMap;

import br.com.nordestefomento.jrimum.domkee.entity.Banco;
import br.com.nordestefomento.jrimum.domkee.ientity.IBanco;
import br.com.nordestefomento.jrimum.domkee.type.CNPJ;




/**
 * 
 * <p>
 * Enumeração dos bancos segundo o <a href="http://www.bcb.gov.br>Banco Central do Brasil</a> em 04.04.2008.
 * </p>
 * 
 * <p>
 * Aqui se encontra todos os bancos sob a <a href="http://www.bcb.gov.br/?CHEQUESCOMPE">supervisão da BACEN</a>, em funcionamento no país.
 * </p>
 * 
 * <p>
 * EXEMPLO: 
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2 
 */
	
public enum EnumBancos implements ICodigosDeBancos{
	
	/**
	 * 
	 */
	BANCO_DO_BRASIL{
		/**
		 * @see br.com.nordestefomento.jrimum.bopepo.EnumBancos#newInstance()
		 */
		public IBanco newInstance(){
			return new Banco(COMPENSACAO_BB, "BANCO DO BRASIL S.A.",new CNPJ(CNPJ_BB),"Banco do Brasil - Banco Múltiplo");
		}
	},
	
	/**
	 * 
	 */
	CAIXA_ECONOMICA_FEDERAL{
		/**
		 * @see br.com.nordestefomento.jrimum.bopepo.EnumBancos#newInstance()
		 */
		public IBanco newInstance(){
			return new Banco(COMPENSACAO_CAIXA,"CAIXA ECONOMICA FEDERAL", new CNPJ(CNPJ_CAIXA),"Caixa Econômica Federal");
		}
	},
	
	/**
	 * 
	 */
	BANCO_BRADESCO{
		/**
		 * @see br.com.nordestefomento.jrimum.bopepo.EnumBancos#newInstance()
		 */
		public IBanco newInstance(){
			return new Banco(COMPENSACAO_BRADESCO,"BANCO BRADESCO S.A.", new CNPJ(CNPJ_BRADESCO),"Banco Múltiplo");
		}
	},
	
	/**
	 * 
	 */
	BANCO_ABN_AMRO_REAL{
		/**
		 * @see br.com.nordestefomento.jrimum.bopepo.EnumBancos#newInstance()
		 */
		public IBanco newInstance(){
			return new Banco(COMPENSACAO_BANCO_REAL,"BANCO ABN AMRO REAL S.A.", new CNPJ(CNPJ_BANCO_REAL),"Banco Múltiplo");
		}
	};
	
	
	/**
	 * Singleton <code>Map</code> para pesquisa por bancos suportados no componente. 
	 */
	public static final HashMap<String, EnumBancos> suportados = new HashMap<String, EnumBancos>(
			EnumBancos.values().length);

	static {

		suportados.put(COMPENSACAO_BB, BANCO_DO_BRASIL);

		suportados.put(COMPENSACAO_CAIXA, CAIXA_ECONOMICA_FEDERAL);

		suportados.put(COMPENSACAO_BRADESCO, BANCO_BRADESCO);

		suportados.put(COMPENSACAO_BANCO_REAL, BANCO_ABN_AMRO_REAL);

	}
	
	
	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 
	 */	
	public abstract IBanco newInstance();
	
	
	/**
	 * <p>
	 * Verifica se exite suporte (implementação) de "Campos Livres" para o banco representado pelo <code>codigoDeCompensacao</code>.
	 * </p>
	 * 
	 * @param codigoDeCompensacao
	 * @return verdadeiro se existe implementação para o banco em questão. 
	 * 
	 * @since 0.2
	 */
	public static boolean isSuportado(String codigoDeCompensacao){
		
		return suportados.containsKey(codigoDeCompensacao);
		
	}
	
}
