package com.quadromedalhasolimpiadas.olimpics.model.command;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.model.enumeration.TipoMedalha;


public record MedalhaCommandSaida ( Long idMedalha,
							TipoMedalha tipoMedalha, 
							Long idEsporte, 
							String nomeEsporte, 
							Boolean ehEquipe, 
							Long idPais, 
							String nomePais, 
							String codigoPais){

	
	public MedalhaCommandSaida(Medalha medalha) {
		
		this(medalha.getId(),
			medalha.getTipoMedalha(),
			medalha.getEsporte().getId(),
			medalha.getEsporte().getNome(),
			medalha.getEsporte().getEhEquipe(),
			medalha.getPais().getId(),
			medalha.getPais().getNome(),
			medalha.getPais().getCodigo()
			);
	}
	
}
