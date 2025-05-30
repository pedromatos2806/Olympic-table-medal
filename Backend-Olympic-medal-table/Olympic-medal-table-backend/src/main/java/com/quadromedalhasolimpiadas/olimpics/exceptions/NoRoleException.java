package com.quadromedalhasolimpiadas.olimpics.exceptions;

import java.io.Serial;

public class NoRoleException extends RuntimeException{

    /**
     * 
     */
    @Serial
    private static final long serialVersionUID = 8063999876708088303L;

	public NoRoleException() {
		super("Não foi possível encontrar nenhuma ROLE nesse usuário!");
	}
}
