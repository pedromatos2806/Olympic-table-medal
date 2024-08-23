package com.quadromedalhasolimpiadas.olimpics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadromedalhasolimpiadas.olimpics.model.dto.EsporteDto;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Esporte;
import com.quadromedalhasolimpiadas.olimpics.services.EsporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/esporte")
public class EsporteController {

	@Autowired
	EsporteService esporteService;
	
	@Operation(description = "Busca os todos os esportes")
	@ApiResponse(description = "retorna um Page de todos os esportes da aplicação")
	@GetMapping
	public Page<EsporteDto> todosOsEsportes(Pageable pag){
		return esporteService.todosOsEsportes(pag);
	}
	
	@Operation(description = "Busca um esporte pelo nome")
	@ApiResponse(description = "Retorna um ResponseEntity de um EsporteDTO")
	@GetMapping(value = "/{nome}")
	public ResponseEntity<EsporteDto> buscarUmEsporte(@PathVariable String nome){
		var esporteDto = esporteService.buscarUmEsporte(nome);
		if(esporteDto == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(esporteDto);
	}
	
	@Operation(description = "Adiciona um esporte na aplicação")
	@ApiResponse(description = "retorna um ResponseEntity de um EsporteDTO com o esporte cadastrado")
	@PostMapping
	public ResponseEntity<EsporteDto> adicionarUmEsporte(@RequestBody Esporte esporte){
		EsporteDto esporteDto = esporteService.adicionarUmEsporte(esporte);
		
		if(esporteDto == null)
			ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(esporteDto);
	}
	
	@Operation(description = "Edita um esporte na aplicação")
	@ApiResponse(description = "retorna um ResponseEntity de um EsporteDTO com o esporte modificado")
	@PutMapping(value = "/{idEsporteString}")
	public ResponseEntity<EsporteDto> editarUmEsporte(@RequestBody Esporte esporte,@PathVariable String idEsporteString) {
		var esporteDto = esporteService.editarUmEsporte(esporte, idEsporteString);
		
		return ResponseEntity.ok(esporteDto);
	}
}
