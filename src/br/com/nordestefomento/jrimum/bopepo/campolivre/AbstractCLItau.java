
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
 * Created at: 16/04/2008 - 23:08:17
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
 * Criado em: 16/04/2008 - 23:08:17
 * 
 */
	
package br.com.nordestefomento.jrimum.bopepo.campolivre;

import java.util.Arrays;

import br.com.nordestefomento.jrimum.domkee.bank.febraban.ContaBancaria;
import br.com.nordestefomento.jrimum.domkee.bank.febraban.Titulo;
import br.com.nordestefomento.jrimum.vallia.digitoverificador.Modulo;


/**
 * 
 * <p>
 * DEFINIÇÃO DA CLASSE
 * </p>
 * 
 * <p>
 * OBJETIVO/PROPÓSITO
 * </p>
 * 
 * <p>
 * EXEMPLO: 
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public abstract class AbstractCLItau extends AbstractCampoLivre {
	
	/**
	 * <p>
	 * Carteiras especiais sem registro na qual são utilizadas 15 posições numéricas 
	 * para identificação do título liquidado (8 do Nosso Número e 7 do Seu Número).
	 * </p>
	 */
	private static final Integer[] CARTEIRAS_ESPECIAIS = {106, 107, 122, 142, 143, 195, 196, 198};

	protected AbstractCLItau(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}
	
	static CampoLivre create(Titulo titulo){
		
		CampoLivre campoLivre = null;
		ContaBancaria conta = titulo.getContaBancaria();
		
		/*
		 * Se a carteira for especial, a forma de construir o campo livre será diferente.
		 */
		if(Arrays.binarySearch(CARTEIRAS_ESPECIAIS, conta.getCarteira().getCodigo()) >= 0) {
			
			campoLivre = new CLItauComCarteirasEspeciais(titulo);
		}
		else {
			
			campoLivre = new CLItauPadrao(titulo);
		}
		
		return campoLivre;
	}
	
	/**
	 * <p>
	 * Método auxiliar para calcular o dígito verificador dos campos 31 e 41.
	 * O dígito é calculado com base em um campo fornecido pelos métodos que o chamam
	 * (<code>calculeDigitoDaPosicao31</code> e <code>calculeDigitoDaPosicao41</code>)
	 * </p>
	 * <p>
	 * O cálculo é feito através do módulo 10.
	 * </p>
	 * 
	 * @param campo
	 * @return Dígito verificador do campo fornecido.
	 * 
	 * @since 
	 */
	protected Integer calculeDigitoVerificador(String campo) {
				
		int restoDivisao = Modulo.calculeMod10(campo, 1, 2);
		int digito = Modulo.MOD10 - restoDivisao;
		
		if(digito > 9) {
			digito = 0;
		}
		
		return new Integer(digito);
	}

}
