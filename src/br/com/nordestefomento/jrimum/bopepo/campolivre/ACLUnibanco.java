package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.domkee.entity.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.entity.EnumTipoCobranca;
import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.Modulo;

public class ACLUnibanco extends ACampoLivre {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6169577742706045367L;

	protected ACLUnibanco(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static ICampoLivre create(Titulo titulo)
			throws NotSuporttedCampoLivreException {

		ICampoLivre campoLivre = null;

		ContaBancaria conta = titulo.getContaBancaria();

		if (exists(conta.getCarteira().getTipoCobranca())) {

			if (conta.getCarteira().getTipoCobranca() == EnumTipoCobranca.COM_REGISTRO) 
				campoLivre = new CLUnibancoCobrancaRegistrada(titulo);
			else 
				if(conta.getCarteira().getTipoCobranca() == EnumTipoCobranca.SEM_REGISTRO)
					campoLivre = new CLUnibancoCobrancaNaoRegistrada(titulo);
				else
					throw new NotSuporttedCampoLivreException(
					"Nao existe suporte para um campo livre do unibanco com a cobranca: "+conta.getCarteira().getTipoCobranca());

		} else {
			throw new NotSuporttedCampoLivreException(
					"Campo livre indeterminado, defina o tipo de cobranca para a carteira usada.");
		}

		return campoLivre;
	}

	/**
	 * <p>
	 * Calcula o dígito verificador para
	 * <em>referência do cliente (cobrança sem registro)</em> e base para
	 * cálculo do <em>super dígito do nosso numero (cobrança com registro)</em>.
	 * </p>
	 * 
	 * <p>
	 * 
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
