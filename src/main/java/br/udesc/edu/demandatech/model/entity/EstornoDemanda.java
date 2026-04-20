package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "estorno_demanda", schema = "demanda_tech")
public class EstornoDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estorno_seq")
    @SequenceGenerator(name = "estorno_seq", sequenceName = "demanda_tech.estorno_demanda_id_estorno_seq", allocationSize = 1)
    @Column(name = "id_estorno")
    private Long idEstorno;

    private String descricao;
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_demanda")
    private Demanda demanda;
}
