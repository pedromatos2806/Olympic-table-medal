package com.quadromedalhasolimpiadas.olimpics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Usuario;
import com.quadromedalhasolimpiadas.olimpics.services.JWTokenService;

class JWTServiceTest {


    private JWTokenService tokenService;
    private String secret = "mySecretKey";  // Use um segredo de teste

    @BeforeEach
    void setUp() {
        tokenService = new JWTokenService(secret);
    }

    @Test
    void testGerarToken() {
        Usuario usuario = new Usuario();
        usuario.setEmail("user@example.com");

        String token = tokenService.gerarToken(usuario);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testGetSubject() {
        Usuario usuario = new Usuario();
        usuario.setEmail("user@example.com");

        String token = tokenService.gerarToken(usuario);

        String subject = tokenService.getSubject(token);

        assertEquals("user@example.com", subject);
    }

    @Test
    void testGetSubjectWithInvalidToken() {
        String invalidToken = "invalidToken";

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tokenService.getSubject(invalidToken);
        });

        
        assertEquals("Token JWT inválido ou expirado!", exception.getMessage());
    }
}
