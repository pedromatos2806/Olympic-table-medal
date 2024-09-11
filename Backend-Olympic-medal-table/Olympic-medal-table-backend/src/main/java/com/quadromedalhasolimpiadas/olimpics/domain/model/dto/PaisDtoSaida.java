package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Pais;


public record PaisDtoSaida(String nomePais, 
		Long idPais, 
		String codigoPais, 
		int quantidadeMedalhasTotais, 
		int quantidadeMedalhasOuro, 
		int quantidadeMedalhasPrata, 
		int quantidadeMedalhasBronze) {

	
	public PaisDtoSaida(Pais pais) {
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
