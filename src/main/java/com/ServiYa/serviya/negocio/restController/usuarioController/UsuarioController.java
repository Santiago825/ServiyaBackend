package com.ServiYa.serviya.negocio.restController.usuarioController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.usuario.UsuarioConsultaService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;
import com.ServiYa.serviya.util.ReglasAcceso;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioConsultaService usuarioService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_USUARIOS)
	public ResponseEntity<ListaDTO> getUsuarios() {
		return RespuestasHttp.lista(usuarioService.listar(), ConstantesCodigosError.MENSAJE_USUARIOS_NO_ENCONTRADOS);
	}

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_COLABORADORES_SEGUIDOS + "/{id}")
	@PreAuthorize(ReglasAcceso.SOLO_CONTRATANTE)
	public ResponseEntity<ListaDTO> getColaboradoresSeguidos(@PathVariable("id") Long id) {
		return RespuestasHttp.lista(usuarioService.colaboradoresSeguidos(id),
				ConstantesCodigosError.MENSAJE_COLABORADORES_NO_SEGUIDOS);
	}

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_SEGUIDORES_COLABORADOR + "/{id}")
	@PreAuthorize(ReglasAcceso.SOLO_COLABORADOR)
	public ResponseEntity<ListaDTO> getSeguidoresColaborador(@PathVariable("id") Long id) {
		return RespuestasHttp.lista(usuarioService.seguidoresDeColaborador(id),
				ConstantesCodigosError.MENSAJE_CONTRATANTE_NO_SEGUIDOR);
	}

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_USUARIO_DETALLE + "/{id}")
	public ResponseEntity<ListaDTO> getUsuarioDetalle(@PathVariable("id") int id) {
		return RespuestasHttp.lista(usuarioService.detalle(id), ConstantesCodigosError.MENSAJE_USUARIOS_NO_ENCONTRADOS);
	}
}
