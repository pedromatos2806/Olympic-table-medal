package com.quadromedalhasolimpiadas.olimpics.model.dto;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Esporte;

import jakarta.validation.constraints.NotBlank;

public record EsporteDto(Long id,@NotBlank(message = "nome do esporte é obrigatório") String nome, @NotBlank boolean ehEquipe) {

	
	public EsporteDto(Esporte esporte) {
		this(esporte.getId(),esporte.getNome(),esporte.getEhEquipe());
	}
	
}
