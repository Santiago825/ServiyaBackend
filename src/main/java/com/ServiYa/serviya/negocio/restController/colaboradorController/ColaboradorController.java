package com.ServiYa.serviya.negocio.restController.colaboradorController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.colaboradorService.ColaboradorService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * Búsqueda y detalle de colaboradores. Si el cliente aún manda idContratante se ignora: el
 * contratante es el usuario del token.
 */
@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ColaboradorController {

	private final ColaboradorService colaboradorService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_COLABORADOR)
	public ResponseEntity<ListaDTO> getColaborador(@RequestParam("idServicio") Long idServicio, @RequestParam("idMunicipio") Long idMunicipio,
			Authentication auth) {
		return RespuestasHttp.lista(colaboradorService.buscar(idServicio, idMunicipio, auth.getName()),
				ConstantesCodigosError.MENSAJE_COLABORADORES_NO_ENCONTRADO);
	}

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_DETALLE_COLABORADOR)
	public ResponseEntity<ListaDTO> getDetalleColaborador(@RequestParam("idColaborador") Long idColaborador, Authentication auth) {
		return RespuestasHttp.lista(List.of(colaboradorService.detalle(idColaborador, auth.getName())),
				ConstantesCodigosError.MENSAJE_DETALLE_COLABORADORES_NO_ENCONTRADO);
	}
}
