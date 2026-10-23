package com.ServiYa.serviya.negocio.servicios.tipoDocumentoService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.entity.tipoDocumento.TipoDocumento;
import com.ServiYa.serviya.repository.tipoDocumentoRepository.TipoDocumentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoDocumentoService {

	private final TipoDocumentoRepository documentoRepository;

	@Transactional(readOnly = true)
	public List<TipoDocumento> listar() {
		return documentoRepository.findAll();
	}
}
