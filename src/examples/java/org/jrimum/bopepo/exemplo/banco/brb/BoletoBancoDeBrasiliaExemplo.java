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
 * Created at: 01/02/2014 - 17:49:03
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
 * Criado em: 01/02/2014 - 17:49:03 
 * 
 */

package org.jrimum.bopepo.exemplo.banco.brb;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.exemplo.Exemplos;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * Exemplo do boleto para BRB - Banco de Brasília.

 * <p>
 * Mostra um exemplo funcional que gera um boleto para a implementação padrão do BRB.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2
 */
/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 */
public class BoletoBancoDeBrasiliaExemplo {

	public static void main(String[] args) {
		
		Titulo titulo = Exemplos.crieTitulo();
		
		/*
		 * Campos específicos para o Banco de Brasília.
		 */
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		
		/*
		 * Banco 070
		 */
		contaBancaria.setBanco(BancosSuportados.BANCO_DE_BRASILIA.create());
		
		/*
		 * Código do agência com no máxiom 3 dígitos
		 */
		contaBancaria.setAgencia(new Agencia(58));//058

		/*
		 * Código do conta com no máxiom 7 dígitos
		 */
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(2006));//0002006
		
		/*
		 * Carteira com no máximo 2 dígitos 
		 */
		contaBancaria.setCarteira(new Carteira(1,TipoDeCobranca.SEM_REGISTRO));
		
		/*
		 * Nosso número com 6 dígitos
		 */
		titulo.setNossoNumero("000001");
		
		Boleto boleto = Exemplos.crieBoleto(titulo);
		
		Exemplos.execute(boleto);
	}
}
