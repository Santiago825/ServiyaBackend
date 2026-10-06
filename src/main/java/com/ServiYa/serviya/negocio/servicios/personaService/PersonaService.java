package com.ServiYa.serviya.negocio.servicios.personaService;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioSeguidoDTO;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.persona.PersonaRainting;
import com.ServiYa.serviya.modelos.modelos.persona.PersonaRainting;
import com.ServiYa.serviya.modelos.modelos.servicio.ServicioDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.UserDTO;
import com.ServiYa.serviya.repository.personaRepository.PersonaRepository;
import com.ServiYa.serviya.repository.usuarioRepository.UserRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PersonaService {
	private final PersonaRepository personaRepository;
	
	public ListaDTO getPersonasRaiting() {
		ListaDTO respuesta;
		try {
			List<PersonaRainting> listado = personaRepository.findPersonasRaiting();
			respuesta = new ListaDTO();
			if (listado.isEmpty()) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_SERVICIOS_NO_ENCONTRADO);
				respuesta.setLista(listado);
			} else {
				respuesta.setLista(listado);
				respuesta.setTotalPaginas(listado.size() > 0 ? 1 : 0);
				respuesta.setTotalRegistros(listado.size());
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);

			}

		} catch (Exception e) {
			System.out.println(e);
			respuesta = new ListaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
		}
		return respuesta;

	}
	

	


}
