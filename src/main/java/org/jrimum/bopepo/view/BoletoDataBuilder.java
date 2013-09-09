/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 27/09/2011 - ‎14:27:10
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 27/09/2011 - ‎14:27:10
 * 
 */

package org.jrimum.bopepo.view;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;
import static org.jrimum.utilix.text.DateFormat.DDMMYYYY_B;
import static org.jrimum.utilix.text.DecimalFormat.MONEY_DD_BR;

import java.awt.Image;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.CodigoDeBarras;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.utilix.Collections;
import org.jrimum.utilix.Exceptions;
import org.jrimum.utilix.Objects;

/**
 * Lê os dados do Boleto e monta-os para uso em {@linkplain #texts()} e {@linkplain #images()}. 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 * @since 0.2
 * 
 * @version 0.2
 */
class BoletoDataBuilder {
	
	private static Logger log = Logger.getLogger(BoletoDataBuilder.class);
	
	private static final String HIFEN_SEPERADOR = "-";
	
	private final ResourceBundle resourceBundle;
	
	private final Map<String,String> text;
	
	private final Map<String,Image> image;
	
	private Boleto boleto;
	
	/**
	 * Modo de instanciação não permitido.
	 * 
	 * @throws IllegalStateException
	 *             Caso haja alguma tentativa de utilização deste construtor.
	 */
	@SuppressWarnings("unused")
	private BoletoDataBuilder(){
		Exceptions.throwIllegalStateException("Instanciação não permitida!");
		resourceBundle = null;
		text = null;
		image = null;
	}
	
	BoletoDataBuilder(ResourceBundle resourceBundle){
		
		Objects.checkNotNull(resourceBundle);
		
		this.resourceBundle = resourceBundle;
		
		text = new WeakHashMap<String, String>();
		image = new WeakHashMap<String, Image>();
	}
	
	BoletoDataBuilder(ResourceBundle resourceBundle, Boleto boleto){
		
		this(resourceBundle);
		Objects.checkNotNull(boleto);
		
		this.boleto = boleto;
		
		build();
	}
	
	Map<String,String> texts(){
		
		return new WeakHashMap<String, String>(text);
	}
	
	Map<String,Image> images(){
		
		return new WeakHashMap<String, Image>(image);
	}
	
	BoletoDataBuilder with(Boleto boleto){
		
		this.boleto = boleto;
		
		text.clear();
		image.clear();
		
		return build();
	}


	/**
	 * Preenche todos os campos com os dados do boleto contido na instância.
	 * 
	 * @return Esta instância após operação
	 * 
	 * @since 0.2
	 */
	private BoletoDataBuilder build(){
		
		setLogotipoDoBanco();
		setCodigoDoBanco();
		setLinhaDigitavel();
		setCedente();
		setAgenciaCodigoCedente();
		setEspecie();
		setQuantidade();
		setUsoBanco();
		setValor();
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
		setCodigoDeBarras();
		setCarteira();
		setCamposExtra();
		setImagensNosCampos();
		
		return this;
	}

	private void setDataProcessamento(){
		
		if(isNotNull(boleto.getDataDeProcessamento())){
			text.put("txtFcDataProcessamento", DDMMYYYY_B.format(boleto.getDataDeProcessamento()));
		}
	}

	private void setAceite(){

		if (isNotNull(boleto.getTitulo().getAceite())) {
			text.put("txtFcAceite", boleto.getTitulo().getAceite().name());
		}
	}

	private void setEspecieDoc(){

		if (isNotNull(boleto.getTitulo().getTipoDeDocumento()) && isNotNull(boleto.getTitulo().getTipoDeDocumento().getSigla())) {
			text.put("txtFcEspecieDocumento", boleto.getTitulo().getTipoDeDocumento().getSigla());
		}
	}

	private void setDataDocumento(){

		if(isNotNull(boleto.getTitulo().getDataDoDocumento())){
			text.put("txtFcDataDocumento", DDMMYYYY_B.format(boleto.getTitulo().getDataDoDocumento()));
		}
	}

	private void setLocalPagamento(){
		
		if(isNotNull(boleto.getLocalPagamento())){
			text.put("txtFcLocalPagamento", (boleto.getLocalPagamento()));
		}
	}

	private void setSacado(){

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
		
		text.put("txtRsSacado", sb.toString());
		text.put("txtFcSacadoL1", sb.toString());

		//clear
		sb.delete(0, sb.length());
		
		Endereco endereco = sacado.getNextEndereco();
		
		setEndereco(endereco, "txtFcSacadoL2", "txtFcSacadoL3", sb);
	}

	private void setSacadorAvalista(){
		
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
			
			text.put("txtFcSacadorAvalistaL1", sb.toString());

			//clear
			sb.delete(0, sb.length());
			
			Endereco endereco = sacadorAvalista.getNextEndereco();

			setEndereco(endereco, "txtFcSacadorAvalistaL2", "txtFcSacadorAvalistaL3", sb);
		}
	}
	
	private void setEndereco(Endereco endereco, String campoEndereco1, String campoEndereco2, StringBuilder sb){
		
		if (isNotNull(endereco)) {
			
			if (isNotBlank(endereco.getBairro())) {
				sb.append(endereco.getBairro());
			}
			
			if (isNotBlank(endereco.getLocalidade())) {
				sb.append(HIFEN_SEPERADOR)
				.append(endereco.getLocalidade());
			}
			
			if (isNotNull(endereco.getUF())) {
				sb.append(" / ")
				.append(endereco.getUF().getNome());
			}

			text.put(campoEndereco1, sb.toString());

			sb.delete(0, sb.length());
			
			if (isNotBlank(endereco.getLogradouro())) {
				sb.append(endereco.getLogradouro());
			}

			if (isNotBlank(endereco.getNumero())) {
				sb.append(", n°: ")
				.append(endereco.getNumero());
			}
			
			if (isNotBlank(endereco.getComplemento())) {
				sb.append(" / ")
				.append(endereco.getComplemento());
			}

			if (isNotNull(endereco.getCEP()) && isNotBlank(endereco.getCEP().getCep())) {
				sb.append(" ")
				.append(HIFEN_SEPERADOR)
				.append(" CEP: ")
				.append(endereco.getCEP().getCep());
			}

			text.put(campoEndereco2, sb.toString());
		}
	}

	private void setInstrucaoAoCaixa(){

		if(isNotNull(boleto.getInstrucao1())){
			text.put("txtFcInstrucaoAoCaixa1", boleto.getInstrucao1());
		}
		if(isNotNull(boleto.getInstrucao2())){
			text.put("txtFcInstrucaoAoCaixa2", boleto.getInstrucao2());
		}
		if(isNotNull(boleto.getInstrucao3())){
			text.put("txtFcInstrucaoAoCaixa3", boleto.getInstrucao3());
		}
		if(isNotNull(boleto.getInstrucao4())){
			text.put("txtFcInstrucaoAoCaixa4", boleto.getInstrucao4());
		}
		if(isNotNull(boleto.getInstrucao5())){
			text.put("txtFcInstrucaoAoCaixa5", boleto.getInstrucao5());
		}
		if(isNotNull(boleto.getInstrucao6())){
			text.put("txtFcInstrucaoAoCaixa6", boleto.getInstrucao6());
		}
		if(isNotNull(boleto.getInstrucao7())){
			text.put("txtFcInstrucaoAoCaixa7", boleto.getInstrucao7());
		}
		if(isNotNull(boleto.getInstrucao8())){
			text.put("txtFcInstrucaoAoCaixa8", boleto.getInstrucao8());
		}
	}

	private void setInstrucaoAoSacado(){

		if(isNotNull(boleto.getInstrucaoAoSacado())){
			text.put("txtRsInstrucaoAoSacado", boleto.getInstrucaoAoSacado());
		}
		
	}

	private void setMoraMulta(){

		if(isNotNull(boleto.getTitulo().getMora())){
		
			text.put("txtRsMoraMulta", MONEY_DD_BR.format(boleto.getTitulo().getMora()));
			text.put("txtFcMoraMulta", MONEY_DD_BR.format(boleto.getTitulo().getMora()));
		}
	}
	
	private void setOutroAcrescimo(){

		if(isNotNull(boleto.getTitulo().getAcrecimo())){
		
			text.put("txtRsOutroAcrescimo", MONEY_DD_BR.format(boleto.getTitulo().getAcrecimo()));
			text.put("txtFcOutroAcrescimo", MONEY_DD_BR.format(boleto.getTitulo().getAcrecimo()));
		}
	}

	private void setOutraDeducao(){
		
		if(isNotNull(boleto.getTitulo().getDeducao())){
			
			text.put("txtRsOutraDeducao", MONEY_DD_BR.format(boleto.getTitulo().getDeducao()));
			text.put("txtFcOutraDeducao", MONEY_DD_BR.format(boleto.getTitulo().getDeducao()));
		}
	}

	private void setDescontoAbatimento(){

		if(isNotNull(boleto.getTitulo().getDesconto())){
			
			text.put("txtRsDescontoAbatimento", MONEY_DD_BR.format(boleto.getTitulo().getDesconto()));
			text.put("txtFcDescontoAbatimento", MONEY_DD_BR.format(boleto.getTitulo().getDesconto()));
		}
	}
	private void setValorDocumento(){

		if(isNotNull(boleto.getTitulo().getValor())){
			
			text.put("txtRsValorDocumento", MONEY_DD_BR.format(boleto.getTitulo().getValor()));
			text.put("txtFcValorDocumento", MONEY_DD_BR.format(boleto.getTitulo().getValor()));
		}
	}

	private void setValorCobrado(){

		if(isNotNull(boleto.getTitulo().getValorCobrado())){
			
			text.put("txtRsValorCobrado", MONEY_DD_BR.format(boleto.getTitulo().getValorCobrado()));
			text.put("txtFcValorCobrado", MONEY_DD_BR.format(boleto.getTitulo().getValorCobrado()));
		}
	}

	/**
	 * Data no formata "dd/mm/yyyy"
	 */
	private void setDataVencimeto(){

		if(isNotNull(boleto.getTitulo().getDataDoVencimento())){
			
			text.put("txtRsDataVencimento",  DDMMYYYY_B.format(boleto.getTitulo().getDataDoVencimento()));
			text.put("txtFcDataVencimento",  DDMMYYYY_B.format(boleto.getTitulo().getDataDoVencimento()));
		}
	}

	private void setAbstractCPRFCedente(){
		
		if (isNotNull(boleto.getTitulo().getCedente().getCPRF())) {
			
			text.put("txtRsCpfCnpj", boleto.getTitulo().getCedente().getCPRF().getCodigoFormatado());
		}
	}

	private void setNumeroDocumento(){

		if(isNotNull(boleto.getTitulo().getNumeroDoDocumento())){
			
			text.put("txtRsNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());
			text.put("txtFcNumeroDocumento", boleto.getTitulo().getNumeroDoDocumento());
		}
	}

	
	private void setCedente(){
		
		if(isNotNull(boleto.getTitulo().getCedente().getNome())){
			
			text.put("txtRsCedente", boleto.getTitulo().getCedente().getNome());
			text.put("txtFcCedente", boleto.getTitulo().getCedente().getNome());
		}
	}
	
	
	private void setCarteira(){

		Carteira carteira = boleto.getTitulo().getContaBancaria().getCarteira();
		
		if (isNotNull(carteira) && isNotNull(carteira.getCodigo())) {
			
			text.put("txtFcCarteira", carteira.getCodigo().toString());
		}
	}	

	private void setQuantidade(){

		text.put("txtRsQuantidade", StringUtils.EMPTY);
		text.put("txtFcQuantidade", StringUtils.EMPTY);
	}

	private void setUsoBanco(){
		
		text.put("txtFcUsoBanco", StringUtils.EMPTY);
	}

	private void setValor(){
		
		text.put("txtFcValor", StringUtils.EMPTY);
	}

	private void setEspecie(){

		if(isNotNull(boleto.getTitulo().getTipoDeMoeda()) && isNotNull(boleto.getTitulo().getTipoDeMoeda())){
			
			text.put("txtRsEspecie", boleto.getTitulo().getTipoDeMoeda().name());
			text.put("txtFcEspecie", boleto.getTitulo().getTipoDeMoeda().name());
		}
	}
	
	private void setCodigoDoBanco(){

		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		
		String codigoCompensacao = conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado();
		String digitoCompensacao = conta.getBanco().getCodigoDeCompensacaoBACEN().getDigito().toString();
		
		text.put("txtRsCodBanco", codigoCompensacao + HIFEN_SEPERADOR + digitoCompensacao);
		text.put("txtFcCodBanco", codigoCompensacao + HIFEN_SEPERADOR + digitoCompensacao);
	}

	private void setAgenciaCodigoCedente(){

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

		text.put("txtRsAgenciaCodigoCedente", sb.toString());
		text.put("txtFcAgenciaCodigoCedente", sb.toString());
	}

	private void setNossoNumero(){

		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);

		if (isNotNull(boleto.getTitulo().getNossoNumero())) {
			sb.append(boleto.getTitulo().getNossoNumero());
		}

		if (isNotNull(boleto.getTitulo().getDigitoDoNossoNumero())) {
			sb.append(HIFEN_SEPERADOR + boleto.getTitulo().getDigitoDoNossoNumero());
		}

		text.put("txtRsNossoNumero", sb.toString());
		text.put("txtFcNossoNumero", sb.toString());
	}
	
	/**
	 * Através da conta bancária será descoberta a imagem que representa o
	 * banco, com base no código do banco.
	 */
	private void setLogotipoDoBanco(){
		
		ContaBancaria conta = boleto.getTitulo().getContaBancaria();
		
		Image imgLogoBanco = null;

		if (isNotNull(conta.getBanco().getImgLogo())) {
			
			imgLogoBanco = conta.getBanco().getImgLogo();

		} else {

			if (BancosSuportados.isSuportado(conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {
				
					String codigoFormatado = conta.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado();
					
					imgLogoBanco = resourceBundle.getLogotipoDoBanco(codigoFormatado);
	
					if (isNotNull(imgLogoBanco)) {

						conta.getBanco().setImgLogo(imgLogoBanco);
	
						if (log.isDebugEnabled()) {
							log.debug(codigoFormatado+"-Banco sem imagem da logo informada. "
									+ "Com base no código de compensação do banco, uma imagem foi "
									+ "encontrada no resource e está sendo utilizada.");
						}
					}
			} 
		}
		
		if (isNotNull(imgLogoBanco)) {
			setImageLogo(imgLogoBanco);
		} else {
			log.warn("Banco sem imagem definida. O nome da instituição será usado como logo.");
			setTextLogo(conta.getBanco().getNome());
		}		
	}
	
	
	/**
	 * <p>
	 * Coloca a logo do passada na ficha de compensação do boleto e no recibo do
	 * sacado.
	 * </p>
	 * 
	 * @param imgLogoBanco
	 * 
	 * @since 0.2
	 */
	private void setImageLogo(Image imgLogoBanco){

		image.put("txtRsLogoBanco",imgLogoBanco);
		image.put("txtFcLogoBanco",imgLogoBanco);	
	}
	
	/**
	 * <p>
	 * Coloca a nome do banco na ficha de compensação do boleto e no recibo do
	 * sacado.
	 * </p>
	 * 
	 * @param nomeBanco
	 * 
	 * @since 0.2
	 */
	private void setTextLogo(String nomeBanco){

		text.put("txtRsLogoBanco",nomeBanco);
		text.put("txtFcLogoBanco",nomeBanco);	
	}	

	private void setLinhaDigitavel(){
		
		text.put("txtRsLinhaDigitavel", boleto.getLinhaDigitavel().write());
		text.put("txtFcLinhaDigitavel", boleto.getLinhaDigitavel().write());
	}
	
	private void setCodigoDeBarras(){
		
		image.put("txtFcCodigoBarra", CodigoDeBarras.valueOf(boleto.getCodigoDeBarras().write()).toImage());
	}
	
	private void setCamposExtra(){

		if (Collections.hasElement(boleto.getTextosExtras())) {
			
			for (Entry<String, String> entry : boleto.getTextosExtras().entrySet()) {
				text.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * <p>
	 * Coloca as imagens dos campos no pdf de acordo com o nome dos campos do boleto atribuídos no map e templante.
	 * </p>
	 * 
	 * @since 0.2
	 */
	private void setImagensNosCampos(){

		if (Collections.hasElement(boleto.getImagensExtras())) {
			
			for (Entry<String, Image> entry : boleto.getImagensExtras().entrySet()) {
				image.put(entry.getKey(), entry.getValue());
			}
		}
	}
}
