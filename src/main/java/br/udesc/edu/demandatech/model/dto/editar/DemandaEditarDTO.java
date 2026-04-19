package br.udesc.edu.demandatech.model.dto.editar;

import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import br.udesc.edu.demandatech.model.entity.Usuario;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.time.LocalDate;

public record DemandaEditarDTO(
    @Size(max = 30, message = "Campo título tem mais de 30 caracteres")
    String titulo,
    LocalDate data,
    Time hora,
    @Size(max = 250, message = "Campo descrição tem mais de 250 caracteres")
    String descricao,
    Prioridade prioridade,
    TipoDemanda tipoDemanda,
    Usuario usuario)
{ }
