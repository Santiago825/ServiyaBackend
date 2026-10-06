package com.ServiYa.serviya.negocio.servicios.contratoService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ServiYa.serviya.modelos.modelos.contrato.ContratoDTO;
import com.ServiYa.serviya.modelos.modelos.contrato.DetalleContratoDTO;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.repository.contratoRepository.ContratoRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;
import com.ServiYa.serviya.util.ListaDTOs;

import lombok.RequiredArgsConstructor;

/** REESCRITO (estaba vacío). Mantiene los nombres de método que usa ContratoController. */
@Service
@RequiredArgsConstructor
public class ContratoService {

	private final ContratoRepository contratoRepository;

	public ListaDTO obtenerContratos(Long idContratante) {
		try {
			List<ContratoDTO> datos = contratoRepository.obtenerContratos(idContratante);
			return ListaDTOs.de(datos, ConstantesCodigosError.MENSAJE_CONTRATOS_NO_ENCONTRADO);
		} catch (Exception e) {
			return ListaDTOs.errorNoControlado();
		}
	}

	public ListaDTO obtenerContratos_detalle(Long idContrato) {
		try {
			List<DetalleContratoDTO> datos = contratoRepository.obtenerContratoDetalle(idContrato);
			return ListaDTOs.de(datos, ConstantesCodigosError.MENSAJE_DETALLE_CONTRATOS_NO_ENCONTRADO);
		} catch (Exception e) {
			return ListaDTOs.errorNoControlado();
		}
	}
}
