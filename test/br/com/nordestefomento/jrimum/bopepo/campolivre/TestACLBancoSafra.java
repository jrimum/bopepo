/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 21/04/2008 - 22:36:47
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 21/04/2008 - 22:36:47
 * 
 */
	
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.xml.internal.ws.util.StringUtils;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.entity.Agencia;
import br.com.nordestefomento.jrimum.domkee.entity.Carteira;
import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.entity.NumeroDaConta;
import br.com.nordestefomento.jrimum.domkee.entity.Pessoa;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;

public class TestACLBancoSafra {
	
	private ICampoLivre clBancoSafra;
	
	private Titulo titulo;

	@Before
	public void setUp() throws Exception {
		
		Pessoa sacado = new Pessoa();
		Pessoa cedente = new Pessoa();

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(EnumBancos.BANCO_SAFRA.newInstance());
		
		contaBancaria.setAgencia(new Agencia(57, "1"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(12345, "7"));
		contaBancaria.setCarteira(new Carteira(123, EnumTipoCobranca.COM_REGISTRO));

		cedente.addContaBancaria(contaBancaria);
		
		titulo = Titulo.getInstance(sacado, cedente);
		titulo.setNumeroDoDocumento("1234567");
		titulo.setNossoNumero("12345678");
	}

	/**
	 * Test method for {@link br.com.nordestefomento.jrimum.bopepo.campolivre.ACLBancoSafra#getInstance(br.com.nordestefomento.jrimum.domkee.entity.Titulo)}.
	 */
	@Test(expected=NotSuporttedCampoLivreException.class)
	public void testGetInstanceNotSuporttedCampoLivreException() {
		
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		conta.getCarteira().setTipoCobranca(null);
		
		clBancoSafra = ACLBancoSafra.getInstance(titulo);
	}

	@Test(expected=CampoLivreException.class)
	public void testGetInstanceCampoLivreException() {
		
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		
		conta.getCarteira().setTipoCobranca(EnumTipoCobranca.COM_REGISTRO);
		//conta.getNumeroDaConta().setDigitoDaConta(null);
		
		clBancoSafra = ACLBancoSafra.getInstance(titulo);
	}
	
	@Test
	public void testGetInstanceCobrancaRegistrada() {
		
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		
		conta.getCarteira().setTipoCobranca(EnumTipoCobranca.COM_REGISTRO);
		
		titulo.getCedente().getContasBancarias().clear();
		titulo.getCedente().addContaBancaria(conta);
		
		clBancoSafra = FactoryCampoLivre.getInstance(titulo);
		
		Assert.assertTrue(clBancoSafra instanceof CLBancoSafraCobrancaRegistrada);
	}
	
	@Test
	public void testGetInstanceCobrancaNaoRegistrada() {
		
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		
		conta.getCarteira().setTipoCobranca(EnumTipoCobranca.SEM_REGISTRO);
		
		titulo.getCedente().getContasBancarias().clear();
		titulo.getCedente().addContaBancaria(conta);
		
		clBancoSafra = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBancoSafra instanceof CLBancoSafraCobrancaNaoRegistrada);
	}
	
	@Test
	public void testWriteCobrancaRegistrada() {
		
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		
		conta.getCarteira().setTipoCobranca(EnumTipoCobranca.COM_REGISTRO);
		
		titulo.getCedente().getContasBancarias().clear();
		titulo.getCedente().addContaBancaria(conta);
		
		//básico feliz
		clBancoSafra = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBancoSafra.write().length() == 25);
		assertEquals("7005710001234570123456782", clBancoSafra.write());
	}
	
	@Test
	public void testWriteCobrancaNaoRegistrada() {
		
		ContaBancaria conta = titulo.getCedente().getContasBancarias().iterator().next();
		
		conta.getCarteira().setTipoCobranca(EnumTipoCobranca.SEM_REGISTRO);
		
		titulo.getCedente().getContasBancarias().clear();
		titulo.getCedente().addContaBancaria(conta);
		
		//básico feliz
		clBancoSafra = FactoryCampoLivre.getInstance(titulo);
		
		assertTrue(clBancoSafra.write().length() == 25);
		assertEquals("7123457000000000123456784", clBancoSafra.write());
	}
}
