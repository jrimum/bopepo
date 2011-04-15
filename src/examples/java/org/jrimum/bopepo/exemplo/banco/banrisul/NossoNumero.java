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
 * Created at: 11/04/2011 - 23:20:00
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
 * Criado em: 11/04/2011 - 23:20:00
 * 
 */

package org.jrimum.bopepo.exemplo.banco.banrisul;

import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Filler;
import org.jrimum.vallia.digitoverificador.Modulo;

/**
 * Classe responsável pela criação do Nosso Número referente ao Banco Banrisul.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class NossoNumero {
	
	private int numero;

	private int dvMod10;
	private int dvMod11;
	
	/**
	 * Nosso número calculado. 
	 */
	private StringBuilder nossoNumero = new StringBuilder("");
	
	/**
	 * Para uso interno.
	 */
	private NossoNumero() {}
	
	public static NossoNumero valueOf(int numero){
	
		Objects.checkArgument(numero > 0, "Número [ "+numero+" ] inválido!");

		NossoNumero nn = new NossoNumero();
		nn.numero = numero;
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
		
		nossoNumero.append(Filler.ZERO_LEFT.fill(numero, 8));
		
		calculeDvMod10();
		calculeDvMod11();
	}
	
	private void calculeDvMod11() {
		
		int somatorio = Modulo.calculeSomaSequencialMod11(nossoNumero.toString()+getDvMod10(),2,7);
		
		int restoDivisao;
		
		/*
		 * Caso o somatório obtido seja menor que 11, considerar como resto da divisão o próprio somatório.
		 */
		if(somatorio < 11){
			restoDivisao = somatorio;
		}else{
			restoDivisao = somatorio % 11;
		}
		
		int restoSubtracao = (11 - restoDivisao);
		
		/*
		 * Caso o resto obtido no cálculo do módulo 11 seja 0, o segundo NC será
		 * igual ao próprio resto.
		 */
		if(restoDivisao == 0){
			this.dvMod11 = 0;
		}else{
			
			if(restoDivisao != 1){
				
				this.dvMod11 = restoSubtracao;
				
			}else{
				/*
				 * Caso o resto obtido no cálculo do módulo 11 seja igual a 1,
				 * considera-se o DV inválido. Soma-se, então, "1" ao DV obtido
				 * do módulo "10" e refaz-se o cálculo do módulo “11” .
				 */
				this.dvMod10++;
				
				/*
				 * Se o dígito obtido pelo módulo “10” era igual a "9",
				 * considera-se então (9+1=10) DV inválido. Neste caso, o DV do
				 * módulo "10" automaticamente será igual a "0" e procede-se
				 * assim novo cálculo pelo módulo "11".
				 */
				if(this.dvMod10 == 10){
					
					dvMod10 = 0;
				}
				
				/*
				 * Novo cálculo
				 */
				calculeDvMod11();
			}
		}
	}

	private void calculeDvMod10() {
		
		int somatorio = Modulo.calculeSomaSequencialMod10(nossoNumero.toString(),1,2);
		
		int restoDivisao;
		
		/*
		 * Quando o somatório for menor que 10, o resto da divisão por 10 será o
		 * próprio somatório.
		 */
		if(somatorio < 10){
			restoDivisao = somatorio;
		}else{
			restoDivisao = somatorio % 10;
		}
		
		int restoSubtracao = (10 - restoDivisao);
		
		if(restoDivisao == 0){
			this.dvMod10 = 0;
		}else{
			this.dvMod10 = restoSubtracao;
		}
	}

	/**
	 * Retorna o digito verificador calculado.
	 * 
	 * @return String Digito Verificador de dois dígitos
	 */
	public String getDv(){
		return dvMod10+""+dvMod11;
	}
	
	/**
	 * @return the dvMod10
	 */
	public int getDvMod10() {
		return dvMod10;
	}

	/**
	 * @return the dvMod11
	 */
	public int getDvMod11() {
		return dvMod11;
	}

	/**
	 * String com o nosso número sem o digito verificador.
	 * 
	 * @return nosso número
	 */
	public String writeRaw(){
		return Filler.ZERO_LEFT.fill(numero, 8);
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
		return Filler.ZERO_LEFT.fill(numero, 8)+"."+getDv();
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
