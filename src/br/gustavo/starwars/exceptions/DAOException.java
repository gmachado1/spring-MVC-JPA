package br.gustavo.starwars.exceptions;

public class DAOException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3458273657401350133L;
	
	private int code;

	public DAOException(String msg, int code) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
