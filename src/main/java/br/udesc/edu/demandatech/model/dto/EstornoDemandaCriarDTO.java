package br.udesc.edu.demandatech.model.dto;

import br.udesc.edu.demandatech.model.entity.Demanda;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EstornoDemandaCriarDTO(
    @NotEmpty(message = "Campo descrição está vazio")
    @Size(max = 50, message = "Campo descrição tem mais de 50 caracteres")
    String descricao,
    @NotEmpty(message = "Campo data está vazio")
    LocalDate data,
    @NotEmpty(message = "Campo idDemanda está vazio")
    Demanda idDemanda
)
{ }
