package com.ServiYa.serviya.modelos.modelos.resena;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Una reseña recibida por un colaborador. El orden de campos debe coincidir con ResenaRepository.buscarRecibidas. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaRecibidaDTO {
	private Long idResena;
	private Integer puntuacion;
	private String comentario;
	private Long idContrato;
	private String nombreContrato;
	private String descripcionContrato;
	private String nombreContratante;
}
