
package org.jrimum.bopepo.exemplo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import junit.framework.TestCase;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;
import org.jrimum.utilix.text.DateFormat;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * Exemplo de código para geração de um boleto para caso específico.
 * </p>
 * <p>
 * Utiliza o Banco do Brasil como exemplo.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class BoletoTest3  extends TestCase {
	
	public void testGeraBoleto() {

        /*
         * INFORMANDO DADOS SOBRE O CEDENTE.
         */
        Cedente cedente = new Cedente("UNICAFES", "07.864.244/0001-61");

        /*
         * INFORMANDO DADOS SOBRE O SACADO.
         */
        Sacado sacado = new Sacado("Cresol Boa Ventura", "06.115.743/0001-93");

        // Informando o endereço do sacado.
        Endereco enderecoSac = new Endereco();
        enderecoSac.setUF(UnidadeFederativa.PR);
        enderecoSac.setLocalidade("Boa Ventura do Sao Roque");
        enderecoSac.setCep(new CEP("85710-000"));
        enderecoSac.setBairro("Vila Catarina");
        enderecoSac.setLogradouro("PRT 163");
        enderecoSac.setNumero("0");
        sacado.addEndereco(enderecoSac);

//        /*
//         * INFORMANDO DADOS SOBRE O SACADOR AVALISTA.
//         */
//        SacadorAvalista sacadorAvalista = new SacadorAvalista("JRimum Enterprise", "00.000.000/0001-91");
//
//        // Informando o endereço do sacador avalista.
//        Endereco enderecoSacAval = new Endereco();
//        enderecoSacAval.setUF(UnidadeFederativa.DF);
//        enderecoSacAval.setLocalidade("Brasília");
//        enderecoSacAval.setCep(new CEP("59000-000"));
//        enderecoSacAval.setBairro("Grande Centro");
//        enderecoSacAval.setLogradouro("Rua Eternamente Principal");
//        enderecoSacAval.setNumero("001");
//        sacadorAvalista.addEndereco(enderecoSacAval);

        /*
         * INFORMANDO OS DADOS SOBRE O TÍTULO.
         */
        
        // Informando dados sobre a conta bancária do título.
        ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_DO_BRASIL.create());
        contaBancaria.setNumeroDaConta(new NumeroDaConta(1708027));
        contaBancaria.setCarteira(new Carteira(17));
        
        contaBancaria.setAgencia(new Agencia(616, "5"));
        
        Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
        titulo.setNumeroDoDocumento("1958");
        titulo.setNossoNumero(Filler.ZERO_LEFT.fill(1958, 10));//saida:"0000001958"
        //titulo.setDigitoDoNossoNumero("5");
        titulo.setValor(BigDecimal.valueOf(138.84)); 
        titulo.setDataDoDocumento(DateFormat.DDMMYYYY_B.parse("18/10/2010"));
        titulo.setDataDoVencimento(DateFormat.DDMMYYYY_B.parse("27/11/2010"));
        titulo.setTipoDeDocumento(TipoDeTitulo.RC_RECIBO);
        titulo.setAceite(EnumAceite.N);
        titulo.setDesconto(new BigDecimal(0.00));
        
        /*
         * INFORMANDO OS DADOS SOBRE O BOLETO.
         */
        Boleto boleto = new Boleto(titulo);
        boleto.setDataDeProcessamento(DateFormat.DDMMYYYY_B.parse("18/10/2010"));
        boleto.setLocalPagamento("Pagável em qualquer Banco até o Vencimento.");
        boleto.setInstrucaoAoSacado("Pague o Boleto");
        boleto.setInstrucao1("Juros...: 2,00% ao mês  -  (R$ 0,09 ao dia)");
        boleto.setInstrucao2("Referente a Contribuição mensal novembro de 2010");
        
        /*
         * Sobrescrevendo os campos para impresssão
         */
        String nossoNumeroFormatoImpresso = titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta()+titulo.getNossoNumero();
        boleto.addTextosExtras("txtRsNossoNumero", nossoNumeroFormatoImpresso);
        boleto.addTextosExtras("txtFcNossoNumero", nossoNumeroFormatoImpresso);
        
        String agenciaCodigoCedenteFormatoImpresso = String.format("%s-%s / %s",titulo.getContaBancaria().getAgencia().getCodigo(),titulo.getContaBancaria().getAgencia().getDigitoVerificador(),"41281-3");
        boleto.addTextosExtras("txtRsAgenciaCodigoCedente", agenciaCodigoCedenteFormatoImpresso);
        boleto.addTextosExtras("txtFcAgenciaCodigoCedente", agenciaCodigoCedenteFormatoImpresso);
        
//        boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
//        boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
//        boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
//        boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
//        boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
//        boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");

        /*
         * GERANDO O BOLETO BANCÁRIO.
         */
        // Instanciando um objeto "BoletoViewer", classe responsável pela
        // geração do boleto bancário.
        BoletoViewer boletoViewer = new BoletoViewer(boleto);

        // Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
        // pasta do projeto. Outros exemplos:
        // WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
        // LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
        File arquivoPdf = boletoViewer.getPdfAsFile("MeuPrimeiroBoleto.pdf");

        // Mostrando o boleto gerado na tela.
        mostreBoletoNaTela(arquivoPdf);
        
        //PODE CONFERIR
        System.out.println("00190.00009 01708.027006 00001.958172 1 47990000013884");
        System.out.println(boleto.getLinhaDigitavel().write());
}

	/**
	 * Exibe o arquivo na tela.
	 * 
	 * @param arquivoBoleto
	 */
	private static void mostreBoletoNaTela(File arquivoBoleto) {

		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

		try {
			desktop.open(arquivoBoleto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
