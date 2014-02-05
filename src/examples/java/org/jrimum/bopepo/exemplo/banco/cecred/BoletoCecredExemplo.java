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
 * Created at: 21/01/2014 - 16:28:50
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
 * Criado em: 21/01/2014 - 16:28:50 
 * 
 */

package org.jrimum.bopepo.exemplo.banco.cecred;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.exemplo.Exemplos;
import org.jrimum.bopepo.parametro.ParametroCECRED;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * Exemplo do boleto para CECRED.
 * 
 * <p>
 * Mostra um exemplo funcional que gera um boleto para a implementação padrão do
 * CECRED.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2
 */
public class BoletoCecredExemplo {

	public static void main(String[] args) {

		Titulo titulo = Exemplos.crieTitulo();

		/*
		 * Campos específicos para o CECRED.
		 */
		ContaBancaria contaBancaria = titulo.getContaBancaria();

		/*
		 * Banco 085
		 */
		contaBancaria.setBanco(BancosSuportados.CECRED.create());
		
		/*
		 * Número da conta com no máximo 8 dígitos
		 */
		int conta = 12345;
		
		contaBancaria.setNumeroDaConta(new NumeroDaConta(conta));
		
		/*
		 * Carteira com no máximo 2 dígitos
		 */
		contaBancaria.setCarteira(new Carteira(12));
		
		/*
		 * Númeero sequencial do boleto com no máximo 9 dígitos 
		 */
		String sequencial = "123456789";

		/*
		 * Nosso número com exatamente 17 dígitos
		 * "numero_da_conta"[8] + "sequencial"[9] 
		 */
		titulo.setNossoNumero("000"+conta+sequencial);

		/*
		 * Código do convênio com no máxiom 6 dígitos
		 */
		titulo.setParametrosBancarios(new ParametrosBancariosMap(ParametroCECRED.CODIGO_DO_CONVENIO, 123));

		Boleto boleto = Exemplos.crieBoleto(titulo);

		Exemplos.execute(boleto);
	}
}
