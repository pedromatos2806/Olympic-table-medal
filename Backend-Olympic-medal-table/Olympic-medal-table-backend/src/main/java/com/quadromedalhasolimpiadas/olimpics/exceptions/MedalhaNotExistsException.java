package com.quadromedalhasolimpiadas.olimpics.exceptions;

public class MedalhaNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5325008164500016547L;

	public MedalhaNotExistsException() {
		super("A medalha está nula! ");
	}

	public MedalhaNotExistsException(String message) {
		super("Não existe medalha com esse id: " + message + " .");
	}

	public MedalhaNotExistsException(String tipo, String esporte) {
		super("Não há nenhuma medalha com esse tipo " + tipo + " nem esse esporte " + esporte + " .");
	}

}
