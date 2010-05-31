/**
 * 
 */
package br.com.nordestefomento.jrimum.bopepo.campolivre.guia;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.domkee.financeiro.banco.febraban.guia.TipoSeguimento;

/**
 * @author misael
 * 
 * @since 0.3
 * 
 * @version 0.3
 * 
 */
public class CampoLivreUtil  {

	public static Integer getTamanhoCorreto(TipoSeguimento tipoSeguimento) {
		Integer tamanhoCorreto = null;
		
		if (tipoSeguimento == TipoSeguimento.USO_EXCLUSIVO_BANCO)
			tamanhoCorreto = 25;
		else
			tamanhoCorreto = 21;
				
		return tamanhoCorreto;
	}
	
	
	public static boolean tamanhoEstaCorreto(CampoLivre campoLivre, TipoSeguimento tipoSeguimento) {
		return tamanhoEstaCorreto(campoLivre.write(), tipoSeguimento);
	}

	public static boolean tamanhoEstaCorreto(String campoLivreStr, TipoSeguimento tipoSeguimento) {
		return (  campoLivreStr.length() == getTamanhoCorreto(tipoSeguimento)  );
	}
	
	public static boolean existeEspacoEmBranco(CampoLivre campoLivre, TipoSeguimento tipoSeguimento) {
		return existeEspacoEmBranco(campoLivre.write(), tipoSeguimento);
	}

	public static boolean existeEspacoEmBranco(String campoLivreStr, TipoSeguimento tipoSeguimento) {
		return !(StringUtils.remove(campoLivreStr, ' ').length() == getTamanhoCorreto(tipoSeguimento));
	}

	public static boolean naoExisteEspacoEmBranco(CampoLivre campoLivre, TipoSeguimento tipoSeguimento) {
		return naoExisteEspacoEmBranco(campoLivre.write(), tipoSeguimento);
	}

	public static boolean naoExisteEspacoEmBranco(String campoLivreStr, TipoSeguimento tipoSeguimento) {
		return (StringUtils.remove(campoLivreStr, ' ').length() == getTamanhoCorreto(tipoSeguimento));
	}
	
	public static void validar(CampoLivre campoLivre, TipoSeguimento tipoSeguimento) throws CampoLivreException {	
		
		int tamanhoAtual = campoLivre.write().length();
		int tamanhoEsperado = getTamanhoCorreto(tipoSeguimento);

		StringBuilder msgErro = new StringBuilder();
		
		if (  !tamanhoEstaCorreto(campoLivre, tipoSeguimento)  ) {
			if (tamanhoAtual > tamanhoAtual)
				msgErro.append("O tamanho da String [" + tamanhoAtual + "] é maior que o esperado [" + tamanhoEsperado + "]!");
			else
				msgErro.append("O tamanho da String [" + tamanhoAtual + "] é menor que o especificado [" + tamanhoEsperado + "]!");			
		}
		
		if (existeEspacoEmBranco(campoLivre, tipoSeguimento)) 
			msgErro.append("Um campo livre possui espaços em branco");
		
		if (  msgErro.length() > 0  )
			throw new CampoLivreException(msgErro.toString());
	}
	
}
