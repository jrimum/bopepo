package org.jrimum.bopepo.exemplo;

public class ItemFaturaBoletoSICREDI {

	String produto;
	String data;
	String servico;
	String quantidade;
	String valor;
	
	
	/**
	 * @param produto
	 * @param data
	 * @param servico
	 * @param quantidade
	 * @param valor
	 */
	ItemFaturaBoletoSICREDI(String produto, String data,
			String servico, String quantidade, String valor) {
		super();
		this.produto = produto;
		this.data = data;
		this.servico = servico;
		this.quantidade = quantidade;
		this.valor = valor;
	}
}
