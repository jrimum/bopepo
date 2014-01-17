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
 * Created at: 17/01/2014 - 14:38:22
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
 * Criado em: 17/01/2014 - 14:38:22 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.hamcrest.Matchers.equalTo;
import static org.jrimum.bopepo.view.info.campo.BoletoInfoCampoAgenciaCodigoCedente.getTextoAgenciaCodigoCedente;
import static org.junit.Assert.*;

import org.jrimum.bopepo.excludes.ContaBancariaBuilder;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBoletoInfoCampoAgenciaCodigoCedente {

	@Test
	public void deve_retornar_agencia_com_dv_e_numero_da_conta_com_dv(){
		ContaBancaria conta = ContaBancariaBuilder.defaultValue();
		
		assertThat(getTextoAgenciaCodigoCedente(conta), equalTo("1234-1 / 123456-0"));
	}

	@Test
	public void deve_retornar_agencia_sem_dv_e_numero_da_conta_com_dv(){
		ContaBancaria conta = ContaBancariaBuilder.defaultValue();
		conta.setAgencia(new Agencia(conta.getAgencia().getCodigo()));
		
		assertThat(getTextoAgenciaCodigoCedente(conta), equalTo("1234 / 123456-0"));
	}

	@Test
	public void deve_retornar_agencia_com_dv_e_numero_da_conta_sem_dv(){
		ContaBancaria conta = ContaBancariaBuilder.defaultValue();
		conta.setNumeroDaConta(new NumeroDaConta(conta.getNumeroDaConta().getCodigoDaConta()));
		
		assertThat(getTextoAgenciaCodigoCedente(conta), equalTo("1234-1 / 123456"));
	}

	@Test
	public void deve_retornar_agencia_sem_dv_e_numero_da_conta_sem_dv(){
		ContaBancaria conta = ContaBancariaBuilder.defaultValue();
		conta.setAgencia(new Agencia(conta.getAgencia().getCodigo()));
		conta.setNumeroDaConta(new NumeroDaConta(conta.getNumeroDaConta().getCodigoDaConta()));
		
		assertThat(getTextoAgenciaCodigoCedente(conta), equalTo("1234 / 123456"));
	}
	
	@Test
	public void deve_nao_gerar_exceptions_quando_agencia_ausente(){
		ContaBancaria conta = ContaBancariaBuilder.defaultValue();
		conta.setAgencia(null);
		
		assertThat(getTextoAgenciaCodigoCedente(conta), equalTo("123456-0"));
	}

	@Test
	public void deve_nao_gerar_exceptions_quando_conta_ausente(){
		ContaBancaria conta = ContaBancariaBuilder.defaultValue();
		conta.setNumeroDaConta(null);
		
		assertThat(getTextoAgenciaCodigoCedente(conta), equalTo("1234-1"));
	}

}
