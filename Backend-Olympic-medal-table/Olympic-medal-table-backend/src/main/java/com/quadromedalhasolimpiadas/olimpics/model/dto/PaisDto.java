package com.quadromedalhasolimpiadas.olimpics.model.dto;

import java.util.List;
import java.util.Set;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Pais;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Usuario;

import jakarta.validation.constraints.NotBlank;

public record PaisDto(@NotBlank String nome, String codigo , Set<Medalha> medalhas, List<Usuario> usuarios, int quantidadeMedalhasTotais,  int quantidadeMedalhasOuro, int quantidadeMedalhasPrata, int quantidadeMedalhasBronze) {

	public PaisDto(Pais pais) {
		this(pais.getNome()
				, pais.getCodigo() 
				, pais.getMedalhas()
				, pais.getUsuarios()
				, pais.getTotalMedalhas()
				, pais.getMedalhasOuro()
				, pais.getMedalhasPrata()
				, pais.getMedalhasBronze()
				);
	}
}
