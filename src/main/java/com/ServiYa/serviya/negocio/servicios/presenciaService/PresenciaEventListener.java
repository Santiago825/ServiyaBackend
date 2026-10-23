package com.ServiYa.serviya.negocio.servicios.presenciaService;

import java.security.Principal;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;

/**
 * Traduce los eventos del ciclo de vida STOMP a presencia. SessionDisconnectEvent se publica tanto
 * en un DISCONNECT limpio como cuando el socket se cae o fallan los heartbeats.
 */
@Component
@RequiredArgsConstructor
public class PresenciaEventListener {

	private final PresenciaService presenciaService;

	@EventListener
	public void alConectar(SessionConnectedEvent event) {
		Principal usuario = event.getUser();
		String sessionId = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
		if (usuario != null && sessionId != null) {
			presenciaService.sesionIniciada(sessionId, usuario.getName());
		}
	}

	@EventListener
	public void alDesconectar(SessionDisconnectEvent event) {
		presenciaService.sesionTerminada(event.getSessionId());
	}
}
