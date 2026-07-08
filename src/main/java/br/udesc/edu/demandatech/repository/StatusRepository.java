package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends MongoRepository<Status, String> {
}
