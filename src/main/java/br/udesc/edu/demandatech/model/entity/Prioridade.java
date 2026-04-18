package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_prioridade")
    private Long idPrioridade;

    private String descricao;
}
