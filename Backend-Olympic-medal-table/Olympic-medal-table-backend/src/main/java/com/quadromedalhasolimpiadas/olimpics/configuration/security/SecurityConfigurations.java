package com.quadromedalhasolimpiadas.olimpics.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfigurations {

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
	            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .authorizeHttpRequests(req -> {
	                req.requestMatchers(HttpMethod.POST, "/login").permitAll();
	                req.requestMatchers(HttpMethod.POST, "/usuario").permitAll();
	                req.requestMatchers(HttpMethod.OPTIONS, "/usuario").permitAll();
	                req.requestMatchers(HttpMethod.GET, "/pais").permitAll();
	                req.requestMatchers(HttpMethod.GET, "/pais/**").permitAll();
	                req.requestMatchers(HttpMethod.GET, "/esporte/**").permitAll();
	                req.requestMatchers(HttpMethod.GET, "/esporte").permitAll();
	                req.requestMatchers("/swagger-ui.html").permitAll(); // Permite qualquer requisição que contenha /swagger-ui.html
	                req.requestMatchers("/swagger-ui.html/**").permitAll(); // Permite qualquer requisição que contenha /swagger-ui.html
	                req.requestMatchers("/swagger-ui/**").permitAll(); // Permite qualquer requisição para /swagger-ui/*
	                req.requestMatchers("/v3/api-docs/**").permitAll(); // Permite o acesso à documentação gerada pelo Swagger
	                req.anyRequest().authenticated();
	            })
	            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration ) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
