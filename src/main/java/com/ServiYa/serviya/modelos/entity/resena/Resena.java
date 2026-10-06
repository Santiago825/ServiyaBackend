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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CORREGIDO: el archivo se llamaba "Reseña.java" pero declaraba "class Resea"
 * (y el nombre quedó mal codificado en el ZIP). javac exige que una clase pública
 * esté en un archivo con su mismo nombre, así que NO compilaba.
 * Ahora: paquete/archivo/clase "Resea" (ASCII). Tabla: resea.
 */
@Entity
@Table(name = "resena")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_resena")
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
