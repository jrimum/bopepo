package br.com.nordestefomento.jrimum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.nordestefomento.jrimum.bopepo.TestSuiteBopepo;
import br.com.nordestefomento.jrimum.domkee.TestSuiteDomkee;
import br.com.nordestefomento.jrimum.utilix.TestSuiteUtilix;
import br.com.nordestefomento.jrimum.vallia.TestSuiteVallia;

import static org.junit.Assert.*;


@RunWith(Suite.class)
@SuiteClasses
( 
		{ 
			TestSuiteUtilix.class,
			TestSuiteVallia.class,
			TestSuiteDomkee.class,
			TestSuiteBopepo.class
		}
)
public class TestJRimum {
	
	/*
	 * The class remains completely empty, being used only as a holder for the
	 * above annotations
	 */
	
	@Test
	public void testProjeto() {
		assertTrue("Testes Do Projeto.", true);
	}

}
