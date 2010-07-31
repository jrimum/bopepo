package org.jrimum.bopepo;

import org.jrimum.vallia.digitoverificador.Modulo;
import org.jrimum.vallia.digitoverificador.TipoDeModulo;

public class Temp {

	public static void main(String[] args) {
		
		String linha = "10499.00002 01400.239180 70443.355113 9 25230000093423";
		String linha2 = "10499.00127 00200.001287 70000.000128 1 10990000016000";
		String linha3 = "10491.00009 02890.100039 00000.000174 9 17140000012350";
		String linha4 = "10490.16320 44000.200046 00000.502021 3 22550000015000";
		
		System.out.println(BoletoUtil.getCampoLivreDaLinhaDigitavelFormatada(linha4));
	
	}
}
