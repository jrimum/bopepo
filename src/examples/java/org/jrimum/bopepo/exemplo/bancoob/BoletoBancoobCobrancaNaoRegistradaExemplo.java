/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 17/02/2011 - 12:40:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 17/02/2011 - 12:40:00
 */

package org.jrimum.bopepo.exemplo.bancoob;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.exemplo.Exemplos;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Exemplo do boleto para o Bancoob - Cobrança não registrada.
 * </p>
 * <p>
 * Mostra um exemplo funcional que gera um boleto para a implementação de campo
 * livre do Bancoob para cobrança não registrada.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2
 */
public class BoletoBancoobCobrancaNaoRegistradaExemplo {

	public static void main(String[] args) {

		Titulo titulo = Exemplos.crieTitulo();

		/*
		 * Campos específicos para o Bancoob.
		 */

		ContaBancaria contaBancaria = titulo.getContaBancaria();

		/*
		 * Banco Sicredi 756
		 */
		contaBancaria.setBanco(BancosSuportados.BANCOOB.create());

		contaBancaria.setAgencia(new Agencia(4340));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(1));
		contaBancaria.setCarteira(new Carteira(1));

		titulo.setNossoNumero("11000001");
		titulo.setParametrosBancarios(new ParametrosBancariosMap("NumeroDaParcela",Integer.valueOf(0)));

		Boleto boleto = Exemplos.crieBoleto(titulo);

		Exemplos.execute(boleto);
	}
}
