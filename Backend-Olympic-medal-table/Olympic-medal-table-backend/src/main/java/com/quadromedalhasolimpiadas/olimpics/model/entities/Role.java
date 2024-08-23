package com.quadromedalhasolimpiadas.olimpics.model.entities;

import org.springframework.security.core.GrantedAuthority;

import com.quadromedalhasolimpiadas.olimpics.model.dto.RoleDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "roles")
@EqualsAndHashCode(of = "id")
public class Role  implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String role;
	
	public Role(RoleDto roleDto) {
		this.id = roleDto.id();
		this.role = roleDto.role();
	}
	
	@Override
	public String getAuthority() {
		return this.role;
	}
}
