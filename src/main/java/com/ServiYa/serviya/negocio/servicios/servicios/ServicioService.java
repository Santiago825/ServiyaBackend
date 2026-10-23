package com.ServiYa.serviya.negocio.servicios.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.modelos.servicio.ServicioDTO;
import com.ServiYa.serviya.repository.servicioRepository.ServicioRepository;

import lombok.RequiredArgsConstructor;

/** Catálogo de servicios (plomería, electricidad...). Solo lectura. */
@Service
@RequiredArgsConstructor
public class ServicioService {

	private final ServicioRepository servicioRepository;

	@Transactional(readOnly = true)
	public List<ServicioDTO> listar() {
		return servicioRepository.findAllServicios();
	}

	@Transactional(readOnly = true)
	public List<ServicioDTO> masUsados() {
		return servicioRepository.findServiciosMasUsos();
	}
}
