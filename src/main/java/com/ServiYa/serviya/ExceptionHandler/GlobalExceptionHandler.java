package com.ServiYa.serviya.ExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.ServiYa.serviya.modelos.modelos.error.ErrorResponse;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

/**
 * Manejo global de excepciones. REESCRITO: el anterior tenía un método sin
 * @ExceptionHandler (código muerto) y devolvía texto plano. Ahora todo error sale como
 * ErrorResponse JSON (codigoRespuesta / mensajeRespuesta, que ya lee el frontend), con el
 * estado HTTP correcto. Los errores inesperados se registran con stack trace pero al
 * cliente solo llega un mensaje genérico (no se filtran detalles internos).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> negocio(BusinessException ex, HttpServletRequest req) {
		return build(ex.getStatus(), ex.getCodigo(), ex.getMessage(), req, null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> validacion(MethodArgumentNotValidException ex, HttpServletRequest req) {
		Map<String, String> errores = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(fe -> errores.putIfAbsent(fe.getField(), fe.getDefaultMessage()));
		ex.getBindingResult().getGlobalErrors().forEach(ge -> errores.putIfAbsent(ge.getObjectName(), ge.getDefaultMessage()));
		String detalle = errores.values().stream().findFirst().orElse(ConstantesCodigosError.MENSAJE_VALIDACION);
		return build(HttpStatus.BAD_REQUEST, ConstantesCodigosError.CODIGO_VALIDACION, detalle, req, errores);
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class, MissingServletRequestParameterException.class,
			MethodArgumentTypeMismatchException.class, IllegalArgumentException.class })
	public ResponseEntity<ErrorResponse> solicitudInvalida(Exception ex, HttpServletRequest req) {
		log.debug("Solicitud inválida en {}: {}", req.getRequestURI(), ex.getMessage());
		return build(HttpStatus.BAD_REQUEST, ConstantesCodigosError.CODIGO_SOLICITUD_INVALIDA,
				"La solicitud es inválida o está incompleta.", req, null);
	}

	/** Validación de parámetros simples (@RequestParam/@PathVariable con constraints). */
	@ExceptionHandler({ HandlerMethodValidationException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorResponse> validacionParametros(Exception ex, HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, ConstantesCodigosError.CODIGO_VALIDACION,
				ConstantesCodigosError.MENSAJE_VALIDACION, req, null);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> noAutenticado(AuthenticationException ex, HttpServletRequest req) {
		return build(HttpStatus.UNAUTHORIZED, ConstantesCodigosError.CODIGO_NO_AUTENTICADO,
				ConstantesCodigosError.MENSAJE_NO_AUTENTICADO, req, null);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> prohibido(AccessDeniedException ex, HttpServletRequest req) {
		return build(HttpStatus.FORBIDDEN, ConstantesCodigosError.CODIGO_PROHIBIDO,
				ConstantesCodigosError.MENSAJE_PROHIBIDO, req, null);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> integridad(DataIntegrityViolationException ex, HttpServletRequest req) {
		log.warn("Violación de integridad en {}: {}", req.getRequestURI(), ex.getMostSpecificCause().getMessage());
		return build(HttpStatus.CONFLICT, ConstantesCodigosError.CODIGO_CONFLICTO,
				"Los datos entran en conflicto con registros existentes o referencian datos que no existen.", req,
				null);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> noEncontrado(NoResourceFoundException ex, HttpServletRequest req) {
		return build(HttpStatus.NOT_FOUND, ConstantesCodigosError.CODIGO_RECURSO_NO_ENCONTRADO,
				ConstantesCodigosError.MENSAJE_RECURSO_NO_ENCONTRADO, req, null);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> metodoNoPermitido(HttpRequestMethodNotSupportedException ex,
			HttpServletRequest req) {
		return build(HttpStatus.METHOD_NOT_ALLOWED, ConstantesCodigosError.CODIGO_SOLICITUD_INVALIDA,
				"Método HTTP no permitido para este recurso.", req, null);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> inesperado(Exception ex, HttpServletRequest req) {
		log.error("Error no controlado en {} {}", req.getMethod(), req.getRequestURI(), ex);
		return build(HttpStatus.INTERNAL_SERVER_ERROR, ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO,
				ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO, req, null);
	}

	private ResponseEntity<ErrorResponse> build(HttpStatus status, String codigo, String mensaje,
			HttpServletRequest req, Map<String, String> errores) {
		ErrorResponse body = ErrorResponse.builder().codigoRespuesta(codigo).mensajeRespuesta(mensaje)
				.status(status.value()).path(req.getRequestURI()).timestamp(Instant.now()).errores(errores).build();
		return ResponseEntity.status(status).body(body);
	}
}
