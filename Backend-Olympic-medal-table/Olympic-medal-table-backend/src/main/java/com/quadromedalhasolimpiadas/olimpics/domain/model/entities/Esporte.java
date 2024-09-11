package com.quadromedalhasolimpiadas.olimpics.domain.model.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.EsporteDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "esportes")
@Entity
public class Esporte {
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ESPORTE_ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "TEM_EQUIPE")
    private Boolean ehEquipe;
    
    @JsonIgnore
    @OneToMany(mappedBy = "esporte")
    private Set<Medalha> medalhas;
    
    public Esporte(EsporteDto esporteDto) {
        this.id=esporteDto.id();
    	this.nome = esporteDto.nome();
        this.ehEquipe = esporteDto.ehEquipe();
    }
}
