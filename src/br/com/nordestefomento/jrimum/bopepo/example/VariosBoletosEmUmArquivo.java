
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
 * Created at: 18/05/2008 - 21:13:29
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
 * Criado em: 18/05/2008 - 21:13:29
 * 
 */
	
package br.com.nordestefomento.jrimum.bopepo.example;

import java.io.File;
import java.util.List;

import br.com.nordestefomento.jrimum.JRimumException;
import br.com.nordestefomento.jrimum.bopepo.Boleto;
import br.com.nordestefomento.jrimum.bopepo.view.BoletoViewer;


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
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class VariosBoletosEmUmArquivo {

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param args
	 * 
	 * @since 
	 */

	public static void main(String[] args) {
		/*
		 * É bem simples, consiga os boletos 
		 */

		List<Boleto> boletos = Util4Exemplos.getVariosBoletos();
		
		/*
		 * Depois diga o nome do diretorio/arquivo para onde os boletos serão gerados. 
		 */
		
		BoletoViewer.groupInOnePDF("TesteVariosEmUm", boletos);
	
		
		/*
		 * Pronto, agora vamos conferir: 
		 */
		
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			
		try{
	
			desktop.open(new File("TesteVariosEmUm.pdf"));
	
		}catch(Exception e){
			throw new JRimumException("Arquivo nao gerado!",e);
		}
		
		/*
		 * É sério, é só isso mesmo!
		 * Acredita não?
		 * Então faça novamente! 
		 */

	}

}
