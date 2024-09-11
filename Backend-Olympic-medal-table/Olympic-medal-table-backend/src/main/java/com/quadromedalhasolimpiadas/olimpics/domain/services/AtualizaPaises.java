package com.quadromedalhasolimpiadas.olimpics.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Pais;
import com.quadromedalhasolimpiadas.olimpics.domain.model.enumeration.TipoMedalha;
import com.quadromedalhasolimpiadas.olimpics.repositories.MedalhaRepository;
import com.quadromedalhasolimpiadas.olimpics.repositories.PaisRepository;

@Service
public class AtualizaPaises {

	@Autowired
	MedalhaRepository medalhaRepository;

	@Autowired
	PaisRepository paisRepository;

	
	public void atualizarEstatisticasMedalhas(Pais pais) {
		
		Integer medalhasOuro = medalhaRepository.countByPaisAndTipoMedalha(pais, TipoMedalha.OURO);
		Integer medalhasPrata = medalhaRepository.countByPaisAndTipoMedalha(pais, TipoMedalha.PRATA);
		Integer medalhasBronze = medalhaRepository.countByPaisAndTipoMedalha(pais, TipoMedalha.BRONZE);

		pais.setMedalhasOuro(medalhasOuro);
		pais.setMedalhasPrata(medalhasPrata);
		pais.setMedalhasBronze(medalhasBronze);
		pais.setTotalMedalhas(medalhasOuro + medalhasPrata + medalhasBronze);

		paisRepository.save(pais);
	}
}
