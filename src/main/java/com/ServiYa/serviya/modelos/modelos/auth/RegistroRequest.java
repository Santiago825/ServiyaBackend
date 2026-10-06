package com.ServiYa.serviya.modelos.modelos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** Se añaden validaciones; el password y los archivos se excluyen del toString. */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequest {

	@NotBlank(message = "El usuario es obligatorio")
	@Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres")
	@Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "El usuario solo admite letras, números, punto, guion y guion bajo")
	private String username;

	@NotBlank(message = "El correo es obligatorio")
	@Email(message = "El correo no es válido")
	@Size(max = 100, message = "El correo no puede superar 100 caracteres")
	private String email;

	// BCrypt solo usa los primeros 72 bytes
	@ToString.Exclude
	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 6, max = 72, message = "La contraseña debe tener entre 6 y 72 caracteres")
	private String password;

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 100)
	private String nombre;

	@NotBlank(message = "El apellido es obligatorio")
	@Size(max = 100)
	private String apellido;

	@NotBlank(message = "El teléfono es obligatorio")
	@Pattern(regexp = "^[0-9+() -]{7,20}$", message = "El teléfono no es válido")
	private String telefono;

	@NotNull(message = "El municipio es obligatorio")
	private Long idMunicipio;

	@NotNull(message = "El tipo de documento es obligatorio")
	private Long idDocumento;

	@NotNull(message = "El número de documento es obligatorio")
	private Long numeroDocumento;

	@NotBlank(message = "El rol es obligatorio")
	@Pattern(regexp = "(?i)^(Contratante|Colaborador)$", message = "El rol debe ser Contratante o Colaborador")
	private String rol;

	@Size(max = 1000, message = "La descripción no puede superar 1000 caracteres")
	private String descripcion;

	/** Obligatorio solo para colaboradores (se valida en UserService). */
	private Long idServicio;

	@ToString.Exclude
	@Size(max = 2_097_152, message = "La foto no puede superar 2 MB")
	private byte[] foto;

	@ToString.Exclude
	@Size(max = 5_242_880, message = "La hoja de vida no puede superar 5 MB")
	private byte[] cv;
}
