package com.quadromedalhasolimpiadas.olimpics.model.command;

import jakarta.validation.constraints.NotBlank;

public record MedalhaCommandEntrada (@NotBlank Long idEsporte,@NotBlank String codigoPais,@NotBlank String tipoMedalha) {
	
	
}
