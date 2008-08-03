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
 * Created at: 02/08/2008 - 12:06:09
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
 * Criado em: 02/08/2008 - 12:06:09
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.domkee.entity.Titulo;
import br.com.nordestefomento.jrimum.utilix.Field;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.Modulo;

/**
 * 
 * <p>
 * Fábrica de campos livre do Banco Banrisul.
 * </p>
 * 
 * <p>
 * Objetivos:
 * <ul>
 *   <li>Escolher qual é o campo livre a ser instanciado, baseado no tipo de cobrança;</li>
 *   <li>Agrupar métodos em comum dos campos livres existentes.</li>
 * </ul>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author Samuel Valerio
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
abstract class ACLBanrisul extends ACampoLivre {
	
	protected ACLBanrisul(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	static ICampoLivre create(Titulo titulo)
			throws NotSuporttedCampoLivreException {
		final ICampoLivre campoLivre;

		switch (titulo.getContaBancaria().getCarteira().getTipoCobranca()) {
		case COM_REGISTRO:
			campoLivre = new CLBanrisulCobrancaRegistrada(titulo);
			break;
		case SEM_REGISTRO:
			campoLivre = new CLBanrisulCobrancaNaoRegistrada(titulo);
			break;
		default:
			throw new NotSuporttedCampoLivreException(
					"Campo livre diponível apenas para"
							+ " carteiras com ou sem cobrança.");
		}

		return campoLivre;
	}

	/**
	 * <p>
	 * Calcula o duplo dígito referente às posições 20 a 42 (módulos 10 e 11).
	 * </p>
	 * 
	 * 
	 * @param seisPrimeirosCamposConcatenados
	 * @return duplo dígito
	 * 
	 * @since 0.2
	 */
	protected String calculaDuploDigito(String seisPrimeirosCamposConcatenados) {
		// calcula soma do módulo 10 a partir dos seis primeiros campos concatenados
		final int somaMod10 = Modulo.calculeSomaSequencialMod10(
				seisPrimeirosCamposConcatenados, 1, 2);
		
		// calcula resto do módulo 10 a partir do resultado da soma
		final byte restoMod10 = calculeRestoMod10(somaMod10);
		
		// calcula primeiro DV a partir do resto módulo 10
		byte primeiroDV = calculePrimeiroDV(restoMod10);

		
		// calcula soma do módulo 10 a partir dos seis primeiros campos concatenados
		// incluindo o primeiro dígito
		int somaMod11 = Modulo.calculeSomaSequencialMod11(
				seisPrimeirosCamposConcatenados + primeiroDV, 2, 7);
		
		// calcula o resto do módulo 11 a partir do resultado da soma
		byte restoMod11 = calculeRestoMod11(somaMod11);
		
		// se o resto do módulo 11 for 1 então o primeiro dígito é inválido
		// deve ser feito o recálculo até que o resto não seja 1
		while (restoMod11 == 1) {
			
			// encontra um valor válido para o primeiro DV
			primeiroDV = encontreValorValidoParaPrimeiroDV(primeiroDV);
			
			// calcula a soma do módulo 11 agora com um valor
			// válido para o DV
			somaMod11 = Modulo.calculeSomaSequencialMod11(
					seisPrimeirosCamposConcatenados + primeiroDV, 2, 7);
			
			// calcula o resto do módulo 11 a partir do resultado da soma
			restoMod11 = calculeRestoMod11(somaMod11);
		}

		// calcula o segundo DV a partir do resto módulo 11
		final byte segundoDV = calculeSegundoDV(restoMod11);

		// concatena o primeiro DV com o segundo DV para
		// formar o duplo dígito
		return String.valueOf(primeiroDV) + String.valueOf(segundoDV);
	}

	/**
	 * <p>
	 * Calcula o segundo dígito verificador.
	 * </p>
	 * 
	 * @param restoMod11
	 * @return segundo dígito
	 * 
	 * @since 0.2
	 */
	private byte calculeSegundoDV(byte restoMod11) {
		final byte segundoDV;
		if (restoMod11 == 0)
			segundoDV = restoMod11;
		else
			segundoDV = (byte) (11 - restoMod11);
		return segundoDV;
	}

	/**
	 * <p>
	 * Calcula o primeiro dígito verificador.
	 * </p>
	 * 
	 * @param restoMod10
	 * @return primeiro dígito verificador
	 * 
	 * @since 0.2
	 */
	private byte calculePrimeiroDV(byte restoMod10) {
		final byte primeiroDV;
		if (restoMod10 == 0)
			primeiroDV = 0;
		else
			primeiroDV = (byte) (10 - restoMod10);
		return primeiroDV;
	}

	/**
	 * <p>
	 * Calcula o resto da soma módulo 10.
	 * </p>
	 * 
	 * @param somaMod10
	 * @return
	 * 
	 * @since 0.2
	 */
	private byte calculeRestoMod10(int somaMod10) {
		final byte restoMod10;
		if (somaMod10 < 10)
			restoMod10 = (byte) somaMod10;
		else
			restoMod10 = (byte) (somaMod10 % 10);
		return restoMod10;
	}

	/**
	 * <p>
	 * Encontra um valor válido para o primeiro dígito.
	 * </p>
	 * 
	 * @param primeiroDV
	 * @return valor válido para o primeiro dígito
	 * 
	 * @since 0.2
	 */
	private byte encontreValorValidoParaPrimeiroDV(byte primeiroDV) {
		final byte novoValorDoPrimeiroDV;
		if (primeiroDV == 9)
			novoValorDoPrimeiroDV = 0;
		else
			novoValorDoPrimeiroDV = (byte) (primeiroDV + 1);
		return novoValorDoPrimeiroDV;
	}

	/**
	 * <p>
	 * Calcula o resto do módulo 11.
	 * </p>
	 * 
	 * @param somaMod11
	 * @return restro do módulo 11
	 * 
	 * @since 0.2
	 */
	private byte calculeRestoMod11(int somaMod11) {
		final byte restoMod11;
		if (somaMod11 < 11)
			restoMod11 = (byte) somaMod11;
		else
			restoMod11 = (byte) (somaMod11 % 11);
		return restoMod11;
	}

	@SuppressWarnings("unchecked")
	protected String concateneOsCamposExistentesAteOMomento() {
		final StringBuilder camposExistentesAteOMomentoConcatenados = new StringBuilder(
				StringUtils.EMPTY);
		for (Field field : this) {
			camposExistentesAteOMomentoConcatenados.append(field.write());
		}
		return camposExistentesAteOMomentoConcatenados.toString();
	}

}
