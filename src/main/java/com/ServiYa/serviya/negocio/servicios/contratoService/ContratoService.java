package com.ServiYa.serviya.negocio.servicios.contratoService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.modelos.contrato.ContratoDTO;
import com.ServiYa.serviya.modelos.modelos.contrato.ContratoDetalleDTO;
import com.ServiYa.serviya.modelos.modelos.contrato.ContratoResumenDTO;
import com.ServiYa.serviya.modelos.modelos.contrato.DetalleContratoDTO;
import com.ServiYa.serviya.negocio.servicios.resenaService.PoliticaResena;
import com.ServiYa.serviya.negocio.servicios.usuario.IdentidadService;
import com.ServiYa.serviya.repository.contratoRepository.ContratoRepository;
import com.ServiYa.serviya.repository.resenaRepository.ResenaRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

/**
 * CONSULTAS de contratos. Los comandos (crear, aceptar, rechazar) están en ContratoGestionService.
 * Todas las lecturas se limitan a lo que le pertenece al usuario del token.
 */
@Service
@RequiredArgsConstructor
public class ContratoService {

	private final ContratoRepository contratoRepository;
	private final IdentidadService identidad;
	private final ResenaRepository resenaRepository;

	@Transactional(readOnly = true)
	public List<ContratoResumenDTO> listarDelColaborador(String username) {
		return contratoRepository.listarPorColaborador(identidad.personaIdDe(username));
	}

	/** Incluye, por contrato, si ya fue reseñado y si el botón "Reseñar" debe estar habilitado. */
	@Transactional(readOnly = true)
	public List<ContratoResumenDTO> listarDelContratante(String username) {
		List<ContratoResumenDTO> contratos = contratoRepository.listarPorContratante(identidad.personaIdDe(username));
		if (contratos.isEmpty()) {
			return contratos;
		}
		Set<Long> resenados = new HashSet<>(resenaRepository
				.idsContratosResenados(contratos.stream().map(ContratoResumenDTO::getIdContrato).toList()));
		LocalDate hoy = LocalDate.now();
		for (ContratoResumenDTO c : contratos) {
			boolean yaResenado = resenados.contains(c.getIdContrato());
			c.setResenado(yaResenado);
			c.setPuedeResenar(PoliticaResena.puedeResenar(c.getEstado(), c.getFechaFin(), yaResenado, hoy));
		}
		return contratos;
	}

	/** Solo lo ven las dos partes del contrato; para cualquier otro, "no encontrado". */
	@Transactional(readOnly = true)
	public ContratoDetalleDTO detalle(Long idContrato, String username) {
		return contratoRepository.detalleParaParticipante(idContrato, identidad.personaIdDe(username)).stream()
				.findFirst().orElseThrow(ContratoService::noEncontrado);
	}

	// ───────────── endpoints históricos (se conserva la forma de la respuesta) ─────────────

	/** Contratos de un contratante. El id debe ser el del usuario del token. */
	@Transactional(readOnly = true)
	public List<ContratoDTO> obtenerContratos(Long idContratante, String username) {
		if (!identidad.personaIdDe(username).equals(idContratante)) {
			throw BusinessException.prohibido(ConstantesCodigosError.MENSAJE_PROHIBIDO);
		}
		return contratoRepository.obtenerContratos(idContratante);
	}

	@Transactional(readOnly = true)
	public List<DetalleContratoDTO> obtenerContratoDetalle(Long idContrato, String username) {
		exigirParticipante(idContrato, username);
		return contratoRepository.obtenerContratoDetalle(idContrato);
	}

	/** "No encontrado" (y no "prohibido") para no revelar que el contrato existe. */
	@Transactional(readOnly = true)
	public void exigirParticipante(Long idContrato, String username) {
		if (contratoRepository.contarParticipante(idContrato, identidad.personaIdDe(username)) == 0) {
			throw noEncontrado();
		}
	}

	private static BusinessException noEncontrado() {
		return BusinessException.noEncontrado("Contrato no encontrado.");
	}
}
