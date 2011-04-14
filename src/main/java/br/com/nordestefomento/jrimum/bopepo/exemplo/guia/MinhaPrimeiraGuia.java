package br.com.nordestefomento.jrimum.bopepo.exemplo.guia;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;

import br.com.nordestefomento.jrimum.bopepo.guia.BancoSuportado;
import br.com.nordestefomento.jrimum.bopepo.guia.Guia;
import br.com.nordestefomento.jrimum.bopepo.view.guia.GuiaViewer;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.CodigoDeIdentificacaoFebraban;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Contribuinte;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Convenio;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.OrgaoRecebedor;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoSeguimento;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoValorReferencia;
import br.com.nordestefomento.jrimum.utilix.DateUtil;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;

import com.lowagie.text.BadElementException;

public class MinhaPrimeiraGuia {
	
	
	public static void main(String[] args) throws ParseException, BadElementException, MalformedURLException, IOException {		
		
		/*
		 * ======================================
		 * Informando  os dados do contribuinte
		 * ======================================
		 */
		Contribuinte contribuinte = new Contribuinte("Gilmar Misael Rômulo da Silva", "114.886.892-53");
		
		
		/*
		 * ======================================
		 * Informando os dados do orgao/empresa
		 * recebedor
		 * ======================================
		 */			
		OrgaoRecebedor orgaoRecebedor = new OrgaoRecebedor("JRIMUM ORG", TipoSeguimento.USO_EXCLUSIVO_BANCO);
		/*
		 * O CNPJ é obrigatório nos casos em que a identificação do órgão recebedor será feita através do CNPJ.
		 * Isso ocorre quando o tipo de seguimento utilizado é o TipoSeguimento.CARNES_E_ASSEMELHADOS_OU_DEMAIS. 
		 */
		orgaoRecebedor.setCNPJ("66.308.410/0001-02");		
		
		/*
		 * O código de identificação FEBRABAN é obrigatório nos casos em que a identificação do órgão será feita mediante este código.
		 * Isso pode ocorrer para todos os tipos de seguimento, exceto para os seguimentos:
		 *  - TipoSeguimento.CARNES_E_ASSEMELHADOS_OU_DEMAIS - Identificação feita através do CNPJ;
		 *  - TipoSeguimento.USO_EXCLUSIVO_BANCO - Identificação feita através do código de compensação do banco e número do convênio
		 *    entre o banco e o órgão recebedor. No caso, o número do convênio ficará armazenado na área do CAMPO LIVRE.
		 */
		orgaoRecebedor.setCodigoDeIdentificacaoFebraban(new CodigoDeIdentificacaoFebraban(2233));
		
		Image imageOrgaoRecebedor = null;
		// Linux: imageOrgaoRecebedor = ImageIO.read(new File("/home/user/JRiLogo.png"));
		// Windows: imageOrgaoRecebedor = ImageIO.read(new File("C:/JRiLogo.png"));
		// Se houver uma imagem(Ex: logo) do órgão recebedor... 
		if (ObjectUtil.isNotNull(imageOrgaoRecebedor)) {
			orgaoRecebedor.setImgLogo(imageOrgaoRecebedor);
		}
		
		/*
		 * ======================================
		 * Informando os dados do convênio
		 * ======================================
		 */		
		Convenio convenio = new Convenio(BancoSuportado.BANCO_DO_BRASIL.create(), 105333);
		
		
		/*
		 * ======================================
		 * Informando dados da Arrecadação
		 * ======================================
		 */				
		Arrecadacao arrecadacao = new Arrecadacao(orgaoRecebedor, contribuinte, convenio);
		arrecadacao.setTitulo("RECIBO DO CANDIDATO");
		arrecadacao.setDescricao("Guia de Recebimento não Compensável para " +
				"pagamento de Inscrição via Internet Para o Concurso JRimum - " +
				"Developers 2010");
		
		arrecadacao.setNossoNumero("15744");
		arrecadacao.setValorDocumento(BigDecimal.valueOf(59.98));
		arrecadacao.setTipoValorReferencia(TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10);
		arrecadacao.setDataDoDocumento(DateUtil.FORMAT_DD_MM_YYYY.parse("26/06/2010"));				
		arrecadacao.setDataDoVencimento(DateUtil.FORMAT_DD_MM_YYYY.parse("26/06/2010"));
	
		
		/*
		 * ======================================
		 * Informando dados da Guia
		 * ======================================
		 */				
		Guia guia = new Guia(arrecadacao);
		guia.setInstrucaoAoCaixa1("PAGAMENTO SOMENTE À VISTA NO " + convenio.getBanco().getNome().toUpperCase() + ".");
		guia.setInstrucaoAoCaixa2("PREFERENCIAMENTE DEVE SER PAGA NOS TERMINAIS DE AUTO-ATENDIMENTO,");
		guia.setInstrucaoAoCaixa3("CORRESPONDENTES BANCÁRIOS E INTERNET");

		// GuiaViewer é o responsável por prover uma visualização da guia.
		GuiaViewer guiaViewer = new GuiaViewer(guia);
		
		
		
		File templatePersonalizado = null;
		// Linux: templatePersonalizado = new File("/home/user/MeuTemplate.pdf");
		// Windows:templatePersonalizado = new File("C:/MeuTemplate.pdf");		
		// Se houver um template personalizado, com campos extras, novas informações podem
		// ser adicionadas.
		if (ObjectUtil.isNotNull(templatePersonalizado)) {
			guia.addTextosExtras("txtCampoExtraOpcaoCargo", "Cargo: Developer - Lotação: Natal-RN");
			guia.addTextosExtras("txtCampoExtraNumeroInscricao", "666");
			guia.addTextosExtras("txtCampoExtraVersaoSistema", "Sistema Gerador de Guias (versão 1.0)");

			guiaViewer.setTemplate(templatePersonalizado);
		}
	

		// Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
		// pasta do projeto. Outros exemplos:
		// WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
		// LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
		File arquivoPdf = guiaViewer.getPdfAsFile("MinhaPrimeiraGuia.pdf");

		// Mostrando o boleto gerado na tela.
		mostreGuiaNaTela(arquivoPdf);		
	}
	
	
	private static void mostreGuiaNaTela(File arquivoPDF) {

		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		
		try {
			desktop.open(arquivoPDF);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	
	
}
