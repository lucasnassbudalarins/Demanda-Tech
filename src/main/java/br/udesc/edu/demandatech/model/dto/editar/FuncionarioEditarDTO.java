package br.udesc.edu.demandatech.model.dto.editar;

import br.udesc.edu.demandatech.model.entity.Departamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record FuncionarioEditarDTO(
    @NotEmpty(message = "Campo nome está vazio")
    @Size(max = 100, message = "Campo nome tem mais de 100 caracteres")
    String nome,
    @NotEmpty(message = "Campo email está vazio")
    String email,
    @NotEmpty(message = "Campo senha está vazio")
    String senha,
    Departamento departamento,
    boolean admin
){}