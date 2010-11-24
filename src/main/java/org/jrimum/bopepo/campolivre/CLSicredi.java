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
 * Created at: 03/10/2008 - 16:13:14
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
 * Criado em: 03/10/2008 - 16:13:14
 * 
 */

package org.jrimum.bopepo.campolivre;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;

import org.apache.commons.lang.StringUtils;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;
import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

/**
 * <p>
 * O campo livre definido pela <a href="http://www.sicredi.com.br/">Sicredi</a>.
 * </p>
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Conteúdo</th>
 * </thead> </tr>
 * <tr>
 * <td >20-20</td>
 * <td >1</td>
 * <td >Código númerico correspondente ao tipo de cobrança: "3" - SICREDI</td>
 * </tr>
 * <tr>
 * <td >21-21</td>
 * <td >1</td>
 * <td >Código númerico correspondente ao tipo de carteira: "1" - carteira
 * simples</td>
 * </tr>
 * <tr>
 * <td >22-30</td>
 * <td >9</td>
 * <td >Nosso Número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >31-34</td>
 * <td >4</td>
 * <td >Cooperativa de crédito / agência cedente</td>
 * </tr>
 * <tr>
 * <td >35-36</td>
 * <td >2</td>
 * <td >Unidade de atendimento / posto da agênica cedente</td>
 * </tr>
 * <tr>
 * <td >37-41</td>
 * <td >5</td>
 * <td >Código do Cedente</td>
 * </tr>
 * <tr>
 * <td >42-42</td>
 * <td >1</td>
 * <td >Filler: 1 (um) quando o campo "valor do documento" diferente de 0
 * (zero), caso contrário zero.</td>
 * </tr>
 * <tr>
 * <td >43-43</td>
 * <td >1</td>
 * <td >Zero Fixo</td>
 * </tr>
 * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >Dígito verificador do campo livre calculado por módulo 11 com
 * aproveitamento total (resto igual a (0) zero ou (1) um o Dígito será (0)
 * zero)</td>
 * </tr>
 * </table>
 * 
 * 
 * @see org.jrimum.bopepo.campolivre.AbstractCampoLivre
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 */
class CLSicredi extends AbstractCLSicredi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7697120719706717353L;
	
	private static final Integer FIELDS_LENGTH = 9;

	private static final Modulo modulo11 = new Modulo(TipoDeModulo.MODULO11);
	
	/**
	 * <p>
	 * Código númerico correspondente ao tipo de cobrança: "1" - Com Registro.
	 * </p>
	 */
	private static final String COBRANCA_COM_REGISTRO = "1";
	
	/**
	 * <p>
	 * Código númerico correspondente ao tipo de cobrança: "3" - Sem Registro.
	 * </p>
	 */
	private static final String COBRANCA_SEM_REGISTRO = "3";

	/**
	 * <p>
	 * Código númerico correspondente ao tipo de carteira: "1" - carteira
	 * simples.
	 * </p>
	 */
	private static final String CARTEIRA = "1";

	/**
	 * <p>
	 * Segunda posição do campo livre.
	 * </p>
	 */
	private static final Field<String> FIELD_CARTEIRA = new Field<String>(CARTEIRA, 1);

	protected CLSicredi(Titulo titulo) {
		
		super(FIELDS_LENGTH);
		
		throw new UnsupportedOperationException("CAMPO LIVRE AINDA NÃO IMPLEMENTADO NESSE SNAPSHOT!");
		
		//TODO FAZER
		
//		if(titulo.getContaBancaria().getCarteira().isComRegistro()){
//			
//			this.add(new Field<String>(COBRANCA_COM_REGISTRO, 1));
//			
//		}else{
//			
//			this.add(new Field<String>(COBRANCA_SEM_REGISTRO, 1));
//		}
//		
//		this.add(FIELD_CARTEIRA);
//
//		this.add(new Field<String>(titulo.getNossoNumero(), 9, Filler.ZERO_LEFT));
//
//
//		if (titulo.getValor() != null && titulo.getValor().doubleValue() > 0) {
//			
//			this.add(new Field<String>("1", 1));
//			
//		} else {
//			this.add(new Field<String>("0", 1));
//		}
//
//		this.add(new Field<String>("0", 1));
//		this.add(new Field<String>(calculeDigitoVerificador(), 1));

	}
	
	private String calculeDigitoVerificador() {

		Integer dv = 0;

		int resto = modulo11.calcule(writeFields());

		if (resto != 0 && resto != 1) {

			dv = modulo11.valor() - resto;
		} else
			dv = resto;

		return "" + dv;
	}
}
