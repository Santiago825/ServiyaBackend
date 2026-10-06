package com.ServiYa.serviya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ServiYa.serviya.jwt.JwtAuthenticationFilter;
import com.ServiYa.serviya.modelos.modelos.usuarios.Role;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * REESCRITO. Antes: todos los GET, /private/** y /public/** estaban en permitAll, es decir
 * el filtro JWT existía pero NADA exigía un token. Ahora:
 *
 *  - /public/** (login, registro, validar-username) y /ws/** (el handshake; el token se
 *    valida en el frame STOMP CONNECT)  -> públicos.
 *  - Catálogos del asistente de registro (servicios, departamentos, municipios, tipos de
 *    documento) -> públicos, porque el frontend los pide ANTES de que exista sesión.
 *  - seguir/dejar de seguir y "colaboradores seguidos" -> solo CONTRATANTE;
 *    "seguidores del colaborador" -> solo COLABORADOR.
 *  - Todo lo demás -> requiere token válido (401 JSON si falta/expiró).
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String PRIV = ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	private final RestSecurityErrorHandlers errorHandlers;
	private final org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) // API stateless con JWT en cabecera, sin cookies
				.cors(cors -> cors.configurationSource(corsConfigurationSource))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(e -> e.authenticationEntryPoint(errorHandlers).accessDeniedHandler(errorHandlers))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(ConstantesSeguridadPathRest.PATH_SERVICIOS_PUBLICOS + "/**").permitAll()
						.requestMatchers(ConstantesSeguridadPathRest.PATH_WS + "/**").permitAll()
						// catálogos del registro (se consumen antes de tener sesión)
						.requestMatchers(HttpMethod.GET,
								PRIV + ConstantesSeguridadPathRest.PATH_OBTENER_SERVICIOS,
								PRIV + ConstantesSeguridadPathRest.PATH_OBTENER_DEPARTAMENTO,
								PRIV + ConstantesSeguridadPathRest.PATH_OBTENER_MUNICIPIO + "/*",
								PRIV + ConstantesSeguridadPathRest.PATH_OBTENER_TIPO_DOCUMENTO)
						.permitAll()
						// reglas por rol
						.requestMatchers(HttpMethod.POST,
								PRIV + ConstantesSeguridadPathRest.PATH_SEGUIR_COLABORADOR,
								PRIV + ConstantesSeguridadPathRest.PATH_DEJAR_SEGUIR_COLABORADOR)
						.hasAuthority(Role.CONTRATANTE.name())
						.requestMatchers(HttpMethod.GET,
								PRIV + ConstantesSeguridadPathRest.PATH_OBTENER_COLABORADORES_SEGUIDOS + "/**")
						.hasAuthority(Role.CONTRATANTE.name())
						.requestMatchers(HttpMethod.GET,
								PRIV + ConstantesSeguridadPathRest.PATH_OBTENER_SEGUIDORES_COLABORADOR + "/**")
						.hasAuthority(Role.COLABORADOR.name())
						.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable);
		return http.build();
	}
}
