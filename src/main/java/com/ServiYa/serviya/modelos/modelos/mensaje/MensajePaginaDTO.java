package com.ServiYa.serviya.modelos.modelos.mensaje;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Página de historial de chat (paginación por cursor).
 * - mensajes: orden CRONOLÓGICO ascendente (antiguo -> reciente), listos para mostrar.
 * - hayMas: true si existen mensajes aún más antiguos.
 * - siguienteCursor: id del mensaje más antiguo de la página; se envía como
 *   "beforeId" para pedir la página anterior. Es null si la página está vacía.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensajePaginaDTO {
	private List<MessageDTO> mensajes;
	private boolean hayMas;
	private Long siguienteCursor;
}
