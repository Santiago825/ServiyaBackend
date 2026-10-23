package com.ServiYa.serviya.modelos.modelos.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.ServiYa.serviya.modelos.modelos.resena.ResenaRecibidaDTO;

/**
 * Resumen de la actividad de un colaborador para su pantalla de inicio (GET dashboard_colaborador).
 * Todo se calcula para el colaborador del token.
 */
public record DashboardColaboradorDTO(
		Contratos contratos,
		Ingresos ingresos,
		Resenas resenas,
		long seguidores,
		List<ContratoProximo> proximos,
		List<ResenaRecibidaDTO> ultimasResenas) {

	/**
	 * Contratos por estado. "Aceptados" se reparte según la fecha: en curso (hoy está entre inicio y
	 * fin), por iniciar (empieza después de hoy) y finalizados (ya pasó la fecha fin).
	 * tasaAceptacion = aceptados / (aceptados + rechazados) en %, null si aún no hay respuestas.
	 */
	public record Contratos(long total, long pendientes, long aceptados, long rechazados, long enCurso,
			long porIniciar, long finalizados, Double tasaAceptacion) {
	}

	/** Valor de los contratos ACEPTADOS (suma de "precio"). mesActual y porMes usan la fecha de inicio. */
	public record Ingresos(BigDecimal totalAceptado, BigDecimal mesActual, List<MesIngreso> porMes) {
	}

	/** mes en formato "2026-10". Incluye los meses sin contratos (total 0). */
	public record MesIngreso(String mes, BigDecimal total, long contratos) {
	}

	/** distribucion[0] = cantidad de reseñas de 1 estrella ... distribucion[4] = de 5 estrellas. */
	public record Resenas(long total, Double promedio, List<Long> distribucion) {
	}

	/** Contrato aceptado que está en curso o por iniciar. */
	public record ContratoProximo(Long idContrato, String nombreContrato, String nombreContratante,
			LocalDate fechaInicio, LocalDate fechaFin) {
	}
}
