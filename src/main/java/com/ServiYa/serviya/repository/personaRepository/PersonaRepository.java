package com.ServiYa.serviya.repository.personaRepository;

import java.util.List;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.ServiYa.serviya.modelos.modelos.persona.PersonaRainting;
import com.ServiYa.serviya.modelos.modelos.persona.PersonaRainting;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioSeguidoDTO;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
	@Query(value = """
		    SELECT 
		        p.id_persona AS idPersona,
		        CONCAT(p.nombre, ' ', p.apellido) AS nombre,
		        p.foto AS foto,
		        m.nombre AS municipio,
		        s.nombre AS servicio,
		        CAST(COALESCE(SUM(r.puntuacion), 0) AS SIGNED) AS puntuacion
		    FROM persona p
		    JOIN usuario u ON u.id_persona = p.id_persona
		    LEFT JOIN municipio m ON p.id_municipio = m.id_municipio
		    LEFT JOIN servicio s ON p.id_servicio = s.id_servicio
		    LEFT JOIN resea r ON r.id_colaborador = p.id_persona
		    WHERE u.role = 'COLABORADOR'
		    GROUP BY p.id_persona, p.nombre, p.apellido, p.foto, m.nombre, s.nombre
		    ORDER BY puntuacion DESC
		    LIMIT 4
		""", nativeQuery = true)
		List<PersonaRainting> findPersonasRaiting();

	/** Evita registrar dos personas con el mismo tipo y número de documento. */
	boolean existsByDocumento_IdDocumentoAndNumeroDocumento(Long idDocumento, Long numeroDocumento);

}
