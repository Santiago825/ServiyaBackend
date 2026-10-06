package com.ServiYa.serviya.negocio.servicios.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.auth.AuthResponse;
import com.ServiYa.serviya.modelos.modelos.auth.LoginRequest;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

/**
 * RESTAURADO y CORREGIDO. Antes:
 *  - hacía "new JwtService()" en vez de inyectarlo;
 *  - imprimía el Usuario completo (con el hash de la contraseña) por consola;
 *  - convertía CUALQUIER fallo (incluida contraseña errónea) en un 500 genérico.
 * Ahora credenciales inválidas => 401 con mensaje único (no revela si el usuario existe).
 */
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthResponse login(LoginRequest request) {
		Authentication auth;
		try {
			auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword()));
		} catch (AuthenticationException e) {
			throw new BusinessException(HttpStatus.UNAUTHORIZED, ConstantesCodigosError.CODIGO_LOGIN_INVALIDO,
					ConstantesCodigosError.MENSAJE_LOGIN_NO_ENOCNTRADO);
		}

		Usuario user = (Usuario) auth.getPrincipal();
		return AuthResponse.builder().idUsuario(user.getIdUsuario()).username(user.getUsername())
				.Rol(user.getRole().name()).registro_completo(user.getRegistro_completo())
				.idPersona(user.getPersona().getIdPersona())
				.token(jwtService.getToken(user)).codigoRespuesta(ConstantesCodigosError.CODIGO_EXITO)
				.mensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO).build();
	}
}
