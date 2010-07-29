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
 * Created at: 28/07/2010 - 21:45:00
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
 * Criado em: 28/07/2010 - 21:45:00
 * 
 */

package org.jrimum.bopepo.campolivre;

import org.jrimum.bopepo.BancoSuportado;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de teste para a fábrica de campos livres da CEF.
 * 
 * @author Rômulo Augusto
 */
public class TestAbstractCLCaixaEconomicaFederal {

	private Titulo titulo;
	
	@Before
	public void setUp() {
		
		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente");
		
		ContaBancaria contaBancaria = new ContaBancaria(BancoSuportado.CAIXA_ECONOMICA_FEDERAL.create());
		contaBancaria.setCarteira(new Carteira(1));
		contaBancaria.setAgencia(new Agencia(12345, "x"));
		
		titulo = new Titulo(contaBancaria, sacado, cedente);
	}
	
	@Test(expected = NotSupportedCampoLivreException.class)
	public void quandoTamanhoNossoNumeroDiferenteDe11_15_17DisparaExcecao() {
		titulo.setNossoNumero("123456789");
		AbstractCLCaixaEconomicaFederal.create(titulo);
	}
}
