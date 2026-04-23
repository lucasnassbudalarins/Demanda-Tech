package br.udesc.edu.demandatech.model.dto.criar;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PrioridadeCriarDTO(
    @NotEmpty(message = "Campo descrição está vazio")
    @Size(max = 30, message = "Campo descrição tem mais de 30 caracteres")
    String descricao
)
{ }
