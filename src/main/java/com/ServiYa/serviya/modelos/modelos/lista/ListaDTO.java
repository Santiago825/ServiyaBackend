package com.ServiYa.serviya.modelos.modelos.lista;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Envoltorio estándar de respuesta de listas. RESTAURADO.
 * (Se quitó el @Repository que tenía por error: un DTO no es un bean de Spring.)
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ListaDTO {
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private int totalRegistros;
	private int totalPaginas;
	private List<?> lista;
}
