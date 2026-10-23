package com.ServiYa.serviya.negocio.restController.seguimientoController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.servicio.RequestSeguirColaborador;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.seguimientoService.SeguimientoService;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;
import com.ServiYa.serviya.util.ReglasAcceso;

import lombok.RequiredArgsConstructor;

/** Seguir / dejar de seguir: solo CONTRATANTE, siempre en nombre del usuario del token. */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
@PreAuthorize(ReglasAcceso.SOLO_CONTRATANTE)
public class SeguimientoController {

	private final SeguimientoService seguimientoService;

	@PostMapping(ConstantesSeguridadPathRest.PATH_SEGUIR_COLABORADOR)
	public ResponseEntity<ListaDTO> seguirColaborador(@RequestBody RequestSeguirColaborador request,
			Authentication auth) {
		seguimientoService.seguir(auth.getName(), request.getColaborador());
		return RespuestasHttp.exito();
	}

	@PostMapping(ConstantesSeguridadPathRest.PATH_DEJAR_SEGUIR_COLABORADOR)
	public ResponseEntity<ListaDTO> dejarSeguirColaborador(@RequestBody RequestSeguirColaborador request,
			Authentication auth) {
		seguimientoService.dejarDeSeguir(auth.getName(), request.getColaborador());
		return RespuestasHttp.exito();
	}
}
