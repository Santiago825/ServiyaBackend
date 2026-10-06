package com.ServiYa.serviya.negocio.restController.usuarioController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class DemoController {

	@PostMapping(value = ConstantesSeguridadPathRest.PATH_DEMO)
	public String welcome() {
		//TODO: process POST request
		
		return "welcome drom secure endpoint";
	}
	
}
