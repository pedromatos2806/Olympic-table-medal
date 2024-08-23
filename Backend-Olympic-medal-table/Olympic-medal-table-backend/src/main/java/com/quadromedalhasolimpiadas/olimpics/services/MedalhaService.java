package com.quadromedalhasolimpiadas.olimpics.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.model.command.MedalhaCommand;
import com.quadromedalhasolimpiadas.olimpics.model.command.MedalhaCommandEntrada;
import com.quadromedalhasolimpiadas.olimpics.model.command.MedalhaCommandSaida;
import com.quadromedalhasolimpiadas.olimpics.model.dto.MedalhaDto;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Esporte;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Pais;
import com.quadromedalhasolimpiadas.olimpics.model.enumeration.TipoMedalha;
import com.quadromedalhasolimpiadas.olimpics.repositories.EsporteRepository;
import com.quadromedalhasolimpiadas.olimpics.repositories.MedalhaRepository;
import com.quadromedalhasolimpiadas.olimpics.repositories.PaisRepository;

@Service
public class MedalhaService {
	
	@Autowired
	MedalhaRepository medalhaRepository;
	
	@Autowired
	EsporteRepository esporteRepository;
	
	@Autowired
	PaisRepository paisRepository;
	
	@Autowired
	AtualizaPaises atualizaPaises;
	
	public Page<MedalhaDto> todasAsMedalhas(Pageable pag){
		return medalhaRepository.findAll(pag).map(MedalhaDto::new);
	}
	
	public MedalhaDto buscarUmaMedalha(TipoMedalha tipoMedalha,Esporte esporte) {
		Medalha medalha = medalhaRepository.findByTipoMedalhaAndEsporte(tipoMedalha, esporte).orElse(null);
		if(medalha == null)
			throw new ObjectNotFoundException(medalha, "não há medalha com esse " + tipoMedalha +" e esse " + esporte);
		return new MedalhaDto(medalha);
	}
	
	public MedalhaCommandSaida adicionarUmaMedalha(MedalhaCommandEntrada medalhaCommandEntrada) throws Exception {

		Pais paisBanco = paisRepository.findByCodigo(medalhaCommandEntrada.codigoPais()).get();
		
		Esporte esporteBanco = esporteRepository.findById(medalhaCommandEntrada.idEsporte()).orElseThrow(() -> new ObjectNotFoundException(medalhaCommandEntrada.idEsporte(), "Não há esporte com esse ID!    "));
		
		Medalha medalhaExCommandEntrada = new Medalha();
		
		medalhaExCommandEntrada.setTipoMedalha(TipoMedalha.valueOf(medalhaCommandEntrada.tipoMedalha()));
		medalhaExCommandEntrada.setEsporte(esporteBanco);
		
		
		medalhaExCommandEntrada.setPais(paisBanco);
		Medalha medalhaSalva = medalhaRepository.save(medalhaExCommandEntrada);
		
		
		atualizaPaises.atualizarEstatisticasMedalhas(paisBanco);
		
		return new MedalhaCommandSaida(medalhaSalva);
		
	}
	
	public List<MedalhaCommandSaida> adicionarMedalhas(MedalhaCommand medalhaPresenter, Long idEsporte, String codigoPais) throws Exception {

		if(medalhaPresenter == null)
			return null;
		if(idEsporte == null)
			return null;
		
		Esporte esporteBanco = esporteRepository.findById(idEsporte).orElseThrow(() -> new ObjectNotFoundException(idEsporte, "Não existe esporte com esse id: " + idEsporte.toString()));
		Pais paisBanco = paisRepository.findByCodigo(codigoPais).orElseThrow(() -> new ObjectNotFoundException(new Esporte(), "Não há pais cadastrado com esse código! "));
		
		List<MedalhaCommandSaida> medalhasSalvasCommandSaida = new ArrayList<>();
		
		for (String stringTipoMedalha : medalhaPresenter.tipoMedalhaString()) {
			
			if(!(stringTipoMedalha.equals(TipoMedalha.OURO.toString())	||	stringTipoMedalha.equals(TipoMedalha.PRATA.toString()) || stringTipoMedalha.equals(TipoMedalha.BRONZE.toString())))
				throw new ObjectNotFoundException(medalhaPresenter, "Não foi possível converter algum objeto de " +medalhaPresenter.tipoMedalhaString() +"em um TipoMedalha	");
			
			paisRepository.save(paisBanco);
			TipoMedalha tipoMedalha = TipoMedalha.valueOf(stringTipoMedalha.toUpperCase());
			Medalha medalha = Medalha.builder().withEsporte(esporteBanco).withPais(paisBanco).withTipoMedalha(tipoMedalha).build();
			Medalha medalhaSalva = medalhaRepository.save(medalha);
			
			medalhasSalvasCommandSaida.add(new MedalhaCommandSaida(medalhaSalva));
		}
		atualizaPaises.atualizarEstatisticasMedalhas(paisBanco);
		return medalhasSalvasCommandSaida;

		}

	
	public MedalhaDto editarUmaMedalha(Medalha medalha, String idMedalhaString) {
		Long idMedalha = Long.valueOf(idMedalhaString);
		
		if(medalha == null)
			throw new ObjectNotFoundException(medalha, "Medalha está nula!");
		
		Medalha medalhaBanco = medalhaRepository.findById(idMedalha).orElseThrow(() -> new ObjectNotFoundException(idMedalha, idMedalhaString));
		
		medalhaBanco.setEsporte(medalha.getEsporte());
		medalhaBanco.setPais(medalha.getPais());
		medalhaBanco.setTipoMedalha(medalha.getTipoMedalha());
		
		Medalha medalhaSalva = medalhaRepository.save(medalhaBanco);
		return new MedalhaDto(medalhaSalva);
	}
	

    
	
}
