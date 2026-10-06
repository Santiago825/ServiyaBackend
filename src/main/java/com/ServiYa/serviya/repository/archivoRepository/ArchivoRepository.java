package com.ServiYa.serviya.repository.archivoRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.ServiYa.serviya.modelos.modelos.archivo.ArchivoDTO;

@Repository
public interface ArchivoRepository extends JpaRepository<Persona, Long> {
	@Query(value = "SELECT s.cv as cv  , concat(\"Cv \", s.nombre,\" \",s.apellido) as nombre FROM persona as s where s.id_persona=:id_persona",nativeQuery = true)
	List<Object[]>  obtenerCv(@Param("id_persona") Long id_persona); 

}
