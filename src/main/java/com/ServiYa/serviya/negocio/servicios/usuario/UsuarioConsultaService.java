package com.ServiYa.serviya.negocio.servicios.usuario;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.modelos.usuarios.UserDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioDetalle;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioSeguidoDTO;
import com.ServiYa.serviya.repository.seguimientoRepository.SeguimientoRepository;
import com.ServiYa.serviya.repository.usuarioRepository.UserRepository;

import lombok.RequiredArgsConstructor;

/** Consultas de usuarios y de su red de seguimiento. El registro vive en {@link RegistroService}. */
@Service
@RequiredArgsConstructor
public class UsuarioConsultaService {

	private final UserRepository userRepository;
	private final SeguimientoRepository seguimientoRepository;

	@Transactional(readOnly = true)
	public List<UserDTO> listar() {
		return userRepository.findAllUser();
	}

	@Transactional(readOnly = true)
	public List<UsuarioSeguidoDTO> colaboradoresSeguidos(Long idUsuario) {
		return seguimientoRepository.obtenerColaboradorSeguido(idUsuario);
	}

	@Transactional(readOnly = true)
	public List<UsuarioSeguidoDTO> seguidoresDeColaborador(Long idUsuario) {
		return seguimientoRepository.obtenerSeguidoresColaborador(idUsuario);
	}

	@Transactional(readOnly = true)
	public List<UsuarioDetalle> detalle(int idUsuario) {
		return userRepository.usuarioDetalle(idUsuario);
	}
}
