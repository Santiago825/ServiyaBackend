package com.ServiYa.serviya.modelos.modelos.usuarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	Long id;
	String username;
	String firstname;
	String lastname;
	String country;
}
