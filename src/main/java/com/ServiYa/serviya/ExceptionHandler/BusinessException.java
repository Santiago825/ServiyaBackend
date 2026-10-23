package com.ServiYa.serviya.ExceptionHandler;

import com.ServiYa.serviya.util.ConstantesCodigosError;

/**
 * Error de negocio. Lleva un {@link TipoError} (no un estado HTTP): la capa de negocio no conoce
 * el transporte. Lo traduce GlobalExceptionHandler (REST) o ChatController (WebSocket) usando
 * {@link EstadoHttp}. Sin stack trace: es un flujo esperado, no un fallo.
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final TipoError tipo;
	private final String codigo;

	public BusinessException(TipoError tipo, String codigo, String mensaje) {
		super(mensaje, null, false, false);
		this.tipo = tipo;
		this.codigo = codigo;
	}

	public TipoError getTipo() {
		return tipo;
	}

	public String getCodigo() {
		return codigo;
	}

	// ───────────── fábricas de los errores más comunes ─────────────

	public static BusinessException validacion(String mensaje) {
		return new BusinessException(TipoError.VALIDACION, ConstantesCodigosError.CODIGO_VALIDACION, mensaje);
	}

	public static BusinessException noEncontrado(String mensaje) {
		return new BusinessException(TipoError.NO_ENCONTRADO, ConstantesCodigosError.CODIGO_RECURSO_NO_ENCONTRADO,
				mensaje);
	}

	public static BusinessException conflicto(String mensaje) {
		return new BusinessException(TipoError.CONFLICTO, ConstantesCodigosError.CODIGO_CONFLICTO, mensaje);
	}

	public static BusinessException prohibido(String mensaje) {
		return new BusinessException(TipoError.PROHIBIDO, ConstantesCodigosError.CODIGO_PROHIBIDO, mensaje);
	}
}
