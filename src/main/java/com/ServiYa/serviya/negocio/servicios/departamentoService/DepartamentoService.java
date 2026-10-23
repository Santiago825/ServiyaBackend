package com.ServiYa.serviya.negocio.servicios.departamentoService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.modelos.departamento.DepartamentoDTO;
import com.ServiYa.serviya.repository.departamentoRepository.DepartamentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

	private final DepartamentoRepository departamentoRepository;

	@Transactional(readOnly = true)
	public List<DepartamentoDTO> listar() {
		return departamentoRepository.findAllDepartamento();
	}
}
