package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Departamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends MongoRepository<Departamento, String> {

    Departamento getDepartamentoByDescricao(String descricao);
}
