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

package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;
import static org.jrimum.domkee.financeiro.banco.febraban.Banco.isCodigoDeCompensacaoOK;

import org.apache.log4j.Logger;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.AbstractLineOfFields;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Strings;

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
 * banco para banco.</dd></dt>
 * </dl>
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="mailto:lukas.antunes@virtualsistemas.com.br">Lukas
 *         Antunes</a> - Colaborador com o banco Intermedium (077)
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a> -
 *         Colaborador com o banco Rural (453)
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
	
	private static Logger log = Logger.getLogger(Objects.class);
	
	/**
	 * Nosso número com 10 posições.
	 */
	static final int NN10 = 10;
	
	/**
	 * Nosso número com 11 posições.
	 */
	static final int NN11 = 11;
	
	/**
	 * Nosso número com 15 posições.
	 */
	static final int NN15 = 15;
	
	/**
	 * Nosso número com 17 posições.
	 */
	static final int NN17 = 17;

	/**
	 * <p>Subclasses não precisam definir o tamanho.</p>
	 */
	private AbstractCampoLivre(Integer fieldsLength, Integer stringLength) {
		
		super(fieldsLength, stringLength);
	}
	
	/**
	 * <p>Cria um campo livre com um determinado número de campos</p>
	 * 
	 * @param fieldsLength - Número de campos
	 */
	protected AbstractCampoLivre(Integer fieldsLength) {
		
		super (fieldsLength, CampoLivre.STRING_LENGTH);
	}

	/**
	 * <p>
	 * Cria um campo livre a partir dos dados contidos no título fornecido.
	 * </p>
	 * 
	 * @param titulo
	 *            com todos os dados para a geração do campo livre
	 * @return instância de campo livre ou nulo.
	 * @throws NotSupportedBancoException
	 *             Caso o banco informado na conta bancária não tenha nenhuma
	 *             implementação de campo livre.
	 * @throws NotSupportedCampoLivreException
	 *             Caso exista implementações de campo livre para o banco
	 *             informa na conta bancária, mas nenhuma dessas implementações
	 *             foram adequadas para os dados do título.
	 * @throws CampoLivreException
	 *             Caso ocorra algum problema na geração do campo livre.
	 */
	static CampoLivre create(Titulo titulo) {

		if (log.isTraceEnabled()){
			
			log.trace("Instanciando Campo livre");
		}
		if (log.isDebugEnabled()){
			
			log.debug("titulo instance : " + titulo);
		}

		try{
		
			checkTitulo(titulo);
			checkContaBancaria(titulo);
			checkBanco(titulo);
			
			if (log.isDebugEnabled()){
				
				log.debug(format("Campo Livre do Banco: %s", titulo.getContaBancaria().getBanco().getNome()));
			}
			
			if (BancosSuportados.isSuportado( titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				BancosSuportados banco = BancosSuportados.suportados.get( titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado());

				switch (banco) {

					case BANCO_BRADESCO:
						return AbstractCLBradesco.create(titulo);
					
					case BANCO_DO_BRASIL:
						return AbstractCLBancoDoBrasil.create(titulo);
	
					case BANCO_ABN_AMRO_REAL:
						return AbstractCLBancoReal.create(titulo);
	
					case CAIXA_ECONOMICA_FEDERAL:
						return AbstractCLCaixaEconomicaFederal.create(titulo);
	
					case HSBC:
						return AbstractCLHSBC.create(titulo);
						
					case UNIBANCO:
						return AbstractCLUnibanco.create(titulo);
	
					case BANCO_ITAU:
						return AbstractCLItau.create(titulo);
	
					case BANCO_SAFRA:
						return AbstractCLBancoSafra.create(titulo);
	
					case BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL:
						return AbstractCLBanrisul.create(titulo);
						
					case MERCANTIL_DO_BRASIL:
						return AbstractCLMercantilDoBrasil.create(titulo);
						
					case NOSSA_CAIXA:
						return AbstractCLNossaCaixa.create(titulo);
					
					case BANCO_DO_ESTADO_DO_ESPIRITO_SANTO:
						return AbstractCLBanestes.create(titulo);
						
					case BANCO_RURAL:
						return AbstractCLBancoRural.create(titulo);
						
					case BANCO_SANTANDER:
						return AbstractCLSantander.create(titulo);
						
					case BANCO_INTEMEDIUM:
						return AbstractCLBancoIntermedium.create(titulo);
					default:
						/*
						 * Se chegar neste ponto e nenhum campo livre foi definido, então é
						 * sinal de que existe implementações de campo livre para o banco em
						 * questão, só que nenhuma destas implementações serviu e a classe
						 * abstrata responsável por fornecer o campo livre não gerou a
						 * exceção NotSupportedCampoLivreException. Trata-se de uma mensagem
						 * genérica que será utilizada somente em último caso.
						 */
						throw new NotSupportedCampoLivreException(
								"Não há implementações de campo livre para o banco "
										+ titulo.getContaBancaria().getBanco()
												.getCodigoDeCompensacaoBACEN().getCodigoFormatado()
										+ " compatíveis com as "
										+ "caracteríticas do título informado.");
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
		} catch(CampoLivreException e) {
			/*
			 * Caso seja uma exceção esperada.
			 */
			throw e;
			
		} catch(Exception e) {
			/*
			 * Encapsula-se ualquer outra exceção. 
			 */
			throw new CampoLivreException(e);
		}
	}
	
	/**
	 * <p>
	 * Gera o campo livre a parir dos campos armazenados sem verificar se está
	 * compatível com número de fields declarado pelo campo livre. Isso implica
	 * que a string retornada poderá ser menor do que 25 caracteres.
	 * </p>
	 * 
	 * @return string a partir dos campos contidos até o momento.
	 * 
	 * @since 0.2
	 */
	final String writeFields() {

		StringBuilder campoLivreAtual = new StringBuilder();
		
		for(Field<?> f : this){
			campoLivreAtual.append(f.write());
		}
		
		return campoLivreAtual.toString();
	}

	/*
	 * Validações inicias.
	 */
	
	private static void checkTitulo(Titulo titulo){
		
		Objects.checkNotNull(titulo, "Título não pode ser nulo!");
	}
	
	private static void checkContaBancaria(Titulo titulo) {
		
		Objects.checkNotNull(titulo.getContaBancaria(), "Conta bancária do título não pode ser nula!");
	}
	
	private static void checkBanco(Titulo titulo) {
		
		Objects.checkNotNull(titulo.getContaBancaria().getBanco(), "Banco da conta bancária do título não pode ser nulo!");
		
		if(!isCodigoDeCompensacaoOK(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())){
			
			throw new IllegalArgumentException(String.format("Código de compensação [%s] inválido!", titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()));
		}
	}

	/*
	 * Validações para subclasses.
	 */
	
	final static void checkCarteira(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getCarteira(), "Carteira da conta bancária do título não pode ser nula!");
	}
	
	final static void checkRegistroDaCarteira(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getCarteira().getTipoCobranca(), "Tipo de cobrança (COM ou SEM registro) da carteira não pode ser nulo!");
	}
	
	final static void checkCodigoDaCarteira(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getCarteira().getCodigo(), "Código da carteira não pode ser nulo!");
		
		if(titulo.getContaBancaria().getCarteira().getCodigo() < 1){
			
			throw new IllegalArgumentException(format("Código da carteira deve ser um número inteiro natural positivo e não [%s].",titulo.getContaBancaria().getCarteira().getCodigo()));
		}
	}
	
	final static void checkNumeroDaConta(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getNumeroDaConta(), "Número da conta bancária do título não pode ser nulo!");
	}
	
	final static void checkCodigoDoNumeroDaConta(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), "Código do número da conta bancária não pode ser nulo!");
		
		if(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() < 1){
			
			throw new IllegalArgumentException(format("Código do número da conta bancária deve ser um número inteiro natural positivo e não [%s].",titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta()));
		}
	}
	
	final static void checkDigitoDoCodigoDoNumeroDaConta(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), "Dígito do código do número da conta bancária não pode ser nulo!");
		Strings.checkNotBlank(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), "Dígito do código do número da conta bancária não pode ser vazio!");
	}
	
	final static void checkNossoNumero(Titulo titulo){
		
		Objects.checkNotNull(titulo.getNossoNumero(), "Nosso número do título não pode ser nulo!");
		Strings.checkNotBlank(titulo.getNossoNumero(), "Nosso número do título não pode ser vazio!");
	}
	
	final static void checkDigitoDoNossoNumero(Titulo titulo){
		
		Objects.checkNotNull(titulo.getDigitoDoNossoNumero(), "Dígito do nosso número do título não pode ser nulo!");
		Strings.checkNotBlank(titulo.getDigitoDoNossoNumero(), "Dígito do nosso número do título não pode ser vazio!");
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return Objects.toString(this);
	}
}
