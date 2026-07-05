package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "demandas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Demanda {
    @Id
    private String idDemanda;

    private String titulo;
    private LocalDate data;
    private LocalTime hora;
    private String descricao;

    private Prioridade prioridade;
    private TipoDemanda tipo;
    private Funcionario criador;
    private Funcionario responsavel;
    private Status status;

    private List<FuncionarioEnvolvido> funcionariosEnvolvidos;
}
