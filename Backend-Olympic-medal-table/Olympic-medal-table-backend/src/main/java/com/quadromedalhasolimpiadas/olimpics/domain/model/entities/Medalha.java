package com.quadromedalhasolimpiadas.olimpics.domain.model.entities;

import com.quadromedalhasolimpiadas.olimpics.domain.model.dto.MedalhaDto;
import com.quadromedalhasolimpiadas.olimpics.domain.model.enumeration.TipoMedalha;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Entity
@Table(name = "medalhas")
public class Medalha {
	
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name= "MEDALHA_ID")
	private Long id;
	
	@Column(name = "TIPO_MEDALHA")
	@Enumerated(EnumType.STRING)
	private TipoMedalha tipoMedalha;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "ESPORTE_ID", name = "ESPORTE_ID")
	private Esporte esporte;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "PAIS_ID" , name = "PAIS_ID", nullable = true)
	private Pais pais;
	
	public Medalha(MedalhaDto medalhaDto) {
		this.id = medalhaDto.id();
		this.tipoMedalha = medalhaDto.tipoMedalha();
		this.esporte = medalhaDto.esporte();
	}
	
	public Medalha(TipoMedalha tipoMedalha, Esporte esporte) {
		this.esporte=esporte;
		this.tipoMedalha=tipoMedalha;
	}
	
}
