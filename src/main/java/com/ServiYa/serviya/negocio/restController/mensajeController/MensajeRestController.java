package com.ServiYa.serviya.negocio.restController.mensajeController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.mensaje.MensajePaginaDTO;
import com.ServiYa.serviya.negocio.servicios.mensajeService.MensajeService;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * Historial del chat con paginación por cursor.
 *
 *   GET /private/obtener_mensajes/{interlocutor}                  -> los 30 más recientes
 *   GET /private/obtener_mensajes/{interlocutor}?beforeId=123     -> los 30 anteriores al id 123
 *   GET /private/obtener_mensajes/{interlocutor}?beforeId=123&size=50
 *
 * La URL anterior (/{user1}/{user2}) permitía a cualquier usuario leer la conversación de
 * otros dos. Ahora uno de los participantes es SIEMPRE el usuario del token.
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class MensajeRestController {

	private final MensajeService mensajeService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_MENSAJES + "/{interlocutor}")
	public ResponseEntity<MensajePaginaDTO> obtenerMensajes(@PathVariable String interlocutor,
			@RequestParam(required = false) Long beforeId, @RequestParam(required = false) Integer size,
			Authentication authentication) {
		return ResponseEntity
				.ok(mensajeService.obtenerConversacion(authentication.getName(), interlocutor, beforeId, size));
	}
}
