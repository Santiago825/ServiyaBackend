package com.ServiYa.serviya.repository.MunicipioRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.municipio.Municipio;
import com.ServiYa.serviya.modelos.modelos.departamento.DepartamentoDTO;
import com.ServiYa.serviya.modelos.modelos.municipio.MunicipioDTO;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

	@Query(value = "SELECT m.id_municipio AS idMunicipio, m.nombre AS nombre, m.id_departamento AS idDepartamento "
			+ "FROM municipio AS m WHERE m.id_departamento = :id", nativeQuery = true)
	List<MunicipioDTO> findAllMunicipio(@Param("id") Long id);

}
