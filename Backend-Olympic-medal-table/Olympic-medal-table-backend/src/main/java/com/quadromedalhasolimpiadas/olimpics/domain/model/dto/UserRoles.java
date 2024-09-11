package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Role;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Usuario;

public record UserRoles(String nome, List<Role> roles) {

	
	public UserRoles(Usuario usuario) {
		this(usuario.getNome(), usuario.getRoles());
	}
}
