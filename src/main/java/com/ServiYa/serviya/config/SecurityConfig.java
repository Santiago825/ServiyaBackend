package com.ServiYa.serviya.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfigurationSource;

import com.ServiYa.serviya.jwt.JwtAuthenticationFilter;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;


/**
 * REESCRITO. Antes: todos los GET, /private/** y /public/** estaban en permitAll, es decir
 * el filtro JWT existía pero NADA exigía un token. Ahora:
 *
 *  - /public/** (login, registro, validar-username) y /ws/** (el handshake; el token se
 *    valida en el frame STOMP CONNECT)  -> públicos.
 *  - Catálogos del asistente de registro (servicios, departamentos, municipios, tipos de
 *    documento) -> públicos, porque el frontend los pide ANTES de que exista sesión.
 *  - Las reglas por ROL (CONTRATANTE / COLABORADOR) se declaran con @PreAuthorize en cada
 *    endpoint (ver ReglasAcceso): añadir un endpoint ya no obliga a editar esta clase.
 *  - Todo lo demás -> requiere token válido (401 JSON si falta/expiró).
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private static final String PRIV = ConstantesSeguridadPathRest.PATH_SERVICIOS_PRIVADOS;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	private final RestSecurityErrorHandlers errorHandlers;
	private final CorsConfigurationSource corsConfigurationSource;

	/**
	 * Constructor explícito: hay DOS beans CorsConfigurationSource (el nuestro y el
	 * mvcHandlerMappingIntrospector de Spring MVC). Con @Qualifier ya no se depende de que el
	 * compilador conserve los nombres de parámetros (-parameters), que el IDE puede omitir.
	 */
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
			AuthenticationProvider authenticationProvider, RestSecurityErrorHandlers errorHandlers,
			@Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfigurationSource) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.authenticationProvider = authenticationProvider;
		this.errorHandlers = errorHandlers;
		this.corsConfigurationSource = corsConfigurationSource;
	}

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
						// las reglas por rol viven en cada endpoint (@PreAuthorize + ReglasAcceso)
						.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable);
		return http.build();
	}
}
