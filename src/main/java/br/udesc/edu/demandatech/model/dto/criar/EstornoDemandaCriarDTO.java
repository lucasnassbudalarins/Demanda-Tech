package br.udesc.edu.demandatech.model.dto.criar;

import br.udesc.edu.demandatech.model.entity.Demanda;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EstornoDemandaCriarDTO(
    @NotEmpty(message = "Campo descrição está vazio")
    @Size(max = 50, message = "Campo descrição tem mais de 50 caracteres")
    String descricao,
    @NotNull(message = "Campo data está vazio")
    LocalDate data,
    @NotNull(message = "Campo idDemanda está vazio")
    Demanda demanda
)
{ }
