package com.quadromedalhasolimpiadas.olimpics.exceptions;

import java.io.Serial;

public class PaisNotExistsException extends RuntimeException {

    /**
     * 
     */
    @Serial
    private static final long serialVersionUID = -4232811518365687012L;

	public PaisNotExistsException() {
		super("Não existe pais com esse código ou com esse nome!");
	}

	public PaisNotExistsException(String message) {
		super("Não existe pais com esse código/nome: " + message);
	}
}
