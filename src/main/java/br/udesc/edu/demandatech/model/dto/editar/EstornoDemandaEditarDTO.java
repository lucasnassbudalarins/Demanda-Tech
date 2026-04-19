package br.udesc.edu.demandatech.model.dto.editar;

import br.udesc.edu.demandatech.model.entity.Demanda;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EstornoDemandaEditarDTO(
    @Size(max = 50, message = "Campo descrição tem mais de 50 caracteres")
    String descricao,
    LocalDate data,
    Demanda demanda
)
{ }
