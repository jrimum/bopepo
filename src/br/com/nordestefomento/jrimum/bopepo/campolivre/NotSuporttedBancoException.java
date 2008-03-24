package br.com.nordestefomento.jrimum.bopepo.campolivre;

import br.com.nordestefomento.jrimum.JRimumException;

public class NotSuporttedBancoException extends JRimumException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String msg = "Banco não suportado por não haver " +
								"implementações de Campo Livre para " +
								"o mesmo.";
	
	
	public NotSuporttedBancoException() {
		super(msg);
	}
	
	@SuppressWarnings("unused")
	private NotSuporttedBancoException(String message, Throwable cause) {
		super(message, cause);
	}
	@SuppressWarnings("unused")
	private NotSuporttedBancoException(String message) {
		super(message);
	}
	
	public NotSuporttedBancoException(Throwable cause) {
		super(msg, cause);
	}
	
}
