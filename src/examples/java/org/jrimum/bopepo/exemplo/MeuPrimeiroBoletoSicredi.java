package org.jrimum.bopepo.exemplo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.campolivre.NotSupportedBancoException;
import org.jrimum.bopepo.campolivre.NotSupportedCampoLivreException;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.comum.pessoa.id.cprf.CNPJ;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Banco;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.CodigoDeCompensacaoBACEN;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;

import com.lowagie.text.DocumentException;



public class MeuPrimeiroBoletoSicredi {

	public static void main (String[] args) throws DocumentException, IllegalArgumentException, IOException, NotSupportedBancoException, NotSupportedCampoLivreException {
		
 		/* 
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 * */
		Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");
		
		// Informando dados sobre a conta bancária do cendente.
		
		final Banco banco = new Banco(new CodigoDeCompensacaoBACEN(748),
				"BANCO COOPERATIVO SICREDI S.A.", new CNPJ("01181521000155"));
		
		//SOBREpondo a Logo do banco:
		banco.setImgLogo(new ImageIcon("desenvolvimento/SICREDI/Template/Imagens/LogoSicredi.PNG").getImage());
		
		ContaBancaria contaBancariaCed = new ContaBancaria(banco);
		contaBancariaCed.setAgencia(new Agencia(123, "6"));
		contaBancariaCed.setCarteira(new Carteira(5));
		contaBancariaCed.setNumeroDaConta(new NumeroDaConta(7891, "0"));
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
		titulo.setNumeroDoDocumento("123456789");
		titulo.setNossoNumero("07200009");//NossNúmero já Vem Calculado
		titulo.setDigitoDoNossoNumero("0");
		titulo.setValor(BigDecimal.valueOf(0.23));
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(new Date());
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(EnumAceite.A);

		
		/* 
		 * INFORMANDO OS DADOS DO CASO ESPECIFICO (CAMPO LIVRE DO BANCO) SICREDI.
		 * */
		
		CampoLivreSicredi clSICREDI = new CampoLivreSicredi(titulo);
		
		
		/* 
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 * */
		Boleto boleto = new Boleto(titulo,clSICREDI);
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
		boleto.addTextosExtras("txtEnvSacado1", "CLIENTE TESTE (Algum Sacado)");
		boleto.addTextosExtras("txtEnvSacado2", "RUA TESTE, 999");
		boleto.addTextosExtras("txtEnvSacado3", "17506-970  MARÍLIA-SP");
		
				
		boleto.addTextosExtras("txtRsNumeroFatura", "111");
		boleto.addTextosExtras("txtRsSacado2", "MAIS DADOS SOBRE O SACADO...");
		
		/*
		 * Detalhando a Fatura:
		 * Uma coleção de dados que pode vir de um banco de dados ou 
		 * de qualquer outro lugar.
		 */
		List<ItemFaturaBoletoSICREDI> listaDetalhadaDaFatura = new ArrayList<ItemFaturaBoletoSICREDI>();
		
		listaDetalhadaDaFatura.add(new ItemFaturaBoletoSICREDI("Produto A","11/01/2008","S.A","2","R$ 140,00"));
		listaDetalhadaDaFatura.add(new ItemFaturaBoletoSICREDI("Produto B","01/02/2008","S.B","3","R$ 23,00"));
		listaDetalhadaDaFatura.add(new ItemFaturaBoletoSICREDI("Produto C","15/04/2008","S.C","14","R$ 320,00"));
		listaDetalhadaDaFatura.add(new ItemFaturaBoletoSICREDI("Produto D","20/05/2008","S.D","1","R$ 410,00"));
		listaDetalhadaDaFatura.add(new ItemFaturaBoletoSICREDI("Produto E","27/06/2008","S.E","10","R$ 500,00"));
		
		/*
		 *Um TextArea para cada coluna do detalhamento da fatura. 
		 *Eh necessário declarar uma nova linha para cada item novo em uma coluna.
		 */
		StringBuilder sbProduto = new StringBuilder();
		StringBuilder sbData = new StringBuilder();
		StringBuilder sbServico = new StringBuilder();
		StringBuilder sbQuantidade = new StringBuilder();
		StringBuilder sbValor = new StringBuilder();
		
		final String NOVA_LINHA = "\n";
		
		for(ItemFaturaBoletoSICREDI item : listaDetalhadaDaFatura){
			
			sbProduto.append(item.produto + NOVA_LINHA); 
			sbData.append(item.data + NOVA_LINHA);
			sbServico.append(item.servico + NOVA_LINHA); 
			sbQuantidade.append(item.quantidade + NOVA_LINHA);
			sbValor.append(item.valor + NOVA_LINHA);
			
		}
		
		boleto.addTextosExtras("txtRsListagemNome", sbProduto.toString());
		boleto.addTextosExtras("txtRsListagemData", sbData.toString());
		boleto.addTextosExtras("txtRsListagemServico", sbServico.toString());
		boleto.addTextosExtras("txtRsListagemQtde", sbQuantidade.toString());
		boleto.addTextosExtras("txtRsListagemValor", sbValor.toString());
		
		/*
		 * Campos específicos do SICREDI para ficha de compensação
		 */
		
		boleto.addTextosExtras("txtFcNomeCarteira", "SIMPLES");
		
		//Formatação própria para Agencia/CodigoDoCedente
		CampoLivreSicredi.InnerCooperativaDeCredito cooperativa = clSICREDI.loadCooperativaDeCredito(titulo.getContaBancaria().getAgencia());
		
		String codigoDoCedente = clSICREDI.componhaCodigoDoCedente(titulo.getContaBancaria().getNumeroDaConta());
		//FORMATAÇÃO: AAAA.PP.CCCCC
		boleto.addTextosExtras("txtFcAgenciaCodigoCedentePosto", cooperativa.codigo+"."+cooperativa.posto+"."+codigoDoCedente);
		
		
		/* 
		 * GERANDO O BOLETO BANCÁRIO.
		 * */
		// Instanciando um objeto "BoletoViewer", classe responsável pela geraçãoo
		// do boleto bancário.
		BoletoViewer boletoViewer = new BoletoViewer(boleto);
		
		//TEMPLATE SICREDI PERSONALIZADO 
		boletoViewer.setTemplate("desenvolvimento/SICREDI/Template/TemplateBoletoSicredi.pdf");
		
		// Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
		// pasta do projeto. Outros exemplos:
		// WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
		// LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
		File arquivoPdf = boletoViewer.getPdfAsFile("MeuPrimeiroBoletoSicredi.pdf");
		
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