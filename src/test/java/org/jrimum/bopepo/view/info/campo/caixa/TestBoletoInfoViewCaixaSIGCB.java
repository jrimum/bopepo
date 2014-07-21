package org.jrimum.bopepo.view.info.campo.caixa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rômulo Augusto
 */
public class TestBoletoInfoViewCaixaSIGCB {
	
	private static final int CARTEIRA_REGISTRADA = 1;
	private static final int CARTEIRA_SEM_REGISTRO = 2;
	private static final String EMISSAO_BENEFICIARIO = "4";
	private static final String NOSSO_NUMERO = "000000000000019";
	private static final String DIGITO_NOSSO_NUMERO = "5";
	
	private BoletoInfoViewCaixaSIGCB view;
	
	private Boleto boleto;
	
	@Before
	public void setUp() {
		boleto = BoletoBuilder.defaultValue();
		boleto.getTitulo().setNossoNumero(NOSSO_NUMERO);
		boleto.getTitulo().setDigitoDoNossoNumero(DIGITO_NOSSO_NUMERO);

		view = new BoletoInfoViewCaixaSIGCB(mock(ResourceBundle.class), boleto);
	}

	@Test
	public void deve_retornar_nosso_numero_no_formato_correto_para_carteira_registrada() {
		boleto.getTitulo().getContaBancaria().getCarteira().setCodigo(CARTEIRA_REGISTRADA);
		
		assertEquals(CARTEIRA_REGISTRADA + EMISSAO_BENEFICIARIO + NOSSO_NUMERO + "-" + DIGITO_NOSSO_NUMERO, view.getTextoFcNossoNumero());
		assertEquals(CARTEIRA_REGISTRADA + EMISSAO_BENEFICIARIO + NOSSO_NUMERO + "-" + DIGITO_NOSSO_NUMERO, view.getTextoRsNossoNumero());
	}
	
	@Test
	public void deve_retornar_nosso_numero_no_formato_correto_para_carteira_sem_registro() {
		boleto.getTitulo().getContaBancaria().getCarteira().setCodigo(CARTEIRA_SEM_REGISTRO);
		
		assertEquals(CARTEIRA_SEM_REGISTRO + EMISSAO_BENEFICIARIO + NOSSO_NUMERO + "-" + DIGITO_NOSSO_NUMERO, view.getTextoFcNossoNumero());
		assertEquals(CARTEIRA_SEM_REGISTRO + EMISSAO_BENEFICIARIO + NOSSO_NUMERO + "-" + DIGITO_NOSSO_NUMERO, view.getTextoRsNossoNumero());
	}
	
	@Test
	public void deve_retornar_carteira_rg_para_registrada() {
		boleto.getTitulo().getContaBancaria().getCarteira().setTipoCobranca(TipoDeCobranca.COM_REGISTRO);
		assertEquals("RG", view.getTextoFcCarteira());
	}
	
	@Test
	public void deve_retornar_carteira_sr_para_carteira_sem_registro() {
		boleto.getTitulo().getContaBancaria().getCarteira().setTipoCobranca(TipoDeCobranca.SEM_REGISTRO);
		assertEquals("SR", view.getTextoFcCarteira());
	}
	
	@Test
	public void deve_retornar_local_de_pagamento_padrao_quando_nenhum_informado() {
		boleto.setLocalPagamento(null);
		assertEquals("PREFERENCIALMENTE NAS CASAS LOTÉRICAS ATÉ O VALOR LIMITE", view.getTextoFcLocalPagamento());
	}
}
