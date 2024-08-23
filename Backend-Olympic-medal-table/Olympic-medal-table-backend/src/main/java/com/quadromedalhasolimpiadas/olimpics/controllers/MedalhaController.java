package com.quadromedalhasolimpiadas.olimpics.controllers;

import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quadromedalhasolimpiadas.olimpics.model.command.MedalhaCommand;
import com.quadromedalhasolimpiadas.olimpics.model.command.MedalhaCommandEntrada;
import com.quadromedalhasolimpiadas.olimpics.model.command.MedalhaCommandSaida;
import com.quadromedalhasolimpiadas.olimpics.model.dto.MedalhaDto;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Esporte;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.model.enumeration.TipoMedalha;
import com.quadromedalhasolimpiadas.olimpics.services.MedalhaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/medalha")
public class MedalhaController {

	@Autowired
	MedalhaService medalhaService;

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	
	@Operation(description = "Busca todas as medalhas da aplicação")
	@ApiResponse(description = "retorna um Page com todas as medalhas cadastrados na aplicação")
	@GetMapping()
	public Page<MedalhaDto> todasAsMedalhas(Pageable pag) {
		return medalhaService.todasAsMedalhas(pag);
	}

	@Operation(description = "Busca uma medalha pelo TipoMedalha e Esporte")
	@ApiResponse(description = "Retorna um ResponseEntity de um MedalhaDTO")
	@GetMapping(value = "/uma-medalha")
	public ResponseEntity<MedalhaDto> buscarUmaMedalha(@RequestBody TipoMedalha tipoMedalha,
			@RequestBody Esporte esporte) {
		MedalhaDto medalha = medalhaService.buscarUmaMedalha(tipoMedalha, esporte);

		if (medalha == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(medalha);
	}

	@Operation(description = "Adiciona uma medalha na aplicação")
	@ApiResponse(description = "retorna um ResponseEntity com a medalha cadastrada")
	@PostMapping
	public ResponseEntity<MedalhaCommandSaida> adicionarUmaMedalha(@RequestBody MedalhaCommandEntrada medalha)
			throws Exception {
		MedalhaCommandSaida medalhaCommandSaida = medalhaService.adicionarUmaMedalha(medalha);
		if (medalhaCommandSaida == null)
			return ResponseEntity.notFound().build();
		
		Message message = new Message(("Criei uma medalha com o id" + medalhaCommandSaida.idMedalha().toString()).getBytes());
		rabbitTemplate.send("email.notificacao",message );
		return ResponseEntity.ok(medalhaCommandSaida);
	}

	@Operation(description = "Adiciona medalhas na aplicação")
	@ApiResponse(description = "retorna um ResponseEntity com as medalhas cadastradas")
	@PostMapping(value = "/esporte/{idEsporte}/pais/{codigoPais}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedalhaCommandSaida>> adicionarMedalhas(@RequestBody MedalhaCommand medalha,
			@PathVariable Long idEsporte, @PathVariable String codigoPais) throws Exception {
		var medalhasDto = medalhaService.adicionarMedalhas(medalha, idEsporte, codigoPais);
		if (medalhasDto == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(medalhasDto);
	}

	@Operation(description = "Edita uma medalha na aplicação")
	@ApiResponse(description = "retorna um ResponseEntity com a medalhas modificada")
	@PutMapping(value = "/idMedalha/{idMedalhaString}")
	public ResponseEntity<MedalhaDto> editarUmaMedalha(@RequestBody Medalha medalha,
			@PathVariable String idMedalhaString) {
		MedalhaDto medalhaDto = medalhaService.editarUmaMedalha(medalha, idMedalhaString);

		if (medalhaDto == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(medalhaDto);
	}
}
