package com.quadromedalhasolimpiadas.olimpics.exceptions;

public class EsporteNotExistsException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6850157073726856494L;


	public EsporteNotExistsException(String esporte) {
		super("O esporte "+ esporte +" não está cadastrado no nosso sistema!");
	}

}
