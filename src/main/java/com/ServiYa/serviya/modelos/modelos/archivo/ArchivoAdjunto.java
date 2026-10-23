package com.ServiYa.serviya.modelos.modelos.archivo;

/**
 * Archivo recibido, ya desligado de la capa web (el controlador convierte el MultipartFile). Así la
 * lógica de negocio no depende de tipos de Spring MVC.
 */
public record ArchivoAdjunto(byte[] contenido, String nombre, String tipo) {

	public boolean estaVacio() {
		return contenido == null || contenido.length == 0;
	}
}
