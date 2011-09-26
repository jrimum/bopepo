package org.jrimum.bopepo.pdf;

import java.io.InputStream;

import org.jrimum.utilix.ClassLoaders;

/**
 * Classe para centralizar ponto de acesso aos resources usados nos testes, não instanciável.
 * 
 * <p>
 * Note que um mesmo resouce é usado e mais de uma classe de teste.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 */
public abstract class Resources {
	
	/**
	 * Mesmo título para ambos os arquivos.
	 */
	public static final String DOCUMENT_TITLE = "Arquivo para insumo em testes";

	/**
	 * Arquivo no classpath sem fields.
	 * 
	 * @return arquivo pronto para uso
	 */
	public static final InputStream crieInputStreamParaArquivoSemCampos(){
		return ClassLoaders.getResourceAsStream("ArquivoSemCampos.pdf");
	}

	/**
	 * Arquivo no classpath com 3 campos:
	 * 
	 * <ul>
	 * <li>nomeDoTestador:"JRiboy Brasileiro da Ordem do Progresso"</li>
	 * <li>funcaoDoTestador:"Developer"</li>
	 * <li>nomeDoTeste:"A definir..."</li>
	 * </u>
	 * 
	 * @return arquivo pronto para uso
	 */
	public static final InputStream crieInputStreamParaArquivoComCampos(){
		return ClassLoaders.getResourceAsStream("ArquivoComCampos.pdf");
	}
}
