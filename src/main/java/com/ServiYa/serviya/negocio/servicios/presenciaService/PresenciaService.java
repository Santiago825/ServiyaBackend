package com.ServiYa.serviya.negocio.servicios.presenciaService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ServiYa.serviya.modelos.modelos.presencia.PresenciaDTO;
import com.ServiYa.serviya.repository.seguimientoRepository.SeguimientoRepository;

import jakarta.annotation.PreDestroy;

/**
 * Presencia en tiempo real basada en sesiones STOMP.
 *
 *  - Un usuario esta "en linea" mientras tenga AL MENOS una sesion WebSocket abierta
 *    (varias pestanas / dispositivos cuentan como una sola presencia).
 *  - Al caer la ultima sesion se espera GRACIA_OFFLINE_MS antes de anunciar "desconectado":
 *    un F5 o un corte breve de red no hace parpadear el punto en los demas.
 *  - Los cambios solo se empujan a los CONTACTOS del usuario (ver SeguimientoRepository.findContactUsernames),
 *    por /user/queue/presence.
 *  - Todos los envios pasan por UN solo hilo y leen el estado en el momento de enviar, asi nunca
 *    llega un "desconectado" viejo despues de un "en linea" nuevo.
 *
 * LIMITE: el estado vive en memoria de ESTA instancia. Con varias instancias del backend habria que
 * moverlo a Redis (o a un broker externo) para que todas vean las mismas sesiones.
 */
@Service
public class PresenciaService {

	private static final Logger log = LoggerFactory.getLogger(PresenciaService.class);
	static final long GRACIA_OFFLINE_MS = 5_000;

	private final NotificadorPresencia notificador;
	private final SeguimientoRepository seguimientoRepository;

	private final ScheduledExecutorService hilo = Executors.newSingleThreadScheduledExecutor(r -> {
		Thread t = new Thread(r, "presencia");
		t.setDaemon(true);
		return t;
	});

	// Todo lo siguiente se protege con "synchronized (this)".
	private final Map<String, Set<String>> sesionesPorUsuario = new HashMap<>();
	private final Map<String, String> usuarioPorSesion = new HashMap<>();
	private final Map<String, ScheduledFuture<?>> desconexionPendiente = new HashMap<>();
	private final Set<String> enLinea = new HashSet<>(); // estado que ven los contactos

	public PresenciaService(NotificadorPresencia notificador, SeguimientoRepository seguimientoRepository) {
		this.notificador = notificador;
		this.seguimientoRepository = seguimientoRepository;
	}

	public void sesionIniciada(String sessionId, String username) {
		boolean cambio;
		synchronized (this) {
			usuarioPorSesion.put(sessionId, username);
			sesionesPorUsuario.computeIfAbsent(username, k -> new HashSet<>()).add(sessionId);
			ScheduledFuture<?> pendiente = desconexionPendiente.remove(username);
			if (pendiente != null) {
				pendiente.cancel(false);
			}
			cambio = enLinea.add(username);
		}
		if (cambio) {
			notificarContactos(username);
		}
	}

	public synchronized void sesionTerminada(String sessionId) {
		String username = usuarioPorSesion.remove(sessionId);
		if (username == null) {
			return; // sesion que nunca llego a autenticarse
		}
		Set<String> sesiones = sesionesPorUsuario.get(username);
		if (sesiones == null) {
			return;
		}
		sesiones.remove(sessionId);
		if (!sesiones.isEmpty()) {
			return; // sigue conectado desde otra pestana/dispositivo
		}
		sesionesPorUsuario.remove(username);
		desconexionPendiente.put(username,
				hilo.schedule(() -> confirmarDesconexion(username), GRACIA_OFFLINE_MS, TimeUnit.MILLISECONDS));
	}

	public synchronized boolean estaEnLinea(String username) {
		return enLinea.contains(username);
	}

	/** Cuales de los contactos de {@code username} estan en linea ahora (foto inicial para el cliente). */
	public List<String> enLineaEntreContactos(String username) {
		List<String> contactos = seguimientoRepository.findContactUsernames(username);
		synchronized (this) {
			return contactos.stream().filter(enLinea::contains).toList();
		}
	}

	private void confirmarDesconexion(String username) {
		boolean cambio;
		synchronized (this) {
			desconexionPendiente.remove(username);
			if (sesionesPorUsuario.containsKey(username)) {
				return; // se reconecto durante la gracia
			}
			cambio = enLinea.remove(username);
		}
		if (cambio) {
			enviarEstado(username);
		}
	}

	private void notificarContactos(String username) {
		hilo.execute(() -> enviarEstado(username));
	}

	/** Se ejecuta SIEMPRE en el hilo "presencia": lee el estado vigente y lo empuja a los contactos. */
	private void enviarEstado(String username) {
		try {
			PresenciaDTO evento = new PresenciaDTO(username, estaEnLinea(username));
			for (String contacto : seguimientoRepository.findContactUsernames(username)) {
				notificador.notificar(contacto, evento);
			}
		} catch (Exception e) {
			log.warn("No se pudo difundir la presencia de {}", username, e);
		}
	}

	@PreDestroy
	void cerrar() {
		hilo.shutdownNow();
	}
}
