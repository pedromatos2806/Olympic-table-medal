package com.quadromedalhasolimpiadas.olimpics.model.command;

import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Role;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Usuario;

public record UserRoles(String nome, List<Role> roles) {

	
	public UserRoles(Usuario usuario) {
		this(usuario.getNome(), usuario.getRoles());
	}
}
