package com.ServiYa.serviya.negocio.restController.personaController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.personaService.PersonaService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class PersonaController {

	private final PersonaService personaService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_PERSONAS_RAITING)
	public ResponseEntity<ListaDTO> getPersonasRaiting() {
		return RespuestasHttp.lista(personaService.ranking(), ConstantesCodigosError.MENSAJE_SERVICIOS_NO_ENCONTRADO);
	}
}
