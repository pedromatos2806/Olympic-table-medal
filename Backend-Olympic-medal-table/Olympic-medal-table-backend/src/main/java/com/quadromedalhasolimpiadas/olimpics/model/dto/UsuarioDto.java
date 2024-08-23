package com.quadromedalhasolimpiadas.olimpics.model.dto;

import java.util.List;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDto(	Long id,
		@NotBlank(message="O nome é obrigatório") String nome, 
		@NotBlank(message="O email é obrigatório")
		@Email String email,
		@NotNull String senha,
		List<RoleDto> roles) {

	
	public UsuarioDto(Usuario usuario) {
		this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), 
				usuario.getRoles().stream().map(RoleDto::new).toList());
	}
}
