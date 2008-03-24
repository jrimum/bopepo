package br.com.nordestefomento.jrimum.bopepo;

import junit.framework.Test;
import junit.framework.TestSuite;
import br.com.nordestefomento.jrimum.bopepo.campolivre.TestCLBancoDoBrasilNN11;
import br.com.nordestefomento.jrimum.bopepo.campolivre.TestCLBancoDoBrasilNN17;
import br.com.nordestefomento.jrimum.bopepo.campolivre.TestCLBradesco;
import br.com.nordestefomento.jrimum.bopepo.campolivre.TestCLCaixaEconomicaFederalSINCO;

public class TestSuiteBopepo {

public static Test suite() {
	    
		TestSuite suite= new TestSuite();
		
		suite.addTestSuite(TestBoleto.class);
		suite.addTestSuite(TestCodigoDeBarra.class);
		suite.addTestSuite(TestLinhaDigitavel.class);
		suite.addTestSuite(TestCLBancoDoBrasilNN11.class);
		suite.addTestSuite(TestCLBancoDoBrasilNN17.class);
		suite.addTestSuite(TestCLBradesco.class);
		suite.addTestSuite(TestCLCaixaEconomicaFederalSINCO.class);
	    
	    return suite;
	}
}
