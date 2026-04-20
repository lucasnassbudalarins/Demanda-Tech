package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "departamentos", schema = "demanda_tech")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamentos_seq")
    @SequenceGenerator(name = "departamentos_seq", sequenceName = "demanda_tech.departamentos_id_departamento_seq", allocationSize = 1)
    @Column(name = "id_departamento")
    private Long idDepartamento;

    private String descricao;

    @OneToOne
    @JoinColumn(name = "gerente")
    private Funcionario gerente;
}
