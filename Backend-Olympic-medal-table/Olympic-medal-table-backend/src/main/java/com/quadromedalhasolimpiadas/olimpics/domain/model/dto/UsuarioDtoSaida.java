package com.quadromedalhasolimpiadas.olimpics.model.command;

import java.util.ArrayList;
import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Role;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Usuario;

public record UsuarioCommand(Long idUsuario, String nomeUsuario, String email, List<PaisCommandSaida> paises, List<Role> roles) {
	
	
	public  UsuarioCommand(Usuario usuario) {
		this(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				new ArrayList<>(usuario.getPaises().stream().map(PaisCommandSaida::new).toList()),
				usuario.getRoles());
	}
}
