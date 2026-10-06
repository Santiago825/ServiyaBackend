package com.ServiYa.serviya.modelos.modelos.usuarios;

import java.time.LocalDateTime;

import com.ServiYa.serviya.modelos.entity.usuario.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	int id_usuario;
	String username;
	String registro_completo;
	String role;
	Long idPersona;

}
