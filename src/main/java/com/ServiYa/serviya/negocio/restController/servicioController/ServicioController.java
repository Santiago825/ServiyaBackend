package com.ServiYa.serviya.negocio.restController.servicioController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.servicios.ServicioService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/** Catálogo de servicios. Colaboradores y seguimiento tienen sus propios controladores. */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ServicioController {

	private final ServicioService servicioService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_SERVICIOS)
	public ResponseEntity<ListaDTO> getServicios() {
		return RespuestasHttp.lista(servicioService.listar(), ConstantesCodigosError.MENSAJE_SERVICIOS_NO_ENCONTRADO);
	}

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_SERVICIOS_MAS_USOS)
	public ResponseEntity<ListaDTO> getServiciosMasUsos() {
		return RespuestasHttp.lista(servicioService.masUsados(),
				ConstantesCodigosError.MENSAJE_SERVICIOS_NO_ENCONTRADO);
	}
}
