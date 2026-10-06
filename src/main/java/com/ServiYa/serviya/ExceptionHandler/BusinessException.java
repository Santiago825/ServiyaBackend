package com.ServiYa.serviya.ExceptionHandler;

import org.springframework.http.HttpStatus;

/**
 * Error de negocio con el estado HTTP que le corresponde. Lo traduce
 * GlobalExceptionHandler (REST) o ChatController (WebSocket).
 * Sin stack trace: es un flujo esperado, no un fallo.
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus status;
	private final String codigo;

	public BusinessException(HttpStatus status, String codigo, String mensaje) {
		super(mensaje, null, false, false);
		this.status = status;
		this.codigo = codigo;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCodigo() {
		return codigo;
	}
}
