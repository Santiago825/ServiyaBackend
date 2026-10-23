package com.ServiYa.serviya.negocio.servicios.presenciaService;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.ServiYa.serviya.modelos.modelos.presencia.PresenciaDTO;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/** Implementación por WebSocket/STOMP: cola personal /user/queue/presence. */
@Component
@RequiredArgsConstructor
public class StompNotificadorPresencia implements NotificadorPresencia {

	private final SimpMessagingTemplate messagingTemplate;

	@Override
	public void notificar(String destinatario, PresenciaDTO evento) {
		messagingTemplate.convertAndSendToUser(destinatario, ConstantesSeguridadPathRest.WS_COLA_PRESENCIA, evento);
	}
}
