package com.ServiYa.serviya.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ServiYa.serviya.modelos.modelos.error.ErrorResponse;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Errores que ocurren DENTRO de la cadena de filtros de Spring Security (401/403): ahí no
 * llega @RestControllerAdvice, así que se responden aquí, también en JSON estándar.
 * Se distingue "sesión expirada" de "no autenticado" para que el frontend decida si
 * redirige al login.
 */
@Component
@RequiredArgsConstructor
public class RestSecurityErrorHandlers implements AuthenticationEntryPoint, AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
			throws IOException {
		boolean expirado = "EXPIRED".equals(request.getAttribute("jwt.error"));
		escribir(request, response, HttpStatus.UNAUTHORIZED,
				expirado ? ConstantesCodigosError.CODIGO_SESION_EXPIRADA : ConstantesCodigosError.CODIGO_NO_AUTENTICADO,
				expirado ? ConstantesCodigosError.MENSAJE_SESION_EXPIRADA : ConstantesCodigosError.MENSAJE_NO_AUTENTICADO);
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException {
		escribir(request, response, HttpStatus.FORBIDDEN, ConstantesCodigosError.CODIGO_PROHIBIDO,
				ConstantesCodigosError.MENSAJE_PROHIBIDO);
	}

	private void escribir(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String codigo,
			String mensaje) throws IOException {
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		objectMapper.writeValue(response.getOutputStream(),
				ErrorResponse.builder().codigoRespuesta(codigo).mensajeRespuesta(mensaje).status(status.value())
						.path(request.getRequestURI()).timestamp(Instant.now()).build());
	}
}
