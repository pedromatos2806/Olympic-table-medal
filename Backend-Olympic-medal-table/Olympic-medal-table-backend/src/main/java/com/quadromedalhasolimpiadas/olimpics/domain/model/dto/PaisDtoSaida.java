package com.quadromedalhasolimpiadas.olimpics.model.command;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Pais;


public record PaisCommandSaida(String nomePais, 
		Long idPais, 
		String codigoPais, 
		int quantidadeMedalhasTotais, 
		int quantidadeMedalhasOuro, 
		int quantidadeMedalhasPrata, 
		int quantidadeMedalhasBronze) {

	
	public PaisCommandSaida(Pais pais) {
		this(pais.getNome(),
			pais.getId(),
			pais.getCodigo(),
			pais.getTotalMedalhas(),
			pais.getMedalhasOuro(),
			pais.getMedalhasPrata(),
			pais.getMedalhasBronze()
				);
	}
}
