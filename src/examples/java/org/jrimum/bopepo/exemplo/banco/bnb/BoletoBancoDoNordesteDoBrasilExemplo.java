/* 
 * Copyright 2010 JRimum Project
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
 * Created at: 16/12/2010 - 13:24:00
 *
 * ================================================================================
 *
 * Direitos autorais 2010 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Created at: 16/12/2010 - 13:24:00
 * 
 */


package org.jrimum.bopepo.exemplo.banco.bnb;


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
 * Exemplo do boleto para o Banco do Nordeste
 * </p>
 * <p>
 * Mostra um exemplo funcional que gera um boleto para a implementação de campo livre
 * do Banco do Nordeste do Brasil
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:pporto@gmail.com">Paulo Porto</a>
 * 
 * @version 0.2
 */
public class BoletoBancoDoNordesteDoBrasilExemplo {

	public static void main(String[] args) {
		
		Titulo titulo = Exemplos.crieTitulo();
		
		/*
		 * Campos específicos para o Banco do Nordeste.
		 */
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		
		/*
		 * Banco do Nordeste 004
		 */
		contaBancaria.setBanco(BancosSuportados.BANCO_DO_NORDESTE_DO_BRASIL.create());
		
		/*
		 * Agência com no máximo 4 dígitos
		 */
		contaBancaria.setAgencia(new Agencia(100));

		/*
		 * Carteira com no máximo 2 dígitos 
		 */
		contaBancaria.setCarteira(new Carteira(57));
		
		/*
		 * Nosso Número com 7 dígitos
		 */
		titulo.setNossoNumero("76");
		
		/*
		 * Dígito do Nosso Número
		 */
		titulo.setDigitoDoNossoNumero("0");
		
		/*
		 * Número da conta com no máxiom 7 dígitos e necessariamente com o dígito verificador
		 */
		contaBancaria.setNumeroDaConta(new NumeroDaConta(13677, "9"));
		
		Boleto boleto = Exemplos.crieBoleto(titulo);
		
		Exemplos.execute(boleto);
	}
}
