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
public class EstornoDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_estorno")
    private Long idEstorno;

    private String descricao;
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_demanda")
    private Demanda demanda;
}
