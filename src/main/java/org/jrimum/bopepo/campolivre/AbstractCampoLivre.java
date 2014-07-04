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
import static java.math.BigDecimal.ZERO;
import static org.jrimum.domkee.financeiro.banco.febraban.Banco.isCodigoDeCompensacaoOK;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.domkee.financeiro.banco.ParametroBancario;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.FixedField;
import org.jrimum.texgit.type.component.BlockOfFields;
import org.jrimum.utilix.Objects;
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
 * @author <a href="mailto:pporto@gmail.com">Paulo Porto</a> - 
 * 		   Colaborador com o Banco do Nordeste do Brasil (004).
 * @author <a href="mailto:fabianojustino@gmail.com">Fabiano Carrijo</a> - 
 * 		   Colaborador com o Banco Citibank (756).
 * @author <a href="mailto:contato@douglasramiro.com.br">Douglas Ramiro</a> - 
 * 		   Colaborador com o Banco de Brasília (070).
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCampoLivre extends BlockOfFields implements CampoLivre {

	/**
	 * {@code serialVersionUID = 4605730904122445595L}
	 */
	private static final long serialVersionUID = 4605730904122445595L;
	
	/**
	 * Looger.
	 */
	private static Logger log = Logger.getLogger(Objects.class);
	
	/**
	 * Nosso número com 7 posições.
	 */
	static final int NN7 = 7;

	/**
	 * Nosso número com 8 posições.
	 */
	static final int NN8 = 8;
	
	/**
	 * Nosso número com 9 posições.
	 */
	static final int NN9 = 9;
	
	/**
	 * Nosso número com 10 posições.
	 */
	static final int NN10 = 10;
	
	/**
	 * Nosso número com 11 posições.
	 */
	static final int NN11 = 11;
	
	/**
	 * Nosso número com 14 posições.
	 */
	static final int NN14 = 14;

	/**
	 * Nosso número com 15 posições.
	 */
	static final int NN15 = 15;
	
	/**
	 * Nosso número com 17 posições.
	 */
	static final int NN17 = 17;

	/**
	 * Subclasses não precisam definir o tamanho.
	 */
	@SuppressWarnings("unused")
	private AbstractCampoLivre(Integer fieldsLength, Integer stringLength) {
		super(null,null);
	}

	/**
	 * Cria um campo livre com um determinado número de campos
	 * 
	 * @param fieldsLength
	 *            - Número de campos
	 */
	protected AbstractCampoLivre(Integer fieldsLength) {
		super();
		setLength(CampoLivre.STRING_LENGTH);
		setSize(fieldsLength);
	}

	/**
	 * Cria um campo livre a partir dos dados contidos no título fornecido.
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
	protected static CampoLivre create(Titulo titulo) throws NotSupportedBancoException,
			NotSupportedCampoLivreException, CampoLivreException {

		if (log.isTraceEnabled()){
			
			log.trace("Instanciando Campo livre");
		}
		if (log.isDebugEnabled()){
			
			log.debug("titulo instance : " + titulo);
		}

		try{
		
			checkTituloNotNull(titulo);
			checkContaBancariaNotNull(titulo);
			checkBancoNotNull(titulo);
			
			if (log.isDebugEnabled()){
				
				log.debug(format("Campo Livre do Banco: %s", titulo.getContaBancaria().getBanco().getNome()));
			}
			
			if (BancosSuportados.isSuportado(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado())) {

				final BancosSuportados banco = BancosSuportados.suportados.get( titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado());

				switch (banco) {

					case BANCO_BRADESCO: return AbstractCLBradesco.create(titulo);
					case BANCO_DO_BRASIL: return AbstractCLBancoDoBrasil.create(titulo);
					case BANCO_DO_NORDESTE_DO_BRASIL: return AbstractCLBancoDoNordesteDoBrasil.create(titulo);
					case BANCO_ABN_AMRO_REAL: return AbstractCLBancoReal.create(titulo);
					case CAIXA_ECONOMICA_FEDERAL: return AbstractCLCaixaEconomicaFederal.create(titulo);
					case HSBC: return AbstractCLHSBC.create(titulo);
					case UNIBANCO: return AbstractCLUnibanco.create(titulo);
					case BANCO_ITAU: return AbstractCLItau.create(titulo);
					case BANCO_SAFRA: return AbstractCLBancoSafra.create(titulo);
					case BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL: return AbstractCLBanrisul.create(titulo);
					case MERCANTIL_DO_BRASIL: return AbstractCLMercantilDoBrasil.create(titulo);
					case BANCO_DO_ESTADO_DO_ESPIRITO_SANTO: return AbstractCLBanestes.create(titulo);
					case BANCO_RURAL: return AbstractCLBancoRural.create(titulo);
					case BANCO_SANTANDER: return AbstractCLSantander.create(titulo);
					case BANCO_INTEMEDIUM: return AbstractCLBancoIntermedium.create(titulo);
					case BANCO_SICREDI: return AbstractCLSicredi.create(titulo);
					case BANCOOB: return AbstractCLBancoob.create(titulo);
					case CITIBANK: return AbstractCLBancoCitibank.create(titulo); 
					case BANCO_DE_BRASILIA: return AbstractCLBancoDeBrasilia.create(titulo);
					case CECRED: return AbstractCLCecred.create(titulo);
						
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
			 * Encapsula-se qualquer outra exceção. 
			 */
			throw new CampoLivreException(e);
		}
	}
	
	/**
	 * <p>
	 * Constrói um campo livre após executar os métodos
	 * {@link AbstractCampoLivre#checkValues(Titulo)} e {@link AbstractCampoLivre#addFields(Titulo)}, retornando em
	 * seguida esta instância pronta para escrita.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @return a instância pronta para escrita
	 * 
	 * @since 0.2
	 */
	protected final CampoLivre build(Titulo titulo) {

		checkValues(titulo);

		addFields(titulo);

		return this;
	}
	
	/**
	 * <p>
	 * Usado pelo método {@link AbstractCampoLivre#build(Titulo)} para verificar a consistência do
	 * campo livre. Se algum inconsistência for verificada, este método deverá
	 * lança-la.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected abstract void checkValues(Titulo titulo);
	
	/**
	 * <p>
	 * Usado pelo método {@link AbstractCampoLivre#build(Titulo)}, adiciona os campos do campo
	 * livre deixando-o pronto para escrita.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected abstract void addFields(Titulo titulo);
	
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
	protected final String writeFields() {

		StringBuilder campoLivreAtual = new StringBuilder();
		
		for(FixedField<?> f : this){
			campoLivreAtual.append(f.write());
		}
		
		return campoLivreAtual.toString();
	}

	/*
	 * Validações inicias.
	 */
	
	/**
	 * <p>
	 * Verifica se o título não é nulo, senão lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private static void checkTituloNotNull(Titulo titulo){
		
		Objects.checkNotNull(titulo, "Título não pode ser nulo!");
	}
	
	/**
	 * <p>
	 * Verifica se a conta bancária do título não é nula, senão lança uma
	 * {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private static void checkContaBancariaNotNull(Titulo titulo) {
		
		Objects.checkNotNull(titulo.getContaBancaria(), "Conta bancária do título não pode ser nula!");
	}
	
	/**
	 * <p>
	 * Verifica se o banco da conta bancária do título não é nulo, senão lança
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	private static void checkBancoNotNull(Titulo titulo) {
		
		Objects.checkNotNull(titulo.getContaBancaria().getBanco(), "Banco da conta bancária do título não pode ser nulo!");
		
		boolean expression = isCodigoDeCompensacaoOK(titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()); 
		
		Objects.checkArgument(expression, format("Código de compensação [%s] inválido!", titulo.getContaBancaria().getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado()));
	}

	/*
	 * Validações para subclasses.
	 */
	
	/**
	 * <p>
	 * Verifica se a carteira da conta bancária do título não é nula, senão
	 * lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkCarteiraNotNull(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getCarteira(), "Carteira da conta bancária do título não pode ser nula!");
	}
	
	/**
	 * <p>
	 * Verifica se o tipo da carteira da conta bancária do título não é nulo,
	 * senão lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkRegistroDaCarteiraNotNull(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getCarteira().getTipoCobranca(), "Tipo de cobrança (COM ou SEM registro) da carteira não pode ser nulo!");
	}
	
	/**
	 * <p>
	 * Verifica se o código da carteira da conta bancária do título não é nulo e
	 * se é um número > 0, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkCodigoDaCarteira(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getCarteira().getCodigo(), "Código da carteira não pode ser nulo!");
		
		boolean expression = titulo.getContaBancaria().getCarteira().getCodigo() > 0; 
		
		Objects.checkArgument(expression, format("Código da carteira deve ser um número inteiro natural positivo e não [%s].",titulo.getContaBancaria().getCarteira().getCodigo()));
	}
	
	/**
	 * <p>
	 * Verifica se o código da carteira da conta bancária do título é um número
	 * menor que ou igual ao limite informado, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param limite - Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	protected final static void checkCodigoDaCarteiraMenorOuIgualQue(Titulo titulo, int limite){
		
		boolean expression = titulo.getContaBancaria().getCarteira().getCodigo() <= limite;
		
		Objects.checkArgument(expression,format("Código [%s] da carteira deve ser um número menor que ou igual a [%s].", titulo.getContaBancaria().getCarteira().getCodigo(), limite));
	}
	
	/**
	 * <p>
	 * Verifica se a agência da conta bancária do título não é nula, senão lança
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkAgenciaNotNull(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getAgencia(), "Agência bancária do título não pode ser nula!");
	}
	
	/**
	 * <p>
	 * Verifica se o código do número da agência bancária não é nulo e se é
	 * um número > 0, caso contrário lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkCodigoDaAgencia(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getAgencia().getCodigo(), "Código da agência bancária não pode ser nulo!");
		
		boolean expression = titulo.getContaBancaria().getAgencia().getCodigo() > 0; 
		
		Objects.checkArgument(expression, format("Código da agência bancária deve ser um número inteiro natural positivo e não [%s].",titulo.getContaBancaria().getAgencia().getCodigo()));
	}

	/**
	 * <p>
	 * Verifica se o código do número da agência da conta bancária do título é
	 * um número menor que ou igual ao limite informado, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param limite
	 *            - Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	protected final static void checkCodigoDaAgenciaMenorOuIgualQue(Titulo titulo, int limite){

		boolean expression = titulo.getContaBancaria().getAgencia().getCodigo() <= limite;
		
		Objects.checkArgument(expression , format("Código [%s] da agência deve ser um número menor que ou igual a [%s].", titulo.getContaBancaria().getAgencia().getCodigo(), limite));
	}
	
	/**
	 * <p>
	 * Verifica se o dígito verificador da agência da conta bancária não é nulo,
	 * não é vazio e se é numérico, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkDigitoDoCodigoDaAgencia(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getAgencia().getDigitoVerificador(), "Dígito verificador da agência bancária não pode ser nulo!");
		Strings.checkNotBlank(titulo.getContaBancaria().getAgencia().getDigitoVerificador(), format("Dígito verificador [\"%s\"] da agência bancária não pode ser vazio!",titulo.getContaBancaria().getAgencia().getDigitoVerificador()));
		Strings.checkNotNumeric(titulo.getContaBancaria().getAgencia().getDigitoVerificador(), format("Nesse contexto o dígito verificador [\"%s\"] da agência bancária deve ser numérico!", titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta()));
	}
	
	/**
	 * <p>
	 * Verifica se o número da conta da conta bancária do título não é nulo,
	 * senão lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkNumeroDaContaNotNull(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getNumeroDaConta(), "Número da conta bancária do título não pode ser nulo!");
	}
	
	/**
	 * <p>
	 * Verifica se o código do do número da conta bancária não é nulo e se é um
	 * número > 0, caso contrário lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkCodigoDoNumeroDaConta(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), "Código do número da conta bancária não pode ser nulo!");
		
		boolean expression = titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() > 0;
		
		Objects.checkArgument(expression, format("Código do número da conta bancária deve ser um número inteiro natural positivo e não [%s].", titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta()));
	}
	
	/**
	 * <p>
	 * Verifica se o código do número da conta bancária do título é um número
	 * menor que ou igual ao limite informado, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param limite - Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	protected final static void checkCodigoDoNumeroDaContaMenorOuIgualQue(Titulo titulo, int limite){
		
		boolean expression = titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta() <= limite; 

		Objects.checkArgument(expression, format("Código [%s] do número da conta deve ser um número menor que ou igual a [%s].", titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), limite));
	}
	
	/**
	 * <p>
	 * Verifica se o dígito verificador do número da conta bancária não é nulo,
	 * não é vazio e se é numérico, caso contrário lança uma {@code
	 * IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkDigitoDoCodigoDoNumeroDaConta(Titulo titulo){
		
		Objects.checkNotNull(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), "Dígito verificador do número da conta bancária não pode ser nulo!");
		Strings.checkNotBlank(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), format("Dígito verificador [\"%s\"] do número da conta bancária não pode ser vazio!", titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta()));
		Strings.checkNotNumeric(titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), format("Nesse contexto o dígito verificador [\"%s\"] do número da conta deve ser numérico!", titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta()));
	}
	
	/**
	 * <p>
	 * Verifica se onosso número do título não é nulo, não é vazio e se é
	 * numérico, caso contrário lança uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkNossoNumero(Titulo titulo){
		
		Objects.checkNotNull(titulo.getNossoNumero(), "Nosso número do título não pode ser nulo!");
		Strings.checkNotBlank(titulo.getNossoNumero(), format("Nosso número [\"%s\"] do título não pode ser vazio!", titulo.getNossoNumero()));
		Strings.checkNotNumeric(titulo.getNossoNumero(), format("Nosso número [\"%s\"] do título deve conter somente dígitos numéricos!", titulo.getNossoNumero()));
	}
	
	/**
	 * <p>
	 * Verifica se o nosso número do título tem o tamanho determinado, caso
	 * contrário lança uma {@code IllegalArgumentException} com a mensagem
	 * <tt>"Tamanho do nosso número [%s] diferente do esperado [%s]!"</tt>.
	 * </p>
	 * 
	 * @param titulo
	 * @param length
	 *            - Tamanho que deve ser
	 * 
	 * @since 0.2
	 */
	protected final static void checkTamanhoDoNossoNumero(Titulo titulo, int length) {

		checkTamanhoNossoNumero(titulo, length, format(
				"Tamanho [%s] do nosso número [\"%s\"] diferente do esperado [%s]!",
				StringUtils.length(titulo.getNossoNumero()), titulo.getNossoNumero(), length));
	}
	
	/**
	 * <p>
	 * Verifica se o nosso número do título tem o tamanho determinado, caso
	 * contrário lança uma {@code IllegalArgumentException} com a mensagem
	 * determinada.
	 * </p>
	 * 
	 * @param titulo
	 * @param length
	 *            - Tamanho que deve ser
	 * @param msg
	 *            - Mensagem para erro
	 * 
	 * @since 0.2
	 */
	protected final static void checkTamanhoNossoNumero(Titulo titulo, int length, String msg){
		
		Objects.checkArgument(titulo.getNossoNumero().length() == length, msg);
	}
	
	/**
	 * <p>
	 * Verifica se o dígito verificador do nosso número do título não é nulo,
	 * não é vazio e se é numérico (natural positivo), caso contrário lança uma
	 * {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkDigitoDoNossoNumero(Titulo titulo){
		
		Objects.checkNotNull(titulo.getDigitoDoNossoNumero(), "Dígito verificador do nosso número do título não pode ser nulo!");
		Strings.checkNotBlank(titulo.getDigitoDoNossoNumero(), format("Dígito verificador [\"%s\"] do nosso número do título não pode ser vazio!", titulo.getDigitoDoNossoNumero()));
		Strings.checkNotNumeric(titulo.getDigitoDoNossoNumero(), format("Nesse contexto o dígito verificador [\"%s\"] do nosso número deve ser um número inteiro positivo!", titulo.getDigitoDoNossoNumero()));
	}
	
	/**
	 * <p>
	 * Verifica se o dígito do nosso número do título tem o tamanho determinado, caso
	 * contrário lança uma {@code IllegalArgumentException} com a mensagem
	 * <tt>"Tamanho [%s] do dígito do nosso número [\"%s\"] diferente do esperado [%s]!"</tt>.
	 * </p>
	 * 
	 * @param titulo
	 * @param length
	 *            - Tamanho que deve ser
	 * 
	 * @since 0.2
	 */
	protected final static void checkTamanhoDigitoDoNossoNumero(Titulo titulo, int length) {

		checkTamanhoDigitoDoNossoNumero(titulo, length, format(
				"Tamanho [%s] do dígito do nosso número [\"%s\"] diferente do esperado [%s]!",
				StringUtils.length(titulo.getDigitoDoNossoNumero()), titulo.getDigitoDoNossoNumero(), length));
	}
	
	/**
	 * <p>
	 * Verifica se o dígito do nosso número do título tem o tamanho determinado, caso
	 * contrário lança uma {@code IllegalArgumentException} com a mensagem
	 * determinada.
	 * </p>
	 * 
	 * @param titulo
	 * @param length
	 *            - Tamanho que deve ser
	 * @param msg
	 *            - Mensagem para erro
	 * 
	 * @since 0.2
	 */
	protected final static void checkTamanhoDigitoDoNossoNumero(Titulo titulo, int length, String msg){
		
		Objects.checkArgument(titulo.getDigitoDoNossoNumero().length() == length, msg);
	}
	
	/**
	 * <p>
	 * Verifica se o valor do título não é nulo e é positivo, caso contrário lança uma
	 * {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @since 0.2
	 */
	protected final static void checkValor(Titulo titulo){
		
		Objects.checkNotNull(titulo.getValor(), "Valor do título não pode ser nulo!");
		Objects.checkArgument(titulo.getValor().compareTo(ZERO) >= 0, format("O valor do título deve ser um número positivo ou zero e não [%s].",titulo.getValor()));
	}
	
	/**
	 * <p>
	 * Verifica se o título contém {@code ParametrosBancariosMap} e se este
	 * contém um valor não é nulo do parâmetro determinado, caso contrário lança
	 * uma {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param param
	 *            Parâmetro a ser validado
	 * 
	 * @since 0.2
	 */
	protected final static void checkParametroBancario(Titulo titulo, ParametroBancario<?> param){
		
		Objects.checkNotNull(titulo.getParametrosBancarios(), format("O parâmetro bancário [\"%s\"] é necessário! [titulo.getParametrosBancarios() == null]",param));
		Objects.checkArgument(titulo.getParametrosBancarios().contemComNome(param),format("Parâmetro bancário [\"%s\"] não encontrado!",param));
		Objects.checkNotNull(titulo.getParametrosBancarios().getValor(param), format("Parâmetro bancário [\"%s\"] não contém valor!", param));
	}
	
	/**
	 * <p>
	 * Verifica se o título com o parâmetro informado é um número inteiro menor
	 * que ou igual ao limite informado, caso contrário lança uma
	 * {@code IllegalArgumentException}.
	 * </p>
	 * 
	 * @param titulo
	 * @param param
	 *            Parâmetro a ser validado
	 * @param limite
	 *            Limite máximo permitido
	 * 
	 * @since 0.2
	 */
	protected final static void checkParametroBancarioMenorOuIgualQue(Titulo titulo, ParametroBancario<?> param, int limite){
		
		checkParametroBancario(titulo, param);
		
		int valor = titulo.getParametrosBancarios().getValor(param).intValue();
		
		boolean expression = valor <= limite; 

		Objects.checkArgument(expression, format("Parâmetro [%s] com valor [%s] deve ser um número menor que ou igual a [%s].", param, valor, limite));
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return Objects.toString(this);
	}
	
}
