package com.quadromedalhasolimpiadas.olimpics.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(  @NotBlank(message = "Email é obrigatório")
								@Email(message = "Formato do email é inválido") String login
								,@NotBlank(message = "senha é obrigatória") String senha) {
}