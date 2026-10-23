package com.ServiYa.serviya.repository.contratoRepository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.ServiYa.serviya.modelos.entity.contrato.Contrato;
import com.ServiYa.serviya.modelos.modelos.contrato.ContratoDTO;
import com.ServiYa.serviya.modelos.modelos.contrato.DetalleContratoDTO;
import java.sql.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import com.ServiYa.serviya.modelos.modelos.contrato.ContratoResenable;
import com.ServiYa.serviya.modelos.modelos.contrato.ContratoResumenDTO;
import com.ServiYa.serviya.modelos.modelos.contrato.EstadoContrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
	
	@Query(value = "SELECT \r\n"
			+ "    c.id_contrato,\r\n"
			+ "    c.nombre AS nombre_contrato,\r\n"
			+ "    CONCAT(p.nombre, ' ', p.apellido) AS nombre_colaborador,\r\n"
			+ "    c.direccion,\r\n"
			+ "    c.precio\r\n"
			+ "FROM contrato c\r\n"
			+ "INNER JOIN persona p ON c.id_colaborador = p.id_persona\r\n"
			+ "WHERE c.id_contratante = :id_contratante;",nativeQuery = true)
	List<ContratoDTO> obtenerContratos(@Param("id_contratante") Long id_contratante);
	@Query(value = "SELECT \r\n"
			+ "    c.id_contrato,CONCAT(p.nombre, ' ', p.apellido) AS nombre_colaborador,\r\n"
			+ "    c.nombre AS nombre_contrato,c.direccion,\r\n"
			+ "    c.fecha_inicio,\r\n"
			+ "    c.fecha_fin,\r\n"
			+ "    c.precio,\r\n"
			+ "    c.descripcion,\r\n"
			+ "    c.nombre_archivo AS contrato,\r\n"
			+ "    p.foto\r\n"
			+ "FROM contrato c\r\n"
			+ "INNER JOIN persona p ON c.id_colaborador = p.id_persona\r\n"
			+ "WHERE c.id_contrato = :idContrato",nativeQuery = true)
	List<DetalleContratoDTO> obtenerContratoDetalle(@Param("idContrato") Long idContrato);
	
	@Query("""
			SELECT new com.ServiYa.serviya.modelos.modelos.contrato.ContratoResumenDTO(
			    c.idContrato, c.nombre, CONCAT(col.nombre, ' ', col.apellido), CONCAT(con.nombre, ' ', con.apellido),
			    c.direccion, c.precio, c.fechaInicio, c.fechaFin, c.estado, c.fechaRespuesta)
			FROM Contrato c JOIN c.colaborador col JOIN c.contratante con
			WHERE con.idPersona = :persona
			ORDER BY c.idContrato DESC
			""")
	List<ContratoResumenDTO> listarPorContratante(@Param("persona") Long persona);

	@Query("""
			SELECT new com.ServiYa.serviya.modelos.modelos.contrato.ContratoResumenDTO(
			    c.idContrato, c.nombre, CONCAT(col.nombre, ' ', col.apellido), CONCAT(con.nombre, ' ', con.apellido),
			    c.direccion, c.precio, c.fechaInicio, c.fechaFin, c.estado, c.fechaRespuesta)
			FROM Contrato c JOIN c.colaborador col JOIN c.contratante con
			WHERE col.idPersona = :persona
			ORDER BY c.idContrato DESC
			""")
	List<ContratoResumenDTO> listarPorColaborador(@Param("persona") Long persona);

	@Query("""
			SELECT new com.ServiYa.serviya.modelos.modelos.contrato.ContratoDetalleDTO(
			    c.idContrato, c.nombre, CONCAT(col.nombre, ' ', col.apellido), CONCAT(con.nombre, ' ', con.apellido),
			    c.direccion, c.telefono, c.fechaInicio, c.fechaFin, c.precio, c.descripcion, c.nombreArchivo,
			    c.estado, c.fechaRespuesta, col.foto)
			FROM Contrato c JOIN c.colaborador col JOIN c.contratante con
			WHERE c.idContrato = :id AND (con.idPersona = :persona OR col.idPersona = :persona)
			""")
	List<com.ServiYa.serviya.modelos.modelos.contrato.ContratoDetalleDTO> detalleParaParticipante(
			@Param("id") Long id, @Param("persona") Long persona);

	/** Datos mínimos del contrato (si es de este contratante) para validar una reseña. */
	@Query("""
			SELECT c.estado AS estado, c.fechaFin AS fechaFin, col.idPersona AS idColaborador
			FROM Contrato c JOIN c.colaborador col JOIN c.contratante con
			WHERE c.idContrato = :id AND con.idPersona = :persona
			""")
	Optional<ContratoResenable> datosParaResenar(@Param("id") Long id, @Param("persona") Long persona);

	/** 1 si la persona es contratante o colaborador del contrato; 0 en otro caso. */
	@Query("""
			SELECT COUNT(c) FROM Contrato c JOIN c.colaborador col JOIN c.contratante con
			WHERE c.idContrato = :id AND (con.idPersona = :persona OR col.idPersona = :persona)
			""")
	long contarParticipante(@Param("id") Long id, @Param("persona") Long persona);

	/** Responde en UNA sentencia atómica: solo si es del contratante Y sigue PENDIENTE. */
	@Modifying(clearAutomatically = true)
	@Query("""
			UPDATE Contrato c SET c.estado = :nuevo, c.fechaRespuesta = :fecha
			WHERE c.idContrato = :id AND c.contratante.idPersona = :persona AND c.estado = :pendiente
			""")
	int responder(@Param("id") Long id, @Param("persona") Long persona, @Param("nuevo") EstadoContrato nuevo,
			@Param("pendiente") EstadoContrato pendiente, @Param("fecha") Date fecha);

	@Query("SELECT c.estado FROM Contrato c WHERE c.idContrato = :id AND c.contratante.idPersona = :persona")
	Optional<EstadoContrato> estadoParaContratante(@Param("id") Long id, @Param("persona") Long persona);

}
