package com.ServiYa.serviya.modelos.entity.seguimiento;

import java.util.List;

import com.ServiYa.serviya.modelos.entity.persona.Persona;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"contratante", "colaborador"}) // 💡 evita bucle Persona↔Seguimiento
@EqualsAndHashCode(exclude = {"contratante", "colaborador"})
@Builder
public class Seguimiento {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguimiento;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name = "id_contratante", nullable = false)
    private Persona contratante;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name = "id_colaborador", nullable = false)
    private Persona colaborador;

}
