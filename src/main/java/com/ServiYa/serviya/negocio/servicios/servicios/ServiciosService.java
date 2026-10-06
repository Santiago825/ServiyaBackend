package com.ServiYa.serviya.negocio.servicios.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.ServiYa.serviya.modelos.entity.seguimiento.Seguimiento;
import com.ServiYa.serviya.modelos.entity.servicio.Servicio;
import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.ColaboradoR.ColaboradorDTO;
import com.ServiYa.serviya.modelos.modelos.ColaboradoR.DetalleColaboradorDTO;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.servicio.RequestSeguirColaborador;
import com.ServiYa.serviya.modelos.modelos.servicio.ServicioDTO;
import com.ServiYa.serviya.repository.colaboradorRepository.ColaboradorRepository;
import com.ServiYa.serviya.repository.seguimientoRepository.SeguimientoRepository;
import com.ServiYa.serviya.repository.servicioRepository.ServicioRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiciosService {

	private final ServicioRepository servicioRepository;
	private final SeguimientoRepository seguiminetoRepository;
	private final ColaboradorRepository colaboradorRepository;

	public ListaDTO getServicios() {
		ListaDTO respuesta;
		try {
			List<ServicioDTO> listado = servicioRepository.findAllServicios();
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
			respuesta = new ListaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
		}
		return respuesta;

	}
	public ListaDTO getServiciosMasUso() {
		ListaDTO respuesta;
		try {
			List<ServicioDTO> listado = servicioRepository.findServiciosMasUsos();
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
			respuesta = new ListaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
		}
		return respuesta;

	}

	public ListaDTO getColaboradores(Long idServicio, Long idMunicipio,Long idContratante) {
		ListaDTO respuesta;
		try {
			List<ColaboradorDTO> listado = colaboradorRepository.findAllColaboradores(idServicio, idMunicipio,idContratante);
			
			System.out.println(listado.get(2));
			respuesta = new ListaDTO();
			if (listado.isEmpty()) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_COLABORADORES_NO_ENCONTRADO);
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

	public ListaDTO getDetalleColaboradores(Long idColaborador,Long idContratante) {

		ListaDTO respuesta;
		try {
			List<Object[]> dColaborador = colaboradorRepository.findAllDetalleColaborador(idColaborador,idContratante);
			respuesta = new ListaDTO();
			DetalleColaboradorDTO dto = new DetalleColaboradorDTO();

			if (dColaborador != null) {
				Object[] row = dColaborador.get(0);
				dto.setId_persona(((Long) row[0]).longValue());
				dto.setNombre((String) row[1]);
				dto.setApellido((String) row[2]);
				dto.setTelefono((String) row[3]);
				dto.setMunicipio((String) row[4]);
				dto.setServicio((String) row[5]);
				dto.setUsername((String) row[6]);
				dto.setNumero_resenas(((Number) row[7]).longValue());
				dto.setDescripcion((String) row[8]);
				dto.setFoto((byte[]) row[9]);
				dto.setSeguido(((Number) row[10]).longValue());
				dto.setResena(colaboradorRepository.findResenaById(idColaborador));
			}
			List<DetalleColaboradorDTO> listado = new ArrayList();
			listado.add(dto);
			
			if (listado.isEmpty()) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_DETALLE_COLABORADORES_NO_ENCONTRADO);
				respuesta.setLista(listado);
			} else {
				respuesta.setLista(listado);
				respuesta.setTotalPaginas(listado.size() > 0 ? 1 : 0);
				respuesta.setTotalRegistros(listado.size());
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
			}
			
			
			} 
			catch (Exception e) {
			respuesta = new ListaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
			}
			return respuesta;

		}
	public ListaDTO seguirColaborador(RequestSeguirColaborador seguidor) {
		ListaDTO respuesta;
		try {
			respuesta = new ListaDTO();
			System.out.println("aqui estoy "+ seguidor);
			Seguimiento seguimiento = seguiminetoRepository.encontrarPorColaboradorYContratante(seguidor.getColaborador(), seguidor.getContratante());
			if(seguimiento != null) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_SEGUIR_COLABBORADOR);
				return respuesta;
			}
			System.out.println("aqui estoy "+ seguimiento);
			System.out.println("aqui estoy "+ seguidor);
			
			Persona contratante = Persona.builder()
					.idPersona(seguidor.getContratante()).build();
			Persona colaborador = Persona.builder()
					.idPersona(seguidor.getColaborador()).build();
			System.out.println("aqui estoy 24"+ colaborador);

			Seguimiento seguir = Seguimiento.builder()
					.contratante(contratante)
					.colaborador(colaborador)
					.build();
			System.out.println("aqui estoy 3 "+ seguir.getColaborador().getIdPersona()+" "+seguir.getContratante().getIdPersona());

			Seguimiento listado = seguiminetoRepository.save(seguir);
			System.out.println("aqui estoy 25"+ listado);
			if (listado == null) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_LOGIN_NO_ENOCNTRADO);
			} else {
				
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);

			}

		} catch (Exception e) {
			respuesta = new ListaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
		}
		return respuesta;

	}
	public ListaDTO dejarSeguirColaborador(RequestSeguirColaborador seguidor) {
		ListaDTO respuesta;
		try {
			int listado = seguiminetoRepository.eliminarPorColaboradorYContratante(seguidor.getColaborador(),seguidor.getContratante());
			respuesta = new ListaDTO();
			if (listado == 0) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_SEGUIR_COLABBORADOR);
			} else {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);

			}

		} catch (Exception e) {
			respuesta = new ListaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
		}
		return respuesta;

	}

}
