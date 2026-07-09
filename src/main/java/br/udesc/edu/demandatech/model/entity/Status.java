package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "status")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {
    @MongoId(FieldType.STRING)
    private String idStatus;
    private String descricao;
}
