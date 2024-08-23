package com.quadromedalhasolimpiadas.olimpics.model.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.quadromedalhasolimpiadas.olimpics.model.dto.UsuarioDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity(name = "usuarios")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = -8916320181306985134L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "USUARIO_ID")
	private Long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "EMAIL")
	private String email;

	@ToString.Exclude
	@Column(name = "SENHA")
	private String senha;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USUARIOS_PAISES", joinColumns = @JoinColumn(name = "USUARIO_ID"), inverseJoinColumns = @JoinColumn(referencedColumnName = "PAIS_ID", name = "PAIS_ID"))
	private Set<Pais> paises;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuarios_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private List<Role> roles = new ArrayList();

	public Usuario(UsuarioDto usuarioDto) {
		this.nome = usuarioDto.nome();
		this.email = usuarioDto.email();
		this.senha = usuarioDto.senha();
		this.roles = usuarioDto.roles().stream().map(Role::new).toList();
	}

	public int quantidadePaises() {
		return this.paises.size();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
