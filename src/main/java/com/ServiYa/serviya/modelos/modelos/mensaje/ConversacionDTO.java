package com.ServiYa.serviya.modelos.modelos.mensaje;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Vista previa de una conversación: con quién y cuál fue el último mensaje (enviado o recibido). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversacionDTO {
	private String interlocutor;
	private MessageDTO ultimoMensaje;
}
