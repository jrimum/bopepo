/* 
 * Copyright 2011 JRimum Project
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
 * Created at: 14/01/2011 - 19:48:01
 *
 * ================================================================================
 *
 * Direitos autorais 2011 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 14/01/2011 - 19:48:01
 * 
 */

package org.jrimum.bopepo.view;

import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.jrimum.bopepo.Boleto;

/**
 * <p>
 * Classe utilizada para gerar a visualização Jasper do boleto com os dados do
 * título e boleto.
 * </p>
 * 
 * @author <a href="http://www.renatoatilio.com/">Renato Atilio</a>
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public class JasperViewer extends AbstractViewer {
	private Map<String, Object>	fields;

	private static String		TEMPLATE_PADRAO	= "/jasper/BoletoTemplate.jrxml";

	@Override
	protected void setText(String field, String content) {
		fields.put(field, content);
	}

	@Override
	protected void setImage(String field, Image image) {
		fields.put(field, image);
	}

	@Override
	protected void setBarcode(String field, String content) {
		setText(field, content);
	}

	// TODO remover este método após alterar a API em BoletoViewer
	public void setTemplate(String pathname) {
		setTemplate(new File(pathname));
	}

	// TODO deixar protected após criar a API em BoletoViewer
	public JasperPrint getJasperPrint(List<Boleto> boletos) throws Exception {
		return getJasperPrint(boletos, new HashMap<String, Object>());
	}

	protected JasperPrint getJasperPrint(List<Boleto> boletos, Map<String, Object> params) throws Exception {
		String fileName = isTemplateFromResource() ? getTemplateFromResource() : getTemplate().getAbsolutePath();

		return JasperFillManager.fillReport(getJasperReport(fileName), params, new JRMapCollectionDataSource(getFields(boletos)));
	}

	private static JasperReport getJasperReport(String fileName) throws Exception {
		JasperReport jasperReport = null;

		InputStream src = JasperViewer.class.getResourceAsStream(fileName);

		JasperDesign jasperDesign = JRXmlLoader.load(src);
		jasperReport = JasperCompileManager.compileReport(jasperDesign);

		return jasperReport;
	}

	private Collection<Map<String, Object>> getFields(Collection<Boleto> boletos) throws Exception {
		Collection<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Boleto boleto : boletos) {
			fields = new HashMap<String, Object>();

			this.boleto = boleto;

			// Adicionando o atributo boleto com o objeto completo
			fields.put("boleto", boleto);
			
			preencher();

			list.add(fields);
		}

		return list;
	}

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @return String template
	 * 
	 * @since
	 */
	private String getTemplateFromResource() {
		return TEMPLATE_PADRAO;
	}
}
