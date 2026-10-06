package com.ServiYa.serviya.modelos.modelos.error;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cuerpo estándar de TODOS los errores del API. Conserva los nombres
 * codigoRespuesta / mensajeRespuesta que ya lee el frontend.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private int status;
	private String path;
	private Instant timestamp;
	/** Errores por campo (solo en validaciones): campo -> mensaje. */
	private Map<String, String> errores;
}
