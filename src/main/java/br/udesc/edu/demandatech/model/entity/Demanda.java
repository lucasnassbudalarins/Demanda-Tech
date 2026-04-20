package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "demandas", schema = "demanda_tech")
public class Demanda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "demandas_seq")
    @SequenceGenerator(name = "demandas_seq", sequenceName = "demanda_tech.demandas_id_demanda_seq", allocationSize = 1)
    @Column(name = "id_demanda")
    private Long idDemanda;

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

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
}
