package br.com.nordestefomento.jrimum.bopepo.example;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.bopepo.view.BoletoViewer;
import br.com.nordestefomento.jrimum.domkee.comum.pessoa.Pessoa;
import br.com.nordestefomento.jrimum.domkee.comum.pessoa.endereco.CEP;
import br.com.nordestefomento.jrimum.domkee.comum.pessoa.endereco.Endereco;
import br.com.nordestefomento.jrimum.domkee.comum.pessoa.endereco.EnumUnidadeFederativa;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.DadoBancario;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Banco;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Carteira;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.EnumMoeda;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;
import br.com.nordestefomento.jrimum.utilix.DateUtil;

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
public class MeuPrimeiroBoletoHsbcCNR {

	public static void main (String[] args) {
		
 		/* 
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 * */
		Pessoa cedente = new Pessoa("Cedente de Teste", "00.000.208/0001-00");
		
		// Informando dados sobre a conta bancária do cendente.		
		Banco banco = EnumBancos.HSBC.create();
		ContaBancaria contaBancariaCed = new ContaBancaria(banco);
		contaBancariaCed.setNumeroDaConta(new NumeroDaConta(1234567));
		
		Carteira carteira = new Carteira();
		carteira.setTipoCobranca(EnumTipoCobranca.SEM_REGISTRO);
		contaBancariaCed.setCarteira(carteira);
		cedente.addContaBancaria(contaBancariaCed);		
		
		/* 
		 * INFORMANDO DADOS SOBRE O SACADO.
		 * */
		Pessoa sacado = new Pessoa("Martin A. G. Souza", "333.333.333-33");

		// Informando o endereço do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setLogradouro("Alameda dos Tocantins");
		enderecoSac.setNumero("678");
		enderecoSac.setComplemento("Bloco A Ap.32");
		enderecoSac.setBairro("Centro");
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setLocalidade("Belo Horizonte");
		enderecoSac.setUF(EnumUnidadeFederativa.MG);
		sacado.addEndereco(enderecoSac);

		
		/* 
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
		 * */		
		Titulo titulo = new Titulo(contaBancariaCed, sacado, cedente);
		titulo.setNumeroDoDocumento("222");
		titulo.setNossoNumero("222449");
		titulo.setValor(BigDecimal.valueOf(9.65));
		titulo.setDataDoDocumento(DateUtil.parse("05/09/2008"));
		titulo.setDataDoVencimento(DateUtil.parse("25/09/2009"));
		titulo.setAceite(EnumAceite.A);
		titulo.setEnumMoeda(EnumMoeda.REAL);
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
		boleto.setLocalPagamento("PAGAR PREFERENCIALMENTE EM AGÊNCIA DO HSBC");
		boleto.setInstrucao1("Após o vencimento cobrar multa de 2,00% e juros de 0,02% por mês de atraso.");
		boleto.setInstrucao3("Até o vencimento conceder desconto de 10%.");
		boleto.setInstrucao5("Não receber após 25/09/2009.");		
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
