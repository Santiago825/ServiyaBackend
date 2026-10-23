package com.ServiYa.serviya.negocio.servicios.contratoService;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.entity.contrato.Contrato;
import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.archivo.ArchivoAdjunto;
import com.ServiYa.serviya.modelos.modelos.contrato.CrearContratoRequest;
import com.ServiYa.serviya.modelos.modelos.contrato.EstadoContrato;
import com.ServiYa.serviya.modelos.modelos.usuarios.Role;
import com.ServiYa.serviya.negocio.servicios.usuario.IdentidadService;
import com.ServiYa.serviya.repository.contratoRepository.ContratoRepository;
import com.ServiYa.serviya.repository.personaRepository.PersonaRepository;
import com.ServiYa.serviya.repository.seguimientoRepository.SeguimientoRepository;
import com.ServiYa.serviya.repository.usuarioRepository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * COMANDOS sobre contratos: el COLABORADOR crea el contrato (PENDIENTE) para un contratante que lo
 * sigue; el CONTRATANTE lo acepta o rechaza una sola vez. La identidad siempre sale del token.
 * La validación de datos y la política de archivos viven en ContratoValidator y
 * ContratoArchivoAdjuntador; las consultas, en ContratoService.
 */
@Service
@RequiredArgsConstructor
public class ContratoGestionService {

	private final ContratoRepository contratoRepository;
	private final UserRepository userRepository;
	private final PersonaRepository personaRepository;
	private final SeguimientoRepository seguimientoRepository;
	private final IdentidadService identidad;
	private final ContratoValidator validator;
	private final ContratoArchivoAdjuntador adjuntador;

	@Transactional
	public void crear(String colaboradorUsername, CrearContratoRequest r, ArchivoAdjunto archivo) {
		validator.validar(r);

		Long colaboradorId = identidad.personaIdDe(colaboradorUsername);
		Usuario contratante = userRepository.findByUsername(r.getContratanteUsername().trim())
				.filter(u -> u.getRole() == Role.CONTRATANTE)
				.orElseThrow(() -> BusinessException.noEncontrado("El contratante seleccionado no existe."));
		Long contratanteId = identidad.personaIdDe(contratante.getUsername());

		// Solo a quien te sigue (es la lista que ve el colaborador en el formulario).
		if (!seguimientoRepository.existsByColaborador_IdPersonaAndContratante_IdPersona(colaboradorId,
				contratanteId)) {
			throw BusinessException.prohibido("Solo puedes crear contratos para contratantes que te siguen.");
		}

		Contrato contrato = Contrato.builder().nombre(r.getNombre().trim()).direccion(r.getDireccion().trim())
				.telefono(r.getTelefono().trim()).descripcion(r.getDescripcion().trim())
				.precio(new BigDecimal(r.getPrecio().trim()).stripTrailingZeros().toPlainString())
				.fechaInicio(Date.valueOf(r.getFechaInicio())).fechaFin(Date.valueOf(r.getFechaFin()))
				.estado(EstadoContrato.PENDIENTE).colaborador(personaRepository.getReferenceById(colaboradorId))
				.contratante(personaRepository.getReferenceById(contratanteId)).build();

		adjuntador.adjuntar(contrato, archivo);
		contratoRepository.save(contrato);
	}

	@Transactional
	public void responder(Long idContrato, String contratanteUsername, boolean aceptar) {
		Long persona = identidad.personaIdDe(contratanteUsername);
		EstadoContrato nuevo = aceptar ? EstadoContrato.ACEPTADO : EstadoContrato.RECHAZADO;

		int filas = contratoRepository.responder(idContrato, persona, nuevo, EstadoContrato.PENDIENTE,
				new Date(System.currentTimeMillis()));
		if (filas == 1) {
			return;
		}
		// 0 filas: no es suyo / no existe, o ya fue respondido.
		EstadoContrato actual = contratoRepository.estadoParaContratante(idContrato, persona)
				.orElseThrow(() -> BusinessException.noEncontrado("Contrato no encontrado."));
		String mensaje = switch (actual) {
		case ACEPTADO -> "Este contrato ya fue aceptado.";
		case RECHAZADO -> "Este contrato ya fue rechazado.";
		case PENDIENTE -> "No se pudo registrar la respuesta. Inténtalo de nuevo.";
		};
		throw BusinessException.conflicto(mensaje);
	}
}
