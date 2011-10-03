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
 * Created at: 19/09/2011 - 15:43:26
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
 * Criado em: 19/09/2011 - 15:43:26
 * 
 */

package org.jrimum.bopepo.pdf;

import static org.jrimum.utilix.Collections.hasElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jrimum.utilix.Exceptions;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;

/**
 * Leitor de documentos PDF.
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class PdfDocReader{
	
	private final PdfReader reader; 
	private final AcroFields form;
	private final PdfDocInfo docInfo;

	/**
	 * Modo de criação não utilizado e não permitido. 
	 */
	@SuppressWarnings("unused")
	private PdfDocReader() {
		Exceptions.throwIllegalStateException("Estado não permitido!");
		reader = null;
		this.form = null;
		this.docInfo = null;
	}

	/**
	 * Ler e analisa o documento.
	 * 
	 * @param pdfIn
	 *            Byte array contendo o documento
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(byte[] pdfIn){
		PdfReader r = null;
		try {
			r = new PdfReader(pdfIn);
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Ler e analisa o documento.
	 * 
	 * @param is
	 *            Stream contendo o documento
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(InputStream is){
		PdfReader r = null;
		try {
			r = new PdfReader(is);
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Ler e analisa o documento.
	 * 
	 * @param url
	 *            URL do documento
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(URL url){
		PdfReader r = null;
		try {
			r = new PdfReader(url);
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Ler e analisa o documento.
	 * 
	 * @param file
	 *            Arquivo contendo o documento
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(File file){
		PdfReader r = null;
		try {
			r = new PdfReader(new FileInputStream(file));
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Ler e analisa o documento.
	 * 
	 * @param pdfIn
	 *            Byte array contendo o documento
	 * @param ownerPassword
	 *            Senha para ler o documento.
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(byte[] pdfIn, byte[] ownerPassword){
		PdfReader r = null;
		try {
			r = new PdfReader(pdfIn,ownerPassword);
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Ler e analisa o documento.
	 * 
	 * @param is
	 *            Stream contendo o documento
	 * @param ownerPassword
	 *            Senha para ler o documento.
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(InputStream is, byte[] ownerPassword){
		PdfReader r = null;
		try {
			r = new PdfReader(is,ownerPassword);
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Ler e analisa o documento.
	 * 
	 * @param url
	 *            URL do documento
	 * @param ownerPassword
	 *            Senha para ler o documento.
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(URL url, byte[] ownerPassword){
		PdfReader r = null;
		try {
			r = new PdfReader(url,ownerPassword);
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Ler e analisa o documento.
	 * 
	 * @param file
	 *            Arquivo contendo o documento
	 * @param ownerPassword
	 *            Senha para ler o documento.
	 */
	@SuppressWarnings("unchecked")
	public PdfDocReader(File file, byte[] ownerPassword){
		PdfReader r = null;
		try {
			r = new PdfReader(new FileInputStream(file),ownerPassword);
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
		this.reader = r;
		this.form = reader.getAcroFields();
		this.docInfo = PdfDocInfo.create(this.reader.getInfo());
	}
	
	/**
	 * Retorna, ou não, o valor de um campo procurando pelo seu nome
	 * ("fully qualified" ou não).
	 * 
	 * @param name
	 *            Nome do campo
	 * @return Valor do campo
	 */
	public String getField(String name){
		
		return this.form.getField(name);
	}

	/**
	 * Retorna todos os nomes de campos contidos no documento. Os nomes são
	 * dados na forma "fully qualified".
	 * 
	 * @return Coleção de nomes em um {@linkplain java.util.Set}
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getFieldsNames() {

		return form.getFields().keySet();
	}
	
	/**
	 * Retorna todos os campos contidos no documento. Os nomes dos campos são
	 * dados na forma "fully qualified".
	 * 
	 * @return Map(campo,valor)
	 */
	public Map<String,String> getFields(){
		
		Collection<String> names = getFieldsNames();
		
		if(hasElement(names)){
			Map<String,String> fields = new HashMap<String, String>(names.size());
			for(String name : names){
				fields.put(name, getField(name));
			}
			return fields;
		}
		
		return Collections.emptyMap();
	}
	
	/**
	 * Retorna as informações sobre o documento: Título, Autor, etc.
	 * 
	 * @return info
	 */
	public PdfDocInfo getInfo(){
		
		return this.docInfo;
	}
	
	/**
	 * Fecha o leitor. Necessário para liberar o recurso.
	 */
	public void close(){
		try {
			this.reader.close();
		} catch (Exception e) {
			Exceptions.throwIllegalStateException(e);
		}
	}
	
}
