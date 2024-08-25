package com.quadromedalhasolimpiadas.olimpics.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5074765554548812811L;

	public UserAlreadyExistsException () {
		super("Já existe um usuário com esse email cadastrado no nosso sistema!");
	}
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
