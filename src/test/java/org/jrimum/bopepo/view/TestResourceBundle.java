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
 * Created at: 08/09/2013 - 12:14:45
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
 * Criado em: 08/09/2013 - 12:14:45
 * 
 */

package org.jrimum.bopepo.view;

import static org.jrimum.bopepo.view.ResourceBundle.BOLETO_TEMPLATE_COM_SACADOR_AVALISTA;
import static org.jrimum.bopepo.view.ResourceBundle.BOLETO_TEMPLATE_SEM_SACADOR_AVALISTA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jrimum.bopepo.excludes.Images;
import org.junit.Test;

import com.google.common.io.Resources;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class TestResourceBundle {

	@Test
	public void deve_carregar_a_logo_do_banco_do_resource() throws Exception {

		BufferedImage logoBanco001Esperado = ImageIO.read(Resources.getResource("img/001.png"));
		assertNotNull(logoBanco001Esperado);

		BufferedImage logoBanco001Carregado = (BufferedImage) new ResourceBundle().getLogotipoDoBanco("001");
		assertNotNull(logoBanco001Carregado);

		assertTrue(Images.areEqual(logoBanco001Esperado, logoBanco001Carregado));
	}

	@Test
	public void deve_carregar_o_mesmo_objeto_imagem_apos_o_primeiro_carregamento_de_uma_mesma_logo() throws Exception {
		
		ResourceBundle resourceBundle = new ResourceBundle();
		
		Image logoBanco001Carregado = resourceBundle.getLogotipoDoBanco("001");
		assertNotNull(logoBanco001Carregado);
		Image logoBanco001Carregado2 = resourceBundle.getLogotipoDoBanco("001");
		assertNotNull(logoBanco001Carregado2);

		assertEquals(logoBanco001Carregado, logoBanco001Carregado2);
	}

	@Test
	public void deve_carregar_o_boleto_template_sem_sacador_avalista() throws Exception {
		
		byte[] templateEsperado = getTempateEsperado(BOLETO_TEMPLATE_SEM_SACADOR_AVALISTA);
		assertNotNull(templateEsperado);
		
		byte[] templateCarregado = new ResourceBundle().getTemplateSemSacadorAvalista();
		assertNotNull(templateCarregado);
		
		assertEquals(templateEsperado.length, templateCarregado.length);
	}

	@Test
	public void deve_carregar_o_boleto_template_com_sacador_avalista() throws Exception {

		byte[] templateEsperado = getTempateEsperado(BOLETO_TEMPLATE_COM_SACADOR_AVALISTA);
		assertNotNull(templateEsperado);
		
		byte[] templateCarregado = new ResourceBundle().getTemplateComSacadorAvalista();
		assertNotNull(templateCarregado);
		
		assertEquals(templateEsperado.length, templateCarregado.length);
	}

	private byte[] getTempateEsperado(String nomeDoTemplate) throws IOException {
		
		return Resources.asByteSource(Resources.getResource("pdf/"+nomeDoTemplate)).read();
	}
}
