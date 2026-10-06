package com.ServiYa.serviya.modelos.modelos.ColaboradoR;

import java.math.BigDecimal;
import java.util.List;
import com.ServiYa.serviya.modelos.modelos.resena.ResenaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleColaboradorDTO {
	private Long id_persona;
	private String nombre;
	private String apellido;
	private String telefono;
	private String municipio;
	private String servicio;
	private String username;
	private Long numero_resenas;
	private List<ResenaDTO> resena;
	private byte[] foto; 
	private String descripcion;
	private Long seguido;

}
