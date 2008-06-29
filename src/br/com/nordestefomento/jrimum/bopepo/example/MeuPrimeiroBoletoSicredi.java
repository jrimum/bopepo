package br.com.nordestefomento.jrimum.bopepo.example;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedBancoException;
import br.com.nordestefomento.jrimum.bopepo.campolivre.NotSuporttedCampoLivreException;
import br.com.nordestefomento.jrimum.bopepo.view.BoletoViewer;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.Carteira;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo.EnumAceite;
import br.com.nordestefomento.jrimum.domkee.ientity.IBanco;
import br.com.nordestefomento.jrimum.domkee.type.CEP;
import br.com.nordestefomento.jrimum.domkee.type.Endereco;
import br.com.nordestefomento.jrimum.domkee.type.EnumTitulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumUnidadeFederativa;
import br.com.nordestefomento.jrimum.domkee.type.Localidade;
import br.com.nordestefomento.jrimum.domkee.type.Logradouro;

import com.lowagie.text.DocumentException;



public class MeuPrimeiroBoletoSicredi {

	public static void main (String[] args) throws DocumentException,
	IllegalArgumentException, IOException, NotSuporttedBancoException,
	NotSuporttedCampoLivreException {
		
 		/* 
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 * */
		Pessoa cedente = new Pessoa("PROJETO JRimum", "00.000.208/0001-00");
		
		// Informando dados sobre a conta banc�ria do cendente.		
		final IBanco banco = EnumBancos.BANCO_DO_BRASIL.create();
		ContaBancaria contaBancariaCed = new ContaBancaria(banco);
		contaBancariaCed.setAgencia(new Agencia(1234, "67"));
		contaBancariaCed.setCarteira(new Carteira(5));
		contaBancariaCed.setNumeroDaConta(new NumeroDaConta(6789, "12"));
		cedente.addContaBancaria(contaBancariaCed);		
		
		
		
		
		/* 
		 * INFORMANDO DADOS SOBRE O SACADO.
		 * */
		Pessoa sacado = new Pessoa("JavaDeveloper Pronto Para F�rias",
				"222.222.222-22");

		// Informando o endere�o do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setUf(EnumUnidadeFederativa.RN);
		enderecoSac.setLocalidade(new Localidade("Natal"));
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setBairro("Grande Centro");
		enderecoSac.setLogradouro(new Logradouro("Rua poeta dos programas"));
		enderecoSac.setNumero("1");
		sacado.addEndereco(enderecoSac);
		
		
		
		
		/* 
		 * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
		 * */
		Pessoa sacadorAvalista = new Pessoa("Nordeste Fomento Mercantil",
				"00.000.000/0001-91");
		
		// Informando o endere�o do sacador avalista. 
		Endereco enderecoSacAval = new Endereco();
		enderecoSacAval.setUf(EnumUnidadeFederativa.DF);
		enderecoSacAval.setLocalidade(new Localidade("Bras�lia"));
		enderecoSacAval.setCep(new CEP("00000-000"));
		enderecoSacAval.setBairro("Grande Centro");
		enderecoSacAval.setLogradouro(new Logradouro("Rua Eternamente Principal"));
		enderecoSacAval.setNumero("001");
		sacadorAvalista.addEndereco(enderecoSacAval);

		
		
		
		/* 
		 * INFORMANDO OS DADOS SOBRE O T�TULO.
		 * */		
		Titulo titulo = new Titulo(contaBancariaCed, sacado, cedente,sacadorAvalista);
		titulo.setNumeroDoDocumento("123456789");
		titulo.setNossoNumero("1234567890");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(0.23));
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(new Date());
		titulo.setTipoDeDocumento(EnumTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(EnumAceite.A);

		
		
		/* 
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 * */
		Boleto boleto = new Boleto(titulo);
		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em " +
				"qualquer Banco até o Vencimento.");
		boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
				"cobrado não o esperado, aproveite o DESCONTO!");
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

		
		
		// Adicionando novos campos ao boleto...
		boleto.addCampoExtra("txtEnvSacado1", "CLIENTE TESTE");
		boleto.addCampoExtra("txtEnvSacado2", "RUA TESTE, 999");
		boleto.addCampoExtra("txtEnvSacado3", "17506-970  MAR�LIA-SP");
		
				
		boleto.addCampoExtra("txtRsNumeroFatura", "111");
		boleto.addCampoExtra("txtRsSacado2", "MAIS DADOS SOBRE O SACADO...");
		
		
		boleto.addCampoExtra("txtRsListagemNome", "Produto A \nProduto B");
		boleto.addCampoExtra("txtRsListagemData", "11/03/2008 \n17/04/2008");
		boleto.addCampoExtra("txtRsListagemServico", "S1 \nS2");
		boleto.addCampoExtra("txtRsListagemQtde", "10 \n12");
		boleto.addCampoExtra("txtRsListagemValor", "R$ 120,00 \nR$ 240,00");
		
		boleto.addCampoExtra("txtFcNomeCarteira", "SIMPLES");
		
		
		//boleto.addCampoExtra("txtRsDadosCompraSacado2", "Produto B - R$ 0,05");
		//boleto.addCampoExtra("txtRsDadosCompraSacado3", "Produto C - R$ 0,06");
		//boleto.addCampoExtra("txtRsDadosCompraSacado4", "Produto D - R$ 0,04");
		
		
		/* 
		 * GERANDO O BOLETO BANC�RIO.
		 * */
		// Instanciando um objeto "BoletoViewer", classe respons�vel pela geraçãoo
		// do boleto bancário.
		BoletoViewer boletoViewer = new BoletoViewer(boleto);
		boletoViewer.setTemplate("desenvolvimento/SICREDI/Template/TemplateBoletoSicrediOtimizado.pdf");
		
		// Setando o meu template...
		//boletoViewer.setTemplate("C:/Documents and Settings/misael/Desktop/tutorial02/MeuBoletoTemplate.pdf");
		
		
		
		// Gerando o arquivo. No caso o arquivo mencionado ser� salvo na mesma
		// pasta do projeto. Outros exemplos:
		// WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
		// LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
		File arquivoPdf = boletoViewer.getAsPDF("MeuPrimeiroBoletoSicrediOtimizado.pdf");
		
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