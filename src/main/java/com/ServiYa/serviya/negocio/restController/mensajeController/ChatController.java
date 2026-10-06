package com.ServiYa.serviya.negocio.restController.mensajeController;

import java.security.Principal;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.modelos.error.ErrorResponse;
import com.ServiYa.serviya.modelos.modelos.mensaje.MensajeEntranteDTO;
import com.ServiYa.serviya.modelos.modelos.mensaje.MessageDTO;
import com.ServiYa.serviya.negocio.servicios.mensajeService.MensajeService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * Chat en tiempo real (STOMP).
 *
 * Cliente -> SEND  /app/private-message   {receiverName, message}
 * Servidor-> /user/queue/messages         (al destinatario Y al remitente: el eco trae el id
 *                                           y la hora reales guardados en la base de datos)
 * Servidor-> /user/queue/errors           (solo al remitente, si el mensaje fue inválido)
 *
 * El remitente sale del Principal (token JWT validado en WebSocketAuthInterceptor).
 */
@Controller
@RequiredArgsConstructor
public class ChatController {

	private static final Logger log = LoggerFactory.getLogger(ChatController.class);

	private final SimpMessagingTemplate messagingTemplate;
	private final MensajeService mensajeService;

	@MessageMapping(ConstantesSeguridadPathRest.WS_DESTINO_ENVIAR)
	public void enviarMensajePrivado(@Payload MensajeEntranteDTO payload, Principal principal) {
		MessageDTO guardado = mensajeService.guardar(principal.getName(), payload.getReceiverName(),
				payload.getMessage());

		messagingTemplate.convertAndSendToUser(guardado.getReceiverName(),
				ConstantesSeguridadPathRest.WS_COLA_MENSAJES, guardado);
		messagingTemplate.convertAndSendToUser(guardado.getSenderName(),
				ConstantesSeguridadPathRest.WS_COLA_MENSAJES, guardado);
	}

	@MessageExceptionHandler
	@SendToUser(ConstantesSeguridadPathRest.WS_COLA_ERRORES)
	public ErrorResponse manejarError(Exception ex) {
		if (ex instanceof BusinessException be) {
			return ErrorResponse.builder().codigoRespuesta(be.getCodigo()).mensajeRespuesta(be.getMessage())
					.status(be.getStatus().value()).timestamp(Instant.now()).build();
		}
		log.error("Error no controlado en el chat", ex);
		return ErrorResponse.builder().codigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO)
				.mensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO).status(500)
				.timestamp(Instant.now()).build();
	}
}
