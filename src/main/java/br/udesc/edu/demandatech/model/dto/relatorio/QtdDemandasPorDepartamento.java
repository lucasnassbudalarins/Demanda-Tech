package br.udesc.edu.demandatech.model.dto.relatorio;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QtdDemandasPorDepartamento {
    private Long idDepartamento;
    private String departamento;
    private Long qtdDemanda;
}
