package br.udesc.edu.demandatech.model.dto.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QtdEstornosPorResponsavel {
    private Long idResponsavel;
    private String responsavel;
    private Long qtdEstornos;
}
