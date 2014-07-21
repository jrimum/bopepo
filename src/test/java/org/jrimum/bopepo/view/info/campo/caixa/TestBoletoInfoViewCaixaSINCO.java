package org.jrimum.bopepo.view.info.campo.caixa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.excludes.BoletoBuilder;
import org.jrimum.bopepo.view.ResourceBundle;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rômulo Augusto
 */
public class TestBoletoInfoViewCaixaSINCO {
	
	private static final int CONSTANTE = 9;
	private static final String NOSSO_NUMERO = "00000001000105270";
	private static final String DIGITO_NOSSO_NUMERO = "8";
	
	private BoletoInfoViewCaixaSINCO view;
	
	private Boleto boleto;
	
	@Before
	public void setUp() {
		boleto = BoletoBuilder.defaultValue();
		view = new BoletoInfoViewCaixaSINCO(mock(ResourceBundle.class), boleto);
	}

	@Test
	public void deve_retornar_nosso_numero_no_formato_correto() {
		boleto.getTitulo().setNossoNumero(NOSSO_NUMERO);
		boleto.getTitulo().setDigitoDoNossoNumero(DIGITO_NOSSO_NUMERO);
		
		assertEquals(CONSTANTE + NOSSO_NUMERO + "-" + DIGITO_NOSSO_NUMERO, view.getTextoFcNossoNumero());
		assertEquals(CONSTANTE + NOSSO_NUMERO + "-" + DIGITO_NOSSO_NUMERO, view.getTextoRsNossoNumero());
	}
	
	@Test
	public void deve_retornar_carteira_01() {
		assertEquals("01", view.getTextoFcCarteira());
	}
}
