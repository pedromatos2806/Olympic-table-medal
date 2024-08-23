package com.quadromedalhasolimpiadas.olimpics.model.command;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;



public record MedalhaCommand(List<String> tipoMedalhaString) {
	
	@JsonCreator
    public MedalhaCommand(List<String> tipoMedalhaString) {
        
        this.tipoMedalhaString = tipoMedalhaString.stream()
            .map(String::toUpperCase)
            .toList();
    }
	
	
}
