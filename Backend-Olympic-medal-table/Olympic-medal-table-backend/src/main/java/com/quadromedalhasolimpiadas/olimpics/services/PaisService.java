package com.quadromedalhasolimpiadas.olimpics.services;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.model.command.PaisCommandSaida;
import com.quadromedalhasolimpiadas.olimpics.model.command.PaisCommandSaidaComMedalhas;
import com.quadromedalhasolimpiadas.olimpics.model.entities.Pais;
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
	
	public Page<PaisCommandSaida> todosOsPaises(Pageable pageable) {

		List<Pais> paises = paisRepository.findAll();
		List<PaisCommandSaida> paisesCommandSaida = paises.stream().map(PaisCommandSaida::new).toList();

		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), paisesCommandSaida.size());

		List<PaisCommandSaida> sublist = paisesCommandSaida.subList(start, end);

		return new PageImpl<PaisCommandSaida>(sublist, pageable, paisesCommandSaida.size());
	}
	
	public List<PaisCommandSaidaComMedalhas> findByName(String nomePais) {
		
		List<Pais> pais = paisRepository.findByNome(nomePais);
		
		if(pais.isEmpty())
			return null;
		
		return pais.stream().map(PaisCommandSaidaComMedalhas::new).toList();
	}
	
	public PaisCommandSaidaComMedalhas findByCodigo(String nomePais) {
		if(nomePais.contains(" "))
			nomePais.replace(" ", "");
		if( nomePais.contains(".") )
			nomePais.replace(".", "");
		
		Pais pais = paisRepository.findByCodigo(nomePais.toUpperCase()).orElse(null);
		
		if(pais == null)
			throw new ObjectNotFoundException(pais, "Não existe pais com esse código!! 		");
		
		
		return new PaisCommandSaidaComMedalhas(pais);
	}

}
