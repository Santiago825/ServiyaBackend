package com.ServiYa.serviya.modelos.modelos.presencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Evento de presencia que se empuja por /user/queue/presence a los contactos del usuario. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresenciaDTO {
	private String username;
	private boolean enLinea;
}
