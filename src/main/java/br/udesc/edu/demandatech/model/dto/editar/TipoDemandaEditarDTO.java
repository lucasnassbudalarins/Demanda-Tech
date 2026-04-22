package br.udesc.edu.demandatech.model.dto.editar;

import br.udesc.edu.demandatech.model.entity.Departamento;
import jakarta.validation.constraints.Size;

public record TipoDemandaEditarDTO(
    @Size(max = 50, message = "Campo descrição tem mais de 50 caracteres")
    String descricao,
    Departamento departamento
)
{ }
