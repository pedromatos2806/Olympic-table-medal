package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Esporte;

import jakarta.validation.constraints.NotBlank;

public record EsporteDto(Long id, @NotBlank(message = "{esporte.nome}") String nome, @NotBlank(message = "{esporte.equipe}") boolean ehEquipe) {

	
	public EsporteDto(Esporte esporte) {
		this(esporte.getId(),esporte.getNome(),esporte.getEhEquipe());
	}
	
}
