package com.ServiYa.serviya.modelos.modelos.contrato;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * REESCRITO. Spring Data JPA mapea la consulta nativa
 * ContratoRepository#obtenerContratoDetalle a este DTO POR POSICIÓN, así que el orden
 * de los campos debe coincidir con el orden de las columnas del SELECT:
 * id_contrato, nombre_colaborador, nombre_contrato, direccion, fecha_inicio, fecha_fin,
 * precio, descripcion, contrato (nombre_archivo), foto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleContratoDTO {
	private Long idContrato;
	private String nombreColaborador;
	private String nombreContrato;
	private String direccion;
	private Date fechaInicio;
	private Date fechaFin;
	private String precio;
	private String descripcion;
	private String contrato;
	private byte[] foto;
}
