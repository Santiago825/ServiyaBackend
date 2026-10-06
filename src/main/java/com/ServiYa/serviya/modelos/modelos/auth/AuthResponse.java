package com.ServiYa.serviya.modelos.modelos.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	private Integer idUsuario;
	private String username;
	private Long idPersona;
	private String registro_completo;
	private String Rol;
	private String token;
    /** The codigo respuesta. */
    private String codigoRespuesta;

    /** The descripcion respuesta. */
    private String mensajeRespuesta;
    private byte[] foto;
	

}
