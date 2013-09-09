/*
 * Copyright 2011 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 27/09/2011 - ‎16:11:14
 * 
 * ================================================================================
 * 
 * Direitos autorais 2011 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 27/09/2011 - ‎16:11:14
 * 
 */

package org.jrimum.bopepo.view;

import static java.lang.String.format;
import static org.jrimum.utilix.Objects.isNull;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.jrimum.bopepo.pdf.Files;
import org.jrimum.utilix.ClassLoaders;
import org.jrimum.utilix.Exceptions;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Strings;

/**
 * Acessa os resources usados pelo Bopepo e os mantém nesta instância para as
 * próximas chamadas.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class ResourceBundle {
	
	/**
	 * Nome do arquivo pdf.
	 */
	public static final String BOLETO_TEMPLATE_COM_SACADOR_AVALISTA = "BoletoTemplateComSacadorAvalista.pdf";
	
	/**
	 * Nome do arquivo pdf.
	 */
	public static final String BOLETO_TEMPLATE_SEM_SACADOR_AVALISTA = "BoletoTemplateSemSacadorAvalista.pdf";

	/**
	 * Imagens usadas na instancia. 
	 */
	private final Map<String,Image> imagensLogoBanco;

	/**
	 * Template do projeto usando na isntancia.
	 */
	private byte[] defaultTemplateComSacadorAvalista;
	
	/**
	 * Template do projeto usando na isntancia.
	 */
	private byte[] defaultTemplateSemSacadorAvalista;
	
	/**
	 * Inicia a instancia com os valores padrões necessários.
	 */
	public ResourceBundle(){
		imagensLogoBanco = new TreeMap<String, Image>();
	}

	/**
	 * 
	 * 
	 * @return template em bytes
	 */
	public byte[] getTemplateComSacadorAvalista() {

		if (isNull(defaultTemplateComSacadorAvalista)) {

			defaultTemplateComSacadorAvalista = loadPdf("BoletoTemplateComSacadorAvalista.pdf");
		}

		return defaultTemplateComSacadorAvalista;
	}

	public byte[] getTemplateSemSacadorAvalista() {

		if (isNull(defaultTemplateSemSacadorAvalista)) {

			defaultTemplateSemSacadorAvalista = loadPdf("BoletoTemplateSemSacadorAvalista.pdf");
		}

		return defaultTemplateSemSacadorAvalista;
	}
	
	public Image getLogotipoDoBanco(String codigo){
		
		Image logo = imagensLogoBanco.get(codigo);
		
		if(isNull(logo)){
			logo = loadLogotipoDoBanco(codigo);
			imagensLogoBanco.put(codigo, logo);
		}

		return logo;
	}
	
	private BufferedImage loadLogotipoDoBanco(String codigo){	
		
		final String path = "/img/%s.png";
		
		Strings.checkNotBlank(codigo,"Codigo do banco não informado!");
		
		final String logo = format(path, codigo);
		
		URL url = ClassLoaders.getResource(logo, this.getClass());
		
		Objects.checkNotNull(url, format("Logo não \"%s\" não encontrada!",logo));		

		BufferedImage imageLogo = null;
		
		try {
			imageLogo = ImageIO.read(url);
			Objects.checkNotNull(imageLogo);	
		} catch (IOException e) {
			Exceptions.throwIllegalStateException("Erro ao tentar ler a imagem logotipo do banco "+codigo,e);
		}
		
		return imageLogo;
	}
	
	private byte[] loadPdf(String fileName){
		
		byte[] pdf = null;
		InputStream is = null;
		
		try {
			
			is = ClassLoaders.getResource(
						"/pdf/"+fileName,
						this.getClass()).openStream();
			
			pdf = Files.toByteArray(is);
			
		} catch (Exception e) {
			
			Exceptions.throwIllegalStateException(e);
			
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (Exception e) {
					Exceptions.throwIllegalStateException(e);
				}
			}
		}
		
		return pdf;
	}
}
