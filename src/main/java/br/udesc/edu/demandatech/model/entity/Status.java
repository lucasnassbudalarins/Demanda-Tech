package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "status")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {
    @Id
    private String idStatus;
    private String descricao;
}
