package com.ServiYa.serviya.modelos.modelos.servicio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSeguirColaborador {
	private long contratante;
	private long colaborador;

}
