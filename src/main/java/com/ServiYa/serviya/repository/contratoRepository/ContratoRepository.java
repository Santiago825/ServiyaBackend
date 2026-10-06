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
	
	

}
