/**
 * 
 */
package org.jrimum.bopepo.campolivre;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.hsbc.TipoIdentificadorCNR;
import org.junit.Before;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a> 
 */
public class TestCLHSBCCobrancaNaoRegistrada extends AbstractCampoLivreBaseTest<CLHSBCCobrancaNaoRegistrada> {

	@Before
	public void setUp(){

		ContaBancaria contaBancaria = new ContaBancaria();

		contaBancaria.setBanco(BancosSuportados.HSBC.create());
		contaBancaria.setAgencia(new Agencia(1234, "1"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(8351202,"2"));
		contaBancaria.setCarteira(new Carteira(1, TipoDeCobranca.SEM_REGISTRO));

		titulo = new Titulo(contaBancaria, new Sacado("S"), new Cedente("C"));
		titulo.setNossoNumero("0000239104761");
		titulo.setDataDoVencimento(new GregorianCalendar(2008, Calendar.JULY, 4).getTime());
		titulo.setParametrosBancarios(new ParametrosBancariosMap(TipoIdentificadorCNR.class.getName(), TipoIdentificadorCNR.COM_VENCIMENTO));

		createCampoLivreToTest();

		setCampoLivreValidoAsString("8351202000023910476118682");
	}

}
