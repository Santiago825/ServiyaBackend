package com.ServiYa.serviya.repository.archivoRepository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.contrato.Contrato;
import com.ServiYa.serviya.modelos.modelos.archivo.ArchivoDTO;

/**
 * REESCRITO (estaba lleno de bytes nulos). Usa JPQL con constructor expression, que es
 * la forma soportada de proyectar a un DTO-clase (ArchivoDTO: nombre, tipoContenido, bytes).
 */
public interface ContratoArchivoRepository extends Repository<Contrato, Long> {

	@Query("""
			SELECT new com.ServiYa.serviya.modelos.modelos.archivo.ArchivoDTO(
			    c.nombreArchivo, c.tipoArchivo, c.arch_contrato)
			FROM Contrato c
			WHERE c.idContrato = :idContrato AND c.arch_contrato IS NOT NULL
			""")
	List<ArchivoDTO> obtenerContrato(@Param("idContrato") Long idContrato);
}
