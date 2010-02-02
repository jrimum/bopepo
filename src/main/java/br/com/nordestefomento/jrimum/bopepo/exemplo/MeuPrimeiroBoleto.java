package br.com.nordestefomento.jrimum.bopepo.exemplo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import br.com.nordestefomento.jrimum.bopepo.BancoSuportado;
import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.view.BoletoViewer;
import br.com.nordestefomento.jrimum.domkee.comum.pessoa.endereco.CEP;
import br.com.nordestefomento.jrimum.domkee.comum.pessoa.endereco.Endereco;
import br.com.nordestefomento.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.Banco;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.DadoBancario;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Agencia;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Cedente;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Modalidade;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Sacado;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;

/**
 * 
 * <p>
 * Exemplo de código para geração de um boleto simples.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class MeuPrimeiroBoleto {

	public static void main (String[] args) {
		
 		/* 
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 * */
		Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");
		
		// Informando dados sobre a conta bancária do cendente.		
		Banco banco = BancoSuportado.NOSSA_CAIXA.create();
		ContaBancaria contaBancariaCed = new ContaBancaria(banco);
		contaBancariaCed.setBanco(banco);
		contaBancariaCed.setNumeroDaConta(new NumeroDaConta(123456, "0"));
		contaBancariaCed.setCarteira(new Carteira(123, TipoDeCobranca.SEM_REGISTRO));
		contaBancariaCed.setModalidade(new Modalidade(4));
		contaBancariaCed.setAgencia(new Agencia(1234, "1"));
		cedente.addContaBancaria(contaBancariaCed);		
		
		/* 
		 * INFORMANDO DADOS SOBRE O SACADO.
		 * */
		Sacado sacado = new Sacado("JavaDeveloper Pronto Para Férias", "222.222.222-22");

		// Informando o endereço do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setUF(UnidadeFederativa.RN);
		enderecoSac.setLocalidade("Natal");
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setBairro("Grande Centro");
		enderecoSac.setLogradouro("Rua poeta dos programas");
		enderecoSac.setNumero("1");
		sacado.addEndereco(enderecoSac);
		
		/* 
		 * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
		 * */
		SacadorAvalista sacadorAvalista = new SacadorAvalista("Nordeste Fomento Mercantil", "00.000.000/0001-91");
		
		// Informando o endereço do sacador avalista. 
		Endereco enderecoSacAval = new Endereco();
		enderecoSacAval.setUF(UnidadeFederativa.DF);
		enderecoSacAval.setLocalidade("Brasília");
		enderecoSacAval.setCep(new CEP("00000-000"));
		enderecoSacAval.setBairro("Grande Centro");
		enderecoSacAval.setLogradouro("Rua Eternamente Principal");
		enderecoSacAval.setNumero("001");
		sacadorAvalista.addEndereco(enderecoSacAval);
		
		/* 
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
		 * */		
		Titulo titulo = new Titulo(contaBancariaCed, sacado, cedente,sacadorAvalista);
		titulo.setNumeroDoDocumento("123456");
		titulo.setNossoNumero("993456789");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(0.23));
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(new Date());
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(EnumAceite.A);
		titulo.setDesconto(new BigDecimal(0.05));

		/*
		 * INFORMANDO MAIS DADOS BANCÁRIOS, QUANDO NECESSÁRIO.
		 * Dependendo do banco, talvez seja necessário informar mais dados além de: 
		 * 
		 * > Valor do título; 
		 * > Vencimento; 
		 * > Nosso número; 
		 * > Código do banco 
		 * > Data de vencimento; 
		 * > Agência/Código do cedente; 
		 * > Código da carteira; 
		 * > Código da moeda;
		 * 
		 * Definidos como padrão pela FEBRABAN.
		 * Verifique na documentação.
		 */
		titulo.setDadosBancarios(new DadoBancario(){});
		
		/* 
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 * */
		Boleto boleto = new Boleto(titulo);
		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em " +
				"qualquer Banco até o Vencimento.");
		boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
				"cobrado não é o esperado, aproveite o DESCONTÃO!");
		boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
		boleto.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
		boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
		boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás " +
				"COBRAR O VALOR DE: R$ 01,00");
		boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR " +
				"DE: R$ 02,00");
		boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR " +
				"DE: R$ 03,00");
		boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR " +
				"QUE VOCÊ QUISER!");
		boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
		
		/* 
		 * GERANDO O BOLETO BANCÁRIO.
		 * */
		// Instanciando um objeto "BoletoViewer", classe responsável pela geração
		// do boleto bancário.
		BoletoViewer boletoViewer = new BoletoViewer(boleto);
		
		// Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
		// pasta do projeto. Outros exemplos:
		// WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
		// LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
		File arquivoPdf = boletoViewer.getPdfAsFile("MeuPrimeiroBoleto.pdf");
		
		// Mostrando o boleto gerado na tela.
		mostreBoletoNaTela(arquivoPdf);
	}
	
	
	private static void mostreBoletoNaTela(File arquivoBoleto) {
		
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		try {
			desktop.open(arquivoBoleto);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
}
