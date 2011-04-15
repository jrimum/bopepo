/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 07/04/2011 - 10:57:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 07/04/2011 - 10:57:00
 * 
 */

package org.jrimum.bopepo.exemplo.banco.bradesco;

import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Filler;
import org.jrimum.vallia.digitoverificador.Modulo;

/**
 * Classe responsável pela criação do Nosso Número referente ao Banco Bradesco.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class NossoNumero {
	
	private String carteira;
	
	private long numero;
	
	private String dv;
	
	private StringBuilder nossoNumero = new StringBuilder("");
	
	/**
	 * Para uso interno.
	 */
	private NossoNumero() {}
	
	public static NossoNumero valueOf(long numero, int carteira){
	
		Objects.checkArgument(numero > 0, "Número [ "+numero+" ] inválido!");
		Objects.checkArgument(carteira > 0, "Carteira [ "+carteira+" ] inválida!");
		Objects.checkArgument(carteira <= 99, "Carteira [ "+carteira+" ] inválida! Carteira Bradesco deve ser um número entre 1 e 99");

		NossoNumero nn = new NossoNumero();
		nn.numero = numero;
		nn.carteira = Filler.ZERO_LEFT.fill(carteira, 2);
		nn.create();

		return nn;
	}
	
	/**
	 * Responsável pela lógica de cálculo do dígito verificador informada pelo
	 * Bradesco.
	 * <p>
	 * Para maiores esclarecimentos ver o manual do Bradesco.
	 * </p>
	 */
	private void create(){
		
		nossoNumero.append(Filler.ZERO_LEFT.fill(numero, 11));
		
		String formula = this.carteira+nossoNumero.toString();
		
		int restoDivisao = Modulo.calculeMod11(formula,2,7);
		
		int restoSubtracao = (11 - restoDivisao);
		
		if(restoDivisao == 0){
			dv = "0";
		}else{
			if(restoSubtracao == 10){
				dv = "P";
			}else{
				dv = ""+restoSubtracao;
			}
		}
		nossoNumero.append(dv);
	}
	
	/**
	 * Retorna o digito verificador calculado.
	 * 
	 * @return String Digito Verificador
	 */
	public String getDv(){
		return dv;
	}
	
	/**
	 * String com o nosso número sem o digito verificador.
	 * 
	 * @return nosso número
	 */
	public String writeRaw(){
		return Filler.ZERO_LEFT.fill(numero, 11);
	}
	
	/**
	 * String com o nosso número com o digito verificador.
	 * 
	 * @return nosso número
	 */
	public String writeFull(){
		return nossoNumero.toString();
	}
	
	/**
	 * String com o nosso número formatado com o digito verificador.
	 * 
	 * @return nosso número
	 */
	public String writeFormated(){
		return Filler.ZERO_LEFT.fill(numero, 11)+"-"+dv;
	}
	
	/**
	 * Escreve o nosso número formatado.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  writeFormated();
	}
	
}
