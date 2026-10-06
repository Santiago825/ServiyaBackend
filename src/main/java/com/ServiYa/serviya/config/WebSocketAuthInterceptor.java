package com.ServiYa.serviya.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ServiYa.serviya.negocio.servicios.auth.JwtService;
import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

/**
 * Autentica el WebSocket. Antes NO había autenticación: cualquiera podía conectarse y el
 * remitente de cada mensaje era el "senderName" que mandara el propio cliente
 * (suplantación trivial).
 *
 *  - CONNECT: exige la cabecera STOMP "Authorization: Bearer <jwt>" y fija el Principal.
 *  - SUBSCRIBE: solo a /user/queue/** (colas personales; Spring las liga al Principal, por
 *    lo que nadie puede escuchar la cola de otro).
 *  - SEND: solo si hay Principal.
 */
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		if (accessor == null || accessor.getCommand() == null) {
			return message;
		}

		StompCommand command = accessor.getCommand();
		if (StompCommand.CONNECT.equals(command)) {
			autenticar(accessor);
		} else if (StompCommand.SUBSCRIBE.equals(command)) {
			exigirUsuario(accessor);
			String destino = accessor.getDestination();
			String permitido = ConstantesSeguridadPathRest.WS_USER_PREFIX + ConstantesSeguridadPathRest.WS_QUEUE_PREFIX
					+ "/";
			if (destino == null || !destino.startsWith(permitido)) {
				throw new MessagingException("Suscripción no permitida");
			}
		} else if (StompCommand.SEND.equals(command)) {
			exigirUsuario(accessor);
		}
		return message;
	}

	private void autenticar(StompHeaderAccessor accessor) {
		String header = accessor.getFirstNativeHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			throw new MessagingException("Token requerido");
		}
		String token = header.substring(7).trim();
		try {
			String username = jwtService.getUsernameFromToken(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			if (!jwtService.isTokenValid(token, user)) {
				throw new MessagingException("Token inválido");
			}
			accessor.setUser(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		} catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
			throw new MessagingException("Token inválido o expirado");
		}
	}

	private void exigirUsuario(StompHeaderAccessor accessor) {
		if (accessor.getUser() == null) {
			throw new MessagingException("No autenticado");
		}
	}
}
