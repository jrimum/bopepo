/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 23/09/2011 - 17:03:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 23/09/2011 - 17:03:00
 * 
 */

package org.jrimum.bopepo.pdf;

import java.io.InputStream;

import org.jrimum.utilix.ClassLoaders;

/**
 * Classe para centralizar ponto de acesso aos resources usados nos testes, não instanciável.
 * 
 * <p>
 * Note que um mesmo resouce é usado e mais de uma classe de teste.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 */
public abstract class Resources {
	
	/**
	 * Mesmo título para ambos os arquivos.
	 */
	public static final String DOCUMENT_TITLE = "Arquivo para insumo em testes";

	/**
	 * Arquivo no classpath sem fields.
	 * 
	 * @return arquivo pronto para uso
	 */
	public static final InputStream crieInputStreamParaArquivoSemCampos(){
		return ClassLoaders.getResourceAsStream("ArquivoSemCampos.pdf");
	}

	/**
	 * Arquivo no classpath com 3 campos:
	 * 
	 * <ul>
	 * <li>nomeDoTestador:"JRiboy Brasileiro da Ordem do Progresso"</li>
	 * <li>funcaoDoTestador:"Developer"</li>
	 * <li>nomeDoTeste:"A definir..."</li>
	 * </u>
	 * 
	 * @return arquivo pronto para uso
	 */
	public static final InputStream crieInputStreamParaArquivoComCampos(){
		return ClassLoaders.getResourceAsStream("ArquivoComCampos.pdf");
	}
}
