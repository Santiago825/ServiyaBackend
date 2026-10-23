package com.ServiYa.serviya.repository.usuarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.usuarios.UserDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioDetalle;


public interface UserRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmailIgnoreCase(String email);
	@Query(value = "SELECT id_usuario,username,registro_completo,role,id_persona FROM usuario ORDER BY username",nativeQuery = true)
	List<UserDTO> findAllUser();
	
	
	@Query(value="SELECT \r\n"
			+ "    u.id_usuario AS idUsuario,\r\n"
			+ "    p.nombre AS nombre,\r\n"
			+ "    p.apellido AS apellido,\r\n"
			+ "    p.telefono AS telefono,\r\n"
			+ "\r\n"
			+ "    p.id_documento AS idDocumento,\r\n"
			+ "    p.numero_documento AS numeroDocumento,\r\n"
			+ "     m.id_departamento AS idDepartamento,p.id_municipio AS idMunicipio,\r\n"
			+ "   \r\n"
			+ "\r\n"
			+ "    p.foto AS foto\r\n"
			+ "FROM usuario u\r\n"
			+ "LEFT JOIN persona p         ON p.id_persona = u.id_persona\r\n"
			+ "LEFT JOIN municipio m       ON m.id_municipio = p.id_municipio\r\n"
			+ "WHERE u.id_usuario = :idUsuario;\r\n"
			+ "",nativeQuery = true)
	List<UsuarioDetalle> usuarioDetalle(@Param("idUsuario") int idUsuario);
	@Query("SELECT u.persona.idPersona FROM Usuario u WHERE u.username = :username")
	Optional<Long> findPersonaIdByUsername(@Param("username") String username);
}
