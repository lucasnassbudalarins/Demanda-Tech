package br.udesc.edu.demandatech.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionarioEnvolvido {

    @Id
    @ManyToOne
    @JoinColumn(name = "matricula_usuario")
    private Funcionario funcionario;
    @Id
    @ManyToOne
    @JoinColumn(name = "id_demanda")
    private Demanda demanda;

}
