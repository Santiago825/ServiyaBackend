package com.ServiYa.serviya.repository.departamentoRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ServiYa.serviya.modelos.entity.departamento.Departamento;
import com.ServiYa.serviya.modelos.modelos.departamento.DepartamentoDTO;
import com.ServiYa.serviya.modelos.modelos.servicio.ServicioDTO;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
	@Query( value ="SELECT d.id_departamento, d.nombre FROM serviya.departamento as d order by d.nombre;", nativeQuery = true)
	 List<DepartamentoDTO> findAllDepartamento();

}
