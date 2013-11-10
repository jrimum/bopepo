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
 * Created at: 30/03/2008 - 18:09:58
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
 * Criado em: 30/03/2008 - 18:09:58
 * 
 */

package org.jrimum.bopepo.campolivre;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.containsAny;
import static org.apache.commons.lang.StringUtils.isNumeric;
import static org.apache.commons.lang.StringUtils.strip;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Strings;


/**
 * <p>
 * Esta classe tem como finalidade encapsular toda a lógica de criação de um
 * campo livre e de fornecer para o pacote
 * <code>org.jrimum.bopepo.campolivre</code> 
 * um único ponto de acesso ao mesmo.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a> 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public final class CampoLivreFactory {

	/**
	 * <p>
	 * Devolve um <code>CampoLivre</code> de acordo com o Banco contido na conta bancária do título.
	 * </p> 
	 * <p>
	 * Caso exista implementação para o banco o retorno terá uma referência não nula.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @return Uma referência para um CampoLivre.
	 * @throws NotSupportedBancoException 
	 * @throws NotSupportedCampoLivreException 
	 */
	public static CampoLivre create(Titulo titulo) throws NotSupportedBancoException, NotSupportedCampoLivreException {

		return AbstractCampoLivre.create(titulo);
	}
	
	/**
	 * Devolve um CampoLivre a partir de uma String.
	 * 
	 * @param strCampoLivre
	 * 
	 * @return Referência para uma instância anônima de CampoLivre.
	 * 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public static CampoLivre create(String strCampoLivre) {
		Objects.checkNotNull(strCampoLivre);
		
		strCampoLivre = strip(strCampoLivre);
		
		Strings.checkNotBlank(strCampoLivre, "O Campo Livre não deve ser vazio!");
		 
		Objects.checkArgument(strCampoLivre.length() == CampoLivre.STRING_LENGTH, "O tamanho do Campo Livre [ " + strCampoLivre + " ] deve ser igual a 25 e não ["+strCampoLivre.length()+"]!");
		Objects.checkArgument(!containsAny(strCampoLivre, " "), "O Campo Livre [ " + strCampoLivre + " ] não deve conter espaços em branco!");
		Objects.checkArgument(isNumeric(strCampoLivre),"O Campo Livre [ " + strCampoLivre + " ] deve ser uma String numérica!");

		return valueOf(strCampoLivre);
	}

	private static CampoLivre valueOf(String strCampoLivre) {
		 CampoLivre campoLivre = new CampoLivre() {
			private static final long serialVersionUID = -7592488081807235080L;

			FixedField<String> campo = new FixedField<String>(EMPTY,
					STRING_LENGTH, Fillers.ZERO_LEFT);

			public void read(String str) {
				campo.read(str);
			}

			public String write() {
				return campo.write();
			}
		};
		campoLivre.read(strCampoLivre);
		return campoLivre;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toString(this);
	}
	
}
