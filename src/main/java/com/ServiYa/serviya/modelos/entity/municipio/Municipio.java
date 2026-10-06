package com.ServiYa.serviya.modelos.entity.municipio;

import java.util.List;

import com.ServiYa.serviya.modelos.entity.departamento.Departamento;
import com.ServiYa.serviya.modelos.entity.persona.Persona;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** RESTAURADO. toString solo con escalares para evitar ciclos. */
@Entity
@Table(name = "municipio")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Municipio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_municipio")
	@ToString.Include
	private Long idMunicipio;

	@ToString.Include
	private String nombre;

	@ManyToOne(targetEntity = Departamento.class)
	@JoinColumn(name = "id_departamento")
	private Departamento departamento;

	/** (Nombre original "psersonas" conservado: no hay columna, solo afecta al código Java.) */
	@OneToMany(targetEntity = Persona.class, fetch = FetchType.LAZY, mappedBy = "municipio")
	private List<Persona> psersonas;
}
