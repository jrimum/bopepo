package org.jrimum.bopepo.view.info.campo.caixa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.parametro.ParametroCaixaEconomicaFederal;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rômulo Augusto
 */
public class TestBoletoInfoViewCaixaSICOB14 {
	
	private BoletoInfoViewCaixaSICOB14 view;
	
	private Boleto boleto;
	
	@Before
	public void setUp() {
		boleto = BoletoBuilder.defaultValue();
		view = new BoletoInfoViewCaixaSICOB14(mock(ResourceBundle.class), boleto);
	}

	@Test
	public void deve_retornar_carteira_sr() {
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
	
	@Test
	public void deve_retornar_agencia_codigo_cliente_no_formato_correto_quando_parametro_bancario_nao_informado() {
		boleto.getTitulo().getContaBancaria().setAgencia(new Agencia(34));
		boleto.getTitulo().getContaBancaria().getNumeroDaConta().setCodigoDaConta(12345);
		boleto.getTitulo().getContaBancaria().getNumeroDaConta().setDigitoDaConta("0");
		
		assertEquals("0034.870.00012345-0", view.getTextoFcAgenciaCodigoCedente());
		assertEquals("0034.870.00012345-0", view.getTextoRsAgenciaCodigoCedente());
	}
	
	@Test
	public void deve_retornar_local_de_pagamento_padrao_quando_nenhum_informado() {
		boleto.setLocalPagamento(null);
		assertEquals("PREFERENCIALMENTE NAS CASAS LOTÉRICAS ATÉ O VALOR LIMITE", view.getTextoFcLocalPagamento());
	}
}
