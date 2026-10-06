package com.ServiYa.serviya.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * ÚNICA fuente de CORS. Antes había un WebMvcConfigurer + varios @CrossOrigin con
 * "localhost:4200" fijo, y nada de eso estaba integrado con Spring Security (las
 * respuestas de error de seguridad salían sin cabeceras CORS y el navegador las mostraba
 * como "error de CORS"). Ahora SecurityConfig usa este bean.
 * Orígenes permitidos: app.cors.allowed-origins (lista separada por comas).
 */
@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource(
			@Value("${app.cors.allowed-origins:http://localhost:4200}") List<String> allowedOrigins) {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(allowedOrigins);
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
		config.setAllowCredentials(false); // el JWT viaja en la cabecera Authorization, no en cookies
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
