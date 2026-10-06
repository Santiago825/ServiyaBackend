package com.ServiYa.serviya.modelos.modelos.mensaje;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Mensaje tal como lo ve el cliente (REST y WebSocket). Incluye el id, que sirve de cursor. */
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
	private LocalDateTime timestamp;
}
