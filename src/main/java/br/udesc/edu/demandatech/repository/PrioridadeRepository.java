package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Prioridade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioridadeRepository extends MongoRepository<Prioridade, String> {
}
