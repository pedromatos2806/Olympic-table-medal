package com.quadromedalhasolimpiadas.olimpics.configuration.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.quadromedalhasolimpiadas.olimpics.repositories.UsuarioRepository;
import com.quadromedalhasolimpiadas.olimpics.services.JWTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private JWTokenService tokenService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		var token = recuperarToken(request);
		
		//System.out.println("Token: " + token);
		if(token != null) {
			var login = tokenService.getSubject(token);
			//System.out.println("Login: " + login);
			var usuario = usuarioRepository.findByEmail(login);
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
		
	}
	
	public String recuperarToken(HttpServletRequest request) {
	    var token = request.getHeader("Authorization");
	    
	    
	    //System.out.println("Header Authorization: " + token); // Imprime o valor completo do cabeçalho Authorization
	    
	    if (token != null && token.startsWith("Bearer ")) {
	        String tokenSemBearer = token.replace("Bearer ", "").trim();
	        //System.out.println("Token processado: " + tokenSemBearer); // Imprime o token sem o prefixo Bearer
	        return tokenSemBearer;
	    }
	    
	    return null;
	}

}