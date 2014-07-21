package org.jrimum.bopepo.view.info.campo.caixa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.parametro.ParametroCaixaEconomicaFederal;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rômulo Augusto
 */
public class TestBoletoInfoViewCaixaSICOB10 {
	
	private BoletoInfoViewCaixaSICOB10 view;
	
	private Boleto boleto;
	
	@Before
	public void setUp() {
		boleto = BoletoBuilder.defaultValue();
		view = new BoletoInfoViewCaixaSICOB10(mock(ResourceBundle.class), boleto);
	}

	@Test
	public void deve_retornar_carteira_rg_para_cobranca_rapida() {
		boleto.getTitulo().getContaBancaria().getCarteira().setTipoCobranca(TipoDeCobranca.COM_REGISTRO);
		assertEquals("CR", view.getTextoFcCarteira());
	}
	
	@Test
	public void deve_retornar_carteira_sr_para_carteira_sem_registro() {
		boleto.getTitulo().getContaBancaria().getCarteira().setTipoCobranca(TipoDeCobranca.SEM_REGISTRO);
		assertEquals("SR", view.getTextoFcCarteira());
	}
	
	@Test
	public void deve_retornar_agencia_codigo_cliente_no_formato_correto() {
		boleto.getTitulo().getContaBancaria().setAgencia(new Agencia(34));
		boleto.getTitulo().getContaBancaria().getNumeroDaConta().setCodigoDaConta(12345);
		boleto.getTitulo().getContaBancaria().getNumeroDaConta().setDigitoDaConta("0");
		boleto.getTitulo().setParametrosBancarios(new ParametrosBancariosMap(ParametroCaixaEconomicaFederal.CODIGO_OPERACAO, 870));
		
		assertEquals("0034.870.00012345-0", view.getTextoFcAgenciaCodigoCedente());
		assertEquals("0034.870.00012345-0", view.getTextoRsAgenciaCodigoCedente());
	}
}
