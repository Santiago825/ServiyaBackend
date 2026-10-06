package com.ServiYa.serviya.util;

import java.util.List;

import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;

/** Fábrica de respuestas ListaDTO: elimina el bloque repetido en cada servicio. */
public final class ListaDTOs {

	private ListaDTOs() {
	}

	/** Lista vacía -> INF_DAT_NO_ENC + mensaje; con datos -> OK. */
	public static ListaDTO de(List<?> datos, String mensajeSiVacia) {
		ListaDTO r = new ListaDTO();
		r.setLista(datos);
		if (datos == null || datos.isEmpty()) {
			r.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
			r.setMensajeRespuesta(mensajeSiVacia);
		} else {
			r.setTotalRegistros(datos.size());
			r.setTotalPaginas(1);
			r.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
		}
		return r;
	}

	public static ListaDTO ok(String mensaje) {
		ListaDTO r = new ListaDTO();
		r.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
		r.setMensajeRespuesta(mensaje);
		return r;
	}

	public static ListaDTO errorNoControlado() {
		ListaDTO r = new ListaDTO();
		r.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
		r.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
		return r;
	}
}
