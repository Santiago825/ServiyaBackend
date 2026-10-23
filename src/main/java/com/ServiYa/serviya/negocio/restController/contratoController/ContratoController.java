package com.ServiYa.serviya.negocio.restController.contratoController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.contratoService.ContratoService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;
import com.ServiYa.serviya.util.ListaDTOs;
import com.ServiYa.serviya.util.ReglasAcceso;

import lombok.RequiredArgsConstructor;

/**
 * CONSULTAS de contratos (los comandos están en ContratoGestionController). Todas devuelven solo lo
 * que le pertenece al usuario del token.
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ContratoController {

	private final ContratoService contratoService;

	// ───────────── endpoints históricos (misma respuesta de siempre) ─────────────

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_CONTRATOS + "/{id}")
	public ResponseEntity<ListaDTO> obtenerContratos(@PathVariable("id") Long id, Authentication auth) {
		return RespuestasHttp.lista(contratoService.obtenerContratos(id, auth.getName()),
				ConstantesCodigosError.MENSAJE_CONTRATOS_NO_ENCONTRADO);
	}

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_CONTRATOS_DETALLE + "/{id}")
	public ResponseEntity<ListaDTO> obtenerContratosDetalle(@PathVariable("id") Long id, Authentication auth) {
		return RespuestasHttp.lista(contratoService.obtenerContratoDetalle(id, auth.getName()),
				ConstantesCodigosError.MENSAJE_DETALLE_CONTRATOS_NO_ENCONTRADO);
	}

	// ───────────── módulo de contratos ─────────────

	/** COLABORADOR: contratos que creó, con su estado. */
	@GetMapping(ConstantesSeguridadPathRest.PATH_MIS_CONTRATOS_COLABORADOR)
	@PreAuthorize(ReglasAcceso.SOLO_COLABORADOR)
	public ResponseEntity<ListaDTO> misContratosColaborador(Authentication auth) {
		return ResponseEntity.ok(ListaDTOs.de(contratoService.listarDelColaborador(auth.getName()),
				ConstantesCodigosError.MENSAJE_CONTRATOS_NO_ENCONTRADO));
	}

	/** CONTRATANTE: contratos que le crearon los colaboradores. */
	@GetMapping(ConstantesSeguridadPathRest.PATH_MIS_CONTRATOS)
	@PreAuthorize(ReglasAcceso.SOLO_CONTRATANTE)
	public ResponseEntity<ListaDTO> misContratos(Authentication auth) {
		return ResponseEntity.ok(ListaDTOs.de(contratoService.listarDelContratante(auth.getName()),
				ConstantesCodigosError.MENSAJE_CONTRATOS_NO_ENCONTRADO));
	}

	/** Detalle: solo lo ven las dos partes del contrato. */
	@GetMapping(ConstantesSeguridadPathRest.PATH_CONTRATO_DETALLE + "/{id}")
	public ResponseEntity<ListaDTO> detalle(@PathVariable("id") Long id, Authentication auth) {
		return ResponseEntity.ok(ListaDTOs.de(List.of(contratoService.detalle(id, auth.getName())),
				ConstantesCodigosError.MENSAJE_DETALLE_CONTRATOS_NO_ENCONTRADO));
	}
}
