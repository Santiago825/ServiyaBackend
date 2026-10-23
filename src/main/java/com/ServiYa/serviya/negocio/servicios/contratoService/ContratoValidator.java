package com.ServiYa.serviya.negocio.servicios.contratoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ServiYa.serviya.ExceptionHandler.BusinessException;
import com.ServiYa.serviya.modelos.modelos.contrato.CrearContratoRequest;

/** Reglas de validación de los datos de un contrato nuevo (no toca la base de datos). */
@Component
public class ContratoValidator {

	private static final Pattern TELEFONO = Pattern.compile("^[0-9+()\\-\\s]{7,20}$");

	public void validar(CrearContratoRequest r) {
		if (!StringUtils.hasText(r.getContratanteUsername())) {
			throw BusinessException.validacion("Selecciona el contratante.");
		}
		texto(r.getNombre(), "El título del contrato", 150);
		texto(r.getDireccion(), "La dirección", 255);
		texto(r.getDescripcion(), "La descripción", 255);
		if (!StringUtils.hasText(r.getTelefono()) || !TELEFONO.matcher(r.getTelefono().trim()).matches()) {
			throw BusinessException.validacion("El teléfono no es válido.");
		}
		validarPrecio(r.getPrecio());
		validarFechas(r.getFechaInicio(), r.getFechaFin());
	}

	private static void validarPrecio(String precio) {
		try {
			if (new BigDecimal(precio == null ? "" : precio.trim()).signum() <= 0) {
				throw BusinessException.validacion("El precio debe ser mayor a cero.");
			}
		} catch (NumberFormatException e) {
			throw BusinessException.validacion("El precio no es válido.");
		}
	}

	private static void validarFechas(LocalDate inicio, LocalDate fin) {
		if (inicio == null || fin == null) {
			throw BusinessException.validacion("Indica la fecha de inicio y la de fin.");
		}
		if (inicio.isBefore(LocalDate.now())) {
			throw BusinessException.validacion("La fecha de inicio no puede ser anterior a hoy.");
		}
		if (fin.isBefore(inicio)) {
			throw BusinessException.validacion("La fecha de fin no puede ser anterior a la de inicio.");
		}
	}

	private static void texto(String valor, String campo, int max) {
		if (!StringUtils.hasText(valor)) {
			throw BusinessException.validacion(campo + " es obligatorio.");
		}
		if (valor.trim().length() > max) {
			throw BusinessException.validacion(campo + " no puede superar " + max + " caracteres.");
		}
	}
}
