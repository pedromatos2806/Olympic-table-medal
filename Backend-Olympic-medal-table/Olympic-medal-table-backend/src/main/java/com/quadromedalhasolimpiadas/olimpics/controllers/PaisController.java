package com.quadromedalhasolimpiadas.olimpics.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.PaisDtoSaida;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.PaisDtoSaidaComMedalhas;
import com.quadromedalhasolimpiadas.olimpics.domain.services.PaisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/pais")
public class PaisController {

	@Autowired
	private PaisService paisService;
	
	@Operation(summary = "Busca os todos os países")
	@ApiResponse(description = "retorna um Page de todos os países da aplicação")
	@GetMapping
	public Page<PaisDtoSaida> todosOsPaises(Pageable pag) {
		return paisService.todosOsPaises(pag);
	}
	
	@Operation(summary = "Busca os todos os países")
	@ApiResponse(description = "retorna um Page de todos os países da aplicação")
	@GetMapping(value="/commedalha")
	public Page<PaisDtoSaida> todosOsPaisesComMedalha(Pageable pag) {
		return paisService.todosOsPaisesComMedalha(pag);
	}
	
	@Operation(summary = "Busca uma lista de países pelo nome" , responses = {
			@ApiResponse(responseCode = "200", description = "retorna um Response Entity com uma lista de países com esse nome da aplicação" , content = { @Content (mediaType = MediaType.APPLICATION_JSON_VALUE)}),
			@ApiResponse(responseCode = "400", description = "" , content = { @Content (mediaType = MediaType.APPLICATION_JSON_VALUE)})	
	})
	@GetMapping(value = "nome/{nomePais}")
	public ResponseEntity<List<PaisDtoSaidaComMedalhas>> findByName(@PathVariable String nomePais) {
		List<PaisDtoSaidaComMedalhas> paisesDto = paisService.findByName(nomePais);

		if (paisesDto == null)
			return ResponseEntity.badRequest().build();

		return new ResponseEntity<>(paisesDto, HttpStatus.OK);
	}
	
	@Operation(summary = "Busca uma lista de países pelo codigo", responses = {
			@ApiResponse(responseCode = "200" , description ="retorna um Response Entity com uma lista de países com esse codigo da aplicação", content = { @Content (mediaType = MediaType.APPLICATION_JSON_VALUE)}),
			@ApiResponse(responseCode = "400" , description ="", content = { @Content (mediaType = MediaType.APPLICATION_JSON_VALUE)})
					
	})
	@GetMapping(value = "codigo/{codigo}")
	public ResponseEntity<PaisDtoSaidaComMedalhas> findByCodigo(@PathVariable String codigo) {
		PaisDtoSaidaComMedalhas paisesDto = paisService.findByCodigo(codigo);

		if (paisesDto == null)
			return ResponseEntity.badRequest().build();

		return new ResponseEntity<>(paisesDto, HttpStatus.OK);
	}


}
