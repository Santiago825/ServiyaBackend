package com.ServiYa.serviya.negocio.restController.presenciaController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.presencia.PresenciaSnapshotDTO;
import com.ServiYa.serviya.negocio.servicios.presenciaService.PresenciaService;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * GET /private/obtener_presencia -> contactos en linea ahora. El cliente lo pide justo despues de
 * suscribirse a /user/queue/presence; los cambios posteriores llegan por el WebSocket.
 * El usuario sale del token, no de un parametro.
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class PresenciaRestController {

	private final PresenciaService presenciaService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_PRESENCIA)
	public ResponseEntity<PresenciaSnapshotDTO> obtenerPresencia(Authentication authentication) {
		return ResponseEntity
				.ok(new PresenciaSnapshotDTO(presenciaService.enLineaEntreContactos(authentication.getName())));
	}
}
