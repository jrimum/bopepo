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
 * Created at: 19/01/2014 - 21:35:25
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
 * Criado em: 19/01/2014 - 21:35:25 
 * 
 */

package org.jrimum.bopepo.view.info.campo;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.awt.Image;

import org.jrimum.bopepo.view.ResourceBundle;
import org.jrimum.domkee.financeiro.banco.febraban.Banco;
import org.jrimum.domkee.financeiro.banco.febraban.CodigoDeCompensacaoBACEN;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.junit.Test;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestBoletoInfoCampoLogoBanco {

	@Test
	public void deve_retornar_imagem_logo_quando_conta_contem_imagem(){
		Image imgLogo = mock(Image.class);
		Banco banco = new Banco();
		banco.setImgLogo(imgLogo);
		ContaBancaria conta = new ContaBancaria();
		conta.setBanco(banco);
		ResourceBundle resourceBundle = null;

		Image logo = BoletoInfoCampoLogoBanco.getImagemBanco(resourceBundle, conta);

		assertThat(logo, equalTo(imgLogo));
	}

	@Test
	public void deve_retornar_imagem_logo_do_resource_quando_conta_nao_contem_imagem(){
		ContaBancaria conta = new ContaBancaria();
		conta.setBanco(new Banco(new CodigoDeCompensacaoBACEN(1),"Banco do Brasil"));
		ResourceBundle resourceBundle = new ResourceBundle();
		Image logoEsperada = resourceBundle.getLogotipoDoBanco("001");
		
		Image logo = BoletoInfoCampoLogoBanco.getImagemBanco(resourceBundle, conta);

		assertThat(logo, equalTo(logoEsperada));
	}
}
