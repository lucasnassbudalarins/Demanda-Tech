package br.udesc.edu.demandatech.model.dto;

import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import br.udesc.edu.demandatech.model.entity.Usuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.time.LocalDate;

public record DemandaCriarDTO(
    @NotEmpty(message = "Campo título está vazio")
    @Size(max = 30, message = "Campo título tem mais de 30 caracteres")
    String titulo,
    @NotEmpty(message = "Campo data está vazio")
    LocalDate data,
    @NotEmpty(message = "Campo hora está vazio")
    Time hora,
    @Size(max = 250, message = "Campo descrição tem mais de 250 caracteres")
    String descricao,
    @NotEmpty(message = "Campo prioridade está vazio")
    Prioridade prioridade,
    @NotEmpty(message = "Campo tipo demanda está vazio")
    TipoDemanda tipoDemanda,
    @NotEmpty(message = "Campo usuario está vazio")
    Usuario usuario)
{ }
