package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;


import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Esporte;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.domain.model.enumeration.TipoMedalha;

import jakarta.validation.constraints.NotBlank;

public record MedalhaDto(Long id,@NotBlank(message = "{medalha.tipo}") TipoMedalha tipoMedalha,@NotBlank (message = "{medalha.esporte}")Esporte esporte) {
	
	public MedalhaDto(Medalha medalha) {
		this( medalha.getId(),medalha.getTipoMedalha(), medalha.getEsporte());
	}
}
