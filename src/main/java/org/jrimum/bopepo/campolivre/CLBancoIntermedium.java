/*
 * Copyright 2010 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 11/08/2010 - 10:23:00
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 11/08/2010 - 10:23:00
 */


package org.jrimum.bopepo.campolivre;

import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.texgit.type.component.Fillers;
import org.jrimum.texgit.type.component.FixedField;
import org.jrimum.utilix.Exceptions;


/**
 * O campo livre do Banco Intermedium deve seguir esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th>Conteúdo</th>
 * </thead> </tr>
 * <tr>
 * <td >20-23</td>
 * <td >4</td>
 * <td >4 </td>
 * <td >Código da agência</td>
 * </tr>
 * <tr>
 * <td >24-25</td>
 * <td >2</td>
 * <td >2 </td>
 * <td >Constante = 70</td>
 * </tr>
 * <tr>
 * <td >26-36</td>
 * <td >11</td>
 * <td >11 </td>
 * <td >Nosso número sem o dígito</td>
 * </tr>
  * <tr>
 * <td >37-42</td>
 *<td >6</td>
 * <td >6 </td>
 * <td >Código da conta</td>
 * </tr>
 * <tr>
 * <td >43-43</td>
 * <td >1</td>
 * <td >1 </td>
 * <td >Dígito da conta</td>
 * </tr>
  * <tr>
 * <td >44-44</td>
 * <td >1</td>
 * <td >1 </td>
 * <td >Constante = 0</td>
 * </tr>
 * </table>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:lukas.antunes@virtualsistemas.com.br">Lukas Antunes</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
class CLBancoIntermedium extends AbstractCLBancoIntermedium { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 858563493013156459L;
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;
	
	private static final Integer CONSTANTE_70 = Integer.valueOf(70);
	
	private static final Integer CONSTANTE_0 = Integer.valueOf(0);

	/**
	 * <p>
	 *   Dado um título, cria um campo livre para banco Intermedium (077).  
	 * </p>
	 * 
	 * @param titulo - título com as informações para geração do campo livre
	 */
	CLBancoIntermedium(Titulo titulo) {
		
		super(FIELDS_LENGTH);
		
		this.add(new FixedField<Integer>(titulo.getContaBancaria().getAgencia().getCodigo(), 4, Fillers.ZERO_LEFT));
		this.add(new FixedField<Integer>(CONSTANTE_70, 2));
		this.add(new FixedField<String>(titulo.getNossoNumero(), 11, Fillers.ZERO_LEFT));	
		this.add(new FixedField<Integer>( titulo.getContaBancaria().getNumeroDaConta().getCodigoDaConta(), 6, Fillers.ZERO_LEFT));
		this.add(new FixedField<String>( titulo.getContaBancaria().getNumeroDaConta().getDigitoDaConta(), 1, Fillers.ZERO_LEFT));
		this.add(new FixedField<Integer>(CONSTANTE_0, 1));
	}

	@Override
	protected void addFields(Titulo titulo) {
		// TODO IMPLEMENTAR
		Exceptions.throwUnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}

	@Override
	protected void checkValues(Titulo titulo) {
		// TODO IMPLEMENTAR
		Exceptions.throwUnsupportedOperationException("AINDA NÃO IMPLEMENTADO!");
	}
}
