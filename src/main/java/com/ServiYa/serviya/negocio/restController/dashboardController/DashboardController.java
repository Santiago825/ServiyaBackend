package com.ServiYa.serviya.negocio.restController.dashboardController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.dashboard.DashboardColaboradorDTO;
import com.ServiYa.serviya.negocio.servicios.dashboardService.DashboardColaboradorService;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;
import com.ServiYa.serviya.util.ReglasAcceso;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardColaboradorService dashboardService;

	/** COLABORADOR: resumen de su actividad para la pantalla de inicio. */
	@GetMapping(ConstantesSeguridadPathRest.PATH_DASHBOARD_COLABORADOR)
	@PreAuthorize(ReglasAcceso.SOLO_COLABORADOR)
	public ResponseEntity<DashboardColaboradorDTO> dashboardColaborador(Authentication auth) {
		return ResponseEntity.ok(dashboardService.resumen(auth.getName()));
	}
}
