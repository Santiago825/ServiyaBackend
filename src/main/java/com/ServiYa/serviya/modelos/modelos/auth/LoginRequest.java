package com.ServiYa.serviya.modelos.modelos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "El usuario es obligatorio")
	@Size(max = 50, message = "El usuario no puede superar 50 caracteres")
	private String username;

	@ToString.Exclude
	@NotBlank(message = "La contraseña es obligatoria")
	@Size(max = 72, message = "La contraseña no puede superar 72 caracteres")
	private String password;
}
