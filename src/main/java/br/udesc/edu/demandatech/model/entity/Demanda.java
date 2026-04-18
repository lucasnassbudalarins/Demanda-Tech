package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Demanda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_demanda")
    private Long idDemanda;

    private String titulo;
    private LocalDate date;
    private Time time;
    private String descricao;

    @ManyToOne
    @Column(name = "id_prioridade")
    private Prioridade idPrioridade;

    @ManyToOne
    @Column(name = "id_tipo")
    private TipoDemanda idTipo;

    @ManyToOne
    @Column(name = "matricula_funcionario")
    private Funcionario matriculaFuncionario;

    @ManyToOne
    @Column(name = "id_usuario")
    private Usuario idusuario;
}
