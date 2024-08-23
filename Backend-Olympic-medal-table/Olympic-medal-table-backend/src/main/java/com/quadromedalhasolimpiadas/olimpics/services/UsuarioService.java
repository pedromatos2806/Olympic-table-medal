package com.quadromedalhasolimpiadas.olimpics.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.model.command.UsuarioCommand;
import com.quadromedalhasolimpiadas.olimpics.model.dto.UsuarioDto;
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
	
	public UsuarioDto salvar(UsuarioDto usuarioDto) throws Exception {
		Usuario usuarioBanco = (Usuario) usuarioRepository.findByEmail(usuarioDto.email());
		
		if(usuarioDto.roles().isEmpty())
			throw new ObjectNotFoundException(usuarioDto, "Não foi enviado nenhuma Role para o cadastro de usuário!");
		
		if(usuarioBanco != null)
			throw new ObjectNotFoundException(usuarioBanco, "O usuario com o email : " + usuarioDto.email() + " já está cadastrado no banco!  ");
		
		Usuario usuario = new Usuario(usuarioDto);
		String senha = usuario.getSenha();
		usuario.setSenha(passwordEncoder.encode(senha));
		usuarioRepository.save(usuario);
		return new UsuarioDto(usuario);
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
		
		Usuario usuarioBanco = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ObjectNotFoundException(new Object(), "Não existe Usuario com esse Identificador: " + idUsuario));
		Pais paisBanco = paisRepository.findByCodigo(codigoPais).orElseThrow(() -> new ObjectNotFoundException(new Object(), "Não existe pais com esse código: "+ codigoPais));
		
		usuarioBanco.getPaises().add(paisBanco);
		
		
		
		Usuario novoUsuario = usuarioRepository.save(usuarioBanco);
		
		if(novoUsuario != null)
			return ResponseEntity.ok(new UsuarioCommand(novoUsuario));
		
		return ResponseEntity.notFound().build();
	}
	
}
