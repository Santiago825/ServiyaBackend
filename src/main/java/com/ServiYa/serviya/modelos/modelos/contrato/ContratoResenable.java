package com.ServiYa.serviya.modelos.modelos.contrato;

import java.sql.Date;

/** Lo mínimo de un contrato para decidir si se puede reseñar (sin cargar el archivo adjunto). */
public interface ContratoResenable {

	EstadoContrato getEstado();

	Date getFechaFin();

	Long getIdColaborador();
}
