package org.jrimum.bopepo.view.info.campo.caixa;

import static org.apache.commons.lang.StringUtils.isBlank;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.parametro.ParametroCaixaEconomicaFederal;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.campo.AbstractBoletoInfoCampoView;

/**
 * View para o convênio SICOB, Nosso número com 14 dígitos, da CAIXA.
 * 
 * @author Rômulo Augusto
 */
public class BoletoInfoViewCaixaSICOB14 extends AbstractBoletoInfoCampoView {

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
		Integer codigoOperacao = getBoleto().getTitulo().getParametrosBancarios().getValor(ParametroCaixaEconomicaFederal.CODIGO_OPERACAO);
		Integer codigoBeneficiario = getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getCodigoDaConta();
		String digitoDaConta = getBoleto().getTitulo().getContaBancaria().getNumeroDaConta().getDigitoDaConta();
		
		return String.format("%04d.%03d.%08d-%s", agencia, codigoOperacao, codigoBeneficiario, digitoDaConta);
	}
	
	@Override
	public String getTextoFcLocalPagamento() {
		String textoFcLocalPagamento = super.getTextoFcLocalPagamento();
		return isBlank(textoFcLocalPagamento) ? "PREFERENCIALMENTE NAS CASAS LOTÉRICAS ATÉ O VALOR LIMITE" : textoFcLocalPagamento;
	}
}
