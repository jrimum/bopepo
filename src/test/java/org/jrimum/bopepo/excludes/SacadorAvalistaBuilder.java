/* 
 * Copyright 2014 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 14/01/2014 - 18:39:58
 *
 * ================================================================================
 *
 * Direitos autorais 2014 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 14/01/2014 - 18:39:58 
 * 
 */

package org.jrimum.bopepo.excludes;

import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class SacadorAvalistaBuilder {

	private SacadorAvalista sacador;
	
	public SacadorAvalistaBuilder(){
		this.sacador = newDefaultValue();
	}
	
	public static SacadorAvalista defaultValue(){
		return newDefaultValue();
	}
	
	public SacadorAvalista build(){
		return this.sacador;
	}
	
	private static SacadorAvalista newDefaultValue(){
		
		SacadorAvalista sacadorAvalista = new SacadorAvalista("Mastermum", "00.000.000/0001-91");

		Endereco enderecoSacAval = new EnderecoBuilder()
		.with(UnidadeFederativa.DF)
		.withLocalidade("Brasília")
		.withCep("70150-903")
		.withBairro("Grande Centro")
		.withLogradouro("Rua Eternamente Principal")
		.withNumero("001")
		.build();
		
		sacadorAvalista.addEndereco(enderecoSacAval);
		
		return sacadorAvalista;
	}	
}
