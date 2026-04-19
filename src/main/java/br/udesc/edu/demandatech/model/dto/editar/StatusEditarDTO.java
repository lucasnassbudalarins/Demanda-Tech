package br.udesc.edu.demandatech.model.dto.editar;

import jakarta.validation.constraints.Size;

public record StatusEditarDTO(
    @Size(max = 50, message = "Campo descrição tem mais de 50 caracteres")
    String descricao
)
{ }
