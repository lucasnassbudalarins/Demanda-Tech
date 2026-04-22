package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "prioridades", schema = "demanda_tech")
public class Prioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prioridades_seq")
    @SequenceGenerator(name = "prioridades_seq", sequenceName = "demanda_tech.prioridades_id_prioridade_seq", allocationSize = 1)
    @Column(name = "id_prioridade")
    private Long idPrioridade;

    private String descricao;
}
