/* 
 * Copyright 2014 JRimum Project
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
 * Created at: 14/01/2014 - 18:56:03
 *
 * ================================================================================
 *
 * Direitos autorais 2014 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 14/01/2014 - 18:56:03 
 * 
 */

package org.jrimum.bopepo.excludes;

import java.math.BigDecimal;

import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;
import org.jrimum.utilix.text.DateFormat;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TituloBuilder {

	private Titulo titulo;
	
	public TituloBuilder(){
		this.titulo = newDefaultValue();
	}
	
	public static Titulo defaultValue(){
		return newDefaultValue();
	}

	public static Titulo defaultValueSacadorAvalista(){
		return newDefaultValueSacadorAvalista();
	}
	
	public Titulo build(){
		return this.titulo;
	}
	
	private static Titulo newDefaultValue() {
		Titulo titulo = new Titulo(ContaBancariaBuilder.defaultValue(),
				SacadoBuilder.defaultValue(), CedenteBuilder.defaultValue());
		setDefaultValues(titulo);
		return titulo;
	}

	private static Titulo newDefaultValueSacadorAvalista() {
		Titulo titulo = new Titulo(ContaBancariaBuilder.defaultValue(),
				SacadoBuilder.defaultValue(), CedenteBuilder.defaultValue(),
				SacadorAvalistaBuilder.defaultValue());
		setDefaultValues(titulo);
		return titulo;
	}
	
	private static void setDefaultValues(Titulo titulo){
		
		titulo.setNumeroDoDocumento("123456");
		titulo.setNossoNumero("99345678912");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.valueOf(0.23));
		titulo.setDataDoDocumento(DateFormat.DDMMYYYY_B.parse("01/01/2020"));
		titulo.setDataDoVencimento(DateFormat.DDMMYYYY_B.parse("07/09/2020"));		
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(Aceite.A);
		titulo.setDesconto(new BigDecimal(0.05));
		titulo.setDeducao(BigDecimal.ZERO);
		titulo.setMora(BigDecimal.ZERO);
		titulo.setAcrecimo(BigDecimal.ZERO);
		titulo.setValorCobrado(BigDecimal.ZERO);
	}
}
