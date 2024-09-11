package com.quadromedalhasolimpiadas.olimpics.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.EsporteDto;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Esporte;
import com.quadromedalhasolimpiadas.olimpics.exceptions.EsporteNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.repositories.EsporteRepository;

@Service
public class EsporteService {
	
	@Autowired
	EsporteRepository esporteRepository;
	
	public Page<EsporteDto> todosOsEsportes(Pageable pag){
		return esporteRepository.findAll(pag).map(EsporteDto::new);
	}
	
	public EsporteDto buscarUmEsporte(String nome) {
		Esporte esporte;
		esporte = esporteRepository.findByNome(nome).orElse(null);
		
		if(esporte == null)
			throw new EsporteNotExistsException(nome);
		
		return new EsporteDto(esporte);
	}
	
	public EsporteDto adicionarUmEsporte(EsporteDto esporteDto) {
		if(esporteDto == null)
			return null;
		Esporte esporte = new Esporte(esporteDto);
		
		Esporte esporteSalvo = esporteRepository.save(esporte);
		
		return new EsporteDto(esporteSalvo);
	}
	
	public EsporteDto editarUmEsporte(EsporteDto esporteDto, String idEsporteString) {
		Long idEsporte = Long.valueOf(idEsporteString);
		

		Esporte esporteBanco = esporteRepository.findById(idEsporte).orElseThrow(() -> new EsporteNotExistsException(idEsporte.toString()));
		esporteBanco.setEhEquipe(esporteDto.ehEquipe());
		//esporteBanco.setMedalhas(esporteDto.medalhas());
		esporteBanco.setNome(esporteDto.nome());
		
		Esporte esporteSalvo = esporteRepository.save(esporteBanco);
		
		return new EsporteDto(esporteSalvo);
	}
}
