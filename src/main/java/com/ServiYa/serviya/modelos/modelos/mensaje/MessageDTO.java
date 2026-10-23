package com.ServiYa.serviya.modelos.modelos.mensaje;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Mensaje tal como lo ve el cliente (REST y WebSocket). Incluye el id, que sirve de cursor.
 * El timestamp es un Instant (se serializa como "2026-10-06T21:08:00Z"): con LocalDateTime el
 * JSON no llevaba zona horaria y el navegador lo interpretaba en SU zona (horas corridas).
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MessageDTO {
	private Long id;
	private String senderName;
	private String receiverName;
	private String message;
	private Status status;
	private Instant timestamp;
}
