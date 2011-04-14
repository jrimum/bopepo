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

		if (tipoSeguimento == TipoSeguimento.CARNES_E_ASSEMELHADOS_OU_DEMAIS)
			tamanhoCorreto = 21;
		else
			tamanhoCorreto = 25;
				
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
		int tamanhoAtual = campoLivreStr.length();
		return !(StringUtils.remove(campoLivreStr, ' ').length() == tamanhoAtual);
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
			if (tamanhoAtual > tamanhoEsperado)
				msgErro.append("O tamanho do campo livre gerado [" + tamanhoAtual + "] é maior que o esperado [" + tamanhoEsperado + "] para o segmento \"" + tipoSeguimento.getCodigo() + "-" + tipoSeguimento.getDescricao() + "\".");
			else
				msgErro.append("O tamanho do campo livre gerado [" + tamanhoAtual + "] é menor que o esperado [" + tamanhoEsperado + "] para o segmento \"" + tipoSeguimento.getCodigo() + "-" + tipoSeguimento.getDescricao() + "\".");			
		}
		
		if (existeEspacoEmBranco(campoLivre, tipoSeguimento)) 
			msgErro.append("O campo livre possui espaços em branco, e isto não pode ocorrer.");
		
		if (  msgErro.length() > 0  )
			throw new CampoLivreException(msgErro.toString());
	}
	
}
