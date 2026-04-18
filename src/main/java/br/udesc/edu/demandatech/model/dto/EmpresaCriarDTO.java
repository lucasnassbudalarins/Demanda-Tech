package br.udesc.edu.demandatech.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmpresaCriarDTO(
    @NotEmpty(message = "Campo cnpj está vazio")
    @Size(max = 14, message = "Campo cnpj tem mais de 14 caracteres")
    String cnpj,
    @NotEmpty(message = "Campo nome social está vazio")
    @Size(max = 100, message = "Campo nome social tem mais de 100 caracteres")
    String nomeSocial,
    Boolean flagInterno
)
{ }
