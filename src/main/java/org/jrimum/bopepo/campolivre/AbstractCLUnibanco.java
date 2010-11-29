package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.vallia.digitoverificador.Modulo;

abstract class AbstractCLUnibanco extends AbstractCampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6169577742706045367L;

	/**
	 * <p>Cria um campo livre com um determinado número de campos</p>
	 * 
	 * @see AbstractCampoLivre
	 * 
	 * @param fieldsLength - Número de campos
	 */
	protected AbstractCLUnibanco(Integer fieldsLength) {
		
		super(fieldsLength);
	}

	/**
	 * {@inheritDoc}
	 */
	protected static CampoLivre create(Titulo titulo) throws NotSupportedCampoLivreException {

		checkCarteiraNotNull(titulo);
		checkRegistroDaCarteiraNotNull(titulo);

		switch(titulo.getContaBancaria().getCarteira().getTipoCobranca()){
		case SEM_REGISTRO:
			return new CLUnibancoCobrancaNaoRegistrada(titulo);
		case COM_REGISTRO:
			return new CLUnibancoCobrancaRegistrada(titulo);
		default:
			return null;
		}
	}

	/**
	 * <p>
	 * Calcula o dígito verificador para
	 * <em>referência do cliente (cobrança sem registro)</em> e base para
	 * cálculo do <em>super dígito do nosso numero (cobrança com registro)</em>.
	 * </p>
	 * 
	 * @param numero
	 * @return String dígito
	 * 
	 * @since 0.2
	 */
	String calculeDigitoEmModulo11(String numero) {

		String dv = "";

		int soma = Modulo.calculeSomaSequencialMod11(numero, 2, 9);

		soma *= 10;

		final int resto = soma % 11;

		if (resto == 10 || resto == 0)
			dv = "0";
		else
			dv = "" + resto;

		return dv;

	}
}
