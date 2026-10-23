package com.ServiYa.serviya.modelos.modelos.presencia;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Foto inicial: cuáles de MIS contactos están en línea ahora mismo. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresenciaSnapshotDTO {
	private List<String> enLinea;
}
