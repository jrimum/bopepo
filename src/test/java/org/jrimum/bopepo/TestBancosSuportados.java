/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 19:11:33
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 19:11:33
 * 
 */


package org.jrimum.bopepo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a> 
 *
 */
public class TestBancosSuportados{
	
	/**
	 * Quantidade atual 
	 */
	@Test
	public void testQuantidade() {
		
		assertEquals(BancosSuportados.suportados.size(), 18);
	}

	/**
	 * Conferência de códigos 
	 */
	@Test
	public void testGetCodigo() {
		
		//bancos implementados
		
		assertEquals(BancosSuportados.BANCO_DO_BRASIL.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "001");
		assertEquals(BancosSuportados.BANCO_DO_NORDESTE_DO_BRASIL.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "004");
		assertEquals(BancosSuportados.CAIXA_ECONOMICA_FEDERAL.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "104");
		assertEquals(BancosSuportados.BANCO_BRADESCO.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "237");
		assertEquals(BancosSuportados.BANCO_ABN_AMRO_REAL.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "356");
		assertEquals(BancosSuportados.UNIBANCO.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "409");
		assertEquals(BancosSuportados.HSBC.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "399");
		assertEquals(BancosSuportados.BANCO_ITAU.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "341");
		assertEquals(BancosSuportados.BANCO_SAFRA.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "422");
		assertEquals(BancosSuportados.BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "041");
		assertEquals(BancosSuportados.MERCANTIL_DO_BRASIL.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "389");
		assertEquals(BancosSuportados.NOSSA_CAIXA.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "151");
		assertEquals(BancosSuportados.BANCO_DO_ESTADO_DO_ESPIRITO_SANTO.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "021");
		assertEquals(BancosSuportados.BANCO_RURAL.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "453");
		assertEquals(BancosSuportados.BANCO_SANTANDER.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "033");
		assertEquals(BancosSuportados.BANCO_INTEMEDIUM.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "077");
		assertEquals(BancosSuportados.BANCO_SICREDI.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "748");
		assertEquals(BancosSuportados.BANCOOB.create().getCodigoDeCompensacaoBACEN().getCodigoFormatado(), "756");
	}
}
