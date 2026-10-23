package com.ServiYa.serviya.negocio.restController.archivoController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.archivoService.ArchivoService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ArchivoController {

	private final ArchivoService archivoService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_CV + "/{id}")
	public ResponseEntity<ListaDTO> obtenerCv(@PathVariable("id") Long id) {
		return RespuestasHttp.lista(archivoService.obtenerCv(id),
				ConstantesCodigosError.MENSAJE_ARCHIVO_CV_NO_ENCONTRADO);
	}

	/** Solo las dos partes del contrato pueden descargar su archivo. */
	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_ARCHIVO_CONTRATO + "/{id}")
	public ResponseEntity<ListaDTO> obtenerContrato(@PathVariable("id") Long id, Authentication auth) {
		return RespuestasHttp.lista(archivoService.obtenerContrato(id, auth.getName()),
				ConstantesCodigosError.MENSAJE_ARCHIVO_CONTRATO_NO_ENCONTRADO);
	}
}
