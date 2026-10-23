package com.ServiYa.serviya.negocio.restController.contratoController;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.ExceptionHandler.TipoError;
import com.ServiYa.serviya.modelos.modelos.archivo.ArchivoAdjunto;
import com.ServiYa.serviya.modelos.modelos.contrato.CrearContratoRequest;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.servicios.contratoService.ContratoGestionService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;
import com.ServiYa.serviya.util.ListaDTOs;
import com.ServiYa.serviya.util.ReglasAcceso;

import lombok.RequiredArgsConstructor;

/**
 * COMANDOS sobre contratos (crear, aceptar, rechazar). Las consultas están en ContratoController.
 * Este es el único lugar que toca el MultipartFile: lo convierte en ArchivoAdjunto para que el
 * servicio no dependa de la capa web.
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ContratoGestionController {

	private final ContratoGestionService service;

	/** COLABORADOR: crea un contrato PENDIENTE (multipart: campos + archivo opcional). */
	@PostMapping(value = ConstantesSeguridadPathRest.PATH_CREAR_CONTRATO, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize(ReglasAcceso.SOLO_COLABORADOR)
	public ResponseEntity<ListaDTO> crear(@ModelAttribute CrearContratoRequest request,
			@RequestParam(value = "archivo", required = false) MultipartFile archivo, Authentication auth) {
		service.crear(auth.getName(), request, aAdjunto(archivo));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ListaDTOs.ok("Contrato enviado. El contratante podrá aceptarlo o rechazarlo."));
	}

	/** CONTRATANTE: acepta. */
	@PostMapping(ConstantesSeguridadPathRest.PATH_ACEPTAR_CONTRATO + "/{id}")
	@PreAuthorize(ReglasAcceso.SOLO_CONTRATANTE)
	public ResponseEntity<ListaDTO> aceptar(@PathVariable("id") Long id, Authentication auth) {
		service.responder(id, auth.getName(), true);
		return ResponseEntity.ok(ListaDTOs.ok("Contrato aceptado."));
	}

	/** CONTRATANTE: rechaza. */
	@PostMapping(ConstantesSeguridadPathRest.PATH_RECHAZAR_CONTRATO + "/{id}")
	@PreAuthorize(ReglasAcceso.SOLO_CONTRATANTE)
	public ResponseEntity<ListaDTO> rechazar(@PathVariable("id") Long id, Authentication auth) {
		service.responder(id, auth.getName(), false);
		return ResponseEntity.ok(ListaDTOs.ok("Contrato rechazado."));
	}

	/** El tamaño ya está acotado por spring.servlet.multipart.max-file-size (5 MB). */
	private static ArchivoAdjunto aAdjunto(MultipartFile archivo) {
		if (archivo == null || archivo.isEmpty()) {
			return null;
		}
		try {
			return new ArchivoAdjunto(archivo.getBytes(), archivo.getOriginalFilename(), archivo.getContentType());
		} catch (IOException e) {
			throw new BusinessException(TipoError.INTERNO, ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO,
					"No se pudo leer el archivo adjunto.");
		}
	}
}
