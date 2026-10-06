package com.ServiYa.serviya.modelos.modelos.departamento;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartamentoDTO {

	private Long idDepartamento;
	private String nombre;
}
