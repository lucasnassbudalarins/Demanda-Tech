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
    private LocalDate data;
    private Time hora;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_prioridade")
    private Prioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoDemanda tipo;

    @ManyToOne
    @JoinColumn(name = "matricula_funcionario")
    private Funcionario criador;

    @ManyToOne
    @JoinColumn(name = "matricula_funcionario")
    private Funcionario responsavel;
}
