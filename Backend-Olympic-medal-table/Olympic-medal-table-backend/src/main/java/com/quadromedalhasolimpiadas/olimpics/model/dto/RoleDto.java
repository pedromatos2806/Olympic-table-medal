package com.quadromedalhasolimpiadas.olimpics.model.dto;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Role;

public record RoleDto(Long id, String role) {
	
	public RoleDto(Role role) {
		this(role.getId(), role.getRole());
	}

}
