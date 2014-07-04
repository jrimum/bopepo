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
 * Created at: 14/01/2014 - 14:25:25
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
 * Criado em: 14/01/2014 - 14:25:25 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.hamcrest.Matchers.equalTo;
import static org.jrimum.bopepo.view.info.campo.BoletoInfoCampoNossoNumero.getTextoNossoNumero;
import static org.junit.Assert.assertThat;

import org.jrimum.bopepo.excludes.TituloBuilder;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBoletoInfoCampoNossoNumero {
	
	@Test
	public void deve_retornar_nosso_numero_sem_dv_quando_dv_null(){
		Titulo titulo = TituloBuilder.defaultValue();
		
		titulo.setNossoNumero("123456");
		titulo.setDigitoDoNossoNumero(null);
		
		assertThat(getTextoNossoNumero(titulo), equalTo("123456"));
	}

	@Test
	public void deve_retornar_nosso_numero_sem_dv_quando_dv_vazio(){
		Titulo titulo = TituloBuilder.defaultValue();
		
		titulo.setNossoNumero("123456");
		titulo.setDigitoDoNossoNumero("");
		
		assertThat(getTextoNossoNumero(titulo), equalTo("123456"));
	}

	@Test
	public void deve_retornar_nosso_numero_com_dv_quando_dv_presente(){
		Titulo titulo = TituloBuilder.defaultValue();
		
		titulo.setNossoNumero("123456");
		titulo.setDigitoDoNossoNumero("7");
		
		assertThat(getTextoNossoNumero(titulo), equalTo("123456-7"));
	}

	@Test
	public void deve_retornar_string_vaiza_caso_nao_nosso_numero_null(){
		Titulo titulo = TituloBuilder.defaultValue();
		
		titulo.setNossoNumero(null);
		titulo.setDigitoDoNossoNumero("7");
		
		assertThat(getTextoNossoNumero(titulo), equalTo(""));
	}

	@Test
	public void deve_retornar_string_vaiza_caso_nao_nosso_numero_vazio(){
		Titulo titulo = TituloBuilder.defaultValue();
		
		titulo.setNossoNumero("");
		titulo.setDigitoDoNossoNumero("7");
		
		assertThat(getTextoNossoNumero(titulo), equalTo(""));
	}

}
