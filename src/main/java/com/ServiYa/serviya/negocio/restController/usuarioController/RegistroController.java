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
import com.ServiYa.serviya.negocio.servicios.usuario.UserService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Registro. Se eliminó el System.out.println(request), que volcaba a consola los datos
 * del registro (incluida la contraseña). Errores => GlobalExceptionHandler.
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PUBLICOS)
@RequiredArgsConstructor
public class RegistroController {

	private final UserService userService;

	/** 200 = disponible; 409 = ya existe. */
	@GetMapping(ConstantesSeguridadPathRest.PATH_VALIDAR_USERNAME)
	public ResponseEntity<ListaDTO> validarUsername(@RequestParam String username) {
		if (username == null || username.isBlank()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, ConstantesCodigosError.CODIGO_VALIDACION,
					"El usuario es obligatorio");
		}
		return ResponseEntity.ok(userService.validarUsername(username));
	}

	@PostMapping(ConstantesSeguridadPathRest.PATH_REGISTRO)
	public ResponseEntity<ListaDTO> registroUsuario(@Valid @RequestBody RegistroRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registrarUsuario(request));
	}
}
