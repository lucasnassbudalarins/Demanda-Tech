package br.udesc.edu.demandatech.model.dto.criar;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DemandaCriarDTO(
    @NotEmpty(message = "Campo título está vazio")
    @Size(max = 30, message = "Campo título tem mais de 30 caracteres")
    String titulo,
    @Size(max = 250, message = "Campo descrição tem mais de 250 caracteres")
    String descricao,
    @NotNull(message = "Campo prioridade está vazio")
    String prioridade,
    @NotNull(message = "Campo tipo está vazio")
    String tipo,
    @NotNull(message = "Campo criador está vazio")
    String criador,
    @NotNull(message = "Campo usuarios envolvidos está vazio")
    List<String> usuariosEnvolvidos
)
{ }
