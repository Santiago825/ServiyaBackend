package com.ServiYa.serviya.util;

/**
 * Expresiones de acceso por rol para @PreAuthorize. Cada controlador declara su propia regla junto
 * al endpoint: añadir un endpoint ya NO exige editar SecurityConfig (principio abierto/cerrado).
 * Los nombres deben coincidir con {@code Role}.
 */
public final class ReglasAcceso {

	public static final String SOLO_CONTRATANTE = "hasAuthority('CONTRATANTE')";
	public static final String SOLO_COLABORADOR = "hasAuthority('COLABORADOR')";

	private ReglasAcceso() {
	}
}
