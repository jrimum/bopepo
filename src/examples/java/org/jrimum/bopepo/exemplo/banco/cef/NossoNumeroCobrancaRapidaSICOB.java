/*
 * Copyright 2013 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 21/03/2013 - 11:25:33
 * 
 * ================================================================================
 * 
 * Direitos autorais 2013 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 21/03/2013 - 11:25:33
 * 
 */
package org.jrimum.bopepo.exemplo.banco.cef;

import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.utilix.Objects;
import org.jrimum.vallia.digitoverificador.Modulo;

/**
 * Nosso Número referente a COBRANÇA RÁPIDA – 11 POSIÇÕES da CAIXA ECONÔMICA
 * FEDERAL (CEF)
 * 
 * <ul>
 * <li>Campo com 10 posições sempre iniciando com 9;</li>
 * <li>Utiliza 1 dígito verificador calculado através do módulo 11, com peso 2 a
 * 9.</li>
 * </ul>
 * 
 * <p>
 * <b>9NNNNNNNNN - D1</b>
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class NossoNumeroCobrancaRapidaSICOB {

	private long numero;
	
	private String dv;
	
	private StringBuilder nossoNumero = new StringBuilder("");
	
	/**
	 * Para uso interno.
	 */
	private NossoNumeroCobrancaRapidaSICOB() {}
	
	public static NossoNumeroCobrancaRapidaSICOB valueOf(long numero){
	
		Objects.checkArgument(numero > 0, "Número [ "+numero+" ] inválido!");

		NossoNumeroCobrancaRapidaSICOB nn = new NossoNumeroCobrancaRapidaSICOB();
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
		
		nossoNumero.append(9);
		nossoNumero.append(Fillers.ZERO_LEFT.fill(numero, 9));
		
		String formula = nossoNumero.toString();
		
		int restoDivisao = Modulo.calculeMod11(formula,2,9);
		
		int restoSubtracao = (11 - restoDivisao);
		
		if(restoDivisao > 9){
			dv = "0";
		}else{
			dv = ""+restoSubtracao;
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
		return Fillers.ZERO_LEFT.fill(numero, 10);
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
		return "9"+Fillers.ZERO_LEFT.fill(numero, 9)+"-"+dv;
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