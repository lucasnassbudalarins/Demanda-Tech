package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prioridades")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prioridade {
    @Id
    private String idPrioridade;

    private String descricao;
}
