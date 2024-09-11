package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
								@Email(message = "{dadosAutenticacao.login}") String login
								,@NotBlank(message = "{dadosAutenticacao.senha}") String senha) {
}