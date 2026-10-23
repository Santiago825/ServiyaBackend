package com.ServiYa.serviya.modelos.entity.resena;

import com.ServiYa.serviya.modelos.entity.contrato.Contrato;
import com.ServiYa.serviya.modelos.entity.persona.Persona;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Reseña que un CONTRATANTE deja sobre el colaborador de un contrato (una por contrato).
 * Tabla "resea" (ASCII, como en schema.sql): es la misma que leen las consultas nativas de
 * ColaboradorRepository y PersonaRepository. Antes la entidad apuntaba a "resena" y lo que se
 * guardara por JPA no se habría visto en esas consultas.
 * La restricción única impide reseñar dos veces el mismo contrato (también ante doble clic).
 */
@Entity
@Table(name = "resea", uniqueConstraints = @UniqueConstraint(name = "uk_resea_contrato", columnNames = "id_contrato"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_resea")
	private Long idResena;

	private int puntuacion;
	private String comentario;

	@ManyToOne(targetEntity = Contrato.class)
	@JoinColumn(name = "id_contrato")
	private Contrato contrato;

	@ManyToOne(targetEntity = Persona.class)
	@JoinColumn(name = "id_contratante")
	private Persona contratante;

	@ManyToOne(targetEntity = Persona.class)
	@JoinColumn(name = "id_colaborador")
	private Persona colaborador;
}
