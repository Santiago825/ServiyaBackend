package com.ServiYa.serviya.negocio.restController.departamentoController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.departamentoService.DepartamentoService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class DepartamentoController {

	private final DepartamentoService departamentoService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_DEPARTAMENTO)
	public ResponseEntity<ListaDTO> getDepartamentos() {
		return RespuestasHttp.lista(departamentoService.listar(),
				ConstantesCodigosError.MENSAJE_DEPATAMENTOS_NO_ENCONTRADO);
	}
}
