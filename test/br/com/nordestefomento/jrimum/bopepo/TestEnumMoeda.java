package br.com.nordestefomento.jrimum.bopepo;

import br.com.nordestefomento.jrimum.domkee.type.EnumMoeda;

import junit.framework.TestCase;

public class TestEnumMoeda extends TestCase {

	public void testGetCodigo() {
		
		int codigo = 2;//primeira moeda:DÓLAR_AMERICANO_COMERCIAL_VENDA;
		
		for(EnumMoeda moeda: EnumMoeda.values()){
			
			assertTrue(moeda.getCodigo() == codigo);
			codigo++;//próxima moeda
		}
			
		
	}

}
