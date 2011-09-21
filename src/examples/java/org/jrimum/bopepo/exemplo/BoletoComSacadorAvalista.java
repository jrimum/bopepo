package org.jrimum.bopepo.exemplo;

import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * Exemplo de código para geração de um boleto simples com Sacador Avalista.
 * 
 * <p>
 * Baseado no {@linkplain org.jrimum.bopepo.exemplo.MeuPrimeiroBoleto}.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class BoletoComSacadorAvalista extends MeuPrimeiroBoleto{
	
	/**
	 * Executa o exemplo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		new BoletoComSacadorAvalista().exemplo();
	}

	/**
	 * Cria um boleto com sacador avalista, em passos distintos, com os dados necessários para a visualização.
	 * 
	 * @return boleto com dados
	 */
	@Override
	Boleto crieUmBoleto() {
		
		/*
		 * PASSO 1: Faça o mesmo que se fez em MeuPrimeiroBoleto.
		 */		
		ContaBancaria contaBancaria = crieUmaContaBancaria();
		Cedente cedente = crieUmCedente(); 
		Sacado sacado = crieUmSacado();
		
		/*
		 * PASSO 2: Informe os dados do Sacador Avalista.
		 */
		SacadorAvalista sacadorAvalista = crieUmSacadorAvalista();
		
		/*
		 * PASSO 3: Crie um novo título/cobrança e informe os dados.
		 */
		Titulo titulo = crieOsDadosDoNovoTitulo(new Titulo(contaBancaria,sacado,cedente,sacadorAvalista));

		/*
		 * PASSO 4: Crie o boleto e informe os dados necessários do mesmo jeito.
		 */
		Boleto boleto = crieOsDadosDoNovoBoleto(new Boleto(titulo));
		
		return boleto;
	}

	/**
	 * Cria uma instância de sacador avalista com os principais dados para o boleto.
	 * 
	 * @return sacadorAvalista com os dados necssários
	 */
	private final SacadorAvalista crieUmSacadorAvalista() {
		
		SacadorAvalista sacadorAvalista = new SacadorAvalista("JRimum Enterprise", "00.000.000/0001-91");

		// Informando o endereço do sacador avalista.
		Endereco enderecoSacAval = new Endereco();
		enderecoSacAval.setUF(UnidadeFederativa.DF);
		enderecoSacAval.setLocalidade("Brasília");
		enderecoSacAval.setCep(new CEP("70150-903"));
		enderecoSacAval.setBairro("Grande Centro");
		enderecoSacAval.setLogradouro("Rua Eternamente Principal");
		enderecoSacAval.setNumero("001");
		sacadorAvalista.addEndereco(enderecoSacAval);
		
		return sacadorAvalista;
	}
}
