/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 19:08:39
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
 * Criado em: 30/03/2008 - 19:08:39
 * 
 */

package br.com.nordestefomento.jrimum.bopepo;

import java.awt.Image;

import br.com.nordestefomento.jrimum.domkee.entity.Banco;
import br.com.nordestefomento.jrimum.domkee.ientity.IBanco;
import br.com.nordestefomento.jrimum.domkee.type.ACadastroDePessoa;
import br.com.nordestefomento.jrimum.domkee.type.CNPJ;




/**
 * 
 * <p>
 * Enumeração dos bancos segundo o <a href="http://www.bcb.gov.br>Banco Central do Brasil</a> em 04.04.2008.
 * </p>
 * 
 * <p>
 * Aqui se encontra todos os bancos sob a <a href="http://www.bcb.gov.br/?CHEQUESCOMPE">supervisão da BACEN</a>, em funcionamento no país.
 * </p>
 * 
 * <p>
 * EXEMPLO: 
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2 
 */
	
public enum EnumBanco implements IBanco{
	
	/*
	 * Instâncias do EnumBanco  
	 */
	
	BANCO_DO_BRASIL("001",ACadastroDePessoa.getInstance("00000000000191"), "BANCO DO BRASIL S.A.","Banco do Brasil - Banco Múltiplo"),
	BANCO_DA_AMAZONIA("003",ACadastroDePessoa.getInstance("04902979000144"), "BANCO DA AMAZONIA S.A.","Banco Comercial"),
	BANCO_DO_NORDESTE_DO_BRASIL("004",ACadastroDePessoa.getInstance("07237373000120"), "BANCO DO NORDESTE DO BRASIL S.A.","Banco Múltiplo"),
	BANESTES_BANCO_DO_ESTADO_DO_ESPIRITO_SANTO("021",ACadastroDePessoa.getInstance("28127603000178"), "BANESTES S.A. BANCO DO ESTADO DO ESPIRITO SANTO","Banco Múltiplo"),
	BANCO_DE_PERNAMBUCO_BANDEPE("024",ACadastroDePessoa.getInstance("10866788000177"), "BANCO DE PERNAMBUCO S.A. - BANDEPE","Banco Múltiplo"),
	BANCO_ALFA("025",ACadastroDePessoa.getInstance("03323840000183"), "BANCO ALFA S.A.","Banco Comercial"),
	BANCO_DO_ESTADO_DE_SANTA_CATARINA("027",ACadastroDePessoa.getInstance("83876003000110"), "BANCO DO ESTADO DE SANTA CATARINA S.A.","Banco Comercial"),
	BANCO_BANERJ("029",ACadastroDePessoa.getInstance("33885724000119"), "BANCO BANERJ S.A.","Banco Múltiplo"),
	BANCO_BEG("031",ACadastroDePessoa.getInstance("01540541000175"), "BANCO BEG S.A.","Banco Múltiplo"),
	BANCO_SANTANDER_BANESPA("033",ACadastroDePessoa.getInstance("90400888000142"), "BANCO SANTANDER BANESPA S.A.","Banco Múltiplo"),
	BANCO_BRADESCO_BBI("036",ACadastroDePessoa.getInstance("06271464000119"), "BANCO BRADESCO BBI S.A.","Banco Múltiplo"),
	BANCO_DO_ESTADO_DO_PARA("037",ACadastroDePessoa.getInstance("04913711000108"), "BANCO DO ESTADO DO PARÁ S.A.","Banco Múltiplo"),
	BANCO_BANESTADO("038",ACadastroDePessoa.getInstance("76492172000191"), "BANCO BANESTADO S.A.","Banco Múltiplo"),
	BANCO_DO_ESTADO_DO_PIAUI_BEP("039",ACadastroDePessoa.getInstance("06833131000136"), "BANCO DO ESTADO DO PIAUÍ S.A. - BEP","Banco Múltiplo"),
	BANCO_CARGILL("040",ACadastroDePessoa.getInstance("03609817000150"), "BANCO CARGILL S.A.","Banco Múltiplo"),
	BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL("041",ACadastroDePessoa.getInstance("92702067000196"), "BANCO DO ESTADO DO RIO GRANDE DO SUL S.A.","Banco Múltiplo"),
	BANCO_BVA("044",ACadastroDePessoa.getInstance("32254138000103"), "BANCO BVA S.A.","Banco Múltiplo"),
	BANCO_OPPORTUNITY("045",ACadastroDePessoa.getInstance("33857830000199"), "BANCO OPPORTUNITY S.A.","Banco Múltiplo"),
	BANCO_DO_ESTADO_DE_SERGIPE("047",ACadastroDePessoa.getInstance("13009717000146"), "BANCO DO ESTADO DE SERGIPE S.A.","Banco Múltiplo"),
	HIPERCARD_BANCO_MULTIPLO("062",ACadastroDePessoa.getInstance("03012230000169"), "HIPERCARD BANCO MÚLTIPLO S.A.","Banco Múltiplo"),
	BANCO_IBI_BANCO_MULTIPLO("063",ACadastroDePessoa.getInstance("04184779000101"), "BANCO IBI S.A. - BANCO MÚLTIPLO","Banco Múltiplo"),
	LEMON_BANK_BANCO_MULTIPLO("065",ACadastroDePessoa.getInstance("48795256000169"), "LEMON BANK BANCO MULTIPLO S.A.","Banco Múltiplo"),
	BANCO_MORGAN_STANLEY_DEAN_WITTER("066",ACadastroDePessoa.getInstance("02801938000136"), "BANCO MORGAN STANLEY DEAN WITTER S.A.","Banco Múltiplo"),
	BPN_BRASIL_BANCO_MULTIPLO("069",ACadastroDePessoa.getInstance("61033106000186"), "BPN BRASIL BANCO MÚLTIPLO S.A.","Banco Múltiplo"),
	BRB_BANCO_DE_BRASILIA("070",ACadastroDePessoa.getInstance("00000208000100"), "BRB - BANCO DE BRASILIA S.A.","Banco Múltiplo"),
	BANCO_RURAL_MAIS("072",ACadastroDePessoa.getInstance("33074683000180"), "BANCO RURAL MAIS S.A.","Banco Múltiplo"),
	BB_BANCO_POPULAR_DO_BRASIL("073",ACadastroDePessoa.getInstance("06043056000100"), "BB BANCO POPULAR DO BRASIL S.A.","Banco Múltiplo"),
	BANCO_J_SAFRA("074",ACadastroDePessoa.getInstance("03017677000120"), "BANCO J. SAFRA S.A.","Banco Múltiplo"),
	BANCO_CR2("075",ACadastroDePessoa.getInstance("03532415000102"), "BANCO CR2 S/A","Banco Comercial"),
	BANCO_KDB_DO_BRASIL("076",ACadastroDePessoa.getInstance("07656500000125"), "BANCO KDB DO BRASIL S.A.","Banco Múltiplo"),
	BANCO_BMeF_DE_SERVICOS_DE_LIQUIDACAO_E_CUSTODIA("096",ACadastroDePessoa.getInstance("00997185000150"), "BANCO BM&F DE SERVIÇOS DE LIQUIDAÇÃO E CUSTÓDIA S.A.","Banco Comercial"),
	CAIXA_ECONOMICA_FEDERAL("104",ACadastroDePessoa.getInstance("00360305000104"), "CAIXA ECONOMICA FEDERAL","Caixa Econômica Federal"),
	BANCO_BBM("107",ACadastroDePessoa.getInstance("15114366000169"), "BANCO BBM S/A","Banco Múltiplo"),
	BANCO_UNICO("116",ACadastroDePessoa.getInstance("00086413000130"), "BANCO ÚNICO S.A.","Banco Múltiplo"),
	BANCO_NOSSA_CAIXA("151",ACadastroDePessoa.getInstance("43073394000110"), "BANCO NOSSA CAIXA S.A.","Banco Múltiplo"),
	BANCO_FINASA("175",ACadastroDePessoa.getInstance("57561615000104"), "BANCO FINASA S.A.","Banco Múltiplo"),
	BANCO_ITAU_BBA("184",ACadastroDePessoa.getInstance("17298092000130"), "BANCO ITAÚ BBA S.A.","Banco Múltiplo"),
	BANKPAR_BANCO_MULTIPLO("204",ACadastroDePessoa.getInstance("59438325000101"), "BANKPAR BANCO MÚLTIPLO S.A.","Banco Múltiplo"),
	BANCO_UBS_PACTUAL("208",ACadastroDePessoa.getInstance("30306294000145"), "BANCO UBS PACTUAL S.A.","Banco Múltiplo"),
	BANCO_MATONE("212",ACadastroDePessoa.getInstance("92894922000108"), "BANCO MATONE S.A.","Banco Múltiplo"),
	BANCO_ARBI("213",ACadastroDePessoa.getInstance("54403563000150"), "BANCO ARBI S.A.","Banco Comercial"),
	BANCO_DIBENS("214",ACadastroDePessoa.getInstance("61199881000106"), "BANCO DIBENS S.A.","Banco Múltiplo"),
	BANCO_COMERCIAL_E_DE_INVESTIMENTO_SUDAMERIS("215",ACadastroDePessoa.getInstance("61230165000144"), "BANCO COMERCIAL E DE INVESTIMENTO SUDAMERIS S.A.","Banco Múltiplo"),
	BANCO_JOHN_DEERE("217",ACadastroDePessoa.getInstance("91884981000132"), "BANCO JOHN DEERE S.A.","Banco Múltiplo"),
	BANCO_BONSUCESSO("218",ACadastroDePessoa.getInstance("71027866000134"), "BANCO BONSUCESSO S.A.","Banco Múltiplo"),
	BANCO_CALYON_BRASIL("222",ACadastroDePessoa.getInstance("75647891000171"), "BANCO CALYON BRASIL S.A.","Banco Múltiplo"),
	BANCO_FIBRA("224",ACadastroDePessoa.getInstance("58616418000108"), "BANCO FIBRA S.A.","Banco Múltiplo"),
	BANCO_BRASCAN("225",ACadastroDePessoa.getInstance("33923111000129"), "BANCO BRASCAN S.A.","Banco Múltiplo"),
	BANCO_CRUZEIRO_DO_SUL("229",ACadastroDePessoa.getInstance("62136254000199"), "BANCO CRUZEIRO DO SUL S.A.","Banco Múltiplo"),
	UNICARD_BANCO_MULTIPLO("230",ACadastroDePessoa.getInstance("61071387000161"), "UNICARD BANCO MÚLTIPLO S.A.","Banco Múltiplo"),
	BANCO_GE_CAPITAL("233",ACadastroDePessoa.getInstance("62421979000129"), "BANCO GE CAPITAL S.A.","Banco Múltiplo"),
	BANCO_BRADESCO("237",ACadastroDePessoa.getInstance("60746948000112"), "BANCO BRADESCO S.A.","Banco Múltiplo"),
	BANCO_CLASSICO("241",ACadastroDePessoa.getInstance("31597552000152"), "BANCO CLASSICO S.A.","Banco Múltiplo"),
	BANCO_MAXIMA("243",ACadastroDePessoa.getInstance("33923798000100"), "BANCO MÁXIMA S.A.","Banco Comercial"),
	BANCO_ABC_BRASIL("246",ACadastroDePessoa.getInstance("28195667000106"), "BANCO ABC BRASIL S.A.","Banco Múltiplo"),
	BANCO_BOAVISTA_INTERATLANTICO("248",ACadastroDePessoa.getInstance("33485541000106"), "BANCO BOAVISTA INTERATLANTICO S.A.","Banco Múltiplo"),
	BANCO_INVESTCRED_UNIBANCO("249",ACadastroDePessoa.getInstance("61182408000116"), "BANCO INVESTCRED UNIBANCO S.A.","Banco Múltiplo"),
	BANCO_SCHAHIN("250",ACadastroDePessoa.getInstance("50585090000106"), "BANCO SCHAHIN S.A.","Banco Múltiplo"),
	BANCO_FININVEST("252",ACadastroDePessoa.getInstance("33098518000169"), "BANCO FININVEST S.A.","Banco Múltiplo"),
	PARANA_BANCO("254",ACadastroDePessoa.getInstance("14388334000199"), "PARANA BANCO S.A.","Banco Múltiplo"),
	BANCO_CACIQUE("263",ACadastroDePessoa.getInstance("33349358000183"), "BANCO CACIQUE S.A.","Banco Múltiplo"),
	BANCO_FATOR("265",ACadastroDePessoa.getInstance("33644196000106"), "BANCO FATOR S.A.","Banco Múltiplo"),
	BANCO_CEDULA("266",ACadastroDePessoa.getInstance("33132044000124"), "BANCO CEDULA S.A.","Banco Múltiplo"),
	BANCO_DE_LA_NACION_ARGENTINA("300",ACadastroDePessoa.getInstance("33042151000161"), "BANCO DE LA NACION ARGENTINA","Banco Comercial Estrangeiro - Filial no país"),
	BANCO_BMG("318",ACadastroDePessoa.getInstance("61186680000174"), "BANCO BMG S.A.","Banco Múltiplo"),
	BANCO_INDUSTRIAL_E_COMERCIAL("320",ACadastroDePessoa.getInstance("07450604000189"), "BANCO INDUSTRIAL E COMERCIAL S.A.","Banco Múltiplo"),
	BANCO_ITAU("341",ACadastroDePessoa.getInstance("60701190000104"), "BANCO ITAÚ S.A.","Banco Múltiplo"),
	BANCO_SUDAMERIS_BRASIL("347",ACadastroDePessoa.getInstance("60942638000173"), "BANCO SUDAMERIS BRASIL S.A.","Banco Múltiplo"),
	BANCO_ABN_AMRO_REAL("356",ACadastroDePessoa.getInstance("33066408000115"), "BANCO ABN AMRO REAL S.A.","Banco Múltiplo"),
	BANCO_SOCIETE_GENERALE_BRASIL("366",ACadastroDePessoa.getInstance("61533584000155"), "BANCO SOCIETE GENERALE BRASIL S.A.","Banco Múltiplo"),
	BANCO_WESTLB_DO_BRASIL("370",ACadastroDePessoa.getInstance("61088183000133"), "BANCO WESTLB DO BRASIL S.A.","Banco Múltiplo"),
	BANCO_J_P_MORGAN("376",ACadastroDePessoa.getInstance("33172537000198"), "BANCO J.P. MORGAN S.A.","Banco Múltiplo"),
	BANCO_MERCANTIL_DO_BRASIL("389",ACadastroDePessoa.getInstance("17184037000110"), "BANCO MERCANTIL DO BRASIL S.A.","Banco Múltiplo"),
	BANCO_BMC("394",ACadastroDePessoa.getInstance("07207996000150"), "BANCO BMC S.A.","Banco Múltiplo"),
	HSBC_BANK_BRASIL_BANCO_MULTIPLO("399",ACadastroDePessoa.getInstance("01701201000189"), "HSBC BANK BRASIL S.A. - BANCO MULTIPLO","Banco Múltiplo"),
	UNIBANCO_UNIAO_DE_BANCOS_BRASILEIROS("409",ACadastroDePessoa.getInstance("33700394000140"), "UNIBANCO-UNIAO DE BANCOS BRASILEIROS S.A.","Banco Múltiplo"),
	BANCO_CAPITAL("412",ACadastroDePessoa.getInstance("15173776000180"), "BANCO CAPITAL S.A.","Banco Múltiplo"),
	BANCO_SAFRA("422",ACadastroDePessoa.getInstance("58160789000128"), "BANCO SAFRA S.A.","Banco Múltiplo"),
	BANCO_RURAL("453",ACadastroDePessoa.getInstance("33124959000198"), "BANCO RURAL S.A.","Banco Múltiplo"),
	BANCO_DE_TOKYO_MITSUBISHI_UFJ_BRASIL("456",ACadastroDePessoa.getInstance("60498557000126"), "BANCO DE TOKYO-MITSUBISHI UFJ BRASIL S/A","Banco Múltiplo"),
	BANCO_SUMITOMO_MITSUI_BRASILEIRO("464",ACadastroDePessoa.getInstance("60518222000122"), "BANCO SUMITOMO MITSUI BRASILEIRO S.A.","Banco Múltiplo"),
	CITIBANK("477",ACadastroDePessoa.getInstance("33042953000171"), "CITIBANK N.A.","Banco Comercial Estrangeiro - Filial no país"),
	BANCO_ITAUBANK("479",ACadastroDePessoa.getInstance("60394079000104"), "BANCO ITAUBANK S.A.","Banco Múltiplo"),
	DEUTSCHE_BANK_BANCO_ALEMAO("487",ACadastroDePessoa.getInstance("62331228000111"), "DEUTSCHE BANK S.A.BANCO ALEMAO","Banco Múltiplo"),
	JPMORGAN_CHASE_BANK_NATIONAL_ASSOCIATION("488",ACadastroDePessoa.getInstance("46518205000164"), "JPMORGAN CHASE BANK, NATIONAL ASSOCIATION","Banco Comercial Estrangeiro - Filial no país"),
	ING_BANK("492",ACadastroDePessoa.getInstance("49336860000190"), "ING BANK N.V.","Banco Comercial Estrangeiro - Filial no país"),
	BANCO_DE_LA_REPUBLICA_ORIENTAL_DEL_URUGUAY("494",ACadastroDePessoa.getInstance("51938876000114"), "BANCO DE LA REPUBLICA ORIENTAL DEL URUGUAY","Banco Comercial Estrangeiro - Filial no país"),
	BANCO_DE_LA_PROVINCIA_DE_BUENOS_AIRES("495",ACadastroDePessoa.getInstance("44189447000126"), "BANCO DE LA PROVINCIA DE BUENOS AIRES","Banco Comercial Estrangeiro - Filial no país"),
	BANCO_CREDIT_SUISSE_BRASIL("505",ACadastroDePessoa.getInstance("32062580000138"), "BANCO CREDIT SUISSE (BRASIL) S.A.","Banco Múltiplo"),
	BANCO_LUSO_BRASILEIRO("600",ACadastroDePessoa.getInstance("59118133000100"), "BANCO LUSO BRASILEIRO S.A.","Banco Múltiplo"),
	BANCO_INDUSTRIAL_DO_BRASIL("604",ACadastroDePessoa.getInstance("31895683000116"), "BANCO INDUSTRIAL DO BRASIL S.A.","Banco Múltiplo"),
	BANCO_VR("610",ACadastroDePessoa.getInstance("78626983000163"), "BANCO VR S.A.","Banco Múltiplo"),
	BANCO_PAULISTA("611",ACadastroDePessoa.getInstance("61820817000109"), "BANCO PAULISTA S.A.","Banco Comercial"),
	BANCO_GUANABARA("612",ACadastroDePessoa.getInstance("31880826000116"), "BANCO GUANABARA S.A.","Banco Múltiplo"),
	BANCO_PECUNIA("613",ACadastroDePessoa.getInstance("60850229000147"), "BANCO PECUNIA S.A.","Banco Múltiplo"),
	BANCO_PANAMERICANO("623",ACadastroDePessoa.getInstance("59285411000113"), "BANCO PANAMERICANO S.A.","Banco Múltiplo"),
	BANCO_FICSA("626",ACadastroDePessoa.getInstance("61348538000186"), "BANCO FICSA S.A.","Banco Múltiplo"),
	BANCO_INTERCAP("630",ACadastroDePessoa.getInstance("58497702000102"), "BANCO INTERCAP S.A.","Banco Múltiplo"),
	BANCO_RENDIMENTO("633",ACadastroDePessoa.getInstance("68900810000138"), "BANCO RENDIMENTO S.A.","Banco Comercial"),
	BANCO_TRIANGULO("634",ACadastroDePessoa.getInstance("17351180000159"), "BANCO TRIANGULO S.A.","Banco Múltiplo"),
	BANCO_SOFISA("637",ACadastroDePessoa.getInstance("60889128000180"), "BANCO SOFISA S.A.","Banco Múltiplo"),
	BANCO_PROSPER("638",ACadastroDePessoa.getInstance("33876475000103"), "BANCO PROSPER S.A.","Banco Múltiplo"),
	BANCO_ALVORADA("641",ACadastroDePessoa.getInstance("33870163000184"), "BANCO ALVORADA S.A.","Banco Múltiplo"),
	BANCO_PINE("643",ACadastroDePessoa.getInstance("62144175000120"), "BANCO PINE S.A.","Banco Múltiplo"),
	BANCO_ITAU_HOLDING_FINANCEIRA("652",ACadastroDePessoa.getInstance("60872504000123"), "BANCO ITAÚ HOLDING FINANCEIRA S.A.","Banco Múltiplo"),
	BANCO_INDUSVAL("653",ACadastroDePessoa.getInstance("61024352000171"), "BANCO INDUSVAL S.A.","Banco Comercial"),
	BANCO_A_J_RENNER("654",ACadastroDePessoa.getInstance("92874270000140"), "BANCO A.J. RENNER S.A.","Banco Múltiplo"),
	BANCO_VOTORANTIM("655",ACadastroDePessoa.getInstance("59588111000103"), "BANCO VOTORANTIM S.A.","Banco Múltiplo"),
	BANCO_DAYCOVAL("707",ACadastroDePessoa.getInstance("62232889000190"), "BANCO DAYCOVAL S.A.","Banco Múltiplo"),
	BANIF_BANCO_INTERNACIONAL_DO_FUNCHAL_BRASIL("719",ACadastroDePessoa.getInstance("33884941000194"), "BANIF - BANCO INTERNACIONAL DO FUNCHAL (BRASIL), S.A.","Banco Múltiplo"),
	BANCO_CREDIBEL("721",ACadastroDePessoa.getInstance("69141539000167"), "BANCO CREDIBEL S.A.","Banco Múltiplo"),
	BANCO_GERDAU("734",ACadastroDePessoa.getInstance("00183938000194"), "BANCO GERDAU S.A","Banco Múltiplo"),
	BANCO_POTTENCIAL("735",ACadastroDePessoa.getInstance("00253448000117"), "BANCO POTTENCIAL S.A.","Banco Comercial"),
	BANCO_MORADA("738",ACadastroDePessoa.getInstance("43717511000131"), "BANCO MORADA S.A","Banco Múltiplo"),
	BANCO_BGN("739",ACadastroDePessoa.getInstance("00558456000171"), "BANCO BGN S.A.","Banco Múltiplo"),
	BANCO_BARCLAYS("740",ACadastroDePessoa.getInstance("61146577000109"), "BANCO BARCLAYS S.A.","Banco Múltiplo"),
	BANCO_RIBEIRAO_PRETO("741",ACadastroDePessoa.getInstance("00517645000104"), "BANCO RIBEIRAO PRETO S.A.","Banco Múltiplo"),
	BANCO_SEMEAR("743",ACadastroDePessoa.getInstance("00795423000145"), "BANCO SEMEAR S.A.","Banco Múltiplo"),
	BANKBOSTON("744",ACadastroDePessoa.getInstance("33140666000102"), "BANKBOSTON, N.A.","Banco Comercial Estrangeiro - Filial no país"),
	BANCO_CITIBANK("745",ACadastroDePessoa.getInstance("33479023000180"), "BANCO CITIBANK S.A.","Banco Múltiplo"),
	BANCO_MODAL("746",ACadastroDePessoa.getInstance("30723886000162"), "BANCO MODAL S.A.","Banco Múltiplo"),
	BANCO_RABOBANK_INTERNATIONAL_BRASIL("747",ACadastroDePessoa.getInstance("01023570000160"), "BANCO RABOBANK INTERNATIONAL BRASIL S.A.","Banco Múltiplo"),
	BANCO_COOPERATIVO_SICREDI("748",ACadastroDePessoa.getInstance("01181521000155"), "BANCO COOPERATIVO SICREDI S.A.","Banco Múltiplo Cooperativo"),
	BANCO_SIMPLES("749",ACadastroDePessoa.getInstance("10995587000170"), "BANCO SIMPLES S.A.","Banco Múltiplo"),
	DRESDNER_BANK_BRASIL_BANCO_MULTIPLO("751",ACadastroDePessoa.getInstance("29030467000166"), "DRESDNER BANK BRASIL S.A. BANCO MULTIPLO","Banco Múltiplo"),
	BANCO_BNP_PARIBAS_BRASIL("752",ACadastroDePessoa.getInstance("01522368000182"), "BANCO BNP PARIBAS BRASIL S.A.","Banco Múltiplo"),
	BANCO_COMERCIAL_URUGUAI("753",ACadastroDePessoa.getInstance("74828799000145"), "BANCO COMERCIAL URUGUAI S.A.","Banco Múltiplo"),
	BANCO_COOPERATIVO_DO_BRASIL_BANCOOB("756",ACadastroDePessoa.getInstance("02038232000164"), "BANCO COOPERATIVO DO BRASIL S.A. - BANCOOB","Banco Comercial Cooperativo"),
	BANCO_KEB_DO_BRASIL("757",ACadastroDePessoa.getInstance("02318507000113"), "BANCO KEB DO BRASIL S.A. ","Banco Comercial");

	/**
	 * Váriavel delegada para fornecer as implementações dos métodos da interface <code>IBanco</code>
	 * 
	 * @see br.com.nordestefomento.jrimum.domkee.ientity.IBanco
	 */
	private IBanco banco;
	
		
	EnumBanco(String codigoDeCompensacao, ACadastroDePessoa cNPJ, String instituicao, String segmento){
		//TODO criar construtor proprio para CNPJ
		banco = new Banco(codigoDeCompensacao,instituicao,(CNPJ)cNPJ,segmento); 
	}

	/*
	 * Implementção da interface IBanco.
	 * ==>
	 */
	
	public IBanco getBanco() {
		return banco;
	}

	@Override
	public CNPJ getCNPJ() {
		return banco.getCNPJ();
	}

	@Override
	public String getCodigoDeCompensacao() {
		return banco.getCodigoDeCompensacao();
	}


	@Override
	public Image getImgLogo() {
		return banco.getImgLogo();
	}


	@Override
	public String getInstituicao() {
		return banco.getInstituicao();
	}


	@Override
	public String getSegmento() {
		return banco.getSegmento();
	}


	@Override
	public void setCNPJ(CNPJ cnpj) {
		this.banco.setCNPJ(cnpj);	
	}


	@Override
	public void setCodigoDeCompensacao(String codigoDeCompensacao) {
		
		this.banco.setCodigoDeCompensacao(codigoDeCompensacao);
	}


	@Override
	public void setImgLogo(Image imgLogo) {
		
		this.banco.setImgLogo(imgLogo);
	}


	@Override
	public void setInstituicao(String instituicao) {
		
		this.banco.setInstituicao(instituicao);
	}


	@Override
	public void setSegmento(String segmento) {
		
		this.banco.setSegmento(segmento);
	}

	/*
	 * <==
	 * Fim da implementção da interface IBanco.
	 * 
	 */
	
}
