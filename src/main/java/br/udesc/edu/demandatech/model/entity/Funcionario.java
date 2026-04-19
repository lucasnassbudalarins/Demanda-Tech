package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long matricula;

    private String nome;
    private String email;
    private Boolean admin;

    @ManyToOne
    @Column(name = "id_departamento")
    private Departamento departamento;


}
