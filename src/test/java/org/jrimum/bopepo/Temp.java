package org.jrimum.bopepo;

import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

public class Temp {

	public static void main(String[] args) {
		
		String linha = "10499.00002 01400.239180 70443.355113 9 25230000093423";
		String linha2 = "10499.00127 00200.001287 70000.000128 1 10990000016000";
		
		System.out.println(BoletoUtil.getCampoLivreDaLinhaDigitavelFormatada(linha2));
	
	}
}
