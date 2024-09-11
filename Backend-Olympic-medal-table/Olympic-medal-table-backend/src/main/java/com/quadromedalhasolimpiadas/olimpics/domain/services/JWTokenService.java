package com.quadromedalhasolimpiadas.olimpics.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Usuario;

@Service
public class JWTokenService {

    private final String secret;
    private static final long EXPIRATION_HOURS = 6;

    public JWTokenService(@Value("${api.security.token.secret}") String secret) {
        this.secret = secret;
    }

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
            		.withIssuer("API Olimpcs - table medal")
                    .withSubject(usuario.getEmail())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(EXPIRATION_HOURS).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
            		.withIssuer("API Olimpcs - table medal")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }
    
    public Long getClaim(String tokenJWT, String claimName) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algoritmo)
                    .withIssuer("API Olimpcs - table medal")
                    .build();
            var decodedJWT = verifier.verify(tokenJWT);

            return decodedJWT.getClaim(claimName).asLong(); // Para claims inteiros, por exemplo
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }
}
