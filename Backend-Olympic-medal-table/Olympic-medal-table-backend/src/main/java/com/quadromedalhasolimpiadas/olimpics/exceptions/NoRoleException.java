package com.quadromedalhasolimpiadas.olimpics.exceptions;

public class NoRoleException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8063999876708088303L;

	public NoRoleException() {
		super("Não foi possível encontrar nenhuma ROLE nesse usuário!");
	}
}
