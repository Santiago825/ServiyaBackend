package com.ServiYa.serviya.repository.servicioRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ServiYa.serviya.modelos.entity.servicio.Servicio;
import com.ServiYa.serviya.modelos.modelos.ColaboradoR.ColaboradorDTO;
import com.ServiYa.serviya.modelos.modelos.servicio.ServicioDTO;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
	 @Query( value ="SELECT \r\n"
	 		+ "    s.id_servicio, \r\n"
	 		+ "    s.nombre,s.usos,s.icono \r\n"
	 		+ "FROM \r\n"
	 		+ "    serviya.servicio AS s\r\n"
	 		+ "WHERE \r\n"
	 		+ "    s.id_servicio <> 100\r\n"
	 		+ "ORDER BY \r\n"
	 		+ "    s.nombre;\r\n"
	 		+ "", nativeQuery = true)
	 List<ServicioDTO> findAllServicios();
	 @Query(value="SELECT \r\n"
	 		+ "    id_servicio,\r\n"
	 		+ "    nombre,\r\n"
	 		+ "    usos,\r\n"
	 		+ "    icono\r\n"
	 		+ "FROM servicio\r\n"
	 		+ "ORDER BY usos DESC\r\n"
	 		+ "LIMIT 4;",nativeQuery = true)
	 List<ServicioDTO> findServiciosMasUsos();


}
