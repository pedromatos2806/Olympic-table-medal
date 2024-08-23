package com.quadromedalhasolimpiadas.olimpics.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
	public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info().title("API Olympic medal table")
	                .version("1.0")
	                .description("Documentação da API com Swagger"));
	    }
	}


