package com.ServiYa.serviya.negocio.servicios.colaboradorService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.modelos.ColaboradoR.ColaboradorDTO;
import com.ServiYa.serviya.modelos.modelos.ColaboradoR.DetalleColaboradorDTO;
import com.ServiYa.serviya.negocio.servicios.usuario.IdentidadService;
import com.ServiYa.serviya.repository.colaboradorRepository.ColaboradorRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

/**
 * Búsqueda y detalle de colaboradores. El campo "seguido" se calcula respecto al usuario del token
 * (antes lo mandaba el cliente en idContratante, lo que permitía preguntar por los seguidos de otro).
 */
@Service
@RequiredArgsConstructor
public class ColaboradorService {

	private final ColaboradorRepository colaboradorRepository;
	private final IdentidadService identidad;

	@Transactional(readOnly = true)
	public List<ColaboradorDTO> buscar(Long idServicio, Long idMunicipio, String username) {
		return colaboradorRepository.findAllColaboradores(idServicio, idMunicipio, identidad.personaIdDe(username));
	}

	@Transactional(readOnly = true)
	public DetalleColaboradorDTO detalle(Long idColaborador, String username) {
		List<Object[]> filas = colaboradorRepository.findAllDetalleColaborador(idColaborador,
				identidad.personaIdDe(username));
		if (filas == null || filas.isEmpty()) {
			throw BusinessException
					.noEncontrado(ConstantesCodigosError.MENSAJE_DETALLE_COLABORADORES_NO_ENCONTRADO);
		}
		DetalleColaboradorDTO dto = aDetalle(filas.get(0));
		dto.setResena(colaboradorRepository.findResenaById(idColaborador));
		return dto;
	}

	/** El orden de columnas debe coincidir con la consulta findAllDetalleColaborador. */
	private static DetalleColaboradorDTO aDetalle(Object[] fila) {
		DetalleColaboradorDTO dto = new DetalleColaboradorDTO();
		dto.setId_persona(((Number) fila[0]).longValue());
		dto.setNombre((String) fila[1]);
		dto.setApellido((String) fila[2]);
		dto.setTelefono((String) fila[3]);
		dto.setMunicipio((String) fila[4]);
		dto.setServicio((String) fila[5]);
		dto.setUsername((String) fila[6]);
		dto.setNumero_resenas(((Number) fila[7]).longValue());
		dto.setDescripcion((String) fila[8]);
		dto.setFoto((byte[]) fila[9]);
		dto.setSeguido(((Number) fila[10]).longValue());
		return dto;
	}
}
