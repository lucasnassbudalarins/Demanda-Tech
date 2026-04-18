package br.udesc.edu.demandatech.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandaCriarDTO {
    @NotEmpty(message = "Field name is empty")
    @Size(max = 255, message = "Field name has more than 255 characters")
    private String titulo;
    @NotNull(message = "Field ranking is empty")
    private LocalDate date;
    @NotNull(message = "Field qtyCalories is empty")
    private Time time;
    @NotNull(message = "Field qtyGlucose is empty")
    private String descricao;
    @NotNull(message = "Field qtyProteins is empty")
    private String descricaoPrioridade;
    @NotEmpty(message = "Field url is empty")
    private TipoDemanda tipoDemanda;


}
