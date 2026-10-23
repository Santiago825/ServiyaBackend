package com.ServiYa.serviya.ExceptionHandler;

import org.springframework.http.HttpStatus;

/** Traduce un {@link TipoError} de negocio al estado HTTP que le corresponde (solo capa web). */
public final class EstadoHttp {

	private EstadoHttp() {
	}

	public static HttpStatus de(TipoError tipo) {
		return switch (tipo) {
		case VALIDACION -> HttpStatus.BAD_REQUEST;
		case NO_AUTENTICADO -> HttpStatus.UNAUTHORIZED;
		case PROHIBIDO -> HttpStatus.FORBIDDEN;
		case NO_ENCONTRADO -> HttpStatus.NOT_FOUND;
		case CONFLICTO -> HttpStatus.CONFLICT;
		case INTERNO -> HttpStatus.INTERNAL_SERVER_ERROR;
		};
	}
}
