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

package org.jrimum.bopepo.exemplo;

import java.io.File;
import java.util.List;

import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;



/**
 * 
 * <p>
 * Exemplo de código para geração de vários boletos em um único arquivo PDF.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public class VariosBoletosEmVariosArquivos {


	public static void main(String[] args) {
		
		/*
		 * É bem simples, consiga os boletos 
		 */

		List<Boleto> boletos = Exemplos.getVariosBoletos();
		
		/*
		 * Depois diga o nome do diretorio para onde os boletos serão gerados. 
		 */
		
		BoletoViewer.onePerPDF(boletos, new File("./"), "prefixo-boleto-", "-sufixo-DePagamento");
	
		
		/*
		 * Pronto, agora vamos conferir um: 
		 */
		
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			
		try{
	
			desktop.open(new File("prefixo-boleto-1-sufixo-DePagamento.pdf"));
	
		}catch(Exception e){
			throw new RuntimeException("Arquivo não gerado!",e);
		}
		
		/*
		 * É sério, é só isso mesmo!
		 * Se não acredita confira os vários arquivos que estão no diretório. 
		 */
		
	}

}
