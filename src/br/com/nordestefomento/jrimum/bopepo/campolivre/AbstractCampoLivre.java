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

import static br.com.nordestefomento.jrimum.domkee.banco.febraban.Banco.isCodigoDeCompensacaoOK;
import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;
import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNull;

import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.bopepo.EnumBancos;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.banco.febraban.Titulo;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;
import br.com.nordestefomento.jrimum.utilix.AbstractLineOfFields;

/**
 * <p>
 * Esta classe é responsável por determinar a interface campo livre e também
 * determinar qual implementação de campo livre se aplica a um determinado
 * título.
 * </p>
 * 
 * <p>
 * Uma outra forma de analisar esta classe é sob o prisma de uma Abstract
 * Factory.
 * </p>
 * 
 * <p>
 * <dl>
 * <dt><strong>Field Livre:</strong>
 * <dd>É um espaço reservado no código de barras e a sua implementação varia de
 * banco para banco.</dd>
 * </dt>
 * </dl>
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordeste-fomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCampoLivre extends AbstractLineOfFields implements CampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4605730904122445595L;
	
	private static Logger log = Logger.getLogger(ObjectUtil.class);

	protected AbstractCampoLivre(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static CampoLivre create(Titulo titulo)
			throws NotSuporttedBancoException, NotSuporttedCampoLivreException, CampoLivreException {

		if (log.isTraceEnabled())
			log.trace("Instanciando Campo livre");
		if (log.isDebugEnabled())
			log.debug("titulo instance : " + titulo);

		CampoLivre campoLivre = null;
		ContaBancaria contaBancaria = null;
		EnumBancos enumBanco = null;
		
		try{
		
		contaBancaria = titulo.getContaBancaria();

		if (log.isDebugEnabled())
			log.debug("Campo Livre do Banco: "
					+ contaBancaria.getBanco().getNome());

		/*
		 * A conta bancária passada não é sincronizada.
		 */
		if (isContaBacariaOK(contaBancaria)) {

			if (EnumBancos.isSuportado(contaBancaria.getBanco()
					.getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				enumBanco = EnumBancos.suportados.get(contaBancaria.getBanco()
						.getCodigoDeCompensacaoBACEN().getCodigoFormatado());

				switch (enumBanco) {

				case BANCO_BRADESCO:
					campoLivre = AbstractCLBradesco.create(titulo);
					break;

				case BANCO_DO_BRASIL:
					campoLivre = AbstractCLBancoDoBrasil.create(titulo);
					break;

				case BANCO_ABN_AMRO_REAL:
					campoLivre = AbstractCLBancoABNAmroReal.create(titulo);
					break;

				case CAIXA_ECONOMICA_FEDERAL:
					campoLivre = AbstractCLCaixaEconomicaFederal.create(titulo);
					break;

				case HSBC:
					campoLivre = AbstractCLHSBC.create(titulo);
					break;
					
				case UNIBANCO:
					campoLivre = AbstractCLUnibanco.create(titulo);
					break;

				case BANCO_ITAU:
					campoLivre = AbstractCLItau.create(titulo);
					break;

				case BANCO_SAFRA:
					campoLivre = AbstractCLBancoSafra.create(titulo);
					break;

				case BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL:
					campoLivre = AbstractCLBanrisul.create(titulo);
					break;
					
				case MERCANTIL_DO_BRASIL:
					campoLivre = AbstractCLMercantilDoBrasil.create(titulo);
					break;
					
				case NOSSA_CAIXA:
					campoLivre = AbstractCLNossaCaixa.create(titulo);
					break;
				
				case BANCO_DO_ESTADO_DO_ESPIRITO_SANTO:
					campoLivre = AbstractCLBanestes.create(titulo);
					break;
					
				}
			} else {
				/*
				 * Se chegar até este ponto, é sinal de que para o banco em em
				 * questão, apesar de estar definido no EnumBancos, não há
				 * implementações de campo livre, logo considera-se o banco com
				 * não suportado.
				 */
				throw new NotSuporttedBancoException();
			}

			/*
			 * Se chegar neste ponto e nenhum campo livre foi definido, então é
			 * sinal de que existe implementações de campo livre para o banco em
			 * questão, só que nenhuma destas implementações serviu e a classe
			 * abstrata responsável por fornecer o campo livre não gerou a
			 * exceção NotSuporttedCampoLivreException. Trata-se de uma mensagem
			 * genérica que será utilizada somente em último caso.
			 */
			if (isNull(campoLivre)) {
				throw new NotSuporttedCampoLivreException(
						"Não há implementações de campo livre para o banco "
								+ contaBancaria.getBanco()
										.getCodigoDeCompensacaoBACEN().getCodigoFormatado()
								+ " compatíveis com as "
								+ "caracteríticas do título informado.");
			}
		}
		
		}catch(Exception e){
			if(e instanceof CampoLivreException)
				throw (CampoLivreException)e;
			else
				throw new CampoLivreException(e);		
		}

		if (log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Campo Livre Instanciado : " + campoLivre);

		return campoLivre;
	}

	/**
	 * <p>
	 * Verifica se a conta bancária passada está ok em relação aos atributos
	 * usados nessa na composição do campo livre.
	 * </p>
	 * 
	 * @param conta
	 * @return se ok.
	 * 
	 * @since 0.2
	 */
	private static boolean isContaBacariaOK(ContaBancaria conta) {

		return (isNotNull(conta, "contaBancaria")
				&& isNotNull(conta.getBanco(), "Banco") && isCodigoDeCompensacaoOK(conta
				.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()));

	}
	
	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
