package com.ServiYa.serviya.modelos.modelos.servicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioDTO {
	
	private Long idServicio;
	private String nombre;
	private Long usos;
	private String icono;

}
