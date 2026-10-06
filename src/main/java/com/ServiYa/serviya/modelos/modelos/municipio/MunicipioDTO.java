package com.ServiYa.serviya.modelos.modelos.municipio;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MunicipioDTO {
	
	private Long idMunicipio;
	private String nombre;
	private Long id_departamento;

}
