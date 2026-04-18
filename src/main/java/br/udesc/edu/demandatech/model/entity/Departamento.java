package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_departamento")
    private Long idDepartamento;

    private String descricao;

    @OneToOne
    @Column(name = "matricula_funcionario_gerente")
    private Funcionario gerente;

}
