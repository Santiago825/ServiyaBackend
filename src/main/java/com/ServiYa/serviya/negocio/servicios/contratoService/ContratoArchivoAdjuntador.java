package com.ServiYa.serviya.negocio.servicios.contratoService;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.entity.contrato.Contrato;
import com.ServiYa.serviya.modelos.modelos.archivo.ArchivoAdjunto;

/**
 * Política de archivos de un contrato (tamaño y tipo permitidos) y su asignación a la entidad.
 * Trabaja con {@link ArchivoAdjunto}, no con MultipartFile: no depende de la capa web.
 */
@Component
public class ContratoArchivoAdjuntador {

	static final long MAX_BYTES = 5L * 1024 * 1024;
	private static final Set<String> TIPOS = Set.of("application/pdf", "application/msword",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document");

	/** El archivo es opcional: sin archivo (o vacío) no hace nada. */
	public void adjuntar(Contrato contrato, ArchivoAdjunto archivo) {
		if (archivo == null || archivo.estaVacio()) {
			return;
		}
		if (archivo.contenido().length > MAX_BYTES) {
			throw BusinessException.validacion("El archivo no puede superar 5 MB.");
		}
		String tipo = archivo.tipo();
		if (tipo == null || !TIPOS.contains(tipo)) {
			throw BusinessException.validacion("El archivo debe ser PDF o Word (.doc, .docx).");
		}
		String nombre = StringUtils.cleanPath(archivo.nombre() == null ? "contrato" : archivo.nombre());
		contrato.setArch_contrato(archivo.contenido());
		contrato.setNombreArchivo(nombre.length() > 255 ? nombre.substring(0, 255) : nombre);
		contrato.setTipoArchivo(tipo);
	}
}
