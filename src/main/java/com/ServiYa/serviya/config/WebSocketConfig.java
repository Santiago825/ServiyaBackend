package com.ServiYa.serviya.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.ServiYa.serviya.util.ConstantesSeguridadPathRest;

import lombok.RequiredArgsConstructor;

/**
 * Endpoint STOMP: /ws (SockJS). Colas por usuario: /user/queue/**.
 * Orígenes: la misma propiedad app.cors.allowed-origins que usa REST.
 *
 * CAMBIO: el broker simple ahora envía/espera HEARTBEATS STOMP. Sin un TaskScheduler el
 * servidor negociaba heart-beat 0,0, así que una conexión "muerta" (portátil que se
 * suspende, WiFi que se cae) no se detectaba hasta que el SO cerraba el socket, y la
 * presencia "En línea" quedaba pegada durante minutos.
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private static final long HEARTBEAT_MS = 10_000;

	private final WebSocketAuthInterceptor authInterceptor;

	/**
	 * Scheduler PROPIO del heartbeat (no es un bean: no puede chocar con otros TaskScheduler del
	 * contexto ni depender de nombres de beans internos de Spring).
	 */
	private final ThreadPoolTaskScheduler heartbeatScheduler = crearScheduler();

	@Value("${app.cors.allowed-origins:http://localhost:4200}")
	private List<String> allowedOrigins;

	private static ThreadPoolTaskScheduler crearScheduler() {
		ThreadPoolTaskScheduler ts = new ThreadPoolTaskScheduler();
		ts.setPoolSize(1);
		ts.setThreadNamePrefix("ws-heartbeat-");
		ts.setDaemon(true);
		ts.initialize();
		return ts;
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(ConstantesSeguridadPathRest.PATH_WS)
				.setAllowedOriginPatterns(allowedOrigins.toArray(new String[0])).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes(ConstantesSeguridadPathRest.WS_APP_PREFIX);
		registry.enableSimpleBroker(ConstantesSeguridadPathRest.WS_QUEUE_PREFIX)
				.setHeartbeatValue(new long[] { HEARTBEAT_MS, HEARTBEAT_MS })
				.setTaskScheduler(this.heartbeatScheduler);
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
