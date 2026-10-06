package com.ServiYa.serviya.modelos.entity.servicio;

import java.util.List;

import com.ServiYa.serviya.modelos.entity.persona.Persona;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString(exclude = {"personas"})
public class Servicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idServicio;
	private String nombre;
	private Long usos;
	private String icono;
	@OneToMany(targetEntity = Persona.class,fetch = FetchType.LAZY,mappedBy = "servicio")
	private List<Persona> personas;
}
