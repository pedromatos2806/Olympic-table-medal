package com.quadromedalhasolimpiadas.olimpics.model.command;

import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Pais;

public record PaisCommandSaidaComMedalhas (String nomePais,
										Long idPais, 
										String codigoPais, 
										int quantidadeMedalhasTotais, 
										int quantidadeMedalhasOuro,
										int quantidadeMedalhasPrata, 
										int quantidadeMedalhasBronze, 
										List<MedalhaCommandSaida> medalhas
										){

	
	public PaisCommandSaidaComMedalhas(Pais pais) {
		this(pais.getNome(),
				pais.getId(),
				pais.getCodigo(),
				pais.getTotalMedalhas(),
				pais.getMedalhasOuro(),
				pais.getMedalhasPrata(),
				pais.getMedalhasBronze(),
				pais.getMedalhas().stream().map(MedalhaCommandSaida::new).toList()
				);
	}
}
