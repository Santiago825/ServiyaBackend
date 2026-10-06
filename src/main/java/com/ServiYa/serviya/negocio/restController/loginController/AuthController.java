package com.ServiYa.serviya.negocio.restController.loginController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.auth.AuthResponse;
import com.ServiYa.serviya.modelos.modelos.auth.LoginRequest;
import com.ServiYa.serviya.negocio.servicios.auth.AuthService;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Login. Sin try/catch: los errores (401 credenciales inválidas, 400 validación) los
 * traduce GlobalExceptionHandler. Sin @CrossOrigin: CORS es global (SecurityConfig).
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PUBLICOS)
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping(ConstantesSeguridadPathRest.PATH_LOGIN)
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}
}
