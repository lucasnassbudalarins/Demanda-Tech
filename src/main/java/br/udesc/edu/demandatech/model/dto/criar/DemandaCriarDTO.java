package br.udesc.edu.demandatech.model.dto.criar;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public record DemandaCriarDTO(
    @NotEmpty(message = "Campo título está vazio")
    @Size(max = 30, message = "Campo título tem mais de 30 caracteres")
    String titulo,
    @Size(max = 250, message = "Campo descrição tem mais de 250 caracteres")
    String descricao,
    @NotNull(message = "Campo prioridade está vazio")
    Prioridade prioridade,
    @NotNull(message = "Campo tipo demanda está vazio")
    TipoDemanda tipo,
    @NotNull(message = "Campo criador está vazio")
    Funcionario criador,
    @NotNull(message = "Campo funcionários envolvidos está vazio")
    List<Funcionario> usuariosEnvolvidos
)
{ }
