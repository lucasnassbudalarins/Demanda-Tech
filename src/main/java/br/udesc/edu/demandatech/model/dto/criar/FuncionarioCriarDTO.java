package br.udesc.edu.demandatech.model.dto.criar;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FuncionarioCriarDTO(
    @NotEmpty(message = "Campo nome está vazio")
    @Size(max = 255, message = "Campo nome tem mais de 255 caracteres")
    String nome,
    @NotEmpty(message = "Campo email está vazio")
    @Size(max = 255, message = "Campo nome tem mais de 255 caracteres")
    String email,
    @NotEmpty(message = "Campo senha está vazio")
    @Size(max = 60, message = "Campo nome tem mais de 60 caracteres")
    String senha,
    @NotNull(message = "Campo departamento está vazio")
    String departamento,
    @NotNull(message = "Campo admin está vazio")
    boolean admin
)
{ }
