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


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.utilix.Filler;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;


/**
 * <p>
 * Esta classe tem como finalidade encapsular toda a lógica de criação de um
 * campo livre e de fornecer para o pacote
 * <code>br.com.nordestefomento.jrimum.bopepo.campolivre</code> 
 * um único ponto de acesso ao mesmo.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public final class CampoLivreFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8572635342980404937L;
	
	private static Logger log = Logger.getLogger(CampoLivreFactory.class);

	/**
	 * Devolve um ICampoLivre de acordo com o Banco contido na conta do Cedente. 
	 *  
	 * Caso exista implementação para o banco o retorno terá uma referência não nula.
	 * 
	 * @param titulo
	 * 
	 * @return Uma referência para um ICampoLivre.
	 * @throws NotSuporttedBancoException 
	 * @throws NotSuporttedCampoLivreException 
	 */
	public static ICampoLivre create(Titulo titulo) throws NotSuporttedBancoException, NotSuporttedCampoLivreException {

		return AbstractCampoLivre.create(titulo);
	}
	
	/**
	 * Devolve um ICampoLivre de acordo a partir de uma String.
	 * 
	 * @param strCampoLivre
	 * 
	 * @return Uma referência para um ICampoLivre.
	 * @throws IllegalArgumentException
	 */
	public static ICampoLivre create(String strCampoLivre) throws IllegalArgumentException{
		
		ICampoLivre campoLivre = null;
		
		if (isNotNull(strCampoLivre, "strCampoLivre")) {

			if (StringUtils.isNotBlank(strCampoLivre)) {

				strCampoLivre = StringUtils.strip(strCampoLivre); 
				
				if (strCampoLivre.length() == ICampoLivre.STRING_LENGTH) {

					if (StringUtils.remove(strCampoLivre, ' ').length() == ICampoLivre.STRING_LENGTH) {

						if (StringUtils.isNumeric(strCampoLivre)) {

							campoLivre = new ICampoLivre() {

								private static final long serialVersionUID = -7592488081807235080L;

								Field<String> campo = new Field<String>(StringUtils.EMPTY,
										STRING_LENGTH, Filler.ZERO_LEFT);

								
								public void read(String str) {
									campo.read(str);
								}

								
								public String write() {
									return campo.write();
								}
							};
							
							campoLivre.read(strCampoLivre);
							
						}else{
							
							IllegalArgumentException e = new IllegalArgumentException("O Campo Livre [ " + strCampoLivre + " ] deve ser uma String numérica!");
							
							log.error(StringUtils.EMPTY, e);
							
							throw e;
						}
					}else{
						
						IllegalArgumentException e = new IllegalArgumentException("O Campo Livre [ " + strCampoLivre + " ] não deve conter espaços em branco!");
						
						log.error(StringUtils.EMPTY, e);
						
						throw e;
					}
				}else{
					
					IllegalArgumentException e = new IllegalArgumentException("O tamanho do Campo Livre [ " + strCampoLivre + " ] deve ser igual a 25 e não ["+strCampoLivre.length()+"]!");
					
					log.error(StringUtils.EMPTY, e);
					
					throw e;
				}
			}else{
				
				IllegalArgumentException e = new IllegalArgumentException("O Campo Livre [ " + strCampoLivre + " ] não deve ser vazio!");
				
				log.error(StringUtils.EMPTY, e);
				
				throw e;
			}
		}
		return campoLivre;
	}

	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
