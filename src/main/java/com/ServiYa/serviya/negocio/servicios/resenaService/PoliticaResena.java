package com.ServiYa.serviya.negocio.servicios.resenaService;

import java.sql.Date;
import java.time.LocalDate;

import com.ServiYa.serviya.modelos.modelos.contrato.EstadoContrato;

/**
 * Regla ÚNICA de cuándo se puede reseñar un contrato. La usan el listado (para habilitar el botón)
 * y el servicio (para validar al guardar), de modo que ambos nunca se contradigan.
 *
 * Se puede reseñar cuando el contrato fue ACEPTADO, ya se cumplió su fecha de fin (hoy es igual o
 * posterior) y todavía no tiene reseña.
 */
public final class PoliticaResena {

	private PoliticaResena() {
	}

	public static boolean fechaFinCumplida(Date fechaFin, LocalDate hoy) {
		return fechaFin != null && !hoy.isBefore(fechaFin.toLocalDate());
	}

	public static boolean puedeResenar(EstadoContrato estado, Date fechaFin, boolean yaResenado, LocalDate hoy) {
		return estado == EstadoContrato.ACEPTADO && fechaFinCumplida(fechaFin, hoy) && !yaResenado;
	}
}
