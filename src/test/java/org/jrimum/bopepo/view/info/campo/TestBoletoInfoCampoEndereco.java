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
 * Created at: 17/01/2014 - 19:32:45
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
 * Criado em: 17/01/2014 - 19:32:45 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.jrimum.bopepo.excludes.EnderecoBuilder;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.financeiro.banco.febraban.EntidadeDeCobranca;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBoletoInfoCampoEndereco {

	private static final String ENDERECO_ESPERADO_LINHA1 = "Grande Centro - Natal / RN";
	private static final String ENDERECO_ESPERADO_LINHA2 = "Rua poeta dos programas, n°: 1 / Apt 101 - CEP: 59064-120";
	
	//LINHA1

	@Test
	public void deve_retornar_linha1_correta_para_entidade_de_cobraca(){
		
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		EntidadeDeCobranca entidade = new EntidadeDeCobranca("");
		entidade.addEndereco(endereco);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo(ENDERECO_ESPERADO_LINHA1));
	}
	
	@Test
	public void deve_retornar_linha1_vazia_para_entidade_de_cobraca_null(){
		EntidadeDeCobranca entidade = null;
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(entidade), equalTo(EMPTY));
	}
	
	@Test
	public void deve_retornar_linha1_vazia_para_entidade_de_cobraca_com_endereco_null(){
		EntidadeDeCobranca entidade = new EntidadeDeCobranca("");
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(entidade), equalTo(EMPTY));
	}
	
	@Test
	public void deve_retornar_linha1_correta_para_endereco(){
		
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo(ENDERECO_ESPERADO_LINHA1));
	}
	

	@Test
	public void deve_retornar_linha1_para_endereco_quando_bairro_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setBairro(null);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo("Natal / RN"));
	}

	@Test
	public void deve_retornar_linha1_para_endereco_quando_bairro_blank(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setBairro(EMPTY);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo("Natal / RN"));
	}

	@Test
	public void deve_retornar_linha1_para_endereco_quando_localidade_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setLocalidade(null);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo("Grande Centro / RN"));
	}

	@Test
	public void deve_retornar_linha1_para_endereco_quando_localidade_blank(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setLocalidade(null);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo("Grande Centro / RN"));
	}

	@Test
	public void deve_retornar_linha1_para_endereco_quando_uf_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setUF(null);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo("Grande Centro - Natal"));
	}
	
	@Test
	public void deve_retornar_linha1_vazia_para_endereco_null(){
		Endereco endereco = null; 
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha1(endereco), equalTo(EMPTY));
	}
	
	//LINHA2
	
	@Test
	public void deve_retornar_linha2_correta_para_entidade_de_cobraca(){
		
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		EntidadeDeCobranca entidade = new EntidadeDeCobranca("");
		entidade.addEndereco(endereco);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo(ENDERECO_ESPERADO_LINHA2));
	}
	
	@Test
	public void deve_retornar_linha2_vazia_para_entidade_de_cobraca_null(){
		EntidadeDeCobranca entidade = null;
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(entidade), equalTo(EMPTY));
	}

	@Test
	public void deve_retornar_linha2_vazia_para_entidade_de_cobraca_com_endereco_null(){
		EntidadeDeCobranca entidade = new EntidadeDeCobranca("");
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(entidade), equalTo(EMPTY));
	}
	
	@Test
	public void deve_retornar_linha2_correta_para_endereco(){
		
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo(ENDERECO_ESPERADO_LINHA2));
	}

	@Test
	public void deve_retornar_linha2_para_endereco_quando_logradouro_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setLogradouro(null);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo(", n°: 1 / Apt 101 - CEP: 59064-120"));
	}

	@Test
	public void deve_retornar_linha2_para_endereco_quando_logradouro_blank(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setLogradouro("");
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo(", n°: 1 / Apt 101 - CEP: 59064-120"));
	}

	@Test
	public void deve_retornar_linha2_para_endereco_quando_numero_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setNumero(null);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo("Rua poeta dos programas / Apt 101 - CEP: 59064-120"));
	}

	@Test
	public void deve_retornar_linha2_para_endereco_quando_numero_blank(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setNumero("");
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo("Rua poeta dos programas / Apt 101 - CEP: 59064-120"));
	}

	@Test
	public void deve_retornar_linha2_para_endereco_quando_complemento_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setComplemento(null);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo("Rua poeta dos programas, n°: 1 - CEP: 59064-120"));
	}
	
	@Test
	public void deve_retornar_linha2_para_endereco_quando_compplemento_blank(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		endereco.setComplemento("");
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo("Rua poeta dos programas, n°: 1 - CEP: 59064-120"));
	}
	
	@Test
	public void deve_retornar_linha2_para_endereco_quando_objeto_cep_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		CEP cep = null;
		endereco.setCep(cep);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo("Rua poeta dos programas, n°: 1 / Apt 101"));
	}

	@Test
	public void deve_retornar_linha2_para_endereco_quando_codigo_cep_null(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		CEP cep = new CEP();
		endereco.setCep(cep);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo("Rua poeta dos programas, n°: 1 / Apt 101"));
	}

	@Test
	public void deve_retornar_linha2_para_endereco_quando_codigo_cep_blank(){
		Endereco endereco = EnderecoBuilder.defaultValue();
		
		CEP cep = new CEP("");
		endereco.setCep(cep);
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo("Rua poeta dos programas, n°: 1 / Apt 101"));
	}
	
	@Test
	public void deve_retornar_linha2_vazia_para_endereco_null(){
		Endereco endereco = null; 
		
		assertThat(BoletoInfoCampoEndereco.getTextoEnderecoLinha2(endereco), equalTo(EMPTY));
	}

}