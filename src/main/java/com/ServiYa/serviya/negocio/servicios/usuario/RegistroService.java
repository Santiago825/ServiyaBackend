package com.ServiYa.serviya.negocio.servicios.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.ExceptionHandler.TipoError;
import com.ServiYa.serviya.modelos.entity.municipio.Municipio;
import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.ServiYa.serviya.modelos.entity.servicio.Servicio;
import com.ServiYa.serviya.modelos.entity.tipoDocumento.TipoDocumento;
import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.auth.RegistroRequest;
import com.ServiYa.serviya.modelos.modelos.usuarios.Role;
import com.ServiYa.serviya.repository.personaRepository.PersonaRepository;
import com.ServiYa.serviya.repository.usuarioRepository.UserRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

/**
 * Alta de usuarios. Es TRANSACCIONAL: si falla el guardado del usuario no queda una Persona huérfana.
 * Lanza BusinessException ante duplicados; el controlador arma la respuesta.
 */
@Service
@RequiredArgsConstructor
public class RegistroService {

	private final UserRepository userRepository;
	private final PersonaRepository personaRepository;
	private final PasswordEncoder passwordEncoder;

	/** Lanza 409 si el username ya existe; si está libre, no hace nada. */
	@Transactional(readOnly = true)
	public void validarUsernameDisponible(String username) {
		if (userRepository.existsByUsername(username.trim())) {
			throw usernameExiste();
		}
	}

	@Transactional
	public void registrar(RegistroRequest request) {
		final String username = request.getUsername().trim();
		final String email = request.getEmail().trim();
		final Role rol = Role.valueOf(request.getRol().trim().toUpperCase());

		validarNoDuplicado(request, username, email, rol);

		// Los contratantes no tienen servicio (antes se forzaba el id 100, que exigía una
		// fila "comodín" en la tabla servicio o fallaba por la clave foránea).
		Servicio servicio = (rol == Role.COLABORADOR)
				? Servicio.builder().idServicio(request.getIdServicio()).build()
				: null;

		Persona persona = personaRepository.save(Persona.builder().nombre(request.getNombre().trim())
				.apellido(request.getApellido().trim()).telefono(request.getTelefono().trim())
				.documento(TipoDocumento.builder().idDocumento(request.getIdDocumento()).build())
				.servicio(servicio).municipio(Municipio.builder().idMunicipio(request.getIdMunicipio()).build())
				.numeroDocumento(request.getNumeroDocumento()).descripcion(request.getDescripcion())
				.foto(request.getFoto()).cv(request.getCv()).build());

		userRepository.save(Usuario.builder().username(username).email(email)
				.password(passwordEncoder.encode(request.getPassword())).role(rol).persona(persona).build());
	}

	private void validarNoDuplicado(RegistroRequest request, String username, String email, Role rol) {
		if (rol == Role.COLABORADOR && request.getIdServicio() == null) {
			throw BusinessException.validacion(ConstantesCodigosError.MENSAJE_SERVICIO_OBLIGATORIO);
		}
		if (userRepository.existsByUsername(username)) {
			throw usernameExiste();
		}
		if (userRepository.existsByEmailIgnoreCase(email)) {
			throw new BusinessException(TipoError.CONFLICTO, ConstantesCodigosError.CODIGO_CORREO_EXISTE,
					ConstantesCodigosError.MENSAJE_CORREO_EXISTE);
		}
		if (personaRepository.existsByDocumento_IdDocumentoAndNumeroDocumento(request.getIdDocumento(),
				request.getNumeroDocumento())) {
			throw new BusinessException(TipoError.CONFLICTO, ConstantesCodigosError.CODIGO_DOCUMENTO_EXISTE,
					ConstantesCodigosError.MENSAJE_DOCUMENTO_EXISTE);
		}
	}

	private static BusinessException usernameExiste() {
		return new BusinessException(TipoError.CONFLICTO, ConstantesCodigosError.CODIGO_USUARIO_EXISTE,
				ConstantesCodigosError.MENSAJE_REGISTRO_USUARIO_EXISTENTE);
	}
}
