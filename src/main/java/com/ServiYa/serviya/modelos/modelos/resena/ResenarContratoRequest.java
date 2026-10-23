package com.ServiYa.serviya.modelos.modelos.resena;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** Cuerpo de POST resenar_contrato/{id}. El contratante sale del token, no del cuerpo. */
@Data
public class ResenarContratoRequest {

	@Min(value = 1, message = "La puntuación mínima es 1.")
	@Max(value = 5, message = "La puntuación máxima es 5.")
	private int puntuacion;

	@Size(max = 255, message = "El comentario no puede superar 255 caracteres.")
	private String comentario;
}
