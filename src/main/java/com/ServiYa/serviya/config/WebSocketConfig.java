package com.ServiYa.serviya.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * Endpoint STOMP: /ws (SockJS). Colas por usuario: /user/queue/**.
 * Orígenes: la misma propiedad app.cors.allowed-origins que usa REST (antes estaban fijos).
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final WebSocketAuthInterceptor authInterceptor;

	@Value("${app.cors.allowed-origins:http://localhost:4200}")
	private List<String> allowedOrigins;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(ConstantesSeguridadPathRest.PATH_WS)
				.setAllowedOriginPatterns(allowedOrigins.toArray(new String[0])).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes(ConstantesSeguridadPathRest.WS_APP_PREFIX);
		registry.enableSimpleBroker(ConstantesSeguridadPathRest.WS_QUEUE_PREFIX);
		registry.setUserDestinationPrefix(ConstantesSeguridadPathRest.WS_USER_PREFIX);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(authInterceptor);
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.setMessageSizeLimit(16 * 1024); // un mensaje de chat no necesita más
	}
}
