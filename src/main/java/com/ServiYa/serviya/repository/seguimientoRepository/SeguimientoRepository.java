package com.ServiYa.serviya.repository.seguimientoRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ServiYa.serviya.modelos.entity.seguimiento.Seguimiento;
import com.ServiYa.serviya.modelos.modelos.usuarios.UsuarioSeguidoDTO;

/**
 * Todo lo relativo a la relación contratante -> colaborador (seguir / dejar de seguir / contactos).
 * Antes estas consultas vivían en UserRepository, que mezclaba usuarios con seguimiento.
 */
@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

	boolean existsByColaborador_IdPersonaAndContratante_IdPersona(Long idColaborador, Long idContratante);

	/** Cuántos contratantes siguen a este colaborador. */
	long countByColaborador_IdPersona(Long idColaborador);

	@Modifying
	@Query(value = "DELETE FROM Seguimiento s WHERE s.id_colaborador = :idColaborador AND s.id_contratante = :idContratante", nativeQuery = true)
	int eliminarPorColaboradorYContratante(@Param("idColaborador") Long idColaborador,
			@Param("idContratante") Long idContratante);

	
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
			+ "    u.id_usuario,\r\n"
			+ "    u.username,\r\n"
			+ "    p.foto\r\n"
			+ "FROM usuario u_col\r\n"
			+ "JOIN seguimiento s ON s.id_colaborador = u_col.id_persona\r\n"
			+ "JOIN usuario u ON u.id_persona = s.id_contratante\r\n"
			+ "JOIN persona p ON p.id_persona = s.id_contratante\r\n"
			+ "WHERE u_col.id_usuario = :idUsuario\r\n"
			+ "ORDER BY u.username;", nativeQuery = true)
	List<UsuarioSeguidoDTO> obtenerSeguidoresColaborador(@Param("idUsuario") Long idUsuario);

	

	

	/**
	 * Usernames con los que el usuario tiene una relación de seguimiento (en cualquiera de los dos
	 * sentidos): para un CONTRATANTE, los colaboradores que sigue; para un COLABORADOR, sus seguidores.
	 * Son sus "contactos" del chat y los únicos que ven su estado En línea.
	 */
	@Query(value = "SELECT u2.username FROM usuario u1 "
			+ "JOIN seguimiento s ON s.id_contratante = u1.id_persona "
			+ "JOIN usuario u2 ON u2.id_persona = s.id_colaborador "
			+ "WHERE u1.username = :username "
			+ "UNION "
			+ "SELECT u2.username FROM usuario u1 "
			+ "JOIN seguimiento s ON s.id_colaborador = u1.id_persona "
			+ "JOIN usuario u2 ON u2.id_persona = s.id_contratante "
			+ "WHERE u1.username = :username", nativeQuery = true)
	List<String> findContactUsernames(@Param("username") String username);
}
