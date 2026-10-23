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

	    // API REST: JWT en cabecera, sin cookies
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(allowedOrigins);
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
	    config.setAllowCredentials(false);
	    config.setMaxAge(3600L);

	    // Handshake SockJS (/ws/info, xhr_streaming, xhr_send...): sockjs-client usa
	    // withCredentials=true, así que el navegador EXIGE Access-Control-Allow-Credentials: true.
	    // Con credenciales los orígenes deben ser explícitos (nunca "*"): aquí lo son.
	    CorsConfiguration ws = new CorsConfiguration();
	    ws.setAllowedOrigins(allowedOrigins);
	    ws.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
	    ws.setAllowedHeaders(List.of("*"));
	    ws.setAllowCredentials(true);
	    ws.setMaxAge(3600L);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/ws/**", ws);   // primero: el más específico
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
}
