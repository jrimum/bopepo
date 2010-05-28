/**
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre.guia;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.IdentificacaoSeguimento;

/**
 * @author misael
 * 
 * @since 0.3
 * 
 * @version 0.3
 * 
 */
public class CampoLivreUtil  {

	public static Integer getTamanhoCorreto(IdentificacaoSeguimento identificacaoSeguimento) {
		Integer tamanhoCorreto = null;
		
		if (identificacaoSeguimento == IdentificacaoSeguimento.USO_EXCLUSIVO_BANCO)
			tamanhoCorreto = 25;
		else
			tamanhoCorreto = 21;
				
		return tamanhoCorreto;
	}
	
	
	public static boolean tamanhoEstaCorreto(CampoLivre campoLivre, IdentificacaoSeguimento identificacaoSeguimento) {
		return tamanhoEstaCorreto(campoLivre.write(), identificacaoSeguimento);
	}

	public static boolean tamanhoEstaCorreto(String campoLivreStr, IdentificacaoSeguimento identificacaoSeguimento) {
		return (  campoLivreStr.length() == getTamanhoCorreto(identificacaoSeguimento)  );
	}
	
	public static boolean existeEspacoEmBranco(CampoLivre campoLivre, IdentificacaoSeguimento identificacaoSeguimento) {
		return existeEspacoEmBranco(campoLivre.write(), identificacaoSeguimento);
	}

	public static boolean existeEspacoEmBranco(String campoLivreStr, IdentificacaoSeguimento identificacaoSeguimento) {
		return !(StringUtils.remove(campoLivreStr, ' ').length() == getTamanhoCorreto(identificacaoSeguimento));
	}

	public static boolean naoExisteEspacoEmBranco(CampoLivre campoLivre, IdentificacaoSeguimento identificacaoSeguimento) {
		return naoExisteEspacoEmBranco(campoLivre.write(), identificacaoSeguimento);
	}

	public static boolean naoExisteEspacoEmBranco(String campoLivreStr, IdentificacaoSeguimento identificacaoSeguimento) {
		return (StringUtils.remove(campoLivreStr, ' ').length() == getTamanhoCorreto(identificacaoSeguimento));
	}

}
