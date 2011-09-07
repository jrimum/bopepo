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

package org.jrimum.bopepo.campolivre;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jrimum.domkee.financeiro.banco.ParametrosBancariosMap;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
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
public abstract class AbstractCampoLivreBaseTest <CL extends CampoLivre>{
	
	protected Titulo titulo;
	
	private String campoLivreValidoAsString;
	
	private CampoLivre campoLivreToTest;
	
	public void setUp(){

		titulo = new Titulo(new ContaBancaria(), new Sacado("S"), new Cedente("C"));
	}
	
	/*
	 * Testes para obrigatórios de todos os campos livres.
	 */
	
	@Test
	public final void seCriacaoDoCampoLivreOcorreSemFalha() {
		
		assertNotNull(campoLivreToTest);
	}
	
	@Test
	public final void seTamanhoDoCampoLivreEscritoIgualA25() {
		
		assertEquals(25, campoLivreToTest.write().length());
	}
	
	@Test
	public final void seClasseDaInstaciaDoCampoLivreEstaCorreta() {
		
		@SuppressWarnings("unchecked")
		Class<CL> classeGeradoraDoCampoLivre = (Class<CL>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		assertEquals(classeGeradoraDoCampoLivre, campoLivreToTest.getClass());
	}
	
	@Test
	public final void seCampoLivreEscritoEstaCorreto() {
		
		assertEquals(campoLivreValidoAsString, campoLivreToTest.write());
	}
	
	/*
	 * Testes para uso específico
	 */
	
	protected final void seNaoPermiteAgenciaNula(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setAgencia(null);

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteAgenciaComCodigoZero(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setAgencia(new Agencia(-0));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteAgenciaComCodigoNegativo(Titulo titulo) throws IllegalArgumentException{

		//uma exceção deve ser lançada aqui
		titulo.getContaBancaria().setAgencia(new Agencia(-1));
	}
	
	protected final void seNaoPermiteNumeroDaAgenciaComDigitosAcimaDoLimite(Titulo titulo, int limiteAcima) throws CampoLivreException {

		titulo.getContaBancaria().setAgencia(new Agencia(limiteAcima));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteDigitoDaAgenciaNulo(Titulo titulo) throws CampoLivreException {
		
		titulo.getContaBancaria().setAgencia(new Agencia(1));
		
		createCampoLivreToTest();
		
		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteDigitoDaAgenciaNaoNumerico(Titulo titulo) throws CampoLivreException {
		
		titulo.getContaBancaria().setAgencia(new Agencia(1,"X"));
		
		createCampoLivreToTest();
		
		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteCarteiraNula(Titulo titulo) throws CampoLivreException {

		titulo.getContaBancaria().setCarteira(null);

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteCarteiraSemTipoDeCobranca(Titulo titulo) throws CampoLivreException{
		
		titulo.getContaBancaria().setCarteira(new Carteira());
		
		createCampoLivreToTest();
		
		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteCarteiraComCodigoNegativo(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setCarteira(new Carteira(-1));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteCarteiraComCodigoAcimaDoLimite(Titulo titulo, int limiteAcima) throws CampoLivreException{

		titulo.getContaBancaria().setCarteira(new Carteira(limiteAcima));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteNossoNumeroNulo(Titulo titulo) throws CampoLivreException{

		titulo.setNossoNumero(null);

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteNossoNumeroComBrancos(Titulo titulo, int nnLength) throws CampoLivreException{

		StringBuilder nnBlank = new StringBuilder(nnLength);
	
		for(int i = 1; i <= nnLength; i++){
			nnBlank.append(" ");
		}
		
		titulo.setNossoNumero(nnBlank.toString());

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteNossoNumeroComEspacos(Titulo titulo, int nnLength) throws CampoLivreException{

		//Nosso número randômico
		String nnRadom = nossoNumeroRadom(nnLength);
		
		char[] nn = nnRadom.toCharArray();
		
		//Com espaço (+ ou -) no meio
		nn[nnRadom.length()/2] = ' ';
		
		titulo.setNossoNumero(nn.toString());

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteNossoNumeroComTamanhoDiferenteDoEspecificado(Titulo titulo, int nnOutLength) throws CampoLivreException{

		titulo.setNossoNumero(nossoNumeroRadom(nnOutLength));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteNumeroDaContaNulo(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(null);

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteNumeroDaContaComCodigoZero(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(0));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteNumeroDaContaComCodigoNegativo(Titulo titulo) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(-1));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteNumeroDaContaComCodigoAcimaDoLimite(Titulo titulo, int limiteAcima) throws CampoLivreException{

		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(limiteAcima));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteDigitoDaContaNulo(Titulo titulo) throws CampoLivreException {
		
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(1));
		
		createCampoLivreToTest();
		
		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteDigitoDaContaVazio(Titulo titulo) throws CampoLivreException {
		
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(1,EMPTY));
		
		createCampoLivreToTest();
		
		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteDigitoDaContaNegativo(Titulo titulo) throws CampoLivreException {
		
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(1,"-1"));
		
		createCampoLivreToTest();
		
		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}

	protected final void seNaoPermiteDigitoDaContaNaoNumerico(Titulo titulo) throws CampoLivreException {
		
		titulo.getContaBancaria().setNumeroDaConta(new NumeroDaConta(1,"X"));
		
		createCampoLivreToTest();
		
		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteParametroBancarioNulo(Titulo titulo) throws CampoLivreException{

		titulo.setParametrosBancarios(null);

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteParametroBancarioAusente(Titulo titulo) throws CampoLivreException{

		titulo.setParametrosBancarios(new ParametrosBancariosMap());

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	protected final void seNaoPermiteParametroBancarioSemValor(Titulo titulo, String parametro) throws IllegalArgumentException{
		
		//uma exceção deve ser lançada aqui
		titulo.setParametrosBancarios(new ParametrosBancariosMap(parametro, null));
	}
	
	protected final void seNaoPermiteValorDoTituloNulo(Titulo titulo) throws NullPointerException{

		//uma exceção deve ser lançada aqui
		titulo.setValor(null);
	}
	
	protected final void seNaoPermiteValorDoTituloNegativo(Titulo titulo) throws CampoLivreException{

		titulo.setValor(BigDecimal.valueOf(-23.4150));

		createCampoLivreToTest();

		//uma exceção deve ser lançada aqui
		writeCampoLivre();
	}
	
	/*
	 * Obtenção de novas instâncias.
	 */
	
	public final void createCampoLivreToTest() {
		this.campoLivreToTest = CampoLivreFactory.create(titulo);
	}
	
	/*
	 * Acessores
	 */
	
	/**
	 * Simplesmente escreve o campo livre executando o método {@code write()}.
	 * 
	 * @return campo livre
	 */
	public final String writeCampoLivre(){
		
		return campoLivreToTest.write();
	}
	
	protected final void setCampoLivreValidoAsString(String campoLivreValidoAsString) {
		this.campoLivreValidoAsString = campoLivreValidoAsString;
	}
	
	/*
	 * Helpers
	 */
	
	/**
	 * Gera um nosso numero randomicamente com o tamanho determinado.
	 * 
	 * @param nnLength
	 * @return geraNossoNumero
	 */
	private String nossoNumeroRadom(int nnLength){
		
		//Nosso número randômico
		return new BigDecimal(Math.random()).movePointRight(nnLength).setScale(0,RoundingMode.DOWN).toString();
	}
	
}
