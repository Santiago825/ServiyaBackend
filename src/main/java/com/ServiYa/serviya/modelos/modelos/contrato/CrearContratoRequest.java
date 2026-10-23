package com.ServiYa.serviya.modelos.modelos.contrato;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrearContratoRequest {
	private String contratanteUsername;
	private String nombre;
	private String direccion;
	private String telefono;
	private String precio;
	private String descripcion;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaInicio;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fechaFin;

	// El archivo (opcional) llega como parte aparte; lo recibe el controlador, no este DTO.
}