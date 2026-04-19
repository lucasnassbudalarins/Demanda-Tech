package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Demanda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_demanda")
    private Long idDemanda;

    @Column(name = "id_status")
    private Long status;

    private String titulo;
    private LocalDate data;
    private LocalTime hora;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_prioridade")
    private Prioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoDemanda tipo;

    @ManyToOne
    @JoinColumn(name = "criador")
    private Funcionario criador;

    @ManyToOne
    @JoinColumn(name = "responsavel")
    private Funcionario responsavel;
}
