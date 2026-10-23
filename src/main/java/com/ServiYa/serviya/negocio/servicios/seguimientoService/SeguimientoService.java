package com.ServiYa.serviya.negocio.servicios.seguimientoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.entity.seguimiento.Seguimiento;
import com.ServiYa.serviya.negocio.servicios.usuario.IdentidadService;
import com.ServiYa.serviya.repository.personaRepository.PersonaRepository;
import com.ServiYa.serviya.repository.seguimientoRepository.SeguimientoRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

/**
 * Seguir / dejar de seguir a un colaborador. El contratante es SIEMPRE el usuario del token: antes
 * venía en el cuerpo de la petición y cualquier contratante podía actuar en nombre de otro.
 */
@Service
@RequiredArgsConstructor
public class SeguimientoService {

	private final SeguimientoRepository seguimientoRepository;
	private final PersonaRepository personaRepository;
	private final IdentidadService identidad;

	@Transactional
	public void seguir(String contratanteUsername, Long idColaborador) {
		Long idContratante = identidad.personaIdDe(contratanteUsername);
		if (seguimientoRepository.existsByColaborador_IdPersonaAndContratante_IdPersona(idColaborador,
				idContratante)) {
			throw BusinessException.conflicto(ConstantesCodigosError.MENSAJE_SEGUIR_COLABBORADOR);
		}
		seguimientoRepository.save(Seguimiento.builder()
				.contratante(personaRepository.getReferenceById(idContratante))
				.colaborador(personaRepository.getReferenceById(idColaborador)).build());
	}

	@Transactional
	public void dejarDeSeguir(String contratanteUsername, Long idColaborador) {
		int filas = seguimientoRepository.eliminarPorColaboradorYContratante(idColaborador,
				identidad.personaIdDe(contratanteUsername));
		if (filas == 0) {
			throw BusinessException.noEncontrado(ConstantesCodigosError.MENSAJE_NO_SEGUIR_COLABBORADOR);
		}
	}
}
