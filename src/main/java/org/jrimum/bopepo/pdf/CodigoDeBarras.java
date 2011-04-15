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
 * Created at: 14/04/2011 - 14:49:07
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
 * Criado em: 14/04/2011 - 14:49:07
 * 
 */

package org.jrimum.bopepo.pdf;

import static java.lang.String.format;

import java.awt.Color;
import java.awt.Image;

import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Strings;

import com.lowagie.text.pdf.BarcodeInter25;


/**
 * Classe geradora  de código de barras no padrão FEBRABAN.
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 *
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class CodigoDeBarras {
	
	private String codigo;
	
	/**
	 * Classe não instanciável
	 * 
	 * @throws IllegalStateException
	 *             Caso haja alguma tentativa de utilização deste construtor.
	 *             
	 * @since 0.2
	 */
	@SuppressWarnings("unused")
	private CodigoDeBarras() {

		Objects.checkState(false, "Instanciação não permitida!");
	}
	
	public CodigoDeBarras(String codigo){
	
		checkCodigo(codigo);
		
		this.codigo = codigo;
	}
	
	public static CodigoDeBarras valueOf(String codigo){
		checkCodigo(codigo);
		return new CodigoDeBarras(codigo);
	}
	
	public String write(){
		
		return codigo;
	}

	public Image toImage(){
		
		// Montando o código de barras.
		BarcodeInter25 barCode = new BarcodeInter25();
		barCode.setCode(this.write());

		barCode.setExtended(true);
		barCode.setBarHeight(35);
		barCode.setFont(null);
		barCode.setN(3);
		
		return barCode.createAwtImage(Color.BLACK, Color.WHITE);
	}
	
	private static void checkCodigo(String str) {

		Objects.checkNotNull(str, "Código nulo!");
		Strings.checkNotBlank(str, format("Código ausente! str = \"%s\"",str));
		Strings.checkNotNumeric(str, format("Código não contém apenas números! str = \"%s\"",str));
		Objects.checkArgument(str.length()==44, format("Código com tamanho diferente de 44 dígitos! str = \"%s\"",str));
	}
}
