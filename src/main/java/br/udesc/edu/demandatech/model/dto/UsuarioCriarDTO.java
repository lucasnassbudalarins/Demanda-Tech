package br.udesc.edu.demandatech.model.dto;

import br.udesc.edu.demandatech.model.entity.Departamento;
import br.udesc.edu.demandatech.model.entity.Empresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UsuarioCriarDTO(
    @NotEmpty(message = "Campo nome está vazio")
    @Size(max = 100, message = "Campo nome tem mais de 100 caracteres")
    String nome,
    @NotEmpty(message = "Campo email está vazio")
    @Email
    String email,
    Empresa idEmpresa
)
{ }
