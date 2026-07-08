package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.TipoDemanda;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDemandaRepository extends MongoRepository<TipoDemanda, String> {
}
