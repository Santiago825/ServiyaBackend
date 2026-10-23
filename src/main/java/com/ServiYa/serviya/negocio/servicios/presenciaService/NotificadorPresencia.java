package com.ServiYa.serviya.negocio.servicios.presenciaService;

import com.ServiYa.serviya.modelos.modelos.presencia.PresenciaDTO;

/**
 * Cómo se avisa a un usuario de un cambio de presencia. PresenciaService depende de esta
 * abstracción, no de STOMP: se puede cambiar el canal (o probar el servicio) sin tocar su lógica.
 */
public interface NotificadorPresencia {

	void notificar(String destinatario, PresenciaDTO evento);
}
