package br.udesc.edu.demandatech.model.entity;

import br.udesc.edu.demandatech.model.enumerators.Prioridade;
import br.udesc.edu.demandatech.model.enumerators.Status;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
