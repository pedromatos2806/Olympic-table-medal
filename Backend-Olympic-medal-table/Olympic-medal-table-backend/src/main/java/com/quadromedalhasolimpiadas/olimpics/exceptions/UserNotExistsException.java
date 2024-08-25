package com.quadromedalhasolimpiadas.olimpics.exceptions;

public class UserNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4597908953042055463L;

	public UserNotExistsException() {
		super("Usuário não encontrado no nosso sistema! ");
	}

	public UserNotExistsException(String message) {
		super(message);
	}
}
