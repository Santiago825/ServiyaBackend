package com.ServiYa.serviya.modelos.modelos.servicio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSeguirColaborador {
	/** Ignorado: el contratante es el usuario del token. Se conserva para no romper clientes antiguos. */
	private long contratante;
	private long colaborador;

}
