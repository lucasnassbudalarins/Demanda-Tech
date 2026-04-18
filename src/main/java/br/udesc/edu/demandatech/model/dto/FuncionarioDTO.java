package br.udesc.edu.demandatech.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record FuncionarioDTO(
    @NotEmpty(message = "Campo nome está vazio")
    @Size(max = 100, message = "Campo descrição tem mais de 50 caracteres")
    String descricao,
    @NotEmpty(message = "Campo data está vazio")
    LocalDate data
)
{ }
