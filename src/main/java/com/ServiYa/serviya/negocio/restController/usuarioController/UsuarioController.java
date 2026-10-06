package com.ServiYa.serviya.negocio.restController.usuarioController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ServiYa.serviya.modelos.modelos.auth.AuthResponse;
import com.ServiYa.serviya.modelos.modelos.auth.LoginRequest;
import com.ServiYa.serviya.modelos.modelos.auth.RegistroRequest;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.UserDTO;
import com.ServiYa.serviya.negocio.servicios.usuario.UserService;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS)
@RequiredArgsConstructor
public class UsuarioController {
	private final UserService userService;

	

	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_USUARIOS)
	public ResponseEntity<ListaDTO> getUsuarios() {
		ListaDTO respuesta = null;
		try {
			respuesta = userService.getUsuarios();
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
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_COLABORADORES_SEGUIDOS+"/{id}")
	public ResponseEntity<ListaDTO> getColaboradoresSeguidos(@PathVariable Long id) {
		ListaDTO respuesta = null;
		try {
			respuesta = userService.getColaboradoresSeguidos(id);
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
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_SEGUIDORES_COLABORADOR+"/{id}")
	public ResponseEntity<ListaDTO> getSeguidoresColaborador(@PathVariable Long id) {
		ListaDTO respuesta = null;
		try {
			respuesta = userService.getSeguidoresColaborador(id);
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
	@GetMapping(value = ConstantesSeguridadPathRest.PATH_OBTENER_USUARIO_DETALLE+"/{id}")
	public ResponseEntity<ListaDTO> getUsuarioDetalle(@PathVariable int id) {
		ListaDTO respuesta = null;
		try {
			respuesta = userService.getUsuarioDetalle(id);
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
