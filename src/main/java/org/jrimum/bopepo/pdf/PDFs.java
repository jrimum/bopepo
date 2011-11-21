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
 * Created at: 30/03/2008 - 23:49:00
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
 * Criado em: 30/03/2008 - 23:49:00
 * 
 */

package org.jrimum.bopepo.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import org.jrimum.utilix.Exceptions;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * Serviços e atividades relacionadas a manipulação de PDF (provavelmente da lib
 * iText).
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class PDFs{

	/**
	 * <p>
	 * Muda um input field para uma imgem com as dimensões e possição do field.
	 * </p>
	 * 
	 * @param stamper
	 * @param positions
	 * @param image
	 * @return rectanglePDF
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	public static PdfRectangle changeFieldToImage(PdfStamper stamper,
			float[] positions, Image image) throws DocumentException {

		PdfRectangle rect = new PdfRectangle(positions);

		return changeFieldToImage(stamper, rect, image);
	}

	/**
	 * <p>
	 * Muda um input field para uma imgem com as dimensões e possição do field.
	 * </p>
	 * 
	 * @param stamper
	 * @param rect
	 * @param image
	 * @return rectanglePDF
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */
	public static PdfRectangle changeFieldToImage(PdfStamper stamper,
			PdfRectangle rect, Image image) throws DocumentException {

		// Ajustando o tamanho da imagem de acordo com o tamanho do campo.
		// image.scaleToFit(rect.getWidth(), rect.getHeight());
		image.scaleAbsolute(rect.getWidth(), rect.getHeight());

		// A rotina abaixo tem por objetivo deixar a imagem posicionada no
		// centro
		// do field, tanto na perspectiva horizontal como na vertical.
		// Caso não se queira mais posicionar a imagem no centro do field, basta
		// efetuar a chamada a seguir:
		// "image.setAbsolutePosition
		// (rect.getLowerLeftX(),rect.getLowerLeftY());"
		image.setAbsolutePosition(rect.getLowerLeftX()
				+ (rect.getWidth() - image.getScaledWidth()) / 2, rect
				.getLowerLeftY()
				+ (rect.getHeight() - image.getScaledHeight()) / 2);

		stamper.getOverContent(rect.getPage()).addImage(image);
		
		return rect;
	}
	
	/**
	 * Junta varios arquivos pdf em um só.
	 * 
	 * @param pdfFiles
	 *            Coleção de array de bytes
	 * 
	 * @return Arquivo PDF em forma de byte
	 * @since 0.2
	 */
	public static byte[] mergeFiles(Collection<byte[]> pdfFiles) {
		
		return mergeFiles(pdfFiles, null);
	}
	
	/**
	 * Junta varios arquivos pdf em um só.
	 * 
	 * @param pdfFiles
	 *            Coleção de array de bytes
	 * @param info
	 *            Usa somente as informações
	 *            (title,subject,keywords,author,creator)
	 * 
	 * @return Arquivo PDF em forma de byte
	 * 
	 * @since 0.2
	 */
	public static byte[] mergeFiles(Collection<byte[]> pdfFiles, PdfDocInfo info) {
		
		try{
			
			ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
			
			Document document = new Document();
			
			PdfCopy copy = new PdfCopy(document, byteOS);
			
			document.open();
			
			for (byte[] f : pdfFiles) {

				PdfReader reader = new PdfReader(f);

				for (int page = 1; page <= reader.getNumberOfPages(); page++) {

					copy.addPage(copy.getImportedPage(reader, page));
				}

				reader.close();
			}
			
			document.addCreationDate();
			
			if(info != null){
				
				 document.addAuthor(info.author());
				 document.addCreator(info.creator());
				 document.addTitle(info.title());
				 document.addSubject(info.subject());
				 document.addKeywords(info.keywords());
			}
			
			copy.close();
			document.close();
			byteOS.close();

			return byteOS.toByteArray();
			
		}catch (Exception e) {
			return Exceptions.throwIllegalStateException(e);
		}
	}
	
}
