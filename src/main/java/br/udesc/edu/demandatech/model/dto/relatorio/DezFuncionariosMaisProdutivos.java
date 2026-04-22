package br.udesc.edu.demandatech.model.dto.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DezFuncionariosMaisProdutivos {
    private Long matricula;
    private String nome;
    private Long qtdDemanda;
}
