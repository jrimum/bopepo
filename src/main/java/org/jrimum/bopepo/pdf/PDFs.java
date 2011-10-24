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

import static org.jrimum.utilix.Objects.isNotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jrimum.utilix.Exceptions;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;

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

	public static byte[] mergeFiles(Collection<byte[]> pdfFiles) {
		
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
//			document.addAuthor(author);
//			document.addCreator(creator);
//			document.addTitle(title);
//			document.addSubject(subject);
//			document.addKeywords(keywords);
			
			copy.close();
			document.close();
			byteOS.close();

			return byteOS.toByteArray();
			
		}catch (Exception e) {
			return Exceptions.throwIllegalStateException(e);
		}
	}
	
	/**
	 * <p>
	 * Junta varios arquivos pdf em um só.
	 * </p>
	 * 
	 * @param pdfFiles
	 *            Coleção de array de bytes
	 * 
	 * @return Arquivo PDF em forma de byte
	 * @since 0.2
	 */
//	public static byte[] mergeFiles(Collection<byte[]> pdfFiles) {
//
//		// retorno
//		byte[] bytes = null;
//
//		if (isNotNull(pdfFiles) && !pdfFiles.isEmpty()) {
//
//			int pageOffset = 0;
//			boolean first = true;
//
//			List<Object> master = null;
//			Document document = null;
//			PdfCopy writer = null;
//			ByteArrayOutputStream byteOS = null;
//
//			try {
//
//				byteOS = new ByteArrayOutputStream();
//				master = new ArrayList<Object>();
//
//				for (byte[] doc : pdfFiles) {
//
//					if (isNotNull(doc)) {
//
//						PdfReader reader = new PdfReader(doc);
//
//						if (reader.isEncrypted()) {
//							reader = new PdfReader(doc, "".getBytes());
//						}
//
//						reader.consolidateNamedDestinations();
//
//						int n = reader.getNumberOfPages();
//						List<?> bookmarks = SimpleBookmark.getBookmark(reader);
//
//						if (isNotNull(bookmarks)) {
//							if (pageOffset != 0) {
//								SimpleBookmark.shiftPageNumbers(bookmarks,
//										pageOffset, null);
//							}
//							master.addAll(bookmarks);
//						}
//
//						pageOffset += n;
//
//						if (first) {
//
//							document = new Document(reader
//									.getPageSizeWithRotation(1));
//
//							writer = new PdfCopy(document, byteOS);
//							
//							document.addAuthor("JRimum Group");
//							document.addSubject("JRimum Merged Document");
//							document.addCreator("JRimum Utilix");
//
//							document.open();
//							first = false;
//						}
//
//						PdfImportedPage page;
//						
//						for (int i = 0; i < n;) {
//							++i;
//							page = writer.getImportedPage(reader, i);
//
//							writer.addPage(page);
//						}
//						
//						reader.close();
//					}
//				}
//
//				if (master.size() > 0) {
//					writer.setOutlines(master);
//				}
//
//				if (isNotNull(document)) {
//					writer.close();
//					document.close();
//				}
//
//				bytes = byteOS.toByteArray();
//				
//				byteOS.close();
//				
//				pdfFiles.clear();
//
//			} catch (Exception e) {
//				Exceptions.throwIllegalStateException(e);
//			}
//		}
//		return bytes;
//	}
}
