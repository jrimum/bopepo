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
 * Created at: 18/05/2008 - 21:15:39
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
 * Criado em: 18/05/2008 - 21:15:39
 * 
 */
	
package org.jrimum.bopepo.exemplo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
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
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;

/**
 * Classe utilitária para criação de objetos necessários para geração dos 
 * boletos de exemplo.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class Exemplos {
	
	/**
	 * Utility class pattern: classe não instanciável
	 * 
	 * @throws AssertionError Caso haja alguma tentativa de utilização deste construtor.
	 */
	private Exemplos() {
		throw new AssertionError("Classe utilitária não instanciável.");
	}
	
	/**
	 * <p>
	 * Devolve uma lista com mais de um boleto.
	 * </p>
	 * 
	 * @return List<Boleto>
	 * 
	 * @since 0.2
	 */
	public static List<Boleto> getVariosBoletos() {
		
		List<Boleto> boletos = new ArrayList<Boleto>(5);
		
		Titulo titulo;

		final Date VENCIMENTO = new GregorianCalendar(2000, Calendar.JULY, 3).getTime();
		
		final Date DATA_DO_DOCUMENTO =  new GregorianCalendar(2000, Calendar.APRIL, 14).getTime();

		Sacado sacado = crieSacado();
		
		Cedente cedente = crieCedente();
	
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_DO_BRASIL.create());
		
		contaBancaria.setAgencia(new Agencia(1234, "7"));
		contaBancaria.setCarteira(new Carteira(5));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(6789, "12"));

		cedente.addContaBancaria(contaBancaria);
		
		SacadorAvalista sacadorAvalista = crieSacadorAvalista();
		
		// Fim Código em teste
		titulo = new Titulo(contaBancaria,sacado, cedente, sacadorAvalista);
		titulo.setNumeroDoDocumento("123456789");
		titulo.setNossoNumero("1234567890");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoDocumento(DATA_DO_DOCUMENTO);
		titulo.setDataDoVencimento(VENCIMENTO);
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(EnumAceite.A);
	
		Boleto b1,b2,b3,b4,b5;
		b1 = new Boleto(titulo);
		
		b1.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
		b1.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado é injusto e esperamos seu pagamento assim mesmo.");
		b1.setInstrucao1("PARA PAGAMENTO 1 ");
		b1.getTitulo().getSacado().setNome("Misael Martins Motriz");
	
		b2 = new Boleto(titulo);
		
		b2.setLocalPagamento(b1.getLocalPagamento());
		b2.setInstrucaoAoSacado(b1.getInstrucaoAoSacado());
		b2.setInstrucao1("PARA PAGAMENTO 2 ");
		b2.getTitulo().getSacado().setNome("Samuca Meu Carro");
		
		b3 = new Boleto(titulo);
		
		b3.setLocalPagamento(b1.getLocalPagamento());
		b3.setInstrucaoAoSacado(b1.getInstrucaoAoSacado());
		b3.setInstrucao1("PARA PAGAMENTO 3 ");
		b3.getTitulo().getSacado().setNome("Romulo O Namorador");
		
		b4 = new Boleto(titulo);
		
		b4.setLocalPagamento(b1.getLocalPagamento());
		b4.setInstrucaoAoSacado(b1.getInstrucaoAoSacado());
		b4.setInstrucao1("PARA PAGAMENTO 4 ");
		b1.getTitulo().getSacado().setNome("Michelx E Seus Irmãos");
		
		b5 = new Boleto(titulo);
		
		b5.setLocalPagamento(b1.getLocalPagamento());
		b5.setInstrucaoAoSacado(b1.getInstrucaoAoSacado());
		b5.setInstrucao1("PARA PAGAMENTO 5 ");
		b5.getTitulo().getSacado().setNome("É o doido o que sobrescreveu.");
		
		boletos.add(b1);
		boletos.add(b2);
		boletos.add(b3);
		boletos.add(b4);
		boletos.add(b5);

		return boletos;
	}
	
	/**
	 * Cria uma conta bancária com dados padrão. Esta contabancária não tem banco definido.
	 * 
	 * @return
	 */
	public static ContaBancaria crieContaBancaria() {
		
		ContaBancaria contaBancaria = new ContaBancaria();
		
		Agencia agencia = new Agencia(1234, "0");
		contaBancaria.setAgencia(agencia);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta(123456, "0");
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		contaBancaria.setCarteira(new Carteira(1));
		
		return contaBancaria;
	}

	/**
	 * Cria um cedente com dados padrão.
	 * 
	 * @return
	 */
	public static Cedente crieCedente() {
		
		Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");
	
		cedente.addContaBancaria(crieContaBancaria());
		
		return cedente;
	}
	
	/**
	 * Cria um Sacado com dados padrão.
	 * 
	 * @return
	 */
	public static Sacado crieSacado() {
		
		Sacado sacado = new Sacado("JRimum Developer", "111.111.111-11");
		
		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.RN);
		endereco.setLocalidade("Natal");
		endereco.setCep("59064-120");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua JRimum");
		endereco.setNumero("2332");
		
		sacado.addEndereco(endereco);
		
		return sacado;
	}
	
	/**
	 * Cria um Sacador Avalista padrão.
	 * 
	 * @return
	 */
	public static SacadorAvalista crieSacadorAvalista() {
		
		SacadorAvalista sacadorAvalista = new SacadorAvalista("JavaRN", "00.000.000/0001-91");
		
		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.PB);
		endereco.setLocalidade("João Pessoa");
		endereco.setCep("59064-120");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua JRimum Paraiba");
		endereco.setNumero("3223");
		
		sacadorAvalista.addEndereco(endereco);
		
		return sacadorAvalista;
	}
	
	/**
	 * Cria um título com dados padrão.
	 * 
	 * @return
	 */
	public static Titulo crieTitulo() {
		return crieTitulo(crieCedente(), crieSacado(), crieSacadorAvalista());
	}
	
	/**
	 * Cria um título com dados padrão sem sacador avalista.
	 * 
	 * @return
	 */
	public static Titulo crieTituloSemSacadorAvalista() {		
		return crieTitulo(crieCedente(), crieSacado(), null);
	}	
	
	/**
	 * Cria um título a partir de um cedente, um sacado e um sacador avalista.
	 * 
	 * @param cedente
	 * @param sacado
	 * @param sacadorAvalista
	 * @return
	 */
	public static Titulo crieTitulo(Cedente cedente, Sacado sacado, SacadorAvalista sacadorAvalista) {
		
		ContaBancaria contaBancariaDoCedente = cedente.getContasBancarias().iterator().next();
		Titulo titulo = null;
		
		if (sacadorAvalista != null) {
			titulo = new Titulo(contaBancariaDoCedente, sacado, cedente, sacadorAvalista);
			
		} else {
			titulo = new Titulo(contaBancariaDoCedente, sacado, cedente);
		}
		
		titulo.setNumeroDoDocumento("123465");
		titulo.setNossoNumero("1");
		titulo.setDigitoDoNossoNumero("0");
		titulo.setValor(BigDecimal.TEN);
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(new Date());
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(EnumAceite.A);
		titulo.setDesconto(BigDecimal.ONE);
		
		return titulo;
	}
	
	/**
	 * Cria um boleto a partir de um título. Para as informações extras, como instruções, 
	 * é configurado um texto padrão.
	 * 
	 * @param titulo
	 * @return
	 */
	public static Boleto crieBoleto(Titulo titulo) {
		
		Boleto boleto = new Boleto(titulo);
		
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
		
		return boleto;
	}
	
	/**
	 * Gera o boleto.
	 * 
	 * @param boleto
	 */
	public static void execute(Boleto boleto) {
		execute(boleto, null);
	}
	
	public static void executeComTemplate(Boleto boleto, File template) {
		execute(boleto, template);
	}
	
	/**
	 * Abre o arquivo no aplicativo padrão do sistema.
	 * 
	 * @param arquivoBoleto
	 */
	public static void mostreBoletoNaTela(File arquivoBoleto) {
		
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		
		try {
			desktop.open(arquivoBoleto);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void execute(Boleto boleto, File template) {
		
		BoletoViewer viewer = new BoletoViewer(boleto);
		
		if (template != null) {
			viewer.setTemplate(template);
		}
		
		File boletoPDF = viewer.getPdfAsFile("BOLETO_" + boleto.getClass().getSimpleName().toUpperCase() + ".PDF");
		
		mostreBoletoNaTela(boletoPDF);
	}
}
