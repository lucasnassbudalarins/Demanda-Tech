package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tipos_de_demanda")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoDemanda {
    @MongoId(FieldType.STRING)
    private String idTipo;
    private String descricao;
    private Departamento departamento;
}
