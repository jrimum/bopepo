package org.jrimum.bopepo.exemplo.banco.bb;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.exemplo.Exemplos;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;

/**
 * <p>
 * Exemplo do boleto para o Banco do Brasil com Nosso Número de 17 dígitos e Convênio de 7 posições.
 * </p>
 * <p>
 * Mostra um exemplo funcional que gera um boleto para a implementação de campo livre
 * do Banco do Brasil com Nosso Número 17/Convênio 7;
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 * @version 0.2
 */
public class BoletoBBNossoNumero17Convenio7Exemplo {


public static void main(String[] args) {
		
		Titulo titulo = Exemplos.crieTitulo();
		
		/*
		 * Campos específicos para o Banco do Brasil com Nosso Número 17 / Convênio 6.
		 */
		
		ContaBancaria contaBancaria = titulo.getContaBancaria();

		/*
		 * Banco do Brasil 001
		 */
		contaBancaria.setBanco(BancosSuportados.BANCO_DO_BRASIL.create());
		
		/*
		 * Conta/Convênio de 7 posições ou seja, acima de 1.000.000
		 */
		contaBancaria.setNumeroDaConta(new NumeroDaConta(1234567));
		
		/*
		 * Nosso Número de 17 posições
		 */
		titulo.setNossoNumero("12345678901234567");
		
		/*
		 * Carteira com no máximo 2 dígitos
		 */
		contaBancaria.setCarteira(new Carteira(23));
		
		Boleto boleto = Exemplos.crieBoleto(titulo);
		
		Exemplos.execute(boleto);
	}
}
