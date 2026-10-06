package com.ServiYa.serviya.modelos.modelos.resena;

import java.util.List;

import com.ServiYa.serviya.modelos.modelos.ColaboradoR.DetalleColaboradorDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResenaDTO {
	private Long id_reseña;
	private Long id_colaborador;
	private String comentario;
	private int puntuacion;
	private String nombre;


	
	

}
