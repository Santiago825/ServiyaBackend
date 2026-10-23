package com.ServiYa.serviya.util;

import java.util.Locale;

/** Convierte lo que escribe la persona en un patrón LIKE seguro para búsquedas "contiene". */
public final class PatronLike {

	private static final int LONGITUD_MAXIMA = 100;

	private PatronLike() {
	}

	/**
	 * "Ana" -> "%ana%". Pasa a minúsculas, recorta espacios y largo, y escapa \ % _ para que se busquen
	 * literalmente (MySQL usa "\" como escape por defecto). Sin texto -> "%%" (coincide con todo).
	 */
	public static String contiene(String texto) {
		String t = texto == null ? "" : texto.trim().toLowerCase(Locale.ROOT);
		if (t.length() > LONGITUD_MAXIMA) {
			t = t.substring(0, LONGITUD_MAXIMA);
		}
		return "%" + t.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_") + "%";
	}
}
