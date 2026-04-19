package br.udesc.edu.demandatech.model.dto.editar;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioEditarDTO(
    @Size(max = 100, message = "Campo nome tem mais de 100 caracteres")
    String nome,
    @Email
    String email,
    Boolean admin
)
{ }
