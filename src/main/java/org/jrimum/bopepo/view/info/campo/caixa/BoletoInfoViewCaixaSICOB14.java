package org.jrimum.bopepo.view.info.campo.caixa;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.parametro.ParametroCaixaEconomicaFederal;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * View para o convênio SICOB, Nosso número com 14 dígitos, da CAIXA.
 * 
 * @author Rômulo Augusto
 */
public class BoletoInfoViewCaixaSICOB14 extends AbstractBoletoInfoCampoView {

	private static final int CODIGO_OPERACAO_PADRAO = 870;

	public BoletoInfoViewCaixaSICOB14(ResourceBundle resourceBundle, Boleto boleto) {
		super(resourceBundle, boleto);
	}
	
	@Override
	public String getTextoFcCarteira() {
		return "SR";
	}
	
	@Override
	public String getTextoFcAgenciaCodigoCedente() {
		Integer agencia = getBoleto().getTitulo().getContaBancaria().getAgencia().getCodigo();
		Integer codigoOperacao = getCodigoOperacao();
		Integer codigoBeneficiario = getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getCodigoDaConta();
		String digitoDaConta = getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getDigitoDaConta();
		
		return String.format("%04d.%03d.%08d-%s", agencia, codigoOperacao, codigoBeneficiario, digitoDaConta);
	}

	private Integer getCodigoOperacao() {
		Titulo titulo = getBoleto().getTitulo();
		Integer codigoOperacao = CODIGO_OPERACAO_PADRAO;
		
		if (titulo.hasParametrosBancarios() && isNotNull(titulo.getParametrosBancarios().getValor(ParametroCaixaEconomicaFederal.CODIGO_OPERACAO))) {
			codigoOperacao = titulo.getParametrosBancarios().getValor(ParametroCaixaEconomicaFederal.CODIGO_OPERACAO);
		}
		
		return codigoOperacao;
	}
	
	@Override
	public String getTextoFcLocalPagamento() {
		String textoFcLocalPagamento = super.getTextoFcLocalPagamento();
		return isBlank(textoFcLocalPagamento) ? "PREFERENCIALMENTE NAS CASAS LOTÉRICAS ATÉ O VALOR LIMITE" : textoFcLocalPagamento;
	}
}
