package com.ServiYa.serviya.negocio.restController.usuarioController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.modelos.auth.RegistroRequest;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.servicios.usuario.RegistroService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;
import com.ServiYa.serviya.util.ListaDTOs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Registro. Errores (duplicados, validación) => GlobalExceptionHandler. */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PUBLICOS)
@RequiredArgsConstructor
public class RegistroController {

	private final RegistroService registroService;

	/** 200 = disponible; 409 = ya existe. */
	@GetMapping(ConstantesSeguridadPathRest.PATH_VALIDAR_USERNAME)
	public ResponseEntity<ListaDTO> validarUsername(@RequestParam("username") String username) {
		if (username == null || username.isBlank()) {
			throw BusinessException.validacion("El usuario es obligatorio");
		}
		registroService.validarUsernameDisponible(username);
		return ResponseEntity.ok(ListaDTOs.okSinDatos());
	}

	@PostMapping(ConstantesSeguridadPathRest.PATH_REGISTRO)
	public ResponseEntity<ListaDTO> registroUsuario(@Valid @RequestBody RegistroRequest request) {
		registroService.registrar(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ListaDTOs.ok(ConstantesCodigosError.MENSAJE_REGISTRO_EXITOSO));
	}
}
