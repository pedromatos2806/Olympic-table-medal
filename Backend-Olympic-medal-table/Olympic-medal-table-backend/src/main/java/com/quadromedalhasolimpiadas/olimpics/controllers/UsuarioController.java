package com.quadromedalhasolimpiadas.olimpics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.quadromedalhasolimpiadas.olimpics.model.command.UsuarioCommand;
import com.quadromedalhasolimpiadas.olimpics.model.dto.UsuarioDto;
import com.quadromedalhasolimpiadas.olimpics.services.JWTokenService;
import com.quadromedalhasolimpiadas.olimpics.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	JWTokenService tokenService;
	
	@Operation(description = "Busca todos usuarios da aplicação")
	@ApiResponse(description = "retorna um Page com todos usuarios cadastrados na aplicação")
	@GetMapping
	public Page<UsuarioDto> todosUsuarios(Pageable pag){
		return usuarioService.todosUsuarios(pag);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDto> salvar(@RequestBody @Valid UsuarioDto usuarioDto) {
	    try {
	        UsuarioDto usuarioDtoSalvo = usuarioService.salvar(usuarioDto);
	        return ResponseEntity.ok(usuarioDtoSalvo);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@Operation(description = "Cadastra um usuário em um país")
	@ApiResponse(description = "Retorna um ResponseEntity de um país com uma lista de usuários dentro")
	@PostMapping(value = "/codigoPais/{codigo}")
	public ResponseEntity<UsuarioCommand> cadastrarUsuarioEmUmPais(
	        @RequestHeader("Authorization") String authorizationHeader,
	        @PathVariable String codigo) {
	    
	    // Extrair o token JWT do header
	    String token = null;
	    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        token = authorizationHeader.substring(7); // Remove "Bearer " do início
	    }
	    
	    if (token == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    
	    // Obter o ID do usuário a partir do token JWT
	    var userId = tokenService.getClaim(token, "id").longValue();
	    
	    // Certifique-se de que o ID é numérico antes de convertê-lo para Long
	    Long idUsuario;
	    try {
	        idUsuario = userId;
	    } catch (NumberFormatException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	    
	    // Chamar o serviço para cadastrar o usuário no país
	    return usuarioService.cadastrarUsuarioEmUmPais(idUsuario, codigo);
	}
	
	@SuppressWarnings("unchecked")
	@DeleteMapping("/{id}")
	@Transactional
	@Secured("ROLE_ADMIN")
	public ResponseEntity<UsuarioDto> deletar(@PathVariable Long id) {

		return usuarioService.deletar(id)?   (ResponseEntity<UsuarioDto>) ResponseEntity.ok() : ResponseEntity.notFound().build();
	}
	
	
}
