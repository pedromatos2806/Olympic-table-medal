package com.quadromedalhasolimpiadas.olimpics.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Esporte;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Medalha;
import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Pais;
import com.quadromedalhasolimpiadas.olimpics.domain.model.enumeration.TipoMedalha;
@Repository
public interface MedalhaRepository extends JpaRepository<Medalha, Long>{
	
	Integer countByPaisAndTipoMedalha(Pais pais, TipoMedalha tipoMedalha);
	
	public Optional<Medalha> findByTipoMedalhaAndEsporte(TipoMedalha tipoMedalha, Esporte esporte);
	
}
