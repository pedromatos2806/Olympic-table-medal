package com.quadromedalhasolimpiadas.olimpics.domain.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;



public record MedalhaDtoSaida(List<String> tipoMedalhaString) {
	
	@JsonCreator
    public MedalhaDtoSaida(List<String> tipoMedalhaString) {
        
        this.tipoMedalhaString = tipoMedalhaString.stream()
            .map(String::toUpperCase)
            .toList();
    }
	
	
}
