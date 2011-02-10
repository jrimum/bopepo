package org.jrimum.bopepo.exemplo;

import static org.jrimum.utilix.text.DateFormat.DDMMYYYY_B;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Banco;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeMoeda;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;
/**
 * 
 * <p>
 * Exemplo de código para geração de um boleto simples.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TesteBoletoBancoRuralRegistro {

	public static void main (String[] args) {
		
 		/* 
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 * */
		Cedente cedente = new Cedente("Fernando Dias de Souza", "00.000.208/0001-00");
		
		// Informando dados sobre a conta bancária do cendente.		
		Banco banco = BancosSuportados.BANCO_RURAL.create();
		
		ContaBancaria contaBancariaCed = new ContaBancaria(banco);
		NumeroDaConta numeroConta = new NumeroDaConta(1234567);
		numeroConta.setDigitoDaConta("1");
		contaBancariaCed.setNumeroDaConta(numeroConta);
		
		
		Carteira carteira = new Carteira();
		carteira.setTipoCobranca(TipoDeCobranca.COM_REGISTRO);
		contaBancariaCed.setCarteira(carteira);
		contaBancariaCed.setAgencia( new Agencia( new Integer(61), new String("0")));
		cedente.addContaBancaria(contaBancariaCed);		
		
		/* 
		 * INFORMANDO DADOS SOBRE O SACADO.
		 * */
		Sacado sacado = new Sacado("Ricardo Oliveira", "333.333.333-33");

		// Informando o endereço do sacado.
		Endereco enderecoSac = new Endereco();
		enderecoSac.setLogradouro("Av. Paulista");
		enderecoSac.setNumero("6700");
		enderecoSac.setComplemento("Bloco A Ap.32");
		enderecoSac.setBairro("Centro");
		enderecoSac.setCep(new CEP("59064-120"));
		enderecoSac.setLocalidade("Sao Paulo");
		enderecoSac.setUF(UnidadeFederativa.SP);
		sacado.addEndereco(enderecoSac);

		
		/* 
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
		 * */		
		Titulo titulo = new Titulo(contaBancariaCed, sacado, cedente);
		titulo.setNumeroDoDocumento("222");
		titulo.setNossoNumero("2224491");
		titulo.setDigitoDoNossoNumero("1");
		titulo.setValor(BigDecimal.valueOf(9.65));
		titulo.setDataDoDocumento(DDMMYYYY_B.parse("05/09/2008"));
		titulo.setDataDoVencimento(DDMMYYYY_B.parse("25/09/2009"));
		titulo.setAceite(EnumAceite.A);
		titulo.setTipoDeMoeda(TipoDeMoeda.REAL);
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
		titulo.setParametrosBancarios(new ParametrosBancariosMap("POST0_AGENCIA",2));
		
		/* 
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 * */
		Boleto boleto = new Boleto(titulo);
		boleto.setLocalPagamento("PAGAR PREFERENCIALMENTE EM AGÊNCIA DO BANCO RURAL");
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
