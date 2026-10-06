package com.ServiYa.serviya.modelos.modelos.usuarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDetalle {
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String telefono;
	private Long idDocumento;
	private Long numeroDocumento;
	private Long idDepartamento;
	private Long idMunicipio;
	private byte[] foto;
	
}
