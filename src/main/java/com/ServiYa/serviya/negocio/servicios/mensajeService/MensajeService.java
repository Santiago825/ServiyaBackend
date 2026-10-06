package com.ServiYa.serviya.negocio.servicios.mensajeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.entity.mensaje.Mensaje;
import com.ServiYa.serviya.modelos.modelos.mensaje.MensajePaginaDTO;
import com.ServiYa.serviya.modelos.modelos.mensaje.MessageDTO;
import com.ServiYa.serviya.modelos.modelos.mensaje.Status;
import com.ServiYa.serviya.repository.mensajeRepository.MensajeRepository;
import com.ServiYa.serviya.repository.usuarioRepository.UserRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

/**
 * REESCRITO. Antes devolvía TODO el historial en una sola consulta y sin control de acceso.
 * Ahora:
 *  - guardar(): valida y persiste; el remitente lo decide el servidor (token), no el cliente.
 *  - obtenerConversacion(): paginación por cursor; el "usuario actual" lo pone el controlador
 *    desde el token, así nadie puede leer conversaciones ajenas.
 */
@Service
@RequiredArgsConstructor
public class MensajeService {

	public static final int TAMANO_POR_DEFECTO = 30;
	public static final int TAMANO_MAXIMO = 100;
	public static final int LARGO_MAXIMO_MENSAJE = 1000;

	private final MensajeRepository mensajeRepository;
	private final UserRepository userRepository;

	@Transactional
	public MessageDTO guardar(String remitente, String destinatario, String texto) {
		String contenido = texto == null ? "" : texto.strip();
		if (contenido.isEmpty()) {
			throw chatError(ConstantesCodigosError.MENSAJE_CHAT_VACIO);
		}
		if (contenido.length() > LARGO_MAXIMO_MENSAJE) {
			throw chatError(ConstantesCodigosError.MENSAJE_CHAT_LARGO);
		}
		String receptor = destinatario == null ? "" : destinatario.strip();
		if (receptor.isEmpty() || !userRepository.existsByUsername(receptor)) {
			throw chatError(ConstantesCodigosError.MENSAJE_CHAT_DESTINATARIO_INVALIDO);
		}
		if (receptor.equals(remitente)) {
			throw chatError(ConstantesCodigosError.MENSAJE_CHAT_A_SI_MISMO);
		}

		Mensaje guardado = mensajeRepository
				.save(Mensaje.builder().senderName(remitente).receiverName(receptor).message(contenido).build());
		return aDto(guardado);
	}

	/**
	 * @param beforeId cursor: devuelve mensajes con id menor. Null = los más recientes.
	 * @param size     cantidad pedida (se acota a 1..TAMANO_MAXIMO).
	 */
	@Transactional(readOnly = true)
	public MensajePaginaDTO obtenerConversacion(String usuarioActual, String interlocutor, Long beforeId,
			Integer size) {
		if (interlocutor == null || interlocutor.isBlank()) {
			throw chatError(ConstantesCodigosError.MENSAJE_CHAT_DESTINATARIO_INVALIDO);
		}
		int limite = size == null ? TAMANO_POR_DEFECTO : Math.max(1, Math.min(size, TAMANO_MAXIMO));

		// Se pide uno de mas para saber si quedan mensajes mas antiguos sin hacer un COUNT.
		// Se consulta cada direccion por separado (usa el indice) y se mezclan por id.
		PageRequest pagina = PageRequest.of(0, limite + 1);
		List<Mensaje> recientesPrimero = new ArrayList<>();
		if (beforeId == null) {
			recientesPrimero.addAll(mensajeRepository.ultimosDe(usuarioActual, interlocutor, pagina));
			recientesPrimero.addAll(mensajeRepository.ultimosDe(interlocutor, usuarioActual, pagina));
		} else {
			recientesPrimero.addAll(mensajeRepository.anterioresDe(usuarioActual, interlocutor, beforeId, pagina));
			recientesPrimero.addAll(mensajeRepository.anterioresDe(interlocutor, usuarioActual, beforeId, pagina));
		}
		recientesPrimero.sort(Comparator.comparing(Mensaje::getId).reversed());
		if (recientesPrimero.size() > limite + 1) {
			recientesPrimero = new ArrayList<>(recientesPrimero.subList(0, limite + 1));
		}

		boolean hayMas = recientesPrimero.size() > limite;
		List<Mensaje> pagina_ = new ArrayList<>(hayMas ? recientesPrimero.subList(0, limite) : recientesPrimero);
		Collections.reverse(pagina_); // a orden cronológico ascendente para pintar

		List<MessageDTO> dtos = pagina_.stream().map(this::aDto).toList();
		Long cursor = dtos.isEmpty() ? null : dtos.get(0).getId();
		return new MensajePaginaDTO(dtos, hayMas, cursor);
	}

	private MessageDTO aDto(Mensaje m) {
		return MessageDTO.builder().id(m.getId()).senderName(m.getSenderName()).receiverName(m.getReceiverName())
				.message(m.getMessage()).status(Status.MESSAGE).timestamp(m.getTimestamp()).build();
	}

	private BusinessException chatError(String mensaje) {
		return new BusinessException(HttpStatus.BAD_REQUEST, ConstantesCodigosError.CODIGO_CHAT_INVALIDO, mensaje);
	}
}
