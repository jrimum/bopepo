package org.jrimum.bopepo.view.info.campo.caixa;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.bopepo.view.info.campo.BoletoInfoCampoView;
import org.jrimum.bopepo.view.info.campo.caixa.BoletoInfoViewCaixaFactory;
import org.jrimum.bopepo.view.info.campo.caixa.BoletoInfoViewCaixaSIGCB;
import org.junit.Before;
import org.junit.Test;

public class TestBoletoInfoViewCaixaFactory {
	
	private static final String NN10 = "0123456789";
	private static final String NN14 = "01234567890123";
	private static final String NN15 = "012345678901234";
	private static final String NN17 = "01234567890123456";
	
	private Boleto boleto;
	private ResourceBundle resourceBundle;

	@Before
	public void setUp() {
		boleto = BoletoBuilder.defaultValue();
		resourceBundle = mock(ResourceBundle.class);
	}

	@Test
	public void deve_retornar_implementacao_para_convenio_sigcb() {
		boleto.getTitulo().setNossoNumero(NN15);
		BoletoInfoCampoView view = BoletoInfoViewCaixaFactory.create(resourceBundle, boleto);
		
		assertTrue(view instanceof BoletoInfoViewCaixaSIGCB);
	}
	
	@Test
	public void deve_retornar_implementacao_para_convenio_sinco() {
		boleto.getTitulo().setNossoNumero(NN17);
		BoletoInfoCampoView view = BoletoInfoViewCaixaFactory.create(resourceBundle, boleto);
		
		assertTrue(view instanceof BoletoInfoViewCaixaSINCO);
	}
	
	@Test
	public void deve_retornar_implementacao_para_convenio_sicob_nn_10_digitos() {
		boleto.getTitulo().setNossoNumero(NN10);
		BoletoInfoCampoView view = BoletoInfoViewCaixaFactory.create(resourceBundle, boleto);
		
		assertTrue(view instanceof BoletoInfoViewCaixaSICOB10);
	}
	
	@Test
	public void deve_retornar_implementacao_para_convenio_sicob_nn_14_digitos() {
		boleto.getTitulo().setNossoNumero(NN14);
		BoletoInfoCampoView view = BoletoInfoViewCaixaFactory.create(resourceBundle, boleto);
		
		assertTrue(view instanceof BoletoInfoViewCaixaSICOB14);
	}
}
