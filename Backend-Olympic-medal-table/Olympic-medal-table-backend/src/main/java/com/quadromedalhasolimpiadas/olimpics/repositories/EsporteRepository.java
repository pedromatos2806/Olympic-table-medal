package com.quadromedalhasolimpiadas.olimpics.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quadromedalhasolimpiadas.olimpics.domain.model.entities.Esporte;
@Repository
public interface EsporteRepository extends JpaRepository<Esporte, Long>{

	Optional<Esporte> findByNome(String nome);
}
