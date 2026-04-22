package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@IdClass(FuncionarioEnvolvidoId.class)
@Table(name = "funcionarios_envolvidos", schema = "demanda_tech")
public class FuncionarioEnvolvido {

    @Id
    @ManyToOne
    @JoinColumn(name = "matricula_funcionario")
    private Funcionario funcionario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_demanda")
    private Demanda demanda;
}
