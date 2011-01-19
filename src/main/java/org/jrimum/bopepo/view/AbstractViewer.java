/* 
 * Copyright 2011 JRimum Project
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
 * Created at: 14/01/2011 - 19:48:01
 *
 * ================================================================================
 *
 * Direitos autorais 2011 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 14/01/2011 - 19:48:01
 * 
 */

package org.jrimum.bopepo.view;

import static org.jrimum.utilix.Objects.isNull;
import static org.jrimum.utilix.Objects.isNotNull;
import static org.jrimum.utilix.text.DateFormat.DDMMYYYY_B;
import static org.jrimum.utilix.text.DecimalFormat.MONEY_DD_BR;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;

/**
 * <p>
 * Classe abstrata utilizada para gerar visualizações do boleto com os dados do título e boleto.
 * </p>
 * 
 * @author <a href="http://www.renatoatilio.com/">Renato Atilio</a>
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public abstract class AbstractViewer {
	private static Logger log = Logger.getLogger(AbstractViewer.class);
	
	private static final String	HIFEN_SEPERADOR	= "-";
	protected Boleto			boleto;
	private File				template;

	/**
	 * Define o conteúdo de um dado campo do template
	 * 
	 * @param field
	 * @param content
	 * @throws exception
	 */
	protected abstract void setText(String field, String content) throws Exception;
	
	protected abstract void setImage(String field, java.awt.Image image) throws Exception;
	
	protected abstract void setBarcode(String field, String content) throws Exception;

	/**
	 * <p>
	 * Faz o preenchimento dos campos do template
	 * </p>
	 * 
	 * @throws MalformedURLException
	 * @throws Exception
	 * 
	 * @since
	 */
	protected void preencher() throws MalformedURLException, Exception {
		setLogoBanco();
		setCodigoBanco();
		setLinhaDigitavel();
		setCedente();
		setAgenciaCondigoCedente();
		setEspecie();
		setQuantidade();
		setNossoNumero();
		setNumeroDocumento();
		setAbstractCPRFCedente();
		setDataVencimeto();
		setValorDocumento();
		setDescontoAbatimento();
		setOutraDeducao();
		setMoraMulta();
		setOutroAcrescimo();
		setValorCobrado();
		setInstrucaoAoSacado();
		setInstrucaoAoCaixa();
		setSacado();
		setLocalPagamento();
		setDataDocumento();
		setEspecieDoc();
		setAceite();
		setDataProcessamento();
		setSacadorAvalista();
		setCodigoBarra();
		setCarteira();
		setCamposExtra();
		setImagensNosCampos();
	}

	private void setCamposExtra() throws Exception {

		if (isNotNull(boleto.getTextosExtras())) {

			for (String campo : boleto.getTextosExtras().keySet()) {
				setText(campo, boleto.getTextosExtras().get(campo));
			}
		}
	}

	private void setCodigoBanco() throws Exception {

		ContaBancaria conta = boleto.getTitulo().getContaBancaria();

		String codigoCompensacao = conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado();
		String digitoCompensacao = conta.getBanco().getCodigoDeCompensacaoBACEN().getDigito().toString();

		setText("txtRsCodBanco", codigoCompensacao + HIFEN_SEPERADOR + digitoCompensacao);
		setText("txtFcCodBanco", codigoCompensacao + HIFEN_SEPERADOR + digitoCompensacao);
	}

	private void setCodigoBarra() throws Exception {
		
		setBarcode("txtFcCodigoBarra", boleto.getCodigoDeBarras().write());
		
	}

	private void setDataProcessamento() throws Exception {

		setText("txtFcDataProcessamento", DDMMYYYY_B.format(boleto.getDataDeProcessamento()));
	}

	private void setAceite() throws Exception {

		if (isNotNull(boleto.getTitulo().getAceite())) {
			setText("txtFcAceite", boleto.getTitulo().getAceite().name());
		}
	}

	private void setEspecieDoc() throws Exception {
		if (isNotNull(boleto.getTitulo().getTipoDeDocumento()) && isNotNull(boleto.getTitulo().getTipoDeDocumento().getSigla())) {
			setText("txtFcEspecieDocumento", boleto.getTitulo().getTipoDeDocumento().getSigla());
		}
	}

	private void setDataDocumento() throws Exception {
		setText("txtFcDataDocumento", DDMMYYYY_B.format(boleto.getTitulo().getDataDoDocumento()));
	}

	private void setLocalPagamento() throws Exception {
		setText("txtFcLocalPagamento", (boleto.getLocalPagamento()));
	}

	private void setSacado() throws Exception {

		StringBuilder sb = new StringBuilder();
		Sacado sacado = boleto.getTitulo().getSacado();

		if (isNotNull(sacado.getNome())) {
			sb.append(sacado.getNome());
		}

		if (isNotNull(sacado.getCPRF())) {
			sb.append(", ");

			if (sacado.getCPRF().isFisica()) {
				sb.append("CPF: ");

			} else if (sacado.getCPRF().isJuridica()) {
				sb.append("CNPJ: ");
			}

			sb.append(sacado.getCPRF().getCodigoFormatado());
		}

		setText("txtRsSacado", sb.toString());
		setText("txtFcSacadoL1", sb.toString());

		// TODO Código em teste
		sb.delete(0, sb.length());
		Endereco endereco = sacado.getEnderecos().iterator().next();

		setEndereco(endereco, "txtFcSacadoL2", "txtFcSacadoL3", sb);
	}

	private void setSacadorAvalista() throws Exception {

		if (boleto.getTitulo().hasSacadorAvalista()) {

			SacadorAvalista sacadorAvalista = boleto.getTitulo().getSacadorAvalista();

			StringBuilder sb = new StringBuilder();

			if (isNotNull(sacadorAvalista.getNome())) {
				sb.append(sacadorAvalista.getNome());
			}

			if (isNotNull(sacadorAvalista.getCPRF())) {

				sb.append(", ");

				if (sacadorAvalista.getCPRF().isFisica()) {
					sb.append("CPF: ");

				} else if (sacadorAvalista.getCPRF().isJuridica()) {
					sb.append("CNPJ: ");
				}

				sb.append(sacadorAvalista.getCPRF().getCodigoFormatado());
			}

			setText("txtFcSacadorAvalistaL1", sb.toString());

			// TODO Código em teste
			sb.delete(0, sb.length());
			Endereco endereco = sacadorAvalista.getEnderecos().iterator().next();

			setEndereco(endereco, "txtFcSacadorAvalistaL2", "txtFcSacadorAvalistaL3", sb);
		}
	}

	private void setEndereco(Endereco endereco, String campoEndereco1, String campoEndereco2, StringBuilder sb) throws Exception {

		if (isNotNull(endereco)) {

			if (isNotNull(endereco.getBairro())) {
				sb.append(endereco.getBairro());
			}

			if (isNotNull(endereco.getLocalidade())) {
				sb.append(HIFEN_SEPERADOR);
				sb.append(endereco.getLocalidade());
			}

			if (isNotNull(endereco.getUF())) {
				sb.append(" / ");
				sb.append(endereco.getUF().getNome());
			}

			setText(campoEndereco1, sb.toString());

			sb.delete(0, sb.length());

			if (isNotNull(endereco.getLogradouro())) {
				sb.append(endereco.getLogradouro());
			}

			if (isNotNull(endereco.getNumero())) {
				sb.append(", n°: ");
				sb.append(endereco.getNumero());
			}

			if (isNotNull(endereco.getCEP())) {
				sb.append(" ");
				sb.append(HIFEN_SEPERADOR);
				sb.append(" CEP: ");
				sb.append(endereco.getCEP().getCep());
			}

			setText(campoEndereco2, sb.toString());
		}
	}

	private void setInstrucaoAoCaixa() throws Exception {

		setText("txtFcInstrucaoAoCaixa1", boleto.getInstrucao1());
		setText("txtFcInstrucaoAoCaixa2", boleto.getInstrucao2());
		setText("txtFcInstrucaoAoCaixa3", boleto.getInstrucao3());
		setText("txtFcInstrucaoAoCaixa4", boleto.getInstrucao4());
		setText("txtFcInstrucaoAoCaixa5", boleto.getInstrucao5());
		setText("txtFcInstrucaoAoCaixa6", boleto.getInstrucao6());
		setText("txtFcInstrucaoAoCaixa7", boleto.getInstrucao7());
		setText("txtFcInstrucaoAoCaixa8", boleto.getInstrucao8());
	}

	private void setInstrucaoAoSacado() throws Exception {

		setText("txtRsInstrucaoAoSacado", boleto.getInstrucaoAoSacado());
	}

	private void setMoraMulta() throws Exception {

		if (isNotNull(boleto.getTitulo().getMora())) {

			setText("txtRsMoraMulta", MONEY_DD_BR.format(boleto.getTitulo().getMora()));
			setText("txtFcMoraMulta", MONEY_DD_BR.format(boleto.getTitulo().getMora()));
		}
	}

	private void setOutroAcrescimo() throws Exception {

		if (isNotNull(boleto.getTitulo().getAcrecimo())) {

			setText("txtRsOutroAcrescimo", MONEY_DD_BR.format(boleto.getTitulo().getAcrecimo()));
			setText("txtFcOutroAcrescimo", MONEY_DD_BR.format(boleto.getTitulo().getAcrecimo()));
		}
	}

	private void setOutraDeducao() throws Exception {

		if (isNotNull(boleto.getTitulo().getDeducao())) {

			setText("txtRsOutraDeducao", MONEY_DD_BR.format(boleto.getTitulo().getDeducao()));
			setText("txtFcOutraDeducao", MONEY_DD_BR.format(boleto.getTitulo().getDeducao()));
		}
	}

	private void setDescontoAbatimento() throws Exception {

		if (isNotNull(boleto.getTitulo().getDesconto())) {

			setText("txtRsDescontoAbatimento", MONEY_DD_BR.format(boleto.getTitulo().getDesconto()));
			setText("txtFcDescontoAbatimento", MONEY_DD_BR.format(boleto.getTitulo().getDesconto()));
		}
	}

	private void setValorDocumento() throws Exception {

		if (isNotNull(boleto.getTitulo().getValor())) {

			setText("txtRsValorDocumento", MONEY_DD_BR.format(boleto.getTitulo().getValor()));
			setText("txtFcValorDocumento", MONEY_DD_BR.format(boleto.getTitulo().getValor()));
		}
	}

	private void setValorCobrado() throws Exception {

		if (isNotNull(boleto.getTitulo().getValorCobrado())) {

			setText("txtRsValorCobrado", MONEY_DD_BR.format(boleto.getTitulo().getValorCobrado()));
			setText("txtFcValorCobrado", MONEY_DD_BR.format(boleto.getTitulo().getValorCobrado()));
		}
	}

	/**
	 * Data no formata "dd/mm/yyyy"
	 * 
	 * @throws Exception
	 */
	private void setDataVencimeto() throws Exception {

		if (isNotNull(boleto.getTitulo().getDataDoVencimento())) {

			setText("txtRsDataVencimento", DDMMYYYY_B.format(boleto.getTitulo().getDataDoVencimento()));
			setText("txtFcDataVencimento", DDMMYYYY_B.format(boleto.getTitulo().getDataDoVencimento()));
		}
	}

	private void setAbstractCPRFCedente() throws Exception {

		setText("txtRsCpfCnpj", boleto.getTitulo().getCedente().getCPRF().getCodigoFormatado());
	}

	private void setNumeroDocumento() throws Exception {

		setText("txtRsNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());
		setText("txtFcNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());
	}

	private void setCedente() throws Exception {

		setText("txtRsCedente", boleto.getTitulo().getCedente().getNome());
		setText("txtFcCedente", boleto.getTitulo().getCedente().getNome());
	}

	private void setCarteira() throws Exception {

		Carteira carteira = boleto.getTitulo().getContaBancaria().getCarteira();

		if (isNotNull(carteira) && isNotNull(carteira.getCodigo())) {

			setText("txtFcCarteira", carteira.getCodigo().toString());
		}
	}

	private void setQuantidade() throws Exception {

		setText("txtRsQuantidade", StringUtils.EMPTY);
		setText("txtFcQuantidade", StringUtils.EMPTY);
	}

	private void setEspecie() throws Exception {

		setText("txtRsEspecie", boleto.getTitulo().getTipoDeMoeda().name());
		setText("txtFcEspecie", boleto.getTitulo().getTipoDeMoeda().name());
	}

	private void setLinhaDigitavel() throws Exception {

		setText("txtRsLinhaDigitavel", boleto.getLinhaDigitavel().write());
		setText("txtFcLinhaDigitavel", boleto.getLinhaDigitavel().write());
	}

	private void setLogoBanco() throws MalformedURLException, Exception {
		// Através da conta bancária será descoberto a imagem que representa o
		// banco, com base no código do banco.
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		Image imgLogoBanco = null;

		if (isNotNull(conta.getBanco().getImgLogo())) {
			imgLogoBanco = conta.getBanco().getImgLogo();
		} else {
			if (BancosSuportados.isSuportado(conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				URL url = this.getClass().getResource(
						"/img/" + conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado() + ".png");

				if (isNotNull(url)) {
					imgLogoBanco = ImageIO.read(url);
				}

				if (isNotNull(imgLogoBanco)) {
					// Esta imagem gerada aqui é do tipo java.awt.Image
					conta.getBanco().setImgLogo(imgLogoBanco);

					// Se o banco em questão é suportado nativamente pelo
					// componente, então um alerta será exibido.
					if (log.isDebugEnabled()) {
						log.debug("Banco sem imagem da logo informada. "
								+ "Com base no código de compensação do banco, uma imagem foi "
								+ "encontrada no resource e está sendo utilizada.");
					}
				}
			}
		}
			
		if (isNotNull(imgLogoBanco)) {
			setImageLogo(imgLogoBanco);
		} else {
			// Sem imagem, um alerta é exibido.
			log.warn("Banco sem imagem definida. O nome da instituição será usado como logo.");
			setTextLogo(conta.getBanco().getNome());
		}
	}

	private void setAgenciaCondigoCedente() throws Exception {

		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();

		if (isNotNull(conta.getAgencia())) {
			if (isNotNull(conta.getAgencia().getCodigo()))
				sb.append(conta.getAgencia().getCodigo());

			if (isNotNull(conta.getAgencia().getDigitoVerificador())
					&& StringUtils.isNotBlank(conta.getAgencia().getDigitoVerificador().toString())) {

				sb.append(HIFEN_SEPERADOR);
				sb.append(conta.getAgencia().getDigitoVerificador());
			}
		}

		if (isNotNull(conta.getNumeroDaConta().getCodigoDaConta())) {

			sb.append(" / ");

			sb.append(conta.getNumeroDaConta().getCodigoDaConta());

			if (isNotNull(conta.getNumeroDaConta().getDigitoDaConta())) {

				sb.append(HIFEN_SEPERADOR);
				sb.append(conta.getNumeroDaConta().getDigitoDaConta());
			}
		}

		setText("txtRsAgenciaCodigoCedente", sb.toString());
		setText("txtFcAgenciaCodigoCedente", sb.toString());
	}

	private void setNossoNumero() throws Exception {

		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);

		if (isNotNull(boleto.getTitulo().getNossoNumero())) {
			sb.append(boleto.getTitulo().getNossoNumero());
		}

		if (isNotNull(boleto.getTitulo().getDigitoDoNossoNumero())) {
			sb.append(HIFEN_SEPERADOR + boleto.getTitulo().getDigitoDoNossoNumero());
		}

		setText("txtRsNossoNumero", sb.toString());
		setText("txtFcNossoNumero", sb.toString());
	}

	/**
	 * <p>
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do
	 * boleto atribuídos no map e templante.
	 * </p>
	 * 
	 * @throws Exception
	 * 
	 * @since 0.2
	 */
	private void setImagensNosCampos() throws Exception {

		if (isNotNull(boleto.getImagensExtras())) {

			for (String campo : boleto.getImagensExtras().keySet()) {
				setImagemNoCampo(campo, boleto.getImagensExtras().get(campo));
			}
		}
	}

	/**
	 * <p>
	 * Coloca uma imagem no pdf de acordo com o nome do field no templante.
	 * </p>
	 * 
	 * @param nomeDoCampo
	 * @param imagem
	 * @throws Exception
	 * 
	 * @since 0.2
	 */
	private void setImagemNoCampo(String nomeDoCampo, Image imagem) throws Exception {
		if (StringUtils.isNotBlank(nomeDoCampo) && imagem != null) {
			setImage(nomeDoCampo, imagem);
		}
	}

	/**
	 * <p>
	 * Coloca a logo do passada na ficha de compensação do boleto e no recibo do
	 * sacado.
	 * </p>
	 * 
	 * @param imgLogoBanco
	 * @throws Exception
	 * 
	 * @since 0.2
	 */
	private void setImageLogo(Image imgLogoBanco) throws Exception {

		// RECIBO DO SACADO
		setImagemNoCampo("txtRsLogoBanco", imgLogoBanco);

		// FICHA DE COMPENSAÇÃO
		setImagemNoCampo("txtFcLogoBanco", imgLogoBanco);
	}
	
	protected File getTemplate() {
		return template;
	}

	protected void setTemplate(File template) {
		this.template = template;
	}

	protected void setTemplate(String pathname) {
		setTemplate(new File(pathname));
	}
	
	/**
	 * <p>
	 * Verifica se o template que será utilizado virá do resource ou é externo,
	 * ou seja, se o usuário definiu ou não um template.
	 * </p>
	 * 
	 * @return true caso o template que pode ser definido pelo usuário for null;
	 *         false caso o usuário tenha definido um template.
	 * 
	 * @since
	 */
	protected boolean isTemplateFromResource() {
		
		return isNull(getTemplate());
	}
	
		         
	/** 
	 * <p> 
	 * Coloca a nome do banco na ficha de compensação do boleto e no recibo do 
	 * sacado. 
	 * </p> 
 	 *  
 	 * @param nomeBanco 
 	 * @throws DocumentException 
 	 * @throws IOException  
 	 *  
 	 * @since 0.2 
 	 */ 
	private void setTextLogo(String nomeBanco) throws Exception { 
	
		// RECIBO DO SACADO 
		setText("txtRsLogoBanco", nomeBanco); 

		// FICHA DE COMPENSAÇÃO 
		setText("txtFcLogoBanco", nomeBanco);       
	}   
}
