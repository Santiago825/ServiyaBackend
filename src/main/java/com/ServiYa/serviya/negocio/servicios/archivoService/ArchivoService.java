package com.ServiYa.serviya.negocio.servicios.archivoService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.modelos.archivo.ArchivoDTO;
import com.ServiYa.serviya.negocio.servicios.contratoService.ContratoService;
import com.ServiYa.serviya.repository.archivoRepository.ArchivoRepository;
import com.ServiYa.serviya.repository.archivoRepository.ContratoArchivoRepository;

import lombok.RequiredArgsConstructor;

/** Descarga de archivos almacenados (hoja de vida y archivo adjunto de un contrato). */
@Service
@RequiredArgsConstructor
public class ArchivoService {

	private final ArchivoRepository archivoRepository;
	private final ContratoArchivoRepository contratoArchivoRepository;
	private final ContratoService contratoService;

	/** Lista vacía si la persona no existe o no subió hoja de vida (antes: excepción -> 500 genérico). */
	@Transactional(readOnly = true)
	public List<ArchivoDTO> obtenerCv(Long idPersona) {
		List<Object[]> filas = archivoRepository.obtenerCv(idPersona);
		if (filas.isEmpty()) {
			return List.of();
		}
		Object[] fila = filas.get(0);
		byte[] contenido = (byte[]) fila[0];
		if (contenido == null || contenido.length == 0) {
			return List.of();
		}
		return List.of(new ArchivoDTO((String) fila[1], "PDF", contenido));
	}

	/** Solo las dos partes del contrato pueden descargar su archivo. */
	@Transactional(readOnly = true)
	public List<ArchivoDTO> obtenerContrato(Long idContrato, String username) {
		contratoService.exigirParticipante(idContrato, username);
		return contratoArchivoRepository.obtenerContrato(idContrato);
	}
}
