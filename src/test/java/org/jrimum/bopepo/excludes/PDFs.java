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
 * Created at: 12/01/2014 - 17:09:13
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
 * Criado em: 12/01/2014 - 17:09:13 
 * 
 */

package org.jrimum.bopepo.excludes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

/**
 * Utilitário para operações com PDFs.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 */
public class PDFs {
	
	public static  Map<String,Collection<BufferedImage>> getImages(byte[] pdf) throws IOException{
		PdfReader reader = new PdfReader(pdf);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        ImageRenderListener listener = new ImageRenderListener();
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            parser.processContent(i, listener);
        }
        reader.close();
		return listener.getImages();
	}

	public static class ImageRenderListener implements RenderListener {
		private Multimap<String, BufferedImage> images = ArrayListMultimap
				.create();
		public void renderImage(ImageRenderInfo renderInfo) {
			try {
				PdfImageObject image = renderInfo.getImage();
				if (image == null) {
					return;
				}
				final String ref = String.format("Ref-%s-%s", renderInfo.getRef()
						.getNumber(), image.getFileType());
				images.put(ref, image.getBufferedImage());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		public  Map<String,Collection<BufferedImage>> getImages(){
			return images.asMap();
		}
		public void beginTextBlock() {}
		public void endTextBlock() {}
		public void renderText(TextRenderInfo renderInfo) {}
	}
}
