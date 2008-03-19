package br.com.nordestefomento.jrimum.bopepo;

import br.com.nordestefomento.jrimum.domkee.type.EnumTitulo;

import junit.framework.TestCase;

public class TestEnumTitulo extends TestCase {

	EnumTitulo CH = EnumTitulo.CH_CHEQUE;

	EnumTitulo DM = EnumTitulo.DM_DUPLICATA_MERCANTIL;

	EnumTitulo DMI = EnumTitulo.DMI_DUPLICATA_MERCANTIL_PARA_INDICACAO;

	EnumTitulo DS = EnumTitulo.DS_DUPLICATA_DE_SERVICO;

	EnumTitulo DSI = EnumTitulo.DSI_DUPLICATA_DE_SERVICO_PARA_INDICACAO;

	EnumTitulo DR = EnumTitulo.DR_DUPLICATA_RURAL;

	EnumTitulo LC = EnumTitulo.LC_LETRA_DE_CAMBIO;

	EnumTitulo NCC = EnumTitulo.NCC_NOTA_DE_CREDITO_COMERCIAL;

	EnumTitulo NCE = EnumTitulo.NCE_NOTA_DE_CREDITO_A_EXPORTACAO;

	EnumTitulo NCI = EnumTitulo.NCI_NOTA_DE_CREDITO_INDUSTRIAL;

	EnumTitulo NCR = EnumTitulo.NCR_NOTA_DE_CREDITO_RURAL;

	EnumTitulo NP = EnumTitulo.NP_NOTA_PROMISSORIA;

	EnumTitulo NPR = EnumTitulo.NPR_NOTA_PROMISSORIA_RURAL;

	EnumTitulo TM = EnumTitulo.TM_TRIPLICATA_MERCANTIL;

	EnumTitulo TS = EnumTitulo.TS_TRIPLICATA_DE_SERVICO;

	EnumTitulo NS = EnumTitulo.NS_NOTA_DE_SEGURO;

	EnumTitulo RC = EnumTitulo.RC_RECIBO;

	EnumTitulo FAT = EnumTitulo.FAT_FATURA;

	EnumTitulo ND = EnumTitulo.ND_NOTA_DE_DEBITO;

	EnumTitulo AP = EnumTitulo.AP_APOLICE_DE_SEGURO;

	EnumTitulo ME = EnumTitulo.ME_MENSALIDADE_ESCOLAR;

	EnumTitulo PC = EnumTitulo.PC_PARCELA_DE_CONSORCIO;

	EnumTitulo NF = EnumTitulo.NF_NOTA_FISCAL;

	EnumTitulo DD = EnumTitulo.DD_DOCUMENTO_DE_DIVIDA;

	EnumTitulo cedulaDeProdutoRural = EnumTitulo.CEDULA_DE_PRODUTO_RURAL;

	EnumTitulo warrant = EnumTitulo.WARRANT;

	EnumTitulo dividaAtivaDeEstado = EnumTitulo.DIVIDA_ATIVA_DE_ESTADO;

	EnumTitulo dividaAtivaDeMunicipio = EnumTitulo.DIVIDA_ATIVA_DE_MUNICIPIO;

	EnumTitulo dividaAtivaDaUniao = EnumTitulo.DIVIDA_ATIVA_DA_UNIAO;

	EnumTitulo cotaCondominial = EnumTitulo.COTA_CONDOMINIAL;

	EnumTitulo outros = EnumTitulo.OUTROS;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		CH = EnumTitulo.CH_CHEQUE;
		DM = EnumTitulo.DM_DUPLICATA_MERCANTIL;
		DMI = EnumTitulo.DMI_DUPLICATA_MERCANTIL_PARA_INDICACAO;
		DS = EnumTitulo.DS_DUPLICATA_DE_SERVICO;
		DSI = EnumTitulo.DSI_DUPLICATA_DE_SERVICO_PARA_INDICACAO;
		DR = EnumTitulo.DR_DUPLICATA_RURAL;
		LC = EnumTitulo.LC_LETRA_DE_CAMBIO;
		NCC = EnumTitulo.NCC_NOTA_DE_CREDITO_COMERCIAL;
		NCE = EnumTitulo.NCE_NOTA_DE_CREDITO_A_EXPORTACAO;
		NCI = EnumTitulo.NCI_NOTA_DE_CREDITO_INDUSTRIAL;
		NCR = EnumTitulo.NCR_NOTA_DE_CREDITO_RURAL;
		NP = EnumTitulo.NP_NOTA_PROMISSORIA;
		NPR = EnumTitulo.NPR_NOTA_PROMISSORIA_RURAL;
		TM = EnumTitulo.TM_TRIPLICATA_MERCANTIL;
		TS = EnumTitulo.TS_TRIPLICATA_DE_SERVICO;
		NS = EnumTitulo.NS_NOTA_DE_SEGURO;
		RC = EnumTitulo.RC_RECIBO;
		FAT = EnumTitulo.FAT_FATURA;
		ND = EnumTitulo.ND_NOTA_DE_DEBITO;
		AP = EnumTitulo.AP_APOLICE_DE_SEGURO;
		ME = EnumTitulo.ME_MENSALIDADE_ESCOLAR;
		PC = EnumTitulo.PC_PARCELA_DE_CONSORCIO;
		NF = EnumTitulo.NF_NOTA_FISCAL;
		DD = EnumTitulo.DD_DOCUMENTO_DE_DIVIDA;
		cedulaDeProdutoRural = EnumTitulo.CEDULA_DE_PRODUTO_RURAL;
		warrant = EnumTitulo.WARRANT;
		dividaAtivaDeEstado = EnumTitulo.DIVIDA_ATIVA_DE_ESTADO;
		dividaAtivaDeMunicipio = EnumTitulo.DIVIDA_ATIVA_DE_MUNICIPIO;
		dividaAtivaDaUniao = EnumTitulo.DIVIDA_ATIVA_DA_UNIAO;
		cotaCondominial = EnumTitulo.COTA_CONDOMINIAL;
		outros = EnumTitulo.OUTROS;

	}

	public void testGetSigla() {

		assertEquals("CH", CH.getSigla());
		assertEquals("DM", DM.getSigla());
		assertEquals("DMI", DMI.getSigla());
		assertEquals("DS", DS.getSigla());
		assertEquals("DSI", DSI.getSigla());
		assertEquals("DR", DR.getSigla());
		assertEquals("LC", LC.getSigla());
		assertEquals("NCC", NCC.getSigla());
		assertEquals("NCE", NCE.getSigla());
		assertEquals("NCI", NCI.getSigla());
		assertEquals("NCR", NCR.getSigla());
		assertEquals("NP", NP.getSigla());
		assertEquals("NPR", NPR.getSigla());
		assertEquals("TM", TM.getSigla());
		assertEquals("TS", TS.getSigla());
		assertEquals("NS", NS.getSigla());
		assertEquals("RC", RC.getSigla());
		assertEquals("FAT", FAT.getSigla());
		assertEquals("ND", ND.getSigla());
		assertEquals("AP", AP.getSigla());
		assertEquals("ME", ME.getSigla());
		assertEquals("PC", PC.getSigla());
		assertEquals("NF", NF.getSigla());
		assertEquals("DD", DD.getSigla());
		assertEquals("Cédula de Produto Rural", cedulaDeProdutoRural.getSigla());
		assertEquals("Warrant", warrant.getSigla());
		assertEquals("Dívida Ativa de Estado", dividaAtivaDeEstado.getSigla());
		assertEquals("Dívida Ativa de Município", dividaAtivaDeMunicipio
				.getSigla());
		assertEquals("Dívida Ativa da União", dividaAtivaDaUniao.getSigla());
		assertEquals("Cota Condominial", cotaCondominial.getSigla());
		assertEquals("Outros", outros.getSigla());

	}

	public void testGetCodigo() {

		assertTrue(CH.getCodigo() == 1);
		assertTrue(DM.getCodigo() == 2);
		assertTrue(DMI.getCodigo() == 3);
		assertTrue(DS.getCodigo() == 4);
		assertTrue(DSI.getCodigo() == 5);
		assertTrue(DR.getCodigo() == 6);
		assertTrue(LC.getCodigo() == 7);
		assertTrue(NCC.getCodigo() == 8);
		assertTrue(NCE.getCodigo() == 9);
		assertTrue(NCI.getCodigo() == 10);
		assertTrue(NCR.getCodigo() == 11);
		assertTrue(NP.getCodigo() == 12);
		assertTrue(NPR.getCodigo() == 13);
		assertTrue(TM.getCodigo() == 14);
		assertTrue(TS.getCodigo() == 15);
		assertTrue(NS.getCodigo() == 16);
		assertTrue(RC.getCodigo() == 17);
		assertTrue(FAT.getCodigo() == 18);
		assertTrue(ND.getCodigo() == 19);
		assertTrue(AP.getCodigo() == 20);
		assertTrue(ME.getCodigo() == 21);
		assertTrue(PC.getCodigo() == 22);
		assertTrue(NF.getCodigo() == 23);
		assertTrue(DD.getCodigo() == 24);
		assertTrue(cedulaDeProdutoRural.getCodigo() == 25);
		assertTrue(warrant.getCodigo() == 26);
		assertTrue(dividaAtivaDeEstado.getCodigo() == 27);
		assertTrue(dividaAtivaDeMunicipio.getCodigo() == 28);
		assertTrue(dividaAtivaDaUniao.getCodigo() == 29);
		assertTrue(cotaCondominial.getCodigo() == 30);
		assertTrue(outros.getCodigo() == 99);

	}

}
