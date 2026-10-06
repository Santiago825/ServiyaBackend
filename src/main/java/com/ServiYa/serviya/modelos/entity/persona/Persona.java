package com.ServiYa.serviya.modelos.entity.persona;

import java.util.List;

import com.ServiYa.serviya.modelos.entity.contrato.Contrato;
import com.ServiYa.serviya.modelos.entity.municipio.Municipio;
import com.ServiYa.serviya.modelos.entity.seguimiento.Seguimiento;
import com.ServiYa.serviya.modelos.entity.servicio.Servicio;
import com.ServiYa.serviya.modelos.entity.tipoDocumento.TipoDocumento;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Datos personales de un usuario (contratante o colaborador).
 *
 * RESTAURADO (el archivo original estaba lleno de bytes nulos).
 * Cambios respecto a la versión recuperada:
 *  - @ToString solo con campos escalares: evita el StackOverflowError por ciclos
 *    Persona <-> Servicio / Seguimiento / Contrato y no vuelca los BLOB (foto, cv) en logs.
 *  - Sin @Data: equals/hashCode generados sobre relaciones LAZY provocan cargas
 *    inesperadas y ciclos.
 *  - @JoinColumn mal puesto sobre el @Id reemplazado por @Column (mismo nombre de columna).
 *  - Campos "reseñante/reseñado" (con ñ, y que en realidad eran contratos) renombrados a
 *    nombres ASCII. Son mappedBy: no tienen columna, no afectan la base de datos.
 */
@Entity
@Table(name = "persona")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona")
	@ToString.Include
	private Long idPersona;

	@Column(nullable = false)
	@ToString.Include
	private String nombre;

	@Column(nullable = false)
	@ToString.Include
	private String apellido;

	@Column(nullable = false)
	private String telefono;

	private String descripcion;

	/** Colaboradores a los que este contratante sigue. */
	@OneToMany(mappedBy = "contratante", cascade = CascadeType.ALL)
	private List<Seguimiento> siguiendo;

	/** Contratantes que siguen a este colaborador. */
	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL)
	private List<Seguimiento> seguidores;

	/** Nulo para contratantes. */
	@ManyToOne(targetEntity = Servicio.class)
	@JoinColumn(name = "id_servicio")
	private Servicio servicio;

	@ManyToOne(targetEntity = Municipio.class)
	@JoinColumn(name = "id_municipio", nullable = false)
	private Municipio municipio;

	@ManyToOne(targetEntity = TipoDocumento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_documento", nullable = false)
	private TipoDocumento documento;

	private Long numeroDocumento;

	/** Contratos donde esta persona actúa como contratante. */
	@OneToMany(targetEntity = Contrato.class, fetch = FetchType.LAZY, mappedBy = "contratante")
	private List<Contrato> contratosComoContratante;

	/** Contratos donde esta persona actúa como colaborador. */
	@OneToMany(targetEntity = Contrato.class, fetch = FetchType.LAZY, mappedBy = "colaborador")
	private List<Contrato> contratosComoColaborador;

	@Lob
	@Column(name = "foto", columnDefinition = "LONGBLOB")
	private byte[] foto;

	@Lob
	@Column(name = "cv", columnDefinition = "LONGBLOB")
	private byte[] cv;
}
