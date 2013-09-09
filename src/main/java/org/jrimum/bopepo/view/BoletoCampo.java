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
 * Created at: 29/08/2013 - 19:19:25
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
 * Criado em: 29/08/2013 - 19:19:25
 * 
 */

package org.jrimum.bopepo.view;

/**
 * Enumeração com todos os campos padrão FEBRABAN utilizados no boleto. 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 * @since 0.2
 * 
 * @version 0.2
 */
public enum BoletoCampo {
	
	/*
	 * Campos em ordem alfabetica
	 */
	
	//Ficha de Compensação
	txtFcAceite,
	txtFcAgenciaCodigoCedente,
	txtFcCarteira,
	txtFcCedente,
	txtFcCodBanco,
	txtFcCodigoBarra,
	txtFcDataDocumento,
	txtFcDataProcessamento,
	txtFcDataVencimento,
	txtFcDescontoAbatimento,
	txtFcEspecie,
	txtFcEspecieDocumento,
	txtFcInstrucaoAoCaixa1,
	txtFcInstrucaoAoCaixa2,
	txtFcInstrucaoAoCaixa3,
	txtFcInstrucaoAoCaixa4,
	txtFcInstrucaoAoCaixa5,
	txtFcInstrucaoAoCaixa6,
	txtFcInstrucaoAoCaixa7,
	txtFcInstrucaoAoCaixa8,
	txtFcLinhaDigitavel,
	txtFcLocalPagamento,
	txtFcLogoBanco,
	txtFcMoraMulta,
	txtFcNossoNumero,
	txtFcNumeroDocumento,
	txtFcOutraDeducao,
	txtFcOutroAcrescimo,
	txtFcQuantidade,
	txtFcSacadoL1,
	txtFcSacadoL2,
	txtFcSacadoL3,
	txtFcSacadorAvalistaL1,
	txtFcSacadorAvalistaL2,
	txtFcSacadorAvalistaL3,
	txtFcUsoBanco,
	txtFcValor,
	txtFcValorCobrado,
	txtFcValorDocumento,
	//Recibo do Sacado
	txtRsAgenciaCodigoCedente,
	txtRsCedente,
	txtRsCodBanco,
	txtRsCpfCnpj,
	txtRsDataVencimento,
	txtRsDescontoAbatimento,
	txtRsEspecie,
	txtRsInstrucaoAoSacado,
	txtRsLinhaDigitavel,
	txtRsLogoBanco,
	txtRsMoraMulta,
	txtRsNossoNumero,
	txtRsNumeroDocumento,
	txtRsOutraDeducao,
	txtRsOutroAcrescimo,
	txtRsQuantidade,
	txtRsSacado,
	txtRsValorCobrado,
	txtRsValorDocumento
}
