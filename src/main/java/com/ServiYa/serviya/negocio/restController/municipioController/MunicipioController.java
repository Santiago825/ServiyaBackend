package com.ServiYa.serviya.negocio.restController.municipioController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.municipioService.MunicipioService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class MunicipioController {

	private final MunicipioService municipioService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_MUNICIPIO + "/{id}")
	public ResponseEntity<ListaDTO> getMunicipio(@PathVariable("id") Long id) {
		return RespuestasHttp.lista(municipioService.listarPorDepartamento(id),
				ConstantesCodigosError.MENSAJE_MUNICIPIOS_NO_ENCONTRADO);
	}
}
