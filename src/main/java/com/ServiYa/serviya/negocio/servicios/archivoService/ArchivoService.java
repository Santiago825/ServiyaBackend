package com.ServiYa.serviya.negocio.servicios.archivoService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.ServiYa.serviya.modelos.modelos.archivo.ArchivoDTO;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.servicio.ServicioDTO;
import com.ServiYa.serviya.repository.archivoRepository.ArchivoRepository;
import com.ServiYa.serviya.repository.archivoRepository.ContratoArchivoRepository;
import com.ServiYa.serviya.repository.colaboradorRepository.ColaboradorRepository;
import com.ServiYa.serviya.repository.contratoRepository.ContratoRepository;
import com.ServiYa.serviya.repository.seguimientoRepository.SeguimientoRepository;
import com.ServiYa.serviya.repository.servicioRepository.ServicioRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArchivoService {
	public final ArchivoRepository archivoRepository;
	public final ContratoArchivoRepository contratoArchivoRepository;
	
	public ListaDTO obtenerCv(Long id) {
		ListaDTO respuesta;
		try {
			 Object[] result = archivoRepository.obtenerCv(id).get(0);
			    ArchivoDTO archivo = new ArchivoDTO();
			    archivo.setContenidoBase64((byte[]) result[0]);
			    archivo.setNombre((String) result[1]);
			    archivo.setTipoContenido("PDF");
			    List<ArchivoDTO> listado = new ArrayList<>();
			    listado.add(archivo);
			respuesta = new ListaDTO();
			if (listado.isEmpty()) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_ARCHIVO_CV_NO_ENCONTRADO);
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
	public ListaDTO obtenerContrato(Long id) {
		ListaDTO respuesta;
		try {
			 List<ArchivoDTO> listado = contratoArchivoRepository.obtenerContrato(id);
			
			respuesta = new ListaDTO();
			if (listado.isEmpty()) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_ARCHIVO_CONTRATO_NO_ENCONTRADO);
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
