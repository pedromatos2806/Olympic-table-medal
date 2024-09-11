package com.quadromedalhasolimpiadas.olimpics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.exceptions.NoRoleException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.PaisNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserAlreadyExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.model.command.UserRoles;
import com.quadromedalhasolimpiadas.olimpics.model.command.UsuarioCommand;
import com.quadromedalhasolimpiadas.olimpics.model.dto.UsuarioDto;
import com.quadromedalhasolimpiadas.olimpics.model.dto.UsuarioDtoSalvo;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Pais;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Usuario;
import com.quadromedalhasolimpiadas.olimpics.repositories.PaisRepository;
import com.quadromedalhasolimpiadas.olimpics.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	 @Autowired
	 PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 PaisRepository paisRepository;
	
	public Page<UsuarioDto> todosUsuarios(Pageable pag){
		return usuarioRepository.findAll(pag).map(UsuarioDto::new);
	}
	
	public UsuarioDtoSalvo salvar(UsuarioDto usuarioDto){
		Usuario usuarioBanco = (Usuario) usuarioRepository.findByEmail(usuarioDto.email());
		
		if(usuarioDto.roles().isEmpty())
			throw new NoRoleException();
		
		if(usuarioBanco != null)
			throw new UserAlreadyExistsException("Já existe um usuário cadastrado no nosso sistema com esse email! Por favor, tente novamente com outro email!");
		
		Usuario usuario = new Usuario(usuarioDto);
		String senha = usuario.getSenha();
		usuario.setSenha(passwordEncoder.encode(senha));
		usuarioRepository.save(usuario);
		return new UsuarioDtoSalvo(usuario);
	}
	
	public ResponseEntity<UserRoles> pegarRolesUsuario(Long idUsuario){
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow( () -> new UserNotExistsException());
		
		return ResponseEntity.ok(new UserRoles(usuario)) ;
	}
	
	public Boolean deletar(Long id) {
		var usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			usuarioRepository.delete(usuario.get());
			return true;
		}
		return false;
	}
	
public ResponseEntity<UsuarioCommand> cadastrarUsuarioEmUmPais(Long idUsuario, String codigoPais){
		
		Usuario usuarioBanco = usuarioRepository.findById(idUsuario).orElseThrow(() -> new UserNotExistsException());
		Pais paisBanco = paisRepository.findByCodigo(codigoPais).orElseThrow(() -> new PaisNotExistsException());
		
		usuarioBanco.getPaises().add(paisBanco);
		
		
		
		Usuario novoUsuario = usuarioRepository.save(usuarioBanco);
		
		if(novoUsuario != null)
			return ResponseEntity.ok(new UsuarioCommand(novoUsuario));
		
		return ResponseEntity.notFound().build();
	}
	
}
