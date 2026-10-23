package com.ServiYa.serviya.negocio.servicios.dashboardService;

import java.time.LocalDate;

import com.ServiYa.serviya.modelos.modelos.contrato.EstadoContrato;

/** Lo que el cálculo de estadísticas necesita de un contrato (desligado de las entidades y de JPA). */
public record DatoContrato(Long idContrato, String nombreContrato, String nombreContratante, EstadoContrato estado,
		String precio, LocalDate inicio, LocalDate fin) {
}
