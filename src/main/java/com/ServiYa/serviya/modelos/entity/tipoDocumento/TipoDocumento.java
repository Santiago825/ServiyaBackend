package com.ServiYa.serviya.modelos.entity.tipoDocumento;

import java.util.List;

import org.hibernate.annotations.GeneratorType;

import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "persona"})
public class TipoDocumento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocumento;
	private String tipo;
	private String nombre;
	@OneToMany(targetEntity = Persona.class ,fetch = FetchType.LAZY,mappedBy = "documento")
	private List<Persona> persona;

}
