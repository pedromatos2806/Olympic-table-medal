package com.quadromedalhasolimpiadas.olimpics.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UserRoles;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UsuarioDto;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UsuarioDtoSaida;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UsuarioDtoSalvo;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Pais;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Usuario;
import com.quadromedalhasolimpiadas.olimpics.exceptions.NoRoleException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.PaisNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserAlreadyExistsException;
import com.quadromedalhasolimpiadas.olimpics.exceptions.UserNotExistsException;
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

	public Page<UsuarioDto> todosUsuarios(Pageable pag) {
		return usuarioRepository.findAll(pag).map(UsuarioDto::new);
	}

	public UsuarioDtoSalvo salvar(UsuarioDto usuarioDto) {
		if (usuarioDto.roles().isEmpty())
			throw new NoRoleException();

		Usuario usuarioBanco = (Usuario) usuarioRepository.findByEmail(usuarioDto.email());

		if (usuarioBanco != null)
			throw new UserAlreadyExistsException("Já existe um usuário cadastrado no nosso sistema com esse email! Por favor, tente novamente com outro email!");

		Usuario usuario = new Usuario(usuarioDto);
		String senha = usuario.getSenha();
		usuario.setSenha(passwordEncoder.encode(senha));
		usuarioRepository.save(usuario);
		return new UsuarioDtoSalvo(usuario);
	}

	public UserRoles pegarRolesUsuario(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new UserNotExistsException());

		return new UserRoles(usuario);
	}

	public UsuarioDtoSaida cadastrarUsuarioEmUmPais(Long idUsuario, String codigoPais) {

		Usuario usuarioBanco = usuarioRepository.findById(idUsuario).orElseThrow(() -> new UserNotExistsException());
		Pais paisBanco = paisRepository.findByCodigo(codigoPais).orElseThrow(() -> new PaisNotExistsException());

		usuarioBanco.getPaises().add(paisBanco);

		Usuario novoUsuario = usuarioRepository.save(usuarioBanco);

		if (novoUsuario != null)
			return new UsuarioDtoSaida(novoUsuario);

		return null;
	}

	public Boolean deletar(Long id) {
		var usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			usuarioRepository.delete(usuario.get());
			return true;
		}
		return false;
	}
}
