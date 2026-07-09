package br.udesc.edu.demandatech.repository;

import br.udesc.edu.demandatech.model.entity.Demanda;
import br.udesc.edu.demandatech.model.entity.EstornoDemanda;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstornoDemandaRepository extends MongoRepository<EstornoDemanda, String>,
        EstornoDemandaRepositoryCustom {

    List<EstornoDemanda> findByDemandaIdDemanda(String idDemanda);
}