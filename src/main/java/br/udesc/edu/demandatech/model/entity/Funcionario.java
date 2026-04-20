package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "funcionarios", schema = "demanda_tech")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionarios_seq")
    @SequenceGenerator(name = "funcionarios_seq", sequenceName = "demanda_tech.funcionarios_matricula_seq", allocationSize = 1)
    private Long matricula;

    private String nome;
    private String email;
    private Boolean admin;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;
}
