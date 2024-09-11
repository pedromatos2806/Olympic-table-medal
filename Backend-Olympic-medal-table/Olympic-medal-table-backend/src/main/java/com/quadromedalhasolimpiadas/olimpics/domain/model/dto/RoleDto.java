package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Role;

public record RoleDto(Long id, String role) {
	
	public RoleDto(Role role) {
		this(role.getId(), role.getRole());
	}

}
