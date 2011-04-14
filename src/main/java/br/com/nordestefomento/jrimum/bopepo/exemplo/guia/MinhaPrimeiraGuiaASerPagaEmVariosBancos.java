package br.com.nordestefomento.jrimum.bopepo.exemplo.guia;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;

import javax.imageio.ImageIO;

import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivre;
import br.com.nordestefomento.jrimum.bopepo.guia.Guia;
import br.com.nordestefomento.jrimum.bopepo.view.guia.GuiaViewer;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.CodigoDeIdentificacaoFebraban;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Contribuinte;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.OrgaoRecebedor;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoSeguimento;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoValorReferencia;
import br.com.nordestefomento.jrimum.utilix.DateUtil;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;

import com.lowagie.text.BadElementException;

public class MinhaPrimeiraGuiaASerPagaEmVariosBancos {
	
	
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
		OrgaoRecebedor orgaoRecebedor = new OrgaoRecebedor("JRIMUM ORG", TipoSeguimento.PREFEITURA);
		orgaoRecebedor.setCodigoDeIdentificacaoFebraban(new CodigoDeIdentificacaoFebraban(2594));
	
		
		Image imageOrgaoRecebedor = null;
		// Linux: imageOrgaoRecebedor = ImageIO.read(new File("/home/user/JRiLogo.png"));
		// Windows: imageOrgaoRecebedor = ImageIO.read(new File("C:/JRiLogo.png"));
				
		// Se houver uma imagem(Ex: logo) do órgão recebedor... 
		if (ObjectUtil.isNotNull(imageOrgaoRecebedor)) {
			orgaoRecebedor.setImgLogo(imageOrgaoRecebedor);
		}
		
		
				
		/*
		 * ======================================
		 * Informando dados da Arrecadação
		 * ======================================
		 */
		// Ao não informar um convênio específico, a guia poderá ser paga em "n" bancos aos quais o órgão recebor 
		// possui convênio. Neste caso:
		// - A guia não apresentará a logo de banco.
		// - É recomendável informar nos campos de instrução em quais bancos as guias podem ser pagas.
		Arrecadacao arrecadacao = new Arrecadacao(orgaoRecebedor, contribuinte);
		arrecadacao.setTitulo("RECIBO DO CANDIDATO");
		arrecadacao.setDescricao("Guia de Recebimento não Compensável para " +
				"pagamento de Inscrição via Internet Para o Concurso JRimum - " +
				"Developers 2010");
		
		arrecadacao.setNossoNumero("11100000001776721");
		arrecadacao.setValorDocumento(BigDecimal.valueOf(247.09));
		arrecadacao.setTipoValorReferencia(TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10);
		arrecadacao.setDataDoDocumento(DateUtil.FORMAT_DD_MM_YYYY.parse("15/04/2011"));				
		arrecadacao.setDataDoVencimento(DateUtil.FORMAT_DD_MM_YYYY.parse("15/04/2011"));
	
		
		/*
		 * ======================================
		 * Informando dados da Guia
		 * ======================================
		 */
		
		// Como não há um convênio especificado, por sua vez não haverá também um banco especificado, 
		// o órgão recebedor será responsável por gerar a informação do CAMPO LIVRE.
		class CampoLivreX implements CampoLivre {
			private static final long serialVersionUID = -8431518866602113511L;
			private Arrecadacao arrecadacao;
			
			public CampoLivreX(Arrecadacao arrecadacao) {
				this.arrecadacao = arrecadacao;
			}

			@Override
			public void read(String g) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String write() {
				// Criando um campo com 8 caracteres, com o conteúdo da data de vencimento.
				String dataFormatadaYYYYMMDD = DateUtil.FORMAT_YYYYMMDD.format(arrecadacao.getDataDoVencimento());
				Field<String> fieldDataVencimento = new Field<String>(dataFormatadaYYYYMMDD, 8);					
				
				// Criando um campo com 13 caracteres, com zeros preenchendo espaços vazios à esquerda, se existirem.
				Field<String> fieldNossoNumero = new Field<String>(arrecadacao.getNossoNumero(), 17, Filler.ZERO_LEFT);

				// Retornando todos os campos concatenados, resultando assim no CAMPO LIVRE.
				// Tamnho total do CAMPO LIVRE: 25
				return  fieldDataVencimento.write()
						+ fieldNossoNumero.write();
			}
			
		}
		
		// RECURSO PONTO DE EXTENSÃO:
		// Informando um campo livre ainda não suportado nativamente pelo componente.
		CampoLivreX campoLivreX = new CampoLivreX(arrecadacao);
		
		
		
		
		
		// Ao instanciar a guia, informasse a arrecadacao e o campo livre "X".
		Guia guia = new Guia(arrecadacao, campoLivreX);
		guia.setInstrucaoAoCaixa1("PAGAMENTO SOMENTE À VISTA NO BANCO DO BRASIL, ITAÚ OU CAIXA ECONÔMICA FEDERAL");
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
