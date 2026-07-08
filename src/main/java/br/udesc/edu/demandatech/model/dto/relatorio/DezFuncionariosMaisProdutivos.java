package br.udesc.edu.demandatech.model.dto.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DezFuncionariosMaisProdutivos {
    private String matricula;
    private String nome;
    private Long qtdDemanda;
}
