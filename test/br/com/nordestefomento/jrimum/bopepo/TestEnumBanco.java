/**
 * 
 */
package br.com.nordestefomento.jrimum.bopepo;

import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;

import junit.framework.TestCase;

/**
 * @author Gilmar
 *
 */
public class TestEnumBanco extends TestCase {


	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.domkee.type.EnumBanco#getCodigo()}.
	 */
	public void testGetCodigo() {
		
		//bancos implementados
		EnumBanco bancoDoBrasil = EnumBanco.BANCO_DO_BRASIL;
		EnumBanco bradesco = EnumBanco.BANCO_BRADESCO;
		EnumBanco caixa = EnumBanco.CAIXA_ECONOMICA_FEDERAL;
		
		assertEquals(bancoDoBrasil.getCodigoDeCompensacao(), "001");
		assertEquals(bradesco.getCodigoDeCompensacao(), "237");
		assertEquals(caixa.getCodigoDeCompensacao(), "104");
		
	}

}
