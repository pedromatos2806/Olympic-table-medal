package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Pais;

public record PaisDtoSaidaComMedalhas (String nomePais,
										Long idPais, 
										String codigoPais, 
										int quantidadeMedalhasTotais, 
										int quantidadeMedalhasOuro,
										int quantidadeMedalhasPrata, 
										int quantidadeMedalhasBronze, 
										List<MedalhaDtoSaidaCompleta> medalhas
										){

	
	public PaisDtoSaidaComMedalhas(Pais pais) {
		this(pais.getNome(),
				pais.getId(),
				pais.getCodigo(),
				pais.getTotalMedalhas(),
				pais.getMedalhasOuro(),
				pais.getMedalhasPrata(),
				pais.getMedalhasBronze(),
				pais.getMedalhas().stream().map(MedalhaDtoSaidaCompleta::new).toList()
				);
	}
}
