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
 * Created at: 14/01/2014 - 14:39:37
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
 * Criado em: 14/01/2014 - 14:39:37 
 * 
 */

package org.jrimum.bopepo.excludes;

import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class EnderecoBuilder {
	
	private Endereco end;
	
	public static Endereco defaultValue(){
		return newDefaultValue();
	}
	
	public EnderecoBuilder(){
		this.end = defaultValue();
	}
	
	public EnderecoBuilder withLocalidade(String localidade){
		end.setLocalidade(localidade);
		return this;
	}
	
	public EnderecoBuilder withBairro(String bairro){
		end.setBairro(bairro);
		return this;
	}
	
	public EnderecoBuilder withLogradouro(String logradouro){
		end.setLogradouro(logradouro);
		return this;
	}

	public EnderecoBuilder withNumero(String numero){
		end.setNumero(numero);
		return this;
	}
	
	public EnderecoBuilder with(String complemento){
		end.setComplemento(complemento);
		return this;
	}
	
	public EnderecoBuilder withCep(String cep){
		end.setCep(new CEP(cep));
		return this;
	}
	
	public EnderecoBuilder with(UnidadeFederativa uf){
		this.end.setUF(uf);
		return this;
	}
	
	public Endereco build(){
		return end;
	}
	
	private static Endereco newDefaultValue(){
		Endereco end = new Endereco();
		end.setUF(UnidadeFederativa.RN);
		end.setLocalidade("Natal");
		end.setCep(new CEP("59064-120"));
		end.setBairro("Grande Centro");
		end.setLogradouro("Rua poeta dos programas");
		end.setNumero("1");
		end.setComplemento("Apt 101");
		return end;
	}
}
