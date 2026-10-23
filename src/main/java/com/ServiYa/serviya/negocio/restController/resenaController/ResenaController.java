package com.ServiYa.serviya.negocio.restController.resenaController;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.resena.ResenaRecibidaDTO;
import com.ServiYa.serviya.modelos.modelos.resena.ResenarContratoRequest;
import com.ServiYa.serviya.negocio.servicios.resenaService.ResenaService;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;
import com.ServiYa.serviya.util.ListaDTOs;
import com.ServiYa.serviya.util.ReglasAcceso;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ResenaController {

	private final ResenaService resenaService;

	/**
	 * COLABORADOR: reseñas que ha recibido, de la más reciente a la más antigua.
	 * q = texto a buscar (contratante, contrato, descripción o comentario); page base 0; size 1..50.
	 */
	@GetMapping(ConstantesSeguridadPathRest.PATH_MIS_RESENAS_COLABORADOR)
	@PreAuthorize(ReglasAcceso.SOLO_COLABORADOR)
	public ResponseEntity<ListaDTO> misResenas(
			@RequestParam(value = "q", required = false, defaultValue = "") String q,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, Authentication auth) {
		Page<ResenaRecibidaDTO> resultado = resenaService.recibidas(auth.getName(), q, page, size);
		return ResponseEntity.ok(ListaDTOs.pagina(resultado.getContent(), resultado.getTotalElements(),
				resultado.getTotalPages(), "No se encontraron reseñas."));
	}

	/** CONTRATANTE: reseña el contrato (aceptado, con fecha de fin cumplida y sin reseña previa). */
	@PostMapping(ConstantesSeguridadPathRest.PATH_RESENAR_CONTRATO + "/{id}")
	@PreAuthorize(ReglasAcceso.SOLO_CONTRATANTE)
	public ResponseEntity<ListaDTO> resenar(@PathVariable("id") Long id,
			@Valid @RequestBody ResenarContratoRequest request, Authentication auth) {
		resenaService.resenar(auth.getName(), id, request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ListaDTOs.ok("Reseña registrada. ¡Gracias por tu opinión!"));
	}
}
