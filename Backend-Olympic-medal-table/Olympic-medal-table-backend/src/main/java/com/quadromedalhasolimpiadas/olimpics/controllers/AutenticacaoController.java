package com.quadromedalhasolimpiadas.olimpics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.DadosAutenticacao;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.DadosTokenJWT;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Usuario;
import com.quadromedalhasolimpiadas.olimpics.domain.services.JWTokenService;


@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JWTokenService tokenService;

	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}