package com.quadromedalhasolimpiadas.olimpics.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.model.dto.EsporteDto;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Esporte;
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
			throw new ObjectNotFoundException(esporte, nome);
		
		return new EsporteDto(esporte);
	}
	
	public EsporteDto adicionarUmEsporte(Esporte esporte) {
		if(esporte == null)
			return null;
		
		var esporteSalvo = esporteRepository.save(esporte);
		
		return new EsporteDto(esporteSalvo);
	}
	
	public EsporteDto editarUmEsporte(Esporte esporte, String idEsporteString) {
		Long idEsporte = Long.valueOf(idEsporteString);
		
		Esporte esporteBanco = esporteRepository.findById(idEsporte).orElseThrow(() -> new ObjectNotFoundException(idEsporte, idEsporteString));
		esporteBanco.setEhEquipe(esporte.getEhEquipe());
		esporteBanco.setMedalhas(esporte.getMedalhas());
		esporteBanco.setNome(esporte.getNome());
		
		Esporte esporteSalvo = esporteRepository.save(esporteBanco);
		
		return new EsporteDto(esporteSalvo);
	}
}
