package br.udesc.edu.demandatech.model.projection;

import br.udesc.edu.demandatech.model.enumerators.Prioridade;
import br.udesc.edu.demandatech.model.enumerators.Status;

import java.time.LocalDate;

public record DemandaProjection(
    Long idDemanda,
    String titulo,
    Status status,
    Prioridade prioridade,
    String tipo,
    String responsavel,
    LocalDate data
) {}
