package com.quadromedalhasolimpiadas.olimpics.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadromedalhasolimpiadas.olimpics.model.command.PaisCommandSaida;
import com.quadromedalhasolimpiadas.olimpics.model.command.PaisCommandSaidaComMedalhas;
import com.quadromedalhasolimpiadas.olimpics.services.PaisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/pais")
public class PaisController {

	@Autowired
	private PaisService paisService;
	
	@Operation(description = "Busca os todos os países")
	@ApiResponse(description = "retorna um Page de todos os países da aplicação")
	@GetMapping
	public Page<PaisCommandSaida> todosOsPaises(Pageable pag) {
		return paisService.todosOsPaises(pag);
	}
	
	@Operation(description = "Busca os todos os países")
	@ApiResponse(description = "retorna um Page de todos os países da aplicação")
	@GetMapping(value="/commedalha")
	public Page<PaisCommandSaida> todosOsPaisesComMedalha(Pageable pag) {
		return paisService.todosOsPaisesComMedalha(pag);
	}
	
	@Operation(description = "Busca uma lista de países pelo nome")
	@ApiResponse(description = "retorna um Response Entity com uma lista de países com esse nome da aplicação")
	@GetMapping(value = "nome/{nomePais}")
	public ResponseEntity<List<PaisCommandSaidaComMedalhas>> findByName(@PathVariable String nomePais) {
		List<PaisCommandSaidaComMedalhas> paisesDto = paisService.findByName(nomePais);

		if (paisesDto == null)
			return ResponseEntity.noContent().build();

		return new ResponseEntity<>(paisesDto, HttpStatus.OK);
	}
	
	@Operation(description = "Busca uma lista de países pelo codigo")
	@ApiResponse(description = "retorna um Response Entity com uma lista de países com esse codigo da aplicação")
	@GetMapping(value = "codigo/{codigo}")
	public ResponseEntity<PaisCommandSaidaComMedalhas> findByCodigo(@PathVariable String codigo) {
		PaisCommandSaidaComMedalhas paisesDto = paisService.findByCodigo(codigo);

		if (paisesDto == null)
			return ResponseEntity.noContent().build();

		return new ResponseEntity<>(paisesDto, HttpStatus.OK);
	}
	


}
