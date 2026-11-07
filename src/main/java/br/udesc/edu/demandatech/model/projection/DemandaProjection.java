package br.udesc.edu.demandatech.model.projection;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.entity.Status;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;

import java.time.LocalDate;

public record DemandaProjection(
    Long idDemanda,
    String titulo,
    Status status,
    Prioridade prioridade,
    TipoDemanda tipo,
    Funcionario responsavel,
    LocalDate data
) {}
