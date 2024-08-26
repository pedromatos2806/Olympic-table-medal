package com.quadromedalhasolimpiadas.olimpics.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quadromedalhasolimpiadas.olimpics.model.entities.Pais;
@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
	
	@Query("""
			SELECT p
			FROM Pais p 
			JOIN FETCH Medalha m
			ON m.pais.id = p.id
			WHERE p.totalMedalhas > 0
			ORDER BY p.totalMedalhas DESC
					, p.medalhasOuro DESC
					, p.medalhasPrata DESC
					, p.medalhasBronze DESC
			""")
	public List<Pais> findAllComMedalha();
	
	public List<Pais> findByNome(String nome);

	public Optional<Pais> findByCodigo(String codigo);
	
	
}
