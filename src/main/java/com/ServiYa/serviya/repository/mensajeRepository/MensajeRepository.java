package com.ServiYa.serviya.repository.mensajeRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.mensaje.Mensaje;

/**
 * Historial con paginacion por CURSOR (id), no por OFFSET: el costo no crece al ir hacia
 * atras y no se duplican ni se saltan mensajes si llegan nuevos mientras se pagina.
 *
 * Cada consulta cubre UNA direccion (de -> para) con igualdad en las dos primeras columnas
 * de los indices (sender_name, receiver_name, id), asi MySQL recorre el indice hacia atras y
 * se detiene en LIMIT filas. Una unica consulta con (A->B OR B->A) lo impediria y terminaria
 * escaneando la clave primaria. MensajeService mezcla ambas direcciones por id.
 * Ambas devuelven id DESC (mas reciente primero).
 */
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

	@Query("SELECT m FROM Mensaje m WHERE m.senderName = :de AND m.receiverName = :para ORDER BY m.id DESC")
	List<Mensaje> ultimosDe(@Param("de") String de, @Param("para") String para, Pageable pageable);

	@Query("SELECT m FROM Mensaje m WHERE m.senderName = :de AND m.receiverName = :para AND m.id < :beforeId ORDER BY m.id DESC")
	List<Mensaje> anterioresDe(@Param("de") String de, @Param("para") String para, @Param("beforeId") Long beforeId,
			Pageable pageable);
}
