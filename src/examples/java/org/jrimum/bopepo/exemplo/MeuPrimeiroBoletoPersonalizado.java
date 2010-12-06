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
 * Created at: 01/11/2010 - 09:37:00
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
 * Criado em: 01/11/2010 - 09:37:00
 * 
 */
package org.jrimum.bopepo.exemplo;

import java.io.File;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.ClassLoaders;

/**
 * <p>
 * Exemplo de código para geração de um boleto simples usando um template personalizado.
 * </p>
 * <p>
 * Utiliza a classe utilitária <code>Exemplos</code> para criar os objetos necessários para gerar o boleto.
 * </p>
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class MeuPrimeiroBoletoPersonalizado {

	public static void main(String[] args) {

		Titulo titulo = Exemplos.crieTitulo();
		titulo.getContaBancaria().setBanco(BancosSuportados.BANCO_DO_BRASIL.create());
		titulo.setNossoNumero("1234567890");
		
		Boleto boleto = Exemplos.crieBoleto(titulo);
		
		//Informando o template personalizado:
		File templatePersonalizado = new File(ClassLoaders.getResource("/templates/BoletoTemplatePersonalizacaoSimples.pdf").getFile());
		BoletoViewer boletoViewer = new BoletoViewer(boleto, templatePersonalizado);

		File arquivoPdf = boletoViewer.getPdfAsFile("MeuBoletoPersonalizado.pdf");

		Exemplos.mostreBoletoNaTela(arquivoPdf);
	}
}
