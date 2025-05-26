package com.quadromedalhasolimpiadas.olimpics.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quadromedalhasolimpiadas.olimpics.configuration.security.SecurityFilter;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.UsuarioDto;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Usuario;
import com.quadromedalhasolimpiadas.olimpics.domain.services.JWTokenService;
import com.quadromedalhasolimpiadas.olimpics.domain.services.UsuarioService;
import com.quadromedalhasolimpiadas.olimpics.repositories.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UsuarioControllerTest {

	@MockBean
	private SecurityFilter securityFilter;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private JWTokenService tokenService;

	@Autowired
	private JacksonTester<UsuarioDto> jsonUsuarioDto;

	@BeforeEach
	void setUp() {
	    JacksonTester.initFields(this, new ObjectMapper());
	    when(tokenService.gerarToken(any())).thenReturn("token-mockado");
	    when(tokenService.getSubject("token-mockado")).thenReturn("pedro@teste.com");
	    // Mock the repository to return a user
	    Usuario usuario = new Usuario();
	    usuario.setId(1L);
	    usuario.setNome("pedro-teste");
	    usuario.setEmail("pedro@teste.com");
	    usuario.setSenha("12345");
	    usuario.setRoles(Collections.emptyList());
	    when(usuarioRepository.findByEmail("pedro@teste.com")).thenReturn(usuario);
	}

	@Test
	@DisplayName("testando")
	void test_Salvar_Usuario() throws Exception {
		UsuarioDto usuarioDto = new UsuarioDto(1L, "pedro-teste", "pedro@teste.com", "12345", null);

		var content = jsonUsuarioDto.write(usuarioDto).getJson();
//		 @formatter:off
		this.mockMvc.perform(
		        post("/usuario")
		            .with(csrf())
		            .header("Authorization", "Bearer token-mockado")
		            .accept(MediaType.APPLICATION_JSON)
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(content))
		    .andExpect(status().isOk());
//		 @formatter:on
	}
}
