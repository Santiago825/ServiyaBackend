package com.ServiYa.serviya.repository.resenaRepository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.resena.Resena;
import com.ServiYa.serviya.modelos.modelos.resena.ResenaRecibidaDTO;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

	boolean existsByContrato_IdContrato(Long idContrato);

	/** Cuántas reseñas tiene un colaborador por cada puntuación: filas [puntuacion, cantidad]. */
	@Query("SELECT r.puntuacion, COUNT(r) FROM Resena r WHERE r.colaborador.idPersona = :colaborador GROUP BY r.puntuacion")
	List<Object[]> distribucionPuntuaciones(@Param("colaborador") Long colaborador);

	/**
	 * Reseñas que RECIBIÓ un colaborador, de la más reciente a la más antigua (id descendente: la
	 * tabla no guarda fecha), paginadas. `q` es un patrón LIKE ya en minúsculas ("%texto%") que se
	 * busca en: nombre del contratante, nombre del contrato, descripción del contrato y comentario.
	 */
	@Query(value = """
			SELECT new com.ServiYa.serviya.modelos.modelos.resena.ResenaRecibidaDTO(
			    r.idResena, r.puntuacion, r.comentario, c.idContrato, c.nombre, c.descripcion,
			    CONCAT(con.nombre, ' ', con.apellido))
			FROM Resena r JOIN r.contrato c JOIN r.contratante con
			WHERE r.colaborador.idPersona = :colaborador
			  AND (LOWER(CONCAT(con.nombre, ' ', con.apellido)) LIKE :q
			       OR LOWER(c.nombre) LIKE :q
			       OR LOWER(COALESCE(c.descripcion, '')) LIKE :q
			       OR LOWER(COALESCE(r.comentario, '')) LIKE :q)
			ORDER BY r.idResena DESC
			""", countQuery = """
			SELECT COUNT(r)
			FROM Resena r JOIN r.contrato c JOIN r.contratante con
			WHERE r.colaborador.idPersona = :colaborador
			  AND (LOWER(CONCAT(con.nombre, ' ', con.apellido)) LIKE :q
			       OR LOWER(c.nombre) LIKE :q
			       OR LOWER(COALESCE(c.descripcion, '')) LIKE :q
			       OR LOWER(COALESCE(r.comentario, '')) LIKE :q)
			""")
	Page<ResenaRecibidaDTO> buscarRecibidas(@Param("colaborador") Long colaborador, @Param("q") String q,
			Pageable pageable);

	/** Ids (de los recibidos) de los contratos que ya tienen reseña. Evita una consulta por contrato. */
	@Query("SELECT r.contrato.idContrato FROM Resena r WHERE r.contrato.idContrato IN :ids")
	List<Long> idsContratosResenados(@Param("ids") Collection<Long> ids);
}
