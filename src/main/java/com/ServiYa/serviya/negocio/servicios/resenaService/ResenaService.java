package com.ServiYa.serviya.negocio.servicios.resenaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.entity.resena.Resena;
import com.ServiYa.serviya.modelos.modelos.contrato.ContratoResenable;
import com.ServiYa.serviya.modelos.modelos.contrato.EstadoContrato;
import com.ServiYa.serviya.modelos.modelos.resena.ResenaRecibidaDTO;
import com.ServiYa.serviya.modelos.modelos.resena.ResenarContratoRequest;
import com.ServiYa.serviya.negocio.servicios.usuario.IdentidadService;
import com.ServiYa.serviya.repository.contratoRepository.ContratoRepository;
import com.ServiYa.serviya.repository.personaRepository.PersonaRepository;
import com.ServiYa.serviya.repository.resenaRepository.ResenaRepository;
import com.ServiYa.serviya.util.PatronLike;

import lombok.RequiredArgsConstructor;

/**
 * Alta de reseñas. El CONTRATANTE (siempre el del token) reseña al colaborador de un contrato suyo
 * una sola vez y solo cuando {@link PoliticaResena} lo permite.
 */
@Service
@RequiredArgsConstructor
public class ResenaService {

	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final ResenaRepository resenaRepository;
	private final ContratoRepository contratoRepository;
	private final PersonaRepository personaRepository;
	private final IdentidadService identidad;

	private static final int TAMANO_MAXIMO = 50;

	/**
	 * Reseñas recibidas por el colaborador del token (más recientes primero), con búsqueda opcional y
	 * paginación (página base 0; tamaño entre 1 y 50).
	 */
	@Transactional(readOnly = true)
	public Page<ResenaRecibidaDTO> recibidas(String colaboradorUsername, String busqueda, int pagina, int tamano) {
		Long idColaborador = identidad.personaIdDe(colaboradorUsername);
		Pageable pageable = PageRequest.of(Math.max(pagina, 0), Math.min(Math.max(tamano, 1), TAMANO_MAXIMO));
		return resenaRepository.buscarRecibidas(idColaborador, PatronLike.contiene(busqueda), pageable);
	}

	@Transactional
	public void resenar(String contratanteUsername, Long idContrato, ResenarContratoRequest datos) {
		Long idContratante = identidad.personaIdDe(contratanteUsername);

		// Solo encuentra el contrato si es del usuario: para cualquier otro, "no encontrado".
		ContratoResenable contrato = contratoRepository.datosParaResenar(idContrato, idContratante)
				.orElseThrow(() -> BusinessException.noEncontrado("Contrato no encontrado."));

		if (contrato.getEstado() != EstadoContrato.ACEPTADO) {
			throw BusinessException.conflicto("Solo puedes reseñar contratos que fueron aceptados.");
		}
		if (!PoliticaResena.fechaFinCumplida(contrato.getFechaFin(), LocalDate.now())) {
			String fecha = contrato.getFechaFin() == null ? "" : " (" + contrato.getFechaFin().toLocalDate().format(FORMATO) + ")";
			throw BusinessException.conflicto("Podrás reseñar este contrato cuando se cumpla su fecha de fin" + fecha + ".");
		}
		if (resenaRepository.existsByContrato_IdContrato(idContrato)) {
			throw BusinessException.conflicto("Ya reseñaste este contrato.");
		}

		String comentario = datos.getComentario() == null ? "" : datos.getComentario().trim();

		Resena resena = new Resena();
		resena.setContrato(contratoRepository.getReferenceById(idContrato));
		resena.setContratante(personaRepository.getReferenceById(idContratante));
		resena.setColaborador(personaRepository.getReferenceById(contrato.getIdColaborador()));
		resena.setPuntuacion(datos.getPuntuacion());
		resena.setComentario(comentario.isEmpty() ? null : comentario);
		resenaRepository.save(resena);
	}
}
