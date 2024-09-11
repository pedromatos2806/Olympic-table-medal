package com.quadromedalhasolimpiadas.olimpics.domain.model.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "paises")
public class Pais {
	
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name= "PAIS_ID")
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY ,  mappedBy = "pais")
	private Set<Medalha> medalhas;
	
	@ManyToMany(mappedBy = "paises", fetch = FetchType.LAZY )
	private List<Usuario> usuarios;
	
	@Column(name = "TOTAL_MEDALHAS")
	private Integer  totalMedalhas;

	@Column(name = "MEDALHAS_OURO")
	private Integer  medalhasOuro ;

	@Column(name = "MEDALHAS_PRATA")
	private Integer  medalhasPrata;

	@Column(name = "MEDALHAS_BRONZE")
	private Integer  medalhasBronze ;

}
