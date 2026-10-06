package com.ServiYa.serviya.modelos.entity.contrato;

import java.sql.Date;
import java.util.List;

import com.ServiYa.serviya.modelos.entity.persona.Persona;
import com.ServiYa.serviya.modelos.entity.resena.Resena;

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

/** RESTAURADO. El archivo del contrato (arch_contrato) no entra en toString. */
@Entity
@Table(name = "contrato")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Contrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contrato")
	@ToString.Include
	private Long idContrato;

	@ToString.Include
	private String nombre;
	private String direccion;
	private Date fechaInicio;
	private Date fechaFin;
	private String telefono;
	private String precio;
	private String descripcion;
	private String nombreArchivo;
	private String tipoArchivo;

	@Lob
	@Column(name = "arch_contrato", columnDefinition = "LONGBLOB")
	private byte[] arch_contrato;

	@ManyToOne(targetEntity = Persona.class)
	@JoinColumn(name = "id_contratante")
	private Persona contratante;

	@ManyToOne(targetEntity = Persona.class)
	@JoinColumn(name = "id_colaborador")
	private Persona colaborador;

	@OneToMany(targetEntity = Resena.class, fetch = FetchType.LAZY, mappedBy = "contrato")
	private List<Resena> resenas;
}
