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

import static org.junit.Assert.*;

import org.jrimum.bopepo.campolivre.CampoLivre;
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
	
	protected void setClasseGeradoraDoCampoLivre(Class<? extends CampoLivre> classe) {
		this.classeGeradoraDoCampoLivre = classe;
	}
	
	protected void setCampoLivreValidoAsString(String campoLivreValidoAsString) {
		this.campoLivreValidoAsString = campoLivreValidoAsString;
	}
	
	protected void setCampoLivreToTest(CampoLivre campoLivreToTest) {
		this.campoLivreToTest = campoLivreToTest;
	}
	
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
}
