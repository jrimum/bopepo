/* 
 * Copyright 2008 JRimum Project
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
 * Created at: 16/09/2009 - 00:44:51
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 16/09/2009 - 00:44:51
 * 
 */
package org.jrimum.bopepo.exemplo.banco.bradesco;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.exemplo.Exemplos;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Exemplo do boleto para o Banco Bradesco
 * </p>
 * <p>
 * Mostra um exemplo funcional que gera um boleto para a implementação de campo livre
 * do Banco Bradesco
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @version 0.2
 */
public class BoletoBradescoExemplo {

	public static void main(String[] args) {
		
		Titulo titulo = Exemplos.crieTitulo();
		
		/*
		 * Campos específicos para o Banco Bradesco.
		 */
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		
		/*
		 * Banco bradesco 237
		 */
		contaBancaria.setBanco(BancosSuportados.BANCO_BRADESCO.create());
		
		/*
		 * Agência com no máximo 4 dígitos
		 */
		contaBancaria.setAgencia(new Agencia(1234));

		/*
		 * Carteira com no máximo 2 dígitos 
		 */
		contaBancaria.setCarteira(new Carteira(12));
		
		/*
		 * Nosso número com 11 dígitos
		 */
		titulo.setNossoNumero("01234567891");
		
		/*
		 * Número da conta com no máxiom 7 dígitos
		 */
		contaBancaria.setNumeroDaConta(new NumeroDaConta(1234567));
		
		Boleto boleto = Exemplos.crieBoleto(titulo);
		
		Exemplos.execute(boleto);
	}
}
