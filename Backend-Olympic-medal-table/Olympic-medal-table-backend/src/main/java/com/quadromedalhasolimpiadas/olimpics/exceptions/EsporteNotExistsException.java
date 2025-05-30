package com.quadromedalhasolimpiadas.olimpics.exceptions;

import java.io.Serial;

public class EsporteNotExistsException extends RuntimeException {

    /**
     * 
     */
    @Serial
    private static final long serialVersionUID = 6850157073726856494L;


	public EsporteNotExistsException(String esporte) {
		super("O esporte "+ esporte +" não está cadastrado no nosso sistema!");
	}

}
