package com.ServiYa.serviya.negocio.servicios.usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.repository.usuarioRepository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Resuelve quién es el usuario autenticado (el username sale SIEMPRE del token, nunca del cliente).
 * Centraliza una búsqueda que estaba duplicada en varios servicios.
 */
@Service
@RequiredArgsConstructor
public class IdentidadService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public Long personaIdDe(String username) {
		return userRepository.findPersonaIdByUsername(username)
				.orElseThrow(() -> BusinessException.noEncontrado("Usuario no encontrado."));
	}
}
