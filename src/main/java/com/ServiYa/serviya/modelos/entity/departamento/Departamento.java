package com.ServiYa.serviya.modelos.entity.departamento;

import java.util.List;

import com.ServiYa.serviya.modelos.entity.municipio.Municipio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** RESTAURADO. */
@Entity
@Table(name = "departamento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_departamento")
	@ToString.Include
	private Long idDepartamento;

	@ToString.Include
	private String nombre;

	@OneToMany(targetEntity = Municipio.class, fetch = FetchType.LAZY, mappedBy = "departamento")
	private List<Municipio> municipios;
}
