package com.quadromedalhasolimpiadas.olimpics.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.repositories.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    //System.out.println("****** AUTENTICACAO SERVICE ******");
		var usuario = repository.findByEmail(username);
	    if (usuario == null) {
	        throw new UsernameNotFoundException("Usuário não encontrado! ");
	    }
	    return usuario;
	}

	
}
