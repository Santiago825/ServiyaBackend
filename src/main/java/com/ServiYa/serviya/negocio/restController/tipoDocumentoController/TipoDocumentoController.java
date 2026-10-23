package com.ServiYa.serviya.negocio.restController.tipoDocumentoController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.restController.RespuestasHttp;
import com.ServiYa.serviya.negocio.servicios.tipoDocumentoService.TipoDocumentoService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class TipoDocumentoController {

	private final TipoDocumentoService documentoService;

	@GetMapping(ConstantesSeguridadPathRest.PATH_OBTENER_TIPO_DOCUMENTO)
	public ResponseEntity<ListaDTO> getTipoDocumento() {
		return RespuestasHttp.lista(documentoService.listar(),
				ConstantesCodigosError.MENSAJE_TIPO_DOCUMENTO_NO_ENCONTRADOS);
	}
}
