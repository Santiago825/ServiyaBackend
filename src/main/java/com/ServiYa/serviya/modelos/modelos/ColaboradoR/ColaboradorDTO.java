package com.ServiYa.serviya.modelos.modelos.ColaboradoR;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColaboradorDTO {
	private Long id_persona;
	private String nombre;
	private String apellido;
	private String telefono;
	private String municipio;
	private String servicio;
	private Long numero_resenas;
	private byte[] foto;
	private int seguido;

}
