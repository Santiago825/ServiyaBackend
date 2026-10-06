package com.ServiYa.serviya.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ServiYa.serviya.negocio.servicios.auth.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Autentica cada request con "Authorization: Bearer <jwt>".
 * Un token inválido/expirado NO lanza excepción (antes rompía la petición con un 500/403
 * confuso): simplemente no autentica y el AuthenticationEntryPoint responde 401 en JSON
 * si el recurso lo exigía.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String PREFIJO = "Bearer ";

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header != null && header.startsWith(PREFIJO)
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			String token = header.substring(PREFIJO.length()).trim();
			try {
				String username = jwtService.getUsernameFromToken(token);
				UserDetails user = userDetailsService.loadUserByUsername(username);
				if (jwtService.isTokenValid(token, user)) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
							user.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
				// token inválido, manipulado, expirado o de un usuario que ya no existe
				request.setAttribute("jwt.error", e instanceof io.jsonwebtoken.ExpiredJwtException ? "EXPIRED" : "INVALID");
			}
		}
		chain.doFilter(request, response);
	}
}
