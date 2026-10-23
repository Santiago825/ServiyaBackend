package com.ServiYa.serviya.ExceptionHandler;

/**
 * Clasificación de un error de negocio, independiente del transporte (HTTP, WebSocket...).
 * La capa de negocio solo dice QUÉ pasó; cómo se comunica (estado HTTP, frame STOMP) lo decide
 * la capa web. Ver {@link EstadoHttp}.
 */
public enum TipoError {
	VALIDACION, NO_AUTENTICADO, PROHIBIDO, NO_ENCONTRADO, CONFLICTO, INTERNO
}
