package br.udesc.edu.demandatech.model.dto.editar;

import br.udesc.edu.demandatech.model.entity.Funcionario;
import br.udesc.edu.demandatech.model.entity.Prioridade;
import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public record DemandaEditarDTO(
        @Size(max = 30, message = "Campo título tem mais de 30 caracteres") String titulo,
        LocalDate data,
        LocalTime hora,
        @Size(max = 250, message = "Campo descrição tem mais de 250 caracteres") String descricao,
        Prioridade prioridade,
        TipoDemanda tipo,
        Funcionario criador) {
}
