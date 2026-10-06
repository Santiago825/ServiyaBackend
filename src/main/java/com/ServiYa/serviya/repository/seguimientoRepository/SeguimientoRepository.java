package com.ServiYa.serviya.repository.seguimientoRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ServiYa.serviya.modelos.entity.seguimiento.Seguimiento;
import com.ServiYa.serviya.modelos.modelos.servicio.RequestSeguirColaborador;

import jakarta.transaction.Transactional;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Seguimiento s WHERE s.id_colaborador = :idColaborador AND s.id_contratante = :idContratante", nativeQuery = true)
	int eliminarPorColaboradorYContratante(@Param("idColaborador") Long idColaborador,
			@Param("idContratante") Long idContratante);

	@Query(value = "SELECT * FROM seguimiento WHERE id_colaborador = :idColaborador AND id_contratante = :idContratante", nativeQuery = true)
	Seguimiento encontrarPorColaboradorYContratante(@Param("idColaborador") Long idColaborador,
			@Param("idContratante") Long idContratante);

}
