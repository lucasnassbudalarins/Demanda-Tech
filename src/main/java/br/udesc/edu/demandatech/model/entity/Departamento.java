package br.udesc.edu.demandatech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "departamentos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Departamento {
    @Id
    private String idDepartamento;

    private String descricao;
    private Funcionario gerente;
}
