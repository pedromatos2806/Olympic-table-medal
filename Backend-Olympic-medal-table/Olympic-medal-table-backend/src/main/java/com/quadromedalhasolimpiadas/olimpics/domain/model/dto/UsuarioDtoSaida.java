package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Role;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Usuario;

public record UsuarioDtoSaida(Long idUsuario, String nomeUsuario, String email, List<PaisDtoSaida> paises, List<Role> roles) {
	
	
	public  UsuarioDtoSaida(Usuario usuario) {
		this(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				new ArrayList<>(usuario.getPaises().stream().map(PaisDtoSaida::new).toList()),
				usuario.getRoles());
	}
}
