package br.udesc.edu.demandatech.model.dto.editar;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import jakarta.validation.constraints.Size;

public record DepartamentoEditarDTO(
    @Size(max = 50, message = "Campo descrição tem mais de 50 caracteres")
    String descricao,
    Funcionario gerente
) { }
