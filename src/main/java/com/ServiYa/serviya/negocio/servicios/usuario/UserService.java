package com.ServiYa.serviya.negocio.servicios.usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.entity.municipio.Municipio;
import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.ServiYa.serviya.modelos.entity.servicio.Servicio;
import com.ServiYa.serviya.modelos.entity.tipoDocumento.TipoDocumento;
import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.auth.RegistroRequest;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.Role;
import com.ServiYa.serviya.modelos.modelos.usuarios.UserDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioDetalle;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioSeguidoDTO;
import com.ServiYa.serviya.repository.personaRepository.PersonaRepository;
import com.ServiYa.serviya.repository.usuarioRepository.UserRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ListaDTOs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Se reescribe para: (1) quitar los System.out de depuración, (2) eliminar el bloque
 * try/catch/else repetido 5 veces, (3) hacer el registro TRANSACCIONAL (antes, si fallaba
 * el guardado del usuario quedaba una Persona huérfana) y (4) validar duplicados.
 * Los métodos de lectura conservan firma y comportamiento.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PersonaRepository personaRepository;
	private final PasswordEncoder passwordEncoder;

	public ListaDTO getUsuarios() {
		try {
			List<UserDTO> listado = userRepository.findAllUser();
			return ListaDTOs.de(listado, ConstantesCodigosError.MENSAJE_USUARIOS_NO_ENCONTRADOS);
		} catch (Exception e) {
			log.error("Error al listar usuarios", e);
			return ListaDTOs.errorNoControlado();
		}
	}

	/**
	 * 200 si el username está libre; 409 (BusinessException) si ya existe. El frontend
	 * muestra "Usuario ya existente" en el callback de error.
	 */
	@Transactional(readOnly = true)
	public ListaDTO validarUsername(String username) {
		if (userRepository.existsByUsername(username.trim())) {
			throw new BusinessException(HttpStatus.CONFLICT, ConstantesCodigosError.CODIGO_USUARIO_EXISTE,
					ConstantesCodigosError.MENSAJE_REGISTRO_USUARIO_EXISTENTE);
		}
		ListaDTO libre = ListaDTOs.ok(null);
		libre.setLista(List.of());
		return libre;
	}

	@Transactional
	public ListaDTO registrarUsuario(RegistroRequest request) {
		final String username = request.getUsername().trim();
		final String email = request.getEmail().trim();
		final Role rol = Role.valueOf(request.getRol().trim().toUpperCase());
		System.out.println(rol);
		if (rol == Role.COLABORADOR && request.getIdServicio() == null) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, ConstantesCodigosError.CODIGO_VALIDACION,
					ConstantesCodigosError.MENSAJE_SERVICIO_OBLIGATORIO);
		}
		if (userRepository.existsByUsername(username)) {
			throw new BusinessException(HttpStatus.CONFLICT, ConstantesCodigosError.CODIGO_USUARIO_EXISTE,
					ConstantesCodigosError.MENSAJE_REGISTRO_USUARIO_EXISTENTE);
		}
		if (userRepository.existsByEmailIgnoreCase(email)) {
			throw new BusinessException(HttpStatus.CONFLICT, ConstantesCodigosError.CODIGO_CORREO_EXISTE,
					ConstantesCodigosError.MENSAJE_CORREO_EXISTE);
		}
		if (personaRepository.existsByDocumento_IdDocumentoAndNumeroDocumento(request.getIdDocumento(),
				request.getNumeroDocumento())) {
			throw new BusinessException(HttpStatus.CONFLICT, ConstantesCodigosError.CODIGO_DOCUMENTO_EXISTE,
					ConstantesCodigosError.MENSAJE_DOCUMENTO_EXISTE);
		}

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

		return ListaDTOs.ok(ConstantesCodigosError.MENSAJE_REGISTRO_EXITOSO);
	}

	public ListaDTO getColaboradoresSeguidos(Long id) {
		try {
			List<UsuarioSeguidoDTO> listado = userRepository.obtenerColaboradorSeguido(id);
			return ListaDTOs.de(listado, ConstantesCodigosError.MENSAJE_COLABORADORES_NO_SEGUIDOS);
		} catch (Exception e) {
			log.error("Error al obtener colaboradores seguidos de {}", id, e);
			return ListaDTOs.errorNoControlado();
		}
	}

	public ListaDTO getSeguidoresColaborador(Long id) {
		try {
			List<UsuarioSeguidoDTO> listado = userRepository.obtenerSeguidoresColaborador(id);
			return ListaDTOs.de(listado, ConstantesCodigosError.MENSAJE_CONTRATANTE_NO_SEGUIDOR);
		} catch (Exception e) {
			log.error("Error al obtener seguidores del colaborador {}", id, e);
			return ListaDTOs.errorNoControlado();
		}
	}

	public ListaDTO getUsuarioDetalle(int idUsuario) {
		try {
			List<UsuarioDetalle> listado = userRepository.usuarioDetalle(idUsuario);
			return ListaDTOs.de(listado, ConstantesCodigosError.MENSAJE_USUARIOS_NO_ENCONTRADOS);
		} catch (Exception e) {
			log.error("Error al obtener detalle del usuario {}", idUsuario, e);
			return ListaDTOs.errorNoControlado();
		}
	}
}
