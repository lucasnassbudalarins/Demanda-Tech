package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Data
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_departamento")
    private Long idDepartamento;

    @Column(name = "matricula_funcionario_gerente")
    private Long matriculaFuncionarioGerente;

    private String descricao;

    @ManyToOne
    @Column(name = "id_prioridade")
    private Prioridade idPrioridade;

    @ManyToOne
    @Column(name = "id_tipo")
    private Tipo idTipo;

    @ManyToOne
    @Column(name = "matricula_funcionario")
    private Funcionario matriculaFuncionario;

    @ManyToOne
    @Column(name = "id_usuario")
    private Usuario idusuario;
}
