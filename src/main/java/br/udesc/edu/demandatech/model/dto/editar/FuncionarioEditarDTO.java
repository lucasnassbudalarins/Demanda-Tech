package br.udesc.edu.demandatech.model.dto.editar;

import br.udesc.edu.demandatech.model.entity.Departamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record FuncionarioEditarDTO(
    @Size(max = 100, message = "Campo nome tem mais de 100 caracteres")
    String nome,
    @Email
    String email,
    Departamento departamento
)
{ }
