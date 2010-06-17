package br.com.nordestefomento.jrimum.bopepo.exemplo.guia;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import javax.imageio.ImageIO;

import br.com.nordestefomento.jrimum.bopepo.campolivre.guia.CampoLivre;
import br.com.nordestefomento.jrimum.bopepo.guia.Guia;
import br.com.nordestefomento.jrimum.bopepo.view.guia.GuiaViewer;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.Banco;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.CodigoDeCompensacaoBACEN;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Contribuinte;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Convenio;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.OrgaoRecebedor;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoSeguimento;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoValorReferencia;
import br.com.nordestefomento.jrimum.utilix.DateUtil;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;

import com.lowagie.text.BadElementException;

public class MinhaPrimeiraGuiaParaBancoNaoSuportadoNativamente {
	
	
	public static void main(String[] args) throws ParseException, BadElementException, MalformedURLException, IOException {
	
		Image imageOrgaoRecebedor = null;
		// Linux: imageOrgaoRecebedor = ImageIO.read(new File("/home/user/JRiLogo.png"));
		// Windows: imageOrgaoRecebedor = ImageIO.read(new File("C:/JRiLogo.png"));
		
		File templatePersonalizado = null;
		// Linux: templatePersonalizado = new File("/home/user/MeuTemplate.pdf");
		// Windows:templatePersonalizado = new File("C:/MeuTemplate.pdf");
		
		
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
		OrgaoRecebedor orgaoRecebedor = new OrgaoRecebedor("JRIMUM ORG", "66.308.410/0001-02", TipoSeguimento.USO_EXCLUSIVO_BANCO);		
		// Se houver uma imagem(Ex: logo) do órgão recebedor... 
		if (ObjectUtil.isNotNull(imageOrgaoRecebedor)) {
			orgaoRecebedor.setImgLogo(imageOrgaoRecebedor);
		}
		
		
		
		/*
		 * ======================================
		 * Informando os dados do convênio
		 * ======================================
		 */
		
		// RECURSO PONTO DE EXTENSÃO:
		// Informando um banco que AINDA não é suportado nativamente pelo componente.
		Banco bancoX = new Banco(new CodigoDeCompensacaoBACEN("104"), "CAIXA ECONOMICA FEDERAL"); 
		URL urlImgLogoBancoX = null;
		// Obtendo do resource do BOPEPO a imagem da logo da CAIXA ECONOMICA FEDERAL. Caso possua uma 
		// outra imagem, basta indicar.
		// Linux: meuBanco.setImgLogo(ImageIO.read(new File("/home/user/logoBanco.png")));
		// Windows: meuBanco.setImgLogo(ImageIO.read(new File("C:/logoBanco.png")));		
		urlImgLogoBancoX = MinhaPrimeiraGuiaParaBancoNaoSuportadoNativamente.class.getResource("/img/104.png");
		bancoX.setImgLogo(ImageIO.read(urlImgLogoBancoX));
		
		Convenio convenio = new Convenio(bancoX, 105333);
		
		
		
		/*
		 * ======================================
		 * Informando dados da Arrecadação
		 * ======================================
		 */				
		Arrecadacao arrecadacao = new Arrecadacao(convenio, orgaoRecebedor, contribuinte);
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
		
		// Como o banco não é nativamente suportado pelo sistema, obviamente
		// também não existirá implementações de campo livre para este banco.
		// Sabendo disto, e tendo o conhecimento de como montar o campo livre,
		// o usuário desenvolvedor pode criar o seu próprio campo livre. 
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
				// Criando um campo com 2 caracteres, com zeros preenchendo espaços vazios à esquerda, se existirem.
				String digitos11Com12DoCNPJ = arrecadacao.getOrgaoRecebedor().getCNPJ().getCodigoFormatadoSemPontuacao().substring(10,12);
				Field<String> fieldDigitos11Com12DoCNPJ = new Field<String>(digitos11Com12DoCNPJ, 2, Filler.ZERO_LEFT);

				// Criando um campo com 6 caracteres, com zeros preenchendo espaços vazios à esquerda, se existirem.
				Field<Integer> fieldConvenio = new Field<Integer>(arrecadacao.getConvenio().getNumero(), 6, Filler.ZERO_LEFT);
				
				// Criando um campo com 8 caracteres, com zeros preenchendo espaços vazios à esquerda, se existirem.
				String dataFormatadaYYYYMMDD = DateUtil.FORMAT_YYYYMMDD.format(arrecadacao.getDataDoVencimento());
				Field<String> fieldDataVencimento = new Field<String>(dataFormatadaYYYYMMDD, 8);					
				
				// Criando um campo com 9 caracteres, com zeros preenchendo espaços vazios à esquerda, se existirem.
				Field<String> fieldNossoNumero = new Field<String>(arrecadacao.getNossoNumero(), 9, Filler.ZERO_LEFT);

				// Retornando todos os campos concatenados, resultando assim no CAMPO LIVRE.
				return 	fieldDigitos11Com12DoCNPJ.write() 
						+ fieldConvenio.write() 
						+ fieldDataVencimento.write()
						+ fieldNossoNumero.write();
			}
			
		}
		
		// RECURSO PONTO DE EXTENSÃO:
		// Informando um campo livre ainda não suportado nativamente pelo componente.
		CampoLivreX campoLivreX = new CampoLivreX(arrecadacao);
		
		
		
		
		
		
		// Ao instanciar a guia, informasse a arrecadacao e o campo livre "X".
		Guia guia = new Guia(arrecadacao, campoLivreX);
		guia.setInstrucaoAoCaixa1("PAGAMENTO SOMENTE À VISTA NA " + bancoX.getNome().toUpperCase() + ".");
		guia.setInstrucaoAoCaixa2("PREFERENCIAMENTE DEVE SER PAGA NOS TERMINAIS DE AUTO-ATENDIMENTO,");
		guia.setInstrucaoAoCaixa3("CORRESPONDENTES BANCÁRIOS E INTERNET");

		// GuiaViewer é o responsável por prover uma visualização da guia.
		GuiaViewer guiaViewer = new GuiaViewer(guia);
		
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
