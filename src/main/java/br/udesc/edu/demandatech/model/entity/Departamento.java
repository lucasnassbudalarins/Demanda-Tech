package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @JoinColumn(name = "gerente")
    private Funcionario gerente;

    @ManyToOne
    @Column(name = "responsavel")
    private Funcionario responsavel;

}
