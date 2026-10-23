package com.ServiYa.serviya.negocio.servicios.dashboardService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.modelos.contrato.ContratoResumenDTO;
import com.ServiYa.serviya.modelos.modelos.dashboard.DashboardColaboradorDTO;
import com.ServiYa.serviya.modelos.modelos.resena.ResenaRecibidaDTO;
import com.ServiYa.serviya.negocio.servicios.usuario.IdentidadService;
import com.ServiYa.serviya.repository.contratoRepository.ContratoRepository;
import com.ServiYa.serviya.repository.resenaRepository.ResenaRepository;
import com.ServiYa.serviya.repository.seguimientoRepository.SeguimientoRepository;
import com.ServiYa.serviya.util.PatronLike;

import lombok.RequiredArgsConstructor;

/**
 * Arma el resumen del inicio del colaborador: lee los datos (contratos, reseñas, seguidores) y deja
 * los cálculos en {@link EstadisticasColaborador}. Siempre del colaborador del token.
 */
@Service
@RequiredArgsConstructor
public class DashboardColaboradorService {

	private static final int ULTIMAS_RESENAS = 3;

	private final IdentidadService identidad;
	private final ContratoRepository contratoRepository;
	private final ResenaRepository resenaRepository;
	private final SeguimientoRepository seguimientoRepository;

	@Transactional(readOnly = true)
	public DashboardColaboradorDTO resumen(String colaboradorUsername) {
		Long idColaborador = identidad.personaIdDe(colaboradorUsername);
		LocalDate hoy = LocalDate.now();

		List<DatoContrato> contratos = contratoRepository.listarPorColaborador(idColaborador).stream()
				.map(DashboardColaboradorService::aDato).toList();

		Map<Integer, Long> porPuntuacion = new HashMap<>();
		for (Object[] fila : resenaRepository.distribucionPuntuaciones(idColaborador)) {
			porPuntuacion.put(((Number) fila[0]).intValue(), ((Number) fila[1]).longValue());
		}

		List<ResenaRecibidaDTO> ultimas = resenaRepository
				.buscarRecibidas(idColaborador, PatronLike.contiene(""), PageRequest.of(0, ULTIMAS_RESENAS))
				.getContent();

		return new DashboardColaboradorDTO(
				EstadisticasColaborador.contratos(contratos, hoy),
				EstadisticasColaborador.ingresos(contratos, hoy),
				EstadisticasColaborador.resenas(porPuntuacion),
				seguimientoRepository.countByColaborador_IdPersona(idColaborador),
				EstadisticasColaborador.proximos(contratos, hoy),
				ultimas);
	}

	private static DatoContrato aDato(ContratoResumenDTO c) {
		return new DatoContrato(c.getIdContrato(), c.getNombreContrato(), c.getNombreContratante(), c.getEstado(),
				c.getPrecio(), aLocal(c.getFechaInicio()), aLocal(c.getFechaFin()));
	}

	private static LocalDate aLocal(Date fecha) {
		return fecha == null ? null : fecha.toLocalDate();
	}
}
