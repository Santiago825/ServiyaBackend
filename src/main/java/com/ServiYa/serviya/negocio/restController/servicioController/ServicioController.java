package com.ServiYa.serviya.negocio.restController.servicioController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.servicio.RequestSeguirColaborador;
import com.ServiYa.serviya.negocio.servicios.servicios.ServiciosService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class ServicioController {
	public final ServiciosService serviciosService;
	
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_SERVICIOS)
	public ResponseEntity<ListaDTO> getServicios() {
		ListaDTO respuesta = null;
		try {
			respuesta = serviciosService.getServicios();
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
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_SERVICIOS_MAS_USOS)
	public ResponseEntity<ListaDTO> getServiciosMasUsos() {
		ListaDTO respuesta = null;
		try {
			respuesta = serviciosService.getServiciosMasUso();
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
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_COLABORADOR)
	public ResponseEntity<ListaDTO> getColaborador(@RequestParam(required = true) Long idServicio,
	        @RequestParam(required = true) Long idMunicipio,@RequestParam(required = true) Long idContratante) {
		
		ListaDTO respuesta = null;
		try {
	        respuesta = serviciosService.getColaboradores(idServicio,idMunicipio,idContratante);
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
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_DETALLE_COLABORADOR)
	public ResponseEntity<ListaDTO> getDetalleColaborador(@RequestParam(required = true) Long idColaborador,@RequestParam(required = true) Long idContratante) {
		ListaDTO respuesta = null;
		try {
			System.out.println("entreees");
			respuesta = serviciosService.getDetalleColaboradores(idColaborador,idContratante);
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
	
	@PostMapping(value = ConstantesSeguridadPathRest.PATH_SEGUIR_COLABORADOR)
	public ResponseEntity<ListaDTO> seguirColaborador(@RequestBody RequestSeguirColaborador request) {
		ListaDTO respuesta = null;
		try {
			respuesta = serviciosService.seguirColaborador(request);
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
	@PostMapping(value = ConstantesSeguridadPathRest.PATH_DEJAR_SEGUIR_COLABORADOR)
	public ResponseEntity<ListaDTO> dejarSeguirColaborador(@RequestBody RequestSeguirColaborador request) {
		ListaDTO respuesta = null;
		try {
			respuesta = serviciosService.dejarSeguirColaborador(request);
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
