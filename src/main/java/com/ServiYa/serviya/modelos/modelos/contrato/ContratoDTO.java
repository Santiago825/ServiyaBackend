package com.ServiYa.serviya.modelos.modelos.contrato;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoDTO {
	private Long idContrato;
	private String nombreContrato;
	private String nombreColaborador;
	private String direccion;
	private String precio;
	
}
