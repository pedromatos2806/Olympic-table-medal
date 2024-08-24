package com.services.emailservice.dtos;

import java.util.List;

public record MedalhaCommandSaida ( Long idMedalha,
		String tipoMedalha, 
		Long idEsporte, 
		String nomeEsporte, 
		Boolean ehEquipe, 
		Long idPais, 
		String nomePais, 
		String codigoPais,
		List<String> emailsCadastradosNoPais){

}