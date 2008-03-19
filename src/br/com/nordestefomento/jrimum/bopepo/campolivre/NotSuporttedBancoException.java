package br.com.nordestefomento.jrimum.bopepo.campolivre;

public class NotSuporttedBancoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String msg = "Banco não suportado por não haver " +
								"implementações de Campo Livre para " +
								"o mesmo.";
	
	
	public NotSuporttedBancoException() {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unused")
	private NotSuporttedBancoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("unused")
	private NotSuporttedBancoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public NotSuporttedBancoException(Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
