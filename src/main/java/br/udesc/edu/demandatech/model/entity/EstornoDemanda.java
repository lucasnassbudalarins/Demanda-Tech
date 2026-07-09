package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "estornos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstornoDemanda {

    @MongoId(FieldType.STRING)
    private String idEstorno;
    private String descricao;
    private LocalDate data;
    private Demanda demanda;
}
