/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 21/04/2008 - 20:27:04
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 21/04/2008 - 20:27:04
 * 
 */

package org.jrimum.bopepo.campolivre;

import static java.lang.String.format;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Exceptions;

/**
 * <p>
 * Interface comum para todos os campos livres do Banco Safra que venham a
 * existir.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLBancoSafra extends AbstractCampoLivre {

	/**
	 * {@code serialVersionUID = -555393808447532987L}
	 */
	private static final long serialVersionUID = -555393808447532987L;

	/**
	 * Códigos do "Tipo de Cobrança" utilizados nos boletos do Banco Safra.
	 * 
	 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
	 */
	protected enum TipoDeCobranca {

		/**
		 * 1 = Bloqueto Emitido pelo Banco.
		 */
		BOLETO_EMITIDO_PELO_BANCO,
		/**
		 * 2 = Eletrônica Emitido pelo Cliente.
		 */
		DIRETA_BOLETO_EMITIDO_PELO_CLIENTE,
		/**
		 * 4 = Express Emitido pelo Cliente.
		 */
		EXPRESS_BOLETO_EMITIDO_PELO_CLIENTE;

		public int codigo() {
			switch (this) {
			case BOLETO_EMITIDO_PELO_BANCO:
				return 1;
			case DIRETA_BOLETO_EMITIDO_PELO_CLIENTE:
				return 2;
			case EXPRESS_BOLETO_EMITIDO_PELO_CLIENTE:
				return 4;
			default:
				return (Integer) Exceptions.throwIllegalStateException(format(
						"Tipo \"%s\" sem código atribuído!", this));
			}
		}
	}

	/**
	 * Tamanho do campo Constante = 1, chamado de "SISTEMA", definido pelo Banco
	 * Safra nos dois campos livres: {@link CLBancoSafraCobrancaNaoRegistrada} e
	 * {@link CLBancoSafraCobrancaRegistrada}.
	 */
	private static final Integer CONSTANT_LENGTH_SISTEMA = Integer.valueOf(1);
	
	/**
	 * Valor do campo Constante = 7, chamado de "SISTEMA", definido pelo Banco
	 * Safra nos dois campos livres: {@link CLBancoSafraCobrancaNaoRegistrada} e
	 * {@link CLBancoSafraCobrancaRegistrada}.
	 */
	private static final Integer CONSTANT_VALUE_SISTEMA = Integer.valueOf(7);
	
	/**
	 * Constante em forma de campo {@link AbstractCLBancoSafra#CONSTANT_VALUE_SISTEMA} e {@link AbstractCLBancoSafra#CONSTANT_LENGTH_SISTEMA}.
	 */
	protected static final FixedField<Integer> SISTEMA_CONSTANT_FIELD = new FixedField<Integer>(CONSTANT_VALUE_SISTEMA, CONSTANT_LENGTH_SISTEMA);
	
	/**
	 * Tamanho do campo = 1, chamado de "TIPO DE COBRANÇA", definido pelo Banco
	 * Safra nos dois campos livres: {@link CLBancoSafraCobrancaNaoRegistrada} e
	 * {@link CLBancoSafraCobrancaRegistrada}.
	 */
	protected static final Integer TIPO_COBRANCA_FIELD_LENGTH = Integer.valueOf(1);

	/**
	 * <p>
	 * Cria um campo livre com um determinado número de campos
	 * </p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength
	 *            - Número de campos
	 */
	protected AbstractCLBancoSafra(Integer fieldsLength) {

		super(fieldsLength);
	}

	protected static CampoLivre create(Titulo titulo)
			throws NotSupportedCampoLivreException {

		checkCarteiraNotNull(titulo);
		checkRegistroDaCarteiraNotNull(titulo);

		switch (titulo.getContaBancaria().getCarteira().getTipoCobranca()) {
		case COM_REGISTRO:
			return new CLBancoSafraCobrancaRegistrada().build(titulo);
		case SEM_REGISTRO:
			return new CLBancoSafraCobrancaNaoRegistrada().build(titulo);
		default:
			throw new NotSupportedCampoLivreException(
					"Campo livre diponível somente para títulos com carteiras com tipo de cobrança "
							+ "COM_REGISTRO ou SEM_REGISTRO.");
		}
	}
}
