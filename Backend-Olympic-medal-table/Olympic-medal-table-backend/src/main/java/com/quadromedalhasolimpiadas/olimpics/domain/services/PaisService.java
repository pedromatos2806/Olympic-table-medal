package com.quadromedalhasolimpiadas.olimpics.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.PaisDtoSaida;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.PaisDtoSaidaComMedalhas;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Pais;
import com.quadromedalhasolimpiadas.olimpics.exceptions.PaisNotExistsException;
import com.quadromedalhasolimpiadas.olimpics.repositories.MedalhaRepository;
import com.quadromedalhasolimpiadas.olimpics.repositories.PaisRepository;
import com.quadromedalhasolimpiadas.olimpics.repositories.UsuarioRepository;

@Service
public class PaisService {
	
	@Autowired
	PaisRepository paisRepository;
	
	@Autowired
	MedalhaRepository medalhaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Page<PaisDtoSaida> todosOsPaises(Pageable pageable) {
	    Page<Pais> pagePaises = paisRepository.findAll(pageable);

	    List<PaisDtoSaida> paisesCommandSaida = pagePaises
	        .getContent()
	        .stream()
	        .map(PaisDtoSaida::new)
	        .toList();
	    
	    return new PageImpl<>(paisesCommandSaida, pageable, pagePaises.getTotalElements());
	}
	public Page<PaisDtoSaida> todosOsPaisesComMedalha(Pageable pageable) {

		List<Pais> paises = paisRepository.findAllComMedalha();
		List<PaisDtoSaida> paisesCommandSaida = paises.stream().map(PaisDtoSaida::new).toList();

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), paisesCommandSaida.size());

		List<PaisDtoSaida> sublist = paisesCommandSaida.subList(start, end);

		return new PageImpl<>(sublist, pageable, paisesCommandSaida.size());
	}
	
	public List<PaisDtoSaidaComMedalhas> findByName(String nomePais) {
		
		List<Pais> pais = paisRepository.findByNome(nomePais);
		
		if(pais.isEmpty())
			throw new PaisNotExistsException();
		
		return pais.stream().map(PaisDtoSaidaComMedalhas::new).toList();
	}
	
	public PaisDtoSaidaComMedalhas findByCodigo(String codigoPais) {
		if(codigoPais.contains(" "))
			codigoPais.replace(" ", "");
		if( codigoPais.contains(".") )
			codigoPais.replace(".", "");
		
		Pais pais = paisRepository.findByCodigo(codigoPais.toUpperCase()).orElse(null);
		
		if(pais == null)
			throw new PaisNotExistsException();
		
		
		return new PaisDtoSaidaComMedalhas(pais);
	}

}
