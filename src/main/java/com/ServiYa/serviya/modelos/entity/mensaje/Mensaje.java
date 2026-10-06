package com.ServiYa.serviya.modelos.entity.mensaje;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Mensaje privado entre dos usuarios (por username).
 *
 * REESCRITO (el original estaba lleno de bytes nulos). Se conserva el nombre de tabla
 * "mensaje" y las columnas sender_name / receiver_name / message / timestamp para no
 * perder el historial existente. Los dos índices cubren la consulta paginada por
 * conversación: (emisor, receptor, id) y (receptor, emisor, id).
 */
@Entity
@Table(name = "mensaje", indexes = {
		@Index(name = "idx_mensaje_emisor_receptor_id", columnList = "sender_name, receiver_name, id"),
		@Index(name = "idx_mensaje_receptor_emisor_id", columnList = "receiver_name, sender_name, id") })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mensaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sender_name", nullable = false, length = 50)
	private String senderName;

	@Column(name = "receiver_name", nullable = false, length = 50)
	private String receiverName;

	@Column(nullable = false, length = 1000)
	private String message;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	@PrePersist
	void antesDeGuardar() {
		if (timestamp == null) {
			timestamp = LocalDateTime.now();
		}
	}
}
