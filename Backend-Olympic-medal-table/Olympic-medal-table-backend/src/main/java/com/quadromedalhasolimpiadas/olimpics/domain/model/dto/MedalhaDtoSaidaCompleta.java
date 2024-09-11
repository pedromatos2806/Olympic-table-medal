package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Usuario;
import com.quadromedalhasolimpiadas.olimpics.domain.model.enumeration.TipoMedalha;


public record MedalhaDtoSaidaCompleta ( Long idMedalha,
							TipoMedalha tipoMedalha, 
							Long idEsporte, 
							String nomeEsporte, 
							Boolean ehEquipe, 
							Long idPais, 
							String nomePais, 
							String codigoPais,
							List<String> emailsCadastradosNoPais){

	
	public MedalhaDtoSaidaCompleta(Medalha medalha) {
		
		this(medalha.getId(),
			medalha.getTipoMedalha(),
			medalha.getEsporte().getId(),
			medalha.getEsporte().getNome(),
			medalha.getEsporte().getEhEquipe(),
			medalha.getPais().getId(),
			medalha.getPais().getNome(),
			medalha.getPais().getCodigo(),
			medalha.getPais().getUsuarios().stream().map(Usuario::getEmail).toList()
			);
	}
	
}
