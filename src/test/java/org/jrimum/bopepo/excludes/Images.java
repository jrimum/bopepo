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
 * Created at: 08/09/2013 - 17:33:00
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
 * Criado em: 08/09/2013 - 17:33:00
 * 
 */

package org.jrimum.bopepo.excludes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Operações com imagem em geral.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class Images {

	/**
	 * Realiza a comparação entre imagens.
	 * 
	 * @param b1
	 * @param b2
	 * @return true se são iguais.
	 */
	public static boolean areEqual(BufferedImage b1, BufferedImage b2) {
		if (b1.getWidth() != b2.getWidth()) {
			return false;
		}
		if (b1.getHeight() != b2.getHeight()) {
			return false;
		}
		for (int i = 0; i < b1.getWidth(); i++) {
			for (int j = 0; j < b1.getHeight(); j++) {
				if (b1.getRGB(i, j) != b2.getRGB(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Transforma em um BufferedImage.
	 * 
	 * @param image
	 * @return BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image image) {
		final int grayScale = 10;
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), grayScale);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, null, null);
		return bufferedImage;
	}
}
