package com.ServiYa.serviya.negocio.servicios.municipioService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.modelos.municipio.MunicipioDTO;
import com.ServiYa.serviya.repository.MunicipioRepository.MunicipioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MunicipioService {

	private final MunicipioRepository municipioRepository;

	@Transactional(readOnly = true)
	public List<MunicipioDTO> listarPorDepartamento(Long idDepartamento) {
		return municipioRepository.findAllMunicipio(idDepartamento);
	}
}
