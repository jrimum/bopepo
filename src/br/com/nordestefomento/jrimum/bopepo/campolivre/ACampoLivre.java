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
 * Created at: 30/03/2008 - 18:07:11
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
 * Criado em: 30/03/2008 - 18:07:11
 * 
 */


package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.domkee.type.EnumBanco;
import br.com.nordestefomento.jrimum.utilix.LineOfFields;


/**
 * 
 * Esta classe é responsável por determinar a interface campo livre e também
 * determinar qual implementação de campo livre se aplica a um determinado
 * título.
 * 
 * Uma outra forma de analisar esta classe é sob o prisma de uma Abstract
 * Factory.
 * 
 * <dl>
 * <dt><strong>Field Livre:</strong>
 * <dd>É um espaço reservado no código de barras e a sua implementação varia de
 * banco para banco.</dd>
 * </dt>
 * </dl>
 * 
 * 
 * @author Gabriel Guimarães
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since JMatryx 1.0
 * 
 * @version 1.0
 */
abstract class ACampoLivre extends LineOfFields implements ICampoLivre{
	
	protected ACampoLivre(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}
	
	
	static ICampoLivre getInstance(Titulo titulo) throws NotSuporttedBancoException, NotSuporttedCampoLivreException {
		if(log.isTraceEnabled())
			log.trace("Instanciando Field livre");
		if(log.isDebugEnabled())
			log.debug("titulo instance : "+titulo);
		
		ICampoLivre campoLivre = null;
		ContaBancaria contaBancaria = null;
		EnumBanco banco = null;
		
		
		if(titulo.getCedente().hasContaBancaria()) {
			contaBancaria = titulo.getCedente().getContasBancarias().iterator().next();
			
			if(log.isDebugEnabled())
				log.debug("Campo Livre do Banco: " + contaBancaria.getBanco().getInstituicao());	
			
			if (contaBancaria.getBanco() instanceof EnumBanco) {
				banco = (EnumBanco)contaBancaria.getBanco();
				switch (banco) {
					case BANCO_BRADESCO:
						campoLivre = ACLBradesco.getInstance(titulo);
						break;	
						
					case BANCO_DO_BRASIL:
						campoLivre = ACLBancoDoBrasil.getInstance(titulo);
						break;
						
					case BANCO_ABN_AMRO_REAL:
						campoLivre = ACLBancoReal.getInstance(titulo);
						break;
						
					case CAIXA_ECONOMICA_FEDERAL:
						campoLivre = ACLCaixaEconomicaFederal.getInstance(titulo);
						break;
					
					/*
					 * Se chegar até este ponto, é sinal de que para o banco em
					 * em questão, apesar de estar definido no EnumBanco, não
					 * há implementações de campo livre, logo considera-se o
					 * banco com não suportado.
					 */
					default:
						throw new NotSuporttedBancoException();
				}
				
				/*
				 * Se chegar neste ponto e nenhum campo livre foi definido,
				 * então é sinal de que existe implementações de campo livre
				 * para o banco em questão, só que nenhuma destas implementações
				 * serviu e a classe abstrata responsável por fornecer o campo
				 * livre não gerou a exceção NotSuporttedCampoLivreException.
				 * Trata-se de uma mensagem genérica que será utilizada somente
				 * em último caso.
				 */
				if (campoLivre == null) {
					throw new NotSuporttedCampoLivreException (
						"Não há implementações de campo livre para o banco " +
						banco.getInstituicao() + " compatíveis com as " +
						"caracteríticas do título informado."
					);
				}
			}
			/*
			 * Senão é sinal de que o banco em questão não esté definido em
			 * EnumBanco, logo não haverá implementações de campo livre para
			 * o mesmo, então considera-se o banco com não suportado.
			 */
			else {
				throw new NotSuporttedBancoException();
			}
		}
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Field Livre Instanciado : "+campoLivre);

		
		return campoLivre;
	}
}
