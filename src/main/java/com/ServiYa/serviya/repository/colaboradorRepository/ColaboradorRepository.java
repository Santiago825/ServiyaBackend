package com.ServiYa.serviya.repository.colaboradorRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.ColaboradoR.ColaboradorDTO;
import com.ServiYa.serviya.modelos.modelos.resena.ResenaDTO;

/**
 * RESTAURADO desde el JAR compilado y ACTUALIZADO: las consultas apuntaban a la tabla
 * antigua "reseña"/"id_reseña"; la entidad ahora es Resea (tabla "resea", id_resea).
 * Los DTO se mapean POR POSICIÓN: el orden de columnas debe coincidir con el constructor.
 */
public interface ColaboradorRepository extends JpaRepository<Usuario, Integer> {

	@Query(value = """
			SELECT
			    p.id_persona,
			    p.nombre AS nombre_persona,
			    p.apellido AS apellido_persona,
			    p.telefono,
			    m.nombre AS municipio,
			    s.nombre AS servicio,
			    COUNT(r.id_resea) AS numero_resenas,
			    p.foto,
			    CASE
			        WHEN EXISTS (
			            SELECT 1
			            FROM seguimiento sg
			            WHERE sg.id_colaborador = p.id_persona
			              AND sg.id_contratante = :idContratante
			        ) THEN TRUE
			        ELSE FALSE
			    END AS seguido
			FROM usuario u
			INNER JOIN persona p ON u.id_persona = p.id_persona
			INNER JOIN municipio m ON p.id_municipio = m.id_municipio
			INNER JOIN servicio s ON p.id_servicio = s.id_servicio
			LEFT JOIN resea r ON r.id_colaborador = p.id_persona
			WHERE u.role = 'COLABORADOR'
			  AND (:idServicio IS NULL OR s.id_servicio = :idServicio)
			  AND (:idMunicipio IS NULL OR m.id_municipio = :idMunicipio)
			GROUP BY
			    p.id_persona, p.nombre, p.apellido, p.telefono,
			    m.nombre, s.nombre, p.foto
			""", nativeQuery = true)
	List<ColaboradorDTO> findAllColaboradores(@Param("idServicio") Long idServicio,
			@Param("idMunicipio") Long idMunicipio,@Param("idContratante") Long idContratante);

	@Query(value = """
			SELECT
			    p.id_persona,
			    p.nombre AS nombre_persona,
			    p.apellido AS apellido_persona,
			    p.telefono,
			    m.nombre AS municipio,
			    s.nombre AS servicio,
			    u.username,
			    COUNT(r.id_resea) AS numero_resenas,
			    p.descripcion,
			    p.foto,
			    CASE
			        WHEN EXISTS (
			            SELECT 1
			            FROM seguimiento sg
			            WHERE sg.id_colaborador = p.id_persona
			              AND sg.id_contratante = :idContratante
			        ) THEN TRUE
			        ELSE FALSE
			    END AS seguido
			FROM usuario u
			INNER JOIN persona p ON u.id_persona = p.id_persona
			INNER JOIN municipio m ON p.id_municipio = m.id_municipio
			INNER JOIN servicio s ON p.id_servicio = s.id_servicio
			LEFT JOIN resea r ON r.id_colaborador = p.id_persona
			WHERE u.role = 'COLABORADOR'
			  AND p.id_persona = :idColaborador
			GROUP BY
			    p.id_persona, p.nombre, p.apellido, p.telefono,
			    m.nombre, s.nombre, u.username, p.descripcion, p.foto
			""", nativeQuery = true)
	List<Object[]> findAllDetalleColaborador(@Param("idColaborador") Long idColaborador,@Param("idContratante") Long idContratante);

	@Query(value = """
			SELECT
			    r.id_resea,
			    r.id_colaborador,
			    r.comentario,
			    r.puntuacion,
			    CONCAT(c.nombre, ' ', c.apellido) AS nombre
			FROM resea r
			INNER JOIN persona u ON r.id_colaborador = u.id_persona
			INNER JOIN persona c ON r.id_contratante = c.id_persona -- persona que escribió la reseña
			WHERE r.id_colaborador = :idColaborador
			""", nativeQuery = true)
	List<ResenaDTO> findResenaById(@Param("idColaborador") Long idColaborador);
}
