package br.udesc.edu.demandatech.model.dto.criar;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DepartamentoCriarDTO(
    @NotEmpty(message = "Campo descrição está vazio")
    @Size(max = 50, message = "Campo descrição tem mais de 50 caracteres")
    String descricao,
    Funcionario gerente
) { }
