package com.ServiYa.serviya.modelos.modelos.archivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoDTO {
	
	private String nombre;
	private String tipoContenido;
	private byte[] contenidoBase64;
	

}
