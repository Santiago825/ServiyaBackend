package com.ServiYa.serviya.negocio.restController.departamentoController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.servicios.departamentoService.DepartamentoService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * RESTAURADO. El archivo estaba guardado en UTF-16 (javac no lo lee bien); ahora UTF-8.
 * Misma ruta y mismo contrato que antes: OK -> 200, cualquier otro código -> 500.
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class DepartamentoController {

	private final DepartamentoService departamentoService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_DEPARTAMENTO)
	public ResponseEntity<ListaDTO> getDepartamentos() {
		ListaDTO respuesta = departamentoService.getDepartamento();
		if (ConstantesCodigosError.CODIGO_EXITO.equals(respuesta.getCodigoRespuesta())) {
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
			return ResponseEntity.ok(respuesta);
		}
		if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().isBlank()) {
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	}
}
