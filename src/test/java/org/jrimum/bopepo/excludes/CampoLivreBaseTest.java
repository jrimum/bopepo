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
 * Created at: 15/02/2010 - 17:42:20
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
 * Criado em: 15/02/2010 - 17:42:20
 * 
 */

package org.jrimum.bopepo.excludes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.CampoLivreException;
import org.jrimum.bopepo.campolivre.CampoLivreFactory;
import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.junit.Test;

/**
 * <p>
 * Classe base para os testes de campos livres. Contém os métodos
 * básicos de testes de qualquer campo livre.
 * </p>
 * <p>
 * Todos os testes de campo livre devem herdar desta classe.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 *
 * @since 0.2
 * 
 * @version 0.2
 */
public class CampoLivreBaseTest {
	
	private Class<? extends CampoLivre> classeGeradoraDoCampoLivre;
	
	private String campoLivreValidoAsString;
	
	private CampoLivre campoLivreToTest;
	
	public CampoLivre getCampoLivreToTest() {
		
		return campoLivreToTest;
	}

	@Test
	public void seCriacaoDoCampoLivreOcorreSemFalha() {
		
		assertNotNull(campoLivreToTest);
	}
	
	@Test
	public void seTamanhoDoCampoLivreEscritoIgualA25() {
		
		assertEquals(25, campoLivreToTest.write().length());
	}
	
	@Test
	public void seClasseDaInstaciaDoCampoLivreEstaCorreta() {
		
		assertEquals(classeGeradoraDoCampoLivre, campoLivreToTest.getClass());
	}
	
	@Test
	public void seCampoLivreEscritoEstaCorreto() {
		
		assertEquals(campoLivreValidoAsString, campoLivreToTest.write());
	}
	
	/*
	 * Testes para uso específico
	 */
	
	protected void seNaoPermiteAgenciaNula(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setAgencia(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected void seNaoPermiteAgenciaComCodigoZero(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setAgencia(new Agencia(-0));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected void seNaoPermiteAgenciaComCodigoNegativo(Titulo titulo) throws IllegalArgumentException{

		//uma exceção deve ser lançada aqui
		titulo.getContaBancaria().setAgencia(new Agencia(-1));
	}
	
	protected void seNaoPermiteNumeroDaAgenciaComDigitosAcimaDoLimite(Titulo titulo, int limiteAcima) throws CampoLivreException {

		titulo.getContaBancaria().setAgencia(new Agencia(limiteAcima));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteCarteiraNula(Titulo titulo) throws CampoLivreException {

		titulo.getContaBancaria().setCarteira(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected void seNaoPermiteCarteiraComCodigoNegativo(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setCarteira(new Carteira(-1));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteCarteiraComCodigoAcimaDoLimite(Titulo titulo, int limiteAcima) throws CampoLivreException{

		titulo.getContaBancaria().setCarteira(new Carteira(limiteAcima));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteNossoNumeroNulo(Titulo titulo) throws CampoLivreException{

		titulo.setNossoNumero(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteNossoNumeroComBrancos(Titulo titulo, int nnLength) throws CampoLivreException{

		StringBuilder nnBlank = new StringBuilder(nnLength);
	
		for(int i = 1; i <= nnLength; i++){
			nnBlank.append(" ");
		}
		
		titulo.setNossoNumero(nnBlank.toString());

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteNossoNumeroComEspacos(Titulo titulo, int nnLength) throws CampoLivreException{

		//Nosso número randômico
		String nnRadom = nossoNumeroRadom(nnLength);
		
		char[] nn = nnRadom.toCharArray();
		
		//Com espaço (+ ou -) no meio
		nn[nnRadom.length()/2] = ' ';
		
		titulo.setNossoNumero(nn.toString());

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteNossoNumeroComTamanhoDiferenteDoEspecificado(Titulo titulo, int nnOutLength) throws CampoLivreException{

		titulo.setNossoNumero(nossoNumeroRadom(nnOutLength));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteNumeroDaContaNulo(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected void seNaoPermiteNumeroDaContaComCodigoZero(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(0));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteNumeroDaContaComCodigoNegativo(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(-1));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected void seNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(Titulo titulo, int limiteAcima) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(limiteAcima));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected void seNaoPermiteParametroBancarioNulo(Titulo titulo) throws CampoLivreException{

		titulo.setParametrosBancarios(null);

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected void seNaoPermiteParametroBancarioAusente(Titulo titulo) throws CampoLivreException{

		titulo.setParametrosBancarios(new ParametrosBancariosMap());

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected void seNaoPermiteParametroBancarioSemValor(Titulo titulo, String parametro) throws IllegalArgumentException{
		
		//uma exceção deve ser lançada aqui
		titulo.setParametrosBancarios(new ParametrosBancariosMap(parametro, null));
	}
	
	protected void seNaoPermiteValorDoTituloNulo(Titulo titulo) throws NullPointerException{

		//uma exceção deve ser lançada aqui
		titulo.setValor(null);
	}
	
	protected void seNaoPermiteValorDoTituloNegativo(Titulo titulo) throws CampoLivreException{

		titulo.setValor(BigDecimal.valueOf(-23.4150));

		setCampoLivreToTest(CampoLivreFactory.create(titulo));

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	/*
	 * Acessores
	 */
	
	protected void setClasseGeradoraDoCampoLivre(Class<? extends CampoLivre> classe) {
		this.classeGeradoraDoCampoLivre = classe;
	}
	
	protected void setCampoLivreValidoAsString(String campoLivreValidoAsString) {
		this.campoLivreValidoAsString = campoLivreValidoAsString;
	}
	
	protected void setCampoLivreToTest(CampoLivre campoLivreToTest) {
		this.campoLivreToTest = campoLivreToTest;
	}
	
	/**
	 * Gera um nosso numero randomicamente com o tamanho determinado.
	 * 
	 * @param nnLength
	 * @return geraNossoNumero
	 */
	protected String nossoNumeroRadom(int nnLength){
		
		//Nosso número randômico
		return new BigDecimal(Math.random()).movePointRight(nnLength).setScale(0,RoundingMode.DOWN).toString();
	}
	
	/**
	 * Simplesmente escreve o campo livre executando o método {@code write()}.
	 * 
	 * @return campo livre
	 */
	protected String writeCampoLivre(){
		
		return campoLivreToTest.write();
	}
}
