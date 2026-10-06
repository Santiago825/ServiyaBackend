package com.ServiYa.serviya.modelos.modelos.mensaje;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lo único que el cliente puede enviar por WebSocket. NO incluye el remitente:
 * el servidor lo toma del token JWT (antes se confiaba en "senderName" del payload,
 * lo que permitía suplantar a cualquier usuario).
 */
@Getter
@Setter
@NoArgsConstructor
public class MensajeEntranteDTO {
	private String receiverName;
	private String message;
}
