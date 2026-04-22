package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tipos_de_demanda", schema = "demanda_tech")
public class TipoDemanda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipos_demanda_seq")
    @SequenceGenerator(name = "tipos_demanda_seq", sequenceName = "demanda_tech.tipos_de_demanda_id_tipo_seq", allocationSize = 1)
    @Column(name = "id_tipo")
    private Long idTipo;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;
}
