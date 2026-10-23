package com.ServiYa.serviya.modelos.modelos.contrato;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDetalleDTO {
	private Long idContrato;
	private String nombreContrato;
	private String nombreColaborador;
	private String nombreContratante;
	private String direccion;
	private String telefono;
	private Date fechaInicio;
	private Date fechaFin;
	private String precio;
	private String descripcion;
	private String nombreArchivo;
	private EstadoContrato estado;
	private Date fechaRespuesta;
	private byte[] foto; // foto del colaborador
}