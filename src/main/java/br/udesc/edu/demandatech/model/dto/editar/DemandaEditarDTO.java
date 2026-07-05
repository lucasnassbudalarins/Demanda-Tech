package br.udesc.edu.demandatech.model.dto.editar;

import jakarta.validation.constraints.Size;

public record DemandaEditarDTO(
        @Size(max = 30, message = "Campo título tem mais de 30 caracteres") 
        String titulo,
        @Size(max = 250, message = "Campo descrição tem mais de 250 caracteres") 
        String descricao,
        String prioridade,
        String tipo
) {
}
