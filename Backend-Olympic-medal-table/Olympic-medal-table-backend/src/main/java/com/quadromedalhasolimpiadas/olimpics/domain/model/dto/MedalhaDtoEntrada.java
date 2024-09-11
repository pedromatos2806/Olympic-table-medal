package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import jakarta.validation.constraints.NotBlank;

public record MedalhaDtoEntrada (@NotBlank Long idEsporte,@NotBlank String codigoPais,@NotBlank String tipoMedalha) {
	
	
}
