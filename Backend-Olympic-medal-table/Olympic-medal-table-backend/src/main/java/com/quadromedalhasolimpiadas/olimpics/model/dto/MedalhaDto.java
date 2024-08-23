package com.quadromedalhasolimpiadas.olimpics.model.dto;


import com.quadromedalhasolimpiadas.olimpics.model.entities.Esporte;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.model.enumeration.TipoMedalha;

import jakarta.validation.constraints.NotBlank;

public record MedalhaDto(Long id,@NotBlank TipoMedalha tipoMedalha,@NotBlank Esporte esporte) {
	
	public MedalhaDto(Medalha medalha) {
		this( medalha.getId(),medalha.getTipoMedalha(), medalha.getEsporte());
	}
}
