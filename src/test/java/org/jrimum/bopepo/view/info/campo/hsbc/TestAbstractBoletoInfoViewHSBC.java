package org.jrimum.bopepo.view.info.campo.hsbc;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.campo.BoletoInfoCampoView;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rômulo Augusto
 */
public class TestAbstractBoletoInfoViewHSBC {
	
	private Boleto boleto;

	@Before
	public void setUp() {
		boleto = BoletoBuilder.defaultValue();
	}

	@Test
	public void deve_retornar_instancia_correta_para_cobranca_registrada() {
		boleto.getTitulo().getContaBancaria().getCarteira().setTipoCobranca(TipoDeCobranca.COM_REGISTRO);
		BoletoInfoCampoView view = AbstractBoletoInfoViewHSBC.create(mock(ResourceBundle.class), boleto);

		assertTrue(view instanceof BoletoInfoViewHSBCRegistrada);
	}
	
	@Test
	public void deve_retornar_instancia_correta_para_cobranca_nao_registrada() {
		boleto.getTitulo().getContaBancaria().getCarteira().setTipoCobranca(TipoDeCobranca.SEM_REGISTRO);
		BoletoInfoCampoView view = AbstractBoletoInfoViewHSBC.create(mock(ResourceBundle.class), boleto);

		assertTrue(view instanceof BoletoInfoViewHSBCNaoRegistrada);
	}
}
