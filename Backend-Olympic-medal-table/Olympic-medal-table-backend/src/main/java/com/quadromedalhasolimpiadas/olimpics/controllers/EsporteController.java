package com.quadromedalhasolimpiadas.olimpics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.EsporteDto;
import com.quadromedalhasolimpiadas.olimpics.domain.services.EsporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/esporte")
public class EsporteController {

	@Autowired
	EsporteService esporteService;

	
	private static final String  errosBuscarUmEsporteBadRequest = """
			O esporte {0} não está cadastrado no nosso sistema! <br>
			""";
	
	
	@Operation(description = "Busca os todos os esportes")
	@ApiResponse(description = "retorna um Page de todos os esportes da aplicação")
	@GetMapping
	public Page<EsporteDto> todosOsEsportes(Pageable pag){
		return esporteService.todosOsEsportes(pag);
	}
	
	@Operation(summary = "Busca um esporte pelo nome", responses = {
			@ApiResponse(responseCode = "200", description = "Retorna um ResponseEntity de um EsporteDTO" , content = {@Content (mediaType = MediaType.APPLICATION_JSON_VALUE)}),
			@ApiResponse(responseCode = "400", description = errosBuscarUmEsporteBadRequest , content = {@Content (mediaType = MediaType.APPLICATION_JSON_VALUE)})
	})
	@GetMapping(value = "/{nome}")
	public ResponseEntity<EsporteDto> buscarUmEsporte(@PathVariable String nome){
		var esporteDto = esporteService.buscarUmEsporte(nome);
		if(esporteDto == null)
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(esporteDto);
	}
	
	@Operation(summary = "Adiciona um esporte na aplicação", responses = {
			@ApiResponse(responseCode = "201", description = "retorna um ResponseEntity de um EsporteDTO com o esporte cadastrado" , content = {@Content (mediaType = MediaType.APPLICATION_JSON_VALUE)}),
			@ApiResponse(responseCode = "400", description = "" , content = {@Content (mediaType = MediaType.APPLICATION_JSON_VALUE)})
	})
	@PostMapping
	public ResponseEntity<EsporteDto> adicionarUmEsporte(@RequestBody EsporteDto esporteDto){
		EsporteDto esporteDtoSalvo = esporteService.adicionarUmEsporte(esporteDto);
		
		if(esporteDto == null)
			ResponseEntity.badRequest().build();
		
		return new ResponseEntity<>(esporteDtoSalvo, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Edita um esporte na aplicação" , responses = {
			@ApiResponse(responseCode = "201", description = "retorna um ResponseEntity de um EsporteDTO com o esporte modificado" , content = {@Content (mediaType = MediaType.APPLICATION_JSON_VALUE)}),
			@ApiResponse(responseCode = "400", description = errosBuscarUmEsporteBadRequest , content = {@Content (mediaType = MediaType.APPLICATION_JSON_VALUE)})
	})
	@PutMapping(value = "/{idEsporteString}")
	public ResponseEntity<EsporteDto> editarUmEsporte(@RequestBody EsporteDto esporteDto, @PathVariable String idEsporteString) {
		var esporteDtoEditado = esporteService.editarUmEsporte(esporteDto, idEsporteString);
		
		return ResponseEntity.ok(esporteDtoEditado);
	}
}
