package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tipos_demanda")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoDemanda {
    @Id
    private String idTipo;
    private String descricao;
    private Departamento departamento;
}
