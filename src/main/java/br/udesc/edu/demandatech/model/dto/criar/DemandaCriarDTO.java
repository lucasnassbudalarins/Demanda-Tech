package br.udesc.edu.demandatech.model.dto.criar;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DemandaCriarDTO(
    @NotEmpty(message = "Campo título está vazio")
    @Size(max = 30, message = "Campo título tem mais de 30 caracteres")
    String titulo,
    @Size(max = 250, message = "Campo descrição tem mais de 250 caracteres")
    String descricao,
    Prioridade prioridade,
    TipoDemanda tipo,
    Funcionario criador,
    List<Funcionario> usuariosEnvolvidos
)
{ }
