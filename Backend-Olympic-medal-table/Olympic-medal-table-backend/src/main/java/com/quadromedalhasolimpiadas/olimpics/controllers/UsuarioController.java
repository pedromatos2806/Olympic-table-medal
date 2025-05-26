package com.quadromedalhasolimpiadas.olimpics.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UserRoles;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UsuarioDto;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UsuarioDtoSaida;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UsuarioDtoSalvo;
import com.quadromedalhasolimpiadas.olimpics.domain.services.JWTokenService;
import com.quadromedalhasolimpiadas.olimpics.domain.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

	
	private static UsuarioService usuarioService;

	private static JWTokenService tokenService;

	private static final String ERROS_SALVAR_USUARIO_BAD_REQUEST = """
			Não foi possível encontrar nenhuma ROLE nesse usuário!<br>
			""";
	
	private static final String ERROS_SALVAR_USUARIO_UNPROSSABLE_ENTITY = """
			Já existe um usuário cadastrado no nosso sistema com esse email! Por favor, tente novamente com outro email!<br>
			""";

	private static final String ERROS_PEGAR_ROLES_DO_USUARIO = """
			Usuário não encontrado no nosso sistema! <br>
			""";

	private static final String ERROS_CADASTRAR_USUARIO_EM_UM_PAIS = """
			Usuário não encontrado no nosso sistema! <br>
			Não existe pais com esse código ou com esse nome!<br>
			""";
	
	
	@Operation(summary = "Busca todos usuarios da aplicação")
	@ApiResponse(description = "retorna um Page com todos usuarios cadastrados na aplicação")
	@GetMapping
 	public Page<UsuarioDto> todosUsuarios(Pageable pag) {
		return usuarioService.todosUsuarios(pag);
	}

	@Operation(summary = "Faz o cadastro de usuário na aplicação", responses = {
			@ApiResponse(responseCode = "201", description = "retorna um ResponseEntity de usuarioDto com a senha encryptada", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = ERROS_SALVAR_USUARIO_BAD_REQUEST),
			@ApiResponse(responseCode = "422", description = ERROS_SALVAR_USUARIO_UNPROSSABLE_ENTITY) })
	@PostMapping
	public ResponseEntity<UsuarioDtoSalvo> salvar(@RequestBody @Valid UsuarioDto usuarioDto) {
		return new ResponseEntity<>(usuarioService.salvar(usuarioDto), HttpStatus.CREATED);
	}

	@Operation(summary = "Busca as roles do usuario", responses = {
			@ApiResponse(responseCode = "200", description = "Retorna um ResponseEntity de UsuarioCommand com uma Lista de Roles do usuario"),
			@ApiResponse(responseCode = "400", description = ERROS_PEGAR_ROLES_DO_USUARIO) })
	@PostMapping(value = "/roles")
	public ResponseEntity<UserRoles> pegarRolesDoUsuario(@RequestHeader("Authorization") String authorizationHeader) {
		String token = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
		}

		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		var userId = tokenService.getClaim(token, "id").longValue();
		Long idUsuario;
		try {
			idUsuario = userId;
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return new ResponseEntity<>(usuarioService.pegarRolesUsuario(idUsuario), HttpStatus.OK);
	}

	@Operation(summary = "Cadastra um usuário em um país", responses = {
			@ApiResponse(responseCode = "201", description = "Retorna um ResponseEntity de um país com uma lista de usuários dentro" , content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = ERROS_CADASTRAR_USUARIO_EM_UM_PAIS) })
	@PostMapping(value = "/codigoPais/{codigo}")
	public ResponseEntity<UsuarioDtoSaida> cadastrarUsuarioEmUmPais(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String codigo) {

		String token = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
		}

		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		var userId = tokenService.getClaim(token, "id").longValue();
		Long idUsuario;
		try {
			idUsuario = userId;
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		UsuarioDtoSaida user = usuarioService.cadastrarUsuarioEmUmPais(idUsuario, codigo);
		if (user == null)
			return ResponseEntity.badRequest().build();

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@Operation(summary = "Deleta um usuário cadastrado da base de dados" , responses = {
			@ApiResponse(description = "retorna um ok", responseCode = "200"),
			@ApiResponse(description = "retorna um unprocessableEntity", responseCode = "422")
	})
	@SuppressWarnings("unchecked")
	@DeleteMapping("/{id}")
	@Transactional
	@Secured("ROLE_ADMIN")
	public ResponseEntity<UsuarioDto> deletar(@PathVariable Long id) {

		return Boolean.TRUE.equals(usuarioService.deletar(id)) ? 
				(ResponseEntity<UsuarioDto>) ResponseEntity.ok() 
				: ResponseEntity.unprocessableEntity().build();
	}

}