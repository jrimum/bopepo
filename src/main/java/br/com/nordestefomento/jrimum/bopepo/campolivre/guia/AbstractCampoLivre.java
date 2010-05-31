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

package br.com.nordestefomento.jrimum.bopepo.campolivre.guia;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;
import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNull;

import org.apache.log4j.Logger;

import br.com.nordestefomento.jrimum.bopepo.guia.BancoSuportado;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Arrecadacao;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.Convenio;
import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoSeguimento;
import br.com.nordestefomento.jrimum.utilix.AbstractLineOfFields;
import br.com.nordestefomento.jrimum.utilix.ObjectUtil;

/**
 * <p>
 * Esta classe é responsável por determinar a interface campo livre e também
 * determinar qual implementação de campo livre se aplica a um determinada
 * arrecadação.
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
 * @author Misael Barreto
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
abstract class AbstractCampoLivre extends AbstractLineOfFields implements CampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4605730904122445595L;
	
	private static Logger log = Logger.getLogger(ObjectUtil.class);

		
	protected AbstractCampoLivre(Integer fieldsLength, TipoSeguimento tipoSeguimento) {
		super();
		
		Integer STRING_LENGTH;
		STRING_LENGTH = CampoLivreUtil.getTamanhoCorreto(tipoSeguimento);	
		
		setStringLength(STRING_LENGTH);
		setFieldsLength(fieldsLength);
	}
	
	/**
	 * 
	 * @param arrecadacao
	 * @return
	 * 
	 * @throws NotSupportedBancoException Caso o banco informado no convênio não 
	 * tenha nenhuma implementação de campo livre.
	 * @throws NotSupportedCampoLivreException Caso exista implementações de campo livre para o banco informa
	 * no convênio, mas nenhuma dessas implementações foram adequadas para os dados da arrecadação.
	 * @throws CampoLivreException Caso ocorra algum problema na geração do campo livre.
	 */
	static CampoLivre create(Arrecadacao arrecadacao) {

		if (log.isTraceEnabled())
			log.trace("Instanciando Campo livre");
		if (log.isDebugEnabled())
			log.debug("titulo instance : " + arrecadacao);

	
		CampoLivre campoLivre = null;
		BancoSuportado bancoSuportado = null;
		Convenio convenio = null;
		
		try{		
			convenio = arrecadacao.getConvenio();

			if (isConvenioOK(convenio)) {
				
				if (log.isDebugEnabled())
					log.debug("Campo Livre do Banco: " + convenio.getBanco().getNome());
	
				if (BancoSuportado.isSuportado(convenio.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {
					bancoSuportado = BancoSuportado.suportados.get(convenio.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado());
										
					switch (bancoSuportado) {
						case BANCO_DO_BRASIL:
							campoLivre = AbstractCLBancoDoBrasil.create(arrecadacao);
							break;
					}
				} else {
					/*
					 * Se chegar até este ponto, é sinal de que para o banco em
					 * questão, apesar de estar definido no EnumBancos, não há
					 * implementações de campo livre, logo considera-se o banco com
					 * não suportado.
					 */
					throw new NotSupportedBancoException();
				}
	
				/*
				 * Se chegar neste ponto e nenhum campo livre foi definido, então é
				 * sinal de que existe implementações de campo livre para o banco em
				 * questão, só que nenhuma destas implementações serviu e a classe
				 * abstrata responsável por fornecer o campo livre não gerou a
				 * exceção NotSupportedCampoLivreException. Trata-se de uma mensagem
				 * genérica que será utilizada somente em último caso.
				 */
				if (isNull(campoLivre)) {
					throw new NotSupportedCampoLivreException(
							"Não há implementações de campo livre para o banco "
									+ convenio.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()
									+ " compatíveis com as "
									+ "caracteríticas da arrecadação informada.");
				}
			}
		
		} catch(Exception e) {
			
			if(e instanceof CampoLivreException)
				throw (CampoLivreException)e;
			else
				throw new CampoLivreException(e);		
		}

		if (log.isDebugEnabled() || log.isTraceEnabled())
			log.trace("Campo Livre Instanciado : " + campoLivre);

		return campoLivre;
	}

	
	private static boolean isConvenioOK(Convenio convenio) {
		return (  isNotNull(convenio) && isNotNull(convenio.getBanco())  );
	}
	
	
	@Override
	public String toString() {
		return ObjectUtil.toString(this);
	}
}
