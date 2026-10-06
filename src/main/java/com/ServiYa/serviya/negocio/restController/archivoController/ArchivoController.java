package com.ServiYa.serviya.negocio.restController.archivoController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.negocio.servicios.archivoService.ArchivoService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ArchivoController {
	public final ArchivoService archivoService;
	
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_CV+"/{id}")
	public ResponseEntity<ListaDTO> obtenerCv(@PathVariable Long id) {
		ListaDTO respuesta = null;
		try {
			respuesta = archivoService.obtenerCv(id);
			if (respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {

				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

				return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.OK);

			} else {
				if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().trim().equals("")) {
					respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
				}

				return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			if (respuesta == null) {
				respuesta = new ListaDTO();
			}
			if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
			}
            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

		}

       
    }
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_ARCHIVO_CONTRATO+"/{id}")
	public ResponseEntity<ListaDTO> obtenerContrato(@PathVariable Long id) {
		ListaDTO respuesta = null;
		try {
			respuesta = archivoService.obtenerContrato(id);
			if (respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {

				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

				return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.OK);

			} else {
				if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().trim().equals("")) {
					respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
				}

				return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			if (respuesta == null) {
				respuesta = new ListaDTO();
			}
			if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
			}
            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

		}

       
    }

}
