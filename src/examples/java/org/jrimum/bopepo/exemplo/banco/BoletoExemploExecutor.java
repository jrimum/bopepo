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
 * Created at: 15/09/2009 - 23:53:57
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
 * Criado em: 15/09/2009 - 23:53:57
 * 
 */
package org.jrimum.bopepo.exemplo.banco;

import java.io.File;
import java.io.IOException;

import org.jrimum.bopepo.view.BoletoViewer;


/**
 * 
 * <p>
 * Executa todos os exemplos de geração de boletos
 * </p>
 *  
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @version 0.2
 */
public class BoletoExemploExecutor {
	
	public BoletoExemploExecutor() {
		init();
	}
	
	private void init() {
		
		AbstractBoletoExemplo.newInstance(BoletoBradescoExemplo.class);
		AbstractBoletoExemplo.newInstance(BoletoBancoRealExemplo.class);
		AbstractBoletoExemplo.newInstance(BoletoItauCarteirasPadroesExemplo.class);
		AbstractBoletoExemplo.newInstance(BoletoItauCarteirasEspeciaisExemplo.class);
		AbstractBoletoExemplo.newInstance(BoletoHSBCCobrancaNaoRegistradaExemplo.class);
		AbstractBoletoExemplo.newInstance(BoletoUnibancoCobrancaNaoRegistradaExemplo.class);
		AbstractBoletoExemplo.newInstance(BoletoUnibancoCobrancaRegistradaExemplo.class);
	}
	
	private void executeAll() {
		
		for (AbstractBoletoExemplo boletoExemplo  : AbstractBoletoExemplo.getBoletosExemplo()) {
			
			BoletoViewer viewer = new BoletoViewer(boletoExemplo.execute());
			File boletoPDF = viewer.getPdfAsFile("BOLETO_" + boletoExemplo.getClass().getSimpleName().toUpperCase() + ".PDF");
			
			mostreBoletoNaTela(boletoPDF);
		}
	}
	
	private void mostreBoletoNaTela(File arquivoBoleto) {
		
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		
		try {
			desktop.open(arquivoBoleto);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new BoletoExemploExecutor().executeAll();
	}
}
