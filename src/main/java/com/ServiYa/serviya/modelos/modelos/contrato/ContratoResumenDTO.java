package com.ServiYa.serviya.modelos.modelos.contrato;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/** El orden de los campos debe coincidir con la expresión "new ..." de ContratoRepository. */
@Data
@NoArgsConstructor
public class ContratoResumenDTO {
	private Long idContrato;
	private String nombreContrato;
	private String nombreColaborador;
	private String nombreContratante;
	private String direccion;
	private String precio;
	private Date fechaInicio;
	private Date fechaFin;
	private EstadoContrato estado;
	private Date fechaRespuesta;

	/** Solo se calcula en el listado del contratante (ContratoService); por defecto false. */
	private boolean resenado;
	/** true si ya puede reseñarlo: aceptado, fecha de fin cumplida y sin reseña (PoliticaResena). */
	private boolean puedeResenar;

	/** Constructor de la expresión JPQL "new ..." de ContratoRepository (las banderas se calculan aparte). */
	public ContratoResumenDTO(Long idContrato, String nombreContrato, String nombreColaborador,
			String nombreContratante, String direccion, String precio, Date fechaInicio, Date fechaFin,
			EstadoContrato estado, Date fechaRespuesta) {
		this.idContrato = idContrato;
		this.nombreContrato = nombreContrato;
		this.nombreColaborador = nombreColaborador;
		this.nombreContratante = nombreContratante;
		this.direccion = direccion;
		this.precio = precio;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.fechaRespuesta = fechaRespuesta;
	}
}