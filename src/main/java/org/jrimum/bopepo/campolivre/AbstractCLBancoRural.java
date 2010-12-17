package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Interface comum para todos os campos livres do Banco Santander que venham a
 * existir.
 * </p>
 * 
 * @author <a href="mailto:fernandobgi@gmail.com">Fernando Dias</a> -
 *         Colaborador com o campo livre {@code CLBancoRuralCobrancaRegistrada}
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class AbstractCLBancoRural extends AbstractCampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = -602454445158254612L;

	/**
	 * O código reduzido campos livre: {@code CLBancoRuralCobrancaNaoRegistrada}
	 * e {@code CLBancoRuralCobrancaNaoRegistradaSeguradora}
	 */
	static final String CODIGO_REDUZIDO = "CODIGO_REDUZIDO";

	/**
	 * <p>Cria um campo livre com um determinado número de campos</p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength - Número de campos
	 */
	protected AbstractCLBancoRural(Integer fieldsLength) {
		
		super(fieldsLength);
	}

	/**
	 * {@inheritDoc}
	 */
	protected static CampoLivre create(Titulo titulo) {
		
		checkCarteiraNotNull(titulo);
		checkRegistroDaCarteiraNotNull(titulo);
		
		switch(titulo.getContaBancaria().getCarteira().getTipoCobranca()){
		case SEM_REGISTRO:
			return campoSemRegistro(titulo);
		case COM_REGISTRO:
			return new CLBancoRuralCobrancaRegistrada(titulo);
		default:
			return null;
		}
	}

	/**
	 * Determina e cria um campo livre de cobrança não registrada.
	 * 
	 * @param titulo
	 *            - título com as informações para geração do campo livre
	 * @return campo livre ou null
	 */
	private static CampoLivre campoSemRegistro(Titulo titulo) {
		
		checkNossoNumero(titulo);
		
		switch(titulo.getNossoNumero().length()){
		case NN10:
			return new CLBancoRuralCobrancaNaoRegistradaSeguradora(titulo);
		case NN15:
			return new CLBancoRuralCobrancaNaoRegistrada(titulo);
		default:
			throw new NotSupportedCampoLivreException(
					"Combrança sem registro com campo livre diponível somente para títulos com nosso número" 
					+ " composto por 10 posições(apólice de seguro com I.O.S.) e 15 posições(padrão)."
				);
		}
	}
}
