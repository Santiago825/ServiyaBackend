package com.ServiYa.serviya.repository.usuarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ServiYa.serviya.modelos.entity.usuario.Usuario;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioSeguidoDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.UserDTO;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioDetalle;


public interface UserRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmailIgnoreCase(String email);
	@Query(value = "SELECT id_usuario,username,registro_completo,role,id_persona FROM usuario ORDER BY username",nativeQuery = true)
	List<UserDTO> findAllUser();
	@Query(value = "SELECT id_usuario,username,registro_completo,role,id_persona FROM usuario where username=:username",nativeQuery = true)
	List<UserDTO> validUsername(@Param("username") String username);
	
	@Query(value = "SELECT \r\n"
			+ "    u_col.id_usuario AS idUsuario,\r\n"
			+ "    u_col.username AS username,\r\n"
			+ "    p_col.foto AS foto\r\n"
			+ "FROM usuario u_contr\r\n"
			+ "JOIN persona p_contr\r\n"
			+ "    ON u_contr.id_persona = p_contr.id_persona\r\n"
			+ "JOIN seguimiento s\r\n"
			+ "    ON s.id_contratante = p_contr.id_persona\r\n"
			+ "JOIN persona p_col\r\n"
			+ "    ON s.id_colaborador = p_col.id_persona\r\n"
			+ "JOIN usuario u_col\r\n"
			+ "    ON u_col.id_persona = p_col.id_persona\r\n"
			+ "WHERE u_contr.id_usuario = :idUsuario\r\n"
			+ "ORDER BY u_col.username;",nativeQuery = true)
	List<UsuarioSeguidoDTO> obtenerColaboradorSeguido(@Param("idUsuario") Long idUsuario);
	@Query(value = "SELECT \r\n"
			+ "    u.id_usuario, \r\n"
			+ "    u.username, \r\n"
			+ "    p.foto\r\n"
			+ "FROM seguimiento s\r\n"
			+ "JOIN usuario u ON u.id_persona = s.id_contratante\r\n"
			+ "JOIN persona p ON p.id_persona = s.id_contratante\r\n"
			+ "WHERE s.id_colaborador = :idUsuario\r\n"
			+ "ORDER BY u.username;",nativeQuery = true)
	List<UsuarioSeguidoDTO> obtenerSeguidoresColaborador(@Param("idUsuario") Long idUsuario);
	
	@Query(value = "SELECT p.foto\r\n"
			+ "FROM persona p\r\n"
			+ "JOIN usuario u ON u.id_persona = p.id_persona\r\n"
			+ "WHERE u.id_usuario = :idUsuario;",nativeQuery = true)
	byte[] obtenerFoto(@Param("idUsuario") int idUsuario);
	
	
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

	

	

}
