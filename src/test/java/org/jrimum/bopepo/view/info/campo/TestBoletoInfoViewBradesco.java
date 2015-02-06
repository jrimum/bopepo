package org.jrimum.bopepo.view.info.campo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rômulo Augusto
 */
public class TestBoletoInfoViewBradesco {
	
	private BoletoInfoViewBradesco view;
	private Boleto boleto;
	
	@Before
	public void setUp() {
		boleto = BoletoBuilder.defaultValue();
		view = new BoletoInfoViewBradesco(mock(ResourceBundle.class), boleto);
	}

	@Test
	public void deve_retornar_local_de_pagamento_padrao() {
		boleto.setLocalPagamento(null);
		assertEquals("Pagável Preferencialmente na rede Bradesco ou no Bradesco expresso", view.getTextoFcLocalPagamento());
	}
	
	@Test
	public void deve_retornar_agencia_e_conta_no_campo_agencia_codigo_cliente() {
		boleto.getTitulo().getContaBancaria().setAgencia(new Agencia(1234,"5"));
		boleto.getTitulo().getContaBancaria().setNumeroDaConta(new NumeroDaConta(1234567, "0"));
		
		assertEquals("1234-5 / 1234567-0", view.getTextoFcAgenciaCodigoCedente());
		assertEquals("1234-5 / 1234567-0", view.getTextoRsAgenciaCodigoCedente());
	}
	
 	@Test
	public void deve_retornar_carteira_e_nosso_numero_e_dv_com_traco() {
 		boleto.getTitulo().getContaBancaria().setCarteira(new Carteira(9));
		boleto.getTitulo().setNossoNumero("00000123456");
		boleto.getTitulo().setDigitoDoNossoNumero("2");
		
		assertEquals("09/00000123456-2", view.getTextoFcNossoNumero());
		assertEquals("09/00000123456-2", view.getTextoRsNossoNumero());
	}
}
