package com.ServiYa.serviya.modelos.modelos.usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSeguidoDTO {
	
	private int idUsuario; 
	private String username;
	private byte[] foto;

}
