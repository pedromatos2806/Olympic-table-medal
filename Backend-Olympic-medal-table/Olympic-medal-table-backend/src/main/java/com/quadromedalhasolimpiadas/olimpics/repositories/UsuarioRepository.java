package com.quadromedalhasolimpiadas.olimpics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public UserDetails findByEmail(String email);
}
