package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tipos_de_demanda")
public class TipoDemanda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tipo")
    private Long idTipo;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;
}
