package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FuncionarioEnvolvidoId implements Serializable {
    private Long funcionario;
    private Long demanda;
}
