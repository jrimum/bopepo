/*
 * Copyright 2013 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 08/09/2013 - 00:05:33
 * 
 * ================================================================================
 * 
 * Direitos autorais 2013 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 08/09/2013 - 00:05:33
 * 
 */

package org.jrimum.bopepo.excludes;

import java.math.BigDecimal;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;
import org.jrimum.utilix.text.DateFormat;

/**
 * Criação de dados para testes.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class BoletoBuilder {

	public final static Boleto create(){
		return crieUmBoleto();
	}
	
	public final static Boleto createWithSacadorAvalista(){
		return crieUmBoletoComSacadorAvalista();
	}
	
	private final static Boleto crieUmBoleto() {
		
		ContaBancaria contaBancaria = crieUmaContaBancaria();
		Cedente cedente = crieUmCedente(); 
		Sacado sacado = crieUmSacado();
		Titulo titulo = crieOsDadosDoNovoTitulo(new Titulo(contaBancaria,sacado,cedente));
		
		return crieOsDadosDoNovoBoleto(new Boleto(titulo));
	}

	private final static Boleto crieUmBoletoComSacadorAvalista() {
		
		ContaBancaria contaBancaria = crieUmaContaBancaria();
		Cedente cedente = crieUmCedente(); 
		Sacado sacado = crieUmSacado();
		SacadorAvalista sacadorAvalista = crieUmSacadorAvalista();
		Titulo titulo = crieOsDadosDoNovoTitulo(new Titulo(contaBancaria,sacado,cedente,sacadorAvalista));
		
		return crieOsDadosDoNovoBoleto(new Boleto(titulo));
	}

	private final static Boleto crieOsDadosDoNovoBoleto(Boleto boleto) {
		
		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
		boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado não é o esperado, aproveite o DESCONTÃO!");
		boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
		boleto.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
		boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
		boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
		boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
		boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
		boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
		boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
		boleto.setDataDeProcessamento(DateFormat.DDMMYYYY_B.parse("14/01/2020"));
		
		return boleto;
	}

	private static final Titulo crieOsDadosDoNovoTitulo(Titulo titulo) {
		
		titulo.setNumeroDoDocumento("123456");
		titulo.setNossoNumero("99345678912");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(0.23));
		titulo.setDataDoDocumento(DateFormat.DDMMYYYY_B.parse("01/01/2020"));
		titulo.setDataDoVencimento(DateFormat.DDMMYYYY_B.parse("07/09/2020"));		
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(Aceite.A);
		titulo.setDesconto(new BigDecimal(0.05));
		titulo.setDeducao(BigDecimal.ZERO);
		titulo.setMora(BigDecimal.ZERO);
		titulo.setAcrecimo(BigDecimal.ZERO);
		titulo.setValorCobrado(BigDecimal.ZERO);
		
		return titulo;
	}
	
	private static final Sacado crieUmSacado() {
		
		Sacado sacado = new Sacado("JRimum Developer Pronto Para Férias", "222.222.222-22");

		Endereco enderecoSac = new Endereco();
		enderecoSac.setUF(UnidadeFederativa.RN);
		enderecoSac.setLocalidade("Natal");
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setBairro("Grande Centro");
		enderecoSac.setLogradouro("Rua poeta dos programas");
		enderecoSac.setNumero("1");
		enderecoSac.setComplemento("Apt 101");
		sacado.addEndereco(enderecoSac);
		
		return sacado;
	}
	
	private static final Cedente crieUmCedente() {
		
		return new Cedente("Projeto JRimum", "00.000.208/0001-00");
	}
	
	private static final ContaBancaria crieUmaContaBancaria(){
		
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_BRADESCO.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
		contaBancaria.setCarteira(new Carteira(30));
		contaBancaria.setAgencia(new Agencia(1234, "1"));
		
		return contaBancaria;
	}

	private static final SacadorAvalista crieUmSacadorAvalista() {
		
		SacadorAvalista sacadorAvalista = new SacadorAvalista("Mastermum", "00.000.000/0001-91");

		Endereco enderecoSacAval = new Endereco();
		enderecoSacAval.setUF(UnidadeFederativa.DF);
		enderecoSacAval.setLocalidade("Brasília");
		enderecoSacAval.setCep(new CEP("70150-903"));
		enderecoSacAval.setBairro("Grande Centro");
		enderecoSacAval.setLogradouro("Rua Eternamente Principal");
		enderecoSacAval.setNumero("001");
		sacadorAvalista.addEndereco(enderecoSacAval);
		
		return sacadorAvalista;
	}
}
