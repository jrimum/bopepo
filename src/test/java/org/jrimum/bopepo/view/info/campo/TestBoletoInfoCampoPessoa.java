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
 * Created at: 14/01/2014 - 13:47:35
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
 * Criado em: 14/01/2014 - 13:47:35 
 * 
 */


package org.jrimum.bopepo.view.info.campo;

import static org.hamcrest.Matchers.equalTo;
import static org.jrimum.bopepo.view.info.campo.BoletoInfoCampoPessoa.getTextoNomeCprfDaPessoa;
import static org.junit.Assert.*;

import org.jrimum.domkee.financeiro.banco.febraban.EntidadeDeCobranca;
import org.junit.Test;


/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBoletoInfoCampoPessoa {

	private EntidadeDeCobranca pessoa;

	@Test
	public void deve_retornar_texto_formatado_para_pessoa_fisica(){
		String nome = "Uma Pessoa Física Desconhecida";
		String cpf = "222.222.222-22";
		pessoa = new EntidadeDeCobranca(nome, cpf);
		String textoExperado = String.format("%s, CPF: %s",nome,cpf);

		assertThat(getTextoNomeCprfDaPessoa(pessoa), equalTo(textoExperado));
	}

	@Test
	public void deve_retornar_texto_formatado_para_pessoa_juridica(){
		String nome = "Uma Pessoa Jurídica Qualquer";
		String cpf = "00.000.000/0001-91";
		pessoa = new EntidadeDeCobranca(nome, cpf);
		String textoExperado = String.format("%s, CNPJ: %s",nome,cpf);
		
		assertThat(getTextoNomeCprfDaPessoa(pessoa), equalTo(textoExperado));
	}
	
	@Test
	public void deve_retornar_apenas_nome_quando_cprf_null(){
		String nome = "Nome da Pessoa";
		pessoa = new EntidadeDeCobranca(nome);
		
		assertThat(getTextoNomeCprfDaPessoa(pessoa), equalTo(nome));
	}

	@Test
	public void deve_retornar_apenas_cprf_quando_nome_null(){
		String cprf = "222.222.222-22";
		pessoa = new EntidadeDeCobranca(null,"222.222.222-22");
		
		assertThat(getTextoNomeCprfDaPessoa(pessoa), equalTo("CPF: "+cprf));
	}

	@Test
	public void deve_retornar_string_vazia_quando_pessoa_null(){
		
		assertThat(getTextoNomeCprfDaPessoa(null), equalTo(""));
	}
	
}