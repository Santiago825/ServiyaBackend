package com.ServiYa.serviya.negocio.restController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ListaDTOs;

/**
 * ÚNICO lugar donde se traduce el resultado de una consulta de lista a respuesta HTTP. Reemplaza el
 * bloque try/catch/if-else que estaba copiado en cada controlador.
 *
 * Mantiene el contrato histórico con el frontend: con datos -> 200; sin datos -> {@link #ESTADO_SIN_DATOS}
 * con el código INF_DAT_NO_ENC y su mensaje. Los errores (excepciones) los atiende GlobalExceptionHandler.
 */
public final class RespuestasHttp {

	/**
	 * Estado cuando una consulta no devuelve datos. Es 500 por compatibilidad con el frontend actual.
	 * Si el frontend se adapta, basta cambiarlo aquí (p. ej. a OK, como hace ContratoController en
	 * los endpoints "mis_contratos").
	 */
	static final HttpStatus ESTADO_SIN_DATOS = HttpStatus.INTERNAL_SERVER_ERROR;

	private RespuestasHttp() {
	}

	public static ResponseEntity<ListaDTO> lista(List<?> datos, String mensajeSiVacia) {
		ListaDTO respuesta = ListaDTOs.de(datos, mensajeSiVacia);
		if (ConstantesCodigosError.CODIGO_EXITO.equals(respuesta.getCodigoRespuesta())) {
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
			return ResponseEntity.ok(respuesta);
		}
		if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().isBlank()) {
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
		}
		return ResponseEntity.status(ESTADO_SIN_DATOS).body(respuesta);
	}

	/** Operación (seguir, dejar de seguir...) que terminó bien y no devuelve datos. */
	public static ResponseEntity<ListaDTO> exito() {
		return ResponseEntity.ok(ListaDTOs.ok(ConstantesCodigosError.MENSAJE_CODIGO_EXITO));
	}
}
